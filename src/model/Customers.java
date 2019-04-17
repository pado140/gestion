/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Padovano
 */

public class Customers extends item implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ObjectProperty<String> nom,adresse,tel,eMail;
    
    private ObservableList<Commande> commandeCollection;

    public Customers() {
        nom=new SimpleObjectProperty<>();
        adresse=new SimpleObjectProperty<>();
        tel=new SimpleObjectProperty<>();
        eMail=new SimpleObjectProperty<>();
    }

    public Customers(IntegerProperty id) {
        this.id = id;
    }

    public Customers(IntegerProperty id, String nom) {
        this.id = id;
    }

    public IntegerProperty getCusId() {
        return id;
    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getAdresse() {
        return adresse.get();
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }

    public String getTel() {
        return tel.get();
    }

    public void setTel(String tel) {
        this.tel.set(tel);
    }

    public String getEMail() {
        return eMail.get();
    }

    public void setEMail(String eMail) {
        this.eMail.set(eMail);
    }

    @XmlTransient
    public ObservableList<Commande> getCommandeCollection() {
        return commandeCollection;
    }

    public void setCommandeCollection(ObservableList<Commande> commandeCollection) {
        this.commandeCollection = commandeCollection;
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
        if (!(object instanceof Customers)) {
            return false;
        }
        Customers other = (Customers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nom.get();
    }

    public ObjectProperty<String> getNomProperty() {
        return nom;
    }

    public ObjectProperty<String> getAdresseProperty() {
        return adresse;
    }

    public ObjectProperty<String> getTelProperty() {
        return tel;
    }

    public ObjectProperty<String> geteMailProperty() {
        return eMail;
    }
    
}
