package ems;
import java.util.TreeMap;

public class WorkerLeaveSystem {
  // 取得員工請假時數
  public static int getLeaveHours(String workerId, LeaveRecord leaveRecord) {
    if (!isLeavingWholeDay(leaveRecord)) {
      int m1 = leaveRecord.getStartTime().toMinute();
      int m2 = leaveRecord.getEndTime().toMinute();
      return Time.minuteToHour(m2 - m1);
    }
    int hours = WorkerClockInSystem.getMaxWorkingHours(workerId);
    return hours;
  }
  
  public static boolean isLeavingWholeDay(LeaveRecord leaveRecord) {
    if (leaveRecord.getStartTime() == null) return true;
    return false;
  }
}