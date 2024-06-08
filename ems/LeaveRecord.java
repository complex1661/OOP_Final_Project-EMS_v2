package ems;
public class LeaveRecord {
  private String leaveType;
  private boolean isPaidLeave = false;
  private Time start;
  private Time end;
  private Message msg;
  
  public LeaveRecord() {
    start = end = new Time(0,0);
  }
  
  public LeaveRecord(String leaveType, Message txt, boolean isPaidLeave) {
    setLeaveType(leaveType); 
    msg = txt;
    this.isPaidLeave = isPaidLeave;
    start = null;
    end = null;
  }
  
  public LeaveRecord(String leaveType, Message txt, Time start, Time end) {
    setLeaveType(leaveType); 
    this.start = start;
    this.end = end;
    this.isPaidLeave = false;
    msg = txt;
    if (!isValidLeaveRecord()) throw new IllegalArgumentException("錯誤: 結束時間不可以大於起始時間。");
  }
  
  public boolean isValidLeaveRecord(){
    if (start == null || end == null) return false;
    if (start.getHour() < end.getHour()) return true;
    if (start.getHour() == end.getHour() && start.getMinute() <= end.getMinute()) return true;
    return false;
  }
  
  public void setLeaveType(String s) throws IllegalArgumentException {
    if (s == null || s.isEmpty()) throw new IllegalArgumentException("錯誤: 請假種類不可留白。");
    leaveType = s;
  }
  
  public String getLeaveType() {
    return leaveType;
  }
  
  public Message getLeaveDetail() {
    return msg;
  }
  
  public Time getStartTime() {
    return start;
  }
  
  public Time getEndTime() {
    return end;
  }
  
  public boolean getIsPaidLeave() {
    return isPaidLeave;
  }
}