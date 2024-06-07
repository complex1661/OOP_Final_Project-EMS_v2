package ems;
import java.util.*;

public class TestSystem {
  public static void main (String[] args) { 
    AttendanceRecordSystem attendanceRecordSystem = ManageSystem.getAttendanceRecordSystem();
    SalarySystem salarySystem = ManageSystem.getSalarySystem();
   
    // 第一個員工
    Worker w = new PartTimeWorker(new WorkerInfo("John", "洗碗工", new CustomDate(2020,5,20)), new Time(8,0));
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
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 15), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(9, 0)), 0, new LeaveRecord("病假" ,new Message(), new Time(9,0), new Time(13,0)), false));
    attendanceRecordSystem.deleteDayRecord(id, new CustomDate(2023, 5, 10));
    System.out.println(id.toString() + " 薪資為 " + salarySystem.computeMonthlySalary(id, new CustomDate(2023, 5)));
    
    // 第二個員工 - (使用打卡紀錄作為出勤紀錄)
    Worker w2 = new FullTimeWorker(new WorkerInfo("Sarah", "財務部會計師", new CustomDate(2019,6,30)));
    Worker.addWorker(w2);
    id = w2.getInfo().getId();
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 1), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 2), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 3), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 4), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 5), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(13, 0)), 0, new LeaveRecord("事假" ,new Message(), new Time(13,0), new Time(17,0)), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 6), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 10), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 12), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 13), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 14), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(13, 0)), 4, new LeaveRecord(), false));
    attendanceRecordSystem.addDayRecord(id, new CustomDate(2023, 5, 15), new AttendanceDayRecord(id, new ClockRecord(new Time(8,0), new Time(17, 0)), 0, new LeaveRecord(), false));
    attendanceRecordSystem.addAttendanceRecord(id, new CustomDate(2023, 5, 16), new ClockRecord(new Time(9,0), new Time(16, 0)));
    attendanceRecordSystem.addLeaveRecord(id, new CustomDate(2023, 5, 16), new LeaveRecord("事假", new Message(), new Time(8,0), new Time(9, 0)));
    
    // 取得員工在特定年月的薪資
    System.out.println(id.toString() + " 薪資為 " + salarySystem.computeMonthlySalary(id, new CustomDate(2023, 5)));
    
    // 取得在特定年月的所有員工的出勤紀錄
    CustomDate specificDate = new CustomDate(2023,5,16);
    TreeMap<String, AttendanceDayRecord> attendanceRecordAtSpecificYearMonth = attendanceRecordSystem.searchAllWorkersRecordsByYearMonthDay(specificDate);
    System.out.println("在 " + specificDate.toString() + "，員工的出缺勤紀錄為: ");
    for (Map.Entry<String, AttendanceDayRecord> worker : attendanceRecordAtSpecificYearMonth.entrySet()) {
      System.out.println("ID: " + worker.getKey() + "，姓名: " + Worker.getWorkerById(worker.getKey()).getInfo().getName() + ": \n" + worker.getValue().toString());
    }
    
    // 取得在特定年月的員工出勤紀錄之請假資訊
    specificDate = new CustomDate(2023,5,5);
    AttendanceDayRecord record = attendanceRecordSystem.searchRecordByYearMonthDay(id, specificDate);
    System.out.println(record.getLeaveRecord().getLeaveType());

    
    // 測試生成日期
    System.out.println(new CustomDate().toString());
  }
}