package ems;
public class LeaveRecord {
  private String leaveType;
  private Time start;
  private Time end;
  private Message msg;
  
  public LeaveRecord() {
    start = end = new Time(0,0);
  }
  
  public LeaveRecord(String leaveType, Message txt) {
    setLeaveType(leaveType); 
    msg = txt;
    start = null;
    end = null;
  }
  
  public LeaveRecord(String leaveType, Message txt, Time start, Time end) {
    setLeaveType(leaveType); 
    this.start = start;
    this.end = end;
    msg = txt;
    if (!isValidLeaveRecord()) throw new IllegalArgumentException("���~: �����ɶ����i�H�j��_�l�ɶ��C");
  }
  
  public boolean isValidLeaveRecord(){
    if (start == null || end == null) return false;
    if (start.getHour() < end.getHour()) return true;
    if (start.getHour() == end.getHour() && start.getMinute() <= end.getMinute()) return true;
    return false;
  }
  
  public void setLeaveType(String s) throws IllegalArgumentException {
    if (s == null || s.isEmpty()) throw new IllegalArgumentException("���~: �а��������i�d�աC");
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
}