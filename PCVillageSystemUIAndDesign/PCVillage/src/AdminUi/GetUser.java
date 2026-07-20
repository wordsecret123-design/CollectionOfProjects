/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminUi;

/**
 *
 * @author ADMIN
 */
public class GetUser {
    private String usertype;
    private String username;
    private int userid;
    
    public GetUser(String usertype, String username,int userid){
        this.usertype=usertype;
        this.username=username;
        this.userid = userid;
        
    }
    
    public String getUserType(){
        return usertype;
    }
    public String getUsername(){
        return username;
    }
    public int getUserID(){
        return userid;
    }
}
