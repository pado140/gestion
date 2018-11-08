/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import connection.ConnectionDb;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import observateurs.Observateurs;
import observateurs.Observe;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Padovano
 */
public class WIPDAILLY extends javax.swing.JInternalFrame implements Observe,Observateurs{
private ConnectionDb conn = ConnectionDb.instance();
private Map<String, Integer> list_second;
private Map<String, Integer> list_pack;
private Set<Object[]> lisData;
private Object[][] data;
SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Creates new form WIPCONTROL
     */
    public WIPDAILLY() {
        initComponents();
        this.ajouterObservateur(this);
        loadData();
            second();
            packed();
        mostrar();
        alerter("reload",data);
    }

    private void refresh(){
        loadData();
            second();
            packed();
        mostrar();
        alerter("reload",data);
    }
    private void loadData(){
        lisData=new HashSet<>();
        String requete="select * from prod_daily";
    System.out.println(requete);
    ResultSet rs=conn.select(requete);
        try {
            while(rs.next()){
                String sku=rs.getString("sku");
                   String size=rs.getString("size");
                   /*if(rs.getString("description").toLowerCase().contains("yth")||
                   rs.getString("description").toLowerCase().contains("youth")||
                   rs.getString("description").toLowerCase().contains("girls"))
                   size="Y"+size;*/
                String color,Code;
               color=rs.getString("color").trim();
               Code=sku.substring(sku.indexOf(".")+1,sku.lastIndexOf("."));
             
                   Object[] data=new Object[16];
                   data[0]=rs.getString("Brand");
                   data[1]=rs.getString("po");
                   data[2]=rs.getString("style");
                   data[10]=rs.getInt("PIECES_SEWN");
                   data[3]=Code;
                   data[4]=color;
                   data[5]=size;
                   data[7]=rs.getInt("qty_in_module");
                   data[11]="BLD "+rs.getInt("workcenter")+"- "+rs.getString("mod");
                   data[12]=rs.getDate("date_made");
                   data[6]=rs.getInt("pieces");
                   data[8]=rs.getInt("PIECES_SEWN");
                   data[9]=rs.getString("stickers");
                   data[13]=rs.getString("order_num");
                   lisData.add(data);
            }   } catch (SQLException ex) {
            Logger.getLogger(Sewing_prod.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void loadDyn(){
        while(true){
            loadData();
            second();
            packed();
        //mostrar();
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
        data=new Object[lisData.size()][14];
        int i=0;
            for(Object[] ob:lisData){
                
                   data[i]=ob;
                  
                   //data[i][12]=list_pack.get(ob[12].toString());                  
                   System.out.println("Val:"+((data[i][9].toString()+data[i][12].toString())));
                   int val=0;
                   try{
                       val=list_second.get(data[i][9].toString().trim()+data[i][12].toString());
                   }catch(NullPointerException e){
                       val=0;
                   }
                   data[i][8]=Integer.parseInt(data[i][8].toString())-val;
                   data[i][9]=val;
                  i++;
            }
            System.out.println("load successfull");
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
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        colorsearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        sizesearch = new javax.swing.JTextField();
        posearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        stylesearch = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        datesearch = new com.toedter.calendar.JDateChooser();
        to = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        custsearch = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        location_search = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        GRID_DATA = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("WIP details report");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameDeiconified(evt);
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jButton2.setText("REFRESH");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setText("SIZE");

        jLabel6.setText("COLOR:");

        colorsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                colorsearchKeyReleased(evt);
            }
        });

        jLabel1.setText("PO FILTER:");

        sizesearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sizesearchKeyReleased(evt);
            }
        });

        posearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                posearchKeyReleased(evt);
            }
        });

        jLabel2.setText("STYLE FILTER:");

        stylesearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                stylesearchKeyReleased(evt);
            }
        });

        jLabel3.setText("date");

        datesearch.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                datesearchPropertyChange(evt);
            }
        });

        to.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                toPropertyChange(evt);
            }
        });

        jLabel4.setText("TO");

        jLabel7.setText("CUSTOMER:");

        custsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                custsearchKeyReleased(evt);
            }
        });

        jLabel8.setText("LOCATION:");

        location_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                location_searchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(custsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(posearch, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stylesearch, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sizesearch, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(datesearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(22, 22, 22)
                        .addComponent(to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(location_search, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(stylesearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(sizesearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(posearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(datesearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(custsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(location_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton2))
                .addGap(0, 22, Short.MAX_VALUE))
        );

        GRID_DATA.setAutoCreateRowSorter(true);
        GRID_DATA.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "CUSTOMER", "PO", "STYLE", "COLOR_CODE", "COLOR", "SIZE", "ORDER", "QTY ISSUED", "FIRST", "SEGOND", "TOTAL PRODUCED", "LOCATION", "Date", "WORK ORDER", "Title 15"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(GRID_DATA);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1294, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE))
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/icon/export.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JFileChooser file=new JFileChooser("C:/",FileSystemView.getFileSystemView());
        file.setDialogTitle("enregistre le fichier");

        file.setFileFilter(new FileNameExtensionFilter("Workbook excel","xlsx","xls"));
        int returnAct=file.showSaveDialog(this);
        if(returnAct==JFileChooser.APPROVE_OPTION){
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("cutting card");

            //Create some data to build the pivot table on
            setCellData(sheet,GRID_DATA);

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
            }catch (FileNotFoundException ex) {
                Logger.getLogger(Bundle.class.getName()).log(Level.SEVERE, null, ex);
            }catch (IOException ex) {
                Logger.getLogger(Bundle.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "File saved with success");
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void formInternalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeiconified
        // TODO add your handling code here:
        loadDyn();
    }//GEN-LAST:event_formInternalFrameDeiconified

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        refresh();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void colorsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_colorsearchKeyReleased
        // TODO add your handling code here:
        buscados();
    }//GEN-LAST:event_colorsearchKeyReleased

    private void sizesearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sizesearchKeyReleased
        // TODO add your handling code here:
        buscados();
    }//GEN-LAST:event_sizesearchKeyReleased

    private void posearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_posearchKeyReleased
        // TODO add your handling code here:
        buscados();
    }//GEN-LAST:event_posearchKeyReleased

    private void stylesearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stylesearchKeyReleased
        // TODO add your handling code here:
        buscados();
    }//GEN-LAST:event_stylesearchKeyReleased

    private void datesearchPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_datesearchPropertyChange
        // TODO add your handling code here:
        buscados();
    }//GEN-LAST:event_datesearchPropertyChange

    private void toPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_toPropertyChange
        // TODO add your handling code here:
        buscados();
    }//GEN-LAST:event_toPropertyChange

    private void custsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_custsearchKeyReleased
        // TODO add your handling code here:
         buscados();
    }//GEN-LAST:event_custsearchKeyReleased

    private void location_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_location_searchKeyReleased
        // TODO add your handling code here:
        buscados();
    }//GEN-LAST:event_location_searchKeyReleased

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
                if(j>5){
                    if(table.getValueAt(i, j) instanceof Double)
                cells.setCellValue(Double.parseDouble(table.getValueAt(i, j).toString()));
                    if(table.getValueAt(i, j) instanceof Integer)
                cells.setCellValue(Integer.parseInt(table.getValueAt(i, j).toString()));
                    if(table.getValueAt(i, j) instanceof Date)
                cells.setCellValue((Date)table.getValueAt(i, j));
                }
                }catch(NullPointerException e){
                    
                }
            }
                
        }
    }
    
    private void buscados(){
         DefaultTableModel tbm = (DefaultTableModel) GRID_DATA.getModel();
               tbm.setRowCount(0);
        String po=posearch.getText().trim().toLowerCase();
        String style=stylesearch.getText().trim().toLowerCase();
        String color=colorsearch.getText().trim().toLowerCase();
        String size=sizesearch.getText().trim().toLowerCase();
        String client=custsearch.getText().trim().toLowerCase();
        String location=location_search.getText().trim().toLowerCase();
        System.out.println("date:"+datesearch.getDate());
        for(Object[] ob:lisData){
            if(datesearch.getDate()!=null){
                if(ob[12]!=null){
                    if(to.getDate()!=null){
                        Date d=new Date();
                        Calendar cal=Calendar.getInstance(TimeZone.getDefault(), Locale.CANADA);
                        cal.setTime(datesearch.getDate());
                        cal.add(Calendar.DAY_OF_MONTH, -1);
                        d=cal.getTime();
            if(ob[1].toString().trim().toLowerCase().contains(po)&&
                    ob[2].toString().trim().toLowerCase().contains(style)&&
                    (ob[3].toString().trim().toLowerCase().contains(color)||
                    ob[4].toString().trim().toLowerCase().contains(color))&&
                    ob[5].toString().trim().toLowerCase().contains(size)&&
                    ob[0].toString().trim().toLowerCase().contains(client)&&
                    ob[11].toString().trim().toLowerCase().contains(location)){
                try {
                    Date dd=formatter.parse(ob[12].toString());
                    if(dd.after(d)&&dd.before(to.getDate()))
                        tbm.addRow(ob);
                } catch (ParseException ex) {
                    Logger.getLogger(SOABAR_report.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                
            }
                    }else{
                        if(ob[1].toString().trim().toLowerCase().contains(po)&&
                    ob[2].toString().trim().toLowerCase().contains(style)&&
                    (ob[3].toString().trim().toLowerCase().contains(color)||
                    ob[4].toString().trim().toLowerCase().contains(color))&&
                    ob[5].toString().trim().toLowerCase().contains(size)&&
                    ob[12].toString().equals(formatter.format(datesearch.getDate()))&&
                    ob[0].toString().trim().toLowerCase().contains(client)&&
                    ob[11].toString().trim().toLowerCase().contains(location))
                    
                tbm.addRow(ob);
                    }
                }
            }else{
                if(ob[1].toString().trim().toLowerCase().contains(po)&&
                    ob[2].toString().trim().toLowerCase().contains(style)&&
                    (ob[3].toString().trim().toLowerCase().contains(color)||
                    ob[4].toString().trim().toLowerCase().contains(color))&&
                    ob[5].toString().trim().toLowerCase().contains(size)&&
                    ob[0].toString().trim().toLowerCase().contains(client)&&
                    ob[11].toString().trim().toLowerCase().contains(location))
                    
                tbm.addRow(ob);
            }
        }
        
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
        String requete="select  QTY_PER_LOT qty,order_num,s_traveller,MODIFIED,lot_stickers from sewing"
                + "_production where TYPE_SEW='second' order by sew_id asc";
        list_second=new HashMap<>();
        ResultSet rs=conn.select(requete);
        
    try {
        while(rs.next()){
            System.out.println("key:"+rs.getString("lot_stickers").trim()+rs.getDate("modified").toString());
            list_second.put(rs.getString("lot_stickers").trim()+rs.getDate("modified").toString(),rs.getInt("qty"));
        }
    } catch (SQLException ex) {
        Logger.getLogger(WIPCONTROL.class.getName()).log(Level.SEVERE, null, ex);
    }
        
      
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable GRID_DATA;
    private javax.swing.JTextField colorsearch;
    private javax.swing.JTextField custsearch;
    private com.toedter.calendar.JDateChooser datesearch;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField location_search;
    private javax.swing.JTextField posearch;
    private javax.swing.JTextField sizesearch;
    private javax.swing.JTextField stylesearch;
    private com.toedter.calendar.JDateChooser to;
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
        for(Observateurs o:obs)
            o.executer(ob);
    }

    @Override
    public void executer(Object... obs) {
        
        if(obs[0].equals("reload")){
            GRID_DATA.setModel(new javax.swing.table.DefaultTableModel(
            (Object[][])obs[1],
            new String [] {
                "CUSTOMER", "PO", "STYLE", "COLOR_CODE", "COLOR", "SIZE", "ORDER", "QTY ISSUED", "FIRST", "SEGOND", "TOTAL PRODUCED", "LOCATION", "Date","WORK ORDER"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false,false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(GRID_DATA);
        }
    }
}
