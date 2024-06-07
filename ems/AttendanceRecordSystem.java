package ems;
import java.util.Date;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Map;

public class AttendanceRecordSystem {
  private TreeMap<CustomDate, TreeMap<String, AttendanceDayRecord>> DayToWorkers;
  private TreeMap<String, TreeMap<CustomDate, AttendanceDayRecord>> WorkerToDays;
  
  // �غc��
  public AttendanceRecordSystem() {
    DayToWorkers = new TreeMap<>();
    WorkerToDays = new TreeMap<>();
  }

  // �s�W�X�u����
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
    // ��s���u�X�u����
    workerAttendance.put(date, attendanceDayRecord);
    // �s�W�� WorkersToDays
    WorkerToDays.put(worker_id, workerAttendance);
    // �s�W�� DayToWorkers
    TreeMap<String, AttendanceDayRecord> dayRecordsByWorker = DayToWorkers.getOrDefault(date, new TreeMap<>());
    dayRecordsByWorker.put(worker_id, attendanceDayRecord);
    DayToWorkers.put(date, dayRecordsByWorker);
  }
  
  // �s�W�а�����
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

  
  // �s�W������
  public void addDayRecord(String worker_id, CustomDate date, AttendanceDayRecord dayRecord) throws IllegalArgumentException {
    // ���o�ӭ��u�C��u�ɤW���P UUID
    EWorkerType workerType = Worker.getWorkerById(worker_id).getType();
    int working_hours = WorkerClockInSystem.getMaxWorkingHours(workerType);
    
    // �ֹ� DayRecord �ɼƬO�_�X�z
    if (dayRecord.getTotalHours() > working_hours){
      throw new IllegalArgumentException("���~: ���X�z���X�ʶԬ����C"); 
    }
    
    // �ˬd�ӭ��u�O�_�b�Ӥ���w�s�b�X�ʶԬ���
    TreeMap<CustomDate, AttendanceDayRecord> workerAttendance = WorkerToDays.getOrDefault(worker_id, new TreeMap<>());
    if (workerAttendance.containsKey(date)) {
      throw new IllegalArgumentException("���~: ���uUUID - " + worker_id + "�b" + date.toString() + "�w���X�ʶԬ����C");
    }
    
    // �N�������[�J DayToWorkers �� TreeMap
    TreeMap<String, AttendanceDayRecord> dayRecordsByWorker = DayToWorkers.getOrDefault(date, new TreeMap<>());
    dayRecordsByWorker.put(worker_id, dayRecord);
    DayToWorkers.put(date, dayRecordsByWorker);
    
    // �N�������[�J WorkerToDays �� TreeMap
    TreeMap<CustomDate, AttendanceDayRecord> recordsByDate = WorkerToDays.getOrDefault(worker_id, new TreeMap<>());
    recordsByDate.put(date, dayRecord);
    WorkerToDays.put(worker_id, recordsByDate);
  }
  
  // �R������
  public void deleteDayRecord(String worker_id, CustomDate date) throws IllegalArgumentException {
    // �T�{�Ӭ����O�_�s�b
    TreeMap<CustomDate, AttendanceDayRecord> workerRecords = WorkerToDays.get(worker_id);
    if (workerRecords == null || !workerRecords.containsKey(date)) {
      throw new IllegalArgumentException("���~: ���uUUID - " + worker_id + "�b" + date.toString() + "�L�X�ʶԬ����C");
    }
    // �q��� TreeMap ��������
    AttendanceDayRecord record = workerRecords.remove(date);
    DayToWorkers.get(date).remove(worker_id);
    if (workerRecords.isEmpty()) {
      WorkerToDays.remove(worker_id);
    }
  }
  
  // �H�~��P���u UUID �j�M�X�ʶԪ��p -> �j�M�Y���u�b�Y�~�Y�몺�X�ʶԪ��p
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
    throw new IllegalArgumentException("���~: ���uUUID - " + worker_id + "�b" + date.toString() + "�L�X�ʶԬ����C");
  }
  
  
  public AttendanceDayRecord searchRecordByYearMonthDay(String worker_id, CustomDate date) throws IllegalArgumentException{
    if (date.getDay() == null) throw new IllegalArgumentException("���~: ���i�L����C");
    AttendanceDayRecord recordThisYearMonthDay = null;
    TreeMap<CustomDate, AttendanceDayRecord> workerRecord = WorkerToDays.get(worker_id);
    if (workerRecord != null) {
      for (CustomDate recordDate : workerRecord.keySet()) {
        if (recordDate.getYear().equals(date.getYear()) && recordDate.getMonth().equals(date.getMonth()) && recordDate.getDay().equals(date.getDay())) { 
          AttendanceDayRecord record = workerRecord.get(recordDate);
          recordThisYearMonthDay = record;
        }
      }
      if (recordThisYearMonthDay == null) throw new IllegalArgumentException("���~: ������ӭ��u�L�X�ʶԬ����C");
    }
    return recordThisYearMonthDay;
  }
  
  
  
  // �H�~���j�M�Ҧ����u���X�ʶԪ��p
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