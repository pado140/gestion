/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Users;
import model.manager.ManagerFactory;
import model.manager.ModelName;

/**
 *
 * @author Padovano
 */
public class nouveau_userController implements Initializable{
    @FXML
    private JFXTextField nom_field,prenom_field,username_field,mail_field,tel_field,code_field;
    
    @FXML
    private ImageView image_pane;
    
    @FXML
    private JFXComboBox<String> sex_field;
    
    @FXML
    private JFXPasswordField password_field;
    
    private FileChooser  filechooser;
    private final String path="/view/image/users/default.png";
    private File file=null;
    private final String prefixPath=new File(System.getProperty("user.home")).getPath()+"/gest/users";
    public boolean saved;

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
    private Stage stage;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filechooser=new FileChooser();
        ObservableList<String> se=FXCollections.observableArrayList();
        se.addAll("Homme","Femme");
        sex_field.setItems(se);
    }
    
    @FXML
    private void cancel(){
        stage.close();
        System.exit(0);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
     @FXML
    private void load(){
        
        file=filechooser.showOpenDialog(stage);
        if(file!=null){
        if(file.exists()){
            try {
                //image.getViewport().
                image_pane.setImage(new Image(new FileInputStream(file),image_pane.getFitWidth(),image_pane.getFitHeight(),false,false));
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Nouveau_produitController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
    }
    @FXML
    private void remove(){
        image_pane.setImage(new Image(this.getClass().getResourceAsStream(path),image_pane.getFitWidth()
                ,image_pane.getFitWidth(),false,false));
        file=null;
    }
    @FXML
    private void save(){
        String nom=nom_field.getText();
        String prenom=prenom_field.getText();
        String sex=sex_field.getValue();
        String username=username_field.getText();
        String password=password_field.getText();
        String email=mail_field.getText();
        String tel=tel_field.getText();
        String code=code_field.getText();
        Image imageU=image_pane.getImage();
        
        
        if(!nom.trim().isEmpty()&&!prenom.trim().isEmpty()&&!sex.trim().isEmpty()&&
                !username.trim().isEmpty()&&!password.trim().isEmpty()&&!email.trim().isEmpty()
                &&!tel.trim().isEmpty()){
            Users users=new Users();
            users.setName(nom);
            users.setFirstname(prenom);
            users.setSex(sex);
            users.setUsername(username);
            users.setEmail(email);
            users.setPassword(password);
            users.setTel(tel);
            users.setCode(code);
            System.out.println("nom:"+users.getName());
            if(file!=null)
                users.setImage("/gest/users/"+code+file.getName().substring(file.getName().lastIndexOf(".")));
            else
                users.setImage("/gest/users/default.png");
            
            if(ManagerFactory.createModel(ModelName.user).ajouter(users)!=null){
            if(file!=null){
                try {
                    File dest=new File(prefixPath);
                    if(!dest.exists())
                        dest.mkdirs();
                    
                   
                    Files.copy(file.toPath(), new File(prefixPath+"/"+code+file.getName().substring(file.getName().lastIndexOf("."))).toPath());
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Nouveau_produitController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Nouveau_produitController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                
                    
            }else{
                if(Files.exists(new File(prefixPath+"/default.png").toPath())){
                    try {
                        Files.copy(new File(getClass().getResource(path).getFile()).toPath(), new File(prefixPath+"/default.png").toPath());
                    } catch (IOException ex) {
                        Logger.getLogger(nouveau_userController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
                setSaved(true);
                stage.close();
        }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("fill all fields");
            
            alert.showAndWait();
        }
        
    }

}
