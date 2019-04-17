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
import model.Categorie;
import services.ConnectionSql;

/**
 *
 * @author Padovano
 */
public class CategorieManager extends ModelManager<Categorie>{


    @Override
    public Categorie ajouter(Categorie objet) {
        requete="insert into Categorie(categorie) values(?)";
        Object [] ob=new Object[]{objet.getCategorie()};
        if(this.connection.Update(requete,1, ob))
            objet.setCatId(connection.getLastinsert());
        else
            objet=null;
         return objet;   
    }

    @Override
    public boolean supprimer(Categorie Objet) {
        requete= "delete from categorie where cat_id=?";
        return this.connection.Update(requete,0,Objet.getCatId());
    }

    @Override
    public ObservableList<Categorie> liste() {
        ObservableList<Categorie> categories=FXCollections.observableArrayList();
        requete="select * from categorie";
        
        ResultSet rs=this.connection.select(requete);
        try {
            while(rs.next()){
                Categorie categorie=new Categorie();
                categorie.setCatId(rs.getInt("cat_id"));
                categorie.setCategorie(rs.getString("categorie"));
                
                categories.add(categorie);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    @Override
    public Categorie search(Categorie objet){
        requete="select * from Categorie where categorie=?";
        
        Categorie categorie=null;
        ResultSet rs=this.connection.select(requete,objet.getCategorie());
        try {
            while(rs.next()){
                categorie=new Categorie();
                categorie.setCatId(rs.getInt("cat_id"));
                categorie.setCategorie(rs.getString("categorie"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categorie;
    }

    @Override
    public Categorie modifier(Categorie ancien, Categorie nouveau) {
        requete="update categorie set categorie=? where cat_id=?";
        Object [] ob=new Object[]{nouveau.getCategorie(),ancien.getCatId()};
        if(this.connection.Update(requete,0, ob))
            return nouveau;
        return null;
    }

    @Override
    public Categorie searchById(int id) {
        requete="select * from Categorie where cat_id=?";
        
        Categorie categorie=null;
        ResultSet rs=this.connection.select(requete,id);
        try {
            while(rs.next()){
                categorie=new Categorie();
                categorie.setCatId(rs.getInt("cat_id"));
                categorie.setCategorie(rs.getString("categorie"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categorie;
    }
    
}
