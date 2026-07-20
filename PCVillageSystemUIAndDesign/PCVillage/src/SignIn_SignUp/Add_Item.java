/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SignIn_SignUp;


import AdminUi.AdminUi;
import AdminUi.GetData;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aries
 */
public class Add_Item extends javax.swing.JFrame {

    /**
     * Creates new form SignUp
     */
    
    public Add_Item(GetData data) {
        getData = data;
        setAdd_item(); 
        
        if(data!=null){
            product_type.setText(data.getInventoryPType());
            product_brand.setText(data.getInventoryPBrand());
            product_model.setText(data.getInventoryPModel());
            product_purchase_price.setText(String.valueOf(data.getInventoryPPurchasePrice()));
            product_selling_price.setText(String.valueOf(data.getInventoryPSellingPrice()));
            product_quantity.setText(String.valueOf(data.getInventoryPQuantity())); 
        }
        
    }
    
    public void setAdd_item(){
        initComponents();
        this.setLocationRelativeTo(null);// center form
        
        
    }
    
    public void InventoryAddItemToTable(String query) {
        AdminUi getConnection = new AdminUi(null);
        Connection connection = getConnection.getConnection("pcvillage");
        Statement st; 
        try {
            st = connection.createStatement();
            
            if(!product_type.getText().equals("")&&!product_brand.getText().equals("")&&!product_model.getText().equals("")&&!product_purchase_price.getText().equals("")&&
            !product_selling_price.getText().equals("")&&!product_quantity.getText().equals("")){
               st.execute(query); 
               st.close();
            }else{
                JOptionPane.showMessageDialog(this, "One or more of the fields is empty."+"\nPlease fill in all of the fields.");
            }
                
        } catch (SQLException ex) {
            Logger.getLogger(Add_Item.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void InventoryEditItemFromTable(){
        dateToday = new Date();
        sdf = new SimpleDateFormat("MM-dd-yyyy");
        String query = "UPDATE inventory SET product_type = TRIM('"+product_type.getText()+"'), product_brand = TRIM('"+product_brand.getText()+"'), product_model = TRIM('"+product_model.getText()+"'), product_purchase_price = "+product_purchase_price.getText()+", product_selling_price = "+ product_selling_price.getText()+", product_quantity = "+product_quantity.getText()+", product_last_changed_date = '"+sdf.format(dateToday)+"' WHERE product_id = "+getData.getInventoryPID();
       
        if(!product_type.getText().equals("")&&!product_brand.getText().equals("")&&!product_model.getText().equals("")&&!product_purchase_price.getText().equals("")&&
            !product_selling_price.getText().equals("")&&!product_quantity.getText().equals("")){ 
            try {
                Statement st = new AdminUi(null).getConnection("pcvillage").createStatement();
                st.execute(query);
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(Add_Item.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    public int isItemAlreadyExistsInTable(){
        int ifExists = 0;
        AdminUi getConnection = new AdminUi(null);
        Statement st;
        ResultSet rs; 
        String query = "SELECT* FROM inventory ORDER BY product_id ASC";
        String type,brand,model,purchase,selling;
        
        type = product_type.getText().replaceAll(" ","");
        brand = product_brand.getText().replaceAll(" ","");
        model = product_model.getText().replaceAll(" ","");
        purchase = product_purchase_price.getText().replaceAll(" ","");
        selling = product_selling_price.getText().replaceAll(" ","");
        
        try {
            st = getConnection.getConnection("pcvillage").createStatement();
            rs = st.executeQuery(query);
            
            while(rs.next()){
               
                if(type.equals(rs.getString("product_type"))&&brand.equals(rs.getString("product_brand"))&&
                    model.equals(rs.getString("product_model"))&&
                    purchase.equals(rs.getString("product_purchase_price"))&&
                    selling.equals(rs.getString("product_selling_price"))&&
                    getData.getInventoryPID()!=rs.getInt("product_id")){
                    
                    ifExists = rs.getInt("product_id");
                }
                
            }
            
            return ifExists;
            
        } catch (SQLException ex) {
            Logger.getLogger(Add_Item.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return 0;
        
    }
    
    
    
    public void InventoryItemAddedAlreadyExistsSetToQuantity(int idvalue){
        
        if(idvalue>0){
            AdminUi getConnection = new AdminUi(null);
            Statement st;
            String query;
            String query2 = "SELECT* FROM inventory WHERE product_id = " + idvalue;
            ResultSet rs;
            try {
                st = getConnection.getConnection("pcvillage").createStatement();
                rs = st.executeQuery(query2);
                rs.next();
                query = "UPDATE inventory SET product_quantity = "+(Integer.parseInt(product_quantity.getText())+rs.getInt("product_quantity"))+" WHERE product_id = "+idvalue;
            
                st.execute(query);
            
            } catch (SQLException ex) {
                Logger.getLogger(Add_Item.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
    }
    
    public void ResetTABLEAutoIncrement(int value, String str){
        AdminUi getConnection = new AdminUi(null);
        Connection connection = getConnection.getConnection("pcvillage");
        Statement st;
        ResultSet rs; 
        String query = "Select product_id FROM "+str+" ORDER BY product_id DESC LIMIT 1";
        String query2,query3; 
        if(value!=0)
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            rs.next();
            query2 = "UPDATE inventory SET product_id = "+(value+1)+" "+"WHERE product_id = "+rs.getString("product_id");
            query3 = "ALTER TABLE inventory AUTO_INCREMENT= " + (value+2);
            
            if(rs.getInt("product_id")-value>1){
                st.execute(query2);
                st.execute(query3);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Add_Item.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //---------------------------------
    //This method is currently not used
    //---------------------------------
    public boolean isFieldsEmpty(){
        String type, brand, model, purchase,selling, quantity;
        
        type = product_type.getText().replaceAll(" ","");
        brand = product_brand.getText().replaceAll(" ","");
        model = product_model.getText().replaceAll(" ","");
        purchase = product_purchase_price.getText().replaceAll(" ","");
        selling = product_selling_price.getText().replaceAll(" ","");
        
        
        if(type.equals(""));
            return true;
    }
    //---------------------------------
    //End of method currently not used
    //---------------------------------
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        OkButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        product_type = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        product_brand = new javax.swing.JTextField();
        product_purchase_price = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        product_model = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        product_selling_price = new javax.swing.JTextField();
        product_quantity = new javax.swing.JTextField();
        ClearButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(0, 153, 204));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Update Inventory");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("X");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(0, 51, 102));
        jPanel12.setForeground(new java.awt.Color(255, 255, 255));

        OkButton.setBackground(new java.awt.Color(0, 102, 153));
        OkButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        OkButton.setForeground(new java.awt.Color(255, 255, 255));
        OkButton.setText("OK");
        OkButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                OkButtonMouseReleased(evt);
            }
        });
        OkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OkButtonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Type:");

        product_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                product_typeActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Brand:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Purchase Price:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Selling Price:");

        product_brand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                product_brandActionPerformed(evt);
            }
        });

        product_purchase_price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                product_purchase_priceActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Model:");

        product_model.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                product_modelActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Quantity:");

        product_selling_price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                product_selling_priceActionPerformed(evt);
            }
        });

        product_quantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                product_quantityActionPerformed(evt);
            }
        });

        ClearButton.setBackground(new java.awt.Color(0, 102, 153));
        ClearButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ClearButton.setForeground(new java.awt.Color(255, 255, 255));
        ClearButton.setText("CLEAR");
        ClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel20)
                            .addComponent(jLabel19)
                            .addComponent(jLabel6)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(product_brand, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(product_type, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(product_model, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(product_purchase_price, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(product_selling_price, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(OkButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(ClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(product_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(product_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(13, 13, 13)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(product_brand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(product_model, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(product_purchase_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(product_selling_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(13, 13, 13)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(product_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OkButton)
                    .addComponent(ClearButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
       this.dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void product_selling_priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_product_selling_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_product_selling_priceActionPerformed

    private void product_modelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_product_modelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_product_modelActionPerformed

    private void product_purchase_priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_product_purchase_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_product_purchase_priceActionPerformed

    private void product_brandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_product_brandActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_product_brandActionPerformed

    private void product_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_product_typeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_product_typeActionPerformed

    private void product_quantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_product_quantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_product_quantityActionPerformed

    private void ClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ClearButtonActionPerformed

    private void OkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OkButtonActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        
        dateToday = new Date();
        sdf = new SimpleDateFormat("MM-dd-yyyy");
        String query = "INSERT INTO inventory VALUES(DEFAULT,"+"TRIM('"+product_type.getText()+"')"+","+"TRIM('"+product_brand.getText()+"')"+","+"TRIM('"+product_model.getText()+"')"+","+product_purchase_price.getText()+","+product_selling_price.getText()+","+product_quantity.getText()+","+"'"+sdf.format(dateToday)+"'"+")";
        int ifExists = isItemAlreadyExistsInTable();
        
        if(getData==null){
            AdminUi getAdminUi = new AdminUi(null);
            DefaultTableModel model = getAdminUi.getTableModel("inventory");
            if(getAdminUi.getTable("inventory").getRowCount()!=0)
                value = (int) model.getValueAt(getAdminUi.getTable("inventory").getRowCount()-1, 0);
            
            if(ifExists==0){
                InventoryAddItemToTable(query);
                ResetTABLEAutoIncrement(value,"inventory");
            }else{
                InventoryItemAddedAlreadyExistsSetToQuantity(ifExists);
            }
        }
        else{
            
            //add joptionpane if already exists;
            if(ifExists>0)
                JOptionPane.showMessageDialog(this, "Can't edit this item into entered values. Item already exists.");
            else
                InventoryEditItemFromTable();
            
        }
        
        if(getData!=null&&ifExists>0){
        }else{
        this.dispose();
        }
        
        
    }//GEN-LAST:event_OkButtonActionPerformed

    private void OkButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OkButtonMouseReleased
        // TODO add your handling code here:
            
           
        
    }//GEN-LAST:event_OkButtonMouseReleased

    /**
     * @param args the command line arguments
     */
   /* public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
       /* try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Add_Item.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Add_Item.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Add_Item.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Add_Item.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
       /* java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Add_Item().setVisible(true);
            }
        });
    }*/
    
    private Date dateToday;
    private SimpleDateFormat sdf;
    private GetData getData;
    private int value;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ClearButton;
    private javax.swing.JButton OkButton;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField product_brand;
    private javax.swing.JTextField product_model;
    private javax.swing.JTextField product_purchase_price;
    private javax.swing.JTextField product_quantity;
    private javax.swing.JTextField product_selling_price;
    private javax.swing.JTextField product_type;
    // End of variables declaration//GEN-END:variables
}
