package ems;
import java.util.*;

public class TestSystem {
  public static void main (String[] args) {
    
    ManageSystem system = new ManageSystem();
    SaveWorker workerSaver = new SaveWorker();
    LoadWorker workerLoader = new LoadWorker();
    workerLoader.load();
    AttendanceRecordSystem attendanceRecordSystem = system.getAttendance();
    
    // 第一個員工
    Worker w = new PartTimeWorker(new WorkerInfo("John", "洗碗工", new CustomDate(2020,5,20)), new Time(8,0));
    Worker.addWorker(w);
    String id = w.getInfo().getId();
    attendanceRecordSystem.addAttendanceRecord(id, new CustomDate(2023, 5, 17), new ClockRecord(new Time(9,1), new Time(17, 0)));
    attendanceRecordSystem.addLeaveRecord(id, new CustomDate(2023, 5, 18), new LeaveRecord("特休", new Message()));
    System.out.println(id.toString() + " 薪資為 " + system.getSalary().computeMonthlySalary( id, new CustomDate(2023, 5)));
    
    attendanceRecordSystem.deleteDayRecord(id, new CustomDate(2023, 5, 17));
    
    // 第二個員工 - (使用打卡紀錄作為出勤紀錄)
    Worker w2 = new FullTimeWorker(new WorkerInfo("Sarah", "財務部會計師", new CustomDate(2019,6,30)));
    Worker.addWorker(w2);
    id = w2.getInfo().getId();
    
    attendanceRecordSystem.addAttendanceRecord(id, new CustomDate(2023, 5, 16), new ClockRecord(new Time(9,0), new Time(17, 0)));
    attendanceRecordSystem.addLeaveRecord(id, new CustomDate(2023, 5, 16), new LeaveRecord("事假", new Message(), new Time(8,0), new Time(9, 0)));
    attendanceRecordSystem.addAttendanceRecord(id, new CustomDate(2023, 5, 17), new ClockRecord(new Time(8,0), new Time(17, 0)));
    attendanceRecordSystem.addAttendanceRecord(id, new CustomDate(2023, 5, 18), new ClockRecord(new Time(8,0), new Time(17, 0)));
    attendanceRecordSystem.addOvertimeRecord(id, new CustomDate(2023, 5, 18), new OvertimeRecord(new Time(18,0), new Time(18,50)));
    
    Worker w3 = new FullTimeWorker(new WorkerInfo("Candy", "業務", new CustomDate(2020,8,5)));
    Worker.addWorker(w3);
    
    //儲存系統資料
    SaveManageSystem systemSaver = new SaveManageSystem();
    
  
    
    // 取得員工在特定年月的薪資
    System.out.println(id.toString() + " 薪資為 " + system.getSalary().computeMonthlySalary(id, new CustomDate(2023, 5)));
   
    // 取得在特定年月的所有員工的出勤紀錄
    CustomDate specificDate = new CustomDate(2023,5,18);
    TreeMap<String, AttendanceDayRecord> attendanceRecordAtSpecificYearMonth = system.getAttendance().searchAllWorkersRecordsByYearMonthDay(specificDate);
    System.out.println("在 " + specificDate.toString() + "，員工的出缺勤紀錄為: ");
    for (Map.Entry<String, AttendanceDayRecord> worker : attendanceRecordAtSpecificYearMonth.entrySet()) {
      System.out.println("ID: " + worker.getKey() + "，姓名: " + Worker.getWorkerById(worker.getKey()).getInfo().getName() + ": \n" + worker.getValue().toString());
    }
    
    // 取得在特定年月的員工出勤紀錄之請假資訊
    specificDate = new CustomDate(2023,5,16);
    AttendanceDayRecord record = system.getAttendance().searchRecordByYearMonthDay(id, specificDate);
    System.out.println(record.getLeaveRecord().getLeaveType());

    
    // 測試生成日期
    System.out.println(new CustomDate().toString());
    System.out.println(Worker.getWorkerById("0000001").getPaidLeaveDays());
    System.out.println("IdCounter:" + WorkerInfo.getIdCounter());
    
    workerSaver.save();
  }
}