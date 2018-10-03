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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
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
public class Work_status_open extends javax.swing.JInternalFrame {

    private ConnectionDb conn = ConnectionDb.instance();
    private Map<String , Integer> prod;
    private Map<String, String> ref=null;
    private Map<String, Integer> sewn,packed,plan,second,ship,soa,padprint,sew,sewing;
    private Set<Object[]> listeData,temp;
    private Map<String,Map<String,Map<String,Map<String,Integer>>>> cut_pro=new HashMap<>();
    private PopulateTable populate;
    /**
     * Creates new form Work_status_
     */
    
    class PopulateTable extends SwingWorker<Void,Object[]>{

        @Override
        protected Void doInBackground(){
        load();
            listData();
            return null;
        }

        @Override
        protected void process(List<Object[]> chunks) {
            super.process(chunks); //To change body of generated methods, choose Tools | Templates.
            grid_data.notify();
        }
        
    }
    
    /**
     * Creates new form Work_status
     */
    public Work_status_open() {
        initComponents();
        
        init();
        
    }

   
    private void initPlan(){
        String requete="select * from [plan1]";
        plan =new HashMap<>();
        ResultSet rs=conn.select(requete);
        try {
            while(rs.next()){
                //System.out.println("sku plan:"+rs.getString("po").trim().concat(".").concat(rs.getString("sku")).trim());
              plan.put(rs.getString("order").trim(),rs.getInt("pieces"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Work_status.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void init(){
        
        prod=new HashMap<>();
        
        data_cut();
        initPlan();
        populate=new PopulateTable();
        populate.execute();
    }
    
    
    private void load(){
        int tot=0,line=0;
        int cut=0,aso=0,apad=0,first_tot=0,tplan=0,i=0;
        listeData=new HashSet<>();
        String requete="select * from work_process where state<>'5'";
        ResultSet rs = conn.select(requete);
        int planqty=0,prodqty=0;
        
        try {
            while(rs.next()){
                String sku=rs.getString("sku").trim();
               String status="cut not start yet";
                String size=rs.getString("size");
                String desc=rs.getString("description").trim(),
                        po=rs.getString("po").trim(),
                        order=rs.getString("work_order").trim();
                
                if(!rs.getString("brand").equals("CHS")){
                if(desc.toLowerCase().contains("yth")||desc.toLowerCase().contains("youth")||
                        desc.toLowerCase().contains("girls")||desc.toLowerCase().contains("boys"))
                size="Y"+size;
                }
                int sobar=rs.getInt("at_sb");
                int pp=rs.getInt("at_pp");
                int se=rs.getInt("at_sew");
                int mod=rs.getInt("at_mod");
                int first=rs.getInt("sewn");
                int second=rs.getInt("second");
                int pack=rs.getInt("packed");
                int shipped=rs.getInt("shipped");
                int cutting=0;
                int bal=rs.getInt("qty")-shipped;
                try{
                    planqty=plan.get(order);
                }catch(NullPointerException ex){
                    planqty=0;
                }
                try{
                    prodqty=prod.get(order);
                }catch(NullPointerException ex1){
                    prodqty=0;
                }
                if(prodqty>0){
                cutting=planqty-prodqty;
                planqty-=(prodqty+cutting);
                }
                prodqty-=sobar;
                sobar-=pp;
                pp-=se;
                se-=mod;
                mod-=(first+second);
                first-=pack;
                pack-=shipped;
                
                Object[] data=new Object[25];
                data[1]=rs.getString("brand");
                data[2]=po;
                data[3]=rs.getString("style").trim();
                data[4]=sku.substring(sku.indexOf(".")+1, sku.lastIndexOf("."));
                data[5]=rs.getString("color").trim();
                data[6]=size.trim();
                data[8]=rs.getInt("qty");
                
                data[7]=po+"-"+sku;
                data[10]=order;
                
                data[11]=planqty;
                data[12]=cutting;
                data[13]=prodqty;
                data[14]=sobar;
                data[15]=pp;
                data[16]=se;
                data[17]=mod;
                data[18]=first;
                data[19]=second;
                data[20]=pack;
                data[21]=shipped;
                data[22]=bal;
                         
                        
                        
                            cut+=prodqty;
                            aso+=sobar;
                            apad+=pp;
                            first_tot+=first;
                            tplan+=planqty;
                            if(planqty>0)
                                status="ready to cut";
                            else{
                            if(cutting>0)
                                status="cutting";
                            else{
                                if(prodqty>0)
                                    status="cut";
                                else{
                                    if(sobar>0)
                                        status="At Soabar";
                                    else{
                                        if(pp>0)
                                            status="At pad print/Heat transfer";
                                        else{
                                            if(se>0)
                                                status="Recieved";
                                            else
                                                if(mod+first+pack>0)
                                                    status="WIP-At mod";
                                                else{
                                                    
                                                }
                                        }
                                    }
                                }
                            }
                            }
                            
                        
               data[9]=status;
                data[23]=sku;
                data[24]=status;
                data[0]=rs.getDate("shipdate");
                listeData.add(data);
               tot+=rs.getInt("qty");;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Work_status.class.getName()).log(Level.SEVERE, null, ex);
        }
        items.setText(String.valueOf(listeData.size()));
        totorder.setText(String.valueOf(tot));
        totcut.setText(String.valueOf(cut));
           totsobar.setText(String.valueOf(aso));
           totpad.setText(String.valueOf(apad));
           totpl.setText(String.valueOf(tplan));
           totfirst.setText(String.valueOf(first_tot));
    }
    private void listData(){
        
        DefaultTableModel tbm = (DefaultTableModel) grid_data.getModel();
        tbm.setRowCount(0);
        for(Object[] data:listeData){
                tbm.addRow(data);
                //grid_data.setValueAt(data[0], i, 0);
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
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        items = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        totorder = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        totpl = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        totcut = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        totsobar = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        totpad = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        totfirst = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        grid_data = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();

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

        jLabel5.setText("CLIENT FILTER");

        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
        });

        jLabel6.setText("Line Items:");

        items.setText("jLabel7");

        jLabel7.setText("Total Order:");

        totorder.setText("jLabel8");

        jLabel8.setText("Total Plan:");

        totpl.setText("totalplan");

        jLabel10.setText("Total Cut:");

        totcut.setText("jLabel11");

        jLabel9.setText("total At sobar:");

        totsobar.setText("jLabel11");

        jLabel11.setText("Total Padprint:");

        totpad.setText("jLabel12");

        jLabel12.setText("Total First:");

        totfirst.setText("jLabel13");

        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField6KeyReleased(evt);
            }
        });

        jLabel13.setText("SKU Filter");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
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
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(84, 84, 84)
                                .addComponent(jLabel5)
                                .addGap(119, 119, 119)
                                .addComponent(jLabel13)
                                .addGap(222, 222, 222)
                                .addComponent(jButton1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(items, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totorder, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totpl, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totcut, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(totsobar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(totpad, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(totfirst, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel13))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(items)
                    .addComponent(jLabel7)
                    .addComponent(totorder)
                    .addComponent(jLabel8)
                    .addComponent(totpl)
                    .addComponent(jLabel10)
                    .addComponent(totcut)
                    .addComponent(jLabel9)
                    .addComponent(totsobar)
                    .addComponent(jLabel11)
                    .addComponent(totpad)
                    .addComponent(jLabel12)
                    .addComponent(totfirst))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        grid_data.setAutoCreateRowSorter(true);
        grid_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "X_FACTORY", "CUSTOMER", "PO NUM", "STYLE", "CODE COLOR", "COLOR", "SIZE", "SKU", "QTY", "STATUS", "WORK ORDER", "READY TO CUT", "CUTTING", "CUT", "AT SOBAR", "PAD PRINT", "AT SEWING", "SEW START", "FIRST", "SECOND", "PACKING", "SHIPPED", "BALANCE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
        );

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/icon/export.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton2)))
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

