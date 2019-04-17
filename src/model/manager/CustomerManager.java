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
import model.Customers;

/**
 *
 * @author Padovano
 */
public class CustomerManager extends ModelManager<Customers>{

    @Override
    public Customers ajouter(Customers objet) {
        requete="insert into customers(nom,adresse,tel,e_mail) values(?,?,?,?)";
        if(connection.Update(requete, 1, objet.getNom(),objet.getAdresse(),objet.getTel(),objet.getEMail())){
            objet.setId(connection.getLastinsert());
            return objet;
            
        }
        return null;
    }

    @Override
    public boolean supprimer(Customers Objet) {
        requete="delete from customers where cus_id=?";
        return connection.Update(requete, 0, Objet.getCusId());
    }

    @Override
    public ObservableList<Customers> liste() {
        requete="select * from customers";
        Liste=FXCollections.observableArrayList();
        ResultSet rs=connection.select(requete);
        try {
            while(rs.next()){
                Customers cus=new Customers();
                cus.setAdresse(rs.getString("adresse"));
                cus.setId((rs.getInt("cus_id")));
                cus.setEMail(rs.getString("e_mail"));
                cus.setNom(rs.getString("nom"));
                cus.setTel(rs.getString("tel"));
                Liste.add(cus);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Liste;
    }

    @Override
    public Customers modifier(Customers ancien, Customers nouveau) {
        requete="update customers set nom=? ,adresse=?,tel=?,e_mail=? where cus_id=?";
        if(connection.Update(requete, 0, nouveau.getNom(),nouveau.getAdresse(),nouveau.getTel(),nouveau.getEMail(),ancien.getId())){
            return nouveau;
        }
        return null;
    }

    @Override
    public Customers searchById(int id) {
        requete="select * from customers where cus_id=?";
        Liste=FXCollections.observableArrayList();
        ResultSet rs=connection.select(requete,id);
        try {
            while(rs.next()){
                Customers cus=new Customers();
                cus.setAdresse(rs.getString("adresse"));
                cus.setId(rs.getInt("cus_id"));
                cus.setEMail(rs.getString("e_mail"));
                cus.setNom(rs.getString("nom"));
                cus.setTel(rs.getString("tel"));
                return cus;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Customers save(Customers objet) {
        if(objet.isNew())
            return this.ajouter(objet);
        else
            return this.modifier(objet, objet);
        
    }
    
    
    
}
