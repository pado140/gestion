/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import connection.ConnectionDb;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import observateurs.Observateurs;
import observateurs.Observe;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Duvers
 */
public class Bundle extends javax.swing.JInternalFrame implements Observe{
       private final ConnectionDb conn = ConnectionDb.instance();
       private Map<String , Integer> po=new HashMap<>();
       private Observateurs observa=new lot(null,false),observa1=new TO_WIP(null,false);
       private Set<Object[]> liste;
       private DefaultTableModel tbm;
       private SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yy");
    /**
     * Creates new form Bundle
     */
    public Bundle() {
        initComponents();
        ajouterObservateur(observa);
        ajouterObservateur(observa1);
        tbm = (DefaultTableModel) grid_bundle.getModel();
        mostrarDatos();
        
    }

    
     public void mostrar(){
         
     }
     private Map<String,Integer> initSewStart(){
        String requete="select po,sku, qty from at_mod";
        Map<String,Integer> sewing=new HashMap<>();
        ResultSet rs=conn.select(requete);
        
    try {
        while(rs.next()){
            sewing.put(rs.getString("po").trim().concat("."+rs.getString("sku").trim()),rs.getInt("qty"));
        }
    } catch (SQLException ex) {
        Logger.getLogger(WIPCONTROL.class.getName()).log(Level.SEVERE, null, ex);
    }
    return sewing;
        
    }
     public void mostrarDatos(){
    
               liste=new HashSet<>();
               grid_bundle.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 13 ));
               Map<String,Integer> sew=initSewStart();
               tbm.setRowCount(0);
               int qt=0;
               String query="SELECT [ID_SEWING],sm.[ORDER_NUM] as sewing_card\n" +
