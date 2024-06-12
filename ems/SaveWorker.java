package ems;

import java.io.*;

public class SaveWorker extends Save<Worker>{
  private static final String DIR_NAME = "workers";
  
  public void saveFileTo(Worker worker){
    File dir = new File(DIR_NAME);
    if (!dir.isDirectory() || !dir.exists()){
      dir.mkdir();
    }
    
    String fileName = worker.info.getId() + ".dat";
    File file = new File(dir, fileName);
    
    try (FileOutputStream fileOut = new FileOutputStream(file);
         ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
           out.writeObject(worker);
         } catch (IOException e){
           System.out.println(e);
         }
  }
}