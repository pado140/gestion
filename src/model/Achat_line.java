/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Padovano
 */
public class Achat_line extends item implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private IntegerProperty line,qty;
   
    private DoubleProperty prixU;
    
    private ObjectProperty<Produits> proId;
    
    private ObjectProperty<Achats> achat;

    public Achat_line() {
        id=new SimpleIntegerProperty(0);
        line=new SimpleIntegerProperty(0);
        qty=new SimpleIntegerProperty(0);
        prixU=new SimpleDoubleProperty();
        proId=new SimpleObjectProperty<>();
        achat=new SimpleObjectProperty<>();
    }

    public Achat_line(Integer transId) {
        
    }

    public Achat_line(Integer transId, String qty, double prixU) {
        
    }

    public Integer getTransId() {
        return id.get();
    }

    public void setTransIdProperty(Integer transId) {
        this.id.set(transId);
    }

    public int getQty() {
        return qty.get();
    }

    public void setQtyProperty(int qty) {
        this.qty.set(qty);
    }

    public double getPrixU() {
        return prixU.get();
    }

    public void setPrixUProperty(double prixU) {
        this.prixU.set(prixU);
    }

    public Produits getProId() {
        return proId.get();
    }

    public void setProIdProperty(Produits proId) {
        this.proId.set(proId);
    }


    public IntegerProperty getLine() {
        return line;
    }

    public void setLineProperty(Integer line) {
        this.line.set(line);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public IntegerProperty getTransIdProperty() {
        return id;
    }

    public IntegerProperty getQtyProperty() {
        return qty;
    }

    public DoubleProperty getPrixUProperty() {
        return prixU;
    }

    public ObjectProperty<Produits> getProIdProperty() {
        return proId;
    }

    public DoubleProperty getTotalProperty() {
        return new SimpleDoubleProperty(qty.get()*prixU.get());
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Achat_line)) {
            return false;
        }
        Achat_line other = (Achat_line) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    public Achats getAchat() {
        return achat.get();
    }

    public void setAchat(Achats achat) {
        this.achat.set(achat);
    }

    public ObjectProperty<Double> montant(){
        return new ReadOnlyObjectWrapper<>(prixU.get()*getQty());
    }
    
}
