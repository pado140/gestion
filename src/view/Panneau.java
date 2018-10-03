/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
public class Panneau extends JPanel {
 private int posX = 0;
 private int posY = 0;
private Image im;

    public Panneau() {
        this.setVisible(true);
    }

 public void paintComponent(Graphics g){
 try {
            im=ImageIO.read(new File("src/pict/load.png"));
        } catch (IOException ex) {
            Logger.getLogger(Waiting.class.getName()).log(Level.SEVERE, null, ex);
        }
        Graphics2D gd=(Graphics2D)g;
        BufferedImage img=new BufferedImage(im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, this.getWidth(), this.getHeight());
        int posx=img.getWidth();
        int posy=img.getHeight();
        //gd.drawImage(im,(this.getWidth()-posx)/2 , (this.getWidth()-posx)/2,null);
        
        AffineTransform at=new AffineTransform();
        at.scale(0.5, 0.5);
        at.rotate(posX, posx/2, posy/2);
        //gd.dra
        gd.drawImage(im, at, null);
        //gd.drawIm
        gd.setColor(Color.red);
        //g.
        gd.drawString("Loading...",(this.getWidth()-posx)/2, (this.getWidth()-posx)/2);
 }
 public int getPosX() {
 return posX;
 }
 public void setPosX(int posX) {
 this.posX = posX;
 }
 public int getPosY() {
 return posY;
 }
 public void setPosY(int posY) {
 this.posY = posY;
 }

}