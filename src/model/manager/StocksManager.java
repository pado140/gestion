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
import model.Produits;
import model.Stocks;

/**
 *
 * @author Padovano
 */
public class StocksManager extends ModelManager<Stocks>{

    @Override
    public Stocks ajouter(Stocks objet) {
        requete="insert into stock(prod_id,qty,alert_qty,minqty,cump) values(?,?,?,?,?)";
        if(connection.Update(requete, 1, objet.getProduit().getId(),objet.getQty(),
                objet.getAlert_qty(),objet.getMinQty(),objet.getPump())){
            objet.setId(connection.getLastinsert());
        }else
            objet=null;
        return objet;
    }

    @Override
    public boolean supprimer(Stocks Objet) {
        requete="delete from stock where idstock=?";
        return connection.Update(requete, 0, Objet.getId());
    }

    @Override
    public ObservableList<Stocks> liste() {
        requete="select * from produit";
        Liste=FXCollections.observableArrayList();
        ResultSet rs=connection.select(requete);
        try {
            while(rs.next()){
                Stocks stock=new Stocks();
                Produits p=(Produits)ManagerFactory.createModel(ModelName.produit).searchById(rs.getInt("pro_id"));
                p.setQty(rs.getInt("qty"));
                stock.setProduit(p);
                stock.setQty(rs.getInt("qty"));
                stock.setAlert_qty(rs.getInt("alert_qty"));
                stock.setMinQty(rs.getInt("minqty"));
                stock.setCreated(rs.getDate("stockcreated").toLocalDate());
                stock.setMod(rs.getDate("modified").toLocalDate());
                stock.setPump(rs.getDouble("pump"));
                
                stock.setId(rs.getInt("idstock"));
                Liste.add(stock);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StocksManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Liste;
    }

    @Override
    public Stocks modifier(Stocks ancien, Stocks nouveau) {
        requete="update stock set qty=?,alert_qty=?,minqty=?,cump=?, modified=NOW() where idstock=?";
        if(connection.Update(requete, 0, nouveau.getQty(),nouveau.getAlert_qty(),nouveau.getMinQty(),nouveau.getPump(),ancien.getId()))
            return nouveau;
        return null;
    }

    @Override
    public Stocks save(Stocks objet) {
        if(objet.isNew())
            return ajouter(objet);
        else
            return modifier(objet, objet);
    }

    @Override
    public Stocks search(Stocks objet) {
        requete="select * from produit where pro_id=?";
        ResultSet rs=connection.select(requete,objet.getProduit().getId());
        try {
            while(rs.next()){
                Stocks stock=new Stocks();
                Produits p=(Produits)ManagerFactory.createModel(ModelName.produit).searchById(rs.getInt("pro_id"));
                p.setQty(rs.getInt("qty"));
                stock.setProduit(p);
                stock.setId(rs.getInt("idstock"));
                stock.setQty(rs.getInt("qty"));
                stock.setAlert_qty(rs.getInt("alert_qty"));
                stock.setMinQty(rs.getInt("minqty"));
                stock.setCreated(rs.getDate("stockcreated").toLocalDate());
                stock.setMod(rs.getDate("modified").toLocalDate());
                stock.setPump(rs.getDouble("pump"));
                return stock;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StocksManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
