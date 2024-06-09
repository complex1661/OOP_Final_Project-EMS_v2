import ems.*;
import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;

public class EMS_GUI extends JFrame{
  public EMS_GUI(){
  }
  public static void main(String[] args) {
    JFrame f = new JFrame();
    f.setVisible(true);
    
    // 設定視窗大小、位置
    f.setTitle("人力資源管理系統");
    f.setSize(1200,900);
    centeredFrame(f);
    f.setVisible(true);
    // 關閉視窗程式
    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });   
  }
  
  // 將視窗置中
  public static void centeredFrame(JFrame objFrame){
    Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
    int iCoordX = (objDimension.width - objFrame.getWidth()) / 2;
    int iCoordY = (objDimension.height - objFrame.getHeight()) / 2;
    objFrame.setLocation(iCoordX, iCoordY); 
  } 
}