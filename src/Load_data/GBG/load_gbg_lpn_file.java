/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Load_data.GBG;

import Load_data.loadFrame;
import connection.ConnectionDb;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import view.Bundle;

/**
 *
 * @author Padovano
 */
public class load_gbg_lpn_file extends javax.swing.JInternalFrame {

    private final ConnectionDb conn=ConnectionDb.instance();
    Set<String> erreur;
    private String inputFile;
    DataFormatter formatdata;
     DateFormat dateformat=new SimpleDateFormat("dd/MM/yyyy");
    /**
     * Creates new form load_gbg_lpn_file
     */
    public load_gbg_lpn_file() {
        initComponents();
        formatdata=new DataFormatter();
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        COUNT = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        grid_data = new javax.swing.JTable();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Load File");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/icon/export.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText("COUNT:");

        COUNT.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(COUNT, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 645, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(COUNT, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton3)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 0, 920, -1));

        grid_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "PO", "STYLE", "COLOR CODE", "COLOR", "SIZE", "QTY", "lpn", "lpn_mix", "is_mix", "qty"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(grid_data);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 97, 921, 452));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
            erreur=new HashSet<>();
        Thread t=new Thread(){
             public void run() {
            conn.setErreur(null);
            String ord=null;
            String po,style,color_code,size,lpn=null,lpn_mix=null;
            int qty;
            String details="";
            Object id_mix="NULL";
            int lpn_col=6;
            String lpnval="";
            Set<String> lpnlist=new HashSet<>();
            boolean mix=false,isold=true;
            if(grid_data.getColumnName(6).equals("POLYBAG LPN")){
                lpn_col=7;
                isold=false;
            }
            String initmix=grid_data.getValueAt(0, 7).toString().trim();
            for(int i=0;i<grid_data.getRowCount();i++){
                if(grid_data.getValueAt(i, 0)!=null){
                    lpn=grid_data.getValueAt(i, 7).toString().trim();
                    mix=grid_data.getValueAt(i, 8).equals("true")?true:(grid_data.getValueAt(i, 8).equals("TRUE")?true:false);
                    System.out.println("ddd"+mix);
                    if(mix){
                        
                        id_mix=saveLpnmix(lpn);
                        
                    }
                    po=grid_data.getValueAt(i, 0).toString().trim();
                    style=grid_data.getValueAt(i, 1).toString().trim();
                    color_code=grid_data.getValueAt(i, 2).toString().trim();
                    size=grid_data.getValueAt(i, 4).toString().trim();
                    qty=Integer.parseInt(grid_data.getValueAt(i, 5).toString());
                    details=grid_data.getValueAt(i, 6).toString().trim();
                    int total=Integer.parseInt(grid_data.getValueAt(i, 9).toString());
                    Pattern p=Pattern.compile("[A-Z]");
                    //if()
                    ord=order(po,style.trim()+"."+color_code+"."+size);
                    
                    if(ord!=null){
                        int idlpn=0;
                        if(mix){
                            idlpn=exist(details); 
                            //System.out.println(details+" "+idlpn);
                            
                        }else{
                            idlpn=exist(lpn);
                            //System.out.println(lpn);
                           
                        }
                          //System.out.println("idlpn:"+idlpn);  
                        lpnval=lpn;
                        int index=lpnno(ord)+1;
                            String stickers=ord+qty+index;
                            
                        if(idlpn==0){
                        //if(0==0){
                            System.out.println(details);
                            if(mix)
                                lpnval=details;
                            System.out.println(id_mix);
                            if(!saveLpn(ord, lpnval, stickers, qty,id_mix,mix))
                               erreur.add(conn.getErreur());
                            
                                
                                
                            conn.savecst("{call create_box(?,?,?,?,?,?,?)}",ord,lpn,"NULL",total,qty,stickers,details);
                        }else{
                            updateLpn(idlpn, qty, ord,id_mix,mix);
                        }

                    }
                }
            }
            if(erreur.isEmpty())
                JOptionPane.showMessageDialog(load_gbg_lpn_file.this, "Success");
            else
                JOptionPane.showMessageDialog(load_gbg_lpn_file.this, "Some of lpns are duplicate\n");
             }
        };
        t.setDaemon(true);
        t.start();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        JFileChooser file=new JFileChooser("C:/",FileSystemView.getFileSystemView());
        file.setDialogTitle("enregistre le fichier");

        file.setFileFilter(new FileNameExtensionFilter("Workbook excel","xlsx","xls"));
        int returnAct=file.showSaveDialog(this);
        if(returnAct==JFileChooser.APPROVE_OPTION){
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("cutting card");

            //Create some data to build the pivot table on
            setCellData(sheet,grid_data);

            FileOutputStream fileOut;
            try {
                String name=file.getSelectedFile().getAbsolutePath();
                if(!name.endsWith(".xlsx"))
                name=file.getSelectedFile().getAbsolutePath()+".xlsx";
                System.out.println(name);
                fileOut = new FileOutputStream(name);
                wb.write(fileOut);
                fileOut.close();
                wb.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Bundle.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Bundle.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "File saved with success");
        }

        
 
    }//GEN-LAST:event_jButton3ActionPerformed

    private boolean checkHeader(Map<Integer,String> data){
         Map<Integer, String> val=new HashMap<>();
         val.put(0,"PO");
         val.put(1,"STYLE");
         val.put(2,"COLOR CODE");
         val.put(3,"COLOR");
         val.put(4,"SIZE");
         val.put(5,"QTY");
         val.put(6,"POLYBAG LPN");
         val.put(7,"LPN");
         val.put(8,"is_mix");
         val.put(9,"qty total");
         

         boolean valid=true;
         String text="";
         for(Map.Entry<Integer,String> st:val.entrySet()){
             text+=st.getValue()+" \t ";
             if(!st.getValue().equals(data.get(st.getKey()))){
                valid=false; 
                
             }
         }
         if(!valid)
         JOptionPane.showMessageDialog(this, "please use the right format, the header must be like below\n"+text);
        return valid;
    }
    private String order(String po,String sku){
        System.out.println("sku:"+sku+"\t po:"+po);
        String order="select ordnum_147 ord from shoporder where cusord_147=? and prtnum_147=?";
        ResultSet rs=conn.select(order, po,sku);
        String result=null;
        try {
            while(rs.next()){
                result=rs.getString("ord");
                System.out.println("order:"+result);
            }
        } catch (SQLException ex) {
            Logger.getLogger(load_gbg_lpn_file.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    private int lpnno(String order){
        String req="select count(*) n from box_detail where ordnum=?";
        ResultSet rs=conn.select(req,order);
        int no=0;
        try {
            while(rs.next()){
                no=rs.getInt("n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(load_gbg_lpn_file.class.getName()).log(Level.SEVERE, null, ex);
        }
        return no;
    }
    
    private int mix_exist(String mix){
        String req="select id from mix_box where LPN_MIX=?";
        ResultSet rs=conn.select(req,mix);
        int no=0;
        try {
            while(rs.next()){
                no=rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(load_gbg_lpn_file.class.getName()).log(Level.SEVERE, null, ex);
        }
        return no;
    }
    private boolean saveLpn(String ordnum,String lpn,String sticker,int qty,Object mix,boolean ismix){
        boolean isok=false;
        if(ismix){
        String requete="INSERT INTO BOX_CONTAIN(ORDNUM,LPN,BOX_STICKERS,QTY,BOXMIX_ID) VALUES (?,?,?,?,?)";
        isok=conn.Update(requete, 0, ordnum,lpn,sticker,qty,mix);
        }else{
            String requete="INSERT INTO BOX_CONTAIN(ORDNUM,LPN,BOX_STICKERS,QTY) VALUES (?,?,?,?)";
            isok=conn.Update(requete, 0, ordnum,lpn,sticker,qty);
        }
        if(conn.getErreur()!=null){
            erreur.add(conn.getErreur());
        }
        return isok;
    }
    private int exist(String lpn){
        String req="select id from box_contain where LPN=?";
        ResultSet rs=conn.select(req,lpn);
        int no=0;
        try {
            while(rs.next()){
                no=rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(load_gbg_lpn_file.class.getName()).log(Level.SEVERE, null, ex);
        }
        return no;
    }
    private boolean updateLpn(int id, int qty,String Order,Object idmix, boolean mix){
        boolean isok=false;
        String requete="update BOX_CONTAIN set ORDNUM=? ,QTY=? ";
                if(mix)
                    requete +=",BOXMIX_ID=? ";
                requete+= "where ID=?";
                if(mix)
                    isok=conn.Update(requete, 0, Order,qty,idmix,id);
                else
                isok=conn.Update(requete, 0, Order,qty,id);
        return isok;
    }
    private int saveLpnmix(String lpn){
        if(mix_exist(lpn)==0){
            System.out.println("lpn:"+lpn);
        String requete="INSERT INTO mix_BOX(lpn_mix) VALUES (?)";
        if(conn.Update(requete, 1, lpn))
            return conn.getLast();
        else
            erreur.add(conn.getErreur()+"\n");
        }else
            return mix_exist(lpn);
        return 0;
    }
    public void setInputFile(String inputFile) {
                this.inputFile = inputFile;
    }
    public void read(int type) throws IOException  {
        FileInputStream fis = new FileInputStream(inputFile);
        Sheet sheet=null;
        Workbook book1=null;
        
        if(type==1)
            book1 = new XSSFWorkbook(fis);
        if(type==2)
            book1 = new HSSFWorkbook(fis);
        sheet = book1.getSheetAt(0);
                
        Map<Integer,String> obj=new HashMap<>();
        Iterator<Row> itr = sheet.iterator();
        List<Object> titre=new ArrayList<>();
        try {
            int i=0;
            Row tit=itr.next();
            Iterator<Cell> cellIte=tit.cellIterator();
            while (cellIte.hasNext()) {
                boolean blank=false;
                Object o=null;
                Cell cell = cellIte.next();
                o=formatdata.formatCellValue(cell);
                try{
                    if(o.toString().trim().isEmpty())
                        blank=true;
                }catch(NullPointerException e){
                    blank=true;
                }
                if(blank)
                    return;
                obj.put(i, o.toString().trim());
                titre.add(o);
                i++;
            }
            if(!checkHeader(obj))
                return;
                       
            grid_data.setModel(new DefaultTableModel(new Object[0][],titre.toArray()));
            DefaultTableModel tbm = (DefaultTableModel) grid_data.getModel();
            tbm.setRowCount(0);
            i=0;
            while (itr.hasNext()) {
                Object o=new Object();
                Row row = itr.next();
                Cell ce=row.getCell(0);
                
                Iterator<Cell> cellIterator = row.cellIterator();
                o=formatdata.formatCellValue(ce);
                if(!o.toString().trim().isEmpty()){
                    tbm.addRow(new Object[titre.size()]);
                    for (int a=0;a<titre.size();a++) {
                        o=new Object();
                        boolean isblank=false;
                        Cell cell = row.getCell(a);
                        o=formatdata.formatCellValue(cell);
                        if(!isblank){
                            tbm.setValueAt(o, i, a);
                        }
                    }
                    i++;
                }
            }
            fis.close();
            COUNT.setText(String.valueOf(tbm.getRowCount()));
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace(); 
        }
    } 
 
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        File selectedFile = null;
          JFileChooser fileChooser = new JFileChooser();
fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
fileChooser.setFileFilter(new FileNameExtensionFilter("Workbook excel","xlsx","xls"));
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

    public void setCellData(XSSFSheet sheet,JTable table){
        Row row10=sheet.createRow(0);
        for(int j=0;j<table.getColumnCount();j++){
                Cell cells=row10.createCell(j);
                cells.setCellValue(table.getColumnName(j));
            }
        for(int i=0;i<table.getRowCount();i++){
            Row rows=sheet.createRow(1+i);
            Cell cellMar=rows.createCell(0);
            for(int j=0;j<table.getColumnCount();j++){
                Cell cells=rows.createCell(j);
                try{
                cells.setCellValue(table.getValueAt(i, j).toString());
                }catch(NullPointerException e){
                    
                }
            }
                
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel COUNT;
    private javax.swing.JTable grid_data;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
