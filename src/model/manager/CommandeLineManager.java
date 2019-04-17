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
import model.Commande;
import model.CommandeLine;
import model.Produits;

/**
 *
 * @author Padovano
 */
public class CommandeLineManager extends ModelManager<CommandeLine>{

    @Override
    public CommandeLine ajouter(CommandeLine objet) {
        requete="insert into commande_Line (pro_id,qty,com_id,prix,discount) values(?,?,?,?,?)";
        if(connection.Update(requete, 1, objet.getProId().getId(),objet.getQty(),objet.getComId().getId(),objet.getPrix(),
                0)){
            objet.setId(connection.getLastinsert());
            return objet;
        }
        return null;
    }

    @Override
    public boolean supprimer(CommandeLine Objet) {
        requete="delete from commande_line where cl_id=?";
        return connection.Update(requete, 0, Objet.getId());
    }

    @Override
    public ObservableList<CommandeLine> liste() {
        requete="select * from commandelines";
        Liste=FXCollections.observableArrayList();
        ResultSet rs=connection.select(requete);
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
               Liste.add(cl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeLineManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Liste;
    }

    @Override
    public CommandeLine modifier(CommandeLine ancien, CommandeLine nouveau) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
