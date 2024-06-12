package ems;
import java.io.*;

public class LoadManageSystem extends Load{
  private static String DIR_NAME = "systems";
  private static final String FILE_NAME = "ManageSystemData.dat";

  public LoadManageSystem() {}
  
  public LoadManageSystem(String dir) {
    DIR_NAME = dir + "systems";
  }
  
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

    File dir = new File(DIR_NAME);
    if (!dir.exists() || !dir.isDirectory()) {
      throw new FileNotFoundException("目錄："+ DIR_NAME +"不存在");
    }
    
    File file = new File(dir, FILE_NAME);
    if (!file.exists() ) {
      throw new FileNotFoundException("檔案: " + FILE_NAME + "不存在");
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