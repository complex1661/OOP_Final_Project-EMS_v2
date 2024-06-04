package ems;
import java.util.Date;
import java.util.UUID;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Map;

public class AttendanceRecordSystem {
  private TreeMap<CustomDate, TreeMap<UUID, AttendanceDayRecord>> DayToWorkers;
  private TreeMap<UUID, TreeMap<CustomDate, AttendanceDayRecord>> WorkerToDays;
  private TreeMap<EWorkerType, Integer> maxWorkingHours;
  
  // 建構元
  public AttendanceRecordSystem() {
    DayToWorkers = new TreeMap<>();
    WorkerToDays = new TreeMap<>();
    maxWorkingHours = new TreeMap<>();
    maxWorkingHours.put(EWorkerType.PARTTIME, PartTimeWorker.WORKING_HOURS);
    maxWorkingHours.put(EWorkerType.FULLTIME, FullTimeWorker.WORKING_HOURS);
    maxWorkingHours.put(EWorkerType.SUPERVISOR, Supervisor.WORKING_HOURS);
  }

  // 新增紀錄
  public void addDayRecord(UUID worker_uuid, CustomDate date, AttendanceDayRecord dayRecord) throws IllegalArgumentException {
    // 取得該員工每日工時上限與 UUID
    EWorkerType workerType = Worker.getWorkerByUUID(worker_uuid).getType();
    int working_hours = maxWorkingHours.get(workerType);
    
    // 核對 DayRecord 時數是否合理
    if (dayRecord.getTotalHours() != working_hours){
      throw new IllegalArgumentException("錯誤: 不合理的出缺勤紀錄。"); 
    }
    
    // 檢查該員工是否在該日期已存在出缺勤紀錄
    TreeMap<CustomDate, AttendanceDayRecord> workerAttendance = WorkerToDays.getOrDefault(worker_uuid, new TreeMap<>());
    if (workerAttendance.containsKey(date)) {
      throw new IllegalArgumentException("錯誤: 員工UUID - " + worker_uuid.toString() + "在" + date.toString() + "已有出缺勤紀錄。");
    }
    
    // 將此紀錄加入 DayToWorkers 該 TreeMap
    TreeMap<UUID, AttendanceDayRecord> dayRecordsByWorker = DayToWorkers.getOrDefault(date, new TreeMap<>());
    dayRecordsByWorker.put(worker_uuid, dayRecord);
    DayToWorkers.put(date, dayRecordsByWorker);
    
    // 將此紀錄加入 WorkerToDays 該 TreeMap
    TreeMap<CustomDate, AttendanceDayRecord> recordsByDate = WorkerToDays.getOrDefault(worker_uuid, new TreeMap<>());
    recordsByDate.put(date, dayRecord);
    WorkerToDays.put(worker_uuid, recordsByDate);
  }
  
  // 刪除紀錄
  public void deleteDayRecord(UUID worker_uuid, CustomDate date) throws IllegalArgumentException {
    // 確認該紀錄是否存在
    TreeMap<CustomDate, AttendanceDayRecord> workerRecords = WorkerToDays.get(worker_uuid);
    if (workerRecords == null || !workerRecords.containsKey(date)) {
      throw new IllegalArgumentException("錯誤: 員工UUID - " + worker_uuid.toString() + "在" + date.toString() + "無出缺勤紀錄。");
    }
    // 從兩個 TreeMap 移除紀錄
    AttendanceDayRecord record = workerRecords.remove(date);
    DayToWorkers.get(date).remove(worker_uuid);
    if (workerRecords.isEmpty()) {
      WorkerToDays.remove(worker_uuid);
    }
  }
  
  // 以年月與員工 UUID 搜尋出缺勤狀況 -> 搜尋某員工在某年某月的出缺勤狀況
  public ArrayList<AttendanceDayRecord> searchRecordByYearMonth(UUID worker_uuid, CustomDate date) {
    ArrayList<AttendanceDayRecord> recordThisYearMonth = new ArrayList<>();
    TreeMap<CustomDate, AttendanceDayRecord> workerRecords = WorkerToDays.get(worker_uuid);
    if (workerRecords != null) {
      for (CustomDate recordDate : workerRecords.keySet()) {
        if (recordDate.getYear().equals(date.getYear()) && recordDate.getMonth().equals(date.getMonth())) {
          AttendanceDayRecord record = workerRecords.get(recordDate);
          recordThisYearMonth.add(record);
        }
      }
      return recordThisYearMonth;
    }
    throw new IllegalArgumentException("錯誤: 員工UUID - " + worker_uuid.toString() + "在" + date.toString() + "無出缺勤紀錄。");
  }
  
  // 以年月日搜尋所有員工的出缺勤狀況
  public TreeMap<UUID, AttendanceDayRecord> searchAllWorkersRecordsByYearMonthDay(CustomDate date){
    
    TreeMap<UUID, AttendanceDayRecord> allWorkersRecords = new TreeMap<>();

    for (Map.Entry<CustomDate, TreeMap<UUID, AttendanceDayRecord>> entry : DayToWorkers.entrySet()) {
      CustomDate recordDate = entry.getKey();
      if (recordDate.getYear().equals(date.getYear()) && recordDate.getMonth().equals(date.getMonth()) && recordDate.getDay().equals(date.getDay())) {
        TreeMap<UUID, AttendanceDayRecord> dayRecords = entry.getValue();
        for (Map.Entry<UUID, AttendanceDayRecord> recordEntry : dayRecords.entrySet()) {
          UUID worker_uuid = recordEntry.getKey();
          AttendanceDayRecord record = recordEntry.getValue();
          allWorkersRecords.put(worker_uuid, record);
        }
      }
    }
    return allWorkersRecords;
  }
}