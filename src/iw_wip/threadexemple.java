/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iw_wip;

import connection.ConnectionDb;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import observateurs.Observateurs;
import observateurs.Observe;

/**
 *
 * @author Padovano
 */
public class threadexemple implements Runnable,Observe{
private ConnectionDb conn = ConnectionDb.instance();
DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    @Override
    public void run() {
        Calendar cal;
        boolean isok=false;
        cal=Calendar.getInstance();
        cal.setTime(now());
        for(;;){
            
                //conn=ConnectionDb.instance();
            cal.add(Calendar.SECOND, 1);
            int a=1;
            System.out.println(cal.getTime());
            if(cal.get(Calendar.HOUR_OF_DAY)>=8){
                    //System.exit(0);
                    alerter("closed");
                }
                if(cal.get(Calendar.HOUR_OF_DAY)<8){
                    
                    if(!isok){
                    a=1;
                    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY)
                        a=2;
                    cal.add(Calendar.DATE,-a);
                    }
                    isok=true;
                    alerter("modifier date",cal.getTime());
                }
                alerter("stat");
                alerter("timer",cal.getTime());
                //if(conn.)
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(threadexemple.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    private Date now(){
        Date d=new Date();
        //System.out.println(d);
        String requete="Select  SYSDATETIME() now";
        ResultSet rs=conn.select(requete);
    try {
        while(rs.next()){
        d=rs.getDate("now");
        d=(Date)rs.getObject("now");
        
        //System.out.println(rs.getObject("now").toString());
        }
    } catch (SQLException ex) {
        Logger.getLogger(threadexemple.class.getName()).log(Level.SEVERE, null, ex);
    }
       return d; 
    }
    
    
    @Override
    public void ajouterObservateur(Observateurs ob) {
        obs.add(ob);
    }

    @Override
    public void retirerObservateur(Observateurs ob) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void alerter(Object... ob) {
        for(Observateurs o:obs){
            o.executer(ob);
        }
    }
    
}
