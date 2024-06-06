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
  
  public AbsentRecord(Message txt, Time start, Time end) {
    this.start = start;
    this.end = end;
    msg = txt;
    if (!isValidAbsentRecord()) throw new IllegalArgumentException("錯誤: 結束時間不可以大於起始時間。");
  }
  
  public boolean isValidAbsentRecord(){
    if (start == null || end == null) return false;
    if (start.getHour() < end.getHour()) return true;
    if (start.getHour() == end.getHour() && start.getMinute() <= end.getMinute()) return true;
    return false;
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