",sm.[SEWING_TRAVELER]\n" +
",[QTY_CUT]\n" +
",[DATE]\n" +
",[MOD]\n" +
",[WORKCENTER]\n" +
",sm.[stickers]\n" +
",status_147,\n" +
"prtnum_01 as sku\n" +
",DRANUM_01 as style\n" +
",udfref_01 as color\n" +
",filler_01 as size\n" +
",CUSORD_147 po\n" +
",tomod \n" +
"FROM [SEWING_MIRROR] sm\n" +
"inner join \n" +
"ShopOrder on(sm.ORDER_NUM=ORDNUM_147)\n" +
"inner join Part_Master on(PRTNUM_01=PRTNUM_147)\n" +
"where status_147<>'5'";
               
                try {
               ResultSet rs = conn.select(query);
               
               while (rs.next()){
                   String sku=rs.getString("sku").trim();
                   String sizeval=rs.getString("size").trim();
                   /*if(rs.getString("description").toLowerCase().contains("yth")||
                   rs.getString("description").toLowerCase().contains("youth")||
                   rs.getString("description").toLowerCase().contains("girls"))
                   size="Y"+size;*/
                String colorv,Code_color;
               colorv=rs.getString("color").trim();
               Code_color=sku.substring(sku.indexOf(".")+1,sku.lastIndexOf(".")).trim();
               int atSew=0;
               try{
                   atSew=sew.get(rs.getString("po").trim().concat(".").concat(sku));
               }catch(NullPointerException e){
                   atSew=0;
               }
               Date created=rs.getDate("DATE");
               int tomod=rs.getInt("tomod");
               boolean isOk=false;
               if(tomod!=1)
                   isOk=true;
               
               if(isOk){
                   Object[] data=new Object[10];
                   data[0]=rs.getString("sewing_card");
                   data[1]=rs.getString("po");
                   data[2]=rs.getString("style");
                   data[3]="N/A";
                   data[4]=Code_color;
                   data[5]=colorv;
                   data[6]=sizeval;
                   data[7]=rs.getInt("qty_cut");
                   data[8]=rs.getString("stickers");
                   data[9]=rs.getString("sewing_traveler");
                   qt+=rs.getInt("qty_cut");
                   liste.add(data);
                   tbm.addRow(data);
               }
               }
           } catch (SQLException ex) {
               Logger.getLogger(Bundle.class.getName()).log(Level.SEVERE, null, ex);
           }
           qty.setText(String.valueOf(qt));
           line.setText(String.valueOf(tbm.getRowCount()));
      
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popup = new javax.swing.JPopupMenu();
        gen = new javax.swing.JMenuItem();
        mod = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        grid_bundle = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        po_text = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        style_text = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        color = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        size = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        code = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        qty = new javax.swing.JLabel();
        line = new javax.swing.JLabel();

        gen.setText("Generate bundle barcode");
        gen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genActionPerformed(evt);
            }
        });
        popup.add(gen);

        mod.setText("Issue to WIP");
        mod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modActionPerformed(evt);
            }
        });
        popup.add(mod);

        setClosable(true);
        setIconifiable(true);
        setTitle("Bundle");
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
                formInternalFrameDeiconified(evt);
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        grid_bundle.setAutoCreateRowSorter(true);
        grid_bundle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Record", "PO", "STYLE", "DESCRIPTION", "COLOR_CODE", "COLOR", "SIZE", "QTY", "BARCODE", "SKU"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grid_bundle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                grid_bundleMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(grid_bundle);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/icon/export.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("PO");

        po_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                po_textActionPerformed(evt);
            }
        });
        po_text.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                po_textKeyReleased(evt);
            }
        });

        jLabel2.setText("STYLE");

        style_text.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                style_textKeyReleased(evt);
            }
        });

        jLabel3.setText("COLOR");

        color.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                colorKeyReleased(evt);
            }
        });

        jLabel4.setText("SIZE");

        size.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sizeKeyReleased(evt);
            }
        });

        jLabel5.setText("BARCODE");

        code.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                codeKeyReleased(evt);
            }
        });

        jLabel6.setText("Lines");

        jLabel7.setText("qty:");

        qty.setText("jLabel6");

        line.setText("jLabel6");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1059, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(po_text, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(style_text, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(color, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(size, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(code, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(line, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(210, 210, 210)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 4, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(line))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(style_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(po_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel7)
                                            .addComponent(qty))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
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

    private void genActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genActionPerformed
        // TODO add your handling code here:
        Object[] data=new Object[9];
        
        int ligne=grid_bundle.getSelectedRow();
        data[0]=grid_bundle.getValueAt(ligne, 1);
        data[1]=grid_bundle.getValueAt(ligne, 2);
        data[2]=grid_bundle.getValueAt(ligne, 4);
        data[3]=grid_bundle.getValueAt(ligne, 5);
        data[4]=grid_bundle.getValueAt(ligne, 6);
        data[5]=grid_bundle.getValueAt(ligne, 7);
        data[6]=grid_bundle.getValueAt(ligne, 0);
        data[7]=grid_bundle.getValueAt(ligne, 9);
        data[8]=grid_bundle.getValueAt(ligne, 8);
        
        alerter("generatelot",data);
        ((JDialog)observa).setModal(true);
        ((JDialog)observa).setVisible(true);
    }//GEN-LAST:event_genActionPerformed

    private void grid_bundleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_grid_bundleMouseClicked
        // TODO add your handling code here:
        if(evt.getButton()==3){
            if(!grid_bundle.getSelectionModel().isSelectionEmpty()){
                popup.removeAll();
             if(!hasTicket(grid_bundle.getValueAt(grid_bundle.getSelectedRow(), 0).toString())){   
            
            popup.add(mod);
             }
             else{
                     popup.add(mod);
                 
             }
        popup.show(grid_bundle, evt.getX(), evt.getY());
            }
        }
    }//GEN-LAST:event_grid_bundleMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JFileChooser file=new JFileChooser("C:/",FileSystemView.getFileSystemView());
        file.setDialogTitle("enregistre le fichier");
        
        file.setFileFilter(new FileNameExtensionFilter("Workbook excel","xlsx","xls"));
        int returnAct=file.showSaveDialog(this);
        if(returnAct==JFileChooser.APPROVE_OPTION){
            XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("cutting card");
        
        //Create some data to build the pivot table on
        setCellData(sheet,grid_bundle);
        
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Bundle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Bundle.class.getName()).log(Level.SEVERE, null, ex);
        }
            JOptionPane.showMessageDialog(null, "File saved with success");
        }
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void filtre(){
        String po1=po_text.getText().trim().toLowerCase();
        String style=style_text.getText().trim().toLowerCase();
        String col=color.getText().trim().toLowerCase();
        String siz=size.getText().trim().toLowerCase();
        String co=code.getText().trim().toLowerCase();
        tbm.setRowCount(0);
        int qt=0;
        for(Object[] o:liste){
            if(o[1].toString().toLowerCase().trim().contains(po1)&&
                    o[2].toString().toLowerCase().trim().contains(style)&&(
                    o[4].toString().toLowerCase().trim().contains(col)||
                    o[5].toString().toLowerCase().trim().contains(col))&&
                    o[6].toString().toLowerCase().trim().contains(siz)&&
                    o[8].toString().toLowerCase().trim().contains(co)){
                qt+=(Integer)o[7];
                tbm.addRow(o);
            }
        }
        qty.setText(String.valueOf(qt));
        line.setText(String.valueOf(tbm.getRowCount()));
    }
    private void modActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modActionPerformed
        // TODO add your handling code here:
        Object[][] data=new Object[grid_bundle.getSelectedRowCount()][9];
        for(int i=0;i<grid_bundle.getSelectedRowCount();i++){
        int ligne=grid_bundle.getSelectedRows()[i];
        data[i][0]=grid_bundle.getValueAt(ligne, 1);
        data[i][1]=grid_bundle.getValueAt(ligne, 2);
        data[i][2]=grid_bundle.getValueAt(ligne, 4);
        data[i][3]=grid_bundle.getValueAt(ligne, 5);
        data[i][4]=grid_bundle.getValueAt(ligne, 6);
        data[i][5]=grid_bundle.getValueAt(ligne, 7);
        data[i][6]=grid_bundle.getValueAt(ligne, 0);
        data[i][7]=grid_bundle.getValueAt(ligne, 9);
        data[i][8]=grid_bundle.getValueAt(ligne, 8);
        }
        alerter("issued",data);
        ((JDialog)observa1).setModal(true);
        ((JDialog)observa1).setVisible(true);
        mostrarDatos();
        //filtre(potext.getText(),style_txt.getText(),col_txt.getText());
    }//GEN-LAST:event_modActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        // TODO add your handling code here:
        //mostrarDatos();
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        // TODO add your handling code here:
        mostrarDatos();
    }//GEN-LAST:event_formInternalFrameActivated

    private void formInternalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeiconified
        // TODO add your handling code here:
        mostrarDatos();
    }//GEN-LAST:event_formInternalFrameDeiconified

    private void po_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_po_textActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_po_textActionPerformed

    private void po_textKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_po_textKeyReleased
        // TODO add your handling code here:
        filtre();
    }//GEN-LAST:event_po_textKeyReleased

    private void style_textKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_style_textKeyReleased
        // TODO add your handling code here:
        filtre();
    }//GEN-LAST:event_style_textKeyReleased

    private void colorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_colorKeyReleased
        // TODO add your handling code here:
        filtre();
    }//GEN-LAST:event_colorKeyReleased

    private void sizeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sizeKeyReleased
        // TODO add your handling code here:
        filtre();
    }//GEN-LAST:event_sizeKeyReleased

    private void codeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codeKeyReleased
        // TODO add your handling code here:
        filtre();
    }//GEN-LAST:event_codeKeyReleased

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
                }catch(NullPointerException e){
                    
                }
            }
                
        }
    }
    
    public boolean hasTicket(String bundle){
        String requete="select count(*) nb from sew_prod2 where order_num=?";
        ResultSet rs = conn.select(requete,bundle);
        System.out.println(requete);       
           try {
               while (rs.next()){
                   if(rs.getInt("nb")>0)
                       return true;
               }
           } catch (SQLException ex) {
               Logger.getLogger(Bundle.class.getName()).log(Level.SEVERE, null, ex);
           }
           return false;
    }
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField code;
    private javax.swing.JTextField color;
    private javax.swing.JMenuItem gen;
    private javax.swing.JTable grid_bundle;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel line;
    private javax.swing.JMenuItem mod;
    private javax.swing.JTextField po_text;
    private javax.swing.JPopupMenu popup;
    private javax.swing.JLabel qty;
    private javax.swing.JTextField size;
    private javax.swing.JTextField style_text;
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
