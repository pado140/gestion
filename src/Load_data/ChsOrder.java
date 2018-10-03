package Load_data;


import connection.ConnectionDb;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import observateurs.Observateurs;
import observateurs.Observe;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ChsOrder extends javax.swing.JInternalFrame implements Observe{

     private String inputFile;
     private final ConnectionDb conn=ConnectionDb.instance();
     int result = 0;
     int sizeid=0,colorid=0,poid=0,styleid=0;
     boolean islpn=false,ispo=false;
     List<Object[]> data=null;

  
    public ChsOrder() {
        initComponents();
        
       grid_po.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 13 ));
    }
    
    public void PivotdatanSave(List<Object[]> ob) {
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
            Set<Object[]> dat=new HashSet<>();
            for(Object[] o:ob){
                if((int)Double.parseDouble(o[16].toString())!=initPoline){
                    tbm.addRow(new Object[]{initPo,initStyle,initCol,initColor,initSize,total,initDesc});
                    System.out.println(initPo+"."+initStyle+"."+initCol+"."+initColor+"."+initSize);
                        for(Object[] obj:dat){
                            System.out.println(obj[0]+"."+obj[1]);
                        }
                    total=0;
                    initPoline=(int)Double.parseDouble(o[16].toString());
                    initPo=o[0].toString();
                    initStyle=o[1].toString();
                    initCol=o[2].toString();
                    initColor=o[3].toString();
                    initSize=o[4].toString();
                    initDesc=o[6].toString();
                    dat=new HashSet<>();
                }
                System.out.println(o[16].toString());
                dat.add(new Object[]{o[8],o[5]});
                total+=(int)Double.parseDouble(o[5].toString());
            }
            tbm.addRow(new Object[]{initPo,initStyle,initCol,initColor,initSize,total,initDesc});
            
 txt_box.setText(String.valueOf(grid_po.getRowCount()));
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
                Workbook book1=null;
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
                System.out.print(cell.getNumericCellValue() + "\t");
                o=cell.getNumericCellValue();
                break;
                case Cell.CELL_TYPE_BOOLEAN:
                System.out.print(cell.getBooleanCellValue() + "\t");
                o=cell.getBooleanCellValue();
                break;
                default:
                }
                titre.add(o);
                }
                    grid_po.setModel(new DefaultTableModel(new Object[0][],titre.toArray()));
                    DefaultTableModel tbm = (DefaultTableModel) grid_po.getModel();
                tbm.setRowCount(0);
                while (itr.hasNext()) {
                    Object o=null;
                    Row row = itr.next();
                    ce=row.getCell(0);
                    if(!ce.getStringCellValue().trim().toLowerCase().contains("---") && !ce.getStringCellValue().trim().toLowerCase().isEmpty() &&
                            !ce.getStringCellValue().trim().toLowerCase().contains("plan") &&
                            !ce.getStringCellValue().trim().toLowerCase().contains("no") && !ce.getStringCellValue().trim().toLowerCase().contains("num")){
                        System.out.println(ce.getStringCellValue());
                    tbm.addRow(new Object[titre.size()]);
                    //linetoignore++;
                // Iterating over each column of Excel file 
                Iterator<Cell> cellIterator = row.cellIterator();
                int j=0;
                while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                System.out.print(cell.getStringCellValue() + "\t");
                o=cell.getStringCellValue().trim();
                break;
                case Cell.CELL_TYPE_NUMERIC:
                System.out.print(cell.getNumericCellValue() + "\t");
                o=cell.getNumericCellValue();
                break;
                case Cell.CELL_TYPE_BOOLEAN:
                System.out.print(cell.getBooleanCellValue() + "\t");
                o=cell.getBooleanCellValue();
                break;
                default:
                }
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

public boolean saveCom(Object[] o){
    String requete="insert into Order_Master( LINNUM_10,PRTNUM_10,CURDUE_10,"
            + "ORGDUE_10,CURQTY_10,ORGQTY_10,DUEQTY_10,CUSORD_10,PLANID_10,"
            + "ORDREF_10,COMCDE_10,UDFREF_10,CLASS_10) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    
    Object[] obj=new Object[]{o[16],};
    
    return conn.Update(requete,1, obj);
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

        grid_po.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PO NUM", "STYLE", "COLDSP", "SIZE", "QTY", "DESCRIP", "UPC", "packaging", "ITEM", "X-FACT"
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
        jButton5.setText("GENERATE LPN");
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
           ext =selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".")+1);
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        DefaultTableModel tbm = (DefaultTableModel) grid_po.getModel();
        tbm.setRowCount(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        this.ajouterObservateur(new GenerateCHSLPN());
        alerter(new Object[]{"pivot1",data});
    }//GEN-LAST:event_jButton5ActionPerformed

    private int Po_Exist(String Po){
         try {
             String query="select * from dbo.orders where number=?";
             ResultSet rs=conn.select(query, Po);
             while(rs.next())
             return rs.getInt(1);
            
         } catch (SQLException ex) {
             Logger.getLogger(loadFrame.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return 0;
    }
    
    public boolean savePo(String po){
      String query="insert into dbo.orders( number,created,delivery_date) values(?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
      
      return conn.Update(query,1, po);
      
    }
    
    private int Style_Exist(String style){
         try {
             String query="select * from dbo.styles where code=?";
             ResultSet rs=conn.select(query, style);
             while(rs.next())
             return rs.getInt(1);
            
         } catch (SQLException ex) {
             Logger.getLogger(loadFrame.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return 0;
    }
    
    public boolean saveStyle(String style,String desc,int st){
      String query="insert into dbo.styles( code,description,status) values(?,?,?)";
      Object[] ob={style,desc,st};
      
      return conn.Update(query,1, ob);
    }
    
    private int Color_Exist(String code){
         try {
             String query="select * from dbo.colors where color_code=?";
             ResultSet rs=conn.select(query, code);
             while(rs.next())
             return rs.getInt(1);
            
         } catch (SQLException ex) {
             Logger.getLogger(loadFrame.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return 0;
    }
    
    public boolean saveColor(String code,String desc){
      String query="insert into dbo.colors( color_code,color_desc) values(?,?)";
      return conn.Update(query,1, code,desc);
    }
    
    private int Size_Exist(String size){
         try {
             String query="select * from dbo.sizes where size_desc=?";
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
      return conn.Update(query,1, size,range);
    }
    
    private int item_exist(int color,int size,int style){
        try {
             String query="select * from dbo.items where id_color=? and id_size=? and id_style=?";
             ResultSet rs=conn.select(query, color,size,style);
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
                new ChsOrder().setVisible(true);
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
