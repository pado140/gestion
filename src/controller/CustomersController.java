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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Customers;
import model.Suppliers;
import model.manager.ManagerFactory;
import model.manager.ModelName;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class CustomersController implements Initializable {
@FXML 
    private Label nom,adresse,tel,mail;
    
    @FXML 
    private TableView<Customers> customer_tab;
    
    @FXML
    private TableColumn<Customers,String> name,adress,phone,mailing;
    
    private Stage primary;
    @FXML
    private AnchorPane customer_detail;
    private ObservableList<Customers> customers;
            
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
        customers=ManagerFactory.createModel(ModelName.customer).liste();
        name.setCellValueFactory(cellData -> cellData.getValue().getNomProperty());
        adress.setCellValueFactory(cellData -> cellData.getValue().getAdresseProperty());
        mailing.setCellValueFactory(cellData -> cellData.getValue().geteMailProperty());
        phone.setCellValueFactory(cellData -> cellData.getValue().getTelProperty());
        showDetail(null);
        customer_tab.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,newValue)->showDetail(newValue)
        );
        
        customer_tab.setItems(customers);
        System.out.println("ok opne");
    }    
    
    
    private void showDetail(Customers cust){
        customer_detail.setVisible(false);
        if(cust!=null){
            customer_detail.setVisible(true);
        nom.setText(cust.getNom());
        adresse.setText(cust.getAdresse());
        mail.setText(cust.getEMail());
        tel.setText(cust.getTel());
        
        }
    }
    @FXML
    private void new_customer(){
        Customers cust=Add_edit(null);
        if(cust!=null){
            customers.add(cust);
        }
    }
    
    private Customers Add_edit(Customers cust){
        FXMLLoader l=new FXMLLoader(getClass().getResource("/view/customer_new.fxml"));
        try {
            Parent Customerload=l.load();
            Customer_newController custEdit=l.getController();
            Stage New=new Stage(StageStyle.UNDECORATED);
            New.setTitle("Nouveau Produit");
            New.initOwner((Stage)customer_tab.getScene().getWindow());
            
            Scene scene=new Scene(Customerload);
            New.setScene(scene);
            custEdit.setCustomer(cust);
            New.showAndWait();
            
            
            return custEdit.getCustomer();
        } catch (IOException ex) {
            Logger.getLogger(SuppliersController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @FXML
    private void edit_customer(){
        Customers selectedCustomer = customer_tab.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            Customers edit = Add_edit(selectedCustomer);
            //if (okClicked) {
                showDetail(selectedCustomer);
            //}

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner((Stage)customer_tab.getScene().getWindow() );
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            
            alert.showAndWait();
        }
    }
}
