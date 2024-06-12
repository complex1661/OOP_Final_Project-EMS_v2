package ems;
import java.io.*;

public class LoadManageSystem extends Load{
  private static final String FILE_NAME = "ManageSystemData.dat";
  
  public ManageSystem loadSystem(){
    ManageSystem system = null;
    try {
      system = loadFileByName(FILE_NAME);
    } catch (FileNotFoundException e) {
      System.out.println(e);
    }
    return system;
  }
  
  public ManageSystem loadFileByName(String fileName) throws FileNotFoundException{
    
    File file = new File(fileName);
    if ( !file.exists() ) {
      throw new FileNotFoundException("檔案："+ fileName +"不存在");
    }
    
    ManageSystem system = null;
    try (FileInputStream fileIn = new FileInputStream(file);
         ObjectInputStream in = new ObjectInputStream(fileIn)) {
          
           system = (ManageSystem)in.readObject();
         } catch (IOException | ClassNotFoundException e) {
           System.out.println(e);
         }
         return system;
  }
}