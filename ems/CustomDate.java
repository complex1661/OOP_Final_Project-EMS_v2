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
    if (!isValidDate()) throw new IllegalArgumentException("���~: ���X�z���~��C");
  }
  
  public CustomDate(int y, int m, int d) {
    year = y;
    month = m;
    day = d;
    if (!isValidDate()) throw new IllegalArgumentException("���~: ���X�z������C");
  }
  
  public CustomDate(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date); 
    year = c.get(Calendar.YEAR);
    month = c.get(Calendar.MONTH) + 1;
    day = c.get(Calendar.DAY_OF_MONTH);
  }
  
  // �ϥΤ���ӹ�{�ۭq�q TreeMap �����
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
  
  
  // ����: year ���X�z -> null 2 3, 3001 5 6, 1799 5 8
  // ����: month ���X�z -> 2005 null 3, 2005 0 8, 2005 13 5
  // ����: day ���X�z -> �̷ӸӦ~�Ӥ�P�_�O�_���o��, >31, <1
  public boolean isValidDate() {
    if (year == null) return false;
    if (year.intValue() > 3000 || year.intValue() < 1800) return false;
    if (month == null) return false; 
    if (month.intValue() < 1 || month.intValue() > 13) return false;
    if (day == null) return true;

    Calendar c = Calendar.getInstance();
    // �����Y��]�m
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
    String temp = year + " �~ " + month + " �� ";
    if (day == null) return temp;
    else return temp + day + " �� ";
  }
} 