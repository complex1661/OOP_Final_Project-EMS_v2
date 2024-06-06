package ems;
public class ClockRecord {
  private Time start;
  private Time end;
  
  public ClockRecord () {
    start = end = new Time(0,0);
  }
  
  public ClockRecord(Time s, Time e) {
    start = s; 
    end = e;
    if (!isValidClockRecord()) throw new IllegalArgumentException("���~: �W�Z�ɶ����i�H�j��U�Z�ɶ��C");
  }
  
  public boolean isValidClockRecord(){
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