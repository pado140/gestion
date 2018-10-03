package Load_data.GBG;


import Load_data.GBG.loadOrderFishman;
import Load_data.loadFrame;
import Load_data.EDG.loadlpnEDS;
import connection.ConnectionDb;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import observateurs.Observateurs;
import observateurs.Observe;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class updateOrderFishman extends javax.swing.JInternalFrame implements Observe{

     private String inputFile;
     private final ConnectionDb conn=ConnectionDb.instance();
     int result = 0;
     int sizeid=0,colorid=0,poid=0,styleid=0;
     boolean islpn=false,ispo=false;
     List<Object[]> data=null;
     DataFormatter formatdata;
     

  
    public updateOrderFishman() {
        initComponents();
        formatdata=new DataFormatter();
        
       grid_po.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 13 ));
    }
    


    public void setInputFile(String inputFile) {
                this.inputFile = inputFile;
        }
        
        public void read(int type) throws IOException  {
                FileInputStream fis = new FileInputStream(inputFile);
                Sheet sheet=null;
                islpn=false;
                ispo=false;
                int pieces=0;
                int linetoignore=0;
                Workbook book1;
                if(type==1){
                book1 = new XSSFWorkbook(fis);
                sheet = book1.getSheetAt(0);
                }
                if(type==2){
                book1 = new HSSFWorkbook(fis);
                sheet = book1.getSheetAt(0);
                }
                Iterator<Row> itr = sheet.iterator();
                List<Object> datarow=null;
                List<Object> titre=new ArrayList<>();
                try {
                // Iterating over Excel file in Java
                    int i=0;
                    Row tit=sheet.getRow(0);
                    Cell ce=tit.getCell(i);
                    if(ce.getStringCellValue().trim().toLowerCase().contains("po no")){
                        tit=sheet.getRow(3);
                        ispo=true;
                        //linetoignore=1;
                    }
                    if(ce.getStringCellValue().trim().toLowerCase().contains("po num")){
                        tit=sheet.getRow(0);
                        islpn=true;
                        data=new ArrayList<>();
                        datarow=new ArrayList<>();
                        jButton5.setVisible(islpn);
                        //linetoignore=1;
                    }
                    Iterator<Cell> cellIte=tit.cellIterator();
                    while (cellIte.hasNext()) {
                        Object o=null;
                        Cell cell = cellIte.next();
                switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                System.out.print(cell.getStringCellValue() + "\t");
                o=cell.getStringCellValue();
                break;
                case Cell.CELL_TYPE_NUMERIC:
                System.out.print("int"+cell.getNumericCellValue() + "\t");
                o=cell.getNumericCellValue();
                break;
                case Cell.CELL_TYPE_BOOLEAN:
                System.out.print(cell.getBooleanCellValue() + "\t");
                o=cell.getBooleanCellValue();
                break;
                default:
                }
                o=formatdata.formatCellValue(cell);
                titre.add(o);
                
                }
                    int withdim=0;
                    if(titre.size()==11)
                        withdim=1;
                    grid_po.setModel(new DefaultTableModel(new Object[0][],titre.toArray()));
                    DefaultTableModel tbm = (DefaultTableModel) grid_po.getModel();
                tbm.setRowCount(0);
                while (itr.hasNext()) {
                    Object o=new Object();
                    Row row = itr.next();
                    ce=row.getCell(0);
                    Object ob=new Object();
                    switch (ce.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                System.out.print(ce.getStringCellValue() + "\t");
                ob=ce.getStringCellValue().trim();
                break;
                case Cell.CELL_TYPE_NUMERIC:
                System.out.print(ce.getNumericCellValue() + "\t");
                ob=ce.getNumericCellValue();
                break;
                case Cell.CELL_TYPE_BOOLEAN:
                System.out.print(ce.getBooleanCellValue() + "\t");
                ob=ce.getBooleanCellValue();
                break;
                default:
                }
                    if(!ob.toString().trim().toLowerCase().contains("---") && !ob.toString().trim().toLowerCase().isEmpty() &&
                            !ob.toString().trim().toLowerCase().contains("plan") &&
                            !ob.toString().trim().toLowerCase().contains("no") && !ob.toString().trim().toLowerCase().contains("num")){
                        System.out.println(ob.toString());
                    tbm.addRow(new Object[titre.size()]);
                    //linetoignore++;
                // Iterating over each column of Excel file 
                Iterator<Cell> cellIterator = row.cellIterator();
                int j=0;
                while (cellIterator.hasNext()) {
                    o=new Object();
                    boolean isblank=false;
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                System.out.print(cell.getStringCellValue() + "\t");
                o=cell.getStringCellValue().trim();
                if(o.toString().trim().equals("O10"))
                    o=o.toString().trim().replace("O", "0");
                break;
                case Cell.CELL_TYPE_NUMERIC:
                System.out.print("int"+cell.getNumericCellValue() + "\t");
                o=cell.getNumericCellValue();
                if(j<7){
                        o=String.valueOf((int)cell.getNumericCellValue());
                        if(j==0 || j==1){
                            o=String.valueOf((double)cell.getNumericCellValue());
                            o=o.toString().substring(0, o.toString().indexOf("."));
                        }
                    }
                    if(j==8+withdim && o instanceof Double){
                        o=new SimpleDateFormat("dd/MM/yyyy").format(DateUtil.getJavaDate(cell.getNumericCellValue()));
                    }
                
                break;
                case Cell.CELL_TYPE_BOOLEAN:
                System.out.print(cell.getBooleanCellValue() + "\t");
                o=cell.getBooleanCellValue();
                break;
                    
                case Cell.CELL_TYPE_BLANK:
                    System.out.println("blank");
                    o=" ";
                    isblank=true;
                    break;
                default:
                }
                if(!isblank){
                if(ispo){
                if(i>4 && !o.toString().contains("--")){
                tbm.setValueAt(o, i-linetoignore, j);
                }
                }
                if(islpn){
                if(i>0){
                datarow.add(o);
                tbm.setValueAt(o, i-linetoignore, j);
                }
                }
                }
                j++;
                
                }
                if(islpn){
                data.add(datarow.toArray());
                datarow=new ArrayList<>();
                }
                }else
                        linetoignore++;
                    i++;
                
                System.out.println("");
                }
                
 fis.close();
 txt_box.setText(String.valueOf(grid_po.getRowCount()));
                        //txt_piece.setText(String.valueOf(pieces));
 } catch (FileNotFoundException fe) {
     fe.printStackTrace();
 } catch (IOException ie) {
     ie.printStackTrace(); 
 } 
} 
 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        grid_po = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_box = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_piece = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("LOAD FISHMAN LPN");

        grid_po.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PO NUM", "STYLE", "COL", "COLDSP", "SIZE", "QTY", "DESCRIP", "LPN#", "Cut"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grid_po.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(grid_po);

        jButton1.setText("Load Excel File");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Qtity of boxes:");

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

        jButton5.setVisible(false);
        jButton5.setText("Save");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
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
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        File selectedFile = null;
          JFileChooser fileChooser = new JFileChooser();
fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
int result1 = fileChooser.showOpenDialog(this);
int type=0;
if (result1 == JFileChooser.APPROVE_OPTION) {
    selectedFile = fileChooser.getSelectedFile();
    System.out.println("Selected file: " + selectedFile.getName());
    
    String ext="";
           ext =selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".")+1).toLowerCase();
            System.out.println("Selected file ext: " + ext);
            
            if(ext.equals("xlsx"))
                type=1;
            if(ext.equals("xls"))
                type=2;
    if(type!=0){                
                 setInputFile(selectedFile.getAbsolutePath());
         try {
             read(type);
         } catch (IOException ex) {
             Logger.getLogger(loadFrame.class.getName()).log(Level.SEVERE, null, ex);
         }   
    }else{
        JOptionPane.showMessageDialog(this, "This file can't be open\npleas verify", "Error", JOptionPane.ERROR_MESSAGE);
    }      
            
           
}     
 

         
    }//GEN-LAST:event_jButton1ActionPerformed

    
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
    
    public boolean saveOrder(String po,String ord,String partid,Date created,int qty,String key,String udf,Date xfact,double price){
        String query="INSERT INTO Order_Master (ORDNUM_10,LINNUM_10,DELNUM_10,PRTNUM_10,CURDUE_10,RECFLG_10,TAXABLE_10"+
           ",TYPE_10,ORDER_10,VENID_10,ORGDUE_10,PURUOM_10,CURQTY_10,ORGQTY_10,DUEQTY_10,CURPRM_10,FILL03_10,ORGPRM_10,FILL04_10"+
           ",FRMPLN_10,STATUS_10,STK_10,CUSORD_10,PLANID_10,BUYER_10,PSCRAP_10,ASCRAP_10,SCRPCD_10,SCHCDE_10,REVLEV_10,COST_10"+
           ",CSTCNV_10,APRDBY_10,ORDREF_10,SCHFLG_10,CRTRAT_10,NEGATV_10,REQPEG_10,MPNNUM_10,LABOR_10,AMMEND_10,LOTNUM_10,BEGSER_10"+
           ",REWORK_10,CRTSNS_10,TTLSNS_10,FORCUR_10,EXCESS_10,UOMCST_10,UOMCNV_10,INSREQ_10,CREDTE_10,RTEREV_10,RTEDTE_10"+
           ",COMCDE_10,ORDPTP_10,JOBEXP_10,JOBCST_10,TAXCDE_10,TAX1_10,GLREF_10,CURR_10,UDFKEY_10,UDFREF_10,DISC_10,RECCOST_10"+
           ",MPNMFG_10,DEXPFLG_10,PLSTPRNT_10,ROUTPRNT_10,REQUES_10,ALTBOM_10,ALTRTG_10,CLASS_10,JOB_10,SUBSHP_10) "+
            "VALUES (?,'0','0',?,?,'N','N','MS','"+ord+"0000','',?,'',?,?,?,?,'',?,'','N','3','FG1-FP1',?,'GBG','',0,0,'N','B','',"+
           "?,1,'',?,'','','N','','',0,'N','','','Y','N',0,0,0,0,0,'',?,'',NULL,'SKU','M','Y',0,'',0,'','',?,?,0,0,'','N'"+
            ",'N','N','','','','','',0)";
      
        String Alias=po;
      return conn.Update(query,1, ord,partid,created,xfact,qty,qty,qty,created,created,po,price,Alias,created,key,udf);
      
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
                       "VALUES (?,'M','C','GBG','SKU',0,?,?,'EA',2,2,'N',GETDATE(),'',NULL,'','OZ',0,GETDATE(),'','N',?,'FG1-FP1'" +
                       ",'N',NULL,'',0,0,0,0,'L',100,NULL,'',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'A',GETDATE(),'','EA'" +
                       ",1,0,0,0,0,0,0,0,0,0,0,0,0,'EA',1,0,'','N',0,0,'B','','A','1','Q','N',0,0,'N','N','N','N',0,'',0,0,''" +
                       ",'','','',NULL,0,'Y','N',0,'N','','','',?,?,'N',0,'','N','','',?)";
        upc=upc.length()>15?"":upc;
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
        int x=0;
        int ordernum=Integer.parseInt(ord);
        String partid;
        if(grid_po.getColumnCount()==11)
                x+=1;
        for(int a=0;a<grid_po.getRowCount();a++){
            
            
            String size=grid_po.getValueAt(a, 5).toString().trim();
            
            if(grid_po.getColumnCount()==11){
                String dim=grid_po.getValueAt(a, 6).toString().trim();
                
                if(dim!=null && !dim.isEmpty()){
                    if(dim.contains("U")){
                        size+=dim;
                    }else
                    {
                        size+="X"+dim;
                    }
                }
            }
            String po=grid_po.getValueAt(a, 0).toString().trim();
            int qty=(int)Double.parseDouble(grid_po.getValueAt(a, 6+x).toString());
            po=po.substring(po.indexOf("-")+1);
            String CUT=grid_po.getValueAt(a, 1).toString();
            String description=grid_po.getValueAt(a, 7+x).toString();
            Date xfact=null;
            try {
                xfact=new SimpleDateFormat("dd/MM/yyyy").parse(grid_po.getValueAt(a, 8+x).toString());
            } catch (ParseException ex) {
                Logger.getLogger(loadOrderFishman.class.getName()).log(Level.SEVERE, null, ex);
            }
            String style=grid_po.getValueAt(a, 2).toString().trim();
            
            String color=grid_po.getValueAt(a, 4).toString();
            String sku1,sku=grid_po.getValueAt(a, 2).toString().trim();
            sku+="."+grid_po.getValueAt(a, 3).toString().trim();
            sku+="."+size;
            
            sku1=po+"."+grid_po.getValueAt(a, 2).toString().trim();
            sku1+="."+grid_po.getValueAt(a, 3).toString().trim();
            //System.out.println(sku);
            double price=(Double)grid_po.getValueAt(a, 9+x);
            String ord1;
            try{
            partid=Prtid_Exist(sku)[0];
                System.out.println("part master:"+partid);
            }catch(NullPointerException e){
                partid=savePartid(sku,description,description,style,sku,size,color);
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
                    saveOrder(po, ord1, partid,new Date(), qty, CUT, sku1,xfact,price);
                    if(conn.getErreur()!=null){
                    errors.put(ord1, conn.getErreur());
                }
                }
        }
        if(errors.isEmpty())
            JOptionPane.showMessageDialog(this, "Save without warnings");
        else
            JOptionPane.showMessageDialog(this, "Save with errors");
    }
    
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        DefaultTableModel tbm = (DefaultTableModel) grid_po.getModel();
        tbm.setRowCount(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        //UPLOAD_EDS pivot=new UPLOAD_EDS(new JFrame(),true);
        //pivot.setVisible(true);
        //pivot.
        //this.ajouterObservateur(pivot );
        //alerter(new Object[]{"pivot",data});
        //pivot.setVisible(true);
        Save();
        
    }//GEN-LAST:event_jButton5ActionPerformed

    
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
                new loadlpnEDS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable grid_po;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel txt_box;
    private javax.swing.JLabel txt_piece;
    // End of variables declaration//GEN-END:variables

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
