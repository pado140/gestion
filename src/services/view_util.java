/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import controller.mainController;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Padovano
 */
public class view_util {
    private static String ICON_IMAGE_LOC="";
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    
    public static void setStageIcon(Stage stage) {
        //stage.getIcons().add(new Image(ICON_IMAGE_LOC));
    }

    public static Object loadWindow(URL loc, String title, Stage parentStage,boolean modal) {
        Object controller = null;
        try {
            System.out.println("url:"+loc.getFile());
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            
            Stage stage = null;
            if(modal){
                stage=new Stage(StageStyle.DECORATED);
                stage.initOwner(parentStage);
            }else{
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            }
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            
            //setStageIcon(stage);
            if(modal){
               stage.showAndWait();
            }else
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controller;
    }

    public static Object loadPane(URL loc, String title, BorderPane main,int position) {
        Object controller = null;
        try {
            System.out.println("url:"+loc.getFile());
            FXMLLoader loader = new FXMLLoader(loc);
            Pane parent = loader.load();
            controller = loader.getController();
            switch(position){
                case -1:
                    main.leftProperty().set(parent);
                break;
                case 1:
                    main.rightProperty().set(parent);
                break;
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controller;
    }
    
    public static String formatDateTimeString(Date date) {
        return DATE_TIME_FORMAT.format(date);
    }

    public static String formatDateTimeString(Long time) {
        return DATE_TIME_FORMAT.format(new Date(time));
    }

    public static String getDateString(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static boolean validateEmailAddress(String emailID) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(emailID).matches();
    }
}
