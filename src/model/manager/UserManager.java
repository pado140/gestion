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
import model.Users;

/**
 *
 * @author Padovano
 */
public class UserManager extends ModelManager<Users>{

    @Override
    public Users ajouter(Users objet) {
        requete="insert into users (code,nom,prenom,sexe,username,password,tel,email) values(?,?,?,?,?,?,?,?)";
        boolean check=false;
        if(connection.Update(requete, 1, objet.getCode(),objet.getName(),objet.getFirstname(),objet.getSex(),objet.getUsername(),objet.getPassword(),
                objet.getTel(),objet.getEmail())){
            check=true;
            objet.setUserId(connection.getLastinsert());
        if(objet.getImage()!=null)
            this.aftersave(objet);
        }else
            objet=null;
        return objet;
    }

    @Override
    public boolean supprimer(Users Objet) {
        requete="delete from users where user_id=?";
        return connection.Update(requete, 0, Objet.getUserId());
    }

    
    @Override
    public ObservableList<Users> liste() {
        requete="select * from users";
        Liste=FXCollections.observableArrayList();
        ResultSet rs=connection.select(requete);
        try {
            while (rs.next()){
                Users users=new Users();
                users.setUserId(rs.getInt("user_id"));
                users.setName(rs.getString("nom"));
                users.setFirstname(rs.getString("prenom"));
                users.setSex(rs.getString("sexe"));
                users.setUsername(rs.getString("username"));
                users.setEmail(rs.getString("email"));
                users.setPassword(rs.getString("password"));
                users.setTel(rs.getString("tel"));
                users.setCode(rs.getString("code"));
                users.setImage(rs.getString("image_path"));
                
                Liste.add(users);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Liste;
    }
    
    
    @Override
    public Users modifier(Users ancien, Users nouveau) {
        requete="update Users set nom=?,prenom=?,sexe=?,username=?,password=?,tel=?,email=?,image_path=? where user_id=?";
        if(connection.Update(requete, 0, nouveau.getName(),nouveau.getFirstname(),nouveau.getSex(),nouveau.getUsername(),nouveau.getPassword(),
                nouveau.getTel(),nouveau.getEmail(),nouveau.getImage(),ancien.getUserId()))
            return nouveau;
        return null;
    }

    @Override
    public Users search(Users objet) {
        requete="select * from users where username=?";
        Users users=null;
        ResultSet rs=connection.select(requete,objet.getUsername());
        try {
            while (rs.next()){
                users=new Users();
                users.setUserId(rs.getInt("user_id"));
                users.setName(rs.getString("nom"));
                users.setFirstname(rs.getString("prenom"));
                users.setSex(rs.getString("sexe"));
                users.setUsername(rs.getString("username"));
                users.setEmail(rs.getString("email"));
                users.setPassword(rs.getString("password"));
                users.setTel(rs.getString("tel"));
                users.setCode(rs.getString("code"));
                users.setImage(rs.getString("image_path"));
                
                return users;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    protected void aftersave(Users ob) {
        modifier(ob, ob);
    }

    @Override
    public Users searchById(int id) {
        requete="select * from users where user_id=?";
        Users users=null;
        ResultSet rs=connection.select(requete,id);
        try {
            while (rs.next()){
                users=new Users();
                users.setUserId(rs.getInt("user_id"));
                users.setName(rs.getString("nom"));
                users.setFirstname(rs.getString("prenom"));
                users.setSex(rs.getString("sexe"));
                users.setUsername(rs.getString("username"));
                users.setEmail(rs.getString("email"));
                users.setPassword(rs.getString("password"));
                users.setTel(rs.getString("tel"));
                users.setCode(rs.getString("code"));
                users.setImage(rs.getString("image_path"));
                
                return users;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
}
