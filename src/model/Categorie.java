/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Padovano
 */
public class Categorie extends item implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private StringProperty categorie;
    
    private ObjectProperty<LocalDate> created;
    
    private ObservableList<Produits> produitsCollection;

    public Categorie() {
        categorie=new SimpleStringProperty();
        created=new SimpleObjectProperty<>();
        produitsCollection=FXCollections.observableArrayList();
    }

    public Categorie(IntegerProperty catId) {
        this.id = catId;
    }

    public Categorie(IntegerProperty catId, StringProperty categorie) {
        this.id = catId;
        this.categorie = categorie;
    }

    public Integer getCatId() {
        return id.get();
    }

    public void setCatId(Integer catId) {
        this.id.set(catId);
    }

    public String getCategorie() {
        return categorie.get();
    }

    public void setCategorie(String categorie) {
        this.categorie.set(categorie);
    }

    public LocalDate getCreated() {
        return created.get();
    }

    public void setCreated(LocalDate created) {
        this.created.set(created);
    }

    @XmlTransient
    public ObservableList<Produits> getProduitsCollection() {
        return produitsCollection;
    }

    public void setProduitsCollection(ObservableList<Produits> produitsCollection) {
        this.produitsCollection = produitsCollection;
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
        if (!(object instanceof Categorie)) {
            return false;
        }
        Categorie other = (Categorie) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return categorie.get();
    }

    public StringProperty getCategorieProperty() {
        return categorie;
    }

    public ObjectProperty<LocalDate> getCreatedProperty() {
        return created;
    }
    
    
}