    private void jTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyReleased
        // TODO add your handling code here:
        buscar(); 
    }//GEN-LAST:event_jTextField6KeyReleased

    private void buscar(){
        int tot=0,line=0;
        int cut=0,aso=0,apad=0,first_tot=0,tplan=0,i=0;
        String potxt=jTextField1.getText().trim().toLowerCase();
        String style=jTextField2.getText().trim().toLowerCase();
        String col=jTextField3.getText().trim().toLowerCase();
        String size=jTextField4.getText().trim().toLowerCase();
        String client=jTextField5.getText().trim().toLowerCase();
         String sku=jTextField6.getText().trim().toLowerCase();
        
        String data="";
        
        DefaultTableModel tbm = (DefaultTableModel) grid_data.getModel();
        tbm.setRowCount(0);
        for(Object[] o:listeData){
            if(o[2].toString().toLowerCase().contains(potxt.trim().toLowerCase())&& o[3].toString().contains(style.trim().toLowerCase())&&
                    (o[5].toString().toLowerCase().contains(col.trim())||o[4].toString().toLowerCase().contains(col.trim()))&&
                    o[6].toString().toLowerCase().contains(size.trim())&&o[1].toString().toLowerCase().contains(client)&&o[7].toString().toLowerCase().contains(sku)
               ){
                tot+=Integer.parseInt(o[8].toString());
                tplan+=Integer.parseInt(o[11].toString());
                cut+=Integer.parseInt(o[13].toString());
                aso+=Integer.parseInt(o[14].toString());
                apad+=Integer.parseInt(o[15].toString());
                first_tot+=Integer.parseInt(o[18].toString());
                ++i;
                tbm.addRow(o);
            }
        }
        items.setText(String.valueOf(listeData.size()));
        totorder.setText(String.valueOf(tot));
        totcut.setText(String.valueOf(cut));
           totsobar.setText(String.valueOf(aso));
           totpad.setText(String.valueOf(apad));
           totpl.setText(String.valueOf(tplan));
           totfirst.setText(String.valueOf(first_tot));
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
    
    private Map<String,Integer> type(){
        Map<String,Integer> typ=new HashMap<>();
        String requete="select ordnum_10,count(type_id) n from cut1 where type_id NOT IN(4,6,7) group by ordnum_10";
        ResultSet rs=conn.select(requete);
        try {
            while(rs.next()){
                typ.put(rs.getString("ordnum_10").trim(),rs.getInt("n"));
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
                       production.put(rs.getString("ORDNUM_10").trim(), rs.getInt("qty"));
                       ref.put(rs.getString("ORDNUM_10").trim(),rs.getString("style"));
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
               if(order.contains("30015")){
               System.out.println("type2:"+typecount.get(order));
               System.out.println("type:"+typ);
               }
               if(ty==typ)
               prod.put(order, value);
           }
    }
    
    private boolean changeStatus(String ordnum){
        String requete="update shoporder set status_147='5' where ordnum_147=?";
        return conn.Update(requete, 0, ordnum);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem DETAILS;
    private javax.swing.JPopupMenu POPUP;
    private javax.swing.JTable grid_data;
    private javax.swing.JLabel items;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JLabel totcut;
    private javax.swing.JLabel totfirst;
    private javax.swing.JLabel totorder;
    private javax.swing.JLabel totpad;
    private javax.swing.JLabel totpl;
    private javax.swing.JLabel totsobar;
    // End of variables declaration//GEN-END:variables
}
