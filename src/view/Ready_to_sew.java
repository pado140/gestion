/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import connection.ConnectionDb;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import observateurs.Observateurs;
import observateurs.Observe;
import view.report.data_layout;

/**
 *
 * @author Padovano
 */
public class Ready_to_sew extends javax.swing.JInternalFrame implements Observe{

    private ConnectionDb conn = ConnectionDb.instance();
    private Map<String , Integer> prod=new HashMap<>(),plan;
    private Map<String, String> ref=null;
    private Set<Object[]> liste=null;
    /**
     * Creates new form Ready_to_sew
     */
    public Ready_to_sew() {
        initComponents();
        liste=new HashSet<>();
    listData();
    }

    private Map<String ,Integer> saoBar(){
        String requete="Select po,sku, sum(qty) qty from at_soabar where status<>'5' group by po,sku";
        Map<String , Integer> soa=new HashMap<>();
        ResultSet rs=conn.select(requete);
        try {
            while(rs.next()){
                String order=rs.getString("po").trim();
                order+="."+rs.getString("sku").trim();
                soa.put(order, rs.getInt("qty"));
                       
            }
        } catch (SQLException ex) {
               //Logger.getLogger(Cansew.class.getName()).log(Level.SEVERE, null, ex);
           }
        return soa;
    }
    private Map<String,Integer> type(){
        Map<String,Integer> typ=new HashMap<>();
        String requete="select ordnum_10,po,sku,count(type_id) n from cut1 where type_id NOT IN(4,6,7) group by ordnum_10,po,sku";
        ResultSet rs=conn.select(requete);
        try {
            while(rs.next()){
                typ.put(rs.getString("po").trim().concat("."+rs.getString("sku").trim()),rs.getInt("n"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Ready_to_sew.class.getName()).log(Level.SEVERE, null, ex);
        }
        return typ;
    }
    private Map<String,Integer> listType(){
        String query="select Distinct style,count(distinct[TYPE]) t FROM StyleMaster where type_id NOT IN(4,6,7) group by style";
        ResultSet rs=conn.select(query);
        Map<String,Integer> lissty=new HashMap<>();
        try {
            while(rs.next()){
                String sty=rs.getString("style").trim();
                lissty.put(sty, rs.getInt("t"));
                       
            }
        } catch (SQLException ex) {
               //Logger.getLogger(Cansew.class.getName()).log(Level.SEVERE, null, ex);
           }
        return lissty;
    }
    private void data_cut(){
                 Map<String,Integer> listType=listType();
                 Map<String,Integer> typecount=type();
               Map<String,Integer> production=new HashMap<>();
               ref=new HashMap<>();
               String query = "SELECT * from SUM_CUT1";
                try {
               ResultSet rs = conn.select(query);
               
               while (rs.next()){
                       production.put(rs.getString("po").trim().concat("."+rs.getString("sku").trim()), rs.getInt("qty"));
                       ref.put(rs.getString("po").trim().concat("."+rs.getString("sku").trim()),rs.getString("style"));
               }
           } catch (SQLException ex) {
               Logger.getLogger(Bundle.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           for(String order:production.keySet()){
               int value=0;
               try{
                   value=production.get(order);
                   }catch(NullPointerException e){
                       value=0;
                   }
               
               String style=ref.get(order);
               
               int typ=0,ty=0;
               try{
                   typ=listType.get(style.trim());
                   
               }catch(NullPointerException e){
                   typ=0;
               }
               
               try{
                   ty=typecount.get(order);
               }catch(NullPointerException e){
                   ty=0;
               }
               if(order.contains("183.BP28.NYW")){
               System.out.println("type2:"+typecount.get(order));
               System.out.println("type:"+typ);
               }
               if(ty==typ)
               prod.put(order, value);
           }
    }
    private int getSew(String order){
        int a=0;
        String requete="select count(*) n from soabar where order_num=?";
        ResultSet rs=conn.select(requete, order);
        try {
            while(rs.next()){
                a=rs.getInt("n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Ready_to_sew.class.getName()).log(Level.SEVERE, null, ex);
        }
        a=a+1;
        return a;
    }
    private void initPlan(){
        String requete="select * from [plan1]";
        plan =new HashMap<>();
        ResultSet rs=conn.select(requete);
        try {
            while(rs.next()){
              plan.put(rs.getString("po").trim().concat(".").concat(rs.getString("sku")).trim(),rs.getInt("pieces"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Ready_to_sew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private boolean transferData(Object[][] data){
        String requete="insert into soabar(order_num,SEWING_TRAVElLER,stickers,QTY,user_id) values(?,?,?,?,?)";
        String requete1="insert into tRANSAC(TRANSACT,ITEM,QTY,ACT_TYPE,ACT_NAME,[DATE_T],User_id) values ('to soabar',?,?,1,'transfer to soabar',"
                + "getdate(),?)";
            for(int i=0;i<data.length;i++){
                
            if(this.conn.Update(requete, 1, new Object[]{data[i][8],data[i][0]+"."+data[i][1]+"."+data[i][4]+"."+data[i][6],
                data[i][8].toString()+getSew(data[i][8].toString()),data[i][7],Principal.user_id})){
                this.conn.Update(requete1, 1, new Object[]{data[i][8],data[i][7],Principal.user_id});
            }
            }
        return false;
    }
    private void listData(){
        liste=new HashSet<>();
        data_cut();
        initPlan();
        Map<String,Integer> soabar=this.saoBar();
        DefaultTableModel tbm = (DefaultTableModel) GRID_DATA.getModel();
        tbm.setRowCount(0);
        String requete="select * from orderplan WHERE STATUS_147<>'5'";
        ResultSet rs = conn.select(requete);
        int soa;
        try {
            while(rs.next()){
                String sku=rs.getString("sku").trim();
               
                String size=sku.replace('.','-').split("-")[2];
                String desc=rs.getString("description").trim(),
                        po=rs.getString("po").trim();
                if(!rs.getString("brand").equals("CHS")&& !rs.getString("brand").equals("GBG")){
                if(desc.toLowerCase().contains("yth")||desc.toLowerCase().contains("youth")||
                        desc.toLowerCase().contains("girls")||desc.toLowerCase().contains("boys"))
                size="Y"+size;
                }
                String status="ready to cut";
                try{
                    soa=0;
                    try{
                        soa=soabar.get(po.trim()+"."+sku.trim());
                    }catch(NullPointerException e){
                        soa=0;
                    }
                    if(prod.get(po.trim()+"."+sku.trim())>=0){
                        System.out.println("cut-soa="+(prod.get(po.trim()+"."+sku.trim())-soa));
                            if(prod.get(po.trim()+"."+sku.trim())-soa>0){
                            tbm.addRow(new Object[]{po,rs.getString("style").trim(),rs.getString("PROTO").trim(),desc.trim(),rs.getString("color_code").trim(),rs.getString("color").trim(),
                    size.trim(),prod.get(po.trim()+"."+sku.trim())-soa,rs.getString("ORDNUM_147")});
                          liste.add(new Object[]{po,rs.getString("style").trim(),rs.getString("PROTO").trim(),desc.trim(),rs.getString("color_code").trim(),rs.getString("color").trim(),
                    size.trim(),prod.get(po.trim()+"."+sku.trim())-soa,rs.getString("ORDNUM_147")}); 
                            }
                    }
                    
                }catch(NullPointerException e){
                    
                }
                
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Work_status.class.getName()).log(Level.SEVERE, null, ex);
        }
               
        
    }
    
    private void buscados(){
        String po=po_filter.getText().trim();
        String style=style_filter.getText().trim();
        String colorC=color_code_filter.getText().trim();
        String color=color_filter.getText().trim();
        String size=size_filter.getText().trim();
        DefaultTableModel tbm = (DefaultTableModel) GRID_DATA.getModel();
        tbm.setRowCount(0);
        for(Object[] o:liste){
            if(o[0].toString().toLowerCase().contains(po.toLowerCase()) && o[1].toString().toLowerCase().contains(style.toLowerCase())
                    && o[4].toString().toLowerCase().contains(colorC.toLowerCase())
                    && o[5].toString().toLowerCase().contains(color.toLowerCase()) && o[6].toString().toLowerCase().contains(size.toLowerCase())){
                tbm.addRow(o);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        POPUP = new javax.swing.JPopupMenu();
        transfer = new javax.swing.JMenuItem();
        gen_tag = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        po_filter = new javax.swing.JTextField();
        style_filter = new javax.swing.JTextField();
        color_code_filter = new javax.swing.JTextField();
        color_filter = new javax.swing.JTextField();
        size_filter = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        GRID_DATA = new javax.swing.JTable();

        transfer.setText("Transfer to sew");
        transfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferActionPerformed(evt);
            }
        });
        POPUP.add(transfer);

        gen_tag.setText("generate tag");
        gen_tag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gen_tagActionPerformed(evt);
            }
        });
        POPUP.add(gen_tag);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Transfer to Soa bar");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameDeactivated(evt);
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        po_filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                po_filterKeyReleased(evt);
            }
        });

        style_filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                style_filterKeyReleased(evt);
            }
        });

        color_code_filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                color_code_filterKeyReleased(evt);
            }
        });

        color_filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                color_filterKeyReleased(evt);
            }
        });

        size_filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                size_filterKeyReleased(evt);
            }
        });

        jLabel1.setText("PO:");

        jLabel2.setText("STYLE:");

        jLabel3.setText("COLOR CODE:");

        jLabel4.setText("COLOR:");

        jLabel5.setText("SIZE:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(po_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(style_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(155, 155, 155)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(color_code_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(color_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(size_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(po_filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(style_filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(color_code_filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(color_filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(size_filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        GRID_DATA.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "PO", "STYLE", "PROTO", "DESCRIPTION", "COLOR_CODE", "COLOR", "SIZE", "QUANTITY", "SEWING TRAVELER"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        GRID_DATA.setToolTipText("");
        GRID_DATA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                GRID_DATAMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(GRID_DATA);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1072, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GRID_DATAMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GRID_DATAMouseReleased
        // TODO add your handling code here:
        if(evt.getButton()==MouseEvent.BUTTON3){
            if(!GRID_DATA.getSelectionModel().isSelectionEmpty())
            POPUP.show(GRID_DATA,evt.getX(),evt.getY());
            else
                JOptionPane.showMessageDialog(this, "Please select a row!", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_GRID_DATAMouseReleased

    private void transferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transferActionPerformed
        // TODO add your handling code here:
        Object[][] data=new Object[GRID_DATA.getSelectedRowCount()][9];
        
        
        for(int i=0;i<GRID_DATA.getSelectedRowCount();i++){
            int ligne=GRID_DATA.getSelectedRows()[i];
        data[i][0]=GRID_DATA.getValueAt(ligne, 0);
        data[i][1]=GRID_DATA.getValueAt(ligne, 1);
        data[i][2]=GRID_DATA.getValueAt(ligne, 2);
        data[i][3]=GRID_DATA.getValueAt(ligne, 3);
        data[i][4]=GRID_DATA.getValueAt(ligne, 4);
        data[i][5]=GRID_DATA.getValueAt(ligne, 5);
        data[i][6]=GRID_DATA.getValueAt(ligne, 6);
        data[i][7]=GRID_DATA.getValueAt(ligne, 7);
        data[i][8]=GRID_DATA.getValueAt(ligne, 8);
        }
        transferData(data);
        //listData();
        listData();
    }//GEN-LAST:event_transferActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        // TODO add your handling code here:
        listData();
    }//GEN-LAST:event_formInternalFrameActivated

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        // TODO add your handling code here:
        //listData();
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void po_filterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_po_filterKeyReleased
        // TODO add your handling code here:
        buscados();
    }//GEN-LAST:event_po_filterKeyReleased

    private void style_filterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_style_filterKeyReleased
        // TODO add your handling code here:
        buscados();
    }//GEN-LAST:event_style_filterKeyReleased

    private void color_code_filterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_color_code_filterKeyReleased
        // TODO add your handling code here:
        buscados();
    }//GEN-LAST:event_color_code_filterKeyReleased

    private void color_filterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_color_filterKeyReleased
        // TODO add your handling code here:
        buscados();
    }//GEN-LAST:event_color_filterKeyReleased

    private void size_filterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_size_filterKeyReleased
        // TODO add your handling code here:
        buscados();
    }//GEN-LAST:event_size_filterKeyReleased

    private void gen_tagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gen_tagActionPerformed
        // TODO add your handling code here:
            int ligne=GRID_DATA.getSelectedRow();
        String po=GRID_DATA.getValueAt(ligne, 0).toString().trim();
        String style=GRID_DATA.getValueAt(ligne, 1).toString().trim();
        String color=GRID_DATA.getValueAt(ligne, 5).toString().trim();
        String size=GRID_DATA.getValueAt(ligne, 6).toString().trim();
        String code=GRID_DATA.getValueAt(ligne, 8).toString().trim();
        int qty=(Integer)GRID_DATA.getValueAt(ligne, 7);
        
        
        print(color,size,qty,code,po,style,getSew(code));
    }//GEN-LAST:event_gen_tagActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable GRID_DATA;
    private javax.swing.JPopupMenu POPUP;
    private javax.swing.JTextField color_code_filter;
    private javax.swing.JTextField color_filter;
    private javax.swing.JMenuItem gen_tag;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField po_filter;
    private javax.swing.JTextField size_filter;
    private javax.swing.JTextField style_filter;
    private javax.swing.JMenuItem transfer;
    // End of variables declaration//GEN-END:variables
    private Observateurs observa;
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
        for(Observateurs o:obs)
            o.executer(ob);
    }
    
    private void print(String color,String size,int qty,String code,String pot,String stylet,int no_bundle){
       
        String requete= "select [cut no] , brand,proto from orderplan where ordnum_147=?";
        ResultSet rs=conn.select(requete, code);
        try {
            while(rs.next()){
                if(rs.getString("brand").trim().equals("EDG")){
                    pot=pot+"("+rs.getString("cut no").trim()+")";
                }
                if(rs.getString("brand").trim().equals("HWY")){
                    stylet+="("+rs.getString("proto").trim()+")";
                }
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ready_to_sew.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{ 
            URL  master= this.getClass().getResource("report/card_layout.jasper");
            int nb=0;
            boolean ver=false;
            if(qty<50){
                nb=1;
                ver=true;
            }
            else{
                if(qty%50==0 || qty%50<19)
                    nb=qty/50;
                else{
                    nb=(int)Math.ceil(qty/50)+1;
                    ver=true;
                }
            }
                  Map param = new HashMap();
                  param.put("tickets",nb);
                  param.put("bundle_no",""+no_bundle);
                  
                  ArrayList<data_layout> da=new ArrayList<>();
                  for(int i=0;i<nb;i++){
                      data_layout d=new data_layout();
                      d.setPo(pot);
                      d.setColor(color);
                      d.setSize(size);
                      d.setStyle(stylet);
                      d.setQty(50);
                      d.setNb(nb);
                      if(i==nb-1){
                        if(!ver && qty%50>0){
                            d.setQty(50+(qty%50));
                        }
                      }
                      if(ver){
                      if(i==nb-1)
                      d.setQty(qty%50);
                      }
                      d.setTicket_num(i+1);
                      da.add(d);
                  }
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
                      
                      JasperExportManager.exportReportToPdfFile(jasperPrint, System.getProperty("user.home").concat("/Documents/new travel card/")+code+".pdf");
                  
                  Desktop dsk=Desktop.getDesktop();
                  dsk.open(new File(System.getProperty("user.home").concat("/Documents/new travel card/")+code+".pdf"));
                  
 
                }
 
                catch (Exception e)
                 {
                     e.printStackTrace();
                     System.out.println("Mensaje de Error:"+e.getMessage());
                     JOptionPane.showMessageDialog(this, e.getMessage());
                 }
    }
    
}
