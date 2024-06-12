package ems;

import java.io.*;
import java.util.ArrayList;
public class SaveWorker extends Save<Worker>{
  private static String DIR_NAME = "workers";
  private static ArrayList<String> workerIdList = new ArrayList<>();
  
  public SaveWorker() {}
  
  public SaveWorker(String dir) {
    DIR_NAME = dir + "workers";
  }
  
  public void saveFileTo(Worker worker){
    File dir = new File(DIR_NAME);
    if (!dir.isDirectory() || !dir.exists()){
      dir.mkdir();
    }
    
    String fileName = worker.getInfo().getId() + ".dat";
    workerIdList.add(worker.getInfo().getId());
    File file = new File(dir, fileName);
    File idFile = new File(dir, "idList.dat");
    
    try (FileOutputStream fileOut = new FileOutputStream(file);ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
      out.writeObject(worker);
    } catch (IOException e){
      System.out.println(e);
    }
    
    try (FileOutputStream fileOut = new FileOutputStream(idFile); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
      out.writeObject(workerIdList);
    } catch (IOException e){
      System.out.println(e);
    }
  }
  
}