package ems;
import java.util.TreeMap;
import java.io.*;

public class AttendanceDayRecord implements Serializable{
  private static final long serialVersionUID = 1L;
  
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
  
  // 新增出席紀錄
  public void addClockRecord(String workerId, ClockRecord clockRecord) {
    this.attendHours = WorkerClockInSystem.getClockHour(clockRecord);
    this.clockRecord = clockRecord;
    this.isLate = checkIsLate(workerId);
  }
  
  // 新增請假紀錄
  public void addLeaveRecord(String workerId, LeaveRecord leaveRecord) {
    int leave_hours = WorkerLeaveSystem.getLeaveHours(workerId, leaveRecord);
    this.leaveHours = leave_hours;
    
    // 如果是有薪假
    // 如果先前已經是特休，加回特休的額度
    if (this.leaveRecord != null && this.leaveRecord.getLeaveType().equals("特休")) {
      Worker w = Worker.getWorkerById(workerId);
      w.addPaidLeaveDays();
    } 
    
    this.leaveRecord = leaveRecord;
    if (leaveRecord.getIsPaidLeave()) {
      handlePaidLeave(workerId);
    }
    
    this.isLate = checkIsLate(workerId);
  }
  
  // 處理有薪假
  private void handlePaidLeave(String workerId) {
    this.attendHours = 0;
    this.absentHours = 0;
    this.isPaidLeave = true;
    this.paidLeaveHours = leaveHours;
    
    // 扣掉員工的特休假
    if (leaveRecord.getLeaveType().equals("特休")) {
      Worker w = Worker.getWorkerById(workerId);
      w.reducePaidLeaveDays();
    }
  }
  
  // 新增缺席紀錄
  public void addAbsentRecord(String workerId, AbsentRecord absentRecord) {
    this.absentHours = WorkerAbsentSystem.getAbsentHours(workerId, absentRecord);
    this.absentRecord = absentRecord;
    this.isLate = checkIsLate(workerId);
  }
  
  // 新增加班紀錄
  public void addOvertimeRecord(String workerId, OvertimeRecord overtimeRecord) {
    this.overtimeHours = WorkerClockInSystem.getClockHour(overtimeRecord);
    this.overtimeRecord = overtimeRecord;
  }
  
  
  // getters
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
  
  public int getOvertimeHours() {
    return overtimeHours;
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
  
  public AbsentRecord getAbsentRecord() {
    return absentRecord;
  }
  
  public OvertimeRecord getOvertimeRecord() {
    return overtimeRecord;
  }

  @Override
  public String toString() {
     return String.format(
        "出席: %d小時\n缺席: %d小時\n請假: %d小時\n加班: %d小時\n遲到: %s\n特休: %s",
        attendHours, absentHours, leaveHours, overtimeHours, isLate ? "是" : "否", isPaidLeave ? "是" : "否"
    );
  }
  
  // 檢查是否遲到
  private boolean checkIsLate(String workerId) {
    Time attendTime = Worker.getWorkerById(workerId).getAttendTime();
    Time clockInTime = (clockRecord != null) ? clockRecord.getStartTime() : null;
    
    // 若請假整天，不算遲到
    if (leaveRecord != null && WorkerLeaveSystem.isLeavingWholeDay(leaveRecord)) return false;
    // 若請假的時間是規定的時間，不算遲到
    if (leaveRecord != null && clockInTime != null && isAttendTimeWithinLeavePeriod(attendTime)) {
      return false;
    }
    
    if (clockRecord != null && clockRecord.isLate(attendTime)){
      handleLateAttendance(workerId, attendTime, clockInTime);
    }
    return false;
  }
  
  // 判斷上班應到時間是否在請假期間
  private boolean isAttendTimeWithinLeavePeriod(Time attendTime) {
    Time leaveStart = leaveRecord.getStartTime();
    Time leaveEnd = leaveRecord.getEndTime();
    return leaveStart != null && leaveEnd != null && 
      leaveStart.before(attendTime) && leaveEnd.after(attendTime);
  }
  
  // 處理並判斷是否遲到(無請假)
  private void handleLateAttendance(String workerId, Time attendTime, Time clockInTime) {
    // 若在一小時內抵達算遲到
    int diff = clockInTime.toMinute() - attendTime.toMinute();
    if (diff <= 60) isLate = true;
    // 若 > 60 分算缺勤
    else {
      this.absentRecord = new AbsentRecord(attendTime, new Time(clockInTime.getHour(), clockInTime.getMinute()));
      this.absentHours = WorkerAbsentSystem.getAbsentHours(workerId, absentRecord);
    }
  }
}