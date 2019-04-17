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
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import model.Achat_line;
import model.CommandeLine;
import model.Stocks;
import model.manager.AchatLineManager;
import model.manager.ManagerFactory;
import model.manager.ModelName;
import model.manager.ProduitManager;
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
public class StockDeProduitController implements Initializable {

    @FXML
    private TableView<Stocks> stocksTab;
    
    @FXML
    private Label qty,valeur,nbre;
    
    @FXML
    private TextField filt;
    
    @FXML
    private TableColumn<Stocks,String> state,code,desc;
    
    @FXML
    private TableColumn<Stocks,Integer> stock_init,stock_real;
    
    @FXML
    private TableColumn<Stocks,Double> pmp,val;
    
    
    
    ObservableList<Stocks> stocks;
    ObservableList<Stocks> Temp=FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        stocks=ManagerFactory.createModel(ModelName.stock).liste();
        //Temp=stocks;
        state.setCellValueFactory(cellData->{
            Stocks st=cellData.getValue();
            String etat="GOOD";
            if(st.getProduit().qty_reel_stock().get()<st.getAlert_qty())
                etat="Alert de rupture";
            return new ReadOnlyObjectWrapper<>(etat);
        });
        code.setCellValueFactory(cellData->cellData.getValue().getProduit().getPropertyCode());
        desc.setCellValueFactory(cellData->cellData.getValue().getProduit().getPropertyDescription());
        stock_init.setCellValueFactory(cellData->cellData.getValue().getQtyProperty());
        stock_real.setCellValueFactory(cellData->cellData.getValue().getProduit().qty_reel_stock());
        pmp.setCellValueFactory(cellData->cellData.getValue().getPumpProperty());
        val.setCellValueFactory(cellData->cellData.getValue().valeurStock());
        
