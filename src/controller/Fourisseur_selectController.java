/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Suppliers;
import model.manager.ManagerFactory;
import model.manager.ModelName;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class Fourisseur_selectController implements Initializable {
    
    @FXML
    private TableView<Suppliers> tabs;
    
    @FXML
    private TableColumn<Suppliers,String> name,telephone;
    
    private ObservableList<Suppliers> suppliers;
    private Suppliers supplier;
    private Stage dialog;

    public Stage getDialog() {
        return dialog;
    }

    public void setDialog(Stage dialog) {
        this.dialog = dialog;
    }
    

    public Suppliers getSupplier() {
        return supplier;
    }

    public void setSupplier(Suppliers supplier) {
        this.supplier = supplier;
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        suppliers=ManagerFactory.createModel(ModelName.supplier).liste();
        name.setCellValueFactory((CellData)->CellData.getValue().getNomProperty());
        telephone.setCellValueFactory((CellData)->CellData.getValue().getTelProperty());
        
        tabs.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount()==2){
                    supplier=tabs.getSelectionModel().getSelectedItem();
                    dialog.close();
                }
            }
            
        });
        tabs.setItems(suppliers);
    }    
    
    @FXML
    private  void close(){
        dialog.close();
        supplier=null;
    }
}
