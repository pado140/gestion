/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import connection.ConnectionDb;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import observateurs.Observateurs;
import observateurs.Observe;

/**
 *
 * @author Padovano
 */
public class daemon extends Thread implements Observe,Observateurs{
private final ConnectionDb conn = ConnectionDb.instance();

    private Set<Object[]> dataOrder,summary_cut,lisData;
    private Map<Integer, Integer> list_second;
    private Map<String, Integer> list_pack;
    public static Object[][] data;

    public daemon() {
        this.lisData = new HashSet<>();
        this.dataOrder=new HashSet<>();
        this.list_pack=new HashMap<>();
        this.list_second=new HashMap<>();
        loadDyn();
        
    }

    public static void main(String[] args){
        daemon damon=new daemon();
        try {
            damon.setDaemon(true);
            damon.start();
            daemon.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void loadDyn(){
        while(true){
            loadData();
            second();
            packed();
        mostrar();
        alerter("reload",data);
            try {
                
                Thread.sleep(10000);
                } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                }
        }
    }
    private void mostrar(){
    //DefaultTableModel tbm = (DefaultTableModel) GRID_DATA.getModel();
    //tbm.setRowCount(0);
        data=new Object[lisData.size()][16];
        int i=0;
            for(Object[] ob:lisData){
                
                   data[i]=ob;
                  
                   data[i][12]=list_pack.get(ob[12].toString());                  
                   data[i][8]=Integer.parseInt(data[i][8].toString())-list_second.get(Integer.parseInt(data[i][9].toString()));
                   data[i][9]=list_second.get(Integer.parseInt(data[i][9].toString()));
                  i++;
            }
            System.out.println("load successfull");
}
    
    @Override
    public void run() {
        System.out.println("alive");
        //loadOrder();
    }

    private void packed(){
        String requete="select ordnum,packed from pack";
        list_pack=new HashMap<>();
        ResultSet rs=conn.select(requete);
        
    try {
        while(rs.next()){
            list_pack.put(rs.getString("ordnum"),rs.getInt("packed"));
        }
    } catch (SQLException ex) {
        Logger.getLogger(WIPCONTROL.class.getName()).log(Level.SEVERE, null, ex);
    }
        
    }
    private void second(){
        String requete="select sew_id , QTY_PER_LOT qty,id_sew,s_traveller from sewing"
                + "_production where sew_id in(select max(sew_id) sew_id from"
                + " sewing_production group by S_TRAVELLER) order by sew_id asc";
        list_second=new HashMap<>();
        ResultSet rs=conn.select(requete);
        
    try {
        while(rs.next()){
            list_second.put(rs.getInt("id_sew"),rs.getInt("qty"));
        }
    } catch (SQLException ex) {
        Logger.getLogger(WIPCONTROL.class.getName()).log(Level.SEVERE, null, ex);
    }
        
      
    }
    
    private void loadData(){
        lisData.clear();
        String requete="select * from WIP_PRODUCTION";
    System.out.println(requete);
    ResultSet rs=conn.select(requete);
        try {
            while(rs.next()){
                String sku=rs.getString("sku");
                   String size=sku.substring(sku.lastIndexOf(".")+1).trim();
                   /*if(rs.getString("description").toLowerCase().contains("yth")||
                   rs.getString("description").toLowerCase().contains("youth")||
                   rs.getString("description").toLowerCase().contains("girls"))
                   size="Y"+size;*/
                String color,Code;
               color=rs.getString("color").trim();
               Code=rs.getString("color_code").trim();
             
                   Object[] data=new Object[16];
                   data[0]=rs.getString("Brand");
                   data[1]=rs.getString("po");
                   data[2]=rs.getString("style");
                   data[10]=rs.getInt("PIECES_SEWN");
                   data[3]=Code;
                   data[4]=color;
                   data[5]=size;
                   data[7]=rs.getInt("qty_cut");
                   data[11]=rs.getInt("qty_cut")-rs.getInt("PIECES_SEWN");
                   data[12]=rs.getString("order_num").trim();
                   data[13]=0;
                   data[14]="BLD "+rs.getInt("workcenter")+"- "+rs.getString("mod");
                   data[15]=rs.getDate("date_made");
                   data[6]=rs.getInt("pieces");
                   data[8]=rs.getInt("PIECES_SEWN");
                   data[9]=rs.getInt("id_sew");
                   lisData.add(data);
            }   } catch (SQLException ex) {
            Logger.getLogger(Sewing_prod.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public daemon(Runnable target) {
        super(target);
    }
    private void loadOrder(){
        dataOrder.clear();
        String requete="select * from orderplan";
        ResultSet rs = conn.select(requete);
        
        try {
            while(rs.next()){
                String sku=rs.getString("sku").trim();
               
                String size=rs.getString("size");
                String desc=rs.getString("description").trim(),
                        po=rs.getString("po").trim();
                
                if(!rs.getString("brand").equals("CHS")){
                if(desc.toLowerCase().contains("yth")||desc.toLowerCase().contains("youth")||
                        desc.toLowerCase().contains("girls")||desc.toLowerCase().contains("boys"))
                size="Y"+size;
                }
                
                dataOrder.add(new Object[]{po,rs.getString("style").trim(),desc.trim(),rs.getString("color_code").trim(),rs.getString("color").trim(),
                    size.trim(),rs.getInt("pieces"),"ready to cut",rs.getString("MRKCUTPLAN")+"-"+size,null,rs.getString("ordnum_147")});
            }
        }catch(SQLException e){
            
        }
        alerter("all",dataOrder);
    }
    
    @Override
    public void ajouterObservateur(Observateurs ob) {
        obs.add(ob);
    }

    @Override
    public void retirerObservateur(Observateurs ob) {
        obs.clear();
    }

    @Override
    public void alerter(Object... ob) {
        for(Observateurs o:obs)
            o.executer(ob);
    }

    @Override
    public void executer(Object... obs) {
        
    }
    
}
