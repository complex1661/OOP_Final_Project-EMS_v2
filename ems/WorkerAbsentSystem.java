package ems;
import java.util.UUID;
import java.util.TreeMap;

public class WorkerAbsentSystem {
  private static TreeMap<EWorkerType, Integer> maxWorkingHours = new TreeMap<>();
  
  static {
    maxWorkingHours.put(EWorkerType.PARTTIME, PartTimeWorker.WORKING_HOURS);
    maxWorkingHours.put(EWorkerType.FULLTIME, FullTimeWorker.WORKING_HOURS);
    maxWorkingHours.put(EWorkerType.SUPERVISOR, Supervisor.WORKING_HOURS);
  }
  
  // 取得員工缺席時數
  public static int getAbsentHours(UUID uuid, AbsentRecord absentRecord) {
    if (!isAbsentWholeDay(uuid, absentRecord)) {
      int m1 = absentRecord.getStartTime().toMinute();
      int m2 = absentRecord.getEndTime().toMinute();
      return Time.minuteToHour(m2 - m1);
    }
    int hours = maxWorkingHours.get(Worker.getWorkerByUUID(uuid).getType());
    return hours;
  }
  
  public static boolean isAbsentWholeDay(UUID uuid, AbsentRecord absentRecord) {
    if (absentRecord.getStartTime() == null) return true;
    return false;
  }
}