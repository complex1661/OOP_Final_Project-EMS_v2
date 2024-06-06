package ems;
import java.util.UUID;
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
  
  private static TreeMap<EWorkerType, Integer> maxWorkingHours = new TreeMap<>();
  
  static{
    maxWorkingHours.put(EWorkerType.PARTTIME, PartTimeWorker.WORKING_HOURS);
    maxWorkingHours.put(EWorkerType.FULLTIME, FullTimeWorker.WORKING_HOURS);
    maxWorkingHours.put(EWorkerType.SUPERVISOR, Supervisor.WORKING_HOURS);
  }
  
  public AttendanceDayRecord (UUID uuid) {
    attendHours = maxWorkingHours.get(Worker.getWorkerByUUID(uuid).getType());
    absentHours = 0;
    leaveHours = 0;
  }
  
  public AttendanceDayRecord(UUID uuid, ClockRecord clockRecord, AbsentRecord absentRecord, LeaveRecord leaveRecord, boolean isLate, boolean isPaidLeave) {
    if (WorkerLeaveSystem.isLeavingWholeDay(uuid, leaveRecord)) {
      this.attendHours = 0;
      this.absentHours = 0;
    }
    else {
      this.attendHours = WorkerClockInSystem.getClockHours(clockRecord);
      this.absentHours = WorkerAbsentSystem.getAbsentHours(uuid, absentRecord);
    }
    this.leaveRecord = leaveRecord;
    leaveHours = WorkerLeaveSystem.getLeaveHours(uuid, leaveRecord);
    this.isLate = isLate;
    this.isPaidLeave = isPaidLeave;
  }
  
  public void addClockRecord(ClockRecord clockRecord) {
    this.attendHours = WorkerClockInSystem.getClockHours(clockRecord);
    this.clockRecord = clockRecord;
  }
  
  public void addLeaveRecord(UUID uuid, LeaveRecord leaveRecord) {
    this.leaveHours = WorkerLeaveSystem.getLeaveHours(uuid, leaveRecord);
    this.leaveRecord = leaveRecord;
  }
  
  public void addAbsentRecord(UUID uuid, AbsentRecord absentRecord) {
    this.absentHours = WorkerAbsentSystem.getAbsentHours(uuid, absentRecord);
    this.absentRecord = absentRecord;
  }

  public int getAttendHours() {
    return attendHours;
  }
  
  public int getAbsentHours(UUID uuid) {
    if (attendHours == 0 && leaveHours == 0 && absentHours == 0) {
      absentHours = maxWorkingHours.get(Worker.getWorkerByUUID(uuid).getType());
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