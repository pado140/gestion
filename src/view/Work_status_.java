/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import connection.ConnectionDb;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Duvers
 */
public class Work_status_ extends javax.swing.JInternalFrame {

    private ConnectionDb conn = ConnectionDb.instance();
    private Map<String , Integer> prod;
    private Map<String, String> ref=null;
    private Map<String, Integer> sewn,packed,plan,second,ship,soa,padprint,sew,sewing;
    private Set<Object[]> listeData,temp;
    private Map<String,Map<String,Map<String,Map<String,Integer>>>> cut_pro=new HashMap<>();
    /**
     * Creates new form Work_status
     */
    public Work_status_() {
        initComponents();
        
        init();
        
    }

    private Map<String ,Integer> padprint(){
        String requete="Select po,sku, sum(qty) qty from At_padprint group by po,sku";
        padprint=new HashMap<>();
        ResultSet rs=conn.select(requete);
        try {
            while(rs.next()){
                String order=rs.getString("po").trim();
                order+="."+rs.getString("sku").trim();
                padprint.put(order, rs.getInt("qty"));
                       
            }
        } catch (SQLException ex) {
               //Logger.getLogger(Cansew.class.getName()).log(Level.SEVERE, null, ex);
           }
        return padprint;
    }
    
    private Map<String ,Integer> at_sewing(){
        String requete="Select po,sku, qty from At_sewing1";
        sew=new HashMap<>();
        ResultSet rs=conn.select(requete);
        try {
            while(rs.next()){
                String order=rs.getString("po").trim();
                order+="."+rs.getString("sku").trim();
                sew.put(order, rs.getInt("qty"));
                       
            }
        } catch (SQLException ex) {
               //Logger.getLogger(Cansew.class.getName()).log(Level.SEVERE, null, ex);
           }
        return sew;
    }
    
