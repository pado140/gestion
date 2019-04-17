/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author Padovano
 */
public class Dossiers extends item{
    private ObjectProperty<Boolean> open;
    private ObjectProperty<LocalDate> created;
    private StringProperty name;
    private ObservableList<Achats> achatslist;
    private ObservableList<Commande> commandelist;

    public Dossiers() {
        this.open = new SimpleObjectProperty<>();
        this.created = new SimpleObjectProperty<>();
        this.name = new SimpleStringProperty();
    }

    public ObjectProperty<Boolean> getOpenProperty() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open.set(open);
    }

    public ObjectProperty<LocalDate> getCreatedProperty() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created.set(created);
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Boolean getOpen() {
        return open.get();
    }

    public LocalDate getCreated() {
        return created.get();
    }

    public String getName() {
        return name.get();
    }

    @Override
    public String toString() {
        return name.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
