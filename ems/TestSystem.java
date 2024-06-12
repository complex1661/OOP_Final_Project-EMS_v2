package ems;
import java.util.*;

public class TestSystem {
  public static void main (String[] args) {
    
    ManageSystem system = new ManageSystem();
    SaveWorker workerSaver = new SaveWorker();
    LoadWorker workerLoader = new LoadWorker();
   
    // �Ĥ@�ӭ��u
    Worker w = new PartTimeWorker(new WorkerInfo("John", "�~�J�u", new CustomDate(2020,5,20)), new Time(8,0));
    Worker.addWorker(w);
    String id = w.getInfo().getId();
    system.getAttendance().addAttendanceRecord(id, new CustomDate(2023, 5, 17), new ClockRecord(new Time(9,1), new Time(17, 0)));
    system.getAttendance().addLeaveRecord(id, new CustomDate(2023, 5, 18), new LeaveRecord("�S��", new Message()));
    System.out.println(id.toString() + " �~�ꬰ " + system.getSalary().computeMonthlySalary(system, id, new CustomDate(2023, 5)));
    //�x�s���u���
    workerSaver.saveFileTo(w);
    
    // �ĤG�ӭ��u - (�ϥΥ��d�����@���X�Ԭ���)
    Worker w2 = new FullTimeWorker(new WorkerInfo("Sarah", "�]�ȳ��|�p�v", new CustomDate(2019,6,30)));
    Worker.addWorker(w2);
    id = w2.getInfo().getId();
    system.getAttendance().addAttendanceRecord(id, new CustomDate(2023, 5, 16), new ClockRecord(new Time(9,0), new Time(17, 0)));
    system.getAttendance().addLeaveRecord(id, new CustomDate(2023, 5, 16), new LeaveRecord("�ư�", new Message(), new Time(8,0), new Time(9, 0)));
    system.getAttendance().addAttendanceRecord(id, new CustomDate(2023, 5, 17), new ClockRecord(new Time(8,0), new Time(17, 0)));
    system.getAttendance().addAttendanceRecord(id, new CustomDate(2023, 5, 18), new ClockRecord(new Time(8,0), new Time(17, 0)));
    system.getAttendance().addOvertimeRecord(id, new CustomDate(2023, 5, 18), new OvertimeRecord(new Time(18,0), new Time(18,50)));
    //�x�s���u���
    workerSaver.saveFileTo(w2);
    
    Worker w3 = new FullTimeWorker(new WorkerInfo("Candy", "�~��", new CustomDate(2020,8,5)));
    Worker.addWorker(w3);
    workerSaver.saveFileTo(w3);
    
    //�x�s�t�θ��
    SaveManageSystem systemSaver = new SaveManageSystem();
    systemSaver.saveFileTo(system);
    
    //Ū���t�θ��
    LoadManageSystem systemLoader = new LoadManageSystem();
    system = systemLoader.loadSystem();
    
    // ���o���u�b�S�w�~�몺�~��
    System.out.println(id.toString() + " �~�ꬰ " + system.getSalary().computeMonthlySalary(system, id, new CustomDate(2023, 5)));
   
    // ���o�b�S�w�~�몺�Ҧ����u���X�Ԭ���
    CustomDate specificDate = new CustomDate(2023,5,18);
    TreeMap<String, AttendanceDayRecord> attendanceRecordAtSpecificYearMonth = system.getAttendance().searchAllWorkersRecordsByYearMonthDay(specificDate);
    System.out.println("�b " + specificDate.toString() + "�A���u���X�ʶԬ�����: ");
    for (Map.Entry<String, AttendanceDayRecord> worker : attendanceRecordAtSpecificYearMonth.entrySet()) {
      System.out.println("ID: " + worker.getKey() + "�A�m�W: " + Worker.getWorkerById(worker.getKey()).getInfo().getName() + ": \n" + worker.getValue().toString());
    }
    
    // ���o�b�S�w�~�몺���u�X�Ԭ������а���T
    specificDate = new CustomDate(2023,5,16);
    AttendanceDayRecord record = system.getAttendance().searchRecordByYearMonthDay(id, specificDate);
    System.out.println(record.getLeaveRecord().getLeaveType());

    
    // ���եͦ����
    System.out.println(new CustomDate().toString());
    System.out.println(Worker.getWorkerById("0000001").getPaidLeaveDays());
  }
}