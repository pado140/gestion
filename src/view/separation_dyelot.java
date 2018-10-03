/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import connection.ConnectionDb;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import view.report.data_layout;

/**
 *
 * @author Padovano
 */
public class separation_dyelot extends javax.swing.JDialog {

    private ConnectionDb conn = ConnectionDb.instance();
    private String po,Marker;
    private int id,plys,marker_no;
    /**
     * Creates new form separation_dyelot
     */
    public separation_dyelot(JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        gen.setEnabled(false);
        jTable1.setCellEditor(new DefaultCellEditor(new JTextField()));
    }
    
    public separation_dyelot(JFrame parent, boolean modal,int id,int plys,int marker_no) {
        super(parent, modal);
        initComponents();
        jTable1.setCellEditor(new DefaultCellEditor(new JTextField()));
        this.id=id;
        this.plys=plys;
        gen.setEnabled(false);
        this.marker_no=marker_no;
        cut.setText(String.valueOf(marker_no));
        plys_left.setText(String.valueOf(plys));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        gen = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        plys_left = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cut = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Dye lot qty:");

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));
        jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "qty plys"
            }
        ));
        jTable1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable1PropertyChange(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
        }

        gen.setText("generated");
        gen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genActionPerformed(evt);
            }
        });

        jLabel3.setText("Plys left:");

        plys_left.setText("jLabel4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(gen, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(plys_left, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(plys_left, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gen, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
        );

        jLabel2.setText("Marker no:");

        cut.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cut, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cut, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged
        // TODO add your handling code here:
        int val=(Integer)jSpinner1.getValue();
        if(val>0)
            gen.setEnabled(true);
        else
            gen.setEnabled(false);
        Object[][] o=new Object[val][];
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            o,
            new String [] {
                "qty plys"
            }
        ));
        plys_left.setText(String.valueOf(plys));
    }//GEN-LAST:event_jSpinner1StateChanged

    private void genActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genActionPerformed
        // TODO add your handling code here:
        int pltotal=0;
        if(jTable1.isEditing())
            jTable1.getCellEditor().stopCellEditing();
        int[] data=new int[(Integer)jSpinner1.getValue()];
        for(int i=0;i<data.length;i++){
            try{
            if(jTable1.getValueAt(i, 0).toString().isEmpty()){
                JOptionPane.showMessageDialog(this, "please specify the number of plys by dye lot");
                return;
            }
            }catch(NullPointerException e){
                JOptionPane.showMessageDialog(this, "please specify the number of plys by dye lot");
                return;
            }
                
            data[i]=Integer.parseInt(jTable1.getValueAt(i, 0).toString());
            pltotal+=Integer.parseInt(jTable1.getValueAt(i, 0).toString());
            
        }
        if(pltotal>plys){
            JOptionPane.showMessageDialog(this, "the qty of plys and the qty plys of dyelot is not match");
                return;
        }else if(pltotal<plys){
            JOptionPane.showMessageDialog(this, "the qty of plys and the qty plys of dyelot is not match");
                return;
        }
        print( id, marker_no, data);
    }//GEN-LAST:event_genActionPerformed

    private void jTable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTable1PropertyChange

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        // TODO add your handling code here:
        if(jTable1.isEditing()){
            jTable1.getCellEditor().stopCellEditing();
            System.out.println(sommeIn());
            int result=plys-sommeIn();
            plys_left.setText(String.valueOf(result));
            if(result<0)
                plys_left.setForeground(Color.red);
            else if(result==0)
                plys_left.setForeground(Color.green);
            else
                plys_left.setForeground(null);
        }
    }//GEN-LAST:event_jTable1KeyReleased

    
    private int sommeIn(){
        int sum=0;
            for(int i=0;i<jTable1.getRowCount();i++){
                int val=0;
                try{
                    val=Integer.parseInt(jTable1.getValueAt(i, 0).toString());
                }catch(NullPointerException|NumberFormatException e){
                    val=0;
                }
                sum+=val;
            }
        return sum;
    }
    private void print(int id,int cut_no,int... dyelot){
       String po="";
        String requete= "select * from all_cut where cut_id=?";
        ResultSet rs=conn.select(requete, id);
        Map param = new HashMap();
                  param.put("bundle_no",""+cut_no);
                  
                  ArrayList<data_layout> da=new ArrayList<>();
        try {
            while(rs.next()){
                int a=1;
                String sku=rs.getString("sku");
                String col=sku.substring(sku.indexOf(".")+1, sku.lastIndexOf("."));
                for(int i=0;i<rs.getInt("mrtio");i++){
                    for(int pl:dyelot){
                        if(pl>50){
                            int nb=(int)Math.ceil(pl/50);
                            if(pl%50>0)
                                nb+=1;
                            for(int j=0;j<nb;j++){
                                data_layout d=new data_layout();
                                po=rs.getString("po");
                                if(rs.getString("brand").trim().equals("EDG")){
                                      po=po+"("+rs.getString("cut no").trim()+")";
                                  }
                                d.setPo(po);
                                d.setColor(col+"-"+rs.getString("COLOR"));
                                d.setSize(rs.getString("size"));
                                d.setStyle(rs.getString("style"));
                                d.setQty(50);
                                if(pl%50>0){
                                    if(j==nb-1)
                                        d.setQty(pl%50);
                                }
                                
                                d.setNb(dyelot.length*rs.getInt("mrtio")*nb);

                                d.setTicket_num(a);
                                da.add(d);
                                a++;
                            }
                        }else{
                      data_layout d=new data_layout();
                      po=rs.getString("po");
                      if(rs.getString("brand").trim().equals("EDG")){
                            po=po+"("+rs.getString("cut no").trim()+")";
                        }
                      d.setPo(po);
                      d.setColor(col+"-"+rs.getString("COLOR"));
                      d.setSize(rs.getString("size"));
                      d.setStyle(rs.getString("style"));
                      d.setQty(pl);
                      d.setNb(dyelot.length*rs.getInt("mrtio"));
                      
                      d.setTicket_num(a);
                      da.add(d);
                      a++;
                  }
                    }
                }
                
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ready_to_sew.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{ 
            URL  master= this.getClass().getResource("report/card_layout.jasper");
            
                  param.put("tickets",5);
                  
                  
                  for(int i=0;i<da.size();i++)
                      System.out.println("da:"+da.get(i).getPo());
                  JasperReport jasperReport = (JasperReport) JRLoader.loadObject(master);
                  JRBeanCollectionDataSource beanCutDataSource =new JRBeanCollectionDataSource(da);
                  JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,param,beanCutDataSource);
                  
                      if(!Files.exists(new File(System.getProperty("user.home").concat("/Documents/new travel card")).toPath())){
                          File f=new File(System.getProperty("user.home").concat("/Documents/new travel card/barcode"));
                          //f.createNewFile();
                          f.mkdirs();
                      }
                      
                      JasperExportManager.exportReportToPdfFile(jasperPrint, System.getProperty("user.home").concat("/Documents/new travel card/")+po+"("+id+"-"+cut_no+").pdf");
                  
                  Desktop dsk=Desktop.getDesktop();
                  dsk.open(new File(System.getProperty("user.home").concat("/Documents/new travel card/")+po+"("+id+"-"+cut_no+").pdf"));
                  
 
                }
 
                catch (Exception e)
                 {
                     e.printStackTrace();
                     System.out.println("Mensaje de Error:"+e.getMessage());
                     JOptionPane.showMessageDialog(this, e.getMessage());
                 }
    }
    
    
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cut;
    private javax.swing.JButton gen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel plys_left;
    // End of variables declaration//GEN-END:variables
}
