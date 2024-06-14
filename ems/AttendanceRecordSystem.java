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
  
  // �غc��
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
  
  // ���o�ΰ��X��ѥX�ʶԬ���
  private AttendanceDayRecord getOrCreateAttendanceDayRecord(String workerId, CustomDate date) {
    TreeMap<CustomDate, AttendanceDayRecord> workerAttendance = workerToDays.getOrDefault(workerId, new TreeMap<>());
    return workerAttendance.getOrDefault(date, new AttendanceDayRecord());
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
    // Check if record already exists
    if (workerToDays.getOrDefault(workerId, new TreeMap<>()).containsKey(date)) {
      throw new IllegalArgumentException("���~: ���uUUID - " + workerId + "�b" + date.toString() + "�w���X�ʶԬ����C");
    }
    
    // Verify working hours limit
    int workingHours = WorkerClockInSystem.getMaxWorkingHours(Worker.getWorkerById(workerId).getType());
    if (dayRecord.getTotalHours() > workingHours) {
      throw new IllegalArgumentException("���~: ���X�z���X�ʶԬ����C");
    }
    updateRecords(workerId, date, dayRecord);
  }
  
  // �R������
  public void deleteDayRecord(String workerId, CustomDate date) throws IllegalArgumentException {
    // �T�{�Ӭ����O�_�s�b
    TreeMap<CustomDate, AttendanceDayRecord> workerRecords = workerToDays.get(workerId);
    if (workerRecords == null || !workerRecords.containsKey(date)) {
      throw new IllegalArgumentException("���~: ���uUUID - " + workerId + "�b" + date.toString() + "�L�X�ʶԬ����C");
    }
    
    // �p�G�t���S��A�N�ƶq�[�^
    AttendanceDayRecord dayRecord = workerRecords.get(date);
    LeaveRecord leaveRecord = dayRecord.getLeaveRecord();
    if (leaveRecord.getLeaveType().equals("�S��")) {
      Worker w = Worker.getWorkerById(workerId);
      w.addPaidLeaveDays();
    }
    
    // �q��� TreeMap ��������
    workerRecords.remove(date);
    if (workerRecords.isEmpty()) workerToDays.remove(workerId);

    TreeMap<String, AttendanceDayRecord> dayRecords = dayToWorkers.get(date);
    if (dayRecords != null) {
      dayRecords.remove(workerId);
      if (dayRecords.isEmpty()) dayToWorkers.remove(date);
    }
  }
  
  // �H�~��P���u UUID �j�M�X�ʶԪ��p -> �j�M�Y���u�b�Y�~�Y�몺�X�ʶԪ��p
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
      throw new IllegalArgumentException("���~: ���uUUID - " + workerId + "�b" + date.toString() + "�L�X�ʶԬ����C");
    }
    
    return records;
  }
  
  
  public AttendanceDayRecord searchRecordByYearMonthDay(String workerId, CustomDate date) throws IllegalArgumentException{
    if (date.getDay() == null) throw new IllegalArgumentException("���~: ���i�L����C");
    
    TreeMap<CustomDate, AttendanceDayRecord> workerRecords = workerToDays.get(workerId);
    if (workerRecords != null) {
      AttendanceDayRecord record = workerRecords.get(date);
      if (record != null) return record;
    }
    
    throw new IllegalArgumentException("���~: ������ӭ��u�L�X�ʶԬ����C");
  }
  
  
  
  // �H�~���j�M�Ҧ����u���X�ʶԪ��p
  public TreeMap<String, AttendanceDayRecord> searchAllWorkersRecordsByYearMonthDay(CustomDate date){
    
   TreeMap<String, AttendanceDayRecord> allWorkersRecords = new TreeMap<>();
    TreeMap<String, AttendanceDayRecord> dayRecords = dayToWorkers.get(date);
    
    if (dayRecords != null) {
      allWorkersRecords.putAll(dayRecords);
    } else {
      throw new IllegalArgumentException("���~: ������Ҧ����u���L�X�ʶԬ����C");
    }
    
    return allWorkersRecords;
  }
  
  // �R���ò������u�Ҧ�����(�R�����u) 
  public void deleteWorkerRecords(String workerId) throws IllegalArgumentException {
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