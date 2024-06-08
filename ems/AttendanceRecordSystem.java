package ems;
import java.util.Date;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Map;

public class AttendanceRecordSystem {
  private TreeMap<CustomDate, TreeMap<String, AttendanceDayRecord>> dayToWorkers;
  private TreeMap<String, TreeMap<CustomDate, AttendanceDayRecord>> workerToDays;
  
  // �غc��
  public AttendanceRecordSystem() {
    dayToWorkers = new TreeMap<>();
    workerToDays = new TreeMap<>();
  }
  
  // ���o�ΰ��X��ѥX�ʶԬ���
  private AttendanceDayRecord getOrCreateAttendanceDayRecord(String workerId, CustomDate date) {
    TreeMap<CustomDate, AttendanceDayRecord> workerAttendance = workerToDays.getOrDefault(workerId, new TreeMap<>());
    return workerAttendance.getOrDefault(date, new AttendanceDayRecord(workerId));
  }
  
  // ��s�ܨt��
  private void updateRecords(String workerId, CustomDate date, AttendanceDayRecord attendanceDayRecord) {
    // ��s�� workerToDays
    TreeMap<CustomDate, AttendanceDayRecord> workerAttendance = workerToDays.getOrDefault(workerId, new TreeMap<>());
    workerAttendance.put(date, attendanceDayRecord);
    workerToDays.put(workerId, workerAttendance);
    
    // ��s�� dayToWorkers
    TreeMap<String, AttendanceDayRecord> dayRecordsByWorker = dayToWorkers.getOrDefault(date, new TreeMap<>());
    dayRecordsByWorker.put(workerId, attendanceDayRecord);
    dayToWorkers.put(date, dayRecordsByWorker);
  }
  
  // �s�W�X�u����
  public void addAttendanceRecord(String workerId, CustomDate date, ClockRecord clockRecord) {
    AttendanceDayRecord attendanceDayRecord = getOrCreateAttendanceDayRecord(workerId, date);
    attendanceDayRecord.addClockRecord(workerId, clockRecord);
    updateRecords(workerId, date, attendanceDayRecord);
  }
  
  // �s�W�а�����
  public void addLeaveRecord(String workerId, CustomDate date, LeaveRecord leaveRecord) {
    AttendanceDayRecord attendanceDayRecord = getOrCreateAttendanceDayRecord(workerId, date);
    attendanceDayRecord.addLeaveRecord(workerId, leaveRecord);
    updateRecords(workerId, date, attendanceDayRecord);
  }
  
  // �s�W�ʮu����
  public void addAbsentRecord(String workerId, CustomDate date, AbsentRecord absentRecord) {
    AttendanceDayRecord attendanceDayRecord = getOrCreateAttendanceDayRecord(workerId, date);
    attendanceDayRecord.addAbsentRecord(workerId, absentRecord);
    updateRecords(workerId, date, attendanceDayRecord);
  }
  
  // �s�W�[�Z����
  public void addOvertimeRecord(String workerId, CustomDate date, OvertimeRecord overtimeRecord) {
    AttendanceDayRecord attendanceDayRecord = getOrCreateAttendanceDayRecord(workerId, date);
    attendanceDayRecord.addOvertimeRecord(workerId, overtimeRecord);
    updateRecords(workerId, date, attendanceDayRecord);
  }
  
  // �s�W������
  public void addDayRecord(String workerId, CustomDate date, AttendanceDayRecord dayRecord) throws IllegalArgumentException {
    // ���o�ӭ��u�C��u�ɤW���P UUID
    EWorkerType workerType = Worker.getWorkerById(workerId).getType();
    int working_hours = WorkerClockInSystem.getMaxWorkingHours(workerType);
    
    // �ֹ� DayRecord �ɼƬO�_�X�z
    if (dayRecord.getTotalHours() > working_hours){
      throw new IllegalArgumentException("���~: ���X�z���X�ʶԬ����C"); 
    }
    
    // �ˬd�ӭ��u�O�_�b�Ӥ���w�s�b�X�ʶԬ���
    TreeMap<CustomDate, AttendanceDayRecord> workerAttendance = workerToDays.getOrDefault(workerId, new TreeMap<>());
    if (workerAttendance.containsKey(date)) {
      throw new IllegalArgumentException("���~: ���uUUID - " + workerId + "�b" + date.toString() + "�w���X�ʶԬ����C");
    }
    
    // �N�������[�J DayToWorkers �� TreeMap
    TreeMap<String, AttendanceDayRecord> dayRecordsByWorker = dayToWorkers.getOrDefault(date, new TreeMap<>());
    dayRecordsByWorker.put(workerId, dayRecord);
    dayToWorkers.put(date, dayRecordsByWorker);
    
    // �N�������[�J WorkerToDays �� TreeMap
    TreeMap<CustomDate, AttendanceDayRecord> recordsByDate = workerToDays.getOrDefault(workerId, new TreeMap<>());
    recordsByDate.put(date, dayRecord);
    workerToDays.put(workerId, recordsByDate);
  }
  
  // �R������
  public void deleteDayRecord(String workerId, CustomDate date) throws IllegalArgumentException {
    // �T�{�Ӭ����O�_�s�b
    TreeMap<CustomDate, AttendanceDayRecord> workerRecords = workerToDays.get(workerId);
    if (workerRecords == null || !workerRecords.containsKey(date)) {
      throw new IllegalArgumentException("���~: ���uUUID - " + workerId + "�b" + date.toString() + "�L�X�ʶԬ����C");
    }
    // �q��� TreeMap ��������
    AttendanceDayRecord record = workerRecords.remove(date);
    dayToWorkers.get(date).remove(workerId);
    if (workerRecords.isEmpty()) {
      workerToDays.remove(workerId);
    }
  }
  
  // �H�~��P���u UUID �j�M�X�ʶԪ��p -> �j�M�Y���u�b�Y�~�Y�몺�X�ʶԪ��p
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
    throw new IllegalArgumentException("���~: ���uUUID - " + workerId + "�b" + date.toString() + "�L�X�ʶԬ����C");
  }
  
  
  public AttendanceDayRecord searchRecordByYearMonthDay(String workerId, CustomDate date) throws IllegalArgumentException{
    if (date.getDay() == null) throw new IllegalArgumentException("���~: ���i�L����C");
    AttendanceDayRecord recordThisYearMonthDay = null;
    TreeMap<CustomDate, AttendanceDayRecord> workerRecord = workerToDays.get(workerId);
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
  
  // �R���ò������u�Ҧ�����(�R�����u) 
  public void deleteWorkerRecords(String workerId) throws IllegalArgumentException {
    if (workerId == null) throw new IllegalArgumentException("���~�G���u ID ���i���šC");
    // �q workerToDays ����
    TreeMap<CustomDate, AttendanceDayRecord> records = workerToDays.remove(workerId);
    // �q dayToWorkers ����
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