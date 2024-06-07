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
  
  private static TreeMap<EWorkerType, Integer> maxWorkingHours = new TreeMap<>();
  
  static{
    maxWorkingHours.put(EWorkerType.PARTTIME, PartTimeWorker.WORKING_HOURS);
    maxWorkingHours.put(EWorkerType.FULLTIME, FullTimeWorker.WORKING_HOURS);
    maxWorkingHours.put(EWorkerType.SUPERVISOR, Supervisor.WORKING_HOURS);
  }
  
  public AttendanceDayRecord (String worker_id) {
    attendHours = maxWorkingHours.get(Worker.getWorkerById(worker_id).getType());
    absentHours = 0;
    leaveHours = 0;
  }
  
  public AttendanceDayRecord(String worker_id, ClockRecord clockRecord, int absentHours, LeaveRecord leaveRecord, boolean isLate, boolean isPaidLeave) {
    if (WorkerLeaveSystem.isLeavingWholeDay(leaveRecord)) {
      this.attendHours = 0;
      this.absentHours = 0;
    }
    else {
      this.attendHours = WorkerClockInSystem.getClockHour(clockRecord);
      this.absentHours = absentHours;
    }
    this.leaveRecord = leaveRecord;
    leaveHours = WorkerLeaveSystem.getLeaveHours(worker_id, leaveRecord);
    this.isLate = isLate;
    this.isPaidLeave = isPaidLeave;
  }
  
  public void addClockRecord(ClockRecord clockRecord) {
    this.attendHours = WorkerClockInSystem.getClockHour(clockRecord);
  }
  
  public void addLeaveRecord(String worker_id, LeaveRecord leaveRecord) {
    this.leaveHours = WorkerLeaveSystem.getLeaveHours(worker_id, leaveRecord);
  }

  public int getAttendHours() {
    return attendHours;
  }
  
  public int getAbsentHours(String worker_id) {
    if (attendHours == 0 && leaveHours == 0 && absentHours == 0) {
      absentHours = maxWorkingHours.get(Worker.getWorkerById(worker_id).getType());
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
  
}