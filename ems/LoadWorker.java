package ems;

import java.util.*;
import java.io.*;

public class LoadWorker extends Load{
  private static String DIR_NAME = "workers";
  
  public LoadWorker() {}
  
  public LoadWorker(String dir) {
    DIR_NAME = dir + "workers";
  }
  
  @SuppressWarnings("unchecked")
  public void load() {
    try {
      TreeMap<String, Object> metadata = loadIdFile();
      
      int idCounter = (Integer) metadata.get("idCounter");
      System.out.println("current idCounter is:" + idCounter);
      WorkerInfo.setIdCounter(idCounter);
      
      ArrayList<String> idList = (ArrayList<String>) metadata.get("idList");
      for (String workerId : idList) {
        Worker.addWorker(loadFileByName(workerId));
      }
    } catch (FileNotFoundException e) {
       System.out.println(e);
    }
  }
  
  @SuppressWarnings("unchecked")
  private TreeMap<String, Object> loadIdFile () throws FileNotFoundException{
    File dir = new File(DIR_NAME);
    if (!dir.exists() || !dir.isDirectory()) {
      throw new FileNotFoundException("�ؿ��G"+ DIR_NAME +"���s�b");
    }
    
    File file = new File(dir,"workerIdMetaData.dat");
    if (!file.exists() ) {
      throw new FileNotFoundException("�ɮ� workerIdMetaData.dat ���s�b");
    }
    
    TreeMap<String, Object> metadata = null;
    
    try (FileInputStream fileIn = new FileInputStream(file); ObjectInputStream in = new ObjectInputStream(fileIn)) {
      metadata = (TreeMap<String, Object>) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.out.println(e);
    }

    return metadata;
  }
  
  public Worker loadFileByName(String workerID) throws FileNotFoundException{
    File dir = new File(DIR_NAME);
    if (!dir.exists() || !dir.isDirectory()) {
      throw new FileNotFoundException("�ؿ��G"+ DIR_NAME + "���s�b");
    }
    
    String fileName = workerID + ".dat";
    File file = new File(dir, fileName);
    if (!file.exists()) {
      throw new FileNotFoundException("�ɮסG"+ fileName +"���s�b");
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
      throw new FileNotFoundException("�ؿ����s�b");
    }
    
    File[] files = dir.listFiles();
    if (files == null) {
        throw new FileNotFoundException("�ؿ����S���ɮ�");
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
  
  @SuppressWarnings("unchecked")
  public void removeWorkerId(String workerId) throws IOException, ClassNotFoundException {
    TreeMap<String, Object> metadata = loadIdFile();
    ArrayList<String> workerIdList = (ArrayList<String>) metadata.get("idList");
    if (workerIdList.remove(workerId)) {
      saveIdList(workerIdList);
    } else {
      throw new IllegalArgumentException("worker ID ���b idList.dat�̡C");
    }
  }
  
  private void saveIdList(ArrayList<String> idList) throws IOException {
    File dir = new File(DIR_NAME);
    if (!dir.exists() || !dir.isDirectory()) {
      throw new FileNotFoundException("�ؿ��G" + DIR_NAME + "���s�b");
    }
    
    File file = new File(dir, "idList.dat");
    try (FileOutputStream fileOut = new FileOutputStream(file);
         ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
           out.writeObject(idList);
         }
  }
}