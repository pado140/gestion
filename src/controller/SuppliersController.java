/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Suppliers;
import model.manager.ManagerFactory;
import model.manager.ModelName;
import model.manager.SupplierManager;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class SuppliersController implements Initializable {

    private SupplierManager supmod=(SupplierManager)ManagerFactory.createModel(ModelName.supplier);
    
    
    @FXML 
    private Label nom_sup,adresse_sup,tel_sup,mail_sup;
    
    @FXML 
    private TableView<Suppliers> tab_sup;
    
    @FXML
    private TableColumn<Suppliers,String> tab_nom_sup,tab_tel_sup;
    
    private Stage primary;
    private ObservableList<Suppliers> li;
            
    public Stage getPrimary() {
        return primary;
    }    

    /**
     * Initializes the controller class.
     */
    public void setPrimary(Stage primary) {            
        this.primary = primary;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tab_nom_sup.setCellValueFactory(cellData -> cellData.getValue().getNomProperty());
        tab_tel_sup.setCellValueFactory(cellData -> cellData.getValue().getTelProperty());
        li=supmod.liste();
        showDetail(null);
        tab_sup.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,newValue)->showDetail(newValue)
        );
        
        tab_sup.setItems(li);
    }    
    
    
    private void showDetail(Suppliers sup){
       
            nom_sup.getParent().setVisible(false);
        
        if(sup!=null){
        nom_sup.setText(sup.getNom());
        adresse_sup.setText(sup.getAddresse());
        mail_sup.setText(sup.getEMail());
        tel_sup.setText(sup.getTel());
        nom_sup.getParent().setVisible(true);
        }
    }
    @FXML
    private void loadNew(){
        Suppliers s=Add_edit(null);
        System.err.println("sup:"+s);
        if(s!=null){
            System.out.println("sup:"+s.getNom());
            li.add(s);
        }
    }
    
    private Suppliers Add_edit(Suppliers s){
        FXMLLoader l=new FXMLLoader(getClass().getResource("/view/Nouveau_Supplier.fxml"));
        try {
            Parent SupLoader=l.load();
            Nouveau_SupplierController supC=l.getController();
            Stage newSup=new Stage(StageStyle.UNDECORATED);
            newSup.setTitle("Nouveau Produit");
            newSup.initOwner(primary);
            
            Scene scene=new Scene(SupLoader);
            newSup.setScene(scene);
            supC.setSupplier(s);
            newSup.showAndWait();
            
            
            return supC.getSupplier();
        } catch (IOException ex) {
            Logger.getLogger(SuppliersController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @FXML
    private void edit(){
        Suppliers selectedSupplier = tab_sup.getSelectionModel().getSelectedItem();
        if (selectedSupplier != null) {
            Suppliers edit = Add_edit(selectedSupplier);
            //if (okClicked) {
                showDetail(selectedSupplier);
            //}

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(primary);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            
            alert.showAndWait();
        }
    }
}
