/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Commande;
import model.Customers;
import model.manager.ManagerFactory;
import model.manager.ModelName;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class RemiseController implements Initializable {
    @FXML
    private Label montant;
    
    @FXML
    private TextField remis,monnaie;

    private double total;
    private Commande commande;
    private Customers customers;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {

            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {

                if (t.isReplaced()) 
                    if(t.getText().matches("[^0-9]"))
                        t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
                

                if (t.isAdded()) {
                    if (t.getControlText().contains(".")) {
                        if (t.getText().matches("[^0-9]")) {
                            t.setText("");
                        }
                    } else if (t.getText().matches("[^0-9.]")) {
                        t.setText("");
                    }
                }

                return t;
            }
        };
        remis.setTextFormatter(new TextFormatter<>(filter));
    }    
    
    @FXML
    private void calcul(){
        double val=0;
        try{
            val=Double.parseDouble(remis.getText());
        }catch(NumberFormatException e){
            val=0;
        }
        monnaie.setText(String.valueOf(val-total));
    }
    
    @FXML
    private void print_ok(){
        
    }
    
    @FXML
    private void valide(){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.initOwner(monnaie.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setContentText("Transaction reussie!");
        ManagerFactory.createModel(ModelName.commande).ajouter(getCommande());
        commande=null;
        alert.showAndWait();
    }
    
    @FXML
    private void back(){
        ((Stage)monnaie.getScene().getWindow()).close();
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
        total=commande.montant();
        montant.setText(String.valueOf(total));
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }
    
    
}
