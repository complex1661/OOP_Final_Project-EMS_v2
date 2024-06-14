package ems;
import java.util.Date;
import java.util.TreeMap;
import java.util.ArrayList;
import java.io.*;

public class SalarySystem implements Serializable{
  private static final long serialVersionUID = 1L;
  
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
    AttendanceRecordSystem attendanceRecordSystem = ManageSystem.attendanceRecordSystem;
    
    // ���o�ӭ��u�b�Ӧ~�몺�X�ʶԪ��p
    ArrayList<AttendanceDayRecord> records = attendanceRecordSystem.searchRecordByYearMonth(worker_id, date);

    int salary = 0;
    int hourlyWage = baseSalaryEachWorkerType.get(workerType);
    
    // �p���`�@�u�@(�X��)���ɼ�
    int totalWorkedHours = 0;
    for (AttendanceDayRecord record : records) {
      totalWorkedHours += record.getAttendHours();
    } 
    
    // �S��(���~��) 
    for (AttendanceDayRecord record : records) {
      totalWorkedHours += record.getPaidLeaveHours();
    } 
    
    salary = hourlyWage * totalWorkedHours;
    
    // �[�Z
    for (AttendanceDayRecord record : records) {
      int overtimeHour = record.getOvertimeHours();
      //��1�B2�p�ɡA�[�Z�O������C�p�ɤu���B�[��1/3�H�W
      if (overtimeHour < 2) {
        salary += (int) overtimeHour * 1.34 * hourlyWage;
      }
      //��13�B14�p�ɡA�[�Z�O������C�p�ɤu���B�[��2/3�H�W
      else {
        salary += (int) overtimeHour * 1.67 * hourlyWage;
      }
    }
    
    // ���
    for (AttendanceDayRecord record : records) {
      if (record.getIsLate()) {
        salary -= LATE_PENALTY;
      }
    } 
    
    // �ʶ�
    for (AttendanceDayRecord record : records) {
      salary -= record.getAbsentHours(worker_id) * hourlyWage;
    } 
    
    return salary;
  }
}