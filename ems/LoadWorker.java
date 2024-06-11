package ems;

import java.util.*;
import java.io.*;

public class LoadWorker extends Load{
  
  public ArrayList<Worker> loadFilesFrom(String dirName)  throws FileNotFoundException{
    
    File dir = new File(dirName);
    if (!dir.isDirectory() || !dir.exists()) {
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
}