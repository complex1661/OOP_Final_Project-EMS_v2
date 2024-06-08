package ems; 

public class OvertimeRecord {
  private Time start;
  private Time end;

  public OvertimeRecord() {
    start = end = new Time(0,0);
  }
  
  public OvertimeRecord(Time start, Time end) {
    this.start = start;
    this.end = end;
    isValidAbsentRecord();
  }
  
  public boolean isValidAbsentRecord(){
    if (start == null || end == null) throw new IllegalArgumentException("�[�Z���_�l�ɶ��B�����ɶ��Ҥ��i���šC");
    if (start.getHour() > 23 || end.getHour() > 23) throw new IllegalArgumentException("�[�Z���_�l�ɶ��B�����ɶ��Ҥ��i�W�L11�I�C");
    if (start.getHour() < end.getHour()) return true;
    if (start.getHour() == end.getHour() && start.getMinute() <= end.getMinute()) return true;
    return false;
  }

  public Time getStartTime() {
    return start;
  }
  
  public Time getEndTime() {
    return end;
  }

}