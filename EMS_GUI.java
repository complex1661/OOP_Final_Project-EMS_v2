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
    
    // �]�w�����j�p�B��m
    f.setTitle("�H�O�귽�޲z�t��");
    f.setSize(1200,900);
    centeredFrame(f);
    f.setVisible(true);
    // ���������{��
    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });   
  }
  
  // �N�����m��
  public static void centeredFrame(JFrame objFrame){
    Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
    int iCoordX = (objDimension.width - objFrame.getWidth()) / 2;
    int iCoordY = (objDimension.height - objFrame.getHeight()) / 2;
    objFrame.setLocation(iCoordX, iCoordY); 
  } 
}