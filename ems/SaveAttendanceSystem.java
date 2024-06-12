package ems;
import java.io.*;

public class SaveAttendanceSystem extends Save<AttendanceRecordSystem
  private static final String FILE_NAME = "AttendanceRecordSystem.dat";
  
  public void saveFileTo(AttendanceRecordSystem system){

    File file = new File(FILE_NAME);
    
    try (FileOutputStream fileOut = new FileOutputStream(file);
         ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
           out.writeObject(system);
         } catch (IOException e){
           System.out.println(e);
         }
  }
 }