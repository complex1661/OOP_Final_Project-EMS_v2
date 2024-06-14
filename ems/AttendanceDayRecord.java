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
  
  // �s�W�X�u����
  public void addClockRecord(String workerId, ClockRecord clockRecord) {
    this.attendHours = WorkerClockInSystem.getClockHour(clockRecord);
    this.clockRecord = clockRecord;
    this.isLate = checkIsLate(workerId);
  }
  
  // �s�W�а�����
  public void addLeaveRecord(String workerId, LeaveRecord leaveRecord) {
    int leave_hours = WorkerLeaveSystem.getLeaveHours(workerId, leaveRecord);
    this.leaveHours = leave_hours;
    
    // �p�G�O���~��
    // �p�G���e�w�g�O�S��A�[�^�S���B��
    if (this.leaveRecord != null && this.leaveRecord.getLeaveType().equals("�S��")) {
      Worker w = Worker.getWorkerById(workerId);
      w.addPaidLeaveDays();
    } 
    
    this.leaveRecord = leaveRecord;
    if (leaveRecord.getIsPaidLeave()) {
      handlePaidLeave(workerId);
    }
    
    this.isLate = checkIsLate(workerId);
  }
  
  // �B�z���~��
  private void handlePaidLeave(String workerId) {
    this.attendHours = 0;
    this.absentHours = 0;
    this.isPaidLeave = true;
    this.paidLeaveHours = leaveHours;
    
    // �������u���S��
    if (leaveRecord.getLeaveType().equals("�S��")) {
      Worker w = Worker.getWorkerById(workerId);
      w.reducePaidLeaveDays();
    }
  }
  
  // �s�W�ʮu����
  public void addAbsentRecord(String workerId, AbsentRecord absentRecord) {
    this.absentHours = WorkerAbsentSystem.getAbsentHours(workerId, absentRecord);
    this.absentRecord = absentRecord;
    this.isLate = checkIsLate(workerId);
  }
  
  // �s�W�[�Z����
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
        "�X�u: %d�p��\n�ʮu: %d�p��\n�а�: %d�p��\n�[�Z: %d�p��\n���: %s\n�S��: %s",
        attendHours, absentHours, leaveHours, overtimeHours, isLate ? "�O" : "�_", isPaidLeave ? "�O" : "�_"
    );
  }
  
  // �ˬd�O�_���
  private boolean checkIsLate(String workerId) {
    Time attendTime = Worker.getWorkerById(workerId).getAttendTime();
    Time clockInTime = (clockRecord != null) ? clockRecord.getStartTime() : null;
    
    // �Y�а���ѡA������
    if (leaveRecord != null && WorkerLeaveSystem.isLeavingWholeDay(leaveRecord)) return false;
    // �Y�а����ɶ��O�W�w���ɶ��A������
    if (leaveRecord != null && clockInTime != null && isAttendTimeWithinLeavePeriod(attendTime)) {
      return false;
    }
    
    if (clockRecord != null && clockRecord.isLate(attendTime)){
      handleLateAttendance(workerId, attendTime, clockInTime);
    }
    return false;
  }
  
  // �P�_�W�Z����ɶ��O�_�b�а�����
  private boolean isAttendTimeWithinLeavePeriod(Time attendTime) {
    Time leaveStart = leaveRecord.getStartTime();
    Time leaveEnd = leaveRecord.getEndTime();
    return leaveStart != null && leaveEnd != null && 
      leaveStart.before(attendTime) && leaveEnd.after(attendTime);
  }
  
  // �B�z�çP�_�O�_���(�L�а�)
  private void handleLateAttendance(String workerId, Time attendTime, Time clockInTime) {
    // �Y�b�@�p�ɤ���F����
    int diff = clockInTime.toMinute() - attendTime.toMinute();
    if (diff <= 60) isLate = true;
    // �Y > 60 ����ʶ�
    else {
      this.absentRecord = new AbsentRecord(attendTime, new Time(clockInTime.getHour(), clockInTime.getMinute()));
      this.absentHours = WorkerAbsentSystem.getAbsentHours(workerId, absentRecord);
    }
  }
}