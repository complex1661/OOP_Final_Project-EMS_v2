package ems;
import java.util.*;

public class TestSystem {
  public static void main (String[] args) { 
    AttendanceRecordSystem attendanceRecordSystem = ManageSystem.getAttendanceRecordSystem();
    SalarySystem salarySystem = ManageSystem.getSalarySystem();
   
    // �Ĥ@�ӭ��u
    Worker w = new PartTimeWorker(new WorkerInfo("John", "�~�J�u", new CustomDate(2020,5,20)), new Time(8,0));
    Worker.addWorker(w);
    String id = w.getInfo().getId();
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 1), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(13, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 2), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(12, 0)), 1, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 3), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(13, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 4), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(13, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 5), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(13, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 6), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(13, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 10), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(13, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 12), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(13, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 13), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(13, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 14), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(12, 0)), 1, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 15), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(9, 0)), 0, new LeaveRecord("�f��" ,new Message(), new Time(9,0), new Time(13,0)), false));
    attendanceRecordSystem.deleteDayRecord(id, new CustomDate(2023, 5, 10));
    System.out.println(id.toString() + " �~�ꬰ " + salarySystem.computeMonthlySalary(id, new CustomDate(2023, 5)));
    
    // �ĤG�ӭ��u - (�ϥΥ��d�����@���X�Ԭ���)
    Worker w2 = new FullTimeWorker(new WorkerInfo("Sarah", "�]�ȳ��|�p�v", new CustomDate(2019,6,30)));
    Worker.addWorker(w2);
    id = w2.getInfo().getId();
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 1), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 2), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 3), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 4), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 5), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(13, 0)), 0, new LeaveRecord("�ư�" ,new Message(), new Time(13,0), new Time(17,0)), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 6), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 10), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 12), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 13), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 14), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(13, 0)), 4, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 15), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addAttendanceRecord(id, new CustomDate(2023, 5, 16), new ClockRecord(new Time(9,0), new Time(16, 0)));
    attendanceRecordSystem.addLeaveRecord(id, new CustomDate(2023, 5, 16), new LeaveRecord("�ư�", new Message(), new Time(8,0), new Time(9, 0)));
    
    // ���o���u�b�S�w�~�몺�~��
    System.out.println(id.toString() + " �~�ꬰ " + salarySystem.computeMonthlySalary(id, new CustomDate(2023, 5)));
    
    // ���o�b�S�w�~�몺�Ҧ����u���X�Ԭ���
    CustomDate specificDate = new CustomDate(2023,5,16);
    TreeMap<String, AttendanceDayRecord> attendanceRecordAtSpecificYearMonth = attendanceRecordSystem.searchAllWorkersRecordsByYearMonthDay(specificDate);
    System.out.println("�b " + specificDate.toString() + "�A���u���X�ʶԬ�����: ");
    for (Map.Entry<String, AttendanceDayRecord> worker : attendanceRecordAtSpecificYearMonth.entrySet()) {
      System.out.println("ID: " + worker.getKey() + "�A�m�W: " + Worker.getWorkerById(worker.getKey()).getInfo().getName() + ": \n" + worker.getValue().toString());
    }
    
    // ���o�b�S�w�~�몺���u�X�Ԭ������а���T
    specificDate = new CustomDate(2023,5,5);
    AttendanceDayRecord record = attendanceRecordSystem.searchRecordByYearMonthDay(id, specificDate);
    System.out.println(record.getLeaveRecord().getLeaveType());

    
    // ���եͦ����
    System.out.println(new CustomDate().toString());
  }
}