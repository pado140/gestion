package Load_data;


import connection.ConnectionDb;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class loadWest extends javax.swing.JInternalFrame implements Observe{

     private String inputFile;
     private final ConnectionDb conn=ConnectionDb.instance();
     int result = 0;
     int sizeid=0,colorid=0,poid=0,styleid=0;
     boolean islpn=false,ispo=false;
     List<Object[]> data=null;
     
     DataFormatter formatdata;
     DateFormat dateformat=new SimpleDateFormat("dd/MM/yyyy");
     private final SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yy");
     //private JInternalFrame summary

  
    public loadWest() {
        initComponents();
        formatdata=new DataFormatter();
       grid_po.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 13 ));
    }
    
    private void init(){
        po_date.setText("");
         ship_date.setText("");
        last_date.setText("");
        po_text.setText("");
        cutno.setText("");
        DefaultTableModel tbm = (DefaultTableModel) grid_po.getModel();
        tbm.setRowCount(0);
    }
    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    private boolean checkHeader(List<Object> data){
         Map<Integer, String> titre=new HashMap<>();
            titre.put(0,"Style");
            titre.put(1,"DESCRIPTION");
            titre.put(2,"COLOR");
            titre.put(3,"SIZE");
            titre.put(4,"QTY");
            titre.put(5,"PO-PRICE");
            titre.put(6,"UPC");
            titre.put(7,"QTY/BOX");
            titre.put(8,"MASTER CTN");
         
         boolean valid=true;
         String text="";
         for(Map.Entry<Integer,String> st:titre.entrySet()){
             text+=st.getValue()+" \t ";
             if(!st.getValue().toLowerCase().equals(data.get(st.getKey()).toString().trim().toLowerCase())){
                valid=false; 
                
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
                
        Iterator<Row> itr = sheet.iterator();
        List<Object> datarow=null;
        List<Object> titre=new ArrayList<>();
        try {
            int i=0,count=0;
            Row tit=sheet.getRow(0);
            Cell ce=tit.getCell(i);
                  boolean isblank=false;
            while(itr.hasNext()){
                tit=itr.next();
                    ce=tit.getCell(0);
                    String champ=formatdata.formatCellValue(ce).trim().toLowerCase();
                    String val=formatdata.formatCellValue(tit.getCell(1)).trim();
                    if(champ.contains("date"))
                        po_date.setText(val);
                    if(champ.contains("ship date"))
                        ship_date.setText(val);
                    if(champ.contains("in w/h"))
                        last_date.setText(val);
                    if(champ.contains("po#"))
                        po_text.setText(val);
                    if(champ.contains("po ref"))
                        cutno.setText(val);
                    if(champ.contains("style")){
                        Iterator<Cell> cellIte=tit.cellIterator();
                        while (cellIte.hasNext()) {
                            Cell cell = cellIte.next();
                            val=formatdata.formatCellValue(cell).trim();
                            if(val==null|| val.equals("")){
                                isblank=true;
                                    break;
                            }
                            titre.add(val);
                        }
                        break;
                    }
                    System.out.println("val:"+val);
                }
            
            if(!checkHeader(titre))
                return;
             jButton3.setVisible(true);
                grid_po.setModel(new DefaultTableModel(new Object[0][],titre.toArray()));
                    DefaultTableModel tbm = (DefaultTableModel) grid_po.getModel();
                tbm.setRowCount(0);
                
        int k=0;
        while (itr.hasNext()) {
            Object o=null;
            Row row = itr.next();
            ce=row.getCell(0);
                    
            try{
                if(!formatdata.formatCellValue(ce).trim().equals("")){
                    tbm.addRow(new Object[titre.size()]);
                    Iterator<Cell> cellIterator = row.cellIterator();
                    int j=0;
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        o=formatdata.formatCellValue(cell).trim();
                
                        if(!o.toString().trim().isEmpty()){
                        if(j==0 || j==6)
                            o=o.toString().replace(".", "@").split("@")[0];
                        tbm.setValueAt(o, k, j);
                        }else
                            break;
                        j++;
                    }
                }else
                    break;
            }catch(NullPointerException e){
                e.printStackTrace();
                linetoignore++;
            }
                    k++;
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        po_text = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        po_date = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ship_date = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        last_date = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cutno = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

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

        jLabel7.setText("Cut no:");

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
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cutno, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addContainerGap())
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
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
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
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cutno))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton3.setText("Save");
        jButton3.setVisible(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
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
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(139, 139, 139)
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        File selectedFile = null;
          JFileChooser fileChooser = new JFileChooser();
fileChooser.setCurrentDirectory(new File(System.getProperty("user.home").concat("/Dropbox")));
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
        po_date.setText("");
        ship_date.setText("");
        last_date.setText("");
        po_text.setText("");
        cutno.setText("");
        DefaultTableModel tbm = (DefaultTableModel) grid_po.getModel();
        tbm.setRowCount(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    //private boolean update(String order, String po,String sku, )
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        String ord=lastOrdnum();
        int ordernum=Integer.parseInt(ord);
        int line=1,del=1;
        Calendar cal=Calendar.getInstance();
         Map<String ,String> error =new LinkedHashMap<>();
        if(Po_Exist(po_text.getText().trim())==0){
            String po=po_text.getText().trim();
            String cutnotxt=cutno.getText().trim();
            Date xfact=null;
                Date poDate=null;
                Date shipDate=null;
            try {
                xfact=formatter.parse(last_date.getText().trim());
                poDate=formatter.parse(po_date.getText().trim());
                cal.add(Calendar.DAY_OF_YEAR, 6);
                shipDate=cal.getTime();
            } catch (ParseException ex) {
                Logger.getLogger(loadlpn.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String brand="WCR";
            for(int i=0;i<grid_po.getRowCount();i++){
                if(grid_po.getValueAt(i, 0).toString().trim()!=null){
                ordernum++;
                String ord1=String.valueOf(ordernum);
                
                String prtid="";
                String color=grid_po.getValueAt(i, 2).toString().trim();
                String code=color.substring(0,color.indexOf("-"));
                color=color.substring(color.indexOf("-")+1);
                String size=grid_po.getValueAt(i, 3).toString().trim();
                String style=grid_po.getValueAt(i, 0).toString().trim();
                prtid=style.concat(".").concat(code).concat(".").concat(size);
                int qty=(int)Double.parseDouble(grid_po.getValueAt(i, 4).toString());
                int box=(int)Double.parseDouble(grid_po.getValueAt(i, 7).toString());
                String masterCTN=grid_po.getValueAt(i, 8).toString();
                double po_price=grid_po.getValueAt(i, 5).toString().trim().isEmpty()?0:Double.parseDouble(grid_po.getValueAt(i, 5).toString());
                double lbs=0;
                String description =grid_po.getValueAt(i, 1).toString().trim();
                String desc=description;
                String upc=grid_po.getValueAt(i, 6).toString().trim();
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
                    prtid=savePartid(prtid,brand,description,desc,style,upc,
                                size,color);
                 if(line>99){
                     line=1;
                     del++;
                 }
                if(!saveOrder(po,ord1,prtid,poDate,qty,brand,po.concat(".").concat(style).concat(".").concat(code),shipDate,xfact,po_price,lbs,line,del,cutnotxt)){
                        error.put("po num:"+po+"\t sku: "+prtid+" ==> ", conn.getErreur());
                        System.out.println("erreur:"+conn.getErreur());
                    }else{
                    
                    for(int k=0;k<qty/box;k++){
                        saveLpn(ord1, ord1.trim()+box+(k+1), ord1.trim()+box+(k+1), box);
                        conn.savecst("{call create_box(?,?,?,?,?,?,?)}",ord1,ord1.trim()+box+(k+1),masterCTN.trim(),box,box,ord1.trim()+box+(k+1),"");
                    }
                }
                line++;
                }
            }
            if(error.isEmpty()){
                
                JOptionPane.showMessageDialog(this, "Successfully saved", "save", JOptionPane.PLAIN_MESSAGE);
            }else
                {
                String er="";
                for(String err:error.keySet()){
                    er+="\n"+err+","+error.get(err);
                }
                JOptionPane.showMessageDialog(this, er, "error insert", JOptionPane.ERROR_MESSAGE);
            }
        }else
            JOptionPane.showMessageDialog(this, "This PO is already in the database", "duplicate", JOptionPane.ERROR_MESSAGE);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private int Po_Exist(String Po){
         try {
             String query="select ordnum_147 from shoporder where ordref_147=?";
             ResultSet rs=conn.select(query, Po);
             while(rs.next())
             return rs.getInt(1);
            
         } catch (SQLException ex) {
             Logger.getLogger(loadFrame.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return 0;
    }
    
    private int masterNo(String master){
        String requete="select count(master) compte from box_contain where master=?";
        ResultSet rs=conn.select(requete, master);
         try {
             rs.first();
             return rs.getInt("compte");
         } catch (SQLException ex) {
             Logger.getLogger(loadWest.class.getName()).log(Level.SEVERE, null, ex);
         }
        return 0;
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
    
    public boolean saveOrder(String po,String ord,String partid,Date po_date,int qty,String brand,String udf,Date shipdate,Date X_factory,double poPrice,double lbs,int line,int del,String cut){
        String query="INSERT INTO Order_Master (ORDNUM_10,LINNUM_10,DELNUM_10,PRTNUM_10,CURDUE_10,RECFLG_10,TAXABLE_10"+
           ",TYPE_10,ORDER_10,VENID_10,ORGDUE_10,PURUOM_10,CURQTY_10,ORGQTY_10,DUEQTY_10,CURPRM_10,FILL03_10,ORGPRM_10,FILL04_10"+
           ",FRMPLN_10,STATUS_10,STK_10,CUSORD_10,PLANID_10,BUYER_10,PSCRAP_10,ASCRAP_10,SCRPCD_10,SCHCDE_10,REVLEV_10,COST_10"+
           ",CSTCNV_10,APRDBY_10,ORDREF_10,SCHFLG_10,CRTRAT_10,NEGATV_10,REQPEG_10,MPNNUM_10,LABOR_10,AMMEND_10,LOTNUM_10,BEGSER_10"+
           ",REWORK_10,CRTSNS_10,TTLSNS_10,FORCUR_10,EXCESS_10,UOMCST_10,UOMCNV_10,INSREQ_10,CREDTE_10,RTEREV_10,RTEDTE_10"+
           ",COMCDE_10,ORDPTP_10,JOBEXP_10,JOBCST_10,TAXCDE_10,TAX1_10,GLREF_10,CURR_10,UDFKEY_10,UDFREF_10,DISC_10,RECCOST_10"+
           ",MPNMFG_10,DEXPFLG_10,PLSTPRNT_10,ROUTPRNT_10,REQUES_10,ALTBOM_10,ALTRTG_10,CLASS_10,JOB_10,SUBSHP_10,XDFDTE_10) "+
            "VALUES (?,?,?,?,?,'N','N','MS','"+ord+"0000','',?,'',?,?,?,?,'',?,'','N','3','FG1-FP1',?,?,'',?,0,'N','B','',"+
           "?,1,'',?,'','','N','','',0,'N','','','Y','N',0,0,0,0,0,'',?,'',NULL,'SKU','M','Y',0,'',0,'','',?,?,0,0,'','N'"+
            ",'N','N','','','','','',0,?)";
      return conn.Update(query,1, ord,line,del,partid,po_date,shipdate,qty,qty,qty,po_date,po_date,po,brand,lbs,poPrice,po.split("_")[0],po_date,cut,udf,X_factory);
      
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
    
    private void Save(){
        Map<String,String> errors=new HashMap<>();
        String ord=lastOrdnum();
        int ordernum=Integer.parseInt(ord);
        String partid=null;
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
            
            String ord1=null;
            try{
            partid=Prtid_Exist(sku)[0];
            }catch(NullPointerException e){
                partid=savePartid(sku,"WCR",description,desc,style,sku,size,color);
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
    
    private boolean saveLpn(String ordnum,String lpn,String sticker,int qty){
        String requete="INSERT INTO BOX_CONTAIN(ORDNUM,LPN,BOX_STICKERS,QTY) VALUES (?,?,?,?)";
        return conn.Update(requete, 0, ordnum,lpn,sticker,qty);
    }
    /**
     * @param args the command line arguments
     */
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cutno;
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
    private javax.swing.JLabel jLabel7;
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
