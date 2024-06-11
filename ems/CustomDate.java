package ems;
import java.util.Date;
import java.util.Calendar;
import java.io.*;

public class CustomDate implements Comparable<CustomDate>,Serializable{
  private static final long serialVersionUID = 1L;
  
  private Integer year; 
  private Integer month;
  private Integer day; 
  
  public CustomDate() {
    Calendar c = Calendar.getInstance();
    year = c.get(Calendar.YEAR);
    month = c.get(Calendar.MONTH) + 1; 
    day = c.get(Calendar.DATE);
  }
  
  public CustomDate(Integer y, Integer m) {
    year = y;
    month = m;
    day = null;
    if (!isValidDate()) throw new IllegalArgumentException("錯誤: 不合理的年月。");
  }
  
  public CustomDate(int y, int m, int d) {
    year = y;
    month = m;
    day = d;
    if (!isValidDate()) throw new IllegalArgumentException("錯誤: 不合理的日期。");
  }
  
  public CustomDate(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date); 
    year = c.get(Calendar.YEAR);
    month = c.get(Calendar.MONTH) + 1;
    day = c.get(Calendar.DAY_OF_MONTH);
  }
  
  // 使用比較來實現自訂義 TreeMap 的鍵值
  @Override 
  public int compareTo(CustomDate cd) {
    if (!this.year.equals(cd.year)) return this.year - cd.year;
    if (!this.month.equals(cd.month)) return this.month - cd.month;
    if (this.day == null && cd.day == null) return 0;
    if (this.day == null && cd.day != null) return -1;
    if (this.day != null && cd.day == null) return 1;
    // System.out.println("Compare:" + this.year + " " + this.month + " " + this.day + " " +  cd.year + " " + cd.month + " " + cd.day);
    return this.day - cd.day;
  }
  
  
  public Integer getYear() {
    return year;
  }
  
  public Integer getMonth() {
    return month;
  }
  
  public Integer getDay() {
    return day;
  }
  
  
  // 失效: year 不合理 -> null 2 3, 3001 5 6, 1799 5 8
  // 失效: month 不合理 -> 2005 null 3, 2005 0 8, 2005 13 5
  // 失效: day 不合理 -> 依照該年該月判斷是否有這天, >31, <1
  public boolean isValidDate() {
    if (year == null) return false;
    if (year.intValue() > 3000 || year.intValue() < 1800) return false;
    if (month == null) return false; 
    if (month.intValue() < 1 || month.intValue() > 13) return false;
    if (day == null) return true;

    Calendar c = Calendar.getInstance();
    // 取消嚴格設置
    c.setLenient(false);
    c.set(Calendar.YEAR, year);
    c.set(Calendar.MONTH, month - 1);
    c.set(Calendar.DAY_OF_MONTH, day);
    try {
      c.getTime();
      return true;
    } catch (Exception e) {
      return false;
    }
  }
  
  public String toString() {
    String temp = year + " 年 " + month + " 月 ";
    if (day == null) return temp;
    else return temp + day + " 日 ";
  }
} 