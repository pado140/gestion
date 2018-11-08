/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import connection.ConnectionDb;
import java.awt.event.MouseAdapter;
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
import javax.swing.table.JTableHeader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Duvers
 */
public class Work_status extends javax.swing.JInternalFrame {

    private ConnectionDb conn = ConnectionDb.instance();
    private Map<String , Integer> prod;
    private Map<String, String> ref=null;
    private Map<String, Integer> sewn,packed,plan,second,ship,soa,padprint,sew,sewing;
    private Set<Object[]> listeData,temp;
    private Map<String,Map<String,Map<String,Map<String,Integer>>>> cut_pro=new HashMap<>();
    private JTableHeader header;
    private PopulateTable populate;
    /**
     * Creates new form Work_status_
     */
    
    class PopulateTable extends SwingWorker<Void,Object[]>{

        @Override
        protected Void doInBackground(){
            load();
//        Second();
//        shipped();
//        data_cut();
//        initPlan();
//        padprint();
//        at_sewing();
//        initSewStart();
//        saoBar();
//        initSewn();
//        initPacked();
            //listData();
            return null;
        }

        @Override
        protected void process(List<Object[]> chunks) {
            super.process(chunks); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    public Work_status() {
        initComponents();
        grid_data.getTableHeader().addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                int col=grid_data.columnAtPoint(e.getPoint());
                String name=grid_data.getColumnName(col);
                System.out.println("Column index selected:"+col+ " "+name);
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.err.println("Ok"); //To change body of generated methods, choose Tools | Templates.
            }
            
            
        });
        
