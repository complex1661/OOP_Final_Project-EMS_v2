package ems;

import java.util.*;
import java.io.*;

public class LoadWorker extends Load{
  private static String DIR_NAME = "workers";
  
  public LoadWorker() {}
  
  public LoadWorker(String dir) {
    DIR_NAME = dir + "workers";
  }
  
  public void load() {
    try {
      ArrayList<String>idList = loadIdFile();
      for (String workerId : idList) {
        Worker.addWorker(loadFileByName(workerId));
      }
    } catch (FileNotFoundException e) {
       System.out.println(e);
    }
  }
  
  @SuppressWarnings("unchecked")
  private ArrayList<String> loadIdFile () throws FileNotFoundException{
    File dir = new File(DIR_NAME);
    if (!dir.exists() || !dir.isDirectory()) {
      throw new FileNotFoundException("目錄："+ DIR_NAME +"不存在");
    }
    
    File file = new File(dir,"idList.dat");
    if (!file.exists() ) {
      throw new FileNotFoundException("檔案 idList.dat 不存在");
    }
    
    ArrayList<String> idList = null;
    try (FileInputStream fileIn = new FileInputStream(file); ObjectInputStream in = new ObjectInputStream(fileIn)) {
      idList = (ArrayList<String>)in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.out.println(e);
    }

    return idList;
  }
  
  public Worker loadFileByName(String workerID) throws FileNotFoundException{
    File dir = new File(DIR_NAME);
    if (!dir.exists() || !dir.isDirectory()) {
      throw new FileNotFoundException("目錄："+ DIR_NAME + "不存在");
    }
    
    String fileName = workerID + ".dat";
    File file = new File(dir, fileName);
    if (!file.exists()) {
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
  
  public void removeWorkerId(String workerId) throws IOException, ClassNotFoundException {
    ArrayList<String> idList = loadIdFile();
    if (idList.remove(workerId)) {
      saveIdList(idList);
    } else {
      throw new IllegalArgumentException("worker ID 不再 idList.dat");
    }
  }
  
  private void saveIdList(ArrayList<String> idList) throws IOException {
    File dir = new File(DIR_NAME);
    if (!dir.exists() || !dir.isDirectory()) {
      throw new FileNotFoundException("目錄：" + DIR_NAME + "不存在");
    }
    
    File file = new File(dir, "idList.dat");
    try (FileOutputStream fileOut = new FileOutputStream(file);
         ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
           out.writeObject(idList);
         }
  }
}