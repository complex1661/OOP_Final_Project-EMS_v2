package ems;
import java.util.TreeMap;

public class AttendanceDayRecord {
  private int attendHours;
  private int absentHours;
  private int leaveHours;
  private boolean isLate;
  private boolean isPaidLeave;
  
  private ClockRecord clockRecord;
  private LeaveRecord leaveRecord;
  private AbsentRecord absentRecord;
  
  public AttendanceDayRecord (String worker_id) {
    attendHours = WorkerClockInSystem.getMaxWorkingHours(worker_id);
    absentHours = 0;
    leaveHours = 0;
  }
  
  public void addClockRecord(String worker_id, ClockRecord clockRecord) {
    this.attendHours = WorkerClockInSystem.getClockHour(clockRecord);
    this.clockRecord = clockRecord;
    this.isLate = isLate(worker_id);
  }
  
  public void addLeaveRecord(String worker_id, LeaveRecord leaveRecord) {
    this.leaveHours = WorkerLeaveSystem.getLeaveHours(worker_id, leaveRecord);
    this.leaveRecord = leaveRecord;
    this.isLate = isLate(worker_id);
  }
  
  public void addAbsentRecord(String worker_id, AbsentRecord absentRecord) {
    this.absentHours = WorkerAbsentSystem.getAbsentHours(worker_id, absentRecord);
    this.absentRecord = absentRecord;
    this.isLate = isLate(worker_id);
  }

  public int getAttendHours() {
    return attendHours;
  }
  
  public int getAbsentHours(String worker_id) {
    if (attendHours == 0 && leaveHours == 0 && absentHours == 0) {
      absentHours = WorkerClockInSystem.getMaxWorkingHours(worker_id);
    }
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
      + leaveHours + "小時\n" 
    + "遲到: " + late + "\n特休: " + paidLeave;
  }
  
  private boolean isLate(String worker_id) {
    // 應該要到的時間
    Time attend_time = Worker.getWorkerById(worker_id).getAttendTime();
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
        this.absentHours = WorkerAbsentSystem.getAbsentHours(worker_id, absentRecord);
        return true;
      }
    }
    return false;
  }
  
}