package ems;
import java.util.Date;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Map;

public class AttendanceRecordSystem {
  private TreeMap<CustomDate, TreeMap<String, AttendanceDayRecord>> dayToWorkers;
  private TreeMap<String, TreeMap<CustomDate, AttendanceDayRecord>> workerToDays;
  
  // 建構元
  public AttendanceRecordSystem() {
    dayToWorkers = new TreeMap<>();
    workerToDays = new TreeMap<>();
  }
  
  // 取得或做出當天出缺勤紀錄
  private AttendanceDayRecord getOrCreateAttendanceDayRecord(String workerId, CustomDate date) {
    TreeMap<CustomDate, AttendanceDayRecord> workerAttendance = workerToDays.getOrDefault(workerId, new TreeMap<>());
    return workerAttendance.getOrDefault(date, new AttendanceDayRecord(workerId));
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
    // 取得該員工每日工時上限與 UUID
    EWorkerType workerType = Worker.getWorkerById(workerId).getType();
    int working_hours = WorkerClockInSystem.getMaxWorkingHours(workerType);
    
    // 核對 DayRecord 時數是否合理
    if (dayRecord.getTotalHours() > working_hours){
      throw new IllegalArgumentException("錯誤: 不合理的出缺勤紀錄。"); 
    }
    
    // 檢查該員工是否在該日期已存在出缺勤紀錄
    TreeMap<CustomDate, AttendanceDayRecord> workerAttendance = workerToDays.getOrDefault(workerId, new TreeMap<>());
    if (workerAttendance.containsKey(date)) {
      throw new IllegalArgumentException("錯誤: 員工UUID - " + workerId + "在" + date.toString() + "已有出缺勤紀錄。");
    }
    
    // 將此紀錄加入 DayToWorkers 該 TreeMap
    TreeMap<String, AttendanceDayRecord> dayRecordsByWorker = dayToWorkers.getOrDefault(date, new TreeMap<>());
    dayRecordsByWorker.put(workerId, dayRecord);
    dayToWorkers.put(date, dayRecordsByWorker);
    
    // 將此紀錄加入 WorkerToDays 該 TreeMap
    TreeMap<CustomDate, AttendanceDayRecord> recordsByDate = workerToDays.getOrDefault(workerId, new TreeMap<>());
    recordsByDate.put(date, dayRecord);
    workerToDays.put(workerId, recordsByDate);
  }
  
  // 刪除紀錄
  public void deleteDayRecord(String workerId, CustomDate date) throws IllegalArgumentException {
    // 確認該紀錄是否存在
    TreeMap<CustomDate, AttendanceDayRecord> workerRecords = workerToDays.get(workerId);
    if (workerRecords == null || !workerRecords.containsKey(date)) {
      throw new IllegalArgumentException("錯誤: 員工UUID - " + workerId + "在" + date.toString() + "無出缺勤紀錄。");
    }
    // 從兩個 TreeMap 移除紀錄
    AttendanceDayRecord record = workerRecords.remove(date);
    dayToWorkers.get(date).remove(workerId);
    if (workerRecords.isEmpty()) {
      workerToDays.remove(workerId);
    }
  }
  
  // 以年月與員工 UUID 搜尋出缺勤狀況 -> 搜尋某員工在某年某月的出缺勤狀況
  public ArrayList<AttendanceDayRecord> searchRecordByYearMonth(String workerId, CustomDate date) throws IllegalArgumentException{
    ArrayList<AttendanceDayRecord> recordThisYearMonth = new ArrayList<>();
    TreeMap<CustomDate, AttendanceDayRecord> workerRecords = workerToDays.get(workerId);
    if (workerRecords != null) {
      for (CustomDate recordDate : workerRecords.keySet()) {
        if (recordDate.getYear().equals(date.getYear()) && recordDate.getMonth().equals(date.getMonth())) { 
          AttendanceDayRecord record = workerRecords.get(recordDate);
          recordThisYearMonth.add(record);
        }
      }
      return recordThisYearMonth;
    }
    throw new IllegalArgumentException("錯誤: 員工UUID - " + workerId + "在" + date.toString() + "無出缺勤紀錄。");
  }
  
  
  public AttendanceDayRecord searchRecordByYearMonthDay(String workerId, CustomDate date) throws IllegalArgumentException{
    if (date.getDay() == null) throw new IllegalArgumentException("錯誤: 不可無日期。");
    AttendanceDayRecord recordThisYearMonthDay = null;
    TreeMap<CustomDate, AttendanceDayRecord> workerRecord = workerToDays.get(workerId);
    if (workerRecord != null) {
      for (CustomDate recordDate : workerRecord.keySet()) {
        if (recordDate.getYear().equals(date.getYear()) && recordDate.getMonth().equals(date.getMonth()) && recordDate.getDay().equals(date.getDay())) { 
          AttendanceDayRecord record = workerRecord.get(recordDate);
          recordThisYearMonthDay = record;
        }
      }
      if (recordThisYearMonthDay == null) throw new IllegalArgumentException("錯誤: 此日期該員工無出缺勤紀錄。");
    }
    return recordThisYearMonthDay;
  }
  
  
  
  // 以年月日搜尋所有員工的出缺勤狀況
  public TreeMap<String, AttendanceDayRecord> searchAllWorkersRecordsByYearMonthDay(CustomDate date){
    
    TreeMap<String, AttendanceDayRecord> allWorkersRecords = new TreeMap<>();
    
    for (Map.Entry<CustomDate, TreeMap<String, AttendanceDayRecord>> entry : dayToWorkers.entrySet()) {
      CustomDate recordDate = entry.getKey();
      if (recordDate.getYear().equals(date.getYear()) && recordDate.getMonth().equals(date.getMonth()) && recordDate.getDay().equals(date.getDay())) {
        TreeMap<String, AttendanceDayRecord> dayRecords = entry.getValue();
        for (Map.Entry<String, AttendanceDayRecord> recordEntry : dayRecords.entrySet()) {
          String workerId = recordEntry.getKey();
          AttendanceDayRecord record = recordEntry.getValue();
          allWorkersRecords.put(workerId, record);
        }
      }
    }
    return allWorkersRecords;
  }
  
  // 刪除並移除員工所有紀錄(刪除員工) 
  public void deleteWorkerRecords(String workerId) throws IllegalArgumentException {
    if (workerId == null) throw new IllegalArgumentException("錯誤：員工 ID 不可為空。");
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