        stocksTab.setItems(stocks);
        nbre.setText(String.valueOf(stocks.size()));
        calculStock(stocks);
    }    
    
    private void calculStock(ObservableList<Stocks> stocks){
        nbre.setText(String.valueOf(stocks.size()));
        int qt=0;
        double value=0;
        for(Stocks st:stocks){
            qt+=st.getProduit().qty_reel_stock().get();
            value+=st.valeurStock().get();
        }
        qty.setText(String.valueOf(qt));
        valeur.setText(String.valueOf(value));
    }
    
    @FXML
    private void filtre(){
        Temp.clear();
        for(Stocks st:stocks){
            if(st.getProduit().getNom().trim().toLowerCase().contains(filt.getText().trim().toLowerCase())||
                    st.getProduit().getCode().trim().toLowerCase().contains(filt.getText().trim().toLowerCase()))
                Temp.add(st);
        }
        stocksTab.setItems(Temp);
        calculStock(Temp);
    }
    
    @FXML
    private void refresh(){
        filt.setText("");
        Temp.clear();
        for(Stocks st:stocks){
            if(st.getProduit().getNom().trim().toLowerCase().contains(filt.getText().trim().toLowerCase())||
                    st.getProduit().getCode().trim().toLowerCase().contains(filt.getText().trim().toLowerCase()))
                Temp.add(st);
        }
        stocksTab.setItems(Temp);
        calculStock(Temp);
    }
    
    @FXML
    private void export(){
        Alert alert=new Alert(AlertType.INFORMATION);
        FileChooser file=new FileChooser();
        file.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Spread Sheet", "*.xlsx","*.xls"));
        File f=file.showSaveDialog(stocksTab.getScene().getWindow());
        if(f!=null){
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("rapport de Vente");
            XSSFSheet sheet1 = wb.createSheet("rapport sur les achat");
            XSSFSheet sheet2 = wb.createSheet("Etat du stock");
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
        
        row1 = sheet1.createRow(0);
        cell10 = row1.createCell(0);
        l=7;
        dep=2;
        pos=0;
        //cell11.setCellStyle(CellStyle.ALIGN_CENTER);
        cell10.setCellValue("Marche Archile");
        sheet1.addMergedRegion(new CellRangeAddress(
        pos,
        pos,
        0,
        l));
        pos++;
        row1 = sheet1.createRow(pos);
        // Create a cell and put a value in it.
        cell = row1.createCell(0);
//cell11.setCellStyle(CellStyle.ALIGN_CENTER);
        cell.setCellValue("Date");
        cell = row1.createCell(1);
        cell.setCellValue("Achats Numero");
        cell = row1.createCell(2);
        cell.setCellValue("Article");
        cell = row1.createCell(3);
        cell.setCellValue("Prix Unitaire");
        cell43 = row1.createCell(4);
        cell43.setCellValue("Quantite");
        cell44 = row1.createCell(5);
        cell44.setCellValue("Montant");
        cell45 = row1.createCell(6);
        cell45.setCellValue("User");
        
        row1 = sheet2.createRow(0);
        cell10 = row1.createCell(0);
        l=7;
        dep=2;
        pos=0;
        //cell11.setCellStyle(CellStyle.ALIGN_CENTER);
        cell10.setCellValue("Marche Archile");
        sheet2.addMergedRegion(new CellRangeAddress(
        pos,
        pos,
        0,
        l));
        pos++;
        row1 = sheet2.createRow(pos);
        // Create a cell and put a value in it.
        cell = row1.createCell(0);
//cell11.setCellStyle(CellStyle.ALIGN_CENTER);
        cell.setCellValue("Etat");
        cell = row1.createCell(1);
        cell.setCellValue("Article");
        cell = row1.createCell(2);
        cell.setCellValue("stock initial");
        cell = row1.createCell(3);
        cell.setCellValue("Stock reel");
        cell43 = row1.createCell(4);
        cell43.setCellValue("CUMP");
        cell44 = row1.createCell(5);
        cell44.setCellValue("Montant");
        
        pos++;
        
        
        for(Stocks stock:Temp){
            
            ObservableList<CommandeLine> cl=((ProduitManager)ManagerFactory.createModel(ModelName.produit)).
                    vente_Produit(stock.getProduit().getId());
            for(CommandeLine cline:cl){
                row1 = sheet.createRow(pos);
        // Create a cell and put a value in it.
        cell = row1.createCell(0);
//cell11.setCellStyle(CellStyle.ALIGN_CENTER);
        cell.setCellValue(cline.getComId().getCreated().format(DateTimeFormatter.ISO_LOCAL_DATE));
        cell = row1.createCell(1);
        cell.setCellValue(cline.getComId().getComCode());
        cell = row1.createCell(2);
        cell.setCellValue(stock.getProduit().getNom());
        cell = row1.createCell(3);
        cell.setCellValue(cline.getPrix());
        cell43 = row1.createCell(4);
        cell43.setCellValue(cline.getQty());
        cell44 = row1.createCell(5);
        cell44.setCellValue(cline.total().get());
        pos++;
            }
            pos=2;
            ObservableList<Achat_line> acl=((AchatLineManager)ManagerFactory.createModel(ModelName.line_achat)).
                    search_by_produit(stock.getProduit());
            for(Achat_line acline:acl){
                row1 = sheet1.createRow(pos);
        // Create a cell and put a value in it.
        cell = row1.createCell(0);
//cell11.setCellStyle(CellStyle.ALIGN_CENTER);
        cell.setCellValue(acline.getAchat().getDate().get().format(DateTimeFormatter.ISO_LOCAL_DATE));
        cell = row1.createCell(1);
        cell.setCellValue(acline.getAchat().getTransac_no().get());
        cell = row1.createCell(2);
        cell.setCellValue(stock.getProduit().getNom());
        cell = row1.createCell(3);
        cell.setCellValue(acline.getPrixU());
        cell43 = row1.createCell(4);
        cell43.setCellValue(acline.getQty());
        cell44 = row1.createCell(5);
        cell44.setCellValue(acline.montant().get());
        pos++;
            }
            
            pos=2;
         row1 = sheet2.createRow(pos);
        // Create a cell and put a value in it.
        cell = row1.createCell(0);
        cell.setCellValue(stock.getProduit().qty_reel_stock().get()<stock.getAlert_qty()?"Alert de rupture":"Good");
        cell = row1.createCell(1);
        cell.setCellValue(stock.getProduit().getNom());
        cell = row1.createCell(2);
        cell.setCellValue(stock.getQty());
        cell = row1.createCell(3);
        cell.setCellValue(stock.getProduit().qty_reel_stock().get());
        cell43 = row1.createCell(4);
        cell43.setCellValue(stock.getPump());
        cell44 = row1.createCell(5);
        cell44.setCellValue(stock.valeurStock().get());
            
        }
        
        
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(f);
            wb.write(fileOut);
        fileOut.close();
        wb.close();
        alert.setContentText("Success");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Vente_transactionController.class.getName()).log(Level.SEVERE, null, ex);
            alert.setAlertType(AlertType.ERROR);
            alert.setContentText(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(Vente_transactionController.class.getName()).log(Level.SEVERE, null, ex);
            alert.setAlertType(AlertType.ERROR);
            alert.setContentText(ex.getMessage());
        }
        alert.showAndWait();
        }
    }
}
