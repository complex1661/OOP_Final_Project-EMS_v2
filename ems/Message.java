package ems;
public class Message {
  private String text;
  private String messageSender;
  private CustomDate date;
  private Time timestamp;
  
  public Message() {
    text = "�L";
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
    return "�Ƶ�: " + txt + "\n"  + "�T���ǰe��" + 
      messageSender + "\n"  + "���: " + date.toString();
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