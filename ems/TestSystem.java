package ems;
import java.util.*;

public class TestSystem {
  public static void main (String[] args) { 
    AttendanceRecordSystem attendanceRecordSystem = ManageSystem.getAttendanceRecordSystem();
    SalarySystem salarySystem = ManageSystem.getSalarySystem();
    Worker w = new PartTimeWorker(new WorkerInfo("John", "洗碗工", new CustomDate(2020,5,20)));
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
    System.out.println(uuid.toString() + " 薪資為 " + salarySystem.computeMonthlySalary(uuid, new CustomDate(2023, 5)));
    Worker w2 = new FullTimeWorker(new WorkerInfo("Sarah", "財務部會計師", new CustomDate(2019,6,30)));
    Worker.addWorker(w2);
    uuid = w2.getInfo().getUUID();
    
//    for (Map.Entry<UUID, Worker> entry: Worker.workerList.entrySet()) {
//      System.out.println(entry.getKey().toString());
//    }
    
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 1), new AttendanceDayRecord(8, 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 2), new AttendanceDayRecord(8, 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 3), new AttendanceDayRecord(8, 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 4), new AttendanceDayRecord(8, 0, 0, true, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 5), new AttendanceDayRecord(4, 0, 4, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 6), new AttendanceDayRecord(8, 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 10), new AttendanceDayRecord(8, 0, 0, true, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 12), new AttendanceDayRecord(8, 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 13), new AttendanceDayRecord(8, 0, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 14), new AttendanceDayRecord(4, 4, 0, false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 15), new AttendanceDayRecord(8, 0, 0, false, false));
    
    System.out.println(uuid.toString() + " 薪資為 " + salarySystem.computeMonthlySalary(uuid, new CustomDate(2023, 5)));
    
    TreeMap<UUID, AttendanceDayRecord> attendanceRecordAtSpecificYearMonth = attendanceRecordSystem.searchAllWorkersRecordsByYearMonthDay(new CustomDate(2023,5,5));
    
    for (Map.Entry<UUID, AttendanceDayRecord> worker : attendanceRecordAtSpecificYearMonth.entrySet()) {
      System.out.println(worker.getKey() + ": \n" + worker.getValue().toString());
    }
  }
}