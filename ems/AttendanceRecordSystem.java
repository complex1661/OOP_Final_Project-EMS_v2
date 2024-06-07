package ems;
import java.util.Date;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Map;

public class AttendanceRecordSystem {
  private TreeMap<CustomDate, TreeMap<String, AttendanceDayRecord>> DayToWorkers;
  private TreeMap<String, TreeMap<CustomDate, AttendanceDayRecord>> WorkerToDays;
  
  // 建構元
  public AttendanceRecordSystem() {
    DayToWorkers = new TreeMap<>();
    WorkerToDays = new TreeMap<>();
  }

  // 新增出席紀錄
  public void addAttendanceRecord(String worker_id, CustomDate date, ClockRecord clockRecord) {
    TreeMap<CustomDate, AttendanceDayRecord> workerAttendance = WorkerToDays.getOrDefault(worker_id, new TreeMap<>());
    AttendanceDayRecord attendanceDayRecord = null;
    if (!workerAttendance.containsKey(date)) {
      attendanceDayRecord = new AttendanceDayRecord(worker_id);
    }
    else {
      attendanceDayRecord = workerAttendance.get(date);
    }
    attendanceDayRecord.addClockRecord(worker_id, clockRecord);
    // 更新員工出席紀錄
    workerAttendance.put(date, attendanceDayRecord);
    // 新增到 WorkersToDays
    WorkerToDays.put(worker_id, workerAttendance);
    // 新增到 DayToWorkers
    TreeMap<String, AttendanceDayRecord> dayRecordsByWorker = DayToWorkers.getOrDefault(date, new TreeMap<>());
    dayRecordsByWorker.put(worker_id, attendanceDayRecord);
    DayToWorkers.put(date, dayRecordsByWorker);
  }
  
  // 新增請假紀錄
  public void addLeaveRecord(String worker_id, CustomDate date, LeaveRecord leaveRecord) {
    TreeMap<CustomDate, AttendanceDayRecord> workerAttendance = WorkerToDays.getOrDefault(worker_id, new TreeMap<>());
    AttendanceDayRecord attendanceDayRecord = null;
    if (!workerAttendance.containsKey(date)) {
        attendanceDayRecord = new AttendanceDayRecord(worker_id);
    } else {
        attendanceDayRecord = workerAttendance.get(date);
    }
    attendanceDayRecord.addLeaveRecord(worker_id, leaveRecord);
    workerAttendance.put(date, attendanceDayRecord); 

    WorkerToDays.put(worker_id, workerAttendance);

    TreeMap<String, AttendanceDayRecord> dayRecordsByWorker = DayToWorkers.getOrDefault(date, new TreeMap<>());
    dayRecordsByWorker.put(worker_id, attendanceDayRecord); 

    DayToWorkers.put(date, dayRecordsByWorker);
}

  
  // 新增整日紀錄
  public void addDayRecord(String worker_id, CustomDate date, AttendanceDayRecord dayRecord) throws IllegalArgumentException {
    // 取得該員工每日工時上限與 UUID
    EWorkerType workerType = Worker.getWorkerById(worker_id).getType();
    int working_hours = WorkerClockInSystem.getMaxWorkingHours(workerType);
    
    // 核對 DayRecord 時數是否合理
    if (dayRecord.getTotalHours() > working_hours){
      throw new IllegalArgumentException("錯誤: 不合理的出缺勤紀錄。"); 
    }
    
    // 檢查該員工是否在該日期已存在出缺勤紀錄
    TreeMap<CustomDate, AttendanceDayRecord> workerAttendance = WorkerToDays.getOrDefault(worker_id, new TreeMap<>());
    if (workerAttendance.containsKey(date)) {
      throw new IllegalArgumentException("錯誤: 員工UUID - " + worker_id + "在" + date.toString() + "已有出缺勤紀錄。");
    }
    
    // 將此紀錄加入 DayToWorkers 該 TreeMap
    TreeMap<String, AttendanceDayRecord> dayRecordsByWorker = DayToWorkers.getOrDefault(date, new TreeMap<>());
    dayRecordsByWorker.put(worker_id, dayRecord);
    DayToWorkers.put(date, dayRecordsByWorker);
    
    // 將此紀錄加入 WorkerToDays 該 TreeMap
    TreeMap<CustomDate, AttendanceDayRecord> recordsByDate = WorkerToDays.getOrDefault(worker_id, new TreeMap<>());
    recordsByDate.put(date, dayRecord);
    WorkerToDays.put(worker_id, recordsByDate);
  }
  
  // 刪除紀錄
  public void deleteDayRecord(String worker_id, CustomDate date) throws IllegalArgumentException {
    // 確認該紀錄是否存在
    TreeMap<CustomDate, AttendanceDayRecord> workerRecords = WorkerToDays.get(worker_id);
    if (workerRecords == null || !workerRecords.containsKey(date)) {
      throw new IllegalArgumentException("錯誤: 員工UUID - " + worker_id + "在" + date.toString() + "無出缺勤紀錄。");
    }
    // 從兩個 TreeMap 移除紀錄
    AttendanceDayRecord record = workerRecords.remove(date);
    DayToWorkers.get(date).remove(worker_id);
    if (workerRecords.isEmpty()) {
      WorkerToDays.remove(worker_id);
    }
  }
  
  // 以年月與員工 UUID 搜尋出缺勤狀況 -> 搜尋某員工在某年某月的出缺勤狀況
  public ArrayList<AttendanceDayRecord> searchRecordByYearMonth(String worker_id, CustomDate date) throws IllegalArgumentException{
    ArrayList<AttendanceDayRecord> recordThisYearMonth = new ArrayList<>();
    TreeMap<CustomDate, AttendanceDayRecord> workerRecords = WorkerToDays.get(worker_id);
    if (workerRecords != null) {
      for (CustomDate recordDate : workerRecords.keySet()) {
        if (recordDate.getYear().equals(date.getYear()) && recordDate.getMonth().equals(date.getMonth())) { 
          AttendanceDayRecord record = workerRecords.get(recordDate);
          recordThisYearMonth.add(record);
        }
      }
      return recordThisYearMonth;
    }
    throw new IllegalArgumentException("錯誤: 員工UUID - " + worker_id + "在" + date.toString() + "無出缺勤紀錄。");
  }
  
  
  public AttendanceDayRecord searchRecordByYearMonthDay(String worker_id, CustomDate date) throws IllegalArgumentException{
    if (date.getDay() == null) throw new IllegalArgumentException("錯誤: 不可無日期。");
    AttendanceDayRecord recordThisYearMonthDay = null;
    TreeMap<CustomDate, AttendanceDayRecord> workerRecord = WorkerToDays.get(worker_id);
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

    for (Map.Entry<CustomDate, TreeMap<String, AttendanceDayRecord>> entry : DayToWorkers.entrySet()) {
      CustomDate recordDate = entry.getKey();
      if (recordDate.getYear().equals(date.getYear()) && recordDate.getMonth().equals(date.getMonth()) && recordDate.getDay().equals(date.getDay())) {
        TreeMap<String, AttendanceDayRecord> dayRecords = entry.getValue();
        for (Map.Entry<String, AttendanceDayRecord> recordEntry : dayRecords.entrySet()) {
          String worker_id = recordEntry.getKey();
          AttendanceDayRecord record = recordEntry.getValue();
          allWorkersRecords.put(worker_id, record);
        }
      }
    }
    return allWorkersRecords;
  }
}