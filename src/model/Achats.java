/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Padovano
 */
public class Achats extends item{
    private StringProperty code,transac_no;
    private ObjectProperty<LocalDate> date;
    private ObservableList<Achat_line> lineItems;
    private ObjectProperty<Dossiers> dossiers;
    private ObjectProperty<Users> user;

    public Achats() {
        code=new SimpleStringProperty();
        transac_no=new SimpleStringProperty();
        date=new SimpleObjectProperty<>();
        dossiers=new SimpleObjectProperty<>();
        user=new SimpleObjectProperty<>();
        lineItems=FXCollections.observableArrayList();
    }

    public StringProperty getCode() {
        return code;
    }

    public void setCodeProperty(StringProperty code) {
        this.code = code;
    }

    public StringProperty getTransac_no() {
        return transac_no;
    }

    public void setTransac_noProperty(StringProperty transac_no) {
        this.transac_no = transac_no;
    }

    public ObjectProperty<LocalDate> getDate() {
        return date;
    }

    public void setDateProperty(ObjectProperty<LocalDate> date) {
        this.date = date;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public void setTransac_no(String transac_no) {
        this.transac_no.set(transac_no);
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    @Override
    public String toString() {
        return this.code.get(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    public ObservableList<Achat_line> getLineItems() {
        return lineItems;
    }

    public void setLineItems(ObservableList<Achat_line> lineItems) {
        this.lineItems = lineItems;
    }

    public boolean add(Achat_line elements) {
        return lineItems.add(elements);
    }

    public Achat_line get(int index) {
        return lineItems.get(index);
    }

    public ObjectProperty<Dossiers> getDossiers() {
        return dossiers;
    }

    public void setDossiers(Dossiers dossiers) {
        this.dossiers.set(dossiers);
    }
    
    public ObjectProperty<Double> total(){
        double val=0;
        for(Achat_line acl:lineItems)
            val+=acl.getPrixU()*acl.getQty();
        return new ReadOnlyObjectWrapper<>(val);
    }

    public ObjectProperty<Users> getUserProperty() {
        return user;
    }

    public void setUser(Users user) {
        this.user.set(user);
    }

    public Users getUser() {
        return user.get();
    }
    
    
}
