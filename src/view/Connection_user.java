/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import connection.ConnectionDb;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import observateurs.Observateurs;
import observateurs.Observe;

/**
 *
 * @author Padovano
 */
public class Connection_user extends javax.swing.JDialog implements Observe,Observateurs{
private ConnectionDb conn = ConnectionDb.instance();
    /**
     * Creates new form Connection_user
     */
    javax.swing.JFrame parent;
    public Connection_user(javax.swing.JFrame parent, boolean modal) {
        super(parent, modal);
        this.parent=parent;
        
        initComponents();
        //this.getContentPane().setBackground(Color.WHITE);
        this.setLocationRelativeTo(parent);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        label_error = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        pan_pass = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        pan_user = new javax.swing.JPanel();
        username = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setModalExclusionType(null);
        setUndecorated(true);
        setType(java.awt.Window.Type.POPUP);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/icons8_User_Male_25px.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/icons8_Lock_2_25px.png"))); // NOI18N

        label_error.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label_error.setForeground(new java.awt.Color(255, 0, 0));
        label_error.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_error.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/icon/icons8_ID_not_Verified_24px_1.png"))); // NOI18N
        label_error.setVisible(false);
        label_error.setText("");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/icon/loginbtn.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(17, 81, 155));

        jLabel5.setBackground(null);
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/icon/icons8_Close_Window_32px_1.png"))); // NOI18N
        jLabel5.setOpaque(true);
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Futura Bk BT", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Log In");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 326, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pan_pass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pan_passFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pan_passFocusLost(evt);
            }
        });
        pan_pass.setLayout(null);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/icon/icons8_Eye_24px.png"))); // NOI18N
        jLabel1.setOpaque(true);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel1MouseReleased(evt);
            }
        });
        pan_pass.add(jLabel1);
        jLabel1.setBounds(200, 20, 20, 30);

        password.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwordFocusLost(evt);
            }
        });
        password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordActionPerformed(evt);
            }
        });
        password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                passwordKeyReleased(evt);
            }
        });
        pan_pass.add(password);
        password.setBounds(10, 20, 180, 30);

        pan_user.setLayout(null);

        username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                usernameFocusLost(evt);
            }
        });
        username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                usernameKeyReleased(evt);
            }
        });
        pan_user.add(username);
        username.setBounds(10, 20, 180, 29);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(jLabel2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pan_user, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                            .addComponent(pan_pass, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(label_error, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pan_user, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pan_pass, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(label_error)
                .addGap(12, 12, 12)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        this.parent.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Authentification();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        if(evt.isActionKey()){
            Authentification();
        }
    }//GEN-LAST:event_formKeyReleased

    private void passwordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordKeyReleased
        
        if((evt.getKeyCode() == KeyEvent.VK_ENTER)){
         Authentification();
        }
    }//GEN-LAST:event_passwordKeyReleased

    private void usernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameKeyReleased
       
        if((evt.getKeyCode() == KeyEvent.VK_ENTER)){
         Authentification();
        }
    }//GEN-LAST:event_usernameKeyReleased

    private void passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordActionPerformed

    private void usernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameFocusGained
        // TODO add your handling code here:
        pan_user.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Enter your username",1,1));
    }//GEN-LAST:event_usernameFocusGained

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        // TODO add your handling code here:
        //password.setVisible(false);
        password.setEchoChar((char)0);
        //passwordvisible.setText(password.getText());
    }//GEN-LAST:event_jLabel1MousePressed

    private void jLabel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseReleased
        // TODO add your handling code here:
        password.setEchoChar('*');
    }//GEN-LAST:event_jLabel1MouseReleased

    private void usernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameFocusLost
        // TODO add your handling code here:
        pan_user.setBorder(null);
        if(!username.getText().trim().isEmpty())
        pan_user.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Enter your Username",1,1));
    }//GEN-LAST:event_usernameFocusLost

    private void passwordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordFocusGained
        // TODO add your handling code here:
        pan_pass.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Enter your Password",1,1));
    }//GEN-LAST:event_passwordFocusGained

    private void pan_passFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pan_passFocusGained
        // TODO add your handling code here:
        pan_pass.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Enter your Password",1,1));
    }//GEN-LAST:event_pan_passFocusGained

    private void pan_passFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pan_passFocusLost
        // TODO add your handling code here:
        pan_pass.setBorder(null);
        
    }//GEN-LAST:event_pan_passFocusLost

    private void passwordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordFocusLost
        // TODO add your handling code here:
         pan_pass.setBorder(null);
         if(!password.getText().trim().isEmpty())
        pan_pass.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Enter your Password",1,1));
    }//GEN-LAST:event_passwordFocusLost

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        parent.dispose();
        System.exit(0);
    }//GEN-LAST:event_jLabel5MouseClicked

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label_error;
    private javax.swing.JPanel pan_pass;
    private javax.swing.JPanel pan_user;
    private javax.swing.JPasswordField password;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables

    private void Authentification(){
        
        label_error.setVisible(true);
        label_error.setText("");
        String User=username.getText().trim();
        String pass="";
        try{
            pass=String.valueOf(password.getPassword()).trim();
        }catch(NullPointerException e){
            pass="";
        }
        if(User.isEmpty() && pass.isEmpty()){
            label_error.setText("Fill out all fields");
        }
        else if(User.isEmpty()){
            label_error.setText("Fill out the user field");
        }else if(pass.isEmpty()){
            label_error.setText("Fill out the pass field");
            
            
        }
        else{
            
            String sql="SELECT * from vw_users WHERE USERNAME = ? AND PASS = ? and status='Actif';";
            boolean auth = false;
          ResultSet rs = conn.select(sql, new Object[]{User,pass});
           try{ 
           while (rs.next()) { 
                        sql="select * from roles_user where user_id=?";
                        ResultSet rs1 = conn.select(sql,rs.getInt("iduser"));
                        Object [] droits=null;
                        rs1.last();
                        droits=new Object[rs1.getRow()];
                        rs1.beforeFirst();
                        int i=0;
                        while (rs1.next()) {
                            droits[i]=rs1.getString("field");
                            i++;
                        }
                       alerter(new Object[]{"Connected",rs.getInt("iduser"),rs.getString("username"),rs.getString("fname"),rs.getString("lname"),
                       rs.getString("nivel"),rs.getString("departement"),droits});
               
           }
           if(!auth){
              label_error.setText("Your credential is not valid !"); 
           }
     
       } catch (SQLException e) {
           System.out.println(e);
      }        
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
        for(Observateurs o:obs){
            o.executer(ob);
        }
    }

    @Override
    public void executer(Object... obs) {
        if(obs[0].toString().equals("Sign in")){
            label_error.setVisible(false);
            label_error.setText("");
            username.setText("");
            password.setText("");
        }
    }
}
