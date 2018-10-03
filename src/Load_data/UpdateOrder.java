package Load_data;


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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import observateurs.Observateurs;
import observateurs.Observe;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class UpdateOrder extends javax.swing.JInternalFrame implements Observe{

     private String inputFile;
     private final ConnectionDb conn=ConnectionDb.instance();
     int result = 0;
     int sizeid=0,colorid=0,poid=0,styleid=0;
     boolean islpn=false,ispo=false;
     List<Object[]> data=null;
     private SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yy");
     DataFormatter formatdata;
     //private JInternalFrame summary

  
    public UpdateOrder() {
        initComponents();
       init();
         formatdata=new DataFormatter();
       grid_po.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 13 ));
    }
    
    private void init(){
        ((DefaultTableModel)grid_po.getModel()).setRowCount(0);
         po_date.setText(null);
         ship_date.setText(null);
         last_date.setText(null);
         po_text.setText(null);
         txt_box.setText(null);
         txt_piece.setText(null);
    }
    public void setInputFile(String inputFile) {
                this.inputFile = inputFile;
        }
     //										CONF QTY	EXT. MSG #	DEL. NOTE DATE	MSG #	SUPPLIER	PACKAGING	PO LINE	MO NUM
 	 		  	 	  	   	  

    private boolean checkHeader(List<Object> data,int type){
         Map<Integer, String> titre=new HashMap<>();
         if(type==1){
            titre.put(0,"PO NUM");
            titre.put(1,"STYLE");
            titre.put(2,"COL");
            titre.put(3,"COLDSP");
            titre.put(4,"SIZE");
            titre.put(5,"QTY");
            titre.put(6,"DESCRIP");
            titre.put(7,"W/H");
            titre.put(8,"LPN#");
            titre.put(9,"ABC");
         }
         if(type==0){
            titre.put(0,"Plan date");
            titre.put(1,"Description");
            titre.put(2,"Color");
            titre.put(3,"Item number");
            titre.put(4,"Quantity");
            titre.put(5,"U/M");
            titre.put(6,"U/Ctn");
            titre.put(7,"#Ctns");
            titre.put(8,"U/Bag");
            titre.put(9,"Pckgng");
            titre.put(10,"Box volume");
            titre.put(11,"CBM volume");
            titre.put(12,"Purch price");
            titre.put(13,"Amount");
         }
         
         boolean valid=true;
         String text="";
         if(data.isEmpty())
             valid=false;
         else{
         for(Map.Entry<Integer,String> st:titre.entrySet()){
             text+=st.getValue()+" \t ";
             if(!st.getValue().equals(data.get(st.getKey()))){
                valid=false; 
                
             }
         }
         }
         if(!valid)
         JOptionPane.showMessageDialog(this, "please use the right format, the header must be like below\n"+text);
        return valid;
    }
        
    public void read(int type) throws IOException  {
        FileInputStream fis = new FileInputStream(inputFile);
        Sheet sheet=null;
        islpn=false;
        ispo=false;
        int pieces=0;
        int linetoignore=0;
        Workbook book1=null;
        if(type==1)
            book1 = new XSSFWorkbook(fis);
        if(type==2)
            book1 = new HSSFWorkbook(fis);
        
        sheet = book1.getSheetAt(0);
        Map<Integer,String> obj=new HashMap<>();
        Iterator<Row> itr = sheet.iterator();
        List<Object> datarow=null;
        List<Object> titre=new ArrayList<>();
        try {
           // Iterating over Excel file in Java
            int i=0,count=0;
            Row tit=sheet.getRow(0);
            Cell ce=tit.getCell(i);
            if(formatdata.formatCellValue(ce).trim().toLowerCase().contains("po num")){
                tit=sheet.getRow(0);
                islpn=true;
                data=new ArrayList<>();
                datarow=new ArrayList<>();
                Iterator<Cell> cellIte=tit.cellIterator();
                while (cellIte.hasNext()) {
                    Cell cell = cellIte.next();
                    String val=formatdata.formatCellValue(cell).trim();
                    titre.add(val);
                }
            }else{
                while(itr.hasNext()){
                    tit=itr.next();
                    ce=tit.getCell(0);
                    String champ=formatdata.formatCellValue(ce).trim().toLowerCase();
                    String val=formatdata.formatCellValue(tit.getCell(1)).trim();
                    val=filtre(val);
                    if(champ.contains("po date"))
                        po_date.setText(val);
                            if(champ.contains("ship date"))
                                ship_date.setText(val);
                            if(champ.contains("req de"))
                                last_date.setText(val);
                            if(champ.contains("po no")){
                                po_text.setText(val);
                               ispo=true;
                            }
                            if(champ.contains("plan date")){
                                Iterator<Cell> cellIte=tit.cellIterator();
                                while (cellIte.hasNext()) {
                                    Cell cell = cellIte.next();
                                    val=formatdata.formatCellValue(cell).trim();
                                    titre.add(val);
                                }
                               count=i+1;
                               break;
                            }
                         i++;
                         System.out.println("val:"+val);
                        }
                    }
                    
                    System.out.println("count:"+count);
                    jButton3.setVisible(ispo);
                if(!checkHeader(titre,ispo?0:1))
                    return;
                grid_po.setModel(new DefaultTableModel(new Object[0][],titre.toArray()));
                    DefaultTableModel tbm = (DefaultTableModel) grid_po.getModel();
                tbm.setRowCount(0);
                    
                    
                    
                int k=0;
                while (itr.hasNext()) {
                    Object o=null;
                    Row row = itr.next();
                    ce=row.getCell(0);
                    String val=formatdata.formatCellValue(ce).trim();
                    System.out.println("valeur:"+val);
                    try{
                    if(val.toLowerCase().contains("---") ||val.toLowerCase().isEmpty() ||val.toLowerCase().contains("plan") 
                            || val.toLowerCase().contains("no") ||val.toLowerCase().contains("num")||val.toLowerCase().contains("po date")
                            ||val.toLowerCase().contains("ship date")||val.toLowerCase().contains("req del")||formatdata.formatCellValue(row.getCell(1)).trim().toLowerCase().contains("po add line"))
                        continue;
                    tbm.addRow(new Object[titre.size()]);
                    //linetoignore++;
                // Iterating over each column of Excel file 
                Iterator<Cell> cellIterator = row.cellIterator();
                int j=0;
                while (cellIterator.hasNext()) {
                    ce = cellIterator.next();
                    val=formatdata.formatCellValue(ce).trim();
                if(ispo){
                    if(j==2){
                        String color=val.split("-")[0];
                        if(color.split(" ").length>=2){
                            tbm.setValueAt(tbm.getValueAt(k-linetoignore, j-1).toString()+color.split(" ")[0].trim(), k-linetoignore, j-1);
                            val=color.split(" ")[color.split(" ").length-1].trim()+"-"+val.split("-")[1];
                        }
                    }
                    
                    if(j>2){
                         if(val.split(" ").length>=2){
                            tbm.setValueAt(tbm.getValueAt(k-linetoignore, j-1).toString()+val.split(" ")[0].trim(), k-linetoignore, j-1);
                            val=val.split(" ")[val.split(" ").length-1].trim();
                        }
                         if(j==4)
                             pieces+=Integer.parseInt(val);
                    }
                tbm.setValueAt(val, k, j);
                }
                if(islpn){
                datarow.add(val);
                tbm.setValueAt(val, k, j);
                    if(j==5)
                        pieces+=Integer.parseInt(val);
                }
                j++;
                }
                if(islpn){
                data.add(datarow.toArray());
                datarow=new ArrayList<>();
                }
                
            
                    }catch(NullPointerException e){
                        linetoignore++;
                    }
                    k++;
                    }
                
 fis.close();
 txt_box.setText(String.valueOf(grid_po.getRowCount()));
 txt_piece.setText(String.valueOf(pieces));
 } catch (FileNotFoundException fe) {
     fe.printStackTrace();
 } catch (IOException ie) {
     ie.printStackTrace(); 
 }  

 if(grid_po.getRowCount()>0)
     jButton3.setEnabled(true);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        po_text = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        po_date = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ship_date = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        last_date = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        comb_cust = new javax.swing.JComboBox();

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
        jLabel2.setText("Nbre of lines");

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
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(ship_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(5, 5, 5)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                            .addComponent(last_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(po_text, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(po_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jButton3.setText("Update");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        comb_cust.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Customer", "HWY", "AUG" }));

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
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(146, 146, 146)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comb_cust, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(comb_cust, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
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
        JOptionPane.showMessageDialog(this, "This file can't be open\nplease verify", "Error", JOptionPane.ERROR_MESSAGE);
    }      
            
           
}     
        
         
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        DefaultTableModel tbm = (DefaultTableModel) grid_po.getModel();
        tbm.setRowCount(0);
         po_date.setText(null);
         ship_date.setText(null);
         last_date.setText(null);
         po_text.setText(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        String ord=lastOrdnum();
        int ordernum=Integer.parseInt(ord);
        int line=1,del=1;
        Set<String> Orders=new HashSet<>();
        Map<String,Object[]> datatoUpdate=new HashMap<>();
        
        String po=po_text.getText().trim();
        
        if(Po_Exist(po)!=null){
            Set<String> toRemove=listOrder(po);
            String brand=Po_Exist(po_text.getText().trim())[1].toString();
            Date xfact=null;
                Date poDate=null;
                Date shipDate=null;
            try {
                xfact=formatter.parse(last_date.getText().trim());
                poDate=formatter.parse(po_date.getText().trim());
                shipDate=formatter.parse(ship_date.getText().trim());
            } catch (ParseException ex) {
                Logger.getLogger(loadlpn.class.getName()).log(Level.SEVERE, null, ex);
            }
            Map<String ,String> error =new LinkedHashMap<>();
            
            for(int i=0;i<grid_po.getRowCount();i++){
                String prtid=grid_po.getValueAt(i, 3).toString().trim();
                double po_price=grid_po.getValueAt(i, 12).toString().trim().isEmpty()?0:Double.parseDouble(grid_po.getValueAt(i, 12).toString());
                double lbs=grid_po.getValueAt(i, 10).toString().trim().isEmpty()?0:Double.parseDouble(grid_po.getValueAt(i, 10).toString());
                String ord1=String.valueOf(ordernum);
                String color=grid_po.getValueAt(i, 2).toString().trim();
                String code=prtid.substring(prtid.indexOf(".")+1,prtid.lastIndexOf("."));
                color=color.substring(color.indexOf("-")+1);
                String size=prtid.substring(prtid.lastIndexOf(".")+1);
                String style=prtid.substring(0, prtid.indexOf("."));
                int qty=(int)Double.parseDouble(grid_po.getValueAt(i, 4).toString());
                String description =grid_po.getValueAt(i, 1).toString().trim();
                String desc=description;
                if(description.toLowerCase().contains("youth"))
                        desc=description.replace("YOUTH ", "");
                    else if(description.toLowerCase().contains("yth"))
                        desc=description.replace("YTH ", "");
                    else if(description.toLowerCase().contains("girls"))
                        desc=description.replace("GIRLS ", "");
                    else if(description.toLowerCase().contains("ladies"))
                        desc=description.replace("LADIES ", "");
                    else if(description.toLowerCase().contains("lds"))
                        desc=description.replace("LDS ", "");
                    else if(description.toLowerCase().contains("adult"))
                        desc=description.replace("ADULT ", "");
                if(Prtid_Exist(prtid)==null)
                    prtid=savePartid(prtid,brand,description,desc,style,prtid,
                                size,color);
                 if(line>99){
                     line=1;
                     del++;
                 }
                if(Order_Exist(po,prtid)!=null){
                    
                    System.out.println("order_num="+Order_Exist(po,prtid));
                    String order=Order_Exist(po,prtid).trim();
                    String bran=comb_cust.getSelectedItem().toString();
                    if(updateOrder(order,ship_date.getText().trim(),last_date.getText().trim(),po_price,lbs,qty,bran)){
                        Orders.add(order);
                        System.out.println("success");
                    }else{
                        error.put("order num:"+order+" ==> ", conn.getErreur());
                        System.out.println("erreur:"+conn.getErreur());
                    }
                }else{
                    ordernum++;
                    ord1=String.valueOf(ordernum);
                    if(!saveOrder(po,ord1,prtid,poDate,qty,brand,po.concat(".").concat(style).concat(".").concat(code),shipDate,xfact,po_price,lbs,line,del)){
                        error.put("po num:"+po+"\t sku: "+prtid+" ==> ", conn.getErreur());
                        System.out.println("erreur:"+conn.getErreur());
                    }else{
                        Orders.add(ord1); 
                    }
                
                    /* System.out.println("po="+po+"   part_num="+prtid);
                    error.put("po="+po+"/t part_num="+prtid+" ==> ", "line inexist in the database");*/
                }
               line++; 
            }
            if(!error.isEmpty()){
                String er="";
                for(String err:error.keySet()){
                    er+="\n"+err+","+error.get(err);
                }
                JOptionPane.showMessageDialog(this, er, "error update", JOptionPane.ERROR_MESSAGE);
            }else{
                toRemove.removeAll(Orders);
                if(!toRemove.isEmpty()){
            String requete="delete from order_master where ordnum_10 =?";
            for(String order:toRemove){
                if(!conn.Update(requete, 0, order))
                    error.put(order, conn.getErreur());
            }
            if(!error.isEmpty()){
                String er="";
                for(String err:error.keySet()){
                    er+="\n"+err+","+error.get(err);
                }
                JOptionPane.showMessageDialog(this, er, "error delet", JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(this, "succesfully saved");
                
            }
            }
            jButton2.doClick();
        }else
            JOptionPane.showMessageDialog(this, "This PO is not in the database yet", "unknown", JOptionPane.ERROR_MESSAGE);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    public boolean saveOrder(String po,String ord,String partid,Date po_date,int qty,String brand,String udf,Date shipdate,Date X_factory,double poPrice,double lbs,int line,int del){
        String query="INSERT INTO Order_Master (ORDNUM_10,LINNUM_10,DELNUM_10,PRTNUM_10,CURDUE_10,RECFLG_10,TAXABLE_10"+
           ",TYPE_10,ORDER_10,VENID_10,ORGDUE_10,PURUOM_10,CURQTY_10,ORGQTY_10,DUEQTY_10,CURPRM_10,FILL03_10,ORGPRM_10,FILL04_10"+
           ",FRMPLN_10,STATUS_10,STK_10,CUSORD_10,PLANID_10,BUYER_10,PSCRAP_10,ASCRAP_10,SCRPCD_10,SCHCDE_10,REVLEV_10,COST_10"+
           ",CSTCNV_10,APRDBY_10,ORDREF_10,SCHFLG_10,CRTRAT_10,NEGATV_10,REQPEG_10,MPNNUM_10,LABOR_10,AMMEND_10,LOTNUM_10,BEGSER_10"+
           ",REWORK_10,CRTSNS_10,TTLSNS_10,FORCUR_10,EXCESS_10,UOMCST_10,UOMCNV_10,INSREQ_10,CREDTE_10,RTEREV_10,RTEDTE_10"+
           ",COMCDE_10,ORDPTP_10,JOBEXP_10,JOBCST_10,TAXCDE_10,TAX1_10,GLREF_10,CURR_10,UDFKEY_10,UDFREF_10,DISC_10,RECCOST_10"+
           ",MPNMFG_10,DEXPFLG_10,PLSTPRNT_10,ROUTPRNT_10,REQUES_10,ALTBOM_10,ALTRTG_10,CLASS_10,JOB_10,SUBSHP_10,XDFDTE_10) "+
            "VALUES (?,?,?,?,?,'N','N','MS','"+ord+"0000','',?,'',?,?,?,?,'',?,'','N','3','FG1-FP1',?,?,'',?,0,'N','B','',"+
           "?,1,'',?,'','','N','','',0,'N','','','Y','N',0,0,0,0,0,'',?,'',NULL,'SKU','M','Y',0,'',0,'','','',?,0,0,'','N'"+
            ",'N','N','','','','','',0,?)";
      return conn.Update(query,1, ord,line,del,partid,po_date,shipdate,qty,qty,qty,po_date,po_date,po,brand,lbs,poPrice,po.split("_")[0],po_date,udf,X_factory);
      
    }
    private Object[] Po_Exist(String Po){
         try {
             String query="select ordnum_147 ord,planid_147 brand from shoporder where ordref_147=?";
             ResultSet rs=conn.select(query, Po);
             while(rs.next())
             return new Object[]{rs.getString("ord"),rs.getString("brand")};
            
         } catch (SQLException ex) {
             Logger.getLogger(loadFrame.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return null;
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
    
    public boolean saveOrder(String po,String ord,String partid,Date po_date,int qty,String brand,String udf,Date shipdate,Date X_factory,double poPrice,double lbs,int line){
        String query="INSERT INTO Order_Master (ORDNUM_10,LINNUM_10,DELNUM_10,PRTNUM_10,CURDUE_10,RECFLG_10,TAXABLE_10"+
           ",TYPE_10,ORDER_10,VENID_10,ORGDUE_10,PURUOM_10,CURQTY_10,ORGQTY_10,DUEQTY_10,CURPRM_10,FILL03_10,ORGPRM_10,FILL04_10"+
           ",FRMPLN_10,STATUS_10,STK_10,CUSORD_10,PLANID_10,BUYER_10,PSCRAP_10,ASCRAP_10,SCRPCD_10,SCHCDE_10,REVLEV_10,COST_10"+
           ",CSTCNV_10,APRDBY_10,ORDREF_10,SCHFLG_10,CRTRAT_10,NEGATV_10,REQPEG_10,MPNNUM_10,LABOR_10,AMMEND_10,LOTNUM_10,BEGSER_10"+
           ",REWORK_10,CRTSNS_10,TTLSNS_10,FORCUR_10,EXCESS_10,UOMCST_10,UOMCNV_10,INSREQ_10,CREDTE_10,RTEREV_10,RTEDTE_10"+
           ",COMCDE_10,ORDPTP_10,JOBEXP_10,JOBCST_10,TAXCDE_10,TAX1_10,GLREF_10,CURR_10,UDFKEY_10,UDFREF_10,DISC_10,RECCOST_10"+
           ",MPNMFG_10,DEXPFLG_10,PLSTPRNT_10,ROUTPRNT_10,REQUES_10,ALTBOM_10,ALTRTG_10,CLASS_10,JOB_10,SUBSHP_10,XDFDTE_10) "+
            "VALUES (?,?,?,?,?,'N','N','MS','"+ord+"0000','',?,'',?,?,?,?,'',?,'','N','3','FG1-FP1',?,?,'',?,0,'N','B','',"+
           "?,1,'',?,'','','N','','',0,'N','','','Y','N',0,0,0,0,0,'',?,'',NULL,'SKU','M','Y',0,'',0,'','','',?,0,0,'','N'"+
            ",'N','N','','','','','',0,?)";
      
      return conn.Update(query,1, ord,line,line,partid,po_date,shipdate,qty,qty,qty,po_date,po_date,po,brand,lbs,poPrice,po.split("_")[0],po_date,udf,X_factory);
      
    }
    
    public boolean updateOrder(String ord,String shipdate,String X_factory,double poPrice,double lbs,int qty,String brand){
        String query="update Order_Master set ORGDUE_10=?,PSCRAP_10=?,COST_10=?,XDFDTE_10=?,curqty_10=?,orgqty_10=?, dueqty_10=?, planid_10=? where ORDNUM_10=? ";
         System.out.println(query);
         System.out.println("shipdate:'"+shipdate+"' poids:"+lbs+" poprice:"+poPrice+" xfa:"+X_factory+" order:"+ ord);
        return conn.Update(query,1, shipdate,lbs,poPrice,X_factory,qty,qty,qty,brand,ord);
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
    
    private String savePartid(String partid,String brand,String Descrip,String Desc,String style,String upc,
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
                       ",1,0,0,0,0,0,0,0,0,0,0,0,0,'EA',1,0,'','N',0,0,'B','','A','1','Q','N',0,0,'N','N','N','N',0,'',0,0,''" +
                       ",'','','',NULL,0,'Y','N',0,'N','','','',?,?,'N',0,'','N','','',?)";
        if(conn.Update(requete, 0, partid,brand,Desc,Descrip,style,upc,color,size))
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
    private Set<String> listOrder(String Po){
        Set<String> order=new HashSet<>();
         try {
             String query="select ordnum_147 ord from shoporder where cusord_147=?";
             ResultSet rs=conn.select(query, Po);
             while(rs.next())
                 order.add(rs.getString("ord"));
            
         } catch (SQLException ex) {
             Logger.getLogger(loadFrame.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return order;
    }
    
    private void Save(){
        Map<String,String> errors=new HashMap<>();
        String ord=lastOrdnum();
        int ordernum=Integer.parseInt(ord);
        Map<String,Object[]> datatoUpdate=new HashMap<>();
        
        String po=grid_po.getValueAt(0, 0).toString().trim();
        Set<String> toRemove=listOrder(po);
        String partid=null;
        for(int a=0;a<grid_po.getRowCount();a++){
            po=grid_po.getValueAt(a, 0).toString().trim();
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
            
            String ord1=null;
            try{
            partid=Prtid_Exist(sku)[0];
            }catch(NullPointerException e){
                //partid=savePartid(sku,plan.getSelectedItem().toString(),description,desc,style,wh,sku,size,color);
                if(conn.getErreur()!=null){
                    errors.put(sku, conn.getErreur());
                }
            }
            
            //ord1=Order_Exist(po,partid);
                if(ord1==null){
                    ordernum++;
                    ord1=String.valueOf(ordernum);
                    //String key=Integer.parseInt(wh)>0?"":wh;
                    //saveOrder(po, ord1, partid,date_plan.getDate(), qty, plan.getSelectedItem().toString(),classe, sku1, classe);
                    if(conn.getErreur()!=null){
                    errors.put(ord1, conn.getErreur());
                }
                }
                if(partid!=null && ord1!=null){
                    int inc=1;
                    //for(String lpn:listData.get(po.concat(".").concat(sku)).keySet()){
                    
                    String ind=String.valueOf(inc);
                    String sticker=ord1+"000".substring(ind.length())+ind;
                    //if(!saveLpn(ord1,lpn,sticker,listData.get(po.concat(".").concat(sku)).get(lpn))){
                        //System.err.println(conn.getErreur());
                    //errors.put(lpn, conn.getErreur());
                    //}
                    inc++;
                    //System.out.println(sticker+"\t"+lpn);
                    //}
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
    private javax.swing.JComboBox comb_cust;
    private javax.swing.JTable grid_po;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
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
    private String  filtre(String a){
        String t=a;
        if(t.length()>8){
            String tt[]=t.split(" ");
            for(int ii=0;ii<tt.length;ii++){
                if(tt[ii].trim().length()==8)
                    t=tt[ii];
            }
        }
        return t;
    }
    
}
