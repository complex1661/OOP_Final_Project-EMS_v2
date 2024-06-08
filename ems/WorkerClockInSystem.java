package ems; 
import java.util.TreeMap;

public class WorkerClockInSystem {
  private static TreeMap<EWorkerType, Integer> maxWorkingHours;
  static {
    maxWorkingHours = new TreeMap<>();
    maxWorkingHours.put(EWorkerType.PARTTIME, PartTimeWorker.WORKING_HOURS);
    maxWorkingHours.put(EWorkerType.FULLTIME, FullTimeWorker.WORKING_HOURS);
    maxWorkingHours.put(EWorkerType.SUPERVISOR, Supervisor.WORKING_HOURS);
  }
  
  public static int getClockHour(ClockRecord clockRecord) {
    int m1 = clockRecord.getStartTime().toMinute();
    int m2 = clockRecord.getEndTime().toMinute();
    return Time.minuteToHour(m2 - m1);
  }
  
  public static int getClockHour(OvertimeRecord overtimeRecord) {
    int m1 = overtimeRecord.getStartTime().toMinute();
    int m2 = overtimeRecord.getEndTime().toMinute();
    return Time.minuteToHour(m2 - m1);
  }
  
  public static int getMaxWorkingHours(String worker_id) {
    EWorkerType workerType = Worker.getWorkerById(worker_id).getType();
    int working_hours = maxWorkingHours.get(workerType);
    return working_hours;
  }
  
  public static int getMaxWorkingHours(EWorkerType worker_type) {
    int working_hours = maxWorkingHours.get(worker_type);
    return working_hours;
  }
}