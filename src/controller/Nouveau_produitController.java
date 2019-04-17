/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import model.Categorie;
import model.Produits;
import model.Stocks;
import model.Suppliers;
import model.image_produit;
import model.manager.ManagerFactory;
import model.manager.ModelName;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class Nouveau_produitController implements Initializable {

    @FXML
    private ImageView image;
    
    @FXML
    private Pane pane_image;
    
    @FXML
    private JFXTextField pname,pcode,pcodebar,qty_initial,alert_qty,min_qty,prix_achat,prix_vente,pourcent,tva,fournisseur_name,fournisseur_tel
            ,fournisseur_adresse;
    
    @FXML
    private JFXComboBox<Categorie> pcategorie;
    
    @FXML
    private JFXTextArea pdescription;
    
    private FileChooser  filechooser;
    private final String path="/view/image/no-image/no_image.png",prefixbarcode="7003";
    private File file=null;
    private Stage dialog;
    private Produits produit;
    ObservableList<Categorie> categories;
    private int qtyproduit;
    private Suppliers supplier;

    public void setQty(int qty) {
        this.qtyproduit = qty;
    }
    
    

    public Produits getProduit() {
        return produit;
    }

    public void setProduit(Produits produit) {
        this.produit = produit;
        if(produit!=null){
            pname.setText(produit.getNom());
            pdescription.setText(produit.getDescription());
            pcode.setText(produit.getCode());
            pcategorie.setValue(produit.getCategorieCollection().get(0));
            //pcategorie.getSelectionModel().select(produit.getCategorieCollection().get(0));
            pcodebar.setText(produit.getCodebar());
            prix_achat.setText(produit.getPrix_achat().toString());
            prix_vente.setText(produit.getPrix_vente().toString());
            tva.setText(String.valueOf(produit.getTva().get()));
            pourcent.setText(String.valueOf(produit.getBenefice().get()));
            try{
                fournisseur_name.setText(produit.getSupId().getNom());
                fournisseur_tel.setText(produit.getSupId().getTel());
                fournisseur_adresse.setText(produit.getSupId().getAddresse());
            }catch(NullPointerException e){
                
            }
            qty_initial.setText(produit.getStocks().getQty().toString());
            alert_qty.setText(produit.getStocks().getAlert_qty().toString());
            min_qty.setText(produit.getStocks().getMinQty().toString());
            try {
                image.setImage(new Image(new FileInputStream(new File(produit.getImageProuit().toArray(new image_produit[1])[0].getImagePath()))));
            } catch (FileNotFoundException ex) {
                image.setImage(new Image(this.getClass().getResourceAsStream("/view/image/no-image/no_image.png")));
                //Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private final String prefixPath=new File(System.getProperty("user.home")).getPath()+"/gest/produits";
    //private final String pathToSave=new File(System.getProperty("user.home")).getPath();

    public Nouveau_produitController() {
        
    }
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
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
        UnaryOperator<TextFormatter.Change> filterint = new UnaryOperator<TextFormatter.Change>() {

            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {

                if (t.isReplaced()) 
                    if(t.getText().matches("[^0-9]"))
                        t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
                

                if (t.isAdded()) {if (t.getText().matches("[^0-9]")) {
                        t.setText("");
                    }
                }

                return t;
            }
        };
        alert_qty.setTextFormatter(new TextFormatter<>(filterint));
        qty_initial.setTextFormatter(new TextFormatter<>(filterint));
        min_qty.setTextFormatter(new TextFormatter<>(filterint));
        prix_achat.setTextFormatter(new TextFormatter<>(filter));
        prix_vente.setTextFormatter(new TextFormatter<>(filter));
        pourcent.setTextFormatter(new TextFormatter<>(filter));
        filechooser=new FileChooser();
        filechooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("image", "*.png","*.jpg","*.gif"));
        categories=ManagerFactory.createModel(ModelName.categorie).liste();
        pcategorie.setItems(categories);
        image.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton()==MouseButton.PRIMARY){
                load();
            }
        });
        
    }    
    
    @FXML
    private void load(){
        
        Window st=image.getScene().getWindow();
        file=filechooser.showOpenDialog(st);
        if(file!=null)
            if(file.exists()){
                try {
                    //image.getViewport().
                    image.setImage(new Image(new FileInputStream(file),pane_image.getWidth()-10,pane_image.getHeight()-10,false,false));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Nouveau_produitController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
    @FXML
    private void remove(){
        image.setImage(new Image(this.getClass().getResourceAsStream("/view/image/no-image/no_image.png"),pane_image.getWidth()-10,pane_image.getHeight()-10,false,false));
        file=null;
    }
    
    @FXML
    private void close(){
        dialog.close();
    }
    @FXML
    private void save(){
        String nom=pname.getText();
        String desc=pdescription.getText();
        String code=pcode.getText();
        Categorie cat=pcategorie.getValue();
        Image imagep=image.getImage();
        String barcode=pcodebar.getText();
        double prix_a=(prix_achat.getText().trim().isEmpty()?0:Double.parseDouble(prix_achat.getText()));
        double prix_v=(prix_vente.getText().trim().isEmpty()?0:Double.parseDouble(prix_vente.getText()));        
        
        if(canSave()){
            if(produit==null)
                produit=new Produits();
            produit.setNom(nom);
            produit.setCode(code);
            produit.setDescription(desc);
            produit.getCategorieCollection().add(cat);
            produit.setPrix_achat(prix_a);
            produit.setPrix_vente(prix_v);
            produit.setBenefice(Double.parseDouble(pourcent.getText().isEmpty()?"0":pourcent.getText()));
            produit.setTva(Double.parseDouble(tva.getText().isEmpty()?"0":tva.getText()));
            produit.setSupIdProperty(supplier);
            produit.setCodebar(barcode);
            image_produit imp=new image_produit();
            imp.setDefaut(true);
            
            imp.setProduit(produit);
            imp.setImagePath("");
            if(file!=null){
                InputStream is=null;
                OutputStream os=null;
                imp.setImagePath(code);
                try {
                    is=new FileInputStream(file);
                    File dest=new File(prefixPath+"/"+code);
                    Files.deleteIfExists(new File(prefixPath+"/"+code+"/default"+file.getName().substring(file.getName().lastIndexOf("."))).toPath());
                    if(!dest.exists())
                        dest.mkdirs();
                    if(produit.getId()!=0)
                        dest.renameTo(new File(prefixPath+"/"+code));
                    imp.setImagePath("default"+file.getName().substring(file.getName().lastIndexOf(".")));
                    imp.setImage(is);
                    
                        
                    Files.copy(file.toPath(), new File(prefixPath+"/"+code+"/default"+file.getName().substring(file.getName().lastIndexOf("."))).toPath());
                   
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Nouveau_produitController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Nouveau_produitController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                
                    
            }
            if(file!=null)
                produit.getImageProuit().add(imp);
                
            produit=(Produits)ManagerFactory.createModel(ModelName.produit).save(produit);
           
            if(produit!=null){
                int qty=qty_initial.getText().trim().isEmpty()?0:Integer.parseInt(qty_initial.getText().trim()),
                        alert=alert_qty.getText().trim().isEmpty()?0:Integer.parseInt(alert_qty.getText().trim()),
                        min=min_qty.getText().trim().isEmpty()?0:Integer.parseInt(min_qty.getText().trim());
                Stocks stock=produit.getStocks();
                if(stock==null)
                    stock=new Stocks();
                stock.setProduit(produit);
                stock.setQty(qty);
                stock.setAlert_qty(alert);
                stock.setMinQty(min);
                stock.setPump(prix_a);
                stock.setPrixvente(prix_v);
                stock=(Stocks)ManagerFactory.createModel(ModelName.stock).save(stock);
                produit.setStocks(stock);
            }
            dialog.close();
        
        }
        
    }

    public Stage getDialog() {
        return dialog;
    }

    @FXML
    private void new_category(){
        try {
            Stage newCat=new Stage(StageStyle.UNDECORATED);
            newCat.setTitle("Nouvelle Categorie");
            newCat.initOwner(dialog);
            newCat.initModality(Modality.APPLICATION_MODAL); 
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/categorie.fxml"));
            //view=loader.load();
            
        
            Scene scenelog=new Scene(loader.load());
            newCat.setScene(scenelog);
            CategorieController nbc=loader.getController();
            nbc.setDialog(newCat);
            
            newCat.showAndWait();
            if(nbc.getCategory()!=null)
                categories.add(nbc.getCategory());
        } catch (IOException ex) {
            Logger.getLogger(Nouveau_produitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    private void calcul_vente(){
        prix_vente.setText(String.valueOf(Double.parseDouble(prix_achat.getText())*(1+(Double.parseDouble(pourcent.getText().isEmpty()?"0":pourcent.getText())/100))));
    }
    
    @FXML
    private void calcul_vente_tva (){
        prix_vente.setText(String.valueOf((Double.parseDouble(prix_achat.getText())*(1+(Double.parseDouble(pourcent.getText().isEmpty()?"0":pourcent.getText())/100)))*
                (1+(Double.parseDouble(tva.getText().isEmpty()?"0":tva.getText())/100))));
    }
    public void setDialog(Stage dialog) {
        this.dialog = dialog;
    }
    
    private boolean canSave(){
        String erreur="";
        if(pname.getText().trim().isEmpty())
            erreur+="veuillez indiquer des valeur pour le champ Nom \n";
        if(pcode.getText().trim().isEmpty())
            erreur+="veuillez indiquer des valeur pour le champ Code\n";
        if(pdescription.getText().trim().isEmpty())
            erreur+="veuillez indiquer des valeur pour le champ description \n";
        if(pcategorie.getValue()==null)
            erreur+="veuillez choisir une categorie \n";
        System.out.println("erreur:"+erreur);
        if(erreur.trim().isEmpty())
            return true;
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner((Stage)this.pname.getScene().getWindow());
            alert.setTitle("Fill all fields");
            alert.setHeaderText("Error");
            alert.setContentText(erreur);
            alert.showAndWait();
        }
        
         return false;       
        
    }
    @FXML
    private void generate_code(){
        int nb=8;
        int ln=qtyproduit;
        if(ln>10000)
            nb=8-4;
        if(ln>1000)
            nb=8-3;
        if(ln>100)
            nb=8-2;
        if(ln>10)
            nb=8-1;
        String val="";
        Random rd=new Random();
        for(int i=0;i<nb;i++){
            val+=rd.nextInt(9);
        }
        pcodebar.setText(prefixbarcode+val+ln);
    }
    
    @FXML
    private void load_fournisseur(){
        try {
            Stage newCat=new Stage(StageStyle.UNDECORATED);
            newCat.setTitle("Nouvelle Categorie");
            newCat.initOwner(dialog);
            newCat.initModality(Modality.APPLICATION_MODAL); 
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/Fourisseur_select.fxml"));
            //view=loader.load();
            
        
            Scene scenelog=new Scene(loader.load());
            newCat.setScene(scenelog);
            Fourisseur_selectController nbc=loader.getController();
            nbc.setDialog(newCat);
            
            newCat.showAndWait();
            if(nbc.getSupplier()!=null){
                supplier=nbc.getSupplier();
                
                System.out.println("ok");
                fournisseur_name.setText(supplier.getNom());
                fournisseur_tel.setText(supplier.getTel());
                fournisseur_adresse.setText(supplier.getAddresse());
            }
        } catch (IOException ex) {
            Logger.getLogger(Nouveau_produitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
