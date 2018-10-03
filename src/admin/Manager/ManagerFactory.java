/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.Manager;

import connection.ConnectionDb;
/**
 *
 * @author Padovano
 */
public class ManagerFactory {
    //private final ConnectionSql conn=ConnectionSql.instance(1);
    private static ModelManager mod;
        
    public static ModelManager createModel(Managerlist type){
        ConnectionDb conn=ConnectionDb.instance();
        switch(type){
            case order:
                mod=OrderManager.getManger();
                break;
        }
        
        return mod;
    }
}
