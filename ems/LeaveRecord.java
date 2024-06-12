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
    paidLeaveTypes.add("�S��");
    paidLeaveTypes.add("�B��");
    paidLeaveTypes.add("�ల");
    paidLeaveTypes.add("���˯f��");
    paidLeaveTypes.add("����");
    paidLeaveTypes.add("���˰�");
    paidLeaveTypes.add("�����ˤγ�����");
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
    if (!isValidLeaveRecord()) throw new IllegalArgumentException("���~: �����ɶ����i�H�j��_�l�ɶ��C");
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
  
  public boolean getIsPaidLeave() {
    return isPaidLeave;
  }
}