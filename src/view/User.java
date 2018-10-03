/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import connection.ConnectionDb;
import connection.item;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import observateurs.Observateurs;
import observateurs.Observe;

/**
 *
 * @author Duvers
 */
public class User extends javax.swing.JInternalFrame implements Observe{

   private final ConnectionDb conn = ConnectionDb.instance();
   
   private User_rights droit=new User_rights(null,true);
    
    public static Integer id_staff = 0;
    public static String staff = null;
    Integer coddept = 0;
    private boolean cansave = true;
    //private List<Object[]> 
    
    public User() {
        initComponents();
        this.ajouterObservateur(droit);
        refresh();
        btn_getinfo.setVisible(false);
        
        showData();
        combo_dept(cmb_dept);
        
    }

    
    private void refresh(){
        
        txt_search.setText(null);
        txt_pass1.setText(null);
        txt_pass2.setText(null);
        txt_staff.setText(null);
        txt_usu.setText(null);
        right.setText("User Right");
        
        coddept = 0;
    }
    
    
    public void combo_dept(JComboBox cmb){
    
    DefaultComboBoxModel value;
    
    try{
    ResultSet rs = conn.select("select * from departement ;");
    value = new DefaultComboBoxModel();
    
    cmb.setModel(value);
    cmb.removeAllItems();
    value.addElement(new item(0, "--"));
    while (rs.next()){
    value.addElement(new item(rs.getInt("id_dept"), rs.getString("dept")));
    }
    
    }catch (Exception ex){
    System.err.println(""+ ex);
    }
    }
    
