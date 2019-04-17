/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Padovano
 */
public class Suppliers extends item implements Serializable {
    private static final long serialVersionUID = 1L;
        
    private StringProperty nom;
    
    private StringProperty addresse;
    
    private StringProperty tel;
    
    private StringProperty eMail;
    
    private ObservableList<Produits> produit;

    public Suppliers() {
        this.nom = new SimpleStringProperty();
        this.addresse = new SimpleStringProperty();
        this.tel = new SimpleStringProperty();
        this.eMail = new SimpleStringProperty();
        produit=FXCollections.observableArrayList();
    }

    public Suppliers(Integer supId) {
        this.id.set(supId);
    }

    public Suppliers(IntegerProperty supId, StringProperty nom, StringProperty addresse, StringProperty tel) {
        this.id = supId;
        this.nom = nom;
        this.addresse = addresse;
        this.tel = tel;
    }

    public StringProperty getNomProperty() {
        return nom;
    }

    public void setNomProperty(StringProperty nom) {
        this.nom = nom;
    }

    public StringProperty getTelProperty() {
        return tel;
    }

    public void setTelProperty(StringProperty tel) {
        this.tel = tel;
    }

    public Integer getSupId() {
        return id.get();
    }

    public void setSupId(Integer supId) {
        this.id.set(supId);
    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getAddresse() {
        return addresse.get();
    }

    public void setAddresse(String addresse) {
        this.addresse.set(addresse);
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

    public ObservableList<Produits> getProduit() {
        return produit;
    }

    public void setProduit(ObservableList<Produits> produit) {
        this.produit = produit;
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
        if (!(object instanceof Suppliers)) {
            return false;
        }
        Suppliers other = (Suppliers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getNom();
    }
    
}
