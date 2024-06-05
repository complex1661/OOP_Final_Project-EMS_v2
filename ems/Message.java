package ems;
public class Message {
  private String text;
  private String messageSender;
  private CustomDate date;
  private Time timestamp;
  
  public Message() {
    text = "";
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

}