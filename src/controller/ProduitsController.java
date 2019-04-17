/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import gestion.stock.GestionStock;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
public class ProduitsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Parent view;
    
    @FXML
    private AnchorPane details_pane;
    
    @FXML
    private Label nom,desc,cat;
    
    @FXML
    private ImageView imp;
    
    @FXML
    private TableView<Produits> tab_produits;
    
    @FXML
    private JFXButton newProd;
    
    @FXML
    private TableColumn<Produits,String> name_prod,description_prod,cat_prod;
    
    @FXML
    private TableColumn<Produits,Integer> qty_prod;
    
    @FXML
    private TableColumn<Produits,Integer> price_prod;
    
    @FXML
    private TableColumn<Produits,LocalDate> date_prod;
    
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
        name_prod.setCellValueFactory(cellData->cellData.getValue().getPropertyNom());
        description_prod.setCellValueFactory(cellData->cellData.getValue().getPropertyDescription());
        cat_prod.setCellValueFactory(cellData->cellData.getValue().getCategorie());
        //qty_prod.setCellValueFactory(cellData->cellData.getValue().getQty());
        date_prod.setCellValueFactory(cellData->cellData.getValue().getCreated());
        
        newProd.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                Produits p=addEdit(null);
                if(p!=null){
                    prods.add(p);
                }
            }
        });
        showDetails(null);
        
//        qty_prod.setCellValueFactory(cellData->cellData.getValue().getProperty);
//        price_prod.setCellValueFactory(cellData->cellData.getValue().getPropertyNom());
//        date_prod.setCellValueFactory(cellData->cellData.getValue().getPropertyNom());
//      
        tab_produits.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->showDetails(newValue));
        tab_produits.setItems(prods);
    }    
    @FXML
    private void editp(){
        Produits selectedItem=tab_produits.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            selectedItem=addEdit(selectedItem);
            showDetails(selectedItem);
        }
        
    }
    
    @FXML
    private void deletep(){
        Produits selectedItem=tab_produits.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            prods.remove(selectedItem);
            showDetails(null);
        }
        
    }
    private void showDetails(Produits produit){
        details_pane.setVisible(false);
        if(produit!=null){
            nom.setText(produit.getNom());
            desc.setText(produit.getDescription());
            cat.setText(produit.getCategorie().get());
            
            try {
                System.out.println(produit.getImageProuit().toArray(new image_produit[1])[0].getImagePath());
                imp.setImage(new Image(new FileInputStream(new File(produit.getImageProuit().toArray(new image_produit[1])[0].getImagePath()))));
            } catch (FileNotFoundException ex) {
                imp.setImage(new Image(this.getClass().getResourceAsStream("/view/image/no-image/no_image.png")));
                //Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            details_pane.setVisible(true);
            System.out.println(primary);
        }
    }
    private Produits addEdit(Produits p){
        try{
            Stage newProd=new Stage(StageStyle.UNDECORATED);
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
            lc.setQty(prods.size());
            newProd.showAndWait();
            return lc.getProduit();
            
        } catch (IOException ex) {
            Logger.getLogger(GestionStock.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    @FXML
    private void close(){
        primary.close();
    }
}
