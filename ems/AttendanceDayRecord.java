package ems;
import java.util.UUID;

public class AttendanceDayRecord {
  private int attendHours;
  private int absentHours;
  private int leaveHours;
  private boolean isLate;
  private boolean isPaidLeave;
  private LeaveRecord leaveRecord;
  
  public AttendanceDayRecord(UUID uuid, int attendHours, int absentHours, LeaveRecord leaveRecord, boolean isLate, boolean isPaidLeave) {
    this.attendHours = attendHours;
    this.absentHours = absentHours;
    this.leaveRecord = leaveRecord;
    leaveHours = WorkerLeaveSystem.getLeaveHour(uuid, leaveRecord);
    if (leaveHours > 0 && attendHours < leaveHours) leaveHours -= (attendHours + absentHours);
    this.isLate = isLate;
    this.isPaidLeave = isPaidLeave;
  }
  
  public int getAttendHours() {
    return attendHours;
  }
  
  public int getAbsentHours() {
    return absentHours;
  } 
  
  public int getLeaveHours() {
    return leaveHours;
  }
  
  public int getTotalHours() {
    return attendHours + absentHours + leaveHours;
  }
  
  public boolean getIsLate() {
    return isLate;
  }
  
  public boolean getIsPaidLeave() {
    return isPaidLeave;
  }
  
  public LeaveRecord getLeaveRecord() {
    return leaveRecord;
  }
  
  public String toString() {
    String late = isLate ? "是" : "否";
    String paidLeave = isPaidLeave ? "是" : "否";
    return "出席: " + attendHours + "小時\n缺席: " 
      + absentHours +  "小時\n請假: " 
      + leaveHours + "小時\n" 
    + "遲到: " + late + "\n特休: " + paidLeave;
  }
  
}