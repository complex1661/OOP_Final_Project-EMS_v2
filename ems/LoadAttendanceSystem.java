package ems;
import java.io.*;

public class LoadAttendanceSystem extends Load{
  private static final String FILE_NAME = "AttendanceRecordSystem.dat";
  
  public AttendanceRecordSystem loadSystem(){
    AttendanceRecordSystem system = null;
    try {
      system = loadFileByName(FILE_NAME);
    } catch (FileNotFoundException e) {
      System.out.println(e);
    }
    return system;
  }
  
  public AttendanceRecordSystem loadFileByName(String fileName) throws FileNotFoundException{
    
    File file = new File(fileName);
    if ( !file.exists() ) {
      throw new FileNotFoundException("檔案："+ fileName +"不存在");
    }
    
    AttendanceRecordSystem system = null;
    try (FileInputStream fileIn = new FileInputStream(file);
         ObjectInputStream in = new ObjectInputStream(fileIn)) {
          
           system = (AttendanceRecordSystem)in.readObject();
         } catch (IOException | ClassNotFoundException e) {
           System.out.println(e);
         }
         return system;
  }
}