    private void saveUser(){
      
        String query = "INSERT INTO users (idstaff, username, pass, dept, nivel, status) "
                      + "VALUES (?,?,?,?,?,?)";
        
        boolean wellsave = conn.Update(query, 1, id_staff,txt_usu.getText().trim(),txt_pass2.getText().trim(),coddept,
                cmb_nivel.getSelectedItem().toString(),cmb_statut.getSelectedItem().toString());
        
        if(wellsave){      
             JOptionPane.showMessageDialog(null, "Successfully Saved");  
             
        }else{
            JOptionPane.showMessageDialog(null, "ERROR");
        }
  
    }
    
    
    private void showData(){
        
        grid_usuario.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 13 ));
        DefaultTableModel tbm = (DefaultTableModel) grid_usuario.getModel();
       tbm.setRowCount(0);
       
      try{
        
          String query = "SELECT * from vw_users order by iduser DESC";
         
          ResultSet rs = conn.select(query);
          
           while (rs.next()){
              
  tbm.addRow(new Object[]{rs.getInt("iduser"), rs.getString("fname")+" "+rs.getString("lname"), rs.getString("username"), rs.getString("departement"),
                 rs.getInt("nivel"), rs.getString("status"),rs.getString("pass")});
           }   
    
    }catch (SQLException e){
        System.out.println(e); 
        
        }
    }
    
    
    
        public void buscarDatos(String txtbox){

    DefaultTableModel tbm = (DefaultTableModel) grid_usuario.getModel();
        
        tbm.setRowCount(0);
        String buscar = txtbox.trim();
        
        try{
            
        String sql =  "select * from vw_users where fname like ? or  lname like ? "+
                      " or fname+' '+lname like ? or lname+' '+fname like ? or username like ?";
   
     
             ResultSet rs = conn.select(sql,"%" + buscar + "%","%" + buscar + "%","%" + buscar + "%","%" + buscar + "%","%" + buscar + "%");
  
     while (rs.next()){
              
     tbm.addRow(new Object[]{rs.getInt("iduser"), rs.getString("fname")+" "+rs.getString("lname"), rs.getString("username"), rs.getString("departement"),
                 rs.getInt("nivel"), rs.getString("status"),rs.getString("pass")});
           }
        
        }catch (SQLException e){
        System.out.println(e);
        }
       }

    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pop = new javax.swing.JPopupMenu();
        right = new javax.swing.JMenuItem();
        change_password = new javax.swing.JMenuItem();
        deactivate = new javax.swing.JMenuItem();
        panel_station = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txt_staff = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_usu = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cmb_statut = new javax.swing.JComboBox();
        txt_pass1 = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_pass2 = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        cmb_dept = new javax.swing.JComboBox();
        cmb_nivel = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        btn_nouveau = new javax.swing.JButton();
        btn_guardar = new javax.swing.JButton();
        btn_getinfo = new javax.swing.JButton();
        txt_search = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        grid_usuario = new javax.swing.JTable();

        right.setText("jMenuItem1");
        right.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightActionPerformed(evt);
            }
        });
        pop.add(right);

        change_password.setText("jMenuItem1");
        pop.add(change_password);

        deactivate.setText("jMenuItem2");
        deactivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deactivateActionPerformed(evt);
            }
        });
        pop.add(deactivate);

        setClosable(true);
        setTitle("USER");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
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

        panel_station.setBackground(new java.awt.Color(153, 153, 153));
        panel_station.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setPreferredSize(new java.awt.Dimension(215, 34));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 595, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        txt_staff.setEditable(false);
        txt_staff.setBackground(new java.awt.Color(51, 51, 51));
        txt_staff.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_staff.setForeground(new java.awt.Color(255, 255, 255));
        txt_staff.setText("jTextField1");
        txt_staff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_staffMouseClicked(evt);
            }
        });
        txt_staff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_staffActionPerformed(evt);
            }
        });
        txt_staff.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_staffKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel3.setText("Staff :");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel5.setText("Username :");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        txt_usu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_usu.setText("jTextField1");
        txt_usu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usuActionPerformed(evt);
            }
        });
        txt_usu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_usuKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel7.setText("Statut :");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        cmb_statut.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmb_statut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Actif", "Inactif" }));

        txt_pass1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_pass1.setText("jPasswordField1");
        txt_pass1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_pass1FocusLost(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel8.setText("Password:");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel9.setText("Confirm Password:  ");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        txt_pass2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_pass2.setText("jPasswordField1");
        txt_pass2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_pass2FocusLost(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel6.setText("Department/Nivel:");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        cmb_dept.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmb_dept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_deptActionPerformed(evt);
            }
        });

        cmb_nivel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmb_nivel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--", "5", "4", "3", "2", "1" }));

        javax.swing.GroupLayout panel_stationLayout = new javax.swing.GroupLayout(panel_station);
        panel_station.setLayout(panel_stationLayout);
        panel_stationLayout.setHorizontalGroup(
            panel_stationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
            .addGroup(panel_stationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_stationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(panel_stationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_staff, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_stationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txt_pass1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                        .addComponent(txt_usu, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(panel_stationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txt_pass2, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panel_stationLayout.createSequentialGroup()
                            .addComponent(cmb_dept, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cmb_nivel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cmb_statut, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        panel_stationLayout.setVerticalGroup(
            panel_stationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_stationLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_stationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_staff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(panel_stationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_usu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(panel_stationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_pass1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(panel_stationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_pass2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_stationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_dept, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(cmb_nivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_stationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_statut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_nouveau.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btn_nouveau.setText("NEW");
        btn_nouveau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nouveauActionPerformed(evt);
            }
        });

        btn_guardar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btn_guardar.setText("SAVE");
        btn_guardar.setRequestFocusEnabled(false);
        btn_guardar.setRolloverEnabled(false);
        btn_guardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_guardarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_guardarMouseEntered(evt);
            }
        });
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });

        btn_getinfo.setText("jButton1");
        btn_getinfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_getinfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btn_getinfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_guardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_nouveau)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_guardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_nouveau, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_getinfo)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        txt_search.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_search.setText("jTextField1");
        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchKeyReleased(evt);
            }
        });

        grid_usuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "STAFF", "USERNAME", "DEPARTMENT", "NIVEL", "STATUT", "password"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grid_usuario.getTableHeader().setReorderingAllowed(false);
        grid_usuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                grid_usuarioMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(grid_usuario);
        if (grid_usuario.getColumnModel().getColumnCount() > 0) {
            grid_usuario.getColumnModel().getColumn(0).setMinWidth(0);
            grid_usuario.getColumnModel().getColumn(0).setMaxWidth(0);
            grid_usuario.getColumnModel().getColumn(6).setMinWidth(0);
            grid_usuario.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_station, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel_station, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_staffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_staffMouseClicked

        if(evt.getClickCount() == 2 ){
            
        Staff dialog = new Staff(new javax.swing.JFrame(), true);        
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        }
    }//GEN-LAST:event_txt_staffMouseClicked

    private void txt_staffKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_staffKeyReleased

    }//GEN-LAST:event_txt_staffKeyReleased

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked

    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel5MouseClicked

    private void txt_usuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usuActionPerformed

    private void txt_usuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_usuKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usuKeyReleased

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel7MouseClicked

    private void btn_nouveauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nouveauActionPerformed

       refresh();
    }//GEN-LAST:event_btn_nouveauActionPerformed

    private void btn_guardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_guardarMouseClicked

    }//GEN-LAST:event_btn_guardarMouseClicked

    private void btn_guardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_guardarMouseEntered

    }//GEN-LAST:event_btn_guardarMouseEntered

    
    private void check(){
        
         if(!txt_pass1.getText().toString().trim().equals(txt_pass2.getText().toString().trim())){
       cansave = false;
         }else{
        cansave = true;      
         }
    }
    
    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed

        check();
        String usu = txt_usu.getText().trim(); 
        coddept=cmb_dept.getSelectedIndex();
        if(!txt_staff.getText().trim().equals("")){  
                if(!txt_usu.getText().trim().equals("")){
                     if(!txt_pass1.getText().trim().equals("") && !txt_pass2.getText().trim().equals("")){
                    if(!cmb_nivel.getSelectedItem().equals("--") && coddept>0){
                        if(!cmb_statut.getSelectedItem().equals("--")){

                                if(!check(id_staff)){

                                    if(cansave){
                                        
                                        saveUser();
                                     showData();
                                    btn_nouveau.doClick();   
                                    
                                    
                                    }else{
          JOptionPane.showMessageDialog (null, "The Passwords don't match !", "Error", JOptionPane.WARNING_MESSAGE);                                  
                                    }
                                  

                                }else{
       JOptionPane.showMessageDialog (null, "This User has been already created ", "Error", JOptionPane.INFORMATION_MESSAGE);                          
                                }

                        }else{
         JOptionPane.showMessageDialog (null, "Please fulfill all fields", "Error", JOptionPane.INFORMATION_MESSAGE);
         }

       }else{
         JOptionPane.showMessageDialog (null, "Please fulfill all fields", "Error", JOptionPane.INFORMATION_MESSAGE);
         }
      }else{
         JOptionPane.showMessageDialog (null, "Please fulfill all fields", "Error", JOptionPane.INFORMATION_MESSAGE);
         }
     }else{
         JOptionPane.showMessageDialog (null, "Please fulfill all fields", "Error", JOptionPane.INFORMATION_MESSAGE);
         }
  }else{
         JOptionPane.showMessageDialog (null, "Please fulfill all fields", "Error", JOptionPane.INFORMATION_MESSAGE);
         }

    }//GEN-LAST:event_btn_guardarActionPerformed

    private boolean check(int id){
        boolean ch=false;
        String requete="select idstaff from users where idstaff=?";
        ResultSet rs=conn.select(requete, id);
       try {
           while(rs.next()){
               ch=true;
           }
       } catch (SQLException ex) {
           Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
       }
        return ch;
    }
    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        
        //Principal_iw.allow_user = true;
    }//GEN-LAST:event_formInternalFrameClosed

    private void txt_staffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_staffActionPerformed
        
    
    }//GEN-LAST:event_txt_staffActionPerformed

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel9MouseClicked

    private void btn_getinfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_getinfoActionPerformed
       
        txt_staff.setText(staff);
    }//GEN-LAST:event_btn_getinfoActionPerformed

    private void txt_pass2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_pass2FocusLost
     

    }//GEN-LAST:event_txt_pass2FocusLost

    private void txt_pass1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_pass1FocusLost
        

    }//GEN-LAST:event_txt_pass1FocusLost

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased
       
        buscarDatos(txt_search.getText().trim());
    }//GEN-LAST:event_txt_searchKeyReleased

    private void cmb_deptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_deptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_deptActionPerformed

    private void rightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightActionPerformed
        // TODO add your handling code here:
        int lin=grid_usuario.getSelectedRow();
        alerter("rights",grid_usuario.getValueAt(lin, 0),grid_usuario.getValueAt(lin, 2),grid_usuario.getValueAt(lin, 1));
    }//GEN-LAST:event_rightActionPerformed

    private void grid_usuarioMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grid_usuarioMouseReleased
        // TODO add your handling code here:
        if(evt.getButton()==MouseEvent.BUTTON3){
            if(!grid_usuario.getSelectionModel().isSelectionEmpty()){
            pop.show(grid_usuario,evt.getX(),evt.getY());
            }else
                JOptionPane.showMessageDialog(this, "Please select a row!", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_grid_usuarioMouseReleased

    private void deactivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deactivateActionPerformed
        // TODO add your handling code here:
        String requete="update users set status='inactif' where iduser=?";
        int lin=grid_usuario.getSelectedRow();
        conn.Update(requete, 0, grid_usuario.getValueAt(lin, 0));
        showData();
        
    }//GEN-LAST:event_deactivateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btn_getinfo;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_nouveau;
    private javax.swing.JMenuItem change_password;
    private javax.swing.JComboBox cmb_dept;
    private javax.swing.JComboBox cmb_nivel;
    private javax.swing.JComboBox cmb_statut;
    private javax.swing.JMenuItem deactivate;
    private javax.swing.JTable grid_usuario;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JPanel panel_station;
    private javax.swing.JPopupMenu pop;
    private javax.swing.JMenuItem right;
    private javax.swing.JPasswordField txt_pass1;
    private javax.swing.JPasswordField txt_pass2;
    private javax.swing.JTextField txt_search;
    public static javax.swing.JTextField txt_staff;
    public static javax.swing.JTextField txt_usu;
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
}
