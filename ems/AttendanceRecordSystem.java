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
  
  // �غc��
  public AttendanceRecordSystem() {
    DayToWorkers = new TreeMap<>();
    WorkerToDays = new TreeMap<>();
    maxWorkingHours = new TreeMap<>();
    maxWorkingHours.put(EWorkerType.PARTTIME, PartTimeWorker.WORKING_HOURS);
    maxWorkingHours.put(EWorkerType.FULLTIME, FullTimeWorker.WORKING_HOURS);
    maxWorkingHours.put(EWorkerType.SUPERVISOR, Supervisor.WORKING_HOURS);
  }

  // �s�W����
  public void addDayRecord(UUID worker_uuid, CustomDate date, AttendanceDayRecord dayRecord) throws IllegalArgumentException {
    // ���o�ӭ��u�C��u�ɤW���P UUID
    EWorkerType workerType = Worker.getWorkerByUUID(worker_uuid).getType();
    int working_hours = maxWorkingHours.get(workerType);
    
    // �ֹ� DayRecord �ɼƬO�_�X�z
    if (dayRecord.getTotalHours() != working_hours){
      throw new IllegalArgumentException("���~: ���X�z���X�ʶԬ����C"); 
    }
    
    // �ˬd�ӭ��u�O�_�b�Ӥ���w�s�b�X�ʶԬ���
    TreeMap<CustomDate, AttendanceDayRecord> workerAttendance = WorkerToDays.getOrDefault(worker_uuid, new TreeMap<>());
    if (workerAttendance.containsKey(date)) {
      throw new IllegalArgumentException("���~: ���uUUID - " + worker_uuid.toString() + "�b" + date.toString() + "�w���X�ʶԬ����C");
    }
    
    // �N�������[�J DayToWorkers �� TreeMap
    TreeMap<UUID, AttendanceDayRecord> dayRecordsByWorker = DayToWorkers.getOrDefault(date, new TreeMap<>());
    dayRecordsByWorker.put(worker_uuid, dayRecord);
    DayToWorkers.put(date, dayRecordsByWorker);
    
    // �N�������[�J WorkerToDays �� TreeMap
    TreeMap<CustomDate, AttendanceDayRecord> recordsByDate = WorkerToDays.getOrDefault(worker_uuid, new TreeMap<>());
    recordsByDate.put(date, dayRecord);
    WorkerToDays.put(worker_uuid, recordsByDate);
  }
  
  // �R������
  public void deleteDayRecord(UUID worker_uuid, CustomDate date) throws IllegalArgumentException {
    // �T�{�Ӭ����O�_�s�b
    TreeMap<CustomDate, AttendanceDayRecord> workerRecords = WorkerToDays.get(worker_uuid);
    if (workerRecords == null || !workerRecords.containsKey(date)) {
      throw new IllegalArgumentException("���~: ���uUUID - " + worker_uuid.toString() + "�b" + date.toString() + "�L�X�ʶԬ����C");
    }
    // �q��� TreeMap ��������
    AttendanceDayRecord record = workerRecords.remove(date);
    DayToWorkers.get(date).remove(worker_uuid);
    if (workerRecords.isEmpty()) {
      WorkerToDays.remove(worker_uuid);
    }
  }
  
  // �H�~��P���u UUID �j�M�X�ʶԪ��p -> �j�M�Y���u�b�Y�~�Y�몺�X�ʶԪ��p
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
    throw new IllegalArgumentException("���~: ���uUUID - " + worker_uuid.toString() + "�b" + date.toString() + "�L�X�ʶԬ����C");
  }
  
  // �H�~���j�M�Ҧ����u���X�ʶԪ��p
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