/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SignIn_SignUp;

import AdminUi.AdminUi;
import AdminUi.GetData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aries
 */
public class SignUp extends javax.swing.JFrame {

    /**
     * Creates new form SignUp
     */
    public SignUp(GetData data) {
        getData = data; 
        setSignUp();
        
        if(data!=null){
           SU_firstname.setText(data.getEmployeeFirstName());
           SU_Lastname.setText(data.getEmployeeLastName());
           SU_address.setText(data.getEmployeeAddress());
           SU_username.setText(data.getEmployeeUsername());
           SU_userid.setText(String.valueOf(data.getEmployeeID()));
           SU_password.setText(data.getEmployeePassword());
           if(data.getEmployeeUserType().equals("Admin")){
               SU_usertype.setSelectedIndex(1);
           }else{
               SU_usertype.setSelectedIndex(2);
           }
            
        }
        
    }
    
    public void setSignUp(){
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public Connection getConnection(){
        
        Connection con;
        try{
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.2:3306/pcvillage", "root", "Ab12Cd34Ef56");
            return con;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public void EmployeeAddItemToTable(String query) {
        Connection connection = getConnection();
        Statement st; 
        try {
            st = connection.createStatement();
            
            if(!SU_firstname.getText().equals("")&&!SU_Lastname.getText().equals("")&&!SU_address.getText().equals("")&&
                    !SU_username.getText().equals("")&&!SU_userid.getText().equals("")&&SU_password.getPassword().length !=0&&
                    Arrays.equals(SU_password.getPassword(), SU_retypepassword.getPassword())&&
                    !SU_usertype.getSelectedItem().equals("<select user>")){
               st.execute(query); 
               st.close();
            }else if(!Arrays.equals(SU_password.getPassword(), SU_retypepassword.getPassword())){
                JOptionPane.showMessageDialog(this, "Password and retyped password do not match. Please try again.");
            }else{
                JOptionPane.showMessageDialog(this,"One or more of the fields is empty."+"\nPlease fill in all of the fields.");
            }
            
                
        } catch (SQLException ex) {
            Logger.getLogger(Add_Item.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public boolean isUSERIDAlreadyExistsInTable(){
        
        Statement st;
        ResultSet rs; 
        String query = "SELECT* FROM employee ORDER BY emp_id ASC";
        String userid;
        int user_id;
        
        userid = SU_userid.getText().replaceAll(" ","");
        user_id = Integer.parseInt(userid);
        
        try {
            st = getConnection().createStatement();
            rs = st.executeQuery(query);
            while(rs.next()){
               
                if(user_id == rs.getInt("emp_id")&&getData.getEmployeeID()!=rs.getInt("emp_id")){
                    return true;
                }
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Add_Item.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return false;
        
    }
    
    public void EmployeeEditItemFromTable(){
        String password = "";
        
        for(char a: SU_password.getPassword()){
            password = password + a;
        }
       
        String query = "UPDATE employee SET emp_id = "+SU_userid.getText()+", emp_first_name = TRIM('"+SU_firstname.getText()+"'), emp_last_name = TRIM('"+SU_Lastname.getText()+"'), emp_address = TRIM('"+SU_address.getText()+"'), emp_userType = TRIM('"+(String)SU_usertype.getSelectedItem()+"'), emp_username = TRIM('"+ SU_username.getText()+"'), emp_password = '"+password+"' WHERE emp_id = "+getData.getEmployeeID();
       
        
        
        if(!SU_firstname.getText().equals("")&&!SU_Lastname.getText().equals("")&&!SU_address.getText().equals("")&&
                    !SU_username.getText().equals("")&&!SU_userid.getText().equals("")&&SU_password.getPassword().length !=0&&
                    Arrays.equals(SU_password.getPassword(), SU_retypepassword.getPassword())&&
                    !SU_usertype.getSelectedItem().equals("<select user>")){
            
            Statement st;
            try {
                st = getConnection().createStatement();
                st.execute(query); 
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(!Arrays.equals(SU_password.getPassword(), SU_retypepassword.getPassword())){
            JOptionPane.showMessageDialog(this, "Password and retyped password do not match. Please try again.");
        }else{
            JOptionPane.showMessageDialog(this,"One or more of the fields is empty."+"\nPlease fill in all of the fields.");
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

        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        SU_okbutton = new javax.swing.JButton();
        SU_usertype = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        SU_firstname = new javax.swing.JTextField();
        SU_password = new javax.swing.JPasswordField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        SU_Lastname = new javax.swing.JTextField();
        SU_username = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        SU_retypepassword = new javax.swing.JPasswordField();
        jLabel23 = new javax.swing.JLabel();
        SU_address = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        SU_userid = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(0, 153, 204));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Sign-Up");

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

        SU_okbutton.setBackground(new java.awt.Color(0, 102, 153));
        SU_okbutton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        SU_okbutton.setForeground(new java.awt.Color(255, 255, 255));
        SU_okbutton.setText("OK");
        SU_okbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SU_okbuttonActionPerformed(evt);
            }
        });

        SU_usertype.setForeground(new java.awt.Color(255, 255, 255));
        SU_usertype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<select user>", "Admin", "Cashier" }));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("First Name:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Password:");

        SU_firstname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SU_firstnameActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Last Name:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("User Type:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Username:");

        SU_Lastname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SU_LastnameActionPerformed(evt);
            }
        });

        SU_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SU_usernameActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Retype Pass:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Address:");

        SU_address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SU_addressActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("User ID:");

        SU_userid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SU_useridActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(SU_okbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SU_firstname, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel24))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SU_userid, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SU_Lastname, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SU_address, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SU_usertype, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SU_username, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SU_password, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SU_retypepassword, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(SU_firstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(SU_Lastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(SU_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(SU_usertype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(SU_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SU_userid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(SU_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(SU_retypepassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(SU_okbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
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
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SU_firstnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SU_firstnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SU_firstnameActionPerformed

    private void SU_okbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SU_okbuttonActionPerformed
       
        String password ="";
        boolean isUserAlreadyEXISTS = false;
       
        for(char a: SU_password.getPassword()){
            password = password + a;
        }
        String query = "INSERT INTO employee VALUES("+SU_userid.getText()+", TRIM('"+SU_firstname.getText()+"'), "+"TRIM('"+SU_Lastname.getText()+"'), "+"TRIM('"+SU_address.getText()+"'), "+"TRIM('"+(String)SU_usertype.getSelectedItem()+"'), TRIM('"+SU_username.getText()+"'), TRIM('"+password+"') )";
        
        if(getData==null){
            if(isUSERIDAlreadyExistsInTable()==false){
                EmployeeAddItemToTable(query);
                isUserAlreadyEXISTS = isUSERIDAlreadyExistsInTable();
            }else{
                JOptionPane.showMessageDialog(this,"The employee id you've entered already exists."+"\nPlease try again.");
                
                
            }
        }
        else{
            //add joptionpane if already exists;
            if(isUSERIDAlreadyExistsInTable()==false){
                EmployeeEditItemFromTable();
                isUserAlreadyEXISTS = isUSERIDAlreadyExistsInTable();
            }else{
                JOptionPane.showMessageDialog(this, "Can't edit employee details into entered values. \nEmployee's ID already exists.");
            }
            
            
        }
        
        if(getData!=null&&isUserAlreadyEXISTS==true){
        }else{
            this.dispose();
        }
        
    }//GEN-LAST:event_SU_okbuttonActionPerformed

    private void SU_LastnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SU_LastnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SU_LastnameActionPerformed

    private void SU_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SU_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SU_usernameActionPerformed

    private void SU_addressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SU_addressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SU_addressActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
       this.dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void SU_useridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SU_useridActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SU_useridActionPerformed

    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignUp().setVisible(true);
            }
        });
    }*/
    
    private GetData getData; 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField SU_Lastname;
    private javax.swing.JTextField SU_address;
    private javax.swing.JTextField SU_firstname;
    private javax.swing.JButton SU_okbutton;
    private javax.swing.JPasswordField SU_password;
    private javax.swing.JPasswordField SU_retypepassword;
    private javax.swing.JTextField SU_userid;
    private javax.swing.JTextField SU_username;
    private javax.swing.JComboBox<String> SU_usertype;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
