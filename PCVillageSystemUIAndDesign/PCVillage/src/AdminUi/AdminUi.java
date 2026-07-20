/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdminUi;



import SignIn_SignUp.Add_Item;
import SignIn_SignUp.SignIn;
import SignIn_SignUp.SignUp;
import SignIn_SignUp.Prompt_low;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author aries
 */
public class AdminUi extends javax.swing.JFrame {

    /**
     * Creates new form MainUi
     */
    public AdminUi(GetUser getuser) {
        getUser = getuser;
         
        initComponents();   
        
        if(getUser!=null){
            
            if(getUser.getUserType().equals("Cashier")){
                jPanel5.remove(jPanel_Emp);
                jPanel5.remove(jPanel_Sales);
                jPanel5.remove(jPanel_Inventory);
            }
            setFrameNameAndID();
        }
        
        promptUserIfLowOnStock();
        setPosTableAndPosTransactionTableColumIDWidthZERO();
        this.setLocationRelativeTo(null);// center form
        scaleImage_POSLabel();
        scaleImage_InventoryLabel();
        scaleImage_SalesLabel();
        scaleImage_EmployeeLabel();
        scaleImage_LogoLabel();
        scaleImage_LogoutLabel();
        scaleImage_IconUserLabel();
        
        ShowInventoryDataTable();
        InventorySetComboBox();
        
        showEmployeeDataTable();
        EmployeeSetComboBox();
       
        showPosDataTable();
        PosSetComboBox();
        
        showSalesDataTable();
        SalesSetComboBox();
       
    }
    
//------------------------------------
//GROUP OF GENERAL USE METHODS
//------------------------------------
    
    public void setFrameNameAndID(){
        
        FrameNameOfUser.setText(getUser.getUsername());
        FrameIdOfUser.setText("#"+String.valueOf(getUser.getUserID()));
    }
    
