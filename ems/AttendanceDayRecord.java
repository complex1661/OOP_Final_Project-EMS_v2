package ems;
import java.util.TreeMap;

public class AttendanceDayRecord {
  private int attendHours;
  private int absentHours;
  private int leaveHours;
  private int paidLeaveHours;
  private int overtimeHours;
  
  private boolean isLate;
  private boolean isPaidLeave;
  
  private ClockRecord clockRecord;
  private LeaveRecord leaveRecord;
  private AbsentRecord absentRecord;
  private OvertimeRecord overtimeRecord;
  
  public AttendanceDayRecord (String workerId) {}
  
  public void addClockRecord(String workerId, ClockRecord clockRecord) {
    this.attendHours = WorkerClockInSystem.getClockHour(clockRecord);
    this.clockRecord = clockRecord;
    this.isLate = checkIsLate(workerId);
  }
  
  public void addLeaveRecord(String workerId, LeaveRecord leaveRecord) {
    int leave_hours = WorkerLeaveSystem.getLeaveHours(workerId, leaveRecord);
    this.leaveHours = leave_hours;
    this.leaveRecord = leaveRecord;
    
    // 如果是特休假
    if (leaveRecord.getIsPaidLeave()) {
      this.attendHours = 0;
      this.absentHours = 0;
      isPaidLeave = true;
      this.paidLeaveHours = leave_hours;
    }
    
    this.isLate = checkIsLate(workerId);
  }
  
  public void addAbsentRecord(String workerId, AbsentRecord absentRecord) {
    this.absentHours = WorkerAbsentSystem.getAbsentHours(workerId, absentRecord);
    this.absentRecord = absentRecord;
    this.isLate = checkIsLate(workerId);
  }
  
  public void addOvertimeRecord(String workerId, OvertimeRecord overtimeRecord) {
    this.overtimeHours = WorkerClockInSystem.getClockHour(overtimeRecord);
    this.overtimeRecord = overtimeRecord;
  }
  
  public int getAttendHours() {
    return attendHours;
  }
  
  public int getAbsentHours(String workerId) {
    if (attendHours == 0 && leaveHours == 0 && absentHours == 0) {
      absentHours = WorkerClockInSystem.getMaxWorkingHours(workerId);
    }
    return absentHours;
  } 

  public int getLeaveHours() {
    return leaveHours;
  }

  public int getPaidLeaveHours() {
    return paidLeaveHours;
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
  
  public ClockRecord getClockRecord() {
    return clockRecord;
  }
  
  public LeaveRecord getLeaveRecord() {
    return leaveRecord;
  }

  public String toString() {
    String late = isLate ? "是" : "否";
    String paidLeave = isPaidLeave ? "是" : "否";
    return "出席: " + attendHours + "小時\n缺席: " 
      + absentHours +  "小時\n請假: " 
      + leaveHours + "小時\n加班: " 
      + overtimeHours + "小時\n"
      + "遲到: " + late + "\n特休: " + paidLeave;
  }
  
  private boolean checkIsLate(String workerId) {
    // 應該要到的時間
    Time attend_time = Worker.getWorkerById(workerId).getAttendTime();
    Time clock_in_time = (clockRecord != null ? clockRecord.getStartTime() : null);

    // 若請假整天，不算遲到
    if (leaveRecord != null && WorkerLeaveSystem.isLeavingWholeDay(leaveRecord)) return false;
    // 若請假的時間是規定的時間，不算遲到
    if (leaveRecord != null && clock_in_time != null) {
        Time leave_start = leaveRecord.getStartTime();
        Time leave_end = leaveRecord.getEndTime();
        if (leave_start != null && leave_end != null && 
            leave_start.before(attend_time) && leave_end.after(attend_time)) {
            return false;
        }
    }
    
    if (clockRecord != null && clockRecord.isLate(attend_time)){
      // 若在一小時內抵達算遲到，否則就算缺勤
      int diff = clock_in_time.toMinute() - attend_time.toMinute();
      if (diff <= 60) return true;
      // else if > 60
      else {
        this.absentRecord = new AbsentRecord(attend_time, new Time(clock_in_time.getHour(), clock_in_time.getMinute()));
        this.absentHours = WorkerAbsentSystem.getAbsentHours(workerId, absentRecord);
        return true;
      }
    }
    return false;
  }
  
}