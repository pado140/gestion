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
import model.Achat_line;
import model.Achats;
import model.Produits;
import model.Stocks;

/**
 *
 * @author Padovano
 */
public class AchatLineManager extends ModelManager<Achat_line>{

    @Override
    public Achat_line ajouter(Achat_line objet) {
        requete="insert into achat_detail(qty,prixU,pro_id,line,achats_id) values(?,?,?,?,?)";
        if(connection.Update(requete, 1, objet.getQty(),objet.getPrixU(),objet.getProId().getId(),
                objet.getLine().get(),objet.getAchat().getId())){
            objet.setTransIdProperty(connection.getLastinsert());
            this.aftersave(objet);
            return objet;
        }
        return null;
    }

    @Override
    public boolean supprimer(Achat_line Objet) {
        requete="delete from achat_detail where trans_id=?";
        return connection.Update(requete, 0, Objet.getTransId());
    }

    @Override
    public ObservableList<Achat_line> liste() {
        requete="select * from achat_detail";
        ResultSet rs=connection.select(requete);
        Liste=FXCollections.observableArrayList();
        try {
            while(rs.next()){
                Achat_line acl=new Achat_line();
                acl.setLineProperty(rs.getInt("line"));
                acl.setTransIdProperty(rs.getInt("trans_id"));
                acl.setPrixUProperty(rs.getDouble("prixU"));
                acl.setProIdProperty((Produits)ManagerFactory.createModel(ModelName.produit).searchById(rs.getInt("pro_id")));
                acl.setQtyProperty(rs.getInt("qty"));
                
                
                Liste.add(acl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AchatLineManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Liste;
    }

    @Override
    public Achat_line modifier(Achat_line ancien, Achat_line nouveau) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ObservableList<Achat_line> search_by_achat(int id) {
        requete="select * from achat_detail where achats_id=?";
        ResultSet rs=connection.select(requete,id);
        Liste=FXCollections.observableArrayList();
        try {
            while(rs.next()){
                Achat_line acl=new Achat_line();
                acl.setLineProperty(rs.getInt("line"));
                acl.setTransIdProperty(rs.getInt("trans_id"));
                acl.setPrixUProperty(rs.getDouble("prixU"));
                acl.setProIdProperty((Produits)ManagerFactory.createModel(ModelName.produit).searchById(rs.getInt("pro_id")));
                acl.setQtyProperty(rs.getInt("qty"));
                
                Liste.add(acl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AchatLineManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Liste;
    }
    
    public ObservableList<Achat_line> search_by_produit(Produits p) {
        requete="select * from achat_detail where pro_id=?";
        ResultSet rs=connection.select(requete,p.getId());
        Liste=FXCollections.observableArrayList();
        try {
            while(rs.next()){
                Achat_line acl=new Achat_line();
                acl.setLineProperty(rs.getInt("line"));
                acl.setTransIdProperty(rs.getInt("trans_id"));
                acl.setPrixUProperty(rs.getDouble("prixU"));
                acl.setProIdProperty(p);
                acl.setQtyProperty(rs.getInt("qty"));
                acl.setAchat((Achats)ManagerFactory.createModel(ModelName.achat).searchById(rs.getInt("Achats_id")));
                Liste.add(acl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AchatLineManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Liste;
    }

    @Override
    protected void aftersave(Achat_line ob) {
        Stocks st=new Stocks();
        st.setProduit(ob.getProId());
        st=(Stocks)ManagerFactory.createModel(ModelName.stock).search(st);
        st.setPump(((st.getPump()*st.getQty())+(ob.getPrixU()*ob.getQty()))/(st.getQty()+ob.getQty()));
        st=(Stocks)ManagerFactory.createModel(ModelName.stock).modifier(st,st);
    }
    
    
}
