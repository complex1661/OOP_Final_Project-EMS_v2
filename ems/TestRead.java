package ems;

import java.util.*;
import java.io.*;

public class TestRead{
  public static void main(String[] args) {
    
    LoadWorker loadW = new LoadWorker();
    ArrayList<Worker> workers;
    try {
      workers = loadW.loadFilesFrom("workers");
      for (Worker worker : workers) {
        System.out.println(worker.printInfo());
      }
    } catch (ArrayIndexOutOfBoundsException | FileNotFoundException e) {
      System.out.println(e);
    }
  }
}