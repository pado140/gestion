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
import model.Suppliers;
import model.manager.ManagerFactory;
import model.manager.ModelName;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class Nouveau_SupplierController implements Initializable {

    @FXML
    private JFXTextField nom_sup,tel_sup,mail_sup;
    
    @FXML
    private JFXTextArea adresse_sup;
    
    private Suppliers supplier;

    public Suppliers getSupplier() {
        return supplier;
    }

    public void setSupplier(Suppliers supplier) {
        this.supplier = supplier;
        if(supplier!=null){
            System.out.println("supplier:"+supplier.getNom());
            nom_sup.setText(supplier.getNom());
            adresse_sup.setText(supplier.getAddresse());
            tel_sup.setText(supplier.getTel());
            mail_sup.setText(supplier.getEMail());
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
            if(supplier!=null){
                Suppliers temp=supplier;
                temp.setNom(nom_sup.getText().trim());
                temp.setAddresse(adresse_sup.getText().trim());
                temp.setTel(tel_sup.getText().trim());
                temp.setEMail(mail_sup.getText().trim());
                temp=(Suppliers)ManagerFactory.createModel(ModelName.supplier).modifier(supplier, temp);
                    
            }else{
                Suppliers temp=new Suppliers();
                temp.setNom(nom_sup.getText().trim());
                temp.setAddresse(adresse_sup.getText().trim());
                temp.setTel(tel_sup.getText().trim());
                temp.setEMail(mail_sup.getText().trim());
                temp=(Suppliers)ManagerFactory.createModel(ModelName.supplier).ajouter( temp);
                   
                    
                    System.out.println("sup_id:"+temp.getSupId());
            }
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
