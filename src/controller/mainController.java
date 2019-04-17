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
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Users;
import observateurs.Observateurs;

/**
 *
 * @author Padovano
 */

public class mainController implements Initializable ,Observateurs{
    @FXML
    private AnchorPane produits,achats,home,Fournisseurs;
    
    @FXML
    private BorderPane main;
    
    @FXML
    private VBox left_pane;
    
    @FXML
    private ImageView user_image;
    
    private ObservableList<Node> Nodes;
    
    public static Users user;

    public Users getUser() {
        return user;
    }
    
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void setUser(Users user) {
        this.user = user;
        initComponents();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Rectangle clip=new Rectangle(100,100);
        clip.setArcWidth(100);
        clip.setArcHeight(100);
        user_image.setClip(clip);
        Nodes=left_pane.getChildren();
        for(Node n:Nodes){
            n.toString();
        }
        //left_pane.getChildren().clear();
        left_pane.setVisible(false);
    }  
    
    private void switchNode(URL url){
        //System.out.println("parent:"+content.getParent());
        Pane pane=null;
        try {
            pane = FXMLLoader.load(url);
        } catch (IOException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        main.centerProperty().set(pane);
        //content.getChildren().clear();
        //content.getChildren().add(pane);
        
        
    }
    
    @FXML
    public void switchProd(){
        switchNode(getClass().getResource("/view/Produits.fxml"));
    }
    @FXML
    public void switchSup(){
        switchNode(getClass().getResource("/view/Suppliers.fxml"));
    }
    
    @FXML
    public void switchHome(){
        switchNode(getClass().getResource("/view/home.fxml"));
    }
    
    @FXML
    public void switchAchatProduit(){
        switchNode(getClass().getResource("/view/achat_de_produit.fxml"));
    }
    
    @FXML
    public void switchStock(){
        switchNode(getClass().getResource("/view/Stock de produit.fxml"));
    }

    @FXML
    public void switchVentes(){
        switchNode(getClass().getResource("/view/Vente.fxml"));
    }
    
    @FXML
    public void switch_report_vente(){
        switchNode(getClass().getResource("/view/Vente_transaction.fxml"));
    }
    
    @FXML
    public void switch_report_achat(){
        switchNode(getClass().getResource("/view/Achats.fxml"));
    }

    @FXML
    public void switchCust(){
        switchNode(getClass().getResource("/view/Customers.fxml"));
    }

    @Override
    public void executer(Object... obs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void initComponents(){
        if(user!=null && user.isConnecte()){
        
             
             user_image.setImage(new Image("file:"+System.getProperty("user.home")+user.getImage(),user_image.getFitWidth(),
                     user_image.getFitHeight(),false,false,true));
             left_pane.requestLayout();
             left_pane.setVisible(true);
             
             //view_util.loadWindow(getClass().getResource("/view/Produits.fxml"), "Produits", stage, falsse)
            switchNode(getClass().getResource("/view/Produits.fxml"));
        
        }
    }
}
