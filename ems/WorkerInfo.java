package ems; 
import java.util.Date;
import java.io.*;

public class WorkerInfo implements Serializable{
  private static final long serialVersionUID = 1L;
  private static int idCounter = 0;
  
  private String name;
  private String id;
  private String positionTitle;
  private CustomDate hiredDate;
  public WorkerInfo(String n, String pos, CustomDate hd) {
    setName(n);
    setPosition(pos);
    hiredDate = hd;
    id = generateId();
  }
  
  // 生成 ID
  private synchronized String generateId() {
    idCounter++;
    return String.format("%07d", idCounter);
  }
  
  public void setName(String name) throws IllegalArgumentException {
    if (name == null) throw new IllegalArgumentException("錯誤:未輸入姓名。");
    this.name = name;
  }
  
  public void setPosition(String pos) {
    if (pos == null) throw new IllegalArgumentException("錯誤:未輸入職稱。");
    positionTitle = pos;
  }
  
  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }
  
  public String getPositionTitle() {
    return positionTitle;
  }
}