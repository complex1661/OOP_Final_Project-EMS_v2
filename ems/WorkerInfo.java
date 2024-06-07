package ems; 
import java.util.Date;
import java.util.UUID;

public class WorkerInfo {
  private String name;
  private UUID id;
  private String positionTitle;
  private CustomDate hiredDate;
  public WorkerInfo(String n, String pos, CustomDate hd) {
    setName(n);
    setPosition(pos);
    hiredDate = hd;
    id = UUID.randomUUID();
  }
  
  public void setName(String name) throws IllegalArgumentException {
    if (name == null) throw new IllegalArgumentException("���~:����J�m�W�C");
    this.name = name;
  }
  
  public void setPosition(String pos) {
    if (pos == null) throw new IllegalArgumentException("���~:����J¾�١C");
    positionTitle = pos;
  }
  
  public String getName() {
    return name;
  }
  
  public UUID getUUID() {
    return id;
  }

  public String getId() {
    return id.toString();
  }
  
  public String getPositionTitle() {
    return positionTitle;
  }
}