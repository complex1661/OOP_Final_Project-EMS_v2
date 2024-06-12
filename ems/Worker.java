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
      throw new IllegalArgumentException("���~: ���u�w�s�b�C");
    }
    workerList.put(id, w);
  }
  
  public static void deleteWorker(AttendanceRecordSystem attendanceRecordSystem, String id) {
    if (!workerList.containsKey(id)) {
      throw new IllegalArgumentException("���~: �L�����u�C");
    }
    workerList.remove(id);
    // AttendanceRecordSystem �i��������R��
    attendanceRecordSystem.deleteWorkerRecords(id);
  }
 
  public EWorkerType getType() {
    return type;
  }
  
  public void reducePaidLeaveDays() {
    if (paidLeaveDays == 0) throw new IllegalArgumentException("���~: �w�L�S�𰲡C");
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
      throw new IllegalArgumentException("���~: �L�����u�C");
    }
    return workerList.get(id);
  }
  
  public static TreeMap<String, Worker> getAllWorkers() {
    return workerList;
  }
}