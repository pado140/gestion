/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import connection.ConnectionDb;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import observateurs.Observateurs;
import observateurs.Observe;

/**
 *
 * @author Padovano
 */
public class Ready_to_sew1 extends javax.swing.JInternalFrame implements Observe{

    private ConnectionDb conn = ConnectionDb.instance();
    private Map<String , Integer> prod=new HashMap<>();
    private Map<String, String> ref=null;
    private Set<Object[]> liste=null;
    /**
     * Creates new form Ready_to_sew
     */
    public Ready_to_sew1() {
        initComponents();
        liste=new HashSet<>();
    listData();
    observa=new Confirm_Receiver(null,false);
    this.ajouterObservateur(observa);
    }

    private Map<String,Set<String>> listType(){
        String query="select Distinct styfam,[TYPE] FROM StyleMaster order by styfam";
        ResultSet rs=conn.select(query);
        Map<String,Set<String>> lissty=new HashMap<>();
        try {
            rs.first();
            if(rs.getRow()>0){
            String sty=rs.getString("styfam");
            Set<String> typ=new HashSet<>();
            rs.beforeFirst();
        while(rs.next()){
                  
                   if(!rs.getString("styfam").equals(sty)){
                       lissty.put(sty, typ);
                       typ=new HashSet<>();
                       sty=rs.getString("styfam");
                       
                   }
                   typ.add(rs.getString("type"));
                   
                   if(rs.isLast()){
                       lissty.put(sty, typ);
                   }
        }
            }
        } catch (SQLException ex) {
               //Logger.getLogger(Cansew.class.getName()).log(Level.SEVERE, null, ex);
           }
        return lissty;
    }
    private void data_cut(){
                 Map<String,Set<String>> listType=listType();
                String firstref="";
               Map<String,Integer> productionData=new HashMap<>();
               String type[]=new String[]{"MAIN","CONTRAST","INSERT","POCKETING"};
               String qt_main = "--", qt_contrast = "--",  qt_insert = "--", qt_pocketing = "--";
               boolean check = false;
               Map<String,Integer> production=new HashMap<>();
               Map<String,Integer> productionInsert=new HashMap<>();
               Map<String,Integer> productionContrast=new HashMap<>();
               Map<String,Integer> productionFusing=new HashMap<>();
               Map<String,Integer> productionInner=new HashMap<>();
               Map<String,Integer> productionOutter=new HashMap<>();
               Map<String,Integer> productionInnerC=new HashMap<>();
               Map<String,Integer> productionOutterC=new HashMap<>();
               Map<String,Integer> productionInnerI=new HashMap<>();
               Map<String,Integer> productionOutterI=new HashMap<>();
               ref=new HashMap<>();
               String query = "SELECT * from SUMMARY_CUTted";
                try {
               ResultSet rs = conn.select(query);
               
               while (rs.next()){
                   if(rs.getString("type").toLowerCase().equals("main")){
                       production.put(rs.getString("PO")+"."+rs.getString("PART_NUM"), rs.getInt("PIECES"));
                       ref.put(rs.getString("PO")+"."+rs.getString("PART_NUM"),rs.getString("reference"));
                   }
                   if(rs.getString("type").toLowerCase().equals("inner main")){
                       productionInner.put(rs.getString("PO")+"."+rs.getString("PART_NUM"), rs.getInt("PIECES"));
                       ref.put(rs.getString("PO")+"."+rs.getString("PART_NUM"),rs.getString("reference"));
                   }
                   if(rs.getString("type").toLowerCase().equals("outter main")){
                       productionOutter.put(rs.getString("PO")+"."+rs.getString("PART_NUM"), rs.getInt("PIECES"));
                       ref.put(rs.getString("PO")+"."+rs.getString("PART_NUM"),rs.getString("reference"));
                   }
                   if(rs.getString("type").toLowerCase().equals("inner contrast")){
                       productionInnerC.put(rs.getString("PO")+"."+rs.getString("PART_NUM"), rs.getInt("PIECES"));
                       ref.put(rs.getString("PO")+"."+rs.getString("PART_NUM"),rs.getString("reference"));
                   }
                   if(rs.getString("type").toLowerCase().equals("outter contrast")){
                       productionOutterC.put(rs.getString("PO")+"."+rs.getString("PART_NUM"), rs.getInt("PIECES"));
                       ref.put(rs.getString("PO")+"."+rs.getString("PART_NUM"),rs.getString("reference"));
                   }
                   if(rs.getString("type").toLowerCase().equals("inner insert")){
                       productionInnerI.put(rs.getString("PO")+"."+rs.getString("PART_NUM"), rs.getInt("PIECES"));
                       ref.put(rs.getString("PO")+"."+rs.getString("PART_NUM"),rs.getString("reference"));
                   }
                   if(rs.getString("type").toLowerCase().equals("outter insert")){
                       productionOutterI.put(rs.getString("PO")+"."+rs.getString("PART_NUM"), rs.getInt("PIECES"));
                       ref.put(rs.getString("PO")+"."+rs.getString("PART_NUM"),rs.getString("reference"));
                   }
                   if(rs.getString("type").toLowerCase().equals("insert")){
                       productionInsert.put(rs.getString("PO")+"."+rs.getString("PART_NUM"), rs.getInt("PIECES"));
                       ref.put(rs.getString("PO")+"."+rs.getString("PART_NUM"),rs.getString("reference"));
                   }
                   if(rs.getString("type").toLowerCase().equals("contrast")){
                       productionContrast.put(rs.getString("PO")+"."+rs.getString("PART_NUM"),rs.getInt("PIECES"));
                       ref.put(rs.getString("PO")+"."+rs.getString("PART_NUM"),rs.getString("reference"));
                   }
                   if(rs.getString("type").toLowerCase().equals("fusing")){
                       productionFusing.put(rs.getString("po"), rs.getInt("PIECES"));
                   } 
                   
               }
           } catch (SQLException ex) {
               Logger.getLogger(Bundle.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           for(String keyrevers:productionInner.keySet()){
               int val=productionInner.get(keyrevers);
               int value=val;
               try{
                   value=productionOutter.get(keyrevers);
                   }catch(NullPointerException e){
                       value=0;
                   }
               if(value<val)
                   val=value;
               production.put(keyrevers, val);
           }
           for(String keyProd:production.keySet()){
               String reference=ref.get(keyProd);
               String styFam=reference.split("-")[1];
               if(styFam.equals("RECUT")|| styFam.equals("1") || reference.startsWith("N")){
                   styFam=reference.split("-")[2];
               }
               Set<String> lis=listType.get(styFam);
               int val=production.get(keyProd);
               int value=val;
               for(String ty:lis){
                   
                   try{
                   if(ty.toLowerCase().equals("insert"))
                       value=productionInsert.get(keyProd);
                   if(ty.toLowerCase().equals("contrast"))
                       value=productionContrast.get(keyProd);
                   }catch(NullPointerException e){
                       value=0;
                   }
                   if(value<val)
                       val=value;
               }
               prod.put(keyProd, val);
           }
    }
    private void listData(){
        liste=new HashSet<>();
        data_cut();
        DefaultTableModel tbm = (DefaultTableModel) GRID_DATA.getModel();
        tbm.setRowCount(0);
        String requete="select * from orderplan WHERE STATUS_147='3'";
        ResultSet rs = conn.select(requete);
        try {
            while(rs.next()){
                String sku=rs.getString("sku").trim();
               
                String size=sku.replace('.','-').split("-")[2];
                String desc=rs.getString("description").trim(),
                        po=rs.getString("po").trim();
                if(desc.toLowerCase().contains("yth")||desc.toLowerCase().contains("youth")||
                        desc.toLowerCase().contains("girls")||desc.toLowerCase().contains("boys"))
                size="Y"+size;
                String status="ready to cut";
                try{
                    
                    if(prod.get(rs.getString("po").trim()+"."+sku.trim())>=0){
                        if(prod.get(rs.getString("po").trim()+"."+sku.trim())>=rs.getInt("pieces")){
                            tbm.addRow(new Object[]{po,rs.getString("style").trim(),rs.getString("PROTO").trim(),desc.trim(),rs.getString("color_code").trim(),rs.getString("color").trim(),
                    size.trim(),prod.get(rs.getString("po").trim()+"."+sku.trim()),rs.getString("ORDNUM_147")});
                          liste.add(new Object[]{po,rs.getString("style").trim(),rs.getString("PROTO").trim(),desc.trim(),rs.getString("color_code").trim(),rs.getString("color").trim(),
                    size.trim(),prod.get(rs.getString("po").trim()+"."+sku.trim()),rs.getString("ORDNUM_147")});  
                        }
                    }
                    
                }catch(NullPointerException e){
                    
                }
                
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Work_status.class.getName()).log(Level.SEVERE, null, ex);
        }
               
        
    }
    
    private void buscados(){
        String po=po_filter.getText().trim();
        String style=style_filter.getText().trim();
        String colorC=color_code_filter.getText().trim();
        String color=color_filter.getText().trim();
        String size=size_filter.getText().trim();
        DefaultTableModel tbm = (DefaultTableModel) GRID_DATA.getModel();
        tbm.setRowCount(0);
        for(Object[] o:liste){
            if(o[0].toString().toLowerCase().contains(po.toLowerCase()) && o[1].toString().toLowerCase().contains(style.toLowerCase())
                    && o[3].toString().toLowerCase().contains(colorC.toLowerCase())
                    && o[4].toString().toLowerCase().contains(color.toLowerCase()) && o[5].toString().toLowerCase().contains(size.toLowerCase())){
                tbm.addRow(o);
            }
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
        transfer = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        po_filter = new javax.swing.JTextField();
        style_filter = new javax.swing.JTextField();
        color_code_filter = new javax.swing.JTextField();
        color_filter = new javax.swing.JTextField();
        size_filter = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        GRID_DATA = new javax.swing.JTable();

        transfer.setText("Transfer to sew");
        transfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferActionPerformed(evt);
            }
        });
        POPUP.add(transfer);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ready to sew");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameDeactivated(evt);
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        po_filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                po_filterKeyReleased(evt);
            }
        });

        style_filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                style_filterKeyReleased(evt);
            }
        });

        color_code_filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                color_code_filterKeyReleased(evt);
            }
        });

        color_filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                color_filterKeyReleased(evt);
            }
        });

        size_filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                size_filterKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(po_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(style_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(155, 155, 155)
                .addComponent(color_code_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(color_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(size_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(po_filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(style_filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(color_code_filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(color_filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(size_filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        GRID_DATA.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "PO", "STYLE", "PROTO", "DESCRIPTION", "COLOR_CODE", "COLOR", "SIZE", "QUANTITY", "SEWING TRAVELER"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        GRID_DATA.setToolTipText("");
        GRID_DATA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                GRID_DATAMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(GRID_DATA);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1072, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GRID_DATAMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GRID_DATAMouseReleased
        // TODO add your handling code here:
        if(evt.getButton()==MouseEvent.BUTTON3){
            if(!GRID_DATA.getSelectionModel().isSelectionEmpty())
            POPUP.show(GRID_DATA,evt.getX(),evt.getY());
            else
                JOptionPane.showMessageDialog(this, "Please select a row!", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_GRID_DATAMouseReleased

    private void transferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transferActionPerformed
        // TODO add your handling code here:
        Object[][] data=new Object[GRID_DATA.getSelectedRowCount()][9];
        
        
        for(int i=0;i<GRID_DATA.getSelectedRowCount();i++){
            int ligne=GRID_DATA.getSelectedRows()[i];
        data[i][0]=GRID_DATA.getValueAt(ligne, 0);
        data[i][1]=GRID_DATA.getValueAt(ligne, 1);
        data[i][2]=GRID_DATA.getValueAt(ligne, 2);
        data[i][3]=GRID_DATA.getValueAt(ligne, 3);
        data[i][4]=GRID_DATA.getValueAt(ligne, 4);
        data[i][5]=GRID_DATA.getValueAt(ligne, 5);
        data[i][6]=GRID_DATA.getValueAt(ligne, 6);
        data[i][7]=GRID_DATA.getValueAt(ligne, 7);
        data[i][8]=GRID_DATA.getValueAt(ligne, 8);
        }
        alerter("Transfer to sew1",data);
        ((JDialog)observa).setModal(true);
        ((JDialog)observa).setVisible(true);
        listData();
    }//GEN-LAST:event_transferActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        // TODO add your handling code here:
        //listData();
    }//GEN-LAST:event_formInternalFrameActivated

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        // TODO add your handling code here:
        //listData();
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void po_filterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_po_filterKeyReleased
        // TODO add your handling code here:
        buscados();
    }//GEN-LAST:event_po_filterKeyReleased

    private void style_filterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_style_filterKeyReleased
        // TODO add your handling code here:
        buscados();
    }//GEN-LAST:event_style_filterKeyReleased

    private void color_code_filterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_color_code_filterKeyReleased
        // TODO add your handling code here:
        buscados();
    }//GEN-LAST:event_color_code_filterKeyReleased

    private void color_filterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_color_filterKeyReleased
        // TODO add your handling code here:
        buscados();
    }//GEN-LAST:event_color_filterKeyReleased

    private void size_filterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_size_filterKeyReleased
        // TODO add your handling code here:
        buscados();
    }//GEN-LAST:event_size_filterKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable GRID_DATA;
    private javax.swing.JPopupMenu POPUP;
    private javax.swing.JTextField color_code_filter;
    private javax.swing.JTextField color_filter;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField po_filter;
    private javax.swing.JTextField size_filter;
    private javax.swing.JTextField style_filter;
    private javax.swing.JMenuItem transfer;
    // End of variables declaration//GEN-END:variables
    private Observateurs observa;
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
}
