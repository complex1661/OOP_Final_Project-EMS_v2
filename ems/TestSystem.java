package ems;
import java.util.*;

public class TestSystem {
  public static void main (String[] args) { 
    AttendanceRecordSystem attendanceRecordSystem = ManageSystem.getAttendanceRecordSystem();
    SalarySystem salarySystem = ManageSystem.getSalarySystem();
    Worker w = new PartTimeWorker(new WorkerInfo("John", "�~�J�u", new CustomDate(2020,5,20)));
    Worker.addWorker(w);
    UUID uuid = w.getInfo().getUUID();
    
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 1), new AttendanceDayRecord(5, 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 2), new AttendanceDayRecord(4, 1, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 3), new AttendanceDayRecord(5, 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 4), new AttendanceDayRecord(5, 0, 0, true, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 5), new AttendanceDayRecord(5, 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 6), new AttendanceDayRecord(5, 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 10), new AttendanceDayRecord(5, 0, 0, true, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 12), new AttendanceDayRecord(5, 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 13), new AttendanceDayRecord(5, 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 14), new AttendanceDayRecord(4, 1, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 15), new AttendanceDayRecord(0, 0, 5, false, false));
    attendanceRecordSystem.deleteDayRecord(uuid, new CustomDate(2023, 5, 10));
    System.out.println(uuid.toString() + " �~�ꬰ " + salarySystem.computeMonthlySalary(uuid, new CustomDate(2023, 5)));
    Worker w2 = new FullTimeWorker(new WorkerInfo("Sarah", "�]�ȳ��|�p�v", new CustomDate(2019,6,30)));
    Worker.addWorker(w2);
    uuid = w2.getInfo().getUUID();
    
//    for (Map.Entry<UUID, Worker> entry: Worker.workerList.entrySet()) {
//      System.out.println(entry.getKey().toString());
//    }
    
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 1), new AttendanceDayRecord(WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 2), new AttendanceDayRecord(WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 3), new AttendanceDayRecord(WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 4), new AttendanceDayRecord(WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, 0, true, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 5), new AttendanceDayRecord(4, 0, 4, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 6), new AttendanceDayRecord(WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 10), new AttendanceDayRecord(WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, 0, true, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 12), new AttendanceDayRecord(WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 13), new AttendanceDayRecord(WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 14), new AttendanceDayRecord(4, 4, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 15), new AttendanceDayRecord(WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, 0, false, false));
    
    System.out.println(uuid.toString() + " �~�ꬰ " + salarySystem.computeMonthlySalary(uuid, new CustomDate(2023, 5)));
    
    CustomDate specificDate = new CustomDate(2023,5,5);
    TreeMap<UUID, AttendanceDayRecord> attendanceRecordAtSpecificYearMonth = attendanceRecordSystem.searchAllWorkersRecordsByYearMonthDay(specificDate);
    System.out.println("�b " + specificDate.toString() + "�A���u���X�ʶԬ�����: ");
    for (Map.Entry<UUID, AttendanceDayRecord> worker : attendanceRecordAtSpecificYearMonth.entrySet()) {
      System.out.println("UUID: " + worker.getKey() + "�A�m�W: " + Worker.getWorkerByUUID(worker.getKey()).getInfo().getName() + ": \n" + worker.getValue().toString());
    }
    
  }
}