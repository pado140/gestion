/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.objets;

/**
 *
 * @author Padovano
 */
public abstract class beans {
    protected int id;
    
    public beans(){
        id=0;
    }
    
    protected synchronized boolean isNew(){
        return id==0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
     protected boolean isValid(){
         return true;
     }
}
