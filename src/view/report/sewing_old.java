/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.report;

import codebar.Barcode;
import codebar.BarcodeFactory;
import codebar.BarcodeType;
import connection.ConnectionDb;
import java.awt.Desktop;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import view.Principal;
import view.Work_status;

/**
 *
 * @author Padovano
 */
public class sewing_old extends javax.swing.JInternalFrame {
private ConnectionDb conn = ConnectionDb.instance();
    private Map<String , Integer> prod=new HashMap<>(),plan;
    private Map<String, String> ref=null;
    private Set<Object[]> liste=null;
    /**
     * Creates new form sewing
     */
    public sewing_old() {
        initComponents();
        listData();
    }
    
    /**
     * Creates new form Ready_to_sew
     */

    private Map<String ,Integer> saoBar(){
        String requete="Select po,sku, sum(qty) qty from at_soabar group by po,sku";
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
        String requete="select ordnum_147,count(type_id) n from cutplan_maker where type_id<>4 group by ordnum_147 ";
        ResultSet rs=conn.select(requete);
        try {
            while(rs.next()){
                typ.put(rs.getString("ordnum_147").trim(),rs.getInt("n"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(sewing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return typ;
    }
    private Map<String,Integer> listType(){
        String query="select Distinct style,count([TYPE]) t FROM StyleMaster where type_id<>4 group by style";
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
               Logger.getLogger(sewing.class.getName()).log(Level.SEVERE, null, ex);
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
                   typ=listType.get(style);
               }catch(NullPointerException e){
                   typ=0;
               }
               try{
                   ty=typecount.get(style);
               }catch(NullPointerException e){
                   ty=0;
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
            Logger.getLogger(sewing.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(sewing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private boolean transferData(Object[][] data){
        String requete="insert into soabar(order_num,SEWING_TRAVElLER,stickers,QTY,user_id) values(?,?,?,?,?)";
        String requete1="insert into tRANSAC(TRANSACT,ITEM,QTY,ACT_TYPE,ACT_NAME,[DATE_T],User_id) values ('to soabar',?,?,1,'transfer to soabar',"
                + "getdate(),?)";
        //String requete2="insert into Soabar(order_num,SEWING_TRAVElLER,stickers,QTY,user_id) values(?,?,?,?,?)";
            for(int i=0;i<data.length;i++){
                
            if(this.conn.Update(requete, 1, new Object[]{data[i][8],data[i][0]+"."+data[i][1]+"."+data[i][4]+"."+data[i][6],
                data[i][8].toString()+getSew(data[i][8].toString()),data[i][7],Principal.user_id})){
                this.conn.Update(requete1, 1, new Object[]{data[i][8],data[i][7],Principal.user_id});
                //this.conn.Update(requete2, 0,data[i][8]);
                
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
        //if(!po.getText().trim().isEmpty()&&!style.getText().trim().isEmpty()&&!col.getText().trim().isEmpty()){
        ResultSet rs = conn.select(requete);
        int soa;
        try {
            while(rs.next()){
                String sku=rs.getString("sku").trim();
                String client=rs.getString("brand").trim();
               
                String size=sku.replace('.','-').split("-")[2];
                String desc=rs.getString("description").trim(),
                        po=rs.getString("po").trim();
                if(desc.toLowerCase().contains("yth")||desc.toLowerCase().contains("youth")||
                        desc.toLowerCase().contains("girls")||desc.toLowerCase().contains("boys"))
                size="Y"+size;
                try{
                    soa=0;
                    try{
                        soa=soabar.get(po.trim()+"."+sku.trim());
                    }catch(NullPointerException e){
                        soa=0;
                    }
                    if(prod.get(po.trim()+"."+sku.trim())>=0){
                        //if(prod.get(po.trim()+"."+sku.trim())<=plan.get(po.trim()+"."+sku.trim())){
                        //if("9576718.2905.060.L".equals(po.trim()+"."+sku.trim()))
                        System.out.println("cut-soa="+(prod.get(po.trim()+"."+sku.trim())-soa));
                            if(prod.get(po.trim()+"."+sku.trim())-soa>0){
                            tbm.addRow(new Object[]{client,po,rs.getString("style").trim(),rs.getString("color_code").trim()+"-"+rs.getString("color").trim(),
                    size.trim(),prod.get(po.trim()+"."+sku.trim())-soa,rs.getString("ORDNUM_147"),false});
                          liste.add(new Object[]{client,po,rs.getString("style").trim(),rs.getString("color_code").trim(),rs.getString("color").trim(),
                    size.trim(),prod.get(po.trim()+"."+sku.trim())-soa,rs.getString("ORDNUM_147"),false}); 
                            }
                        //}
                    }
                
                }catch(NullPointerException e){
                    
                }
                
            }   
            
        } catch (SQLException ex) {
            Logger.getLogger(Work_status.class.getName()).log(Level.SEVERE, null, ex);
        }
        //}   
        //}   
        //}   
        //}   
        
    }
    
    private void search(){
        String potexte=po.getText().trim().toLowerCase();
        String styletexte=style.getText().trim().toLowerCase();
        String colortexte=col.getText().trim().toLowerCase();
        DefaultTableModel tbm = (DefaultTableModel) GRID_DATA.getModel();
        tbm.setRowCount(0);
        for(Object[] o: liste){
            if(o[1].toString().trim().toLowerCase().contains(potexte)&& o[2].toString().trim().toLowerCase().contains(styletexte)&&o[3].toString().trim().toLowerCase().contains(colortexte))
                tbm.addRow(o);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        po = new javax.swing.JTextField();
        style = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        col = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        GRID_DATA = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        setTitle("sewing traveler");

        jLabel1.setText("PO FILTER");

        po.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                poKeyReleased(evt);
            }
        });

        style.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                styleKeyReleased(evt);
            }
        });

        jLabel2.setText("STYLE FILTER");

        jLabel4.setText("COLOR FILTER");

        col.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                colKeyReleased(evt);
            }
        });

        jButton1.setText("search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(po, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(style, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(col, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(85, 85, 85)
                .addComponent(jButton1)
                .addContainerGap(776, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(po, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(style, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(col, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        GRID_DATA.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "CUSTOMER", "PO", "STYLE", "COLOR", "SIZE", "QUANTITY", "CODE", "CHECK"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(GRID_DATA);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void poKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_poKeyReleased
        // TODO add your handling code here:
        search();
    }//GEN-LAST:event_poKeyReleased

    private void styleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_styleKeyReleased
        // TODO add your handling code here:
        search();
    }//GEN-LAST:event_styleKeyReleased

    private void colKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_colKeyReleased
        // TODO add your handling code here:
        search();
    }//GEN-LAST:event_colKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //listData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        ArrayList<Data> listdata=new ArrayList<>();
                  String color=null;
                  String color_code=null;
                  String size=null;
                  String qty=null;
                  String code=null;
                  String pot=null;
                  String stylet=null;
        for(int i=0;i<GRID_DATA.getSelectedRowCount();i++){
        int ligne=GRID_DATA.getSelectedRows()[i];
        
        color=GRID_DATA.getValueAt(ligne, 3).toString().trim();
        pot=GRID_DATA.getValueAt(ligne, 1).toString().trim();
        stylet=GRID_DATA.getValueAt(ligne, 2).toString().trim();
        size=GRID_DATA.getValueAt(ligne, 4).toString().trim();
        qty=GRID_DATA.getValueAt(ligne, 5).toString().trim();
        code=GRID_DATA.getValueAt(ligne, 6).toString().trim();
        color_code=color.substring(0,color.indexOf("-"));
        color=color.substring(color.indexOf("-")+1);
        
        
        Data d=new Data();
                  d.setSize(size);
                  d.setCode(code);
                  d.setQty(Integer.parseInt(qty));
                  listdata.add(d);
                  Barcode b=BarcodeFactory.createBarcode(BarcodeType.Code128,code);
        b.export("jpg", 1, 50 ,true, "src/report/barcode/"+code+".jpg");
        //}
        try{ 
            File source = new File("src/report/newReport.jrxml");
            //JasperDesign jasperDesign=JRXmlLoader.load(getClass().getResourceAsStream("/report/newReport.jrxml"));
            System.out.println("file:"+source.getAbsolutePath());
            
            String po_sku=pot+"."+stylet+"."+color_code+"."+size+"."+qty;
                  Map param = new HashMap();
                  //param.put("PO",po.getText().trim());
                  //param.put("style",style.getText().trim());
                  param.put("COLOR",color);
                  param.put("COL",color_code);
                  //param.put("sizes",size);
                  //param.put("qtity",qty);
                  param.put("codes",code);
                  param.put("posku",po_sku);
                  JasperReport jasperReport = JasperCompileManager.compileReport(source.getAbsolutePath());
                  JRBeanCollectionDataSource beanColDataSource =new JRBeanCollectionDataSource(listdata);
                  //String report=JasperFillManager.fillReportToFile(source.getAbsolutePath(),param,beanColDataSource);
                  JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,param,beanColDataSource);
                  JasperViewer.viewReport(jasperPrint);
                  JasperExportManager.exportReportToPdfFile(jasperPrint, "src/report/barcode/"+code+".pdf");
                  
                  //Desktop dsk=Desktop.getDesktop();
                  //dsk.open(new File("src/report/barcode/data.pdf"));
                  //dsk.
                  //jviewer.setFitPageZoomRatio();
                  /*if(report!=null){
                  JasperExportManager.exportReportToPdfFile(report,
                  "C://users/Padovano/Desktop/sample_report.pdf");
                  }*/
 
                }
 
                catch (Exception e)
                 {
                     e.printStackTrace();
                     System.out.println("Mensaje de Error:"+e.getMessage());
                 }   
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable GRID_DATA;
    private javax.swing.JTextField col;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField po;
    private javax.swing.JTextField style;
    // End of variables declaration//GEN-END:variables
}
