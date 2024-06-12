package ems;
import java.io.*;

public class SaveManageSystem extends Save<ManageSystem>{
  private static final String FILE_NAME = "ManageSystemData.dat";
  
  public void saveFileTo(ManageSystem system){

    File file = new File(FILE_NAME);
    
    try (FileOutputStream fileOut = new FileOutputStream(file);
         ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
           out.writeObject(system);
         } catch (IOException e){
           System.out.println(e);
         }
  }
 }