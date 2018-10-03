/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import javax.swing.JFrame;
public class Fenetre extends JFrame{
 private Panneau pan = new Panneau();

 public Fenetre(){

 this.setTitle("Animation");
 this.setSize(300, 300);
 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 this.setLocationRelativeTo(null);
 this.setContentPane(pan);
 this.setVisible(true);
 System.out.println("000".substring(3)+"999");

 go();
 }

 private void go(){

 for(;;)
 {
 int x = pan.getPosX();
 x++;
 if(x==360)
     x=0;
 pan.setPosX(x);
 
 pan.repaint();
 try {
 Thread.sleep(100);
 } catch (InterruptedException e) {
 // TODO Auto-generated catch block
 e.printStackTrace();
 }
 }
 }
}
