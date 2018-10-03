/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.Manager;

import admin.objets.Orders;
import java.util.Set;

/**
 *
 * @author Padovano
 */
public class OrderManager extends ModelManager<Orders>{
    private static OrderManager manager=null;

    
    public synchronized static OrderManager getManger() {
        if(manager==null)
            manager=new OrderManager();
        return manager;
    }

    @Override
    public boolean ajouter(Orders objet) {
        return true;
    }

    @Override
    public boolean supprimer(Orders Objet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Orders> Dynamicrecherche(String objet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Orders> Dynamicsearch(Orders objet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Orders> liste() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Orders search(Orders objet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Orders searchById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean modifier(Orders ancien, Orders nouveau) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
