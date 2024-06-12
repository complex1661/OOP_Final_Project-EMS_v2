package ems;
public class ManageSystem {
  private static AttendanceRecordSystem attendanceRecordSystem  = new AttendanceRecordSystem();
  private static SalarySystem salarySystem = new SalarySystem();
 
  public AttendanceRecordSystem getAttendanceRecordSystem() {
    return attendanceRecordSystem;
  } 
  
  public SalarySystem getSalarySystem() {
    return salarySystem;
  }
} 