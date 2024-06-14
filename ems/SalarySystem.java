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
    
    // 取得該員工在該年月的出缺勤狀況
    ArrayList<AttendanceDayRecord> records = attendanceRecordSystem.searchRecordByYearMonth(worker_id, date);

    int salary = 0;
    int hourlyWage = baseSalaryEachWorkerType.get(workerType);
    
    // 計算總共工作(出勤)的時數
    int totalWorkedHours = 0;
    for (AttendanceDayRecord record : records) {
      totalWorkedHours += record.getAttendHours();
    } 
    
    // 特休(有薪假) 
    for (AttendanceDayRecord record : records) {
      totalWorkedHours += record.getPaidLeaveHours();
    } 
    
    salary = hourlyWage * totalWorkedHours;
    
    // 加班
    for (AttendanceDayRecord record : records) {
      int overtimeHour = record.getOvertimeHours();
      //第1、2小時，加班費為平日每小時工資額加給1/3以上
      if (overtimeHour < 2) {
        salary += (int) overtimeHour * 1.34 * hourlyWage;
      }
      //第13、14小時，加班費為平日每小時工資額加給2/3以上
      else {
        salary += (int) overtimeHour * 1.67 * hourlyWage;
      }
    }
    
    // 遲到
    for (AttendanceDayRecord record : records) {
      if (record.getIsLate()) {
        salary -= LATE_PENALTY;
      }
    } 
    
    // 缺勤
    for (AttendanceDayRecord record : records) {
      salary -= record.getAbsentHours(worker_id) * hourlyWage;
    } 
    
    return salary;
  }
}