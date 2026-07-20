/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminUi;

/**
 *
 * @author ADMIN
 */
public class GetData {
    
    // Fields for Inventory
    private int product_id;
    private String product_type;
    private String product_brand;
    private String product_model;
    private int product_purchase_price;
    private int product_selling_price;
    private int product_quantity;
    private String product_last_changed_date;
    
   // Fields for Employees
    private int emp_id;
    private String emp_firstname;
    private String emp_lastname; 
    private String emp_address; 
    private String emp_usertype; 
    private String emp_username;
    private String emp_password;
    
   // Fields for Sales
    private int sales_id;
    private int sales_product_id;
    private int sales_cashier_id;
    private int sales_product_price;
    private int sales_product_quantity_sold;
    private int sales_total_price;
    private String sales_date_of_purchase;
    
   //fields for Point of Sale
    private int Posproduct_id;
    private String Posproduct_type;
    private String Posproduct_brand;
    private String Posproduct_model;
    private int Posproduct_purchase_price;
    private int Posproduct_selling_price;
    private int Posproduct_quantity;
    private String Posproduct_last_changed_date;
    
    public void setInventoryData(int id,String type,String brand, String model,int purchase,int selling,int quantity,String date){
        
        product_id=id;
        product_type = type;
        product_brand = brand;
        product_model = model;
        product_purchase_price = purchase;
        product_selling_price = selling;
        product_quantity = quantity;
        product_last_changed_date = date;    
    }   
    
    public int getInventoryPID(){
        return product_id;
    }
    public String getInventoryPType(){
        return product_type;
    }
    public String getInventoryPBrand(){
        return product_brand;
    }
    public String getInventoryPModel(){
        return product_model;
    }
    public int getInventoryPPurchasePrice(){
        return product_purchase_price;
    }
    public int getInventoryPSellingPrice(){
        return product_selling_price;
    }
    public int getInventoryPQuantity(){
        return product_quantity;
    }
    public String getInventoryDateChanged(){
        return product_last_changed_date;
    }
    
    public void setEmployeeData(int id,String firstname,String lastname, String address, String usertype, String username,String password){
        emp_id=id;
        emp_firstname = firstname;
        emp_lastname = lastname;
        emp_address = address;
        emp_usertype = usertype; 
        emp_username = username;
        emp_password = password;  
    }
     
    public int getEmployeeID(){
        return emp_id;
    }
    public String getEmployeeFirstName(){
        return emp_firstname;
    }
    public String getEmployeeLastName(){
        return emp_lastname;
    }
    public String getEmployeeAddress(){
        return emp_address;
    }
    public String getEmployeeUserType(){
        return emp_usertype;
    }
    public String getEmployeeUsername(){
        return emp_username;
    }
    public String getEmployeePassword(){
        return emp_password;
    }
    
    public void setSalesData(int id, int productid, int cashier_id, int price, int quantity, int total, String date,String type,String brand,String model,int purchaseprice){
        
        sales_id=id;
        sales_product_id=productid;
        sales_cashier_id=cashier_id;
        sales_product_price=price;
        sales_product_quantity_sold=quantity;
        sales_total_price=total;
        sales_date_of_purchase=date;
        
        product_type=type;
        product_brand=brand;
        product_model=model;
        product_purchase_price=purchaseprice;
      
    }
    
    public int getSalesID(){
        return sales_id;
    }
    public int getSalesProductID(){
        return sales_product_id;
    }
    
    public int getSalesCashierID(){
        return sales_cashier_id;
    }
    public int getSalesProductPrice(){
        return sales_product_price;
    }
    public int getSalesProductQuantity(){
        return sales_product_quantity_sold;
    }
    public int getSalesTotalPrice(){
        return sales_total_price;
    }
    public String getSalesDateOfPurchase(){
        return sales_date_of_purchase;
    }
    
    public void setPosData(int id,String type,String brand, String model,int purchase,int selling,int quantity,String date){
        
        Posproduct_id=id;
        Posproduct_type = type;
        Posproduct_brand = brand;
        Posproduct_model = model;
        Posproduct_purchase_price = purchase;
        Posproduct_selling_price = selling;
        Posproduct_quantity = quantity;
        Posproduct_last_changed_date = date;    
    }   
    
    public int getPosPID(){
        return Posproduct_id;
    }
    public String getPosPType(){
        return Posproduct_type;
    }
    public String getPosPBrand(){
        return Posproduct_brand;
    }
    public String getPosPModel(){
        return Posproduct_model;
    }
    public int getPosPPurchasePrice(){
        return Posproduct_purchase_price;
    }
    public int getPosPSellingPrice(){
        return Posproduct_selling_price;
    }
    public int getPosPQuantity(){
        return Posproduct_quantity;
    }
    public String getPosDateChanged(){
        return Posproduct_last_changed_date;
    }
}
