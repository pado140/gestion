/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iw_wip;

import connection.ConnectionDb;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Padovano
 */
public class update_database {
    private ConnectionDb conn = ConnectionDb.instance();

    public update_database() {
        sta();
    }
    
    
    
    private void sta(){
        
        String requete="select * from  packing where box_id is null";
        
        String req="select * from box_detail b inner join box_id bi on(b.box_id=bi.id) where stickers=?";
        ResultSet rs=conn.select(requete);
        try {
            ResultSetMetaData rd=rs.getMetaData();
        System.out.println("nb column:"+rd.getColumnCount());
        for(int a=1;a<=rd.getColumnCount();a++)
        System.out.print(rd.getColumnName(a)+"\t");
        System.out.println();
        while(rs.next()){
            ResultSet rs1=conn.select(req, rs.getString("box_stickers"));
            while(rs1.next()){
            int id=rs1.getInt("box_id");
            conn.Update("update packing set box_id=? where box_stickers=?",0,id,rs.getString("box_stickers"));
            }
        for(int a=1;a<=rd.getColumnCount();a++)
        System.out.print(rs.getObject(rd.getColumnName(a))==null?"":rs.getObject(rd.getColumnName(a)).toString()+"\t");
        System.out.println();
        }
  
        System.out.println("nline:"+rs.getRow());
        //rs.
        } catch (SQLException ex) {
        Logger.getLogger(update_database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here
        
        new update_database();
    }
}
