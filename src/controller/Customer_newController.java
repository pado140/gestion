/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Customers;
import model.manager.ManagerFactory;
import model.manager.ModelName;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class Customer_newController implements Initializable {
@FXML
    private JFXTextField nom_sup,tel_sup,mail_sup;
    
    @FXML
    private JFXTextArea adresse_sup;
    
    private Customers customer;

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
        if(customer!=null){
            System.out.println("supplier:"+customer.getNom());
            nom_sup.setText(customer.getNom());
            adresse_sup.setText(customer.getAdresse());
            tel_sup.setText(customer.getTel());
            mail_sup.setText(customer.getEMail());
        }
    }
    /**
     * Initializes the controller class.
     */
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void save(){
        if(canSave()){
            Customers temp=new Customers();
            if(customer!=null){
                temp=customer;
                temp.setNom(nom_sup.getText().trim());
                temp.setAdresse(adresse_sup.getText().trim());
                temp.setTel(tel_sup.getText().trim());
                temp.setEMail(mail_sup.getText().trim());
                temp=(Customers)ManagerFactory.createModel(ModelName.customer).modifier(customer, temp);
                    
            }else{
                
                temp.setNom(nom_sup.getText().trim());
                temp.setAdresse(adresse_sup.getText().trim());
                temp.setTel(tel_sup.getText().trim());
                temp.setEMail(mail_sup.getText().trim());
                temp=(Customers)ManagerFactory.createModel(ModelName.customer).ajouter( temp);
                   
                    
                    System.out.println("Cust_id:"+temp.getId());
            }
            customer=temp;
            close();
        }
        
    }
    
    @FXML
    private void close(){
        ((Stage)this.mail_sup.getScene().getWindow()).close();
    }
    private boolean canSave(){
        String erreur="";
        if(nom_sup.getText().trim().isEmpty())
            erreur+="veuillez indiquer des valeur pour le champ Nom \n";
        if(adresse_sup.getText().trim().isEmpty())
            erreur+="veuillez indiquer des valeur pour le champ Adresse\n";
        if(tel_sup.getText().trim().isEmpty())
            erreur+="veuillez indiquer des valeur pour le champ tel \n";
        if(mail_sup.getText().trim().isEmpty())
            erreur+="veuillez indiquer des valeur pour le champ E mail \n";
        System.out.println("erreur:"+erreur);
        if(erreur.trim().isEmpty())
            return true;
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner((Stage)this.mail_sup.getScene().getWindow());
            alert.setTitle("Fill all fields");
            alert.setHeaderText("Error");
            alert.setContentText(erreur);
            alert.showAndWait();
        }
        
         return false;       
        
    }
}