    private Map<String ,Integer> saoBar(){
        String requete="Select po,sku, sum(qty) qty from at_soabar group by po,sku";
        soa=new HashMap<>();
        ResultSet rs=conn.select(requete);
        try {
            while(rs.next()){
                String order=rs.getString("po").trim();
                order+="."+rs.getString("sku").trim();
                soa.put(order, rs.getInt("qty"));
                       
            }
        } catch (SQLException ex) {
               //Logger.getLogger(Cansew.class.getName()).log(Level.SEVERE, null, ex);
           }
        return soa;
    }
    private void initSewn(){
        String requete="select * from sewn_LAST WHERE type_sew='first'";
        sewn =new HashMap<>();
        ResultSet rs=conn.select(requete);
        try {
            while(rs.next()){
                String order=rs.getString("po").trim();
                order+="."+rs.getString("sku").trim();
              sewn.put(order,rs.getInt("qty"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Work_status.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void initPacked(){
        String requete="select po,sku,packed from pack1";
        packed=new HashMap<>();
        ResultSet rs=conn.select(requete);
        
    try {
        while(rs.next()){
            packed.put(rs.getString("po").trim().concat("."+rs.getString("sku").trim()),rs.getInt("packed"));
        }
    } catch (SQLException ex) {
        Logger.getLogger(WIPCONTROL.class.getName()).log(Level.SEVERE, null, ex);
    }
        
    }
    private void initSewStart(){
        String requete="select po,sku, qty from at_mod";
        sewing=new HashMap<>();
        ResultSet rs=conn.select(requete);
        
    try {
        while(rs.next()){
            sewing.put(rs.getString("po").trim().concat("."+rs.getString("sku").trim()),rs.getInt("qty"));
        }
    } catch (SQLException ex) {
        Logger.getLogger(WIPCONTROL.class.getName()).log(Level.SEVERE, null, ex);
    }
        
    }
    private void initPlan(){
        String requete="select * from [plan1]";
        plan =new HashMap<>();
        ResultSet rs=conn.select(requete);
        try {
            while(rs.next()){
                //System.out.println("sku plan:"+rs.getString("po").trim().concat(".").concat(rs.getString("sku")).trim());
              plan.put(rs.getString("po").trim().concat(".").concat(rs.getString("sku")).trim(),rs.getInt("pieces"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Work_status.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void shipped(){
        String requete="select qty,po,sku from Shipped1";
        ship =new HashMap<>();
        ResultSet rs=conn.select(requete);
        try {
            while(rs.next()){
                String order=rs.getString("po").trim();
                order+="."+rs.getString("sku").trim();
              ship.put(order,rs.getInt("qty"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Work_status.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void Second(){
        String requete="select * from sewn_LAST WHERE type_sew='second'";
        second =new HashMap<>();
        ResultSet rs=conn.select(requete);
        try {
            while(rs.next()){
                String order=rs.getString("po").trim();
                order+="."+rs.getString("sku").trim();
              second.put(order,rs.getInt("qty"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Work_status.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void init(){
        prod=new HashMap<>();
        load();
        Second();
        shipped();
        data_cut();
        initPlan();
        padprint();
        at_sewing();
        initSewStart();
        saoBar();
        initSewn();
        initPacked();
        listData();
    }
    
    
    private void load(){
        listeData=new HashSet<>();
        String requete="select * from orderplan";
        ResultSet rs = conn.select(requete);
        try {
            while(rs.next()){
                String sku=rs.getString("sku").trim();
               
                String size=rs.getString("size");
                String desc=rs.getString("description").trim(),
                        po=rs.getString("po").trim();
                
                if(!rs.getString("brand").equals("CHS")){
                if(desc.toLowerCase().contains("yth")||desc.toLowerCase().contains("youth")||
                        desc.toLowerCase().contains("girls")||desc.toLowerCase().contains("boys"))
                size="Y"+size;
                }
                String status=rs.getString("status_147");
                Object[] data=new Object[24];
                data[0]=rs.getString("brand");
                data[1]=po;
                data[2]=rs.getString("style").trim();
                data[3]=rs.getString("color_code").trim();
                data[4]=rs.getString("color").trim();
                data[5]=size.trim();
                data[7]=rs.getInt("pieces");
                data[8]=status;
                data[6]=po+"-"+sku;
                data[9]=rs.getString("ordnum_147");
                data[10]=data[11]=data[12]=data[13]=data[14]=data[15]=data[16]=data[17]=data[18]=data[19]=data[20]=data[21]=0;
                //data[19]
                data[22]=sku;
                data[23]=status;
                listeData.add(data);
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(Work_status.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void listData(){
        
        //listeData=new HashSet<>();
        DefaultTableModel tbm = (DefaultTableModel) grid_data.getModel();
        tbm.setRowCount(0);
        for(Object[] data:listeData){
            String ordnum=data[9].toString().trim();
            String Sku=data[1].toString().trim()+"."+data[22].toString().trim();
            //System.out.println("sku:"+Sku);
                String status="cut not start yet";
                int bal=Integer.parseInt(data[7].toString());
                //data[0]=Sku.substring(0, Sku.indexOf("."));
                data[10]=data[11]=data[12]=data[13]=data[14]=data[15]=data[16]=data[17]=data[18]=data[19]=data[20]=0;
                data[21]=data[7].toString();
                int so=0,pp=0,se=0,planqty=0,prodqty=0;
                try{
                    if(plan.containsKey(Sku.trim())){
                         status="ready to cut";
                         planqty=plan.get(Sku.trim());
                         
                            data[10]=plan.get(Sku.trim());
                         
                     }
                     
                    if(prod.get(Sku.trim())>0){
                        data[10]=0;
                        status="cutting";
                        prodqty=prod.get(Sku.trim());
                        
                        if(prodqty>=planqty){
                            status="cut";
                            
                            
                            //data[12]=prod.get(Sku.trim())-so;
                        }
                    }
                    if(soa.containsKey(Sku.trim())){
                         so=soa.get(Sku.trim());
                            status="At Soabar";
                            //data[13]=soa.get(Sku.trim())-pp;
                        }
                    if(padprint.containsKey(Sku.trim())){
                        pp=padprint.get(Sku.trim());
                            status="At pad print/Heat transfer";
                            //data[14]=pp-se;
                        }
                    
                    if(sew.containsKey(Sku.trim())){
                         se=sew.get(Sku.trim());
                            status="Recieved";
                            //data[15]=se;
                        }
                     
                     //if(data[23].equals("3")){
                     
                    //}
                        
                            
                            int val=0;
                            int pac=0;
                            int sec=0,sh=0;
                            if(sewn.containsKey(Sku.trim()))
                                val=sewn.get(Sku.trim());
                                
                            //System.out.println("sewn:"+rs.getString("ordnum_147").trim()+"\t"+val);
                            
                            if(packed.containsKey(Sku.trim())){
                                pac=packed.get(Sku.trim());
                            }
                            if(second.containsKey(Sku.trim())){
                                sec=second.get(Sku.trim());
                            }
                            if(ship.containsKey(Sku.trim())){
                                sh=ship.get(Sku.trim());
                                bal=Integer.parseInt(data[7].toString())-sh;
                            }
                            int sew_start=0;
                            if(sewing.containsKey(Sku.trim())){
                                sew_start=sewing.get(Sku.trim());
                                status="WIP";
                            }
                            sew_start-=(val+sec);
                            /*if(Sku.trim().equals("9604979.2909.065.S")){
                            System.out.println("sku:"+Sku.trim()+"\tcut:"+prodqty+"\t"
                            + "soabar:"+so+"\t"
                            + "padprint:"+pp+"\t"
                            + "sewing:"+se+"\t"
                            + "sew_");
                            }*/
                            data[20]=sh;
                            data[19]=pac-sh;
                            data[18]=sec;
                            data[17]=val-pac;
                            data[16]=sew_start;
                            data[15]=se-(sew_start+val+sec);
                            data[14]=pp-se;
                            data[13]=so-pp;
                            data[12]=prodqty-so;
                            data[11]=planqty-prodqty;
                            
                            
                            if(planqty-prodqty>0)
                                status="cutting";
                            else{
                                if(prodqty-so>0)
                                    status="cut";
                                else{
                                    if(so-pp>0)
                                        status="At Soabar";
                                    else{
                                        if(pp-se>0)
                                            status="At pad print/Heat transfer";
                                        else{
                                            if(se-(sew_start+val+sec)>0)
                                                status="Recieved";
                                            else
                                                if(sew_start+val+sec>0)
                                                    status="WIP-At mod";
                                                else{
                                                    
                                                }
                                        }
                                    }
                                }
                            }
                            
                            
                        
                        data[21]=bal;
                    
                    
                }catch(NullPointerException e){
                    
                }
                if(data[23].equals("5"))
                    status="Closed";
                data[8]=status;
                listeData.add(data);
                tbm.addRow(data);
                
            }
           
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        POPUP = new javax.swing.JPopupMenu();
        DETAILS = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        grid_data = new javax.swing.JTable();

        DETAILS.setText("Close SKU");
        DETAILS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DETAILSActionPerformed(evt);
            }
        });
        POPUP.add(DETAILS);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Summary");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel1.setText("PO FILTER");

        jLabel2.setText("STYLE FILTER");

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jLabel3.setText("SIZE FILTER");

        jLabel4.setText("COLOR FILTER");

        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jButton1.setText("refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/icon/export.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setText("CLIENT FILTER");

        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(43, 43, 43)))
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jButton2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1)
                                .addComponent(jLabel5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        grid_data.setAutoCreateRowSorter(true);
        grid_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "CUSTOMER", "PO NUM", "STYLE", "CODE COLOR", "COLOR", "SIZE", "SKU", "QTY", "STATUS", "WORK ORDER", "READY TO CUT", "CUTTING", "CUT", "AT SOBAR", "PAD PRINT", "AT SEWING", "SEW START", "FIRST", "SECOND", "PACKING", "SHIPPED", "BALANCE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grid_data.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                grid_dataMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(grid_data);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        // TODO add your handling code here:
        //listData();
    }//GEN-LAST:event_formInternalFrameActivated

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentHidden

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        buscar();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void grid_dataMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grid_dataMouseReleased
        // TODO add your handling code here:
        if(Principal.group.equals("Merchandizing")||Principal.group.equals("MIS")||
                Principal.group.equals("Planing")){
           
        if(evt.getButton()==MouseEvent.BUTTON3){
            if(!grid_data.getSelectionModel().isSelectionEmpty())
            POPUP.show(grid_data,evt.getX(),evt.getY());
            else
                JOptionPane.showMessageDialog(this, "Please select a row!", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
        }
    }//GEN-LAST:event_grid_dataMouseReleased

    private void DETAILSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DETAILSActionPerformed
        // TODO add your handling code here:
        for(int i=0;i<grid_data.getSelectedRowCount();i++){
            int ligne=grid_data.getSelectedRows()[i];
        //grid_data.getValueAt(ligne, 0);
        if(changeStatus(grid_data.getValueAt(ligne, 9).toString()))
            grid_data.setValueAt("Closed",ligne, 9);
        
        }
    }//GEN-LAST:event_DETAILSActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:
        buscar();
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        // TODO add your handling code here:
        buscar();
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        // TODO add your handling code here:
        buscar();
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        init();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JFileChooser file=new JFileChooser("C:/",FileSystemView.getFileSystemView());
        file.setDialogTitle("enregistre le fichier");

        file.setFileFilter(new FileNameExtensionFilter("Workbook excel","xlsx","xls"));
        int returnAct=file.showSaveDialog(this);
        if(returnAct==JFileChooser.APPROVE_OPTION){
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("Work Status");

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
            }catch (FileNotFoundException ex) {
                Logger.getLogger(Bundle.class.getName()).log(Level.SEVERE, null, ex);
            }catch (IOException ex) {
                Logger.getLogger(Bundle.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "File saved with success");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
        // TODO add your handling code here:
        buscar();
    }//GEN-LAST:event_jTextField5KeyReleased

    private void buscar(){
        String potxt=jTextField1.getText().trim().toLowerCase();
        String style=jTextField2.getText().trim().toLowerCase();
        String col=jTextField3.getText().trim().toLowerCase();
        String size=jTextField4.getText().trim().toLowerCase();
        String client=jTextField5.getText().trim().toLowerCase();
        
        String data="";
        
        DefaultTableModel tbm = (DefaultTableModel) grid_data.getModel();
        tbm.setRowCount(0);
        for(Object[] o:listeData){
            if(o[1].toString().toLowerCase().contains(potxt.trim().toLowerCase())&& o[2].toString().contains(style.trim().toLowerCase())&&
                    (o[4].toString().toLowerCase().contains(col.trim())||o[3].toString().toLowerCase().contains(col.trim()))&&
                    o[5].toString().toLowerCase().contains(size.trim())&&o[0].toString().toLowerCase().contains(client)
               )
                                    {
                tbm.addRow(o);
            }
        }
    }
/*private boolean get(String br,String style,String typ){
        Map<String,Map<String,Set<String>>> listbrand=liststyle();
        for(String bran:listbrand.keySet()){
            //System.out.println("Brand :"+bran);
            //System.out.println("Brand :"+br.equals(bran.trim()));
            if(br.equals(bran.trim())){
                
               for(String styl: listbrand.get(bran).keySet()){
                   if(style.equals(styl.trim())){
                       //System.out.println("style :"+styl);
                   for(String type:listbrand.get(bran).get(styl)){
                       if(type.equals(typ.trim())){
                       //System.out.println("Brand :"+bran+"\t style :"+styl+"\t type :"+type);
                       return true;
                       }
                   }
                 }
               }
            }
           }
        return false;
    }*/
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
    
    /*private Map<String,Map<String,Set<String>>> liststyle(){
    String query="select BRAND,styfam,TYPE FROM StyleMaster GROUP BY BRAND,styfam,TYPE";
    Map<String,Map<String,Set<String>>> listbrand=new HashMap<String,Map<String,Set<String>>>();
    Map<String,Set<String>> lissty=new HashMap<String,Set<String>>();
    ResultSet rs=conn.select(query);
    
    try {
    rs.first();
    String brand=rs.getString("BRAND"),
    sty=rs.getString("styfam"),
    ty=rs.getString("TYPE");
    Set<String> listty=new HashSet<String>();
    
    rs.beforeFirst();
    while(rs.next()){
    
    
    
    if(!rs.getString("styfam").equals(sty)){
    lissty.put(sty, listty);
    listty=new HashSet<String>();
    sty=rs.getString("styfam");
    
    }
    if(!rs.getString("brand").equals(brand)){
    listbrand.put(brand, lissty);
    lissty=new HashMap<String,Set<String>>();
    sty=rs.getString("styfam");
    brand=rs.getString("BRAND");
    }
    ty=rs.getString("TYPE");
    listty.add(ty);
    
    if(rs.isLast()){
    lissty.put(sty, listty);
    listbrand.put(brand, lissty);
    }
    }
    } catch (SQLException ex) {
    //Logger.getLogger(Cansew.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return listbrand;
    }*/
    private Map<String,Integer> type(){
        Map<String,Integer> typ=new HashMap<>();
        String requete="select ordnum_10,po,sku,count(type_id) n from cut1 where type_id NOT IN(4,6,7) group by ordnum_10,po,sku";
        ResultSet rs=conn.select(requete);
        try {
            while(rs.next()){
                typ.put(rs.getString("po").trim().concat("."+rs.getString("sku").trim()),rs.getInt("n"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Ready_to_sew.class.getName()).log(Level.SEVERE, null, ex);
        }
        return typ;
    }
    private Map<String,Integer> listType(){
        String query="select Distinct style,count(distinct[TYPE]) t FROM StyleMaster where type_id not in(4,6,7) group by style";
        ResultSet rs=conn.select(query);
        Map<String,Integer> lissty=new HashMap<>();
        try {
            while(rs.next()){
                String sty=rs.getString("style").trim();
                lissty.put(sty, rs.getInt("t"));
                       
            }
        } catch (SQLException ex) {
               //Logger.getLogger(Cansew.class.getName()).log(Level.SEVERE, null, ex);
           }
        return lissty;
    }
    private void data_cut(){
                 Map<String,Integer> listType=listType();
                 Map<String,Integer> typecount=type();
               Map<String,Integer> production=new HashMap<>();
               ref=new HashMap<>();
               String query = "SELECT * from SUM_CUT1";
                try {
               ResultSet rs = conn.select(query);
               
               while (rs.next()){
                       production.put(rs.getString("po").trim().concat("."+rs.getString("sku").trim()), rs.getInt("qty"));
                       ref.put(rs.getString("po").trim().concat("."+rs.getString("sku").trim()),rs.getString("style"));
               }
           } catch (SQLException ex) {
               Logger.getLogger(Bundle.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           for(String order:production.keySet()){
               int value=0;
               try{
                   value=production.get(order);
                   }catch(NullPointerException e){
                       value=0;
                   }
               
               String style=ref.get(order);
               
               int typ=0,ty=0;
               try{
                   typ=listType.get(style.trim());
                   
               }catch(NullPointerException e){
                   typ=0;
               }
               
               try{
                   ty=typecount.get(order);
               }catch(NullPointerException e){
                   ty=0;
               }
               if(order.contains("181.BP28.BW")){
               System.out.println("type2:"+typecount.get(order));
               System.out.println("type:"+typ);
               }
               if(ty==typ)
               prod.put(order, value);
           }
    }
    private int Min(Object... obj){
         int min=Integer.parseInt(obj[0].toString());
            for(int i=1;i<obj.length;i++){
                if(min>Integer.parseInt(obj[i].toString()))
                    min=Integer.parseInt(obj[i].toString());
            }
         return min;
     }
    
    private boolean changeStatus(String ordnum){
        String requete="update shoporder set status_147='5' where ordnum_147=?";
        return conn.Update(requete, 0, ordnum);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem DETAILS;
    private javax.swing.JPopupMenu POPUP;
    private javax.swing.JTable grid_data;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
