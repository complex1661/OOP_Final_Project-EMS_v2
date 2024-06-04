package ems;
public class FullTimeWorker extends Worker {
  private final int PAID_LEAVE_DAYS = 5;
  static final int HOURLY_WAGE = 210;
  static final int WORKING_HOURS = 8;
  public FullTimeWorker(WorkerInfo i) {
    super(i);
    type = EWorkerType.FULLTIME;
  }
  
  public String printInfo() {
    return "底薪: " + BASE_SALARY + "," + "特休天數: " + PAID_LEAVE_DAYS+ "天, 職位: 正職員工";
  }
}