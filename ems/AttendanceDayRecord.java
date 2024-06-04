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
    String late = isLate ? "是" : "否";
    String paidLeave = isPaidLeave ? "是" : "否";
    return "出席: " + attendHours + "小時\n缺席: " 
      + absentHours +  "小時\n請假: " 
      + leaveHours + "小時\n" 
    + "遲到: " + late + "\n特休: " + paidLeave;
  }
  
}