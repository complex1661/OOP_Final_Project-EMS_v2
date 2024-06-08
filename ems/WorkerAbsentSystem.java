package ems;
import java.util.TreeMap;

public class WorkerAbsentSystem {
  private static TreeMap<EWorkerType, Integer> maxWorkingHours = new TreeMap<>();
  
  static {
    maxWorkingHours.put(EWorkerType.PARTTIME, PartTimeWorker.WORKING_HOURS);
    maxWorkingHours.put(EWorkerType.FULLTIME, FullTimeWorker.WORKING_HOURS);
    maxWorkingHours.put(EWorkerType.SUPERVISOR, Supervisor.WORKING_HOURS);
  }
  
  // 取得員工缺席時數
  public static int getAbsentHours(String workerId, AbsentRecord absentRecord) {
    if (!isAbsentWholeDay(absentRecord)) {
      int m1 = absentRecord.getStartTime().toMinute();
      int m2 = absentRecord.getEndTime().toMinute();
      return Time.minuteToHour(m2 - m1);
    }
    int hours = maxWorkingHours.get(Worker.getWorkerById(workerId).getType());
    return hours;
  }
  
  public static boolean isAbsentWholeDay(AbsentRecord absentRecord) {
    if (absentRecord.getStartTime() == null) return true;
    return false;
  }
}