        init();
         
        
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
            Logger.getLogger(Work_status_.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Work_status_.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Work_status_.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void init(){
        
        prod=new HashMap<>();
        
        populate=new PopulateTable();
        populate.execute();
        //listData();
    }
    
    
    private void load(){
        DefaultTableModel tbm = (DefaultTableModel) grid_data.getModel();
        tbm.setRowCount(0);
        int tot=0,line=0;
        listeData=new HashSet<>();
        String requete="select * from work_process";
        ResultSet rs = conn.select(requete);
        try {
            while(rs.next()){
                String sku=rs.getString("sku").trim();
               
                String size=rs.getString("size");
                String desc=rs.getString("description").trim(),
                        po=rs.getString("po").trim();
                
                if(!rs.getString("brand").equals("CHS")&& !rs.getString("brand").equals("GBG")){
                if(desc.toLowerCase().contains("yth")||desc.toLowerCase().contains("youth")||
                        desc.toLowerCase().contains("girls")||desc.toLowerCase().contains("boys"))
                size="Y"+size;
                }
                String status=rs.getString("state");
                Object[] data=new Object[23];
                data[1]=rs.getString("brand");
                data[2]=po;
                data[3]=rs.getString("style").trim();
                data[4]=sku.substring(sku.indexOf(".")+1, sku.lastIndexOf("."));
                data[5]=rs.getString("color").trim();
                data[6]=size.trim();
                data[8]=rs.getInt("qty");
                data[9]=status;
                data[7]=po+"-"+sku;
                data[10]=rs.getString("work_order");
                data[11]=data[12]=data[13]=0;
                data[14]=rs.getInt("at_sb");
                data[15]=rs.getInt("at_pp");
                data[16]=rs.getInt("at_sew");
                data[17]=rs.getInt("at_mod");
                data[18]=rs.getInt("sewn");
                data[19]=0;
                data[20]=rs.getInt("packed");
                data[21]=rs.getInt("shipped");
                data[22]=0;
                //data[19]
//                data[23]=sku;
//                data[24]=status;
                data[0]=rs.getDate("shipdate");
                listeData.add(data);
                tbm.addRow(data);
               tot+=rs.getInt("qty");;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Work_status_.class.getName()).log(Level.SEVERE, null, ex);
        }
        items.setText(String.valueOf(listeData.size()));
        totorder.setText(String.valueOf(tot));
    }
    private void listData(){
        
        //listeData=new HashSet<>();
        int cut=0,aso=0,apad=0,first=0,tplan=0;
        DefaultTableModel tbm = (DefaultTableModel) grid_data.getModel();
        tbm.setRowCount(0);
        for(Object[] data:listeData){
            String ordnum=data[10].toString().trim();
                String status="cut not start yet";
                int bal=Integer.parseInt(data[8].toString());
                //data[0]=Sku.substring(0, Sku.indexOf("."));
//                data[21]=data[11]=data[12]=data[13]=data[14]=data[15]=data[16]=data[17]=data[18]=data[19]=data[20]=0;
//                data[22]=data[8].toString();
//                int so=0,pp=0,se=0,planqty=0,prodqty=0;
//                try{
//                    if(plan.containsKey(Sku.trim())){
//                         status="ready to cut";
//                         planqty=plan.get(Sku.trim());
//                         
//                            data[11]=plan.get(Sku.trim());
//                         
//                     }
//                     
//                    if(prod.get(Sku.trim())>0){
//                        data[11]=0;
//                        status="cutting";
//                        prodqty=prod.get(Sku.trim());
//                        
//                        if(prodqty>=planqty){
//                            status="cut";
//                            
//                            
//                            //data[12]=prod.get(Sku.trim())-so;
//                        }
//                    }
//                    if(soa.containsKey(Sku.trim())){
//                         so=soa.get(Sku.trim());
//                            status="At Soabar";
//                            //data[13]=soa.get(Sku.trim())-pp;
//                        }
//                    if(padprint.containsKey(Sku.trim())){
//                        pp=padprint.get(Sku.trim());
//                            status="At pad print/Heat transfer";
//                            //data[14]=pp-se;
//                        }
//                    
//                    if(sew.containsKey(Sku.trim())){
//                         se=sew.get(Sku.trim());
//                            status="Recieved";
//                            //data[15]=se;
//                        }
//                     
//                     //if(data[23].equals("3")){
//                     
//                    //}
//                        
//                            
//                            int val=0;
//                            int pac=0;
//                            int sec=0,sh=0;
//                            if(sewn.containsKey(Sku.trim()))
//                                val=sewn.get(Sku.trim());
//                                
//                            //System.out.println("sewn:"+rs.getString("ordnum_147").trim()+"\t"+val);
//                            
//                            if(packed.containsKey(Sku.trim())){
//                                pac=packed.get(Sku.trim());
//                            }
//                            if(second.containsKey(Sku.trim())){
//                                sec=second.get(Sku.trim());
//                            }
//                            if(ship.containsKey(Sku.trim())){
//                                sh=ship.get(Sku.trim());
//                                bal-=sh;
//                            }
//                            int sew_start=0;
//                            if(sewing.containsKey(Sku.trim())){
//                                sew_start=sewing.get(Sku.trim());
//                                status="WIP";
//                            }
//                            sew_start-=(val+sec);
//                            /*if(Sku.trim().equals("9604979.2909.065.S")){
//                            System.out.println("sku:"+Sku.trim()+"\tcut:"+prodqty+"\t"
//                            + "soabar:"+so+"\t"
//                            + "padprint:"+pp+"\t"
//                            + "sewing:"+se+"\t"
//                            + "sew_");
//                            }*/
//                            data[21]=sh;
//                            data[20]=pac-sh;
//                            data[19]=sec;
//                            data[18]=val-pac;
//                            data[17]=sew_start;
//                            data[16]=se-(sew_start+val+sec);
//                            data[15]=pp-se;
//                            data[14]=so-pp;
//                            data[13]=prodqty-so;
//                            data[12]=planqty-prodqty;
//                            tplan+=planqty-prodqty;
//                            cut+=prodqty-so;
//                            aso+=so-pp;
//                            apad+=pp-se;
//                            first+=val-pac;
//                            
//                            if(planqty-prodqty>0)
//                                status="cutting";
//                            else{
//                                if(prodqty-so>0)
//                                    status="cut";
//                                else{
//                                    if(so-pp>0)
//                                        status="At Soabar";
//                                    else{
//                                        if(pp-se>0)
//                                            status="At pad print/Heat transfer";
//                                        else{
//                                            if(se-(sew_start+val+sec)>0)
//                                                status="Recieved";
//                                            else
//                                                if(sew_start+val+sec>0)
//                                                    status="WIP-At mod";
//                                                else{
//                                                    
//                                                }
//                                        }
//                                    }
//                                }
//                            }
//                            
//                            
//                        
//                        data[22]=bal;
//                    
//                    
//                }catch(NullPointerException e){
//                    
//                }
//                if(data[24].equals("5"))
//                    status="Closed";
//                data[9]=status;
//                listeData.add(data);
                tbm.addRow(data);
                
            }
           totcut.setText(String.valueOf(cut));
           totsobar.setText(String.valueOf(aso));
           totpad.setText(String.valueOf(apad));
           totpl.setText(String.valueOf(tplan));
           totfirst.setText(String.valueOf(first));
        
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

        jLabel13.setText("sku FILTER");

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
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(84, 84, 84)
                                .addComponent(jLabel5)))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(234, 234, 234)
                                .addComponent(jButton1))
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
            if(!grid_data.getSelectionModel().isSelectionEmpty()){
                if(!grid_data.getValueAt(grid_data.getSelectedRow(), grid_data.getColumn("STATUS").getModelIndex()).equals("Closed"))
            POPUP.show(grid_data,evt.getX(),evt.getY());
            }else
                JOptionPane.showMessageDialog(this, "Please select a row!", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
        }
    }//GEN-LAST:event_grid_dataMouseReleased

    private void DETAILSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DETAILSActionPerformed
        // TODO add your handling code here:
        for(int i=0;i<grid_data.getSelectedRowCount();i++){
            int ligne=grid_data.getSelectedRows()[i];
            System.err.println(grid_data.getColumn("WORK ORDER").getModelIndex());
        if(changeStatus(grid_data.getValueAt(ligne, grid_data.getColumn("WORK ORDER").getModelIndex()).toString()))
            grid_data.setValueAt("Closed",ligne, grid_data.getColumn("STATUS").getModelIndex());
        
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
        String sku=jTextField6.getText().trim().toLowerCase();
        
        String data="";
        int cut=0,aso=0,apad=0,first=0,tplan=0,tot=0,line=0;
        
        DefaultTableModel tbm = (DefaultTableModel) grid_data.getModel();
        tbm.setRowCount(0);
        for(Object[] o:listeData){
            if(o[2].toString().toLowerCase().contains(potxt.trim().toLowerCase())&& o[3].toString().toLowerCase().contains(style.trim().toLowerCase())&&
                    (o[5].toString().toLowerCase().contains(col.trim())||o[4].toString().toLowerCase().contains(col.trim()))&&
                    o[6].toString().toLowerCase().contains(size.trim())&&o[1].toString().toLowerCase().contains(client)&&o[7].toString().toLowerCase().contains(sku)
               )
                                    {
                tbm.addRow(o);
                tot+=Integer.parseInt(o[8].toString());
                tplan+=Integer.parseInt(o[12].toString());
                            cut+=Integer.parseInt(o[13].toString());
                            aso+=Integer.parseInt(o[14].toString());
                            apad+=Integer.parseInt(o[15].toString());
                            first+=Integer.parseInt(o[18].toString());
            }
        }
        totcut.setText(String.valueOf(cut));
           totsobar.setText(String.valueOf(aso));
           totpad.setText(String.valueOf(apad));
           totpl.setText(String.valueOf(tplan));
           totfirst.setText(String.valueOf(first));
           items.setText(String.valueOf(grid_data.getRowCount()));
        totorder.setText(String.valueOf(tot));
    }

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
               if(order.contains("183.BP28.NYW")){
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
