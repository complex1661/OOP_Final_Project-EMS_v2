package ems;
public class PartTimeWorker extends Worker {
  private final int PAID_LEAVE_DAYS = 3;
  static final int HOURLY_WAGE = 200;
  static final int WORKING_HOURS = 5;
  public PartTimeWorker(WorkerInfo i) {
    super(i);
    type = EWorkerType.PARTTIME;
  }
  
  public String printInfo() {
    return "���~: " + BASE_SALARY + "," + "�S��Ѽ�: " + PAID_LEAVE_DAYS+ "��, ¾��: �uŪ��";
  }
}