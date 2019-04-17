/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import model.Commande;
import model.CommandeLine;
import model.Customers;
import model.Produits;
import model.manager.CommandeManager;
import model.manager.ManagerFactory;
import model.manager.ModelName;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class Vente_transactionController implements Initializable {

    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

@FXML
    private Label com,mtnt,dte;
    @FXML
    private TableView<CommandeLine> table;
    
    @FXML
    private TableView<Commande> tab_vente;
    
    @FXML
    private TableColumn<CommandeLine,String> article;
    
    @FXML
    private TableColumn<Commande,String> code,user;
    
    @FXML
    private TableColumn<Commande,LocalDate> datevente;
    
    
    @FXML
    private TableColumn<Commande,Double> total;
    
    @FXML
    private TableColumn<CommandeLine,Double> tot,prix;
    
    @FXML
    private TableColumn<CommandeLine,Integer> qty; 
    
    private Produits produit;
    private Customers customer;
    
    private ObservableList<Commande> Commandes;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Commandes=ManagerFactory.createModel(ModelName.commande).liste();
        for(Commande c:Commandes){
            ObservableList<CommandeLine> cl=((CommandeManager)ManagerFactory.createModel(ModelName.commande)).commandeLines(c.getId());
            c.setCommandeLineCollection(cl);
        }
        article.setCellValueFactory(cellData->cellData.getValue().getProId().getPropertyNom());
        prix.setCellValueFactory(cellData->cellData.getValue().getPrixProperty());
        datevente.setCellValueFactory(cellData->cellData.getValue().getCreatedProperty());
        qty.setCellValueFactory(cellData->cellData.getValue().getQtyProperty());
        total.setCellValueFactory(cellData->cellData.getValue().montantProperty());
        code.setCellValueFactory(cell->cell.getValue().getComCodeProperty());
        tot.setCellValueFactory(cell->cell.getValue().total());
        user.setCellValueFactory(cell->{
            return cell.getValue().getUser().getUsernameProperty();
        });
        tab_vente.getSelectionModel().selectedItemProperty().
                addListener((ObservableValue<? extends Commande> observable, Commande oldValue, Commande newValue) -> {
            showDetails(newValue);
        });
        
        tab_vente.setItems(Commandes); 
        
        
    }    
    
    
    private void showDetails(Commande co){
        if(co!=null){
            ObservableList<CommandeLine> cl=((CommandeManager)ManagerFactory.createModel(ModelName.commande)).commandeLines(co.getId());
            table.setItems(cl);
            com.setText(co.getComCode());
            dte.setText(co.getCreated().format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }
    
    private void calcul(){
        double t=0;
        for(Commande c:Commandes)
            t+=c.montant();
        
        mtnt.setText(String.valueOf(t));
    }
    
    
    @FXML
    private void export(){
        FileChooser file=new FileChooser();
        file.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Spread Sheet", "*.xlsx","*.xls"));
        File f=file.showSaveDialog(mtnt.getScene().getWindow());
        if(f!=null){
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("rapport de Vente");
            XSSFSheet sheet1 = wb.createSheet("rapport de Vente(details)");
            Row row1 = sheet.createRow(0);
        // Create a cell and put a value in it.
        Cell cell10 = row1.createCell(0);
        int l=7;
        int dep=2,pos=0;
        //cell11.setCellStyle(CellStyle.ALIGN_CENTER);
        cell10.setCellValue("Marche Archile");
        sheet.addMergedRegion(new CellRangeAddress(
        pos,
        pos,
        0,
        l));
        pos++;
        row1 = sheet.createRow(pos);
        // Create a cell and put a value in it.
        Cell cell = row1.createCell(0);
//cell11.setCellStyle(CellStyle.ALIGN_CENTER);
        cell.setCellValue("Date");
        cell = row1.createCell(1);
        cell.setCellValue("Commande Numero");
        cell = row1.createCell(2);
        cell.setCellValue("Article");
        cell = row1.createCell(3);
        cell.setCellValue("Prix Unitaire");
        Cell cell43 = row1.createCell(4);
        cell43.setCellValue("Quantite");
        Cell cell44 = row1.createCell(5);
        cell44.setCellValue("Montant");
        Cell cell45 = row1.createCell(6);
        cell45.setCellValue("User");
        pos++;
        
        
        for(Commande c:Commandes){
            ObservableList<CommandeLine> cl=((CommandeManager)ManagerFactory.createModel(ModelName.commande)).commandeLines(c.getId());
            for(CommandeLine cline:cl){
                row1 = sheet.createRow(pos);
        // Create a cell and put a value in it.
        cell = row1.createCell(0);
//cell11.setCellStyle(CellStyle.ALIGN_CENTER);
        cell.setCellValue(c.getCreated().format(DateTimeFormatter.ISO_LOCAL_DATE));
        cell = row1.createCell(1);
        cell.setCellValue(c.getComCode());
        cell = row1.createCell(2);
        cell.setCellValue(cline.getProId().getNom());
        cell = row1.createCell(3);
        cell.setCellValue(cline.getPrix());
        cell43 = row1.createCell(4);
        cell43.setCellValue(cline.getQty());
        cell44 = row1.createCell(5);
        cell44.setCellValue(cline.total().get());
        cell45 = row1.createCell(6);
        cell45.setCellValue(c.getUser().getUsername());
        pos++;
            }
            
        }
        pos+=2;
        
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(f);
            wb.write(fileOut);
        fileOut.close();
        wb.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Vente_transactionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Vente_transactionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
    }
    
}


