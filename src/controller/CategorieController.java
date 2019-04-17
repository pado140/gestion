/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.Categorie;
import model.manager.ManagerFactory;
import model.manager.ModelName;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class CategorieController implements Initializable {

    private Stage dialog;
    private Categorie category;
    @FXML
    private JFXTextField name_field;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public Stage getDialog() {
        return dialog;
    }

    public void setDialog(Stage dialog) {
        this.dialog = dialog;
    }

    public Categorie getCategory() {
        return category;
    }

    public void setCategory(Categorie category) {
        this.category = category;
    }
    
    @FXML
    private void save(){
        if(!name_field.getText().trim().isEmpty()){
            Categorie cat=new Categorie();
            cat.setCategorie(name_field.getText());
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Please Confirm");
            alert.setContentText("Etes vous sur de bien vouloir enregistrer cette nouvelle categorie");
            if(ManagerFactory.createModel(ModelName.categorie).search(cat)!=null){
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setHeaderText("Warning");
                alert.setContentText("Cette categorie existe");
                alert.showAndWait();
            }else{
                alert.showAndWait().filter(reponse->reponse==ButtonType.OK)
                        .ifPresent(reponse->exec(cat));
            }
            dialog.close();
        }
    }
    
    @FXML
    private void close(){
        setCategory(null);
        dialog.close();
    }
    
    private void exec(Categorie cat){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Information");
            alert.setContentText("Une nouvelle categorie vient d'être ajouter");
        cat=(Categorie)ManagerFactory.createModel(ModelName.categorie).ajouter(cat);
        if(cat!=null){
              setCategory(cat);
              alert.showAndWait();
        }
    }
}
