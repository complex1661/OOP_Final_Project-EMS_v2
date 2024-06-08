package ems;
public class PartTimeWorker extends Worker {
  static final int HOURLY_WAGE = 200;
  static final int WORKING_HOURS = 5;
  
  public PartTimeWorker(WorkerInfo i, Time attend_time) {
    super(i);
    type = EWorkerType.PARTTIME;
    ATTEND_TIME = attend_time;
    paidLeaveDays = 3;
  }
  
  public String printInfo() {
    return "���~: " + BASE_SALARY + "," + "�S��Ѽ�: " + paidLeaveDays + "��, ¾��: �uŪ��";
  }
}