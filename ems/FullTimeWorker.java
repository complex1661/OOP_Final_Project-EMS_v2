package ems;
public class FullTimeWorker extends Worker {
  private final Time ATTEND_TIME = new Time(8,0);
  static final int HOURLY_WAGE = 210;
  static final int WORKING_HOURS = 9;
  
  public FullTimeWorker(WorkerInfo i) {
    super(i);
    type = EWorkerType.FULLTIME;
    paidLeaveDays = 5;
  }
  
  public String printInfo() {
    return "���~: " + BASE_SALARY + "," + "�S��Ѽ�: " + paidLeaveDays + "��, ¾��: ��¾���u";
  }
}