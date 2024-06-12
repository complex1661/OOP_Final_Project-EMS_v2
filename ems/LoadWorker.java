package ems;

import java.util.*;
import java.io.*;

public class LoadWorker extends Load{
  private static final String DIR_NAME = "workers";
  
  public Worker loadFileByName(String workerID) throws FileNotFoundException{
    
    File dir = new File(DIR_NAME);
    if (!dir.exists() || !dir.isDirectory()) {
      throw new FileNotFoundException("目錄："+DIR_NAME+"不存在");
    }
    
    String fileName = workerID + ".dat";
    File file = new File(dir,workerID);
    if ( !file.exists() ) {
      throw new FileNotFoundException("檔案："+ fileName +"不存在");
    }
    
    Worker worker = null;
    try (FileInputStream fileIn = new FileInputStream(file);
         ObjectInputStream in = new ObjectInputStream(fileIn)) {
          
           worker = (Worker)in.readObject();
         } catch (IOException | ClassNotFoundException e) {
           System.out.println(e);
         }
         return worker;
  }
  
  public ArrayList<Worker> loadFilesFrom(String dirName)  throws FileNotFoundException{  
    File dir = new File(dirName);
    if (!dir.exists() || !dir.isDirectory()) {
      throw new FileNotFoundException("目錄不存在");
    }
    
    File[] files = dir.listFiles();
    if (files == null) {
        throw new FileNotFoundException("目錄中沒有檔案");
    }
    
    ArrayList<Worker> workers = new ArrayList<Worker>();
    
    for (File file : files) {
      String fileName = file.getName();
        
      if (fileName.endsWith(".dat")) {
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
          
               Worker worker = (Worker)in.readObject();
               workers.add(worker);
             } catch (IOException | ClassNotFoundException e) {
               System.out.println(e);
           }
      }
    }
    
    return workers;
  }
}