package ems;
import java.io.*;
import java.util.TreeMap;
import java.util.ArrayList;

public class SaveManageSystem extends Save<TreeMap<String, Object>>{
  private static String DIR_NAME = "systems";
  private static final String FILE_NAME = "ManageSystemData.dat";

  public SaveManageSystem() {}
  
  public SaveManageSystem(String dir) {
    DIR_NAME = dir + "systems";
  }
  
  public void save(AttendanceRecordSystem attendanceRecordSystem) {
    TreeMap<CustomDate, TreeMap<String, AttendanceDayRecord>> dayToWorkers = attendanceRecordSystem.getDayToWorkers();
    TreeMap<String, TreeMap<CustomDate, AttendanceDayRecord>> workerToDays = attendanceRecordSystem.getWorkerToDays();
    TreeMap<String, Object> recordMaps = new TreeMap<>();
    recordMaps.put("dayToWorkers",dayToWorkers);
    recordMaps.put("workerToDays",workerToDays);
    saveFileTo(recordMaps);
  }
  
  public void saveFileTo(TreeMap<String, Object> recordMaps){
    File dir = new File(DIR_NAME);
    if (!dir.isDirectory() || !dir.exists()){ 
      dir.mkdir();
    }
    
    File file = new File(dir, FILE_NAME);
    try (FileOutputStream fileOut = new FileOutputStream(file);
         ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
           out.writeObject(recordMaps);
         } catch (IOException e){
           System.out.println(e);
         }
  }
 }