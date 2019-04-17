/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.manager;

import controller.mainController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Commande;
import model.CommandeLine;
import model.Customers;
import model.Produits;
import model.Users;

/**
 *
 * @author Padovano
 */
public class CommandeManager extends ModelManager<Commande>{

    @Override
    public Commande ajouter(Commande objet) {
        requete="insert into commande (com_code,type,created,discount,cus_id,users_id) values(?,'cash',?,?,?,?)";
        if(connection.Update(requete, 1, objet.getComCode(),objet.getCreated().format(DateTimeFormatter.ISO_LOCAL_DATE),
                0,objet.getCusId()!=null?objet.getCusId().getId():null,mainController.user.getUserId()))
        {
            objet.setId(connection.getLastinsert());
            objet.setComCode("Com-"+objet.getId());
            modifier(objet, objet);
            aftersave(objet);
            return objet;
        }
        return null;
    }

    @Override
    public boolean supprimer(Commande Objet) {
        requete="delete from commande where com_id=?";
        return connection.Update(requete, 0, Objet.getId());
    }

    @Override
    public ObservableList<Commande> liste() {
        requete="select * from commande";
        Liste=FXCollections.observableArrayList();
        ResultSet rs=connection.select(requete);
        try {
            while(rs.next()){
                Commande c=new Commande();
                c.setComCode(rs.getString("com_code"));
                c.setCreated(rs.getDate("created").toLocalDate());
                c.setCusId((Customers)ManagerFactory.createModel(ModelName.customer).searchById(rs.getInt("cus_id")));
                c.setDiscount(rs.getDouble("discount"));
                c.setId(rs.getInt("com_id"));
                c.setUser((Users)ManagerFactory.createModel(ModelName.user).searchById(rs.getInt("users_id")));
                Liste.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Liste;
    }

    @Override
    public Commande modifier(Commande ancien, Commande nouveau) {
        requete="update commande set com_code=? where com_id=?";
        if(connection.Update(requete, 0, nouveau.getComCode(),ancien.getId()))
            return nouveau;
        return null;
    }
    
    public ObservableList<CommandeLine> commandeLines(int com_id){
        requete="select * from commandelines where com_id=?";
        ObservableList<CommandeLine> liste=FXCollections.observableArrayList();
        ResultSet rs=connection.select(requete,com_id);
        try {
            while(rs.next()){
               CommandeLine cl=new CommandeLine();
               cl.setId(rs.getInt("cl_id"));
               cl.setPrix(rs.getDouble("prix"));
               cl.setQty(rs.getInt("qty"));
               Commande c=new Commande();
               c.setId(rs.getInt("com_id"));
               c.setComCode(rs.getString("com_code"));
               c.setCreated(rs.getDate("created").toLocalDate());
               cl.setComId(c);
               cl.setProId((Produits)ManagerFactory.createModel(ModelName.produit).searchById(rs.getInt("pro_id")));
               liste.add(cl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeLineManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return liste;
    }

    @Override
    protected void aftersave(Commande ob) {
        for(CommandeLine cl:ob.getCommandeLineCollection()){
            cl.setComId(ob);
            cl=(CommandeLine)ManagerFactory.createModel(ModelName.commandeLine).ajouter(cl);
        }
    }
    
    
}
