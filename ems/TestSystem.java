package ems;
import java.util.*;

public class TestSystem {
  public static void main (String[] args) { 
    AttendanceRecordSystem attendanceRecordSystem = ManageSystem.getAttendanceRecordSystem();
    SalarySystem salarySystem = ManageSystem.getSalarySystem();
    
    // 第一個員工
    Worker w = new PartTimeWorker(new WorkerInfo("John", "洗碗工", new CustomDate(2020,5,20)));
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
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 15), new AttendanceDayRecord(uuid, 1, 0, new LeaveRecord("病假" ,new Message()), false, false));
    attendanceRecordSystem.deleteDayRecord(uuid, new CustomDate(2023, 5, 10));
    System.out.println(uuid.toString() + " 薪資為 " + salarySystem.computeMonthlySalary(uuid, new CustomDate(2023, 5)));
    
    // 第二個員工 - (使用打卡紀錄作為出勤紀錄)
    Worker w2 = new FullTimeWorker(new WorkerInfo("Sarah", "財務部會計師", new CustomDate(2019,6,30)));
    Worker.addWorker(w2);
    uuid = w2.getInfo().getUUID();
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 1), new AttendanceDayRecord(uuid, WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 2), new AttendanceDayRecord(uuid, WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 3), new AttendanceDayRecord(uuid, WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 4), new AttendanceDayRecord(uuid, WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), true, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 5), new AttendanceDayRecord(uuid,4, 0, new LeaveRecord("事假" ,new Message()), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 6), new AttendanceDayRecord(uuid, WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 10), new AttendanceDayRecord(uuid, WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), true, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 12), new AttendanceDayRecord(uuid, WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 13), new AttendanceDayRecord(uuid, WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 14), new AttendanceDayRecord(uuid, 4, 4, new LeaveRecord(), false, false));
    attendanceRecordSystem.addDayRecord(uuid, new CustomDate(2023, 5, 15), new AttendanceDayRecord(uuid,WorkerClockInSystem.getClockHour(new ClockRecord(new Time(9,0), new Time(17, 0))), 0, new LeaveRecord(), false, false));
    
    // 取得員工在特定年月的薪資
    System.out.println(uuid.toString() + " 薪資為 " + salarySystem.computeMonthlySalary(uuid, new CustomDate(2023, 5)));
    
    // 取得在特定年月的所有員工的出勤紀錄
    CustomDate specificDate = new CustomDate(2023,5,15);
    TreeMap<UUID, AttendanceDayRecord> attendanceRecordAtSpecificYearMonth = attendanceRecordSystem.searchAllWorkersRecordsByYearMonthDay(specificDate);
    System.out.println("在 " + specificDate.toString() + "，員工的出缺勤紀錄為: ");
    for (Map.Entry<UUID, AttendanceDayRecord> worker : attendanceRecordAtSpecificYearMonth.entrySet()) {
      System.out.println("UUID: " + worker.getKey() + "，姓名: " + Worker.getWorkerByUUID(worker.getKey()).getInfo().getName() + ": \n" + worker.getValue().toString());
    }
    
    // 取得在特定年月的員工出勤紀錄之請假資訊
    specificDate = new CustomDate(2023,5,5);
    AttendanceDayRecord record = attendanceRecordSystem.searchRecordByYearMonthDay(uuid, specificDate);
    System.out.println(record.getLeaveRecord().getLeaveType());

    
    // 測試生成日期
    System.out.println(new CustomDate().toString());
  }
}