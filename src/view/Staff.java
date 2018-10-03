/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import connection.ConnectionDb;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Duvers
 */
public class Staff extends javax.swing.JDialog {

      private final ConnectionDb conn = ConnectionDb.instance();
      private Set<Object[]>list=new HashSet<>();
      
    public Staff(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        txt_search.setText(null);
        loadStaff();
    }

    
    
    private void loadStaff(){
           
           DefaultTableModel tbm = (DefaultTableModel) grid_staff.getModel();
       tbm.setRowCount(0);
       
      try{
        
          String query = "SELECT * from staff ORDER BY fname ASC";
      
          ResultSet rs = conn.select(query);
          
           while (rs.next()){
              
  tbm.addRow(new Object[]{rs.getInt("id_staff"), rs.getString("fname"), rs.getString("lname")});
  list.add(new Object[]{rs.getInt("id_staff"), rs.getString("fname"), rs.getString("lname")});
           }   
  
    }catch (SQLException e){
        System.out.println(e); 
        
        } 
    }
    
    
       public void buscarDatos(String txtbox){

    DefaultTableModel tbm = (DefaultTableModel) grid_staff.getModel();
        
        tbm.setRowCount(0);
        String buscar = txtbox.trim();
        for(Object[] o:list){
            if(o[1].toString().toLowerCase().startsWith(txtbox.toLowerCase().trim())||o[2].toString().toLowerCase().startsWith(txtbox.toLowerCase().trim()))
    tbm.addRow(o);
           }
       }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        grid_staff = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txt_search = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Staff Search");

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        grid_staff.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "FIRST NAME", "LAST NAME"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grid_staff.getTableHeader().setReorderingAllowed(false);
        grid_staff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grid_staffMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(grid_staff);
        if (grid_staff.getColumnModel().getColumnCount() > 0) {
            grid_staff.getColumnModel().getColumn(0).setMinWidth(0);
            grid_staff.getColumnModel().getColumn(0).setMaxWidth(0);
            grid_staff.getColumnModel().getColumn(1).setMinWidth(150);
            grid_staff.getColumnModel().getColumn(1).setMaxWidth(200);
        }

        txt_search.setText("jTextField1");
        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void grid_staffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grid_staffMouseClicked
       
        
         if (evt.getClickCount() == 2){

            int selectedrow = grid_staff.getSelectedRow();
            //     maincode = (grid_staff.getModel().getValueAt(selectedrow, 1).toString());
          //  AllCutPlan.id_marker = ((Integer)grid_markerName.getModel().getValueAt(selectedrow, 0));
            User.id_staff = (int)(grid_staff.getModel().getValueAt(selectedrow, 0));
            User.staff = (grid_staff.getModel().getValueAt(selectedrow, 1).toString())+" "+
                    (grid_staff.getModel().getValueAt(selectedrow, 2).toString());

            User.btn_getinfo.doClick();

            this.dispose(); 
        }
    }//GEN-LAST:event_grid_staffMouseClicked

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased
        
        buscarDatos(txt_search.getText().trim());
    }//GEN-LAST:event_txt_searchKeyReleased

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
            java.util.logging.Logger.getLogger(Staff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Staff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Staff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Staff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Staff dialog = new Staff(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable grid_staff;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField txt_search;
    // End of variables declaration//GEN-END:variables
}
