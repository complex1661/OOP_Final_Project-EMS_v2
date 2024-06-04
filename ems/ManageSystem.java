package ems;
public class ManageSystem {
  private static AttendanceRecordSystem attendanceRecordSystem  = new AttendanceRecordSystem();
  private static SalarySystem salarySystem = new SalarySystem();
 
  public static AttendanceRecordSystem getAttendanceRecordSystem() {
    return attendanceRecordSystem;
  } 
  
  public static SalarySystem getSalarySystem() {
    return salarySystem;
  }
} 