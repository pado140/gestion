/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import model.Produits;
import model.image_produit;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class Produit_infosController implements Initializable {

    private Produits produits;
    
    @FXML
    private ImageView im;
    
    @FXML
    private Label identification;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Rectangle clip=new Rectangle(im.getFitWidth(), im.getFitHeight());
        clip.setArcWidth(15);
        clip.setArcHeight(15);
        //clip.se
        im.setClip(clip);
        
        
        
    }    

    public Produits getProduits() {
        return produits;
    }

    public void setProduits(Produits produits) {
        this.produits = produits;
        identification.setText(produits.getNom());
            
            try {
                im.setImage(new Image(new FileInputStream(new File(produits.getImageProuit().toArray(new image_produit[1])[0].getImagePath())),
                        im.getFitWidth(),im.getFitHeight(),false,false));
            } catch (FileNotFoundException ex) {
                im.setImage(new Image(this.getClass().getResourceAsStream("/view/image/no-image/no_image.png"),im.getFitWidth(),im.getFitHeight(),false,false));
                //Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @FXML
    private void showproduit(){
        System.out.println(produits);
    }
}
