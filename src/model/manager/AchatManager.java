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
import model.Achat_line;
import model.Achats;
import model.Users;

/**
 *
 * @author Padovano
 */
public class AchatManager extends ModelManager<Achats>{

    @Override
    public Achats ajouter(Achats objet) {
        requete="insert into achats(code,no_transaction,date_achat,users_id) values(?,?,?,?)";
        if(connection.Update(requete, 1, objet.getCode().get(),objet.getTransac_no().get(),
                objet.getDate().get().format(DateTimeFormatter.ISO_LOCAL_DATE),mainController.user.getUserId())){
            objet.setId(connection.getLastinsert());
            this.aftersave(objet);
            return objet;
        }
        return null;
    }

    @Override
    public boolean supprimer(Achats Objet) {
        requete="delete from achats where id=?";
        return connection.Update(requete, 0, Objet.getId());
    }

    @Override
    public ObservableList<Achats> liste() {
        requete="select * from achats";
        Liste=FXCollections.observableArrayList();
        ResultSet rs=connection.select(requete);
        try {
            while(rs.next()){
                Achats ac=new Achats();
                ac.setId(rs.getInt("id"));
                ac.setCode(rs.getString("code"));
                ac.setTransac_no(rs.getString("no_transaction"));
                ac.setLineItems(((AchatLineManager)ManagerFactory.createModel(ModelName.line_achat)).search_by_achat(rs.getInt("id")));
                ac.setUser((Users)ManagerFactory.createModel(ModelName.user).searchById(rs.getInt("users_id")));
                ac.setDate(rs.getDate("date_achat").toLocalDate());
                Liste.add(ac);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AchatManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return Liste;
    }

    @Override
    public Achats modifier(Achats ancien, Achats nouveau) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void aftersave(Achats ob) {
        if(!ob.getLineItems().isEmpty()){
            for(Achat_line acl:ob.getLineItems()){
                acl.setAchat(ob);
                ManagerFactory.createModel(ModelName.line_achat).ajouter(acl);
            }
        }
    }

    @Override
    public Achats searchById(int id) {
        requete="select * from achats where id=?";
        ResultSet rs=connection.select(requete,id);
        try {
            while(rs.next()){
                Achats ac=new Achats();
                ac.setId(rs.getInt("id"));
                ac.setCode(rs.getString("code"));
                ac.setTransac_no(rs.getString("no_transaction"));
                //ac.setLineItems(((AchatLineManager)ManagerFactory.createModel(ModelName.line_achat)).search_by_achat(rs.getInt("id")));
                ac.setUser((Users)ManagerFactory.createModel(ModelName.user).searchById(rs.getInt("users_id")));
                ac.setDate(rs.getDate("date_achat").toLocalDate());
               return ac;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AchatManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }
    
    
    
    
}
