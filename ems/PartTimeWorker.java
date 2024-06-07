package ems;
public class PartTimeWorker extends Worker {
  private final int PAID_LEAVE_DAYS = 3;
  static final int HOURLY_WAGE = 200;
  static final int WORKING_HOURS = 5;
  
  public PartTimeWorker(WorkerInfo i, Time attend_time) {
    super(i);
    type = EWorkerType.PARTTIME;
    ATTEND_TIME = attend_time;
  }
  
  public String printInfo() {
    return "時薪: " + BASE_SALARY + "," + "特休天數: " + PAID_LEAVE_DAYS+ "天, 職位: 工讀生";
  }
}