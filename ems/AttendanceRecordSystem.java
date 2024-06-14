package ems;
import java.util.Date;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Map;
import java.io.*;

public class AttendanceRecordSystem implements Serializable{
  private static final long serialVersionUID = 1L;
  
  private TreeMap<CustomDate, TreeMap<String, AttendanceDayRecord>> dayToWorkers;
  private TreeMap<String, TreeMap<CustomDate, AttendanceDayRecord>> workerToDays;
  
  // 建構元
  public AttendanceRecordSystem() {
    dayToWorkers = new TreeMap<>();
    workerToDays = new TreeMap<>();
  }
  
  public AttendanceRecordSystem(TreeMap<CustomDate, TreeMap<String, AttendanceDayRecord>> dayToWorkers, TreeMap<String, TreeMap<CustomDate, AttendanceDayRecord>> workerToDays) {
    this.dayToWorkers = dayToWorkers;
    this.workerToDays = workerToDays;
  }
  
  public TreeMap<CustomDate, TreeMap<String, AttendanceDayRecord>> getDayToWorkers() {
    return dayToWorkers;
  }
  
  public TreeMap<String, TreeMap<CustomDate, AttendanceDayRecord>> getWorkerToDays() {
    return workerToDays;
  }
  
  // 取得或做出當天出缺勤紀錄
  private AttendanceDayRecord getOrCreateAttendanceDayRecord(String workerId, CustomDate date) {
    TreeMap<CustomDate, AttendanceDayRecord> workerAttendance = workerToDays.getOrDefault(workerId, new TreeMap<>());
    return workerAttendance.getOrDefault(date, new AttendanceDayRecord());
  }
  
  // 更新至系統
  private void updateRecords(String workerId, CustomDate date, AttendanceDayRecord attendanceDayRecord) {
    // 更新至 workerToDays
    TreeMap<CustomDate, AttendanceDayRecord> workerAttendance = workerToDays.getOrDefault(workerId, new TreeMap<>());
    workerAttendance.put(date, attendanceDayRecord);
    workerToDays.put(workerId, workerAttendance);
    
    // 更新至 dayToWorkers
    TreeMap<String, AttendanceDayRecord> dayRecordsByWorker = dayToWorkers.getOrDefault(date, new TreeMap<>());
    dayRecordsByWorker.put(workerId, attendanceDayRecord);
    dayToWorkers.put(date, dayRecordsByWorker);
  }
  
  // 新增出席紀錄
  public void addAttendanceRecord(String workerId, CustomDate date, ClockRecord clockRecord) {
    AttendanceDayRecord attendanceDayRecord = getOrCreateAttendanceDayRecord(workerId, date);
    attendanceDayRecord.addClockRecord(workerId, clockRecord);
    updateRecords(workerId, date, attendanceDayRecord);
  }
  
  // 新增請假紀錄
  public void addLeaveRecord(String workerId, CustomDate date, LeaveRecord leaveRecord) {
    AttendanceDayRecord attendanceDayRecord = getOrCreateAttendanceDayRecord(workerId, date);
    attendanceDayRecord.addLeaveRecord(workerId, leaveRecord);
    updateRecords(workerId, date, attendanceDayRecord);
  }
  
  // 新增缺席紀錄
  public void addAbsentRecord(String workerId, CustomDate date, AbsentRecord absentRecord) {
    AttendanceDayRecord attendanceDayRecord = getOrCreateAttendanceDayRecord(workerId, date);
    attendanceDayRecord.addAbsentRecord(workerId, absentRecord);
    updateRecords(workerId, date, attendanceDayRecord);
  }
  
  // 新增加班紀錄
  public void addOvertimeRecord(String workerId, CustomDate date, OvertimeRecord overtimeRecord) {
    AttendanceDayRecord attendanceDayRecord = getOrCreateAttendanceDayRecord(workerId, date);
    attendanceDayRecord.addOvertimeRecord(workerId, overtimeRecord);
    updateRecords(workerId, date, attendanceDayRecord);
  }
  
  // 新增整日紀錄
  public void addDayRecord(String workerId, CustomDate date, AttendanceDayRecord dayRecord) throws IllegalArgumentException {
    // Check if record already exists
    if (workerToDays.getOrDefault(workerId, new TreeMap<>()).containsKey(date)) {
      throw new IllegalArgumentException("錯誤: 員工UUID - " + workerId + "在" + date.toString() + "已有出缺勤紀錄。");
    }
    
    // Verify working hours limit
    int workingHours = WorkerClockInSystem.getMaxWorkingHours(Worker.getWorkerById(workerId).getType());
    if (dayRecord.getTotalHours() > workingHours) {
      throw new IllegalArgumentException("錯誤: 不合理的出缺勤紀錄。");
    }
    updateRecords(workerId, date, dayRecord);
  }
  
