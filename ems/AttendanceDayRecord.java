package ems;
public class AttendanceDayRecord {
  private int attendHours;
  private int absentHours;
  private int leaveHours;
  private boolean isLate;
  private boolean isPaidLeave;
  
  public AttendanceDayRecord(int at, int ab, int l, boolean isLate, boolean isPaidLeave) {
    attendHours = at;
    absentHours = ab;
    leaveHours = l;
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
  
  public String toString() {
    String late = isLate ? "�O" : "�_";
    String paidLeave = isPaidLeave ? "�O" : "�_";
    return "�X�u: " + attendHours + "�p��\n�ʮu: " 
      + absentHours +  "�p��\n�а�: " 
      + leaveHours + "�p��\n" 
    + "���: " + late + "\n�S��: " + paidLeave;
  }
  
}