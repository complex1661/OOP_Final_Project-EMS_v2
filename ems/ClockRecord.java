package ems;
public class ClockRecord {
  private Time start;
  private Time end;
  
  public ClockRecord(Time s, Time e) {
    start = s; 
    end = e;
    if (!isValidClockRecord()) throw new IllegalArgumentException("錯誤: 上班時間不可以大於下班時間。");
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