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
import model.Achat_line;
import model.Achats;
import model.Customers;
import model.Produits;
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
public class AchatsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label com,mtnt,dte;
    @FXML
    private TableView<Achat_line> table;
    
    @FXML
    private TableView<Achats> tab_vente;
    
    @FXML
    private TableColumn<Achat_line,String> article;
    
    @FXML
    private TableColumn<Achats,String> code,user;
    
    @FXML
    private TableColumn<Achats,LocalDate> datevente;
    
    
    @FXML
    private TableColumn<Achats,Double> total;
    
    @FXML
    private TableColumn<Achat_line,Double> tot,prix;
    
    @FXML
    private TableColumn<Achat_line,Integer> qty; 
    
    private Produits produit;
    private Customers customer;
    
    private ObservableList<Achats> Achatss;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Achatss=ManagerFactory.createModel(ModelName.achat).liste();
        article.setCellValueFactory(cellData->cellData.getValue().getProId().getPropertyNom());
        prix.setCellValueFactory(cellData->cellData.getValue().getPrixUProperty().asObject());
        datevente.setCellValueFactory(cellData->cellData.getValue().getDate());
        qty.setCellValueFactory(cellData->cellData.getValue().getQtyProperty().asObject());
        total.setCellValueFactory(cellData->cellData.getValue().total());
        code.setCellValueFactory(cell->cell.getValue().getTransac_no());
        tot.setCellValueFactory(cell->cell.getValue().montant());
        user.setCellValueFactory(cell->{
            return cell.getValue().getUser().getUsernameProperty();
        });
        tab_vente.getSelectionModel().selectedItemProperty().
                addListener((ObservableValue<? extends Achats> observable, Achats oldValue, Achats newValue) -> {
            showDetails(newValue);
        });
        
        tab_vente.setItems(Achatss); 
        
        calcul();
    }    
    
    
    private void showDetails(Achats co){
        if(co!=null){
            ObservableList<Achat_line> cl=co.getLineItems();
            table.setItems(cl);
            com.setText(co.getTransac_no().get());
            dte.setText(co.getDate().get().format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }
    
    private void calcul(){
        double t=0;
        for(Achats c:Achatss)
            t+=c.total().get();
        
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
            XSSFSheet sheet = wb.createSheet("rapport sur les achats");
            XSSFSheet sheet1 = wb.createSheet("rapport sur les achats(details)");
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
        cell.setCellValue("Achats Numero");
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
        
        
        for(Achats c:Achatss){
            ObservableList<Achat_line> cl=c.getLineItems();
            for(Achat_line cline:cl){
                row1 = sheet.createRow(pos);
        // Create a cell and put a value in it.
        cell = row1.createCell(0);
//cell11.setCellStyle(CellStyle.ALIGN_CENTER);
        cell.setCellValue(c.getDate().get().format(DateTimeFormatter.ISO_LOCAL_DATE));
        cell = row1.createCell(1);
        cell.setCellValue(c.getTransac_no().get());
        cell = row1.createCell(2);
        cell.setCellValue(cline.getProId().getNom());
        cell = row1.createCell(3);
        cell.setCellValue(cline.getPrixU());
        cell43 = row1.createCell(4);
        cell43.setCellValue(cline.getQty());
        cell44 = row1.createCell(5);
        cell44.setCellValue(cline.montant().get());
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
