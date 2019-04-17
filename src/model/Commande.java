/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Padovano
 */
public class Commande extends item implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ObjectProperty<String> comCode;
    
    private ObjectProperty<String> type;
    
    private ObjectProperty<LocalDate> created;
    
    private ObjectProperty<Double> discount;
   
    private ObservableList<CommandeLine> commandeLineCollection;
    
    private ObjectProperty<Customers> cusId;
    
    private ObjectProperty<Users> user;

    public Commande() {
        comCode=new SimpleObjectProperty<>();
        created=new SimpleObjectProperty<>();
        type=new SimpleObjectProperty<>();
        discount=new SimpleObjectProperty<>();
        cusId=new SimpleObjectProperty<>();
        commandeLineCollection=FXCollections.observableArrayList();
        user=new SimpleObjectProperty<>();
    }

    
    public String getComCode() {
        return comCode.get();
    }

    public void setComCode(String comCode) {
        this.comCode.set(comCode);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public LocalDate getCreated() {
        return created.get();
    }

    public void setCreated(LocalDate created) {
        this.created.set(created);
    }

    public Double getDiscount() {
        return discount.get();
    }

    public void setDiscount(Double discount) {
        this.discount.set(discount);
    }

    @XmlTransient
    public ObservableList<CommandeLine> getCommandeLineCollection() {
        return commandeLineCollection;
    }

    public void setCommandeLineCollection(ObservableList<CommandeLine> commandeLineCollection) {
        this.commandeLineCollection = commandeLineCollection;
    }

    public Customers getCusId() {
        return cusId.get();
    }

    public void setCusId(Customers cusId) {
        this.cusId.get();
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
        if (!(object instanceof Commande)) {
            return false;
        }
        Commande other = (Commande) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getComCode();
    }

    public ObjectProperty<String> getComCodeProperty() {
        return comCode;
    }

    public ObjectProperty<String> getTypeProperty() {
        return type;
    }

    public ObjectProperty<LocalDate> getCreatedProperty() {
        return created;
    }

    public ObjectProperty<Double> getDiscountProperty() {
        return discount;
    }

    public ObjectProperty<Customers> getCusIdProperty() {
        return cusId;
    }
    
    public double montant(){
        double val=0;
        for(CommandeLine cl:commandeLineCollection){
            val+=cl.getPrix()*cl.getQty();
        }
        return val;
    }
    
    public ObjectProperty<Double> montantProperty(){
        return new ReadOnlyObjectWrapper<>(montant());
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
