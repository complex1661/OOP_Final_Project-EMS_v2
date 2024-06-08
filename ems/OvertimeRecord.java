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
    isValidOvertimeRecord();
  }
  
  public boolean isValidOvertimeRecord(){
    if (start == null || end == null) throw new IllegalArgumentException("加班的起始時間、結束時間皆不可為空。");
    if (start.getHour() > 23 || end.getHour() > 23 || (start.getHour() == 23 && start.getMinute() > 0) ||  (end.getHour() == 23 && end.getMinute() > 0)) throw new IllegalArgumentException("加班的起始時間、結束時間皆不可超過11點。");
    if (end.toMinute() - start.toMinute() > 240)  throw new IllegalArgumentException("加班時間不可超過4小時。");
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