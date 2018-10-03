/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import connection.ConnectionDb;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import observateurs.Observateurs;
import observateurs.Observe;

/**
 *
 * @author Duvers
 */
public class Fab_list extends javax.swing.JDialog implements Observateurs,Observe{

    
    private final ConnectionDb conn = ConnectionDb.instance();
    Map<String,Integer> listfab=new HashMap<>();
    private String label;
    //private 
    
    
    public Fab_list(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        txt_po.setText(null);
    }

    
    public void mostrarDatos(){
    
        DefaultTableModel tbm = (DefaultTableModel) grid_po.getModel();
       tbm.setRowCount(0);
       listfab=new HashMap<>();
      try{
        
          String query = "SELECT DISTINCT fabnm,fab_id from fabric";
          
              
        //Statement sta = conn.createStatement();
          ResultSet rs = conn.select(query);
          
           while (rs.next()){
             listfab.put(rs.getString("fabnm"), rs.getInt("fab_id"));
  tbm.addRow(new Object[]{ rs.getString("fabnm")});
           }   
    
    }catch (SQLException e){
        System.out.println(e); 
        
        }
    }
    
    
     public void buscarPo(String txtbox){
         listfab=new HashMap<>();
    DefaultTableModel tbm = (DefaultTableModel) grid_po.getModel();
        
        tbm.setRowCount(0);
        String buscar = txtbox.trim();
        
        try{
            
        String sql =  "SELECT DISTINCT fabnm,fab_id from fabric where fabnm like ?";  //like '%"+buscar+"%'";
              
         
     
             ResultSet rs = conn.select(sql, "%" + buscar + "%");
  
     while (rs.next()){
              listfab.put(rs.getString("fabnm"), rs.getInt("fab_id"));
    tbm.addRow(new Object[]{rs.getString("fabnm")});
           }
        
        }catch (SQLException e){
        System.out.println(e);
        }
       }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        grid_po = new javax.swing.JTable();
        txt_po = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PO List");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        grid_po.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PO NUMBER"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grid_po.getTableHeader().setReorderingAllowed(false);
        grid_po.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grid_poMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(grid_po);

        txt_po.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_po.setText("jTextField1");
        txt_po.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_poMouseClicked(evt);
            }
        });
        txt_po.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_poKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Search PO");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_po, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(txt_po, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void grid_poMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grid_poMouseClicked

        if (evt.getClickCount() == 2){
            Object[] data=new Object[4];
            data[3]=label;
            

            int selectedrow = grid_po.getSelectedRow();
            //     maincode = (grid_staff.getModel().getValueAt(selectedrow, 1).toString());
            
            /*            Marker_info.po_chosen =  (grid_po.getModel().getValueAt(selectedrow, 0).toString());
            Marker_info.BRAND=listfab.get(grid_po.getModel().getValueAt(selectedrow, 0).toString());
            Marker_info.txt_cutplan.setText(grid_po.getModel().getValueAt(selectedrow, 0).toString());
            Marker_info.btn_po.doClick();
            
            System.err.println("po :"+Marker_info.po_chosen);
            System.err.println("po2 :"+Marker_info.po_chosen);*/
            data[1]=grid_po.getModel().getValueAt(selectedrow, 0);
            data[2]=listfab.get(grid_po.getModel().getValueAt(selectedrow, 0).toString());
            data[0]="fill";
            alerter(data);
            this.setVisible(false);
        }
    }//GEN-LAST:event_grid_poMouseClicked

    private void txt_poMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_poMouseClicked

        
    }//GEN-LAST:event_txt_poMouseClicked

    private void txt_poKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_poKeyReleased
 
        buscarPo(txt_po.getText());
    }//GEN-LAST:event_txt_poKeyReleased

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked

    }//GEN-LAST:event_jLabel3MouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
        mostrarDatos();
    }//GEN-LAST:event_formWindowOpened

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
            java.util.logging.Logger.getLogger(Fab_list.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fab_list.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fab_list.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fab_list.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Fab_list dialog = new Fab_list(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable grid_po;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_po;
    // End of variables declaration//GEN-END:variables

    @Override
    public void executer(Object... obs) {
        
        if(obs[0].toString().equals("load fabric")){
            label=obs[1].toString();
        }
    }

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
