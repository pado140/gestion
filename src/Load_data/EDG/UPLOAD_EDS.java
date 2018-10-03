package Load_data.EDG;



import Load_data.loadFrame;
import connection.ConnectionDb;
import java.awt.Font;
import java.awt.Frame;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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


public class UPLOAD_EDS extends javax.swing.JDialog implements Observateurs{
     private final ConnectionDb conn=ConnectionDb.instance();
     private Map<String,Map<String,Integer>> listData;
     DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

    public UPLOAD_EDS(Frame owner, boolean modal) {
        super(owner, modal);
        initComponents();
        
       grid_po.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 13 ));
       //this.setVisible(true);
    }

     
    public UPLOAD_EDS() {
        initComponents();
        
       grid_po.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 13 ));
       this.setVisible(true);
    }
    

    public void mostrardata(List<Object[]> ob) {
        Set<String> skus=new HashSet<>();
            //int initPoline=(int)Double.parseDouble(ob.get(0)[16].toString());
            String initPo,initStyle,initCol,initColor,initSize,initDesc,initCUT,desc,date;
            double price;
            
            DefaultTableModel tbm = (DefaultTableModel) grid_po.getModel();
            tbm.setRowCount(0);
            int total;
            System.out.println(ob.size());
            listData= new HashMap<>();
            
            for(Object[] o:ob){
                int aj=0;
                if(o.length==12)
                    aj=1;
                String size=o[5].toString().trim();
                String po=o[0].toString().substring(o[0].toString().indexOf("-")+1).trim();
                if(aj==1){
                    if(!o[6].toString().trim().isEmpty()){
                        if(o[6].toString().trim().startsWith("U"))
                            size+=o[6].toString().trim();
                        else{
                            size+="X"+o[6].toString().trim();
                            po+="_1";
                        }
                    }
                }
                String sku=po.concat("."+o[2].toString().trim()).concat("."+o[3].toString().trim()
                .concat("."+size));
                if(!skus.contains(sku)){
                    skus.add(sku);
                    Map<String,Integer> listLpn = new HashMap<>();
                    System.out.println("verifications:"+!skus.contains(sku));
                    total=0;
                    //initPoline=(int)Double.parseDouble(o[16].toString());
                    initPo=po;
                    initStyle=o[2].toString();
                    initCol=o[3].toString();
                    if(initCol.contains("O10"))
                        initCol="010";
                    initColor=o[4].toString();
                    initSize=size;
                    initDesc=o[8+aj].toString();
                    initCUT=o[1].toString();
                    desc=initDesc;
                    price=Double.parseDouble(o[10+aj].toString());
                    date=o[9+aj].toString();
                    if(initDesc.toLowerCase().contains("youth"))
                        desc=initDesc.replace("YOUTH", "");
                    else if(initDesc.toLowerCase().contains("yth"))
                        desc=initDesc.replace("YTH", "");
                    else if(initDesc.toLowerCase().contains("girls"))
                        desc=initDesc.replace("GIRLS", "");
                    else if(initDesc.toLowerCase().contains("ladies"))
                        desc=initDesc.replace("LADIES", "");
                for(Object[] o1:ob){
                    String size1=o1[5].toString().trim();
                String po1=o1[0].toString().substring(o1[0].toString().indexOf("-")+1).trim();
                if(aj==1){
                if(!o1[6].toString().trim().isEmpty()){
                        if(o1[6].toString().trim().startsWith("U"))
                            size1+=o1[6].toString().trim();
                        else{
                            size1+="X"+o1[6].toString().trim();
                            po1+="_1";
                        }
                    }
                }
                String sku1=po1.concat("."+o1[2].toString().trim()).concat("."+o1[3].toString().trim()
                .concat("."+size1));
                    System.out.println("sku:"+sku+"\t len:"+sku.length()+" vs sku1:"+sku1+"\t len:"+sku1.length());
                    if(sku.equals(sku1)){
                     listLpn.put(o1[6+aj].toString(), Integer.parseInt(o1[7+aj].toString()));
                    System.out.println(o1[6+aj].toString());
                    total+=Integer.parseInt(o1[7+aj].toString());  
                    }
                }
                listData.put(sku, listLpn);
                tbm.addRow(new Object[]{initPo,initStyle,initCol,initColor,initSize,total,initDesc,desc.trim(),initCUT,date,price});
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        grid_po.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PO NUM", "STYLE", "COL", "COLDSP", "SIZE", "QTY", "DESCRIP", "DESC", "CUT", "X FACT", "COST"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
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
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
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
             String query="select ordnum_147 from shoporder where cusord_147=? and prtnum_147=?";
             ResultSet rs=conn.select(query, Po,part_id);
             while(rs.next())
             return rs.getString(1);
            
         } catch (SQLException ex) {
             Logger.getLogger(loadFrame.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return null;
    }
    
    private boolean update(String order,int qty,Date xfact,double price){
        String query="update Order_Master set CURQTY_10=?,ORGQTY_10=?,DUEQTY_10=?,ORGDUE_10=?,COST_10=? where ORDNUM_10=?";
      return conn.Update(query,0,qty,qty,qty,xfact,price,order);
    }
    public boolean saveOrder(String po,String ord,String partid,Object created,int qty,String key,String udf,double price){
        String query="INSERT INTO Order_Master (ORDNUM_10,LINNUM_10,DELNUM_10,PRTNUM_10,CURDUE_10,RECFLG_10,TAXABLE_10"+
           ",TYPE_10,ORDER_10,VENID_10,ORGDUE_10,PURUOM_10,CURQTY_10,ORGQTY_10,DUEQTY_10,CURPRM_10,FILL03_10,ORGPRM_10,FILL04_10"+
           ",FRMPLN_10,STATUS_10,STK_10,CUSORD_10,PLANID_10,BUYER_10,PSCRAP_10,ASCRAP_10,SCRPCD_10,SCHCDE_10,REVLEV_10,COST_10"+
           ",CSTCNV_10,APRDBY_10,ORDREF_10,SCHFLG_10,CRTRAT_10,NEGATV_10,REQPEG_10,MPNNUM_10,LABOR_10,AMMEND_10,LOTNUM_10,BEGSER_10"+
           ",REWORK_10,CRTSNS_10,TTLSNS_10,FORCUR_10,EXCESS_10,UOMCST_10,UOMCNV_10,INSREQ_10,CREDTE_10,RTEREV_10,RTEDTE_10"+
           ",COMCDE_10,ORDPTP_10,JOBEXP_10,JOBCST_10,TAXCDE_10,TAX1_10,GLREF_10,CURR_10,UDFKEY_10,UDFREF_10,DISC_10,RECCOST_10"+
           ",MPNMFG_10,DEXPFLG_10,PLSTPRNT_10,ROUTPRNT_10,REQUES_10,ALTBOM_10,ALTRTG_10,CLASS_10,JOB_10,SUBSHP_10) "+
            "VALUES (?,'0','0',?,?,'N','N','MS','"+ord+"0000','',?,'',?,?,?,?,'',?,'','N','3','FG1-FP1',?,'EDG','',0,0,'N','B','',"+
           "?,1,'',?,'','','N','','',0,'N','','','Y','N',0,0,0,0,0,'',?,'',NULL,'SKU','M','Y',0,'',0,'','',?,?,0,0,'','N'"+
            ",'N','N','','','','','',0)";
      
        String Alias=po;
        if(po.contains("_"))
            Alias=po.substring(0, po.indexOf("_"));
      return conn.Update(query,1, ord,partid,created,created,qty,qty,qty,created,created,po,price,Alias,created,key,udf);
      
    }
    
    private String[] Prtid_Exist(String style){
         try {
             String query="select prtnum_01,planid_01 from part_master where prtnum_01=?";
             ResultSet rs=conn.select(query, style);
             while(rs.next())
             return new String[]{rs.getString(1).trim(),rs.getString(1).trim()};
            
         } catch (SQLException ex) {
             Logger.getLogger(loadFrame.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return null;
    }
    
    private boolean lpn_Exist(String lpn){
         try {
             String query="select count(lpn) from box_contain where lpn=?";
             ResultSet rs=conn.select(query, lpn);
             while(rs.next())
             return rs.getBoolean(1);
            
         } catch (SQLException ex) {
             Logger.getLogger(loadFrame.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return false;
    }
    
    private String savePartid(String partid,String Descrip,String Desc,String style,String upc,
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
                       "VALUES (?,'M','C','EDG','SKU',0,?,?,'EA',2,2,'N',GETDATE(),'',NULL,'','OZ',0,GETDATE(),'','N',?,'FG1-FP1'" +
                       ",'N',NULL,'',0,0,0,0,'L',100,NULL,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'A',GETDATE(),'','EA'" +
                       ",1,0,0,0,0,0,0,0,0,0,0,0,0,'EA',1,0,'','N',0,0,'B','','A','1','Q','N',0,0,'N','N','N','N',0,'',0,0,''" +
                       ",'','','',NULL,0,'Y','N',0,'N','','','',?,?,'N',0,'','N','','',?)";
        if(conn.Update(requete, 0, partid,Descrip,Desc,style,upc,color,size))
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
            String CUT=grid_po.getValueAt(a, 8).toString();
            String description=grid_po.getValueAt(a, 6).toString();
            String desc=grid_po.getValueAt(a, 7).toString();
            String style=grid_po.getValueAt(a, 1).toString().trim();
            String size=grid_po.getValueAt(a, 4).toString().trim();
            
            String color=grid_po.getValueAt(a, 3).toString();
            String sku1,sku=grid_po.getValueAt(a, 1).toString().trim();
            sku+="."+grid_po.getValueAt(a, 2).toString().trim();
            sku+="."+grid_po.getValueAt(a, 4).toString().trim();
            
            sku1=po+"."+grid_po.getValueAt(a, 1).toString().trim();
            sku1+="."+grid_po.getValueAt(a, 2).toString().trim();
            sku1+="."+grid_po.getValueAt(a, 4).toString().trim();
            //System.out.println(sku);
            //Object da=grid_po.getValueAt(a, 9);
            Date da=null;
            try {
                da = dateFormat.parse(grid_po.getValueAt(a, 9).toString());
            } catch (ParseException ex) {
               Logger.getLogger(UPLOAD_EDS.class.getName()).log(Level.SEVERE, null, ex);
            }
            String ord1;
            double price=0;
            try{
            partid=Prtid_Exist(sku)[0];
                System.out.println("part master:"+partid);
            }catch(NullPointerException e){
                partid=savePartid(sku,description,desc,style,sku,size,color);
                if(conn.getErreur()!=null){
                    errors.put(sku, conn.getErreur());
                }
            }
            
            ord1=Order_Exist(po,partid);
            System.out.println("order:"+ord1);
                if(ord1==null){
                    ordernum++;
                    ord1=String.valueOf(ordernum);
                    //String key=Integer.parseInt(wh)>0?"":wh;
                    saveOrder(po, ord1, partid,da, qty, CUT, sku1,price);
                    if(conn.getErreur()!=null){
                    errors.put(ord1, conn.getErreur());
                }
                }
                if(partid!=null && ord1!=null){
                int inc=1;
                System.out.println(sku1);
                    System.err.println("ss"+listData.get(sku1).size());
                for(String lpn:listData.get(sku1).keySet()){
                    System.out.println(lpn);
                String ind=String.valueOf(inc);
                int val=listData.get(sku1).get(lpn);
                String sticker=ord1+"000".substring(ind.length())+ind;
                if(!lpn_Exist(lpn)){
                conn.savecst("{call create_box(?,?,?,?,?,?,?,?,?)}",ord1,lpn,"",val,val,sticker,"",0,"");
                if(!saveLpn(ord1,lpn,sticker,val))
                    if(conn.getErreur()!=null){
                    errors.put(ord1, conn.getErreur());
                    }
                
                }
                inc++;
               }
                }
        }
        if(errors.isEmpty())
            JOptionPane.showMessageDialog(this, "Save without warnings");
        else
            JOptionPane.showMessageDialog(this, "Save with errors");
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable grid_po;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel txt_box;
    private javax.swing.JLabel txt_piece;
    // End of variables declaration//GEN-END:variables

    @Override
    public void executer(Object... obs) {
        if(obs[0].equals("pivot"))
        {
            mostrardata((List<Object[]>) obs[1]);
        }
    }
}
