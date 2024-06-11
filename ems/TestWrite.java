package ems;

public class TestWrite{
  public static void main(String[] args){
    Worker w = new PartTimeWorker(new WorkerInfo("John", "¬~¸J¤u", new CustomDate(2020,5,20)), new Time(8,0));
  
    SaveWorker workerSaver = new SaveWorker();
    workerSaver.saveFileTo(w,"workers");
  }
}