/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Padovano
 */
public abstract class item {
    protected IntegerProperty id;

    public item() {
        id=new SimpleIntegerProperty(0);
    }

    public IntegerProperty getIdProperty() {
        return id;
    }

    public void setIdProperty(IntegerProperty id) {
        this.id = id;
    }
    
    public void setId(int id){
        this.id.set(id);
    }
    
    public int getId(){
        return this.id.get();
    }
    
    public boolean isNew(){
        return this.id.get()==0;
    }
}
