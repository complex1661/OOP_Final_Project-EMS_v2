package ems;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
public class LeaveRecord implements Serializable{
  private static final long serialVersionUID = 1L;
  
  private String leaveType;
  private boolean isPaidLeave = false;
  private Time start;
  private Time end;
  private Message msg;
  
  
  private static Set<String> paidLeaveTypes = new HashSet<>();
  
  static {
    paidLeaveTypes.add("特休");
    paidLeaveTypes.add("婚假");
    paidLeaveTypes.add("喪假");
    paidLeaveTypes.add("公傷病假");
    paidLeaveTypes.add("公假");
    paidLeaveTypes.add("產檢假");
    paidLeaveTypes.add("陪產檢及陪產假");
  }
  
  public LeaveRecord() {
    start = end = new Time(0,0);
  }
  
  public LeaveRecord(String leaveType, Message txt) {
    setLeaveType(leaveType); 
    msg = txt;
    checkIsPaidLeave();
    start = null;
    end = null;
  }
  
  public LeaveRecord(String leaveType, Message txt, Time start, Time end) {
    setLeaveType(leaveType); 
    this.start = start;
    this.end = end;
    checkIsPaidLeave();
    msg = txt;
    if (!isValidLeaveRecord()) throw new IllegalArgumentException("錯誤: 結束時間不可以大於起始時間。");
  }
  
  public boolean isValidLeaveRecord(){
    if (start == null || end == null) return false;
    if (start.getHour() < end.getHour()) return true;
    if (start.getHour() == end.getHour() && start.getMinute() <= end.getMinute()) return true;
    return false;
  }
  
  private void checkIsPaidLeave() {
    if (paidLeaveTypes.contains(leaveType)) {
      this.isPaidLeave = true;
    }
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