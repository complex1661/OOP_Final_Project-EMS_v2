package ems;
import java.util.TreeMap;
import java.io.*;

public abstract class Worker implements Serializable{
  protected static final long serialVersionUID = 1L;
  protected static TreeMap<String, Worker> workerList = new TreeMap<>();
  
  protected EWorkerType type;
  protected WorkerInfo info;
  protected Time ATTEND_TIME = new Time(8,0);
  protected int WORKING_HOURS;
  protected int paidLeaveDays;
  protected int BASE_SALARY;
  
  public abstract String printInfo();
  
  public Worker(WorkerInfo i) {
    info = i;
  }
  
  public static void addWorker(Worker w) throws IllegalArgumentException{
    String id = w.getInfo().getId();
    if (workerList.containsKey(id)) {
      throw new IllegalArgumentException("錯誤: 員工已存在。");
    }
    workerList.put(id, w);
  }
  
  public static void deleteWorker(AttendanceRecordSystem attendanceRecordSystem, String id) {
    if (!workerList.containsKey(id)) {
      throw new IllegalArgumentException("錯誤: 無此員工。");
    }
    workerList.remove(id);
    // AttendanceRecordSystem 進行紀錄的刪除
    attendanceRecordSystem.deleteWorkerRecords(id);
  }
 
  public EWorkerType getType() {
    return type;
  }
  
  public void reducePaidLeaveDays() {
    if (paidLeaveDays == 0) throw new IllegalArgumentException("錯誤: 已無特休假。");
    paidLeaveDays -= 1;
  }
  
  public WorkerInfo getInfo() {
    return info;
  }
  
  public Time getAttendTime() {
    return ATTEND_TIME;
  }

  public int getPaidLeaveDays() {
    return paidLeaveDays;
  }
  
  public static Worker getWorkerById (String id) throws IllegalArgumentException{
    if (!workerList.containsKey(id)) {
      throw new IllegalArgumentException("錯誤: 無此員工。");
    }
    return workerList.get(id);
  }
  
  public static TreeMap<String, Worker> getAllWorkers() {
    return workerList;
  }
}