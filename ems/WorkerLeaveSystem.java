package ems;
import java.util.UUID;
import java.util.TreeMap;

public class WorkerLeaveSystem {
  private static TreeMap<EWorkerType, Integer> maxWorkingHours = new TreeMap<>();
  
  static{
    maxWorkingHours.put(EWorkerType.PARTTIME, PartTimeWorker.WORKING_HOURS);
    maxWorkingHours.put(EWorkerType.FULLTIME, FullTimeWorker.WORKING_HOURS);
    maxWorkingHours.put(EWorkerType.SUPERVISOR, Supervisor.WORKING_HOURS);
  }
  
  // 取得員工請假時數
  public static int getLeaveHour(UUID uuid, LeaveRecord leaveRecord) {
    if (leaveRecord.getStartTime() != null && leaveRecord.getStartTime() != null) {
      int m1 = leaveRecord.getStartTime().toMinute();
      int m2 = leaveRecord.getEndTime().toMinute();
      return Time.minuteToHour(m2 - m1);
    }
    int hours = maxWorkingHours.get(Worker.getWorkerByUUID(uuid).getType());
    return hours;
  }
}