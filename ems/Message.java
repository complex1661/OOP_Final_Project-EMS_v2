package ems;
import java.io.Serializable;

public class Message implements Serializable{
  private static final long serialVersionUID = 1L;

  private String text;
  private String messageSender;
  private CustomDate date;
  private Time timestamp;
  
  public Message() {
    text = "無";
    messageSender = "System";
    date = new CustomDate();
    timestamp = new Time();
  }
  
  public Message(String txt, String msr) {
    text = txt;
    messageSender = msr;
    date = new CustomDate();
    timestamp = new Time();
  }
  
  public void modifyText(String txt) {
    text = txt;
    timestamp = new Time();
  }
  
  public void modifyMessageSender(String msr) {
    messageSender = msr;
    timestamp = new Time();
  }
  
  public String getDetail() {
    String txt = text == null ? "" : text;
    return "備註: " + txt + "\n"  + "訊息傳送者" + 
      messageSender + "\n"  + "日期: " + date.toString();
  }
  
  public String getMessageSender() {
    return messageSender;
  }
  
  public String getText() {
    return text;
  }
  
  public String getMessageDate() {
    return date.toString();
  }
}