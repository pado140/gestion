/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import model.Commande;
import model.CommandeLine;
import model.Customers;
import model.Produits;
import model.manager.ManagerFactory;
import model.manager.ModelName;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class VenteController implements Initializable {
@FXML
    private Label qtotal,mtotal,totalq;
    @FXML
    private TableView<CommandeLine> table;
    
    @FXML
    private TableColumn<CommandeLine,String> article,desc;
    
    @FXML
    private TableColumn<CommandeLine,Double> total,prix;
    
    @FXML
    private TableColumn<CommandeLine,Integer> qty,line; 
    
    
    @FXML
    private TextField search;
    
    @FXML
    private DatePicker date_trans;
    
    private Produits produit;
    private Customers customer;
    
    private ObservableList<CommandeLine> CommandeLines;
    ObservableList<Integer> index;
    
    private int li=0,i=0;
    private double tot=0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        index=FXCollections.observableArrayList();
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
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
        };
        
        CommandeLines=FXCollections.observableArrayList();
        article.setCellValueFactory(cellData->cellData.getValue().getProId().getPropertyNom());
        desc.setCellValueFactory(cellData->cellData.getValue().getProId().getPropertyDescription());
        line.setCellValueFactory(cellData->cellData.getValue().getLineProperty());
        prix.setCellValueFactory(cellData->cellData.getValue().getPrixProperty());
        prix.setCellFactory(TextFieldTableCell.<CommandeLine,Double>forTableColumn(new StringConverter<Double>() {

            @Override
            public String toString(Double object) {
                return String.valueOf(object);
            }

            @Override
            public Double fromString(String string) {
                return Double.parseDouble(string);
            }
        }));
        prix.setOnEditCommit(e -> {
            CommandeLine c=e.getRowValue();
            c.setPrix(e.getNewValue());
            tot=c.getQty()*c.getPrix();
            total();
        });
        qty.setCellValueFactory(cellData->cellData.getValue().getQtyProperty());
        qty.setCellFactory(TextFieldTableCell.<CommandeLine,Integer>forTableColumn(new StringConverter<Integer>() {

            @Override
            public String toString(Integer object) {
                return String.valueOf(object);
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }
        }));
        qty.setOnEditCommit(e -> {
            CommandeLine c=e.getRowValue();
            c.setQty(e.getNewValue());
            total();
        });
        total.setCellValueFactory(cellData->cellData.getValue().total());
        
        table.setOnKeyReleased(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event) {
                System.out.println("keycode:"+event.getCode());
                if(event.getCode().equals(KeyCode.DELETE)){
                    Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("vous etes sur de bien vouloir enlever cette ligne de commande?");
                alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {CommandeLines.remove(table.getSelectionModel().getSelectedItem());
                total();});
                }
            }
            
        });
        
        table.setItems(CommandeLines); 
        
    }    
    
    
    private void showDetails(){
//        if(!codeP.getText().trim().isEmpty()){
//            produit=new Produits();
//            produit.setCode(codeP.getText().trim());
//            produit=(Produits)ManagerFactory.createModel(ModelName.produit).search(produit);
//            if(produit!=null){
//                System.out.println(produit.getNom());
//                lnom.setText(produit.getNom());
//                ldescription.setText(produit.getDescription());
//                //pimage.imageProperty().s
//                try {
//                pimage.setImage(new Image(new FileInputStream(new File(produit.getImageProuit().toArray(new image_produit[1])[0].getImagePath())),
//                pimage.getFitWidth(),pimage.getFitHeight(),false,false));
//            } catch (FileNotFoundException ex) {
//                pimage.setImage(new Image(this.getClass().getResourceAsStream("/view/image/no-image/no_image.png"),
//                        pimage.getFitWidth(),pimage.getFitHeight(),false,false));
//                //Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//              prixAchat.setText(produit.getPrix_achat().toString());
//              if(produit.getSupId()!=null)
//                  pfournisseur.setText(produit.getSupId().getNom());
//            }
//        }
    }
    @FXML
    private void reset(){
        
    }
    
    @FXML
    private void effectuerVente(){
        Commande c=new Commande();
        c.setComCode("");
        c.setCreated(LocalDate.now());
        
            c.setCommandeLineCollection(CommandeLines);
            
        Stage conf=new Stage(StageStyle.UNDECORATED);
        conf.initOwner(table.getScene().getWindow());
        conf.initModality(Modality.APPLICATION_MODAL);
        
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/remise.fxml"));
        
    try {
        Scene remiseScene=new Scene(loader.load());
        RemiseController rc=loader.getController();
        rc.setCommande(c);
        conf.setScene(remiseScene);
        conf.showAndWait();
        if(rc.getCommande()==null){
            CommandeLines.clear();
        }
    } catch (IOException ex) {
        Logger.getLogger(VenteController.class.getName()).log(Level.SEVERE, null, ex);
    }
        //ManagerFactory.createModel(ModelName.commande).ajouter(c);
    
        
    }
    
    @FXML
    private void Ventes(){
        
    }
    
    private void init(){
//        produit=null;
//        pfournisseur.setText("");
//        prixAchat.setText("");
//        lnom.setText("");
//        ldescription.setText("");
//        pimage.setImage(null);
//        codeP.setText("");
//        qtyAchat.setText("");
    }
    private void total(){
        double tot=0;
        int nbre=0;
        for(CommandeLine cl:CommandeLines){
            nbre+=cl.getQty();
            tot+=cl.getPrix()*cl.getQty();
        }
        mtotal.setText(String.valueOf(tot));
        qtotal.setText(String.valueOf(nbre));
    }
    
    @FXML
    private void filltable(){
        try{
        if(!search.getText().trim().isEmpty()){
            produit=new Produits();
            produit.setCode(search.getText().trim());
            search.setText(null);
            produit=(Produits)ManagerFactory.createModel(ModelName.produit).search(produit);
            if(produit!=null){
                if(contains(produit)){
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("vous avez deja utiliser cet article");
                alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {return ;});
            }else{
                CommandeLine cl=new CommandeLine();
                i++;
                cl.setLine(i);
                cl.setProId(produit);
                cl.setQty(1);
                cl.setPrix(produit.getPrix_vente());
                CommandeLines.add(cl);
                total();
                }
            }
        }
        }catch(NullPointerException ex){
            
        }
    }
    
    private boolean contains(Produits p){
        for(CommandeLine cl:CommandeLines){
            if(cl.getProId().getCode().equals(p.getCode()))
                return true;
        }
        return false;
    }
    
}
