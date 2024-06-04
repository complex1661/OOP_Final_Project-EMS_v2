package ems; 
public class WorkerClockInSystem {
  public static int getClockHour(ClockRecord clockRecord) {
    int m1 = clockRecord.getStartTime().toMinute();
    int m2 = clockRecord.getEndTime().toMinute();
    return Time.minuteToHour(m2 - m1);
  }
}