  // 刪除紀錄
  public void deleteDayRecord(String workerId, CustomDate date) throws IllegalArgumentException {
    // 確認該紀錄是否存在
    TreeMap<CustomDate, AttendanceDayRecord> workerRecords = workerToDays.get(workerId);
    if (workerRecords == null || !workerRecords.containsKey(date)) {
      throw new IllegalArgumentException("錯誤: 員工UUID - " + workerId + "在" + date.toString() + "無出缺勤紀錄。");
    }
    
    // 如果含有特休，將數量加回
    AttendanceDayRecord dayRecord = workerRecords.get(date);
    LeaveRecord leaveRecord = dayRecord.getLeaveRecord();
    if (leaveRecord.getLeaveType().equals("特休")) {
      Worker w = Worker.getWorkerById(workerId);
      w.addPaidLeaveDays();
    }
    
    // 從兩個 TreeMap 移除紀錄
    workerRecords.remove(date);
    if (workerRecords.isEmpty()) workerToDays.remove(workerId);

    TreeMap<String, AttendanceDayRecord> dayRecords = dayToWorkers.get(date);
    if (dayRecords != null) {
      dayRecords.remove(workerId);
      if (dayRecords.isEmpty()) dayToWorkers.remove(date);
    }
  }
  
  // 以年月與員工 UUID 搜尋出缺勤狀況 -> 搜尋某員工在某年某月的出缺勤狀況
  public ArrayList<AttendanceDayRecord> searchRecordByYearMonth(String workerId, CustomDate date) throws IllegalArgumentException{
    ArrayList<AttendanceDayRecord> records = new ArrayList<>();
    TreeMap<CustomDate, AttendanceDayRecord> workerRecords = workerToDays.get(workerId);
    if (workerRecords != null) {
      for (Map.Entry<CustomDate, AttendanceDayRecord> entry : workerRecords.entrySet()) {
        CustomDate recordDate = entry.getKey();
        if (recordDate.getYear().equals(date.getYear()) && recordDate.getMonth().equals(date.getMonth())) {
          records.add(entry.getValue());
        }
      }
    }
    
    if (records.isEmpty()) {
      throw new IllegalArgumentException("錯誤: 員工UUID - " + workerId + "在" + date.toString() + "無出缺勤紀錄。");
    }
    
    return records;
  }
  
  
  public AttendanceDayRecord searchRecordByYearMonthDay(String workerId, CustomDate date) throws IllegalArgumentException{
    if (date.getDay() == null) throw new IllegalArgumentException("錯誤: 不可無日期。");
    
    TreeMap<CustomDate, AttendanceDayRecord> workerRecords = workerToDays.get(workerId);
    if (workerRecords != null) {
      AttendanceDayRecord record = workerRecords.get(date);
      if (record != null) return record;
    }
    
    throw new IllegalArgumentException("錯誤: 此日期該員工無出缺勤紀錄。");
  }
  
  
  
  // 以年月日搜尋所有員工的出缺勤狀況
  public TreeMap<String, AttendanceDayRecord> searchAllWorkersRecordsByYearMonthDay(CustomDate date){
    
   TreeMap<String, AttendanceDayRecord> allWorkersRecords = new TreeMap<>();
    TreeMap<String, AttendanceDayRecord> dayRecords = dayToWorkers.get(date);
    
    if (dayRecords != null) {
      allWorkersRecords.putAll(dayRecords);
    } else {
      throw new IllegalArgumentException("錯誤: 此日期所有員工都無出缺勤紀錄。");
    }
    
    return allWorkersRecords;
  }
  
  // 刪除並移除員工所有紀錄(刪除員工) 
  public void deleteWorkerRecords(String workerId) throws IllegalArgumentException {
    // 從 workerToDays 移除
    TreeMap<CustomDate, AttendanceDayRecord> records = workerToDays.remove(workerId);
    // 從 dayToWorkers 移除
    if (records != null) {
      for (CustomDate date : records.keySet()) {
        TreeMap<String, AttendanceDayRecord> dayRecords = dayToWorkers.get(date);
        if (dayRecords != null) {
          dayRecords.remove(workerId);
          if (dayRecords.isEmpty()) {
            dayToWorkers.remove(date);
          }
        }
      }
    }
  }
}