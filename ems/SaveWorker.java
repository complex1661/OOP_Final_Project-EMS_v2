package ems;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;

public class SaveWorker extends Save<Worker>{
  private static String DIR_NAME = "workers";
  private static ArrayList<String> workerIdList = new ArrayList<>();
  private static int idCounter = 0;
  
  public SaveWorker() {}
  
  public SaveWorker(String dir) {
    DIR_NAME = dir + "workers";
  }
  
  public void save() {
    idCounter = WorkerInfo.getIdCounter();
    File dir = new File(DIR_NAME);
    if (!dir.isDirectory() || !dir.exists()){
      dir.mkdir();
    }
    TreeMap<String, Worker> workersList = Worker.getAllWorkers();
    for (Map.Entry<String, Worker> entry : workersList.entrySet()) {
      Worker worker = entry.getValue();
      saveFileTo(worker);
    }
    File workerIdMetaFile = new File(dir, "workerIdMetaData.dat");
    saveWorkerIdMetaFile(workerIdMetaFile);
  }
  
  public void saveFileTo(Worker worker){
    File dir = new File(DIR_NAME);
    if (!dir.isDirectory() || !dir.exists()){
      dir.mkdir();
    }
    String fileName = worker.getInfo().getId() + ".dat";
    workerIdList.add(worker.getInfo().getId());
    File file = new File(dir, fileName);

    try (FileOutputStream fileOut = new FileOutputStream(file);ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
      out.writeObject(worker);
    } catch (IOException e){
      System.out.println(e);
    }
  }
  
  private void saveWorkerIdMetaFile(File workerIdMetaFile) {
    TreeMap<String, Object> workerIdMetaData = new TreeMap<>();
     workerIdMetaData.put("idList", workerIdList);
     workerIdMetaData.put("idCounter", idCounter);
     
     try (FileOutputStream fileOut = new FileOutputStream(workerIdMetaFile);
          ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(workerIdMetaData);
          } catch (IOException e) {
            System.out.println(e);
          }
  }
  
}