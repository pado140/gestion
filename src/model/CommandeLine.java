/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Padovano
 */

public class CommandeLine extends item implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final ObjectProperty<Integer> qty,line;
    
    private ObjectProperty<Produits> proId;
    
    private ObjectProperty<Commande> comId;
    
    private ObjectProperty<Double> prix;

    public CommandeLine() {
        qty=new SimpleObjectProperty<>(0);
        line=new SimpleObjectProperty<>(0);
        proId=new SimpleObjectProperty<>();
        comId=new SimpleObjectProperty<>();
        prix=new SimpleObjectProperty<>(0.0);
    }


    public Integer getQty() {
        return qty.get();
    }

    public void setQty(Integer qty) {
        this.qty.set(qty);
    }
    public Integer getLine() {
        return line.get();
    }

    public void setLine(Integer line) {
        this.line.set(line);
    }

    public Produits getProId() {
        return proId.get();
    }

    public void setProId(Produits proId) {
        this.proId.set(proId);
    }

    public Commande getComId() {
        return comId.get();
    }

    public void setComId(Commande comId) {
        this.comId.set(comId);
    }

    public Double getPrix() {
        return prix.get();
    }

    public void setPrix(Double ppId) {
        this.prix.set(ppId);
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
        if (!(object instanceof CommandeLine)) {
            return false;
        }
        CommandeLine other = (CommandeLine) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return getComId().getComCode();
    }

    public ObjectProperty<Integer> getQtyProperty() {
        return qty;
    }

    public ObjectProperty<Integer> getLineProperty() {
        return line;
    }

    public ObjectProperty<Produits> getProIdProperty() {
        return proId;
    }

    public ObjectProperty<Commande> getComIdProperty() {
        return comId;
    }

    public ObjectProperty<Double> getPrixProperty() {
        return prix;
    }
    
    public final ObjectProperty<Double> total(){
        double result=prix.get()*qty.get();
        return new ReadOnlyObjectWrapper<>(result);
    }
}
