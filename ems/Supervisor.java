package ems;
public class Supervisor extends Worker {
  private final int PAID_LEAVE_DAYS = 7;
  private final Time ATTEND_TIME = new Time(9,0);
  static final int HOURLY_WAGE = 350;
  static final int WORKING_HOURS = 10;
  public Supervisor(WorkerInfo i) {
    super(i);
    type = EWorkerType.SUPERVISOR;
  }
  
  public String printInfo() {
    return "���~: " + BASE_SALARY + "," + "�S��Ѽ�: " + PAID_LEAVE_DAYS+ "��, ¾��: �D��";
  }
}