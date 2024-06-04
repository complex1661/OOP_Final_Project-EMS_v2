package ems;
import java.util.TreeMap;
import java.util.UUID;
public abstract class Worker {
  protected static TreeMap<UUID, Worker> workerList = new TreeMap<>();
  
  protected EWorkerType type;
  protected WorkerInfo info;
  protected int WORKING_HOURS;
  protected int PAID_LEAVE_DAYS;
  protected int BASE_SALARY;
  
  public abstract String printInfo();
  
  public Worker(WorkerInfo i) {
    info = i;
  }
  
  public static void addWorker(Worker w) throws IllegalArgumentException{
    UUID uuid = w.getInfo().getUUID();
    if (workerList.containsKey(uuid)) {
      throw new IllegalArgumentException("錯誤: 員工已存在。");
    }
    workerList.put(uuid, w);
  }
  
  public static void deleteWorker(UUID uuid) {
    if (!workerList.containsKey(uuid)) {
      throw new IllegalArgumentException("錯誤: 無此員工。");
    }
    workerList.remove(uuid);
    // AttendanceRecordSystem 也要進行紀錄的刪除
  }
 
  public EWorkerType getType() {
    return type;
  }
  
  public WorkerInfo getInfo() {
    return info;
  }
  
  public static Worker getWorkerByUUID (UUID uuid) throws IllegalArgumentException{
    if (!workerList.containsKey(uuid)) {
      throw new IllegalArgumentException("錯誤: 無此員工。");
    }
    return workerList.get(uuid);
  }
}