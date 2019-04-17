/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Users;
import model.manager.ManagerFactory;
import model.manager.ModelName;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class LoginController implements Initializable {

    @FXML
    private TextField user_field;
    
    @FXML
    private PasswordField pass_field;
    
    private Stage stage;
    
    private Users user;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
    @FXML
    public void cancel(){
        
        System.exit(0);
    }
    
    @FXML
    public void authenticate(){
        user=new Users();
        user.setUsername(user_field.getText().trim());
        user.setPassword(pass_field.getText());
        user=(Users)ManagerFactory.createModel(ModelName.user).search(user);
        if(user.getPassword().equals(pass_field.getText())){
            user.setConnecte(true);
            this.setUser(user);
            stage.close();
        }
        else{
            this.setUser(null);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("fill all fields");
            
            alert.showAndWait();
        }
    }
}
