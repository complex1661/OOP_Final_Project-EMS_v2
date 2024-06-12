package ems;
import java.io.*;

public class SaveManageSystem extends Save<ManageSystem>{
  private static String DIR_NAME = "systems";
  private static final String FILE_NAME = "ManageSystemData.dat";
  
  public SaveManageSystem() {}
  
  public SaveManageSystem(String dir) {
    DIR_NAME = dir + "systems";
  }
  
  public void saveFileTo(ManageSystem system){
    File dir = new File(DIR_NAME);
    if (!dir.isDirectory() || !dir.exists()){ 
      dir.mkdir();
    }
    
    File file = new File(dir, FILE_NAME);
    try (FileOutputStream fileOut = new FileOutputStream(file);
         ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
           out.writeObject(system);
         } catch (IOException e){
           System.out.println(e);
         }
  }
 }