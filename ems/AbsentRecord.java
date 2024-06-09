package ems;

public class AbsentRecord {
  private Time start;
  private Time end;
  private Message msg;
  
  public AbsentRecord() {
    start = end = new Time(0,0);
  }
  
  public AbsentRecord(Message txt) {
    msg = txt;
    start = null;
    end = null;
  }
  
  public AbsentRecord(Time start, Time end) {
    this.start = start;
    this.end = end;
    isValidAbsentRecord();
  }
  
  public AbsentRecord(Message txt, Time start, Time end) {
    this.start = start;
    this.end = end;
    msg = txt;
    isValidAbsentRecord();
  }
  
  public void isValidAbsentRecord() throws IllegalArgumentException{
    if (start == null || end == null) throw new IllegalArgumentException("���~: �_�l�B�����ɶ����i���šC");
    if (start.getHour() > end.getHour()) throw new IllegalArgumentException("���~: �����ɶ����i�H�j��_�l�ɶ��C");
    if (start.getHour() == end.getHour() && start.getMinute() > end.getMinute()) throw new IllegalArgumentException("���~: �����ɶ����i�H�j��_�l�ɶ��C");
  }
  
  public Message getAbsentDetail() {
    return msg;
  }
  
  public Time getStartTime() {
    return start;
  }
  
  public Time getEndTime() {
    return end;
  }
}
