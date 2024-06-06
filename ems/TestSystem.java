package ems;
import java.util.*;

public class TestSystem {
  public static void main (String[] args) { 
    AttendanceRecordSystem attendanceRecordSystem = ManageSystem.getAttendanceRecordSystem();
    SalarySystem salarySystem = ManageSystem.getSalarySystem();
    
    // �Ĥ@�ӭ��u
    Worker w = new PartTimeWorker(new WorkerInfo("John", "�~�J�u", new CustomDate(2020,5,20)));
    Worker.addWorker(w);
    UUID uuid = w.getInfo().getUUID();
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 1), new AttendanceDayRecord(uuid, 5, 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 2), new AttendanceDayRecord(uuid, 4, 1, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 3), new AttendanceDayRecord(uuid, 5, 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 4), new AttendanceDayRecord(uuid, 5, 0, new LeaveRecord(), true, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 5), new AttendanceDayRecord(uuid, 5, 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 6), new AttendanceDayRecord(uuid, 5, 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 10), new AttendanceDayRecord(uuid, 5, 0, new LeaveRecord(), true, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 12), new AttendanceDayRecord(uuid, 5, 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 13), new AttendanceDayRecord(uuid, 5, 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 14), new AttendanceDayRecord(uuid, 4, 1, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 15), new AttendanceDayRecord(uuid, 1, 0, new LeaveRecord("�f��" ,new Message()), false, false));
    attendanceRecordSystem.deleteDayRecord(uuid, new CustomDate(2023, 5, 10));
    System.out.println(uuid.toString() + " �~�ꬰ " + salarySystem.computeMonthlySalary(uuid, new CustomDate(2023, 5)));
    
    // �ĤG�ӭ��u - (�ϥΥ��d�����@���X�Ԭ���)
    Worker w2 = new FullTimeWorker(new WorkerInfo("Sarah", "�]�ȳ��|�p�v", new CustomDate(2019,6,30)));
    Worker.addWorker(w2);
    uuid = w2.getInfo().getUUID();
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 1), new AttendanceDayRecord(uuid, WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 2), new AttendanceDayRecord(uuid, WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 3), new AttendanceDayRecord(uuid, WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 4), new AttendanceDayRecord(uuid, WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), true, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 5), new AttendanceDayRecord(uuid,4, 0, new LeaveRecord("�ư�" ,new Message()), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 6), new AttendanceDayRecord(uuid, WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 10), new AttendanceDayRecord(uuid, WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), true, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 12), new AttendanceDayRecord(uuid, WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 13), new AttendanceDayRecord(uuid, WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 14), new AttendanceDayRecord(uuid, 4, 4, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 15), new AttendanceDayRecord(uuid,WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), false, false));
    
    // ���o���u�b�S�w�~�몺�~��
    System.out.println(uuid.toString() + " �~�ꬰ " + salarySystem.computeMonthlySalary(uuid, new CustomDate(2023, 5)));
    
    // ���o�b�S�w�~�몺�Ҧ����u���X�Ԭ���
    CustomDate specificDate = new CustomDate(2023,5,15);
    TreeMap<UUID, AttendanceDayRecord> attendanceRecordAtSpecificYearMonth = attendanceRecordSystem.searchAllWorkersRecordsByYearMonthDay(specificDate);
    System.out.println("�b " + specificDate.toString() + "�A���u���X�ʶԬ�����: ");
    for (Map.Entry<UUID, AttendanceDayRecord> worker : attendanceRecordAtSpecificYearMonth.entrySet()) {
      System.out.println("UUID: " + worker.getKey() + "�A�m�W: " + Worker.getWorkerByUUID(worker.getKey()).getInfo().getName() + ": \n" + worker.getValue().toString());
    }
    
    // ���o�b�S�w�~�몺���u�X�Ԭ������а���T
    specificDate = new CustomDate(2023,5,5);
    AttendanceDayRecord record = attendanceRecordSystem.searchRecordByYearMonthDay(uuid, specificDate);
    System.out.println(record.getLeaveRecord().getLeaveType());

    
    // ���եͦ����
    System.out.println(new CustomDate().toString());
  }
}