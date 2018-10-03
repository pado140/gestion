package Load_data;



import connection.ConnectionDb;
import java.awt.Font;
import java.awt.Frame;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import observateurs.Observateurs;


public class loadFrame extends javax.swing.JDialog implements Observateurs{
     private final ConnectionDb conn=ConnectionDb.instance();
     private Map<String,Map<String,Integer>> listData;

    public loadFrame(Frame owner, boolean modal) {
        super(owner, modal);
        initComponents();
        
       grid_po.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 13 ));
       //this.setVisible(true);
    }

  
    public loadFrame() {
        initComponents();
        
       grid_po.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 13 ));
       this.setVisible(true);
    }
    

    
        public void mostrardata1(List<Object[]> ob) {
            int initPoline=(int)Double.parseDouble(ob.get(0)[16].toString());
            String initPo=ob.get(0)[0].toString();
            String initStyle=ob.get(0)[1].toString();
            String initCol=ob.get(0)[2].toString();
            String initColor=ob.get(0)[3].toString();
            String initSize=ob.get(0)[4].toString();
            String initDesc=ob.get(0)[6].toString();
            
            DefaultTableModel tbm = (DefaultTableModel) grid_po.getModel();
            tbm.setRowCount(0);
            int total=0;
            System.out.println(ob.size());
            
            for(Object[] o:ob){
                if((int)Double.parseDouble(o[16].toString())!=initPoline){
                    tbm.addRow(new Object[]{initPo,initStyle,initCol,initColor,initSize,total,initDesc});
                    total=0;
                    initPoline=(int)Double.parseDouble(o[16].toString());
                    initPo=o[0].toString();
                    initStyle=o[1].toString();
                    initCol=o[2].toString();
                    initColor=o[3].toString();
                    initSize=o[4].toString();
                    initDesc=o[6].toString();
                }
                System.out.println(o[16].toString());
                total+=(int)Double.parseDouble(o[5].toString());
            }
            tbm.addRow(new Object[]{initPo,initStyle,initCol,initColor,initSize,total,initDesc});
            
 txt_box.setText(String.valueOf(grid_po.getRowCount()));
        }
        
    public void mostrardata(List<Object[]> ob) {
        conn.setErreur(null);
        Set<String> skus=new HashSet<>();
            //int initPoline=(int)Double.parseDouble(ob.get(0)[16].toString());
            String initPo,initStyle,initCol,initColor,initSize,initDesc,initWh,initAbc,desc;
            
            DefaultTableModel tbm = (DefaultTableModel) grid_po.getModel();
            tbm.setRowCount(0);
            int total;
            //System.out.println(ob.size());
            listData= new HashMap<>();
            Map<String,Integer> listLpn;
            for(Object[] o:ob){
                String sku=o[0].toString().substring(o[0].toString().indexOf("-")+1).trim().concat("."+o[1].toString().trim()).concat("."+o[2].toString().trim()
                .concat("."+o[4].toString().trim()));
                if(!skus.contains(sku)){ 
                    skus.add(sku);
                    listLpn = new HashMap<>();
                    //System.out.println("verifications:"+!skus.contains(sku));
                    total=0;
                    //initPoline=(int)Double.parseDouble(o[16].toString());
                    initPo=o[0].toString().substring(o[0].toString().indexOf("-")+1).trim();
                    initStyle=o[1].toString();
                    initCol=o[2].toString();
                    initColor=o[3].toString();
                    initSize=o[4].toString();
                    initDesc=o[6].toString();
                    desc=initDesc;
                    if(initDesc.toLowerCase().contains("youth"))
                        desc=initDesc.replace("YOUTH ", "");
                    else if(initDesc.toLowerCase().contains("yth"))
                        desc=initDesc.replace("YTH ", "");
                    else if(initDesc.toLowerCase().contains("girls"))
                        desc=initDesc.replace("GIRLS ", "");
                    else if(initDesc.toLowerCase().contains("ladies"))
                        desc=initDesc.replace("LADIES ", "");
                    else if(initDesc.toLowerCase().contains("lds"))
                        desc=initDesc.replace("LDS ", "");
                    else if(initDesc.toLowerCase().contains("adult"))
                        desc=initDesc.replace("ADULT ", "");
                    initWh=o[7].toString().trim();
                    initAbc=o[9].toString().trim();
                for(Object[] o1:ob){
                    String sku1=o1[0].toString().substring(o1[0].toString().indexOf("-")+1).trim().concat("."+o1[1].toString().trim()).concat("."+o1[2].toString().trim()
                .concat("."+o1[4].toString().trim()));
                    if(sku.equals(sku1)){
                     listLpn.put(o1[8].toString(), (int)Double.parseDouble(o1[5].toString()));
                    //System.out.println(o[16].toString());
                    total+=(int)Double.parseDouble(o1[5].toString());  
                    }
                }
                listData.put(sku, listLpn);
                tbm.addRow(new Object[]{initPo,initStyle,initCol,initColor,initSize,total,initDesc,desc,initAbc,initWh});
                }
                
            }
            //tbm.addRow(new Object[]{initPo,initStyle,initCol,initColor,initSize,total,initDesc});
            
 txt_box.setText(String.valueOf(grid_po.getRowCount()));
        }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        grid_po = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_box = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_piece = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        date_plan = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        plan = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        grid_po.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PO NUM", "STYLE", "COL", "COLDSP", "SIZE", "QTY", "DESCRIP", "DESC", "CLASS", "W/H"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grid_po.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(grid_po);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Qtity of LINE:");

        txt_box.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_box.setText("......");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Qtity of pieces:");

        txt_piece.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_piece.setText("......");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txt_box))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txt_piece)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_box))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_piece))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jButton2.setText("Clear Table");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("DATABASE COMPARAISON");

        jButton4.setText("SAVE ORDER");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Date Plan");

        plan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--SELECTED--", "HWY", "AUG" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1006, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(plan, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(date_plan, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(date_plan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(22, 22, 22))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(plan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        DefaultTableModel tbm = (DefaultTableModel) grid_po.getModel();
        tbm.setRowCount(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Save();
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private boolean saveLpn(String ordnum,String lpn,String sticker,int qty){
        String requete="INSERT INTO BOX_CONTAIN(ORDNUM,LPN,BOX_STICKERS,QTY) VALUES (?,?,?,?)";
        return conn.Update(requete, 0, ordnum,lpn,sticker,qty);
    }
    private String Order_Exist(String Po,String part_id){
         try {
             String query="select ordnum_147 from shoporder where ordref_147=? and prtnum_147=?";
             ResultSet rs=conn.select(query, Po,part_id);
             while(rs.next())
             return rs.getString(1);
            
         } catch (SQLException ex) {
             Logger.getLogger(loadFrame.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return null;
    }
    
    public boolean saveOrder(String po,String ord,String partid,Date created,int qty,String plan,String key,String udf,String classe){
        String query="INSERT INTO Order_Master (ORDNUM_10,LINNUM_10,DELNUM_10,PRTNUM_10,CURDUE_10,RECFLG_10,TAXABLE_10"+
           ",TYPE_10,ORDER_10,VENID_10,ORGDUE_10,PURUOM_10,CURQTY_10,ORGQTY_10,DUEQTY_10,CURPRM_10,FILL03_10,ORGPRM_10,FILL04_10"+
           ",FRMPLN_10,STATUS_10,STK_10,CUSORD_10,PLANID_10,BUYER_10,PSCRAP_10,ASCRAP_10,SCRPCD_10,SCHCDE_10,REVLEV_10,COST_10"+
           ",CSTCNV_10,APRDBY_10,ORDREF_10,SCHFLG_10,CRTRAT_10,NEGATV_10,REQPEG_10,MPNNUM_10,LABOR_10,AMMEND_10,LOTNUM_10,BEGSER_10"+
           ",REWORK_10,CRTSNS_10,TTLSNS_10,FORCUR_10,EXCESS_10,UOMCST_10,UOMCNV_10,INSREQ_10,CREDTE_10,RTEREV_10,RTEDTE_10"+
           ",COMCDE_10,ORDPTP_10,JOBEXP_10,JOBCST_10,TAXCDE_10,TAX1_10,GLREF_10,CURR_10,UDFKEY_10,UDFREF_10,DISC_10,RECCOST_10"+
           ",MPNMFG_10,DEXPFLG_10,PLSTPRNT_10,ROUTPRNT_10,REQUES_10,ALTBOM_10,ALTRTG_10,CLASS_10,JOB_10,SUBSHP_10) "+
            "VALUES (?,'0','0',?,?,'N','N','MS','"+ord+"0000','',?,'',?,?,?,?,'',?,'','N','3','FG1-FP1',?,?,'',0,0,'N','B','',"+
           "0,1,'',?,'','','N','','',0,'N','','','Y','N',0,0,0,0,0,'',?,'',NULL,'SKU','M','Y',0,'',0,'','',?,?,0,0,'','N'"+
            ",'N','N','','','',?,'',0)";
      
      return conn.Update(query,1, ord,partid,created,created,qty,qty,qty,created,created,po,plan,po.split("_")[0],created,key,udf,classe);
      
    }
    
    private String[] Prtid_Exist(String style){
         try {
             String query="select prtnum_01,planid_01 from part_master where prtnum_01=?";
             ResultSet rs=conn.select(query, style);
             while(rs.next())
             return new String[]{rs.getString(1),rs.getString(1)};
            
         } catch (SQLException ex) {
             Logger.getLogger(loadFrame.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return null;
    }
    
    private String savePartid(String partid,String brand,String Descrip,String Desc,String style,String wh,String upc,
                                String size,String color){
        String requete="INSERT INTO Part_Master(PRTNUM_01,TYPE_01,CLSCDE_01,PLANID_01,COMCDE_01,LLC_01,PMDES1_01" +
                       ",PMDES2_01,BOMUOM_01,STAENG_01,STAACT_01,FRMPLN_01,PRDDTE_01,FILL01_01,OBSDTE_01,FILL02_01" +
                       ",WGTDEM_01,WGT_01,EXCDTE_01,FILL03_01,EXCFLG_01,DRANUM_01,DELSTK_01,CYCCDE_01,CYCDTE_01,FILL04_01" +
                       ",CYCNUM_01,CYCPER_01,OBSOLT_01,CYCOOT_01,ORDPOL_01,YIELD_01,TNXDTE_01,FILL05_01,ROP_01" +
                       ",ROQ_01,SAFSTK_01,MINQTY_01,MAXQTY_01,MULQTY_01,AVEQTY_01,ISSMTD_01,ISSYTD_01,SALMTD_01" +
                       ",SALYTD_01,MFGLT_01,MFGPIC_01,MFGOPR_01,MFGSTK_01,PURLT_01,PURPIC_01,PUROPR_01,PURSTK_01" +
                       ",PRICE_01,COST_01,CSTTYP_01,CSTDTE_01,FILL06_01,CSTUOM_01,CSTCNV_01,MATL_01,LABOR_01,VOH_01" +
                       ",FOH_01,QUMMAT_01,QUMLAB_01,QUMVOH_01,QUMFOH_01,HRS_01,QUMHRS_01,ALPHA_01,QUMSUB_01,PURUOM_01" +
                       ",PURCNV_01,SCRAP_01,BUYER_01,INSRQD_01,ONHAND_01,NONNET_01,SCHCDE_01,REVLEV_01,ACTTYP_01" +
                       ",ACTCDE_01,SCHFLG_01,MPNFLG_01,MATLXY_01,CRPHLT_01,LOTTRK_01,MULREC_01,SERTRK_01,LOTSFC_01" +
                       ",SHLIFE_01,DELLOC_01,SUBCST_01,PERDAY_01,LSTECN_01,CURREV_01,RECVEN_01,RTEREV_01,RTEDTE_01,ALLOC_01" +
                       ",JOBEXP_01,RNDRQS_01,EXCREC_01,INDDEM_01,VIEWER_01,MCOMP_01,MSITE_01,UDFKEY_01,UDFREF_01,SUPCDE_01" +
                       ",CYCDOL_01,PRTGRP_01,SNSFC_01,CURBOM_01,CURRTG_01,FILLER_01) " +
                       "VALUES (?,'M','C',?,'SKU',0,?,?,'EA',2,2,'N',GETDATE(),'',NULL,'','OZ',0,GETDATE(),'','N',?,'FG1-FP1'" +
                       ",'N',NULL,'',0,0,0,0,'L',100,NULL,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'A',GETDATE(),'','EA'" +
                       ",1,0,0,0,0,0,0,0,0,0,0,0,0,'EA',1,0,'','N',0,0,'B','','A','1','Q','N',0,0,'N','N','N','N',0,?,0,0,''" +
                       ",'','','',NULL,0,'Y','N',0,'N','','','',?,?,'N',0,'','N','','',?)";
        if(conn.Update(requete, 0, partid,brand,Desc,Descrip,style,wh,upc,color,size))
            return partid;
            
        return null;
    }
    public boolean saveStyle(String style,String desc,int st){
      String query="insert into proto_style( code,description,status) values(?,?,?)";
      Object[] ob={style,desc,st};
      
      return conn.Update(query,1, ob);
    }
    
    private String lastOrdnum(){
         try {
             String query="select max(ordnum_147) ordernum from shoporder";
             ResultSet rs=conn.select(query);
             while(rs.next())
             return rs.getString(1);
            
         } catch (SQLException ex) {
             Logger.getLogger(loadFrame.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return null;
    }
    
    private void Save(){
        Map<String,String> errors=new HashMap<>();
        String ord=lastOrdnum();
        int ordernum=Integer.parseInt(ord);
        String partid;
        for(int a=0;a<grid_po.getRowCount();a++){
            String po=grid_po.getValueAt(a, 0).toString().trim();
            int qty=Integer.parseInt(grid_po.getValueAt(a, 5).toString());
            po=po.substring(po.indexOf("-")+1);
            String classe=grid_po.getValueAt(a, 8).toString();
            String description=grid_po.getValueAt(a, 6).toString();
            String desc=grid_po.getValueAt(a, 7).toString();
            String style=grid_po.getValueAt(a, 1).toString().trim();
            String size=grid_po.getValueAt(a, 4).toString().trim();
            String wh=grid_po.getValueAt(a, 9).toString();
            String color=grid_po.getValueAt(a, 3).toString();
            String sku1,sku=grid_po.getValueAt(a, 1).toString().trim();
            sku+="."+grid_po.getValueAt(a, 2).toString().trim();
            sku+="."+grid_po.getValueAt(a, 4).toString().trim();
            
            sku1=po+"."+grid_po.getValueAt(a, 1).toString().trim();
            sku1+="."+grid_po.getValueAt(a, 2).toString().trim();
            //System.out.println(sku);
            
            String ord1;
            try{
            partid=Prtid_Exist(sku)[0];
            }catch(NullPointerException e){
                partid=savePartid(sku,plan.getSelectedItem().toString(),description,desc,style,wh,sku,size,color);
                if(conn.getErreur()!=null){
                    errors.put(sku, conn.getErreur());
                }
            }
            
            ord1=Order_Exist(po,partid);
                if(ord1==null){
                    ordernum++;
                    ord1=String.valueOf(ordernum);
                    //String key=Integer.parseInt(wh)>0?"":wh;
                    saveOrder(po, ord1, partid,date_plan.getDate(), qty, plan.getSelectedItem().toString(),classe, sku1, classe);
                    if(conn.getErreur()!=null){
                    errors.put(ord1, conn.getErreur());
                }
                }
                if(partid!=null && ord1!=null){
                    int inc=1;
                    for(String lpn:listData.get(po.concat(".").concat(sku)).keySet()){
                    
                    String ind=String.valueOf(inc);
                    int qt=listData.get(po.concat(".").concat(sku)).get(lpn);
                    String sticker=ord1+"000".substring(ind.length())+ind;
                    conn.savecst("{call create_box(?,?,?,?,?,?,?)}",ord1,lpn,"",qt,qt,sticker,"");
                    if(!saveLpn(ord1,lpn,sticker,listData.get(po.concat(".").concat(sku)).get(lpn))){
                        //System.err.println(conn.getErreur());
                    errors.put(lpn, conn.getErreur());
                    }
                    inc++;
                    //System.out.println(sticker+"\t"+lpn);
                    }
                }
        }
        if(errors.isEmpty())
            JOptionPane.showMessageDialog(this, "Save without warnings");
        else
            JOptionPane.showMessageDialog(this, "Save with errors");
    }
    

    
    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser date_plan;
    private javax.swing.JTable grid_po;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox plan;
    private javax.swing.JLabel txt_box;
    private javax.swing.JLabel txt_piece;
    // End of variables declaration//GEN-END:variables

    @Override
    public void executer(Object... obs) {
        if(obs[0].equals("pivot2"))
        {
            mostrardata((List<Object[]>) obs[1]);
        }
    }
}
