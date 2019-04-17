/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gestion.stock.GestionStock;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Produits;
import model.image_produit;
import model.manager.ManagerFactory;
import model.manager.ModelName;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class ProduitsController1 implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Parent view;
    
    @FXML
    private FlowPane flow_product;
    
    @FXML
    private Label Identificationt;
    
    @FXML
    private ImageView imp;
    
    ObservableList<Produits> prods;
    
    private Stage primary;

    public Stage getPrimary() {
        return primary;
    }

    public void setPrimary(Stage primary) {
        this.primary = primary;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        prods=ManagerFactory.createModel(ModelName.produit).liste();
        
        for(Produits pr:prods){
            flow_product.getChildren().add(showDetails(pr));
            
        }
    }    
        
    private AnchorPane showDetails(Produits produit){
        AnchorPane paneprod=null;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/produit_infos.fxml"));
        try {
            paneprod=loader.load();
            Produit_infosController lc=loader.getController();
            lc.setProduits(produit);
        } catch (IOException ex) {
            Logger.getLogger(ProduitsController1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paneprod;
    }
    private Produits addEdit(Produits p){
        try{
            Stage newProd=new Stage(StageStyle.DECORATED);
            newProd.setTitle("Nouveau Produit");
            newProd.initOwner(primary);
            newProd.initModality(Modality.APPLICATION_MODAL); 
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/nouveau_produit.fxml"));
            view=loader.load();
        
            Scene scenelog=new Scene(view);
            newProd.setScene(scenelog);
            Nouveau_produitController lc=loader.getController();
            lc.setDialog(newProd);
            lc.setProduit(p);
            newProd.showAndWait();
            return lc.getProduit();
            
        } catch (IOException ex) {
            Logger.getLogger(GestionStock.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
}
