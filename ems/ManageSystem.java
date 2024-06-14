package ems;
import java.io.*;

public class ManageSystem implements Serializable{
  private static final long serialVersionUID = 1L;
  
  public static AttendanceRecordSystem attendanceRecordSystem = new AttendanceRecordSystem();
  public static SalarySystem salarySystem = new SalarySystem();
  
  public AttendanceRecordSystem getAttendance() {
    return attendanceRecordSystem;
  } 
  
  public SalarySystem getSalary() {
    return salarySystem;
  }
} 