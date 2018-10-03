package Load_data.old;


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
import javax.swing.JFrame;
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


public class loadlpn_old extends javax.swing.JInternalFrame implements Observe{

     private String inputFile;
     private final ConnectionDb conn=ConnectionDb.instance();
     int result = 0;
     int sizeid=0,colorid=0,poid=0,styleid=0;
     boolean islpn=false,ispo=false;
     List<Object[]> data=null;
     //private JInternalFrame summary

  
    public loadlpn_old() {
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
                    //System.out.println("sheets");
                sheet = book1.getSheetAt(0);
                }
                if(type==2){
                book1 = new HSSFWorkbook(fis);
                //System.out.println("sheets"+book1.);
                sheet = book1.getSheetAt(0);
                }
                Iterator<Row> itr = sheet.iterator();
                List<Object> datarow=null;
                List<Object> titre=new ArrayList<>();
                try {
                // Iterating over Excel file in Java
                    int i=0,count=0;
                    Row tit=sheet.getRow(0);
                    Cell ce=tit.getCell(i);
                    
                    if(ce.getStringCellValue().trim().toLowerCase().contains("po num")){
                        tit=sheet.getRow(0);
                        islpn=true;
                        data=new ArrayList<>();
                        datarow=new ArrayList<>();
                        jButton5.setVisible(islpn);
                        //linetoignore=1;
                    }else{
                        while(itr.hasNext()){
                            tit=itr.next();
                            ce=tit.getCell(0);
                            if(ce.getStringCellValue().trim().toLowerCase().contains("po no")){
                            itr.next();
                            tit=itr.next();
                            ispo=true;
                            count=i+4;
                            break;
                            
                            //linetoignore=i+1;
                            }
                         i++;
                        }
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
                i=0;
                int k=0;
                itr= sheet.iterator();
                System.out.println("count:"+count);
                System.out.println("i:"+i);
                while (itr.hasNext()) {
                    Object o=null;
                    Row row = itr.next();
                    ce=row.getCell(0);
                    if(i>count){
                    
                    
                    if(!ce.getStringCellValue().trim().toLowerCase().contains("---") && !ce.getStringCellValue().trim().toLowerCase().isEmpty() &&
                            !ce.getStringCellValue().trim().toLowerCase().contains("plan") &&
                            !ce.getStringCellValue().trim().toLowerCase().contains("no") && !ce.getStringCellValue().trim().toLowerCase().contains("num")
                            &&ce.getStringCellValue()!=null){
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
                if(i>count && !o.toString().contains("--")){
                tbm.setValueAt(o, k-linetoignore, j);
                }
                }
                if(islpn){
                if(i>0){
                datarow.add(o);
                tbm.setValueAt(o, k-linetoignore, j);
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
                    k++;
                    }
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        po_text = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        po_date = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ship_date = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        last_date = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("LOAD LPN ASG");

        grid_po.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PO NUM", "STYLE", "COL", "COLDSP", "SIZE", "QTY", "DESCRIP", "W/H", "LPN#"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, false
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
        jButton5.setText("PIVOT DATA");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel1.setText("PO #:");

        jLabel3.setText("PO date:");

        jLabel5.setText("Ship date:");

        jLabel6.setText("Last Date:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(po_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(po_text, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(last_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ship_date, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(394, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(ship_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(5, 5, 5))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                            .addComponent(last_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(po_text, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(5, 5, 5))
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(po_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

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
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
             Logger.getLogger(loadlpn_old.class.getName()).log(Level.SEVERE, null, ex);
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
        loadFrame_old pivot=new loadFrame_old(new JFrame(),true);
        //pivot.setVisible(true);
        //pivot.
        this.ajouterObservateur(pivot );
        alerter(new Object[]{"pivot",data});
        pivot.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable grid_po;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel last_date;
    private javax.swing.JLabel po_date;
    private javax.swing.JLabel po_text;
    private javax.swing.JLabel ship_date;
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
