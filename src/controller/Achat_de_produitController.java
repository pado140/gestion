/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import model.Achat_line;
import model.Achats;
import model.Produits;
import model.Suppliers;
import model.image_produit;
import model.manager.ManagerFactory;
import model.manager.ModelName;
import org.controlsfx.control.textfield.CustomTextField;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class Achat_de_produitController implements Initializable {

    @FXML
    private CustomTextField codeP; 
    
    @FXML
    private ContextMenu ctmenu;
    
    @FXML
    private Label lnom,ldescription,total;
    
    @FXML
    private ImageView pimage;
    
    @FXML
    private TableView<Achat_line> tab;
    
    @FXML
    private TableColumn<Achat_line,String> nom,detail;
    
    @FXML
    private TableColumn<Achat_line,Double> total_tab,prixu;
    
    @FXML
    private TableColumn<Achat_line,Integer> line,qty; 
    
    @FXML
    private JFXTextField prixAchat,qtyAchat,pfournisseur;
    
    @FXML
    private TextField code_achat,transac_no;
    
    @FXML
    private DatePicker date_trans;
    
    private Produits produit;
    //private Suppliers fournisseur;
    
    private ObservableList<Achat_line> tabAchats;
    private int li=0;
    private double tot=0;
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
        Rectangle clip=new Rectangle(pimage.getFitWidth(), pimage.getFitHeight());
        clip.setArcWidth(15);
        clip.setArcHeight(15);
        pimage.setClip(clip);
        prixAchat.setTextFormatter(new TextFormatter<>(filter));
        qtyAchat.setTextFormatter(new TextFormatter<>(filter));
        tabAchats=FXCollections.observableArrayList();
        nom.setCellValueFactory(cellData->cellData.getValue().getProId().getPropertyNom());
        detail.setCellValueFactory(cellData->cellData.getValue().getProId().getPropertyDescription());
        line.setCellValueFactory(cellData->cellData.getValue().getLine().asObject());
        prixu.setCellValueFactory(cellData->cellData.getValue().getPrixUProperty().asObject());
        qty.setCellValueFactory(cellData->cellData.getValue().getQtyProperty().asObject());
        total_tab.setCellValueFactory(cellData->cellData.getValue().getTotalProperty().asObject());
        total.setText(String.valueOf(tot));
        codeP.setOnKeyReleased((KeyEvent event) -> {
            if(event.getCode()==KeyCode.ENTER){
                showDetails();
            }
        });
        tab.setOnMouseClicked((MouseEvent event) -> {
            if(!tab.getItems().isEmpty())
                tab.setContextMenu(ctmenu);
            else
                tab.setContextMenu(null);
        });
        tab.setItems(tabAchats); 
        ctmenu.getItems().get(0).setOnAction((ActionEvent event) -> {
            tabAchats.removeAll(tab.getSelectionModel().getSelectedItems());
        });
    }    
    
    
    private void showDetails(){
        if(!codeP.getText().trim().isEmpty()){
            produit=new Produits();
            produit.setCode(codeP.getText().trim());
            produit=(Produits)ManagerFactory.createModel(ModelName.produit).search(produit);
            if(produit!=null){
                System.out.println(produit.getNom());
                lnom.setText(produit.getNom());
                ldescription.setText(produit.getDescription());
                //pimage.imageProperty().s
                try {
                pimage.setImage(new Image(new FileInputStream(new File(produit.getImageProuit().toArray(new image_produit[1])[0].getImagePath())),
                pimage.getFitWidth(),pimage.getFitHeight(),false,false));
            } catch (FileNotFoundException ex) {
                pimage.setImage(new Image(this.getClass().getResourceAsStream("/view/image/no-image/no_image.png"),
                        pimage.getFitWidth(),pimage.getFitHeight(),false,false));
                //Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
            }
              prixAchat.setText(produit.getPrix_achat().toString());
              if(produit.getSupId()!=null)
                  pfournisseur.setText(produit.getSupId().getNom());
            }
        }
    }
    @FXML
    private void reset(){
        
    }
    
    @FXML
    private void panier(){
        if(isOk()){
            li++;
            Achat_line acl=new Achat_line();
            acl.setLineProperty(li);
            acl.setPrixUProperty(Double.parseDouble(prixAchat.getText().trim()));
            acl.setQtyProperty(Integer.parseInt(qtyAchat.getText().trim()));
            acl.setProIdProperty(produit);
            //acl.setSupIdProperty(pfournisseur.getSelectionModel().getSelectedItem());
            tot+=acl.getTotalProperty().get();
            total.setText(String.valueOf(tot));
            tabAchats.add(acl);
            init();
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error Fatal");
            alert.setContentText("veuillez remplir tous les champs");
            alert.initOwner(tab.getScene().getWindow());
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }
        
    }
    private void init(){
        produit=null;
        pfournisseur.setText("");
        prixAchat.setText("");
        lnom.setText("");
        ldescription.setText("");
        pimage.setImage(null);
        codeP.setText("");
        qtyAchat.setText("");
    }
    private boolean isOk(){
        boolean ok=false;
        return (produit!=null && !prixAchat.getText().trim().isEmpty()&& !pfournisseur.getText().isEmpty());
            
    }
    
    @FXML
    private void valider(){
        Alert alert=new Alert(Alert.AlertType.ERROR);
            String content="veuillez remplir les champs";
            alert.initOwner(tab.getScene().getWindow());
            alert.initModality(Modality.APPLICATION_MODAL);
        if(!tabAchats.isEmpty()&&!code_achat.getText().trim().isEmpty()&&!transac_no.getText().trim().isEmpty()
                &&date_trans.getValue()!=null){
            Achats achat=new Achats();
            achat.setCode(code_achat.getText().trim());
            achat.setTransac_no(transac_no.getText().trim());
            achat.setDate(date_trans.getValue());
            achat.setLineItems(tabAchats);
            
            if(ManagerFactory.createModel(ModelName.achat).ajouter(achat)!=null){
                alert.setTitle("Success");
                alert.setAlertType(Alert.AlertType.INFORMATION);
                content="votre achat a ete enregistre avec succes";
                tabAchats.clear();
                code_achat.setText("");
                transac_no.setText("");
                date_trans.setValue(null);
            }else{
                    content="erreur fatal";
                }
            
        }
        alert.setContentText(content);
        alert.showAndWait();
        
    }
    
}