    public Connection getConnection(String str){
        Connection con;
        try{
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.2:3306/"+str, "root", "Ab12Cd34Ef56");
            return con;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public ArrayList<GetData> getDataTableList(String query,String fromTable){
        ArrayList<GetData> getlist = new ArrayList<GetData>();
        
        Connection connection = getConnection("pcvillage");
        GetData getData;
        Statement st;
        ResultSet rs;   
        
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query); 
            while(rs.next()){
                
                 
                getData = new GetData();
                
                if(fromTable.equals("inventory"))
                    getData.setInventoryData(rs.getInt("product_id"),rs.getString("product_type"),rs.getString("product_brand"),rs.getString("product_model"),rs.getInt("product_purchase_price"),rs.getInt("product_selling_price"),rs.getInt("product_quantity"),rs.getString("product_last_changed_date"));
                
                if(fromTable.equals("employee"))
                    getData.setEmployeeData(rs.getInt("emp_id"), rs.getString("emp_first_name"), rs.getString("emp_last_name"), rs.getString("emp_address"), rs.getString("emp_userType"), rs.getString("emp_username"), rs.getString("emp_password"));
                
                if(fromTable.equals("sales"))
                    getData.setSalesData(rs.getInt("sales_id"), rs.getInt("product_id"), rs.getInt("cashier_id"),rs.getInt("sales_product_price"), rs.getInt("sales_quantity_sold"), rs.getInt("sales_total_price"), rs.getString("sales_date_of_purchase"),rs.getString("product_type"),rs.getString("product_brand"),rs.getString("product_model"),rs.getInt("product_purchase_price"));
                
                if(fromTable.equals("Pos"))
                    getData.setPosData(rs.getInt("product_id"),rs.getString("product_type"),rs.getString("product_brand"),rs.getString("product_model"),rs.getInt("product_purchase_price"),rs.getInt("product_selling_price"),rs.getInt("product_quantity"),rs.getString("product_last_changed_date"));
                
                getlist.add(getData);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminUi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return getlist; 
           
    }
    
    public void ShowDataToTable(ArrayList<GetData> list, String fromTable){
        
        
        DefaultTableModel model = null;
        if(fromTable.equals("inventory"))
            model = (DefaultTableModel)InventoryTable.getModel();
        
        else if(fromTable.equals("employee"))
            model = (DefaultTableModel)EmployeeTable.getModel();
        
        else if(fromTable.equals("Pos"))
            model = (DefaultTableModel) PosTable.getModel();
        
        else if(fromTable.equals("Sales"))
            model = (DefaultTableModel)SalesTable.getModel();
        
        if(!fromTable.equals("empty")){
            if(fromTable.equals("inventory")){
                Object [] row = new Object[8];
                for(int i=0;i<list.size();i++){
                    row[0] = list.get(i).getInventoryPID();
                    row[1] = list.get(i).getInventoryPType();
                    row[2] = list.get(i).getInventoryPBrand();
                    row[3] = list.get(i).getInventoryPModel();
                    row[4] = list.get(i).getInventoryPPurchasePrice();
                    row[5] = list.get(i).getInventoryPSellingPrice();
                    row[6] = list.get(i).getInventoryPQuantity();
                    row[7] = list.get(i).getInventoryDateChanged();
                    model.addRow(row);
                }
            }
            
            else if(fromTable.equals("employee")){
                Object [] row = new Object[7];
                for(int i=0;i<list.size();i++){
                    row[0] = list.get(i).getEmployeeID();
                    row[1] = list.get(i).getEmployeeFirstName();
                    row[2] = list.get(i).getEmployeeLastName();
                    row[3] = list.get(i).getEmployeeAddress();
                    row[4] = list.get(i).getEmployeeUserType();
                    row[5] = list.get(i).getEmployeeUsername();
                    row[6] = list.get(i).getEmployeePassword();
                    model.addRow(row);
                }
            }
          
        
        else if(fromTable.equals("Pos")){
                Object [] row = new Object[6];
                
                for(int i=0;i<list.size();i++){
                    row[0] = list.get(i).getPosPType();
                    row[1] = list.get(i).getPosPBrand();
                    row[2] = list.get(i).getPosPModel();
                    row[3] = list.get(i).getPosPSellingPrice();
                    row[4] = list.get(i).getPosPQuantity();
                    row[5] = list.get(i).getPosPID();
                    model.addRow(row);
                }
            }
            
             
            
            if(fromTable.equals("Sales")){
                Object [] row = new Object[10];
                for(int i=0;i<list.size();i++){
                    row[0] = list.get(i).getSalesID();
                    row[1] = list.get(i).getSalesCashierID();
                    row[2] = list.get(i).getInventoryPType();
                    row[3] = list.get(i).getInventoryPBrand();
                    row[4] = list.get(i).getInventoryPModel();
                    row[5] = list.get(i).getInventoryPPurchasePrice();
                    row[6] = list.get(i).getSalesProductPrice();
                    row[7] = list.get(i).getSalesProductQuantity();
                    row[8] = list.get(i).getSalesTotalPrice();
                    row[9] = list.get(i).getSalesDateOfPurchase();
                    
                    model.addRow(row);
                }
            }
            
            
        }
    }
    
    public void RemoveDataFromTableAt(int index,String fromTable){
        DefaultTableModel model = null;
        
        if(fromTable.equals("inventory"))
            model = (DefaultTableModel)InventoryTable.getModel();
        else if(fromTable.equals("employee"))
            model = (DefaultTableModel)EmployeeTable.getModel();
        else if(fromTable.equals("Pos"))
            model = (DefaultTableModel)PosTable.getModel();
        else if(fromTable.equals("sales"))
            model = (DefaultTableModel)SalesTable.getModel();
        
        
        if(index==-1){
           
            
            if(model.getRowCount()>0)
            while(true){
                model.removeRow(0);
                if(model.getRowCount() == 0)
                    break;
            }
        }
        else{
            model.removeRow(index);
        }
    }
    
    public void promptUserIfLowOnStock(){
        Statement st; 
        ResultSet rs;       
        String query = "SELECT* FROM inventory WHERE product_quantity<20 ORDER BY product_id";
        try {
            st=getConnection("pcvillage").createStatement();
            rs = st.executeQuery(query);
           
            if(rs.next())
                LowOnStock.setVisible(true);
            else
                LowOnStock.setVisible(false);
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminUi.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
//------------------------------------
//END OF GROUP OF GENERAL USE METHODS
//------------------------------------
    
    
    
    
    
    
    
//------------------------------------
//GROUP OF CODES HANDLING INVENTORY TAB
//------------------------------------
    public ArrayList<GetData> getInventoryList(){
        String query = "SELECT * FROM inventory ORDER BY product_id";
        return getDataTableList(query,"inventory");
    }
    
    public ArrayList<GetData> getInventoryList(String query){
        return getDataTableList(query,"inventory");
    }
    
    
    public void ShowInventoryDataTable(){
        ShowDataToTable(getInventoryList(),"inventory");
    }
    public void ShowInventoryDataTable(ArrayList<GetData> list){
        ShowDataToTable(list,"inventory");
    }
    
    
    
    
    public void RemoveAllInventoryDataTable(){
        RemoveDataFromTableAt(-1,"inventory");
    }
    
    public void RemoveInventoryDataTableAt(int index){
        RemoveDataFromTableAt(index,"inventory");
    }
    
    
    public void InventoryDeleteSelectedRowFromDB(int index){
        DefaultTableModel model = (DefaultTableModel) InventoryTable.getModel();
        String query = "DELETE FROM inventory WHERE product_id = " + model.getValueAt(index, 0);
        
        try {
            Statement st = getConnection("pcvillage").createStatement();
            st.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(AdminUi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    //This method is currently not used----!!!!
    public void AddItemtoInventory(String query) throws SQLException{
        Connection connection = getConnection("pcvillage");
        Statement st = connection.createStatement(); 
        st.execute(query);
        st.close();
    }
    //This method is currently not used----!!!!
    
    
    public void InventorySetComboBox(){
      
        String query = "SELECT* FROM inventory";
        Statement st;
        ResultSet rs;
        int i;
        try {
            
            
            st = getConnection("pcvillage").createStatement();
            rs = st.executeQuery(query);
            
            while(rs.next()){
                
                for(i=0;i<InventoryComboBoxType.getItemCount();i++)
                    if(InventoryComboBoxType.getItemAt(i).equals(rs.getString("product_type")))
                        break;
                if(i==InventoryComboBoxType.getItemCount())
                    InventoryComboBoxType.addItem(rs.getString("product_type"));
                
                
                for(i=0;i<InventoryComboBoxBrand.getItemCount();i++)
                    if(InventoryComboBoxBrand.getItemAt(i).equals(rs.getString("product_brand")))
                        break;
                if(i==InventoryComboBoxBrand.getItemCount())
                    InventoryComboBoxBrand.addItem(rs.getString("product_brand"));
                
                
                for(i=0;i<InventoryComboBoxModel.getItemCount();i++)
                    if(InventoryComboBoxModel.getItemAt(i).equals(rs.getString("product_model")))
                        break;
                if(i==InventoryComboBoxModel.getItemCount())
                    InventoryComboBoxModel.addItem(rs.getString("product_model"));
                
                
                for(i=0;i<InventoryComboBoxPurchasePrice.getItemCount();i++)
                    if(InventoryComboBoxPurchasePrice.getItemAt(i).equals(rs.getString("product_purchase_price")))
                        break;
                if(i==InventoryComboBoxPurchasePrice.getItemCount())
                    InventoryComboBoxPurchasePrice.addItem(rs.getString("product_purchase_price"));
                
                
                for(i=0;i<InventoryComboBoxSellingPrice.getItemCount();i++)
                    if(InventoryComboBoxSellingPrice.getItemAt(i).equals(rs.getString("product_selling_price")))
                        break;
                if(i==InventoryComboBoxSellingPrice.getItemCount())
                    InventoryComboBoxSellingPrice.addItem(rs.getString("product_selling_price"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminUi.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void InventoryClearAllComboBox(){
        InventoryComboBoxType.removeAllItems();
        InventoryComboBoxType.addItem("<select product type>");
        
        InventoryComboBoxBrand.removeAllItems();
        InventoryComboBoxBrand.addItem("<select product brand>");
        
        InventoryComboBoxModel.removeAllItems();
        InventoryComboBoxModel.addItem("<select product model>");
        
        InventoryComboBoxPurchasePrice.removeAllItems();
        InventoryComboBoxPurchasePrice.addItem("<select purchase price>");
        
        InventoryComboBoxSellingPrice.removeAllItems();
        InventoryComboBoxSellingPrice.addItem("<select selling price>");
    }
    

    
    public void SearchInventory(){
        int type_isEmpty = 0;
        int brand_isEmpty = 0;
        int model_isEmpty = 0;
        int purchase_isEmpty = 0;
        int selling_isEmpty = 0;
        int quantity_isEmpty = 0;
        int date_isEmpty = 0;
        
        String type = "product_type = " + "'"+(String) InventoryComboBoxType.getSelectedItem()+"'";
        String brand = "product_brand = "+"'"+(String) InventoryComboBoxBrand.getSelectedItem()+"'";         
        String model = "product_model = " +"'"+(String) InventoryComboBoxModel.getSelectedItem()+"'";
        String purchase_price = "product_purchase_price = " + (String) InventoryComboBoxPurchasePrice.getSelectedItem();
        String selling_price = "product_selling_price = " + (String) InventoryComboBoxSellingPrice.getSelectedItem(); //+ " AND ";
        String quantity = "product_quantity <= " + InventoryQuantityTextField.getText();
        String dateChanged;
        try{
            date = InventoryDateChooserDate.getDate();
            sdf = new SimpleDateFormat("MM-dd-yyyy");
            dateChanged = "product_last_changed_date = " + "'"+sdf.format(date)+"'";
        }catch(Exception e){
            dateChanged = null;
        }
        
        if(((String)InventoryComboBoxType.getSelectedItem()).equals("<select product type>")){
            type = " ";
            type_isEmpty = 1;
        }
        if(((String) InventoryComboBoxBrand.getSelectedItem()).equals("<select product brand>")){
            brand = " ";
            brand_isEmpty = 1;
        }
        
        if(((String) InventoryComboBoxModel.getSelectedItem()).equals("<select product model>")){
            model = " ";
            model_isEmpty = 1;
        }
        if(((String) InventoryComboBoxPurchasePrice.getSelectedItem()).equals("<select purchase price>")){
            purchase_price = " ";
            purchase_isEmpty = 1;
        }
        if(((String) InventoryComboBoxSellingPrice.getSelectedItem()).equals("<select selling price>")){
            selling_price = " ";
            selling_isEmpty = 1;
        }
        if(quantity.equals("product_quantity <= ")){
            quantity = " ";
            quantity_isEmpty = 1;
        }
        
        if(dateChanged == null){
            dateChanged = " ";
            date_isEmpty = 1; 
        }
        
        if(type_isEmpty==0&&(brand_isEmpty==0||model_isEmpty==0||purchase_isEmpty==0||selling_isEmpty==0||quantity_isEmpty==0||date_isEmpty==0))
            type = type + " AND ";
        if(brand_isEmpty==0&&(model_isEmpty==0||purchase_isEmpty==0||selling_isEmpty==0||quantity_isEmpty==0||date_isEmpty==0))
            brand = brand + " AND ";
        if(model_isEmpty==0&&(purchase_isEmpty==0||selling_isEmpty==0||quantity_isEmpty==0||date_isEmpty==0))
            model = model + " AND ";
        if(purchase_isEmpty==0&&(selling_isEmpty==0||quantity_isEmpty==0||date_isEmpty==0))
            purchase_price = purchase_price + " AND ";
        if(selling_isEmpty==0&&(quantity_isEmpty==0||date_isEmpty==0))
            selling_price = selling_price + " AND ";
        if(quantity_isEmpty==0&&date_isEmpty==0)
            quantity = quantity + " AND ";
        
        String query = "SELECT* FROM inventory WHERE "+type+brand+model+purchase_price+selling_price+quantity+dateChanged+" ORDER by product_id";
        
        if(type_isEmpty==1&&brand_isEmpty==1&&model_isEmpty==1&&purchase_isEmpty==1&&selling_isEmpty==1&&quantity_isEmpty==1&&date_isEmpty==1){
            RemoveAllInventoryDataTable();
            ShowInventoryDataTable();
        }
        else{
            RemoveAllInventoryDataTable();
            ShowInventoryDataTable(getInventoryList(query));
        }
        
    }
    
    //This method is currently not used---------!!!!!
    public void InventoryAddItemToTable(){
        DefaultTableModel model = (DefaultTableModel)InventoryTable.getModel();
        String lastID = null;
        
        if(model.getRowCount()!=0){
            lastID = "WHERE product_id >" + model.getValueAt(model.getRowCount()-1, 0);
        }else if(model.getRowCount()==0){
            lastID = " ";
        }
        String query = "SELECT * FROM inventory "+lastID+" ORDER BY product_id";
        Statement st;
        
        try {
                st = getConnection("pcvillage").createStatement();
                ResultSet rs = st.executeQuery(query);
                
            Object[] row = new Object[8];
            while(rs.next()){
                row[0] = rs.getInt("product_id");
                row[1] = rs.getString("product_type");
                row[2] = rs.getString("product_brand");
                row[3] = rs.getString("product_model");
                row[4] = rs.getInt("product_purchase_price");
                row[5] = rs.getInt("product_selling_price");
                row[6] = rs.getInt("product_quantity");
                row[7] = rs.getString("product_last_changed_date");
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminUi.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    //Method above is currently not used---------!!!!!
    
    
//--------------------------------------------
//END OF GROUP OF CODES HANDLING INVENTORY TAB
//--------------------------------------------




//------------------------------------
//GROUP OF CODES HANDLING EMPLOYEE TAB
//------------------------------------    

    public ArrayList<GetData> getEmployeeList(){
        String query = "SELECT* FROM employee ORDER by emp_id";
        return getDataTableList(query, "employee");
    }
    
    public ArrayList<GetData> getEmployeeList(String query){
        return getDataTableList(query,"employee");
    }

    public void showEmployeeDataTable(){
        ShowDataToTable(getEmployeeList(), "employee");
        
    }
    
    public void showEmployeeDataTable(ArrayList<GetData> list){
        ShowDataToTable(list,"employee");
    }

    public void EmployeeSetComboBox(){
        String query = "SELECT* FROM employee";
            Statement st;
            ResultSet rs;
            int i;
            try {
                st = getConnection("pcvillage").createStatement();
                rs = st.executeQuery(query);
            
                while(rs.next()){
                
                    for(i=0;i<EmployeeUserTypeComboBox.getItemCount();i++)
                        if(EmployeeUserTypeComboBox.getItemAt(i).equals(rs.getString("emp_userType")))
                            break;
                    if(i==EmployeeUserTypeComboBox.getItemCount())
                        EmployeeUserTypeComboBox.addItem(rs.getString("emp_userType"));
                    
                
                    for(i=0;i<EmployeeFirstNameComboBox.getItemCount();i++)
                        if(EmployeeFirstNameComboBox.getItemAt(i).equals(rs.getString("emp_first_name")))
                            break;
                    if(i==EmployeeFirstNameComboBox.getItemCount())
                        EmployeeFirstNameComboBox.addItem(rs.getString("emp_first_name"));
                    
                    
                    for(i=0;i<EmployeeLastNameComboBox.getItemCount();i++)
                        if(EmployeeLastNameComboBox.getItemAt(i).equals(rs.getString("emp_last_name")))
                            break;
                    if(i==EmployeeLastNameComboBox.getItemCount())
                        EmployeeLastNameComboBox.addItem(rs.getString("emp_last_name"));
                    
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminUi.class.getName()).log(Level.SEVERE, null, ex);
            } 
    }
    
    public void EmployeeClearAllComboBox(){
        EmployeeUserTypeComboBox.removeAllItems();
        EmployeeUserTypeComboBox.addItem("<select user type>");
        
        EmployeeFirstNameComboBox.removeAllItems();
        EmployeeFirstNameComboBox.addItem("<select name of employee>");
        
        EmployeeLastNameComboBox.removeAllItems();
        EmployeeLastNameComboBox.addItem("<select name of employee>");
    }
    
    public void RemoveAllEmployeeDataTable(){
        RemoveDataFromTableAt(-1,"employee");
    }
    
    
            
    public void RemoveEmployeeDataTableAt(int index){
        RemoveDataFromTableAt(index,"employee");
    }
            
    
    public void EmployeeDeleteSelectedRowFromDB(int index){
        DefaultTableModel model = (DefaultTableModel) EmployeeTable.getModel();
        String query = "DELETE FROM employee WHERE emp_id = " + model.getValueAt(index, 0);
        
        try {
            Statement st = getConnection("pcvillage").createStatement();
            st.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(AdminUi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void searchEmployee(){
        int usertype_isEmpty = 0;
        int firstname_isEmpty = 0;
        int lastname_isEmpty = 0;
        String usertype = "emp_userType = " + "'"+(String) EmployeeUserTypeComboBox.getSelectedItem()+"'";
        String firstname = "emp_first_name = "+"'"+(String) EmployeeFirstNameComboBox.getSelectedItem()+"'";         
        String lastname = "emp_last_name = "+"'"+(String) EmployeeLastNameComboBox.getSelectedItem()+"'";         
        
        if(((String)EmployeeUserTypeComboBox.getSelectedItem()).equals("<select user type>")){
            usertype = " ";
            usertype_isEmpty = 1;
        }
        if(((String) EmployeeFirstNameComboBox.getSelectedItem()).equals("<select name of employee>")){
            firstname = " ";
            firstname_isEmpty = 1;
        }
        if(((String) EmployeeLastNameComboBox.getSelectedItem()).equals("<select name of employee>")){
            lastname = " ";
            lastname_isEmpty = 1;
        }
        
        
        
        if(usertype_isEmpty==0&&(firstname_isEmpty==0||lastname_isEmpty==0))
            usertype = usertype + " AND ";
        if(firstname_isEmpty==0&&lastname_isEmpty==0)
            firstname = firstname + " AND ";
        
        String query = "SELECT* FROM employee WHERE "+usertype+firstname+lastname+" ORDER by emp_id";
        
        if(usertype_isEmpty==1&&firstname_isEmpty==1&&lastname_isEmpty == 1){
            RemoveAllEmployeeDataTable();
            showEmployeeDataTable();
        }
        else{
            RemoveAllEmployeeDataTable();
            showEmployeeDataTable(getEmployeeList(query));
        }
        
    }


//-------------------------------------------
//END OF GROUP OF CODES HANDLING EMPLOYEE TAB
//-------------------------------------------





//--------------------------------
//GROUP OF CODES HANDLING POS TAB
//--------------------------------

    public void setPosTableAndPosTransactionTableColumIDWidthZERO(){
        PosTable.getColumnModel().getColumn(5).setMinWidth(0);
        PosTable.getColumnModel().getColumn(5).setMaxWidth(0);
        PosTable.getColumnModel().getColumn(5).setWidth(0);
        
        PosTransactionTable.getColumnModel().getColumn(6).setMinWidth(0);
        PosTransactionTable.getColumnModel().getColumn(6).setMaxWidth(0);
        PosTransactionTable.getColumnModel().getColumn(6).setWidth(0);
    }
    
    public ArrayList<GetData> getPosList(){
        String query = "SELECT* FROM inventory ORDER by product_id";
        return getDataTableList(query, "Pos");
    }
    
    public ArrayList<GetData> getPosList(String query){
        return getDataTableList(query,"Pos");
    }

    public void showPosDataTable(){
        ArrayList<GetData> list = getPosList();
        PosListRemoveDuplicateDifferentPrice(list);
        ShowDataToTable(list, "Pos");
        
    }
    
    public void showPosDataTable(ArrayList<GetData> list){
        ShowDataToTable(list,"Pos");
    }
    
    public void PosListRemoveDuplicateDifferentPrice(ArrayList<GetData> list){
        
        try {
            Statement st = getConnection("pcvillage").createStatement();
            ResultSet rs;
            for(int i=0;i<list.size();i++){
                String query = " SELECT COUNT(*) FROM inventory WHERE product_type = '"+ list.get(i).getInventoryPType() 
                                +"' AND product_brand = '"+list.get(i).getInventoryPBrand()
                                +"' AND product_model = '"+list.get(i).getInventoryPModel()+"'";
                
                rs = st.executeQuery(query);
                rs.next();
                
                if(rs.getInt("COUNT(*)")>1){
                    for(int j=0;j<list.size();j++){
                        if(list.get(i).getInventoryPType().equals(list.get(j).getInventoryPType())&&
                           list.get(i).getInventoryPBrand().equals(list.get(j).getInventoryPBrand())&&
                           list.get(i).getInventoryPModel().equals(list.get(j).getInventoryPModel())&&     
                           i!=j){
                           list.remove(j);
                            
                        }
                            
                            
                    }
                
                }
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminUi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void PosSetComboBox(){
        String query = "SELECT* FROM inventory";
            Statement st;
            ResultSet rs;
            int i;
            try {
                st = getConnection("pcvillage").createStatement();
                rs = st.executeQuery(query);
            
                while(rs.next()){
                
                    for(i=0;i<PosTypeComboBox.getItemCount();i++)
                        if(PosTypeComboBox.getItemAt(i).equals(rs.getString("product_type")))
                            break;
                    if(i==PosTypeComboBox.getItemCount())
                        PosTypeComboBox.addItem(rs.getString("product_type"));
                    
                
                    for(i=0;i<PosBrandComboBox.getItemCount();i++)
                        if(PosBrandComboBox.getItemAt(i).equals(rs.getString("product_brand")))
                            break;
                    if(i==PosBrandComboBox.getItemCount())
                        PosBrandComboBox.addItem(rs.getString("product_brand"));
                    
                    
                    for(i=0;i<PosModelComboBox.getItemCount();i++)
                        if(PosModelComboBox.getItemAt(i).equals(rs.getString("product_model")))
                            break;
                    if(i==PosModelComboBox.getItemCount())
                        PosModelComboBox.addItem(rs.getString("product_model"));
                    
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminUi.class.getName()).log(Level.SEVERE, null, ex);
            } 
    }
    
    public void PosClearAllComboBox(){
        PosTypeComboBox.removeAllItems();
        PosTypeComboBox.addItem("<select product type>");
        
        PosBrandComboBox.removeAllItems();
        PosBrandComboBox.addItem("<select product brand>");
        
        PosModelComboBox.removeAllItems();
        PosModelComboBox.addItem("<select product model>");
    }
    
    public void RemoveAllPosDataTable(){
        RemoveDataFromTableAt(-1,"Pos");
    }
    
    public void SearchPos(){
        int type_isEmpty = 0;
        int brand_isEmpty = 0;
        int model_isEmpty = 0;
        
        String type = "product_type = " + "'"+(String) PosTypeComboBox.getSelectedItem()+"'";
        String brand = "product_brand = "+"'"+(String) PosBrandComboBox.getSelectedItem()+"'";         
        String model = "product_model = " +"'"+(String) PosModelComboBox.getSelectedItem()+"'";
        
        
        
        if(((String)PosTypeComboBox.getSelectedItem()).equals("<select product type>")){
            type = " ";
            type_isEmpty = 1;
        }
        if(((String) PosBrandComboBox.getSelectedItem()).equals("<select product brand>")){
            brand = " ";
            brand_isEmpty = 1;
        }
        
        if(((String) PosModelComboBox.getSelectedItem()).equals("<select product model>")){
            model = " ";
            model_isEmpty = 1;
        }
        
        
        if(type_isEmpty==0&&(brand_isEmpty==0||model_isEmpty==0))
            type = type + " AND ";
        if(brand_isEmpty==0&&model_isEmpty==0)
            brand = brand + " AND ";
        
        String query = "SELECT* FROM inventory WHERE "+type+brand+model+" ORDER by product_id";
        
        if(type_isEmpty==1&&brand_isEmpty==1&&model_isEmpty==1){
            RemoveAllPosDataTable();
            showPosDataTable();
        }
        else{
            
            RemoveAllPosDataTable();
            showPosDataTable(getPosList(query));
        }
        
    }
    
    public void PosAddtoTransactionTable(){
        DefaultTableModel PosModel = (DefaultTableModel) PosTable.getModel();
        DefaultTableModel model = (DefaultTableModel) PosTransactionTable.getModel();
        int quantity = Integer.parseInt(PosQuantityJtext.getText());
        int price=(int)PosModel.getValueAt(PosTable.getSelectedRow(),3);
        int total;
        total = quantity*price;
        
        if(PosTransactionTable.getRowCount()!=0)
            for(int i=0;i<PosTransactionTable.getRowCount();i++){
                if(PosModel.getValueAt(PosTable.getSelectedRow(),0).equals(model.getValueAt(i,0))&&
                   PosModel.getValueAt(PosTable.getSelectedRow(),1).equals(model.getValueAt(i,1))&&
                   PosModel.getValueAt(PosTable.getSelectedRow(),2).equals(model.getValueAt(i,2))){
                    JOptionPane.showMessageDialog(this, "The item is already in the cart. \nIf you want to add more of the \nsame item simply edit\nthe quantity column.");
                    return;
                    
                }
            
                        
            }
        
        
        Object[] row = new Object[7];
        
        row[0] = PosModel.getValueAt(PosTable.getSelectedRow(),0);
        row[1] = PosModel.getValueAt(PosTable.getSelectedRow(),1);
        row[2] = PosModel.getValueAt(PosTable.getSelectedRow(),2);
        row[3] = PosModel.getValueAt(PosTable.getSelectedRow(),3);
        row[4] = PosQuantityJtext.getText();
        row[5] = total;
        row[6] = PosModel.getValueAt(PosTable.getSelectedRow(),5);
        
        model.addRow(row);
           
    }
    
    public void PosRemoveAllTransactionDataTable(){
        DefaultTableModel model = (DefaultTableModel) PosTransactionTable.getModel();
        
        while(true){
            model.removeRow(0);
            if(PosTransactionTable.getRowCount()==0)
                break;
        }
    }
    
    public void PosTransactionQuantityChanged(){
        DefaultTableModel PosModel = (DefaultTableModel) PosTable.getModel();
        DefaultTableModel model = (DefaultTableModel) PosTransactionTable.getModel();
        int quantity = Integer.parseInt((String)model.getValueAt(PosTransactionTable.getSelectedRow(),4));
        int price=(int)PosModel.getValueAt(PosTable.getSelectedRow(),3);
        int total;
        
        total = quantity*price;
        
        model.setValueAt(total, PosTransactionTable.getSelectedRow(), 5);
        
        
           
    }
    
    
    
    public void PosTotalPriceofAllItems(){
        DefaultTableModel model= (DefaultTableModel) PosTransactionTable.getModel();
        int totalprice = 0;
        
        for(int i=0;i<PosTransactionTable.getRowCount();i++){
           totalprice = totalprice + (int)model.getValueAt(i,5);
        }
        
        PosTotPriceJlabel.setText("PHP "+String.valueOf(totalprice));
        
    }
    
    public void PosCalculateChange(){
        String totalPrice = PosTotPriceJlabel.getText().replaceAll("PHP ","");
        int total = Integer.parseInt(totalPrice);
        int payment = Integer.parseInt(PosPaymentJtext.getText());
        int change = payment - total;
        
        PosChangeJlabel.setText("PHP "+String.valueOf(change));
    }
    
    public void PosUpdateInventoryProductQuantity(){
        int quantitySold;
        int newquantity; 
        DefaultTableModel model = (DefaultTableModel) PosTransactionTable.getModel();
        Statement st;
        ResultSet rs; 
        date = new Date();
        sdf = new SimpleDateFormat("MM-dd-yyyy");
        
        
        try {
            st = getConnection("pcvillage").createStatement();
            for(int i=0;i<PosTransactionTable.getRowCount();i++){
                
                String query = "SELECT product_quantity,product_purchase_price FROM inventory WHERE product_type = '"+(String)model.getValueAt(i,0)+
                        "' AND product_brand = '"+(String)model.getValueAt(i,1)+"' AND product_model = '"+(String)model.getValueAt(i,2)+"' ORDER BY product_id ASC";
                quantitySold = Integer.parseInt((String)model.getValueAt(i,4));
                rs = st.executeQuery(query);
                rs.next();
                
                newquantity = rs.getInt("product_quantity") - quantitySold;
                
                if(newquantity<0){
                    JOptionPane.showMessageDialog(this, "The quantity set exceeds the quantity \nof item available in stock.");
                    quantity = newquantity;
                    return;
                }
                
                query = "UPDATE inventory SET product_quantity = "+newquantity+", product_last_changed_date = '"+sdf.format(date)+"' WHERE product_type = '"+model.getValueAt(i,0)+
                        "' AND product_brand = '"+model.getValueAt(i,1)+"' AND product_model = '"+model.getValueAt(i,2)+"' AND product_selling_price = "+model.getValueAt(i,3)+
                        " AND product_purchase_price = "+rs.getInt("product_purchase_price")+" ORDER BY product_id ASC";
                
                st.execute(query);
                    
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminUi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
    
//---------------------------------------
//END OF GROUP OF CODES HANDLING POS TAB
//---------------------------------------




//------------------------------------
//GROUP OF CODES HANDLING SALES TAB
//------------------------------------

    public ArrayList<GetData> getSalesList(){
        String query = "SELECT sales.*, inventory.product_type,inventory.product_brand,"
                + "inventory.product_model,inventory.product_purchase_price"
                + " FROM sales JOIN inventory ON sales.product_id=inventory.product_id ORDER BY sales_id";
        
        return getDataTableList(query, "sales");
    }
    //used for searching
    public ArrayList<GetData> getSalesList(String query){
        return getDataTableList(query,"sales");
    }

    public void showSalesDataTable(){
        ShowDataToTable(getSalesList(), "Sales");
        
    }
    //used for searching
    public void showSalesDataTable(ArrayList<GetData> list){
        ShowDataToTable(list,"Sales");
    }

    public void SalesSetComboBox(){
        String query = "SELECT sales.*, inventory.product_type,inventory.product_brand,"
                + "inventory.product_model,inventory.product_purchase_price"
                + " FROM sales JOIN inventory ON sales.product_id=inventory.product_id ORDER BY sales_id";
            Statement st;
            ResultSet rs;
            int i;
            try {
                st = getConnection("pcvillage").createStatement();
                rs = st.executeQuery(query);
            
                while(rs.next()){
                
                    for(i=0;i<SalesTypeComboBox.getItemCount();i++)
                        if(SalesTypeComboBox.getItemAt(i).equals(rs.getString("product_type")))
                            break;
                    if(i==SalesTypeComboBox.getItemCount())
                        SalesTypeComboBox.addItem(rs.getString("product_Type"));
                    
                
                    for(i=0;i<SalesBrandComboBox.getItemCount();i++)
                        if(SalesBrandComboBox.getItemAt(i).equals(rs.getString("product_brand")))
                            break;
                    if(i==SalesBrandComboBox.getItemCount())
                        SalesBrandComboBox.addItem(rs.getString("product_brand"));
                    
                    
                    for(i=0;i<SalesModelComboBox.getItemCount();i++)
                        if(SalesModelComboBox.getItemAt(i).equals(rs.getString("product_model")))
                            break;
                    if(i==SalesModelComboBox.getItemCount())
                        SalesModelComboBox.addItem(rs.getString("product_model"));
                    
                    for(i=0;i<SalesSellingPriceComboBox.getItemCount();i++)
                        if(SalesSellingPriceComboBox.getItemAt(i).equals(rs.getString("sales_product_price")))
                            break;
                    if(i==SalesSellingPriceComboBox.getItemCount())
                        SalesSellingPriceComboBox.addItem(rs.getString("sales_product_price"));
                    
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminUi.class.getName()).log(Level.SEVERE, null, ex);
            } 
    }
    
    public void SalesClearAllComboBox(){
        SalesTypeComboBox.removeAllItems();
        SalesTypeComboBox.addItem("<select product type>");
        
        SalesBrandComboBox.removeAllItems();
        SalesBrandComboBox.addItem("<select product brand>");
        
        SalesModelComboBox.removeAllItems();
        SalesModelComboBox.addItem("<select product model>");
        
        SalesSellingPriceComboBox.removeAllItems();
        SalesSellingPriceComboBox.addItem("<select product price>");
    }
    
    public void RemoveAllSalesDataTable(){
        RemoveDataFromTableAt(-1,"sales");
    }
    
    
    public void SalesAddTransactionToDB(){
        DefaultTableModel model = (DefaultTableModel) PosTransactionTable.getModel();
        date = new Date();
        sdf = new SimpleDateFormat("MM-dd-yyyy");
        Statement st;
        
        try {
            st = getConnection("pcvillage").createStatement();
            for(int i=0;i<PosTransactionTable.getRowCount();i++){
                String query = "INSERT INTO sales VALUES("
                    +"DEFAULT,"
                    +model.getValueAt(i,6)+","
                    +getUser.getUserID()+","
                    +model.getValueAt(i,3)+","
                    +model.getValueAt(i,4)+","
                    +model.getValueAt(i,5)+", '"
                    +sdf.format(date)+"')";
                st.execute(query);
                }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminUi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void SalesCalculateProfitMade(){
        DefaultTableModel model = (DefaultTableModel) SalesTable.getModel();
        int capital=0;
        int gross_income=0;
        int net_profit;
        Statement st;
        String query;
        
        for(int i=0;i<SalesTable.getRowCount();i++){
            int total = (int)model.getValueAt(i,5)*(int)model.getValueAt(i,7);
            capital = capital + total;
            
            gross_income = gross_income + (int)model.getValueAt(i,8);
        }
        
        net_profit = gross_income - capital;
        
        
        model.getValueAt(0,0);
        query = "UPDATE sales SET sales_profit = "+net_profit+" WHERE sales_id = "+model.getValueAt(0,0);
        SalesProfitJlabel.setText("PHP "+net_profit);
        
        try {
            st = getConnection("pcvillage").createStatement();
            st.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(AdminUi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
    public void SearchSales(){
        
        int type_isEmpty = 0;
        int brand_isEmpty = 0;
        int model_isEmpty = 0;
        int selling_isEmpty = 0;
        int quantity_isEmpty = 0;
        
        String type = "product_type = " + "'"+(String) SalesTypeComboBox.getSelectedItem()+"'";
        String brand = "product_brand = "+"'"+(String) SalesBrandComboBox.getSelectedItem()+"'";         
        String model = "product_model = " +"'"+(String) SalesModelComboBox.getSelectedItem()+"'";
        String selling_price = "sales_product_price = " + (String) SalesSellingPriceComboBox.getSelectedItem(); 
        String quantity = "sales_quantity_sold <= " + SalesQuantityJtext.getText();
        
        
        
        if(((String)SalesTypeComboBox.getSelectedItem()).equals("<select product type>")){
            type = " ";
            type_isEmpty = 1;
        }
        if(((String) SalesBrandComboBox.getSelectedItem()).equals("<select product brand>")){
            brand = " ";
            brand_isEmpty = 1;
        }
        
        if(((String) SalesModelComboBox.getSelectedItem()).equals("<select product model>")){
            model = " ";
            model_isEmpty = 1;
        }
        
        if(((String) SalesSellingPriceComboBox.getSelectedItem()).equals("<select product price>")){
            selling_price = " ";
            selling_isEmpty = 1;
        }
        if(quantity.equals("sales_quantity_sold <= ")){
            quantity = " ";
            quantity_isEmpty = 1;
        }
        
       
        
        if(type_isEmpty==0&&(brand_isEmpty==0||model_isEmpty==0||selling_isEmpty==0||quantity_isEmpty==0))
            type = type + " AND ";
        if(brand_isEmpty==0&&(model_isEmpty==0||selling_isEmpty==0||quantity_isEmpty==0))
            brand = brand + " AND ";
        if(model_isEmpty==0&&(selling_isEmpty==0||quantity_isEmpty==0))
            model = model + " AND ";
        if(selling_isEmpty==0&&quantity_isEmpty==0)
            selling_price = selling_price + " AND ";
       
        
        String query = "SELECT* FROM sales s JOIN inventory i ON s.product_id = i.product_id WHERE "
                        +type+brand+model+selling_price+quantity+" ORDER by sales_id";
        
        if(type_isEmpty==1&&brand_isEmpty==1&&model_isEmpty==1&&selling_isEmpty==1&&quantity_isEmpty==1){
            RemoveAllSalesDataTable();
            showSalesDataTable();
        }
        else{
            RemoveAllSalesDataTable();
            showSalesDataTable(getSalesList(query));
        }
        
    }
    
//-----------------------------------------
//END OF GROUP OF CODES HANDLING SALES TAB
//-----------------------------------------



    
    
    public DefaultTableModel getTableModel(String typeTable){
        if(typeTable.equals("inventory"))
            return (DefaultTableModel) InventoryTable.getModel();
        else if(typeTable.equals("employee"))
            return (DefaultTableModel) EmployeeTable.getModel();
        
        return null;
    }
    public JTable getTable(String typeTable){
        if(typeTable.equals("inventory"))
            return InventoryTable;
        else if(typeTable.equals("employee"))
            return EmployeeTable;
        
        return null;
    }
    
    
    
    public void scaleImage_POSLabel() {
        
        ImageIcon Pos_icon = new ImageIcon("PoS.png");
        //img scale
        Image img = Pos_icon.getImage();
        Image imgScale = img.getScaledInstance(60, 20, Image.SCALE_SMOOTH);
        ImageIcon i = new ImageIcon(imgScale);
        
        
        Pos_Label.setIcon(i); 
          
    }
    
    public void scaleImage_SalesLabel() {
        
        ImageIcon Inv_icon = new ImageIcon("Sales Report.png");
        //img scale
        Image img = Inv_icon.getImage();
        Image imgScale = img.getScaledInstance(70, 20, Image.SCALE_SMOOTH);
        ImageIcon i = new ImageIcon(imgScale);
         
        Sales_Label.setIcon(i);
         
    }
    
    public void scaleImage_InventoryLabel() {
        
        ImageIcon Inv_icon = new ImageIcon("Inventory.png");
        //img scale
        Image img = Inv_icon.getImage();
        Image imgScale = img.getScaledInstance(100, 20, Image.SCALE_SMOOTH);
        ImageIcon i = new ImageIcon(imgScale);

        Inventory_Label.setIcon(i);
         
    }
    
    public void scaleImage_EmployeeLabel() {
        ImageIcon Inv_icon = new ImageIcon("Employee.png");
        //img scale
        Image img = Inv_icon.getImage();
        Image imgScale = img.getScaledInstance(100, 20, Image.SCALE_SMOOTH);
        ImageIcon i = new ImageIcon(imgScale);
        
        Employee_Label.setIcon(i);
         
    }
    
    public void scaleImage_LogoLabel() {
        
        ImageIcon Inv_icon = new ImageIcon("Title.png");
        //img scale
        Image img = Inv_icon.getImage();
        Image imgScale = img.getScaledInstance(180, 50, Image.SCALE_SMOOTH);
        ImageIcon i = new ImageIcon(imgScale);
        
        Logo_Label.setIcon(i);
         
    }
     
    public void scaleImage_LogoutLabel() {
        
        ImageIcon Inv_icon = new ImageIcon("Logout Logo.png");
        //img scale
        Image img = Inv_icon.getImage();
        Image imgScale = img.getScaledInstance(70, 20, Image.SCALE_SMOOTH);
        ImageIcon i = new ImageIcon(imgScale);
        
        Logout_Label.setIcon(i);
         
    }
    
    public void scaleImage_IconUserLabel() {
        
        ImageIcon Inv_icon = new ImageIcon("Logo User.png");
        //img scale
        Image img = Inv_icon.getImage();
        Image imgScale = img.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ImageIcon i = new ImageIcon(imgScale);
        
        Icon_User.setIcon(i);
         
    }
     
    public void setColor(JPanel p){
        
        p.setBackground(new Color(0,131,175));
        
    }
    
    public void ResetColor(JPanel p1){
        
        p1.setBackground(new Color(0,153,204));
         
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        InventoryEditDeletePopUp = new javax.swing.JPopupMenu();
        InventoryEditSelected = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        InventoryDeleteSelected = new javax.swing.JMenuItem();
        EmployeeEditDeletePopUp = new javax.swing.JPopupMenu();
        EmployeeEditSelected = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        EmployeeDeleteSelected = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        TabSidePanel = new javax.swing.JPanel();
        Employee_jPanel = new javax.swing.JPanel();
        Employee_Label = new javax.swing.JLabel();
        Inventory_jPanel = new javax.swing.JPanel();
        Inventory_Label = new javax.swing.JLabel();
        Sales_jPanel = new javax.swing.JPanel();
        Sales_Label = new javax.swing.JLabel();
        Pos_jPanel = new javax.swing.JPanel();
        Pos_Label = new javax.swing.JLabel();
        Logout_jPanel = new javax.swing.JPanel();
        Logout_Label = new javax.swing.JLabel();
        LowOnStock = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel_Emp = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        EmployeeSearchButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        EmployeeTable = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        EmployeeAddUser = new javax.swing.JButton();
        EmployeeFirstNameComboBox = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        EmployeeRefreshButton = new javax.swing.JButton();
        EmployeeUserTypeComboBox = new javax.swing.JComboBox<>();
        EmployeeClearButton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        EmployeeLastNameComboBox = new javax.swing.JComboBox<>();
        jPanel_Inventory = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        InventoryTable = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        InventoryAddNewItem = new javax.swing.JButton();
        InventorySearchButton = new javax.swing.JButton();
        InventoryComboBoxSellingPrice = new javax.swing.JComboBox<>();
        InventoryComboBoxPurchasePrice = new javax.swing.JComboBox<>();
        InventoryComboBoxType = new javax.swing.JComboBox<>();
        InventoryComboBoxBrand = new javax.swing.JComboBox<>();
        InventoryClearButton = new javax.swing.JButton();
        InventoryPrintReportButton = new javax.swing.JButton();
        InventoryQuantityTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        InventoryComboBoxModel = new javax.swing.JComboBox<>();
        InventoryDateChooserDate = new com.toedter.calendar.JDateChooser();
        InventoryRefreshButton = new javax.swing.JButton();
        jPanel_Sales = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        SalesTable = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        SalesSearchButton = new javax.swing.JButton();
        SalesSellingPriceComboBox = new javax.swing.JComboBox<>();
        SalesTypeComboBox = new javax.swing.JComboBox<>();
        SalesBrandComboBox = new javax.swing.JComboBox<>();
        SalesModelComboBox = new javax.swing.JComboBox<>();
        SalesClearButton = new javax.swing.JButton();
        SalesReport = new javax.swing.JButton();
        SalesQuantityJtext = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        SalesProfitJlabel = new javax.swing.JLabel();
        jPanel_PoS = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        PosTable = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        PosTransactionTable = new javax.swing.JTable();
        jLabel28 = new javax.swing.JLabel();
        PosTotPriceJlabel = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        PosPaymentJtext = new javax.swing.JTextField();
        PosPayButton = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        PosChangeJlabel = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        PosTypeComboBox = new javax.swing.JComboBox<>();
        PosModelComboBox = new javax.swing.JComboBox<>();
        PosBrandComboBox = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        PosAddToCartButton = new javax.swing.JButton();
        PosSearchButton = new javax.swing.JButton();
        PosClearButton = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        PosQuantityJtext = new javax.swing.JTextField();
        PosTransactionClearButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        Logo_Label = new javax.swing.JLabel();
        Icon_User = new javax.swing.JLabel();
        FrameNameOfUser = new javax.swing.JLabel();
        FrameIdOfUser = new javax.swing.JLabel();
        FrameNameOfUser1 = new javax.swing.JLabel();
        FrameIdOfUser1 = new javax.swing.JLabel();

        InventoryEditDeletePopUp.setBackground(new java.awt.Color(0, 51, 102));
        InventoryEditDeletePopUp.setPreferredSize(new java.awt.Dimension(90, 82));

        InventoryEditSelected.setBackground(new java.awt.Color(19, 181, 235));
        InventoryEditSelected.setText("      Edit");
        InventoryEditSelected.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        InventoryEditSelected.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        InventoryEditSelected.setPreferredSize(new java.awt.Dimension(230, 22));
        InventoryEditSelected.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                InventoryEditSelectedMousePressed(evt);
            }
        });
        InventoryEditDeletePopUp.add(InventoryEditSelected);
        InventoryEditDeletePopUp.add(jSeparator1);

        InventoryDeleteSelected.setBackground(new java.awt.Color(19, 181, 235));
        InventoryDeleteSelected.setText("    Delete");
        InventoryDeleteSelected.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        InventoryDeleteSelected.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        InventoryDeleteSelected.setPreferredSize(new java.awt.Dimension(230, 22));
        InventoryDeleteSelected.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                InventoryDeleteSelectedMousePressed(evt);
            }
        });
        InventoryDeleteSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InventoryDeleteSelectedActionPerformed(evt);
            }
        });
        InventoryEditDeletePopUp.add(InventoryDeleteSelected);

        EmployeeEditDeletePopUp.setBackground(new java.awt.Color(0, 51, 102));
        EmployeeEditDeletePopUp.setPreferredSize(new java.awt.Dimension(90, 82));

        EmployeeEditSelected.setBackground(new java.awt.Color(19, 181, 235));
        EmployeeEditSelected.setText("      Edit");
        EmployeeEditSelected.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        EmployeeEditSelected.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        EmployeeEditSelected.setPreferredSize(new java.awt.Dimension(230, 22));
        EmployeeEditSelected.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EmployeeEditSelectedMousePressed(evt);
            }
        });
        EmployeeEditSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeEditSelectedActionPerformed(evt);
            }
        });
        EmployeeEditDeletePopUp.add(EmployeeEditSelected);
        EmployeeEditDeletePopUp.add(jSeparator2);

        EmployeeDeleteSelected.setBackground(new java.awt.Color(19, 181, 235));
        EmployeeDeleteSelected.setText("    Delete");
        EmployeeDeleteSelected.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        EmployeeDeleteSelected.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        EmployeeDeleteSelected.setPreferredSize(new java.awt.Dimension(230, 22));
        EmployeeDeleteSelected.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EmployeeDeleteSelectedMousePressed(evt);
            }
        });
        EmployeeDeleteSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeDeleteSelectedActionPerformed(evt);
            }
        });
        EmployeeEditDeletePopUp.add(EmployeeDeleteSelected);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 51, 102));

        TabSidePanel.setBackground(new java.awt.Color(0, 153, 204));

        Employee_jPanel.setBackground(new java.awt.Color(0, 153, 204));

        Employee_Label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Employee_Label.setForeground(new java.awt.Color(255, 255, 255));
        Employee_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Employee_Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Employee_LabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Employee_LabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Employee_LabelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout Employee_jPanelLayout = new javax.swing.GroupLayout(Employee_jPanel);
        Employee_jPanel.setLayout(Employee_jPanelLayout);
        Employee_jPanelLayout.setHorizontalGroup(
            Employee_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Employee_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Employee_jPanelLayout.setVerticalGroup(
            Employee_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Employee_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );

        Inventory_jPanel.setBackground(new java.awt.Color(0, 153, 204));

        Inventory_Label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Inventory_Label.setForeground(new java.awt.Color(255, 255, 255));
        Inventory_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Inventory_Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Inventory_LabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Inventory_LabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Inventory_LabelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout Inventory_jPanelLayout = new javax.swing.GroupLayout(Inventory_jPanel);
        Inventory_jPanel.setLayout(Inventory_jPanelLayout);
        Inventory_jPanelLayout.setHorizontalGroup(
            Inventory_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Inventory_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
        );
        Inventory_jPanelLayout.setVerticalGroup(
            Inventory_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Inventory_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );

        Sales_jPanel.setBackground(new java.awt.Color(0, 153, 204));

        Sales_Label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Sales_Label.setForeground(new java.awt.Color(255, 255, 255));
        Sales_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Sales_Label.setText("       ");
        Sales_Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Sales_LabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Sales_LabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Sales_LabelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout Sales_jPanelLayout = new javax.swing.GroupLayout(Sales_jPanel);
        Sales_jPanel.setLayout(Sales_jPanelLayout);
        Sales_jPanelLayout.setHorizontalGroup(
            Sales_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Sales_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Sales_jPanelLayout.setVerticalGroup(
            Sales_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Sales_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );

        Pos_jPanel.setBackground(new java.awt.Color(0, 153, 204));

        Pos_Label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Pos_Label.setForeground(new java.awt.Color(255, 255, 255));
        Pos_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Pos_Label.setText("         ");
        Pos_Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Pos_LabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Pos_LabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Pos_LabelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout Pos_jPanelLayout = new javax.swing.GroupLayout(Pos_jPanel);
        Pos_jPanel.setLayout(Pos_jPanelLayout);
        Pos_jPanelLayout.setHorizontalGroup(
            Pos_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Pos_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Pos_jPanelLayout.setVerticalGroup(
            Pos_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Pos_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );

        Logout_jPanel.setBackground(new java.awt.Color(0, 153, 204));

        Logout_Label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Logout_Label.setForeground(new java.awt.Color(255, 255, 255));
        Logout_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Logout_Label.setText("       ");
        Logout_Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Logout_LabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Logout_LabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Logout_LabelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout Logout_jPanelLayout = new javax.swing.GroupLayout(Logout_jPanel);
        Logout_jPanel.setLayout(Logout_jPanelLayout);
        Logout_jPanelLayout.setHorizontalGroup(
            Logout_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Logout_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Logout_jPanelLayout.setVerticalGroup(
            Logout_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Logout_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );

        LowOnStock.setBackground(new java.awt.Color(255, 0, 0));
        LowOnStock.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LowOnStock.setText("Low on Stock");
        LowOnStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LowOnStockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TabSidePanelLayout = new javax.swing.GroupLayout(TabSidePanel);
        TabSidePanel.setLayout(TabSidePanelLayout);
        TabSidePanelLayout.setHorizontalGroup(
            TabSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Employee_jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Sales_jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Pos_jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(TabSidePanelLayout.createSequentialGroup()
                .addComponent(Inventory_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(Logout_jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(TabSidePanelLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(LowOnStock, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        TabSidePanelLayout.setVerticalGroup(
            TabSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabSidePanelLayout.createSequentialGroup()
                .addComponent(Employee_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Inventory_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Sales_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Pos_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Logout_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(LowOnStock, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(0, 51, 102));
        jPanel5.setLayout(new java.awt.CardLayout());

        jPanel_Emp.setBackground(new java.awt.Color(0, 51, 102));
        jPanel_Emp.setForeground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("First Name:");

        EmployeeSearchButton.setBackground(new java.awt.Color(0, 102, 153));
        EmployeeSearchButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        EmployeeSearchButton.setForeground(new java.awt.Color(255, 255, 255));
        EmployeeSearchButton.setText("Search");
        EmployeeSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeSearchButtonActionPerformed(evt);
            }
        });

        EmployeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "First Name", "Last Name", "Address", "User Type", "Username", "Password"
            }
        ) {

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        EmployeeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EmployeeTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(EmployeeTable);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Employee");

        EmployeeAddUser.setBackground(new java.awt.Color(204, 102, 0));
        EmployeeAddUser.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        EmployeeAddUser.setForeground(new java.awt.Color(255, 255, 255));
        EmployeeAddUser.setText("Add User");
        EmployeeAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeAddUserActionPerformed(evt);
            }
        });

        EmployeeFirstNameComboBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        EmployeeFirstNameComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<select name of employee>" }));
        EmployeeFirstNameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeFirstNameComboBoxActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("User Type:");

        EmployeeRefreshButton.setBackground(new java.awt.Color(0, 102, 153));
        EmployeeRefreshButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        EmployeeRefreshButton.setForeground(new java.awt.Color(255, 255, 255));
        EmployeeRefreshButton.setText("Refresh");
        EmployeeRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeRefreshButtonActionPerformed(evt);
            }
        });

        EmployeeUserTypeComboBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        EmployeeUserTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<select user type>" }));

        EmployeeClearButton.setBackground(new java.awt.Color(0, 102, 153));
        EmployeeClearButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        EmployeeClearButton.setForeground(new java.awt.Color(255, 255, 255));
        EmployeeClearButton.setText("Clear");
        EmployeeClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeClearButtonActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Last Name:");

        EmployeeLastNameComboBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        EmployeeLastNameComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<select name of employee>" }));
        EmployeeLastNameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeLastNameComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_EmpLayout = new javax.swing.GroupLayout(jPanel_Emp);
        jPanel_Emp.setLayout(jPanel_EmpLayout);
        jPanel_EmpLayout.setHorizontalGroup(
            jPanel_EmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_EmpLayout.createSequentialGroup()
                .addGroup(jPanel_EmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_EmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel_EmpLayout.createSequentialGroup()
                            .addGap(110, 110, 110)
                            .addGroup(jPanel_EmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel8)
                                .addComponent(jLabel7)
                                .addComponent(EmployeeSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel_EmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel_EmpLayout.createSequentialGroup()
                                    .addGap(11, 11, 11)
                                    .addComponent(EmployeeClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(EmployeeRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel_EmpLayout.createSequentialGroup()
                                    .addGroup(jPanel_EmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(EmployeeUserTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel_EmpLayout.createSequentialGroup()
                                            .addComponent(EmployeeFirstNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel9)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(EmployeeLastNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_EmpLayout.createSequentialGroup()
                            .addGap(95, 95, 95)
                            .addGroup(jPanel_EmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(EmployeeAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 902, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel_EmpLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel10)))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel_EmpLayout.setVerticalGroup(
            jPanel_EmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_EmpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel_EmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmployeeUserTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel_EmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmployeeFirstNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmployeeLastNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_EmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EmployeeSearchButton)
                    .addComponent(EmployeeClearButton)
                    .addComponent(EmployeeRefreshButton))
                .addGap(6, 6, 6)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EmployeeAddUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(45, 45, 45))
        );

        jPanel5.add(jPanel_Emp, "card1");

        jPanel_Inventory.setBackground(new java.awt.Color(0, 51, 102));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Type:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Brand:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Model:");

        InventoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Type", "Brand", "Model", "Purchase  Price", "Selling  Price", "Quantity", "Date"
            }
        ){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        }

    );
    InventoryTable.setToolTipText("Month format is MM-dd-yyyy");
    InventoryTable.setShowGrid(false);
    InventoryTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            InventoryTableMouseClicked(evt);
        }
    });
    jScrollPane1.setViewportView(InventoryTable);

    jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jLabel6.setForeground(new java.awt.Color(255, 255, 255));
    jLabel6.setText("Purchase Price:"); // NOI18N

    jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jLabel33.setForeground(new java.awt.Color(255, 255, 255));
    jLabel33.setText("Selling Price:");

    jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jLabel34.setForeground(new java.awt.Color(255, 255, 255));
    jLabel34.setText("Quantity:");

    InventoryAddNewItem.setBackground(new java.awt.Color(204, 102, 0));
    InventoryAddNewItem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    InventoryAddNewItem.setForeground(new java.awt.Color(255, 255, 255));
    InventoryAddNewItem.setText("Add new Item");
    InventoryAddNewItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            InventoryAddNewItemActionPerformed(evt);
        }
    });

    InventorySearchButton.setBackground(new java.awt.Color(0, 102, 153));
    InventorySearchButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    InventorySearchButton.setForeground(new java.awt.Color(255, 255, 255));
    InventorySearchButton.setText("Search");
    InventorySearchButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            InventorySearchButtonActionPerformed(evt);
        }
    });

    InventoryComboBoxSellingPrice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<select selling price>" }));
    InventoryComboBoxSellingPrice.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            InventoryComboBoxSellingPriceActionPerformed(evt);
        }
    });

    InventoryComboBoxPurchasePrice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<select purchase price>" }));

    InventoryComboBoxType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<select product type>" }));
    InventoryComboBoxType.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            InventoryComboBoxTypeActionPerformed(evt);
        }
    });

    InventoryComboBoxBrand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<select product brand>" }));

    InventoryClearButton.setBackground(new java.awt.Color(0, 102, 153));
    InventoryClearButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    InventoryClearButton.setForeground(new java.awt.Color(255, 255, 255));
    InventoryClearButton.setText("Clear");
    InventoryClearButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            InventoryClearButtonActionPerformed(evt);
        }
    });

    InventoryPrintReportButton.setBackground(new java.awt.Color(204, 102, 0));
    InventoryPrintReportButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    InventoryPrintReportButton.setForeground(new java.awt.Color(255, 255, 255));
    InventoryPrintReportButton.setText("Print Report");
    InventoryPrintReportButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            InventoryPrintReportButtonActionPerformed(evt);
        }
    });

    InventoryQuantityTextField.setToolTipText("Equal to or less than the value entered.");
    InventoryQuantityTextField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            InventoryQuantityTextFieldActionPerformed(evt);
        }
    });

    jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    jLabel11.setForeground(new java.awt.Color(255, 255, 255));
    jLabel11.setText("Inventory");

    jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jLabel17.setForeground(new java.awt.Color(255, 255, 255));
    jLabel17.setText("Date:"); // NOI18N

    InventoryComboBoxModel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<select product model>" }));
    InventoryComboBoxModel.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            InventoryComboBoxModelActionPerformed(evt);
        }
    });

    InventoryRefreshButton.setBackground(new java.awt.Color(0, 102, 153));
    InventoryRefreshButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    InventoryRefreshButton.setForeground(new java.awt.Color(255, 255, 255));
    InventoryRefreshButton.setText("Refresh");
    InventoryRefreshButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            InventoryRefreshButtonActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel_InventoryLayout = new javax.swing.GroupLayout(jPanel_Inventory);
    jPanel_Inventory.setLayout(jPanel_InventoryLayout);
    jPanel_InventoryLayout.setHorizontalGroup(
        jPanel_InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel_InventoryLayout.createSequentialGroup()
            .addGap(41, 41, 41)
            .addGroup(jPanel_InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_InventoryLayout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addComponent(InventorySearchButton)
                    .addGap(41, 41, 41)
                    .addComponent(InventoryClearButton)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(InventoryRefreshButton)
                    .addGap(50, 50, 50))
                .addGroup(jPanel_InventoryLayout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel_InventoryLayout.createSequentialGroup()
                    .addGroup(jPanel_InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1002, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel_InventoryLayout.createSequentialGroup()
                            .addComponent(InventoryAddNewItem)
                            .addGap(53, 53, 53)
                            .addComponent(InventoryPrintReportButton))
                        .addGroup(jPanel_InventoryLayout.createSequentialGroup()
                            .addGroup(jPanel_InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel_InventoryLayout.createSequentialGroup()
                                    .addGroup(jPanel_InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel3))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel_InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(InventoryComboBoxType, 0, 209, Short.MAX_VALUE)
                                        .addComponent(InventoryComboBoxBrand, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(InventoryComboBoxModel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jLabel5))
                            .addGap(25, 25, 25)
                            .addGroup(jPanel_InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel33)
                                .addComponent(jLabel6)
                                .addComponent(jLabel17))
                            .addGap(13, 13, 13)
                            .addGroup(jPanel_InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(InventoryComboBoxSellingPrice, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(InventoryComboBoxPurchasePrice, javax.swing.GroupLayout.Alignment.LEADING, 0, 209, Short.MAX_VALUE)
                                .addComponent(InventoryDateChooserDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(25, 25, 25)
                            .addComponent(jLabel34)
                            .addGap(18, 18, 18)
                            .addComponent(InventoryQuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 35, Short.MAX_VALUE))))
    );
    jPanel_InventoryLayout.setVerticalGroup(
        jPanel_InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_InventoryLayout.createSequentialGroup()
            .addGap(14, 14, 14)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel_InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3)
                .addComponent(InventoryComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel6)
                .addComponent(InventoryComboBoxPurchasePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(InventoryQuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(9, 9, 9)
            .addGroup(jPanel_InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(InventoryComboBoxSellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel_InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel33)
                    .addComponent(InventoryComboBoxBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel_InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_InventoryLayout.createSequentialGroup()
                    .addGap(13, 13, 13)
                    .addGroup(jPanel_InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(InventoryComboBoxModel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17)))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_InventoryLayout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(InventoryDateChooserDate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel_InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_InventoryLayout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(InventorySearchButton))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_InventoryLayout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel_InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(InventoryClearButton)
                        .addComponent(InventoryRefreshButton))))
            .addGap(18, 18, 18)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
            .addGroup(jPanel_InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(InventoryPrintReportButton)
                .addComponent(InventoryAddNewItem))
            .addGap(37, 37, 37))
    );

    jPanel5.add(jPanel_Inventory, "card2");

    jPanel_Sales.setBackground(new java.awt.Color(0, 51, 102));

    jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jLabel12.setForeground(new java.awt.Color(255, 255, 255));
    jLabel12.setText("Type:");

    jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jLabel13.setForeground(new java.awt.Color(255, 255, 255));
    jLabel13.setText("Brand:");

    jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jLabel14.setForeground(new java.awt.Color(255, 255, 255));
    jLabel14.setText("Model:");

    SalesTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Sales ID", "Cashier ID", "Product Type", "Product Brand", "Product Model", "Purchase Price", "Product Price", "Quantity Sold", "Total Price", "Date of Purchase"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false, false, false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    jScrollPane3.setViewportView(SalesTable);

    jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jLabel35.setForeground(new java.awt.Color(255, 255, 255));
    jLabel35.setText("Selling Price:");

    jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jLabel36.setForeground(new java.awt.Color(255, 255, 255));
    jLabel36.setText("Quantity:");

    SalesSearchButton.setBackground(new java.awt.Color(0, 102, 153));
    SalesSearchButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    SalesSearchButton.setForeground(new java.awt.Color(255, 255, 255));
    SalesSearchButton.setText("Search");
    SalesSearchButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            SalesSearchButtonActionPerformed(evt);
        }
    });

    SalesSellingPriceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<select product price>" }));

    SalesTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<select product type>" }));

    SalesBrandComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<select product brand>" }));

    SalesModelComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<select product model>" }));

    SalesClearButton.setBackground(new java.awt.Color(0, 102, 153));
    SalesClearButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    SalesClearButton.setForeground(new java.awt.Color(255, 255, 255));
    SalesClearButton.setText("Clear");
    SalesClearButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            SalesClearButtonActionPerformed(evt);
        }
    });

    SalesReport.setBackground(new java.awt.Color(204, 102, 0));
    SalesReport.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    SalesReport.setForeground(new java.awt.Color(255, 255, 255));
    SalesReport.setText("Print Report");
    SalesReport.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            SalesReportActionPerformed(evt);
        }
    });

    SalesQuantityJtext.setToolTipText("Equal to or less than the value entered.");

    jLabel16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    jLabel16.setForeground(new java.awt.Color(255, 255, 255));
    jLabel16.setText("Sales");

    jLabel39.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jLabel39.setForeground(new java.awt.Color(255, 255, 255));
    jLabel39.setText("PROFIT:");

    SalesProfitJlabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    SalesProfitJlabel.setForeground(new java.awt.Color(255, 255, 255));
    SalesProfitJlabel.setText("PHP 0.0");

    javax.swing.GroupLayout jPanel_SalesLayout = new javax.swing.GroupLayout(jPanel_Sales);
    jPanel_Sales.setLayout(jPanel_SalesLayout);
    jPanel_SalesLayout.setHorizontalGroup(
        jPanel_SalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel_SalesLayout.createSequentialGroup()
            .addGap(41, 41, 41)
            .addGroup(jPanel_SalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_SalesLayout.createSequentialGroup()
                    .addGroup(jPanel_SalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel_SalesLayout.createSequentialGroup()
                            .addComponent(SalesReport)
                            .addGap(35, 35, 35)
                            .addComponent(jLabel39)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(SalesProfitJlabel))
                        .addGroup(jPanel_SalesLayout.createSequentialGroup()
                            .addGroup(jPanel_SalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel_SalesLayout.createSequentialGroup()
                                    .addGroup(jPanel_SalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel12))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel_SalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(SalesTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(SalesBrandComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel_SalesLayout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(SalesModelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel_SalesLayout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(59, 59, 59)
                            .addGroup(jPanel_SalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel35)
                                .addComponent(jLabel36))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel_SalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(SalesSellingPriceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(SalesQuantityJtext, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGap(0, 407, Short.MAX_VALUE))
                .addGroup(jPanel_SalesLayout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addComponent(SalesSearchButton)
                    .addGap(41, 41, 41)
                    .addComponent(SalesClearButton)
                    .addGap(48, 813, Short.MAX_VALUE))))
        .addGroup(jPanel_SalesLayout.createSequentialGroup()
            .addGap(27, 27, 27)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1028, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
    );
    jPanel_SalesLayout.setVerticalGroup(
        jPanel_SalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_SalesLayout.createSequentialGroup()
            .addGap(14, 14, 14)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel_SalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(SalesTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(SalesSellingPriceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel_SalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(SalesBrandComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(SalesQuantityJtext, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel_SalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(SalesModelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel_SalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_SalesLayout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(SalesSearchButton))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_SalesLayout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(SalesClearButton)))
            .addGap(18, 18, 18)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(27, 27, 27)
            .addGroup(jPanel_SalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(SalesReport)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(SalesProfitJlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(38, 38, 38))
    );

    jPanel5.add(jPanel_Sales, "card3");

    jPanel_PoS.setBackground(new java.awt.Color(0, 51, 102));
    jPanel_PoS.setForeground(new java.awt.Color(255, 255, 255));

    jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel15.setForeground(new java.awt.Color(255, 255, 255));
    jLabel15.setText("BRAND:");

    jLabel27.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel27.setForeground(new java.awt.Color(255, 255, 255));
    jLabel27.setText("MODEL:");

    PosTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Type", "Brand", "Model", "Price", "Quantity", "ID"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    jScrollPane5.setViewportView(PosTable);

    PosTransactionTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Type", "Brand", "Model", "Price", "Quantity", "Total", "ID"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false, true, false, true
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    PosTransactionTable.setToolTipText("Quantity is editable");
    PosTransactionTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mousePressed(java.awt.event.MouseEvent evt) {
            PosTransactionTableMousePressed(evt);
        }
    });
    PosTransactionTable.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            PosTransactionTableKeyReleased(evt);
        }
    });
    jScrollPane8.setViewportView(PosTransactionTable);

    jLabel28.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
    jLabel28.setForeground(new java.awt.Color(255, 255, 255));
    jLabel28.setText("Total Price:");

    PosTotPriceJlabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
    PosTotPriceJlabel.setForeground(new java.awt.Color(255, 255, 255));
    PosTotPriceJlabel.setText("PHP 0.0");

    jLabel30.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
    jLabel30.setForeground(new java.awt.Color(255, 255, 255));
    jLabel30.setText("Payment:");

    PosPaymentJtext.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            PosPaymentJtextActionPerformed(evt);
        }
    });
    PosPaymentJtext.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            PosPaymentJtextKeyReleased(evt);
        }
    });

    PosPayButton.setBackground(new java.awt.Color(204, 102, 0));
    PosPayButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    PosPayButton.setForeground(new java.awt.Color(255, 255, 255));
    PosPayButton.setText("Pay");
    PosPayButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            PosPayButtonActionPerformed(evt);
        }
    });

    jLabel31.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
    jLabel31.setForeground(new java.awt.Color(255, 255, 255));
    jLabel31.setText("Change:");

    PosChangeJlabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
    PosChangeJlabel.setForeground(new java.awt.Color(255, 255, 255));
    PosChangeJlabel.setText("PHP 0.0");

    jLabel37.setBackground(new java.awt.Color(255, 255, 255));
    jLabel37.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    jLabel37.setForeground(new java.awt.Color(255, 255, 255));
    jLabel37.setText("Point of Sale");

    PosTypeComboBox.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    PosTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<select product type>" }));

    PosModelComboBox.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    PosModelComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<select product model>" }));

    PosBrandComboBox.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
    PosBrandComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<select product brand>" }));

    jLabel38.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel38.setForeground(new java.awt.Color(255, 255, 255));
    jLabel38.setText("TYPE:");

    PosAddToCartButton.setBackground(new java.awt.Color(204, 102, 0));
    PosAddToCartButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    PosAddToCartButton.setForeground(new java.awt.Color(255, 255, 255));
    PosAddToCartButton.setText("Add to Cart");
    PosAddToCartButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            PosAddToCartButtonActionPerformed(evt);
        }
    });

    PosSearchButton.setBackground(new java.awt.Color(0, 102, 153));
    PosSearchButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    PosSearchButton.setForeground(new java.awt.Color(255, 255, 255));
    PosSearchButton.setText("Search");
    PosSearchButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            PosSearchButtonActionPerformed(evt);
        }
    });

    PosClearButton.setBackground(new java.awt.Color(0, 102, 153));
    PosClearButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    PosClearButton.setForeground(new java.awt.Color(255, 255, 255));
    PosClearButton.setText("Clear");
    PosClearButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            PosClearButtonActionPerformed(evt);
        }
    });

    jLabel32.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
    jLabel32.setForeground(new java.awt.Color(255, 255, 255));
    jLabel32.setText("Quantity:");

    PosQuantityJtext.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            PosQuantityJtextActionPerformed(evt);
        }
    });

    PosTransactionClearButton.setBackground(new java.awt.Color(0, 102, 153));
    PosTransactionClearButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    PosTransactionClearButton.setForeground(new java.awt.Color(255, 255, 255));
    PosTransactionClearButton.setText("Clear");
    PosTransactionClearButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            PosTransactionClearButtonActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel_PoSLayout = new javax.swing.GroupLayout(jPanel_PoS);
    jPanel_PoS.setLayout(jPanel_PoSLayout);
    jPanel_PoSLayout.setHorizontalGroup(
        jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_PoSLayout.createSequentialGroup()
            .addGroup(jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(jPanel_PoSLayout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jLabel32)
                    .addGap(18, 18, 18)
                    .addComponent(PosQuantityJtext, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                    .addComponent(PosAddToCartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_PoSLayout.createSequentialGroup()
                    .addGap(46, 46, 46)
                    .addGroup(jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel37)
                        .addGroup(jPanel_PoSLayout.createSequentialGroup()
                            .addGroup(jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel38)
                                .addComponent(jLabel15)
                                .addComponent(jLabel27))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(PosBrandComboBox, 0, 209, Short.MAX_VALUE)
                                .addComponent(PosTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(PosModelComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGap(0, 0, Short.MAX_VALUE))
                .addGroup(jPanel_PoSLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PosSearchButton)
                    .addGap(18, 18, 18)
                    .addComponent(PosClearButton)
                    .addGap(291, 291, 291))
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_PoSLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)))
            .addGroup(jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_PoSLayout.createSequentialGroup()
                    .addGap(98, 98, 98)
                    .addGroup(jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel30)
                        .addComponent(jLabel28)
                        .addComponent(jLabel31))
                    .addGap(35, 35, 35)
                    .addGroup(jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(PosPaymentJtext, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PosPayButton, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PosChangeJlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PosTotPriceJlabel, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
                    .addGap(50, 50, 50))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_PoSLayout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PosTransactionClearButton, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addContainerGap())))
    );
    jPanel_PoSLayout.setVerticalGroup(
        jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel_PoSLayout.createSequentialGroup()
            .addGap(45, 45, 45)
            .addGroup(jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_PoSLayout.createSequentialGroup()
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(43, 43, 43)
                    .addGroup(jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PosTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(16, 16, 16)
                    .addGroup(jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PosBrandComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(14, 14, 14)
                    .addGroup(jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PosModelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(PosSearchButton)
                        .addComponent(PosClearButton))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(PosAddToCartButton)
                        .addComponent(PosQuantityJtext, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(23, 23, 23))
                .addGroup(jPanel_PoSLayout.createSequentialGroup()
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(PosTransactionClearButton)
                    .addGap(17, 17, 17)
                    .addGroup(jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PosTotPriceJlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PosPaymentJtext, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(PosPayButton)
                    .addGap(33, 33, 33)
                    .addGroup(jPanel_PoSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel31)
                        .addComponent(PosChangeJlabel))
                    .addGap(90, 90, 90))))
    );

    jPanel5.add(jPanel_PoS, "card4");

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(TabSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, 0)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(TabSidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    jPanel3.setBackground(new java.awt.Color(19, 181, 235));

    Logo_Label.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    Logo_Label.setForeground(new java.awt.Color(255, 255, 255));
    Logo_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    Logo_Label.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            Logo_LabelMouseClicked(evt);
        }
    });

    FrameNameOfUser.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    FrameNameOfUser.setForeground(new java.awt.Color(255, 255, 255));
    FrameNameOfUser.setText("Name of User");

    FrameIdOfUser.setBackground(new java.awt.Color(255, 255, 255));
    FrameIdOfUser.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    FrameIdOfUser.setForeground(new java.awt.Color(255, 255, 255));
    FrameIdOfUser.setText("ID No.");

    FrameNameOfUser1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    FrameNameOfUser1.setForeground(new java.awt.Color(255, 255, 255));
    FrameNameOfUser1.setText("Name of User:");

    FrameIdOfUser1.setBackground(new java.awt.Color(255, 255, 255));
    FrameIdOfUser1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    FrameIdOfUser1.setForeground(new java.awt.Color(255, 255, 255));
    FrameIdOfUser1.setText("ID Number:");

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addComponent(Logo_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Icon_User, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(FrameNameOfUser1, javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(FrameIdOfUser1, javax.swing.GroupLayout.Alignment.TRAILING))
            .addGap(18, 18, 18)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(FrameIdOfUser, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(FrameNameOfUser, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Logo_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(70, 70, 70)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(FrameIdOfUser, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(FrameIdOfUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(FrameNameOfUser, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(FrameNameOfUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addComponent(Icon_User, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(16, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, 0)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Logo_LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Logo_LabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Logo_LabelMouseClicked

    private void Employee_LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Employee_LabelMouseClicked
       
        if(!getUser.getUserType().equals("Cashier")){
            //remove panel
            jPanel5.removeAll();
            jPanel5.repaint();
            jPanel5.revalidate();
            //add panel
            jPanel5.add(jPanel_Emp);
            jPanel5.repaint();
            jPanel5.revalidate();
        }else{
            JOptionPane.showMessageDialog(this,"Cashier can't access tab.");
        }
    }//GEN-LAST:event_Employee_LabelMouseClicked

    private void Inventory_LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Inventory_LabelMouseClicked
      RemoveAllInventoryDataTable();
      ShowInventoryDataTable();
      
      DefaultTableModel model = (DefaultTableModel) InventoryTable.getModel();
        for(int i=0;i<InventoryTable.getRowCount();i++){
            if((int)model.getValueAt(i,6)==0){
                model.removeRow(i);
            }
        }
       
        if(!getUser.getUserType().equals("Cashier")){
            //remove panel
            jPanel5.removeAll();
            jPanel5.repaint();
            jPanel5.revalidate();
            //add panel
            jPanel5.add(jPanel_Inventory);
            jPanel5.repaint();
            jPanel5.revalidate();  
        }else{
            JOptionPane.showMessageDialog(this,"Cashier can't access tab.");
        }  
      
    }//GEN-LAST:event_Inventory_LabelMouseClicked

    private void Employee_LabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Employee_LabelMouseEntered
        setColor(Employee_jPanel);
    }//GEN-LAST:event_Employee_LabelMouseEntered

    private void Employee_LabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Employee_LabelMouseExited
       ResetColor(Employee_jPanel);
    }//GEN-LAST:event_Employee_LabelMouseExited

    private void Sales_LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Sales_LabelMouseClicked
        
        RemoveAllSalesDataTable();
        showSalesDataTable();
        SalesClearAllComboBox();
        SalesSetComboBox();
        SalesCalculateProfitMade();
        
        if(!getUser.getUserType().equals("Cashier")){
            //remove panel
            jPanel5.removeAll();
            jPanel5.repaint();
            jPanel5.revalidate();
            //add panel
            jPanel5.add(jPanel_Sales);
            jPanel5.repaint();
            jPanel5.revalidate();         
        }else{
            JOptionPane.showMessageDialog(this,"Cashier can't access tab.");
        }  

        
    }//GEN-LAST:event_Sales_LabelMouseClicked

    private void Sales_LabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Sales_LabelMouseEntered
        setColor(Sales_jPanel);
    }//GEN-LAST:event_Sales_LabelMouseEntered

    private void Sales_LabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Sales_LabelMouseExited
        ResetColor(Sales_jPanel);
    }//GEN-LAST:event_Sales_LabelMouseExited

    private void Inventory_LabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Inventory_LabelMouseEntered
        setColor(Inventory_jPanel);
    }//GEN-LAST:event_Inventory_LabelMouseEntered

    private void Inventory_LabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Inventory_LabelMouseExited
        ResetColor(Inventory_jPanel);
    }//GEN-LAST:event_Inventory_LabelMouseExited

    private void Pos_LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Pos_LabelMouseClicked
       
      //remove panel
      jPanel5.removeAll();
      jPanel5.repaint();
      jPanel5.revalidate();
      //add panel
      jPanel5.add(jPanel_PoS);
      jPanel5.repaint();
      jPanel5.revalidate(); 
      
      PosClearAllComboBox();
      PosSetComboBox();
      RemoveAllPosDataTable();
      showPosDataTable();
    }//GEN-LAST:event_Pos_LabelMouseClicked

    private void Pos_LabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Pos_LabelMouseEntered
        setColor(Pos_jPanel);
    }//GEN-LAST:event_Pos_LabelMouseEntered

    private void Pos_LabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Pos_LabelMouseExited
        ResetColor(Pos_jPanel);
    }//GEN-LAST:event_Pos_LabelMouseExited

    private void Logout_LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Logout_LabelMouseClicked
        this.dispose();
        new SignIn().setVisible(true);
    }//GEN-LAST:event_Logout_LabelMouseClicked

    private void Logout_LabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Logout_LabelMouseEntered
        setColor(Logout_jPanel);
    }//GEN-LAST:event_Logout_LabelMouseEntered

    private void Logout_LabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Logout_LabelMouseExited
        ResetColor(Logout_jPanel);
    }//GEN-LAST:event_Logout_LabelMouseExited

    private void EmployeeSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeSearchButtonActionPerformed
        // TODO add your handling code here:
        searchEmployee();
    }//GEN-LAST:event_EmployeeSearchButtonActionPerformed

    private void EmployeeAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeAddUserActionPerformed
        new SignUp(null).setVisible(true);
    }//GEN-LAST:event_EmployeeAddUserActionPerformed

    private void InventoryAddNewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InventoryAddNewItemActionPerformed
        //new Add_Item().setVisible(true);
       new Add_Item(null).setVisible(true);
    }//GEN-LAST:event_InventoryAddNewItemActionPerformed

    private void InventorySearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InventorySearchButtonActionPerformed
        // TODO add your handling code here:
        SearchInventory();
    }//GEN-LAST:event_InventorySearchButtonActionPerformed

    private void InventoryClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InventoryClearButtonActionPerformed
        // TODO add your handling code here:
        InventoryComboBoxType.setSelectedIndex(0);
        InventoryComboBoxBrand.setSelectedIndex(0);
        InventoryComboBoxModel.setSelectedIndex(0);
        InventoryComboBoxPurchasePrice.setSelectedIndex(0);
        InventoryComboBoxSellingPrice.setSelectedIndex(0);
        InventoryQuantityTextField.setText("");
        InventoryDateChooserDate.setDate(null);
        
        RemoveAllInventoryDataTable();
        ShowInventoryDataTable();
    }//GEN-LAST:event_InventoryClearButtonActionPerformed

    private void InventoryPrintReportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InventoryPrintReportButtonActionPerformed
        try {
            // TODO add your handling code here:

            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\ADMIN\\Desktop\\2696-CPE 223-Software Design\\Assignment\\PC Shop Inventory and Point of Sale System\\Source Code\\ComputerPartsShop\\src\\AdminUi\\InventoryReport.jrxml");
            String query = "SELECT * FROM inventory";
            JRDesignQuery updateQuery = new JRDesignQuery();
            updateQuery.setText(query);
            
            jdesign.setQuery(updateQuery);
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport,null,getConnection("pcvillage"));
            JasperViewer.viewReport(jprint);
            
        } catch (JRException ex) {
            Logger.getLogger(AdminUi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_InventoryPrintReportButtonActionPerformed

    private void SalesSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalesSearchButtonActionPerformed
        // TODO add your handling code here:
        SearchSales();
        SalesCalculateProfitMade();
    }//GEN-LAST:event_SalesSearchButtonActionPerformed

    private void SalesClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalesClearButtonActionPerformed
        // TODO add your handling code here:
        SalesTypeComboBox.setSelectedIndex(0);
        SalesBrandComboBox.setSelectedIndex(0);
        SalesModelComboBox.setSelectedIndex(0);
        SalesSellingPriceComboBox.setSelectedIndex(0);
        SalesQuantityJtext.setText("");
        
        RemoveAllSalesDataTable();
        showSalesDataTable();
        SalesCalculateProfitMade();
    }//GEN-LAST:event_SalesClearButtonActionPerformed

    private void SalesReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalesReportActionPerformed
        // TODO add your handling code here:
        try {

            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\ADMIN\\Desktop\\2696-CPE 223-Software Design\\Assignment\\PC Shop Inventory and Point of Sale System\\Source Code\\ComputerPartsShop\\src\\AdminUi\\SalesReport.jrxml");
            String query = "SELECT * FROM sales";
            JRDesignQuery updateQuery = new JRDesignQuery();
            updateQuery.setText(query);
            
            jdesign.setQuery(updateQuery);
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport,null,getConnection("pcvillage"));
            JasperViewer.viewReport(jprint);
            
        } catch (JRException ex) {
            Logger.getLogger(AdminUi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_SalesReportActionPerformed

    private void PosAddToCartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PosAddToCartButtonActionPerformed
        // TODO add your handling code here:
        PosAddtoTransactionTable();
        PosTotalPriceofAllItems();
    }//GEN-LAST:event_PosAddToCartButtonActionPerformed

    private void PosPayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PosPayButtonActionPerformed
        // TODO add your handling code here:
        
        int change = Integer.parseInt(PosChangeJlabel.getText().replaceAll("PHP ",""));
        DefaultTableModel model = (DefaultTableModel) PosTable.getModel();
        Statement st;
        if(change<0){
            JOptionPane.showMessageDialog(this, "Your payment is insufficient.");
            return;
        }
            
        PosUpdateInventoryProductQuantity();
        if(quantity<0){
            quantity=0;
            return;
        }
        
        
        PosTotPriceJlabel.setText("PHP 0.0");
        PosChangeJlabel.setText("PHP 0.0");
        PosPaymentJtext.setText("");
        /*
        This code deletes the item from inventory if its quantity reaches 0
        try {
            st = getConnection("pcvillage").createStatement();
            st.execute("DELETE FROM inventory WHERE product_quantity = 0");
        } catch (SQLException ex) {
            Logger.getLogger(AdminUi.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        
        SalesAddTransactionToDB();
        PosRemoveAllTransactionDataTable();
        RemoveAllPosDataTable();
        showPosDataTable();
        
        for(int i=0;i<PosTable.getRowCount();i++){
            if((int)model.getValueAt(i,4)==0){
                model.removeRow(i);
            }
        }
        
        promptUserIfLowOnStock();
    }//GEN-LAST:event_PosPayButtonActionPerformed

    private void PosPaymentJtextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PosPaymentJtextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PosPaymentJtextActionPerformed

    private void InventoryRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InventoryRefreshButtonActionPerformed
        // TODO add your handling code here:
            promptUserIfLowOnStock();
            RemoveAllInventoryDataTable();
            ShowInventoryDataTable();
            InventoryClearAllComboBox();
            InventorySetComboBox();
            
            DefaultTableModel model = (DefaultTableModel)InventoryTable.getModel();
            for(int i=0;i<InventoryTable.getRowCount();i++){
                if((int)model.getValueAt(i,6)==0){
                    model.removeRow(i);
                }
            }
    }//GEN-LAST:event_InventoryRefreshButtonActionPerformed

    private void InventoryQuantityTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InventoryQuantityTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InventoryQuantityTextFieldActionPerformed

    private void InventoryComboBoxModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InventoryComboBoxModelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InventoryComboBoxModelActionPerformed

    private void InventoryComboBoxTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InventoryComboBoxTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InventoryComboBoxTypeActionPerformed

    private void InventoryComboBoxSellingPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InventoryComboBoxSellingPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InventoryComboBoxSellingPriceActionPerformed

    private void EmployeeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EmployeeTableMouseClicked
        // TODO add your handling code here:
        int x_position = evt.getXOnScreen()-35;
        int y_position = evt.getYOnScreen()-15;
        Point p = evt.getPoint();
        int row = EmployeeTable.rowAtPoint(p);
        int col = EmployeeTable.columnAtPoint(p);
       
        if ((col == -1) || (row == -1)) {
           return;
        }
        DefaultTableModel model = (DefaultTableModel) InventoryTable.getModel();
        if(SwingUtilities.isRightMouseButton(evt)){
            EmployeeEditDeletePopUp.show(this,x_position,y_position);
            EmployeeTable.setRowSelectionInterval(row, row);
        
        }
    }//GEN-LAST:event_EmployeeTableMouseClicked

    private void InventoryTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InventoryTableMouseClicked
        // TODO add your handling code here:
        int x_position = evt.getXOnScreen()-35;
        int y_position = evt.getYOnScreen()-15;
        Point p = evt.getPoint();
        int row = InventoryTable.rowAtPoint(p);
        int col = InventoryTable.columnAtPoint(p);
       
        if ((col == -1) || (row == -1)) {
           return;
        }
        DefaultTableModel model = (DefaultTableModel) InventoryTable.getModel();
        if(SwingUtilities.isRightMouseButton(evt)){
            InventoryEditDeletePopUp.show(this, x_position, y_position);
            InventoryTable.setRowSelectionInterval(row, row);
        }
    }//GEN-LAST:event_InventoryTableMouseClicked

    private void InventoryDeleteSelectedMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InventoryDeleteSelectedMousePressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_InventoryDeleteSelectedMousePressed

    private void InventoryEditSelectedMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InventoryEditSelectedMousePressed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) InventoryTable.getModel();
        GetData getData = new GetData();
        int index = InventoryTable.getSelectedRow();
        getData.setInventoryData((int)model.getValueAt(index, 0), (String)model.getValueAt(index, 1), (String)model.getValueAt(index, 2), (String)model.getValueAt(index, 3), (int)model.getValueAt(index, 4), (int)model.getValueAt(index, 5), (int)model.getValueAt(index, 6), (String)model.getValueAt(index, 7));
        new Add_Item(getData).setVisible(true);
        
    }//GEN-LAST:event_InventoryEditSelectedMousePressed

    private void InventoryDeleteSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InventoryDeleteSelectedActionPerformed
        // TODO add your handling code here:
        
        InventoryDeleteSelectedRowFromDB(InventoryTable.getSelectedRow());
        RemoveInventoryDataTableAt(InventoryTable.getSelectedRow());
        InventoryClearAllComboBox();
        InventorySetComboBox();
    }//GEN-LAST:event_InventoryDeleteSelectedActionPerformed

    private void EmployeeEditSelectedMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EmployeeEditSelectedMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmployeeEditSelectedMousePressed

    private void EmployeeDeleteSelectedMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EmployeeDeleteSelectedMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmployeeDeleteSelectedMousePressed

    private void EmployeeDeleteSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeDeleteSelectedActionPerformed
        // TODO add your handling code here:
        EmployeeDeleteSelectedRowFromDB(EmployeeTable.getSelectedRow());
        RemoveEmployeeDataTableAt(EmployeeTable.getSelectedRow());
        EmployeeClearAllComboBox();
        EmployeeSetComboBox();
        
    }//GEN-LAST:event_EmployeeDeleteSelectedActionPerformed

    private void EmployeeRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeRefreshButtonActionPerformed
        // TODO add your handling code here:
        RemoveAllEmployeeDataTable();
        showEmployeeDataTable();
        EmployeeClearAllComboBox();
        EmployeeSetComboBox();
    }//GEN-LAST:event_EmployeeRefreshButtonActionPerformed

    private void EmployeeClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeClearButtonActionPerformed
        // TODO add your handling code here:
        EmployeeUserTypeComboBox.setSelectedIndex(0);
        EmployeeFirstNameComboBox.setSelectedIndex(0);
        EmployeeLastNameComboBox.setSelectedIndex(0);
        
        RemoveAllEmployeeDataTable();
        showEmployeeDataTable();
    }//GEN-LAST:event_EmployeeClearButtonActionPerformed

    private void EmployeeFirstNameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeFirstNameComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmployeeFirstNameComboBoxActionPerformed

    private void EmployeeEditSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeEditSelectedActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) EmployeeTable.getModel();
        GetData getData = new GetData();
        int index = EmployeeTable.getSelectedRow();
        getData.setEmployeeData((int)model.getValueAt(index, 0), (String)model.getValueAt(index, 1), (String)model.getValueAt(index, 2), (String)model.getValueAt(index, 3), (String)model.getValueAt(index, 4), (String)model.getValueAt(index, 5), (String)model.getValueAt(index, 6));
        
        new SignUp(getData).setVisible(true);
    }//GEN-LAST:event_EmployeeEditSelectedActionPerformed

    private void EmployeeLastNameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeLastNameComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmployeeLastNameComboBoxActionPerformed

    private void PosSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PosSearchButtonActionPerformed
        // TODO add your handling code here:
        RemoveAllInventoryDataTable();
        ShowInventoryDataTable();
        SearchPos();
    }//GEN-LAST:event_PosSearchButtonActionPerformed

    private void PosClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PosClearButtonActionPerformed
        // TODO add your handling code here:
        PosClearAllComboBox();
        PosSetComboBox();
        RemoveAllPosDataTable();
        showPosDataTable();
    }//GEN-LAST:event_PosClearButtonActionPerformed

    private void PosQuantityJtextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PosQuantityJtextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PosQuantityJtextActionPerformed

    private void PosTransactionTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PosTransactionTableKeyReleased
        // TODO add your handling code here:
        
        PosTransactionQuantityChanged();
        PosTotalPriceofAllItems();
    }//GEN-LAST:event_PosTransactionTableKeyReleased

    private void PosTransactionClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PosTransactionClearButtonActionPerformed
        // TODO add your handling code here:
        PosRemoveAllTransactionDataTable();
        PosTotPriceJlabel.setText("PHP 0.0");
    }//GEN-LAST:event_PosTransactionClearButtonActionPerformed

    private void PosTransactionTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PosTransactionTableMousePressed
        // TODO add your handling code here:
        
        
        PosTransactionQuantityChanged();
    }//GEN-LAST:event_PosTransactionTableMousePressed

    private void PosPaymentJtextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PosPaymentJtextKeyReleased
        // TODO add your handling code here:+
        if(PosPaymentJtext.getText().equals("")){
            PosChangeJlabel.setText("PHP 0.0");
            return;
        }
        PosCalculateChange();
        
    }//GEN-LAST:event_PosPaymentJtextKeyReleased

    private void LowOnStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LowOnStockActionPerformed
        // TODO add your handling code here:
        new Prompt_low().setVisible(true);
    }//GEN-LAST:event_LowOnStockActionPerformed

    /**
     * @param args the command line arguments
     */
   /* public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(AdminUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }*/
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
      /*  java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUi().setVisible(true);
            }
        });
    }*/
    private Date date;
    private SimpleDateFormat sdf;
    JFrame edit_delete = new JFrame();
    private int quantity = 0;
    private GetUser getUser;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EmployeeAddUser;
    private javax.swing.JButton EmployeeClearButton;
    private javax.swing.JMenuItem EmployeeDeleteSelected;
    private javax.swing.JPopupMenu EmployeeEditDeletePopUp;
    private javax.swing.JMenuItem EmployeeEditSelected;
    private javax.swing.JComboBox<String> EmployeeFirstNameComboBox;
    private javax.swing.JComboBox<String> EmployeeLastNameComboBox;
    private javax.swing.JButton EmployeeRefreshButton;
    private javax.swing.JButton EmployeeSearchButton;
    private static javax.swing.JTable EmployeeTable;
    private javax.swing.JComboBox<String> EmployeeUserTypeComboBox;
    private javax.swing.JLabel Employee_Label;
    private javax.swing.JPanel Employee_jPanel;
    private javax.swing.JLabel FrameIdOfUser;
    private javax.swing.JLabel FrameIdOfUser1;
    private javax.swing.JLabel FrameNameOfUser;
    private javax.swing.JLabel FrameNameOfUser1;
    private javax.swing.JLabel Icon_User;
    private javax.swing.JButton InventoryAddNewItem;
    private javax.swing.JButton InventoryClearButton;
    private javax.swing.JComboBox<String> InventoryComboBoxBrand;
    private javax.swing.JComboBox<String> InventoryComboBoxModel;
    private javax.swing.JComboBox<String> InventoryComboBoxPurchasePrice;
    private javax.swing.JComboBox<String> InventoryComboBoxSellingPrice;
    private javax.swing.JComboBox<String> InventoryComboBoxType;
    private com.toedter.calendar.JDateChooser InventoryDateChooserDate;
    private javax.swing.JMenuItem InventoryDeleteSelected;
    private javax.swing.JPopupMenu InventoryEditDeletePopUp;
    private javax.swing.JMenuItem InventoryEditSelected;
    private javax.swing.JButton InventoryPrintReportButton;
    private javax.swing.JTextField InventoryQuantityTextField;
    private javax.swing.JButton InventoryRefreshButton;
    private javax.swing.JButton InventorySearchButton;
    private javax.swing.JTable InventoryTable;
    private javax.swing.JLabel Inventory_Label;
    private javax.swing.JPanel Inventory_jPanel;
    private javax.swing.JLabel Logo_Label;
    private javax.swing.JLabel Logout_Label;
    private javax.swing.JPanel Logout_jPanel;
    private javax.swing.JButton LowOnStock;
    private javax.swing.JButton PosAddToCartButton;
    private javax.swing.JComboBox<String> PosBrandComboBox;
    private javax.swing.JLabel PosChangeJlabel;
    private javax.swing.JButton PosClearButton;
    private javax.swing.JComboBox<String> PosModelComboBox;
    private javax.swing.JButton PosPayButton;
    private javax.swing.JTextField PosPaymentJtext;
    private javax.swing.JTextField PosQuantityJtext;
    private javax.swing.JButton PosSearchButton;
    private javax.swing.JTable PosTable;
    private javax.swing.JLabel PosTotPriceJlabel;
    private javax.swing.JButton PosTransactionClearButton;
    private javax.swing.JTable PosTransactionTable;
    private javax.swing.JComboBox<String> PosTypeComboBox;
    private javax.swing.JLabel Pos_Label;
    private javax.swing.JPanel Pos_jPanel;
    private javax.swing.JComboBox<String> SalesBrandComboBox;
    private javax.swing.JButton SalesClearButton;
    private javax.swing.JComboBox<String> SalesModelComboBox;
    private javax.swing.JLabel SalesProfitJlabel;
    private javax.swing.JTextField SalesQuantityJtext;
    private javax.swing.JButton SalesReport;
    private javax.swing.JButton SalesSearchButton;
    private javax.swing.JComboBox<String> SalesSellingPriceComboBox;
    private javax.swing.JTable SalesTable;
    private javax.swing.JComboBox<String> SalesTypeComboBox;
    private javax.swing.JLabel Sales_Label;
    private javax.swing.JPanel Sales_jPanel;
    private javax.swing.JPanel TabSidePanel;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel_Emp;
    private javax.swing.JPanel jPanel_Inventory;
    private javax.swing.JPanel jPanel_PoS;
    private javax.swing.JPanel jPanel_Sales;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
