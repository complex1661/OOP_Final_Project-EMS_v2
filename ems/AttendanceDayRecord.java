package ems;
import java.util.UUID;

public class AttendanceDayRecord {
  private int attendHours;
  private int absentHours;
  private int leaveHours;
  private boolean isLate;
  private boolean isPaidLeave;
  private LeaveRecord leaveRecord;
  
  public AttendanceDayRecord(UUID uuid, int at, int ab, LeaveRecord leaveRecord, boolean isLate, boolean isPaidLeave) {
    attendHours = at;
    absentHours = ab;
    this.leaveRecord = leaveRecord;
    leaveHours = WorkerLeaveSystem.getLeaveHour(uuid, leaveRecord);
    if (leaveHours > 0 && at < leaveHours) leaveHours -= (at+ab);
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
    String late = isLate ? "�O" : "�_";
    String paidLeave = isPaidLeave ? "�O" : "�_";
    return "�X�u: " + attendHours + "�p��\n�ʮu: " 
      + absentHours +  "�p��\n�а�: " 
      + leaveHours + "�p��\n" 
    + "���: " + late + "\n�S��: " + paidLeave;
  }
  
}