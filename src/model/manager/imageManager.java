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
import model.image_produit;
import services.ConnectionSql;

/**
 *
 * @author Padovano
 */
public class imageManager extends ModelManager<image_produit>{
   

    @Override
    public image_produit ajouter(image_produit objet) {
        requete="insert into image_produit(image,image_path,pro_id,`default`) values(?,?,?,?)";
        Object [] ob=new Object[]{objet.getImage(),objet.getImagePath().substring(objet.getImagePath().lastIndexOf("/")+1),objet.getProduit_id(),objet.isDefaut()};
        if(this.connection.Update(requete,1, ob))
            objet.setId(connection.getLastinsert());
        else
            objet=null;
         return objet;   
    }

    @Override
    public boolean supprimer(image_produit Objet) {
        requete= "delete from produits where pro_id=?";
        return this.connection.Update(requete,0,Objet.getId());
    }

    @Override
    public ObservableList<image_produit> Dynamicrecherche(String objet) {
        ObservableList<image_produit> deps=FXCollections.observableArrayList();
        requete="select * from produits where dep_nom like ? or dep_code like ?";
        
        
        ResultSet rs=this.connection.select(requete,new Object[]{"%"+objet+"%","%"+objet+"%"});
        try {
            while(rs.next()){
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(imageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deps;
    }

    @Override
    public ObservableList<image_produit> Dynamicsearch(image_produit objet) {
        ObservableList<image_produit> pros=FXCollections.observableArrayList();
        requete="select * from image_produit where pro_id=?";
        
        ResultSet rs=this.connection.select(requete,objet.getProduit_id());
        try {
            while(rs.next()){
                image_produit p=new image_produit();
                p.setId(rs.getInt("id"));
                p.setImagePath(rs.getString("image_path"));
                p.setDefaut(rs.getBoolean("default"));
                
                pros.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(imageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pros;
    }

    @Override
    public ObservableList<image_produit> liste() {
        ObservableList<image_produit> pros=FXCollections.observableArrayList();
        requete="select * from image_produit where pro_id=?";
        
        ResultSet rs=this.connection.select(requete);
        try {
            while(rs.next()){
                image_produit p=new image_produit();
                
                pros.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(imageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pros;
    }

    @Override
    public image_produit search(image_produit objet) throws NullPointerException {
        requete="select * from produits where dep_nom =? and dep_code=?";
        
        ResultSet rs=this.connection.select(requete,new Object[]{});
        try {
            while(rs.next()){
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(imageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public image_produit modifier(image_produit ancien, image_produit nouveau) {
        requete="update produits set dep_nom=?,dep_code=? where dep_id=?";
        Object [] ob=new Object[]{};
        if(this.connection.Update(requete,0, ob))
            return nouveau;
        return null;
    }

    @Override
    public image_produit searchById(int id) {
        requete="select * from produits where dep_id=?";
        
        image_produit dep=null;
        ResultSet rs=this.connection.select(requete,id);
        try {
            while(rs.next()){
                dep=new image_produit();
                
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(imageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dep;
    }
    
}
