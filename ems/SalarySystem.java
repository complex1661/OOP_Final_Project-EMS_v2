package ems;
import java.util.Date;
import java.util.TreeMap;
import java.util.ArrayList;

public class SalarySystem {
  private static final int LATE_PENALTY = 50;
  private TreeMap<EWorkerType, Integer> baseSalaryEachWorkerType;
  
  public SalarySystem() {
    baseSalaryEachWorkerType = new TreeMap<>();
    baseSalaryEachWorkerType.put(EWorkerType.PARTTIME, PartTimeWorker.HOURLY_WAGE);
    baseSalaryEachWorkerType.put(EWorkerType.FULLTIME, FullTimeWorker.HOURLY_WAGE);
    baseSalaryEachWorkerType.put(EWorkerType.SUPERVISOR, Supervisor.HOURLY_WAGE);
  } 
  
  public int computeMonthlySalary(String worker_id, CustomDate date) {
    EWorkerType workerType = Worker.getWorkerById(worker_id).getType();
    AttendanceRecordSystem attendanceRecordSystem = ManageSystem.getAttendanceRecordSystem();
    
    // ���o�ӭ��u�b�Ӧ~�몺�X�ʶԪ��p
    ArrayList<AttendanceDayRecord> records = attendanceRecordSystem.searchRecordByYearMonth(worker_id, date);

    int salary = 0;
    int hourly_wage = baseSalaryEachWorkerType.get(workerType);
    
    // �p���`�@�u�@(�X��)���ɼ�
    int totalWorkedHours = 0;
    for (AttendanceDayRecord record : records) {
      totalWorkedHours += record.getAttendHours();
    } 
    salary = hourly_wage * totalWorkedHours;
    
    // ���
    for (AttendanceDayRecord record : records) {
      if (record.getIsLate()) {
        salary -= LATE_PENALTY;
      }
    } 
    
    // �ʶ�
    for (AttendanceDayRecord record : records) {
      salary -= record.getAbsentHours(worker_id) * hourly_wage;
    } 
    
    return salary;
  }
}