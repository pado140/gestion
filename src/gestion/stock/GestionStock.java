/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.stock;

import controller.LoginController;
import controller.mainController;
import controller.nouveau_userController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Users;
import model.manager.ManagerFactory;
import static model.manager.ModelName.user;
import services.view_util;

/**
 *
 * @author Padovano
 */
public class GestionStock extends Application {
    
    private Stage primaryStage;
    private Users users;
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage=primaryStage;
        
        initComponents();
    }
    
    private void initComponents(){
        try {
            FXMLLoader loadFrame=new FXMLLoader(getClass().getResource("/view/main_frame.fxml"));
            
            Parent root = loadFrame.load();
            
            Scene scene = new Scene(root);
            
            
            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            
            primaryStage.show();
            if(ManagerFactory.createModel(user).liste().size()==0){
                boolean New=showAddUser();
                if(New)
                    users=showLogin();
            }else
                users=showLogin();
            System.out.println(users);
            mainController maincont=loadFrame.getController();
            maincont.setUser(users);
            
        } catch (IOException ex) {
            Logger.getLogger(GestionStock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private boolean showAddUser(){
        try {
            Stage login=new Stage(StageStyle.DECORATED);
            login.setTitle("Initiating (new Admin User)");
            login.initOwner(primaryStage);
            FXMLLoader loadlog=new FXMLLoader(getClass().getResource("/view/newUser.fxml"));
            Parent rootLog = loadlog.load();
            
            Scene scenelog=new Scene(rootLog);
            login.setScene(scenelog);
            nouveau_userController nuc=loadlog.getController();
            nuc.setStage(login);
            login.showAndWait();
            return nuc.isSaved();
            
        } catch (IOException ex) {
            Logger.getLogger(GestionStock.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    private Users showLogin(){
        
        //LoginController lc=(LoginController)view_util.loadWindow(getClass().getResource("/view/login.fxml"), "Login", primaryStage, true);
        try {
            Stage login=new Stage(StageStyle.DECORATED);
            login.setTitle("Login");
            login.initOwner(primaryStage);
            FXMLLoader loadlog=new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent rootLog = loadlog.load();
            
            Scene scenelog=new Scene(rootLog);
            login.setScene(scenelog);
            LoginController lc=loadlog.getController();
            lc.setStage(login);
            login.showAndWait();
            return lc.getUser();
            
        } catch (IOException ex) {
            Logger.getLogger(GestionStock.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
       //return lc.getUser(); 
    }
}
