package ems;
import java.util.TreeMap;

public abstract class Worker {
  protected static TreeMap<String, Worker> workerList = new TreeMap<>();
  
  protected EWorkerType type;
  protected WorkerInfo info;
  protected Time ATTEND_TIME = new Time(8,0);
  protected int WORKING_HOURS;
  protected int PAID_LEAVE_DAYS;
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
  
  public static void deleteWorker(String id) {
    if (!workerList.containsKey(id)) {
      throw new IllegalArgumentException("���~: �L�����u�C");
    }
    workerList.remove(id);
    // AttendanceRecordSystem �]�n�i��������R��
    
    
  }
 
  public EWorkerType getType() {
    return type;
  }
  
  public WorkerInfo getInfo() {
    return info;
  }
  
  public Time getAttendTime() {
    return ATTEND_TIME;
  }

  public static Worker getWorkerById (String id) throws IllegalArgumentException{
    if (!workerList.containsKey(id)) {
      throw new IllegalArgumentException("���~: �L�����u�C");
    }
    return workerList.get(id);
  }
}