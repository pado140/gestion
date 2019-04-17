/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Suppliers;

/**
 *
 * @author Padovano
 */
public class SupplierManager extends ModelManager<Suppliers>{

    @Override
    public Suppliers ajouter(Suppliers objet) {
        requete="insert into suppliers(nom,adresse,tel,e_mail) values(?,?,?,?)";
        boolean check=false;
        check=connection.Update(requete,1 ,objet.getNom(),objet.getAddresse(),objet.getTel(),objet.getEMail());
            if(check){
                objet.setSupId(connection.getLastinsert());
            }else
                objet=null;
        return objet;
    }

    @Override
    public boolean supprimer(Suppliers Objet) {
        requete="delete from suppliers where sup_id=?";
        return connection.Update(requete, 0, Objet.getSupId());
    }

    @Override
    public ObservableList<Suppliers> liste() {
        Liste=FXCollections.observableArrayList();
        requete="select * from suppliers";
        ResultSet rs=connection.select(requete);
        
        try {
            while(rs.next()){
                Suppliers sup=new Suppliers();
                sup.setNom(rs.getString("nom"));
                sup.setAddresse(rs.getString("adresse"));
                sup.setSupId(rs.getInt("sup_id"));
                sup.setEMail(rs.getString("e_mail"));
                sup.setTel(rs.getString("tel"));
                
                Liste.add(sup);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Liste;
    }

    @Override
    public Suppliers modifier(Suppliers ancien, Suppliers nouveau) {
        requete="update suppliers set nom=?,adresse=?,e_mail=?,tel=? where sup_id=?";
        if(connection.Update(requete, 0, nouveau.getNom(),nouveau.getAddresse(),nouveau.getEMail(),nouveau.getTel(),ancien.getSupId()))
            return nouveau;
        return null;
    }

    @Override
    public Suppliers searchById(int id) {
        requete="select * from suppliers where sup_id=?";
        ResultSet rs=connection.select(requete,id);
        
        try {
            while(rs.next()){
                Suppliers sup=new Suppliers();
                sup.setNom(rs.getString("nom"));
                sup.setAddresse(rs.getString("adresse"));
                sup.setSupId(rs.getInt("sup_id"));
                sup.setEMail(rs.getString("e_mail"));
                sup.setTel(rs.getString("tel"));
                
                return sup;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
