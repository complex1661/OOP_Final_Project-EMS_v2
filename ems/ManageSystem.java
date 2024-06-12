package ems;
import java.io.*;

public class ManageSystem implements Serializable{
  private static final long serialVersionUID = 1L;
  
  private static AttendanceRecordSystem attendance = new AttendanceRecordSystem();
  private static SalarySystem salary = new SalarySystem();
 
  public AttendanceRecordSystem getAttendance() {
    return attendance;
  } 
  
  public SalarySystem getSalary() {
    return salary;
  }
} 