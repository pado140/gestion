/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Padovano
 */
public class ConnectionSql {
    
    private static ConnectionSql con=null;
    private String user="root";
    private String pass="";
    private String Url="jdbc:mysql://localhost/gestionstock";
    private String api="com.mysql.jdbc.Driver";
    private Statement state=null;
    private ResultSet resultat;
    private Connection connection=null;
    private int lastinsert=0;
    
    private ConnectionSql(){
        
        try
        {
            Class.forName(api);
            connection = DriverManager.getConnection(
    Url, user, pass);
            System.out.println("succes");
    }catch(SQLException ex){
        System.out.println(ex.getMessage());
    }catch(ClassNotFoundException e){
        e.printStackTrace();
    }
    }
    
    public static synchronized ConnectionSql instance(){
        if(con==null)
            con=new ConnectionSql();
        return con;
    }
    
    public ResultSet select(String query,Object... critere){
        try {
            PreparedStatement prepare=connection.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            int i=1;
            for(Object ob:critere){
                prepare.setObject(i, ob);
                i++;
            }
            resultat=prepare.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSql.class.getName()).log(Level.SEVERE, null, ex);
        }
       return resultat; 
    }
    
    public boolean Update(String query,int auto,Object... critere){
        boolean check=false;
        try {
            PreparedStatement prepare=connection.prepareStatement(query, auto);
            int i=1;
            for(Object ob:critere){
                prepare.setObject(i, ob);
                i++;
            }
            if(prepare.executeUpdate()>0){
                check=true;
                if(auto==1){
                    ResultSet rs=prepare.getGeneratedKeys();
                    while(rs.next())
                    lastinsert=(int)rs.getInt(1);
                }
                    
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSql.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("error:"+ex.getMessage());
            
        }
       return check; 
    }

    public int getLastinsert() {
        return lastinsert;
    }
}
