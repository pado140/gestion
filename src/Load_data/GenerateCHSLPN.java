package Load_data;



import connection.ConnectionDb;
import java.awt.Font;
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


public class GenerateCHSLPN extends javax.swing.JDialog implements Observateurs{
     private final ConnectionDb conn=ConnectionDb.instance();
     private Map<String,Map<String,Integer>> listData;
     private final String prefix="50918";
     private final String suffix="2345670";
     private List<Object[]> data;
     private int serial;
  
    public GenerateCHSLPN() {
        initComponents();
        
       grid_po.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 13 ));
       this.setVisible(true);
    }
    //private 
    private String getLast(){
        String requete="select max(serial) serial from serial_lpn_chs";
        ResultSet rs=conn.select(requete);
         try {
             while(rs.next())
                 return rs.getString("serial");
         } catch (SQLException ex) {
             Logger.getLogger(GenerateCHSLPN.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }
        
    public void mostrardata(List<Object[]> ob) {
        Set<String> skus=new HashSet<>();
        String newSuffix=getLast();
        if(newSuffix==null){
            newSuffix=suffix;
        }
            //int initPoline=(int)Double.parseDouble(ob.get(0)[16].toString());
            String initPo,initStyle,initCol,initColor,initSize,initDesc,initupc,initAbc,desc;
            int initpackaging;
            DefaultTableModel tbm = (DefaultTableModel) grid_po.getModel();
            tbm.setRowCount(0);
            System.out.println(ob.size());
            listData= new HashMap<>();
            Map<String,Integer> listLpn;
            serial=Integer.parseInt(newSuffix);
            for(Object[] o:ob){
                    listLpn= new HashMap<>();
                    initPo=o[0].toString().substring(o[0].toString().indexOf("-")+1).trim();
                    initStyle=o[1].toString();
                    //initCol=o[2].toString();
                    initColor=o[2].toString();
                    initSize=o[3].toString();
                    String size_withoutg=initSize.substring(1);
                    initDesc=o[5].toString();
                    desc=initDesc;
                    int qty=(int)Double.parseDouble(o[4].toString().trim());
                    initupc=o[6].toString().trim();
                    initpackaging=(int)Double.parseDouble(o[7].toString().trim());
                    int boxes=qty/initpackaging;
                    String item=o[8].toString().trim();
                    String colsize=item.substring(initStyle.trim().length()+1);
                    initCol=colsize.substring(0,colsize.lastIndexOf(size_withoutg.substring(0, 1)));
                    if(initSize.startsWith("W")||initSize.startsWith("A"))
                        initSize=initSize.substring(1);
                    if(initSize.startsWith("G"))
                        initSize=initSize.replaceFirst("G", "Y");
                    for(int i=0;i<boxes;i++){
                        serial++;
                        String lpn=prefix+serial;
                        listLpn.put(lpn, initpackaging);
                tbm.addRow(new Object[]{initPo,initStyle,initCol,initColor,initSize,initpackaging,initDesc,lpn,initupc,item});
                }
                    listData.put(initPo+initupc, listLpn);
                
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
                "PO NUM", "STYLE", "COL", "COLDSP", "SIZE", "QTY", "DESCRIP", "LPN", "UPC", "ITEM"
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

        plan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--SELECTED--", "HWY", "AUG", "FSM", "CHS", "EDG" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1050, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)))
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
      
      return conn.Update(query,1, ord,partid,created,created,qty,qty,qty,created,created,po,plan,po,created,key,udf,classe);
      
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
        if(conn.Update(requete, 0, partid,brand,Descrip,Desc,style,wh,upc,color,size))
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
        for(Object[] o:data){
            String po=o[0].toString().substring(o[0].toString().indexOf("-")+1).trim();
            int qty=(int)Double.parseDouble(o[4].toString().trim());
            String description=o[5].toString();
            String desc=description;
            String style=o[1].toString().trim();
            String size=o[3].toString().trim();
            String size_withoutg=size.substring(1);
            String upc=o[6].toString().trim();  
            String item=o[8].toString().trim();
            String colsize=item.substring(style.trim().length()+1);
            String color=o[2].toString().trim();
            String sku1,sku=style;
            if(size.startsWith("W")||size.startsWith("A"))
                size=size.substring(1);
            if(size.startsWith("G"))
                size=size.replaceFirst("G", "Y");
            sku+="."+colsize.substring(0,colsize.lastIndexOf(size_withoutg.substring(0, 1)));;
            sku+="."+size;
            sku1=po+"."+style;
            sku1+="."+colsize.substring(0,colsize.lastIndexOf(size_withoutg.substring(0, 1)));
            
            String ord1;
            try{
            partid=Prtid_Exist(sku)[0];
            }catch(NullPointerException e){
                partid=savePartid(sku,plan.getSelectedItem().toString(),description,desc,style,"",upc,size,color);
                if(conn.getErreur()!=null){
                    errors.put(sku, conn.getErreur());
                }
            }
            
            ord1=Order_Exist(po,partid);
                if(ord1==null){
                    ordernum++;
                    ord1=String.valueOf(ordernum);
                    //String key=Integer.parseInt(wh)>0?"":wh;
                    saveOrder(po, ord1, partid,date_plan.getDate(), qty, plan.getSelectedItem().toString(),po, sku1, "");
                    if(conn.getErreur()!=null){
                    errors.put(ord1, conn.getErreur());
                }
                }
                if(partid!=null && ord1!=null){
                    int inc=1;for(String lpn:listData.get(po.concat(upc)).keySet()){
                    
                    String ind=String.valueOf(inc);
                    String sticker=ord1+"000".substring(ind.length())+ind;
                    if(!saveLpn(ord1,lpn,sticker,listData.get(po.concat(upc)).get(lpn)))
                        if(conn.getErreur()!=null){
                            errors.put(ord1, conn.getErreur());
                        }
                    inc++;
                    System.out.println(sticker+"\t"+lpn);
                    }
                }
        }
        saveSequence(""+serial);
        if(errors.isEmpty()){
            JOptionPane.showMessageDialog(this, "Save without warnings");
            
        }
        else
            JOptionPane.showMessageDialog(this, "Save with errors");
    }
    public boolean saveSequence(String serial){
      String query="insert into SERIAL_LPN_CHS(serial) values(?)";
      
      return conn.Update(query,1, serial);
    }
    
    private int Size_Exist(String size){
         try {
             String query="select * from dbo.sizes where size_desc=?";
             String [] critere={size};
             ResultSet rs=conn.select(query, size);
             while(rs.next())
             return rs.getInt(1);
            
         } catch (SQLException ex) {
             Logger.getLogger(loadFrame.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return 0;
    }
    
    public boolean saveSize(String size,String range){
      String query="insert into dbo.sizes( size_desc ,range) values(?,?)";
      Object[] ob={size,range};
      
      return conn.Update(query,1, ob);
    }
    
    private int item_exist(int color,int size,int style){
        try {
             String query="select * from dbo.items where id_color=? and id_size=? and id_style=?";
             Object [] critere={color,size,style};
             ResultSet rs=conn.select(query, critere);
             while(rs.next())
             return rs.getInt(1);
            
         } catch (SQLException ex) {
             Logger.getLogger(loadFrame.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return 0;
    }
    
    public boolean saveItem(int color,int size,int style){
      String query="insert into dbo.items( id_color ,id_size,id_style) values(?,?,?)";
      Object[] ob={color,size,style};
      
      return conn.Update(query,1, ob);
    }
    
    private boolean saveOl(int item,int po,int qty ,String lpn,int wh){
        String query="insert into dbo.order_lines( id_order ,id_item,qty,lpn,wh) values(?,?,?,?,?)";
      Object[] ob={po,item,qty,lpn,wh};
      
      return conn.Update(query,1, ob);
    }
    /*private int Color_Exist(String color){
    try {
    String query="select * from dbo.orders where number=?";
    String [] critere={Po};
    ResultSet rs=conn.select(query, critere);
    while(rs.next())
    return rs.getInt(1);
    
    } catch (SQLException ex) {
    Logger.getLogger(loadFrame.class.getName()).log(Level.SEVERE, null, ex);
    }
    return 0;
    }
    
    public boolean saveColor(String po){
    String query="insert into dbo.orders( number,created,delivery_date) values(?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
    String[] ob={po};
    
    return conn.Update(query, ob);
    }*/
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(loadFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(loadFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(loadFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(loadFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new loadFrame().setVisible(true);
            }
        });
    }

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
        if(obs[0].equals("pivot1"))
        {
            data=(List<Object[]>) obs[1];
            mostrardata((List<Object[]>) obs[1]);
        }
    }
}
