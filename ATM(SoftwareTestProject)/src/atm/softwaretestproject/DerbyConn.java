/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.softwaretestproject;

/**
 *
 * @author ASUS
 */

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DerbyConn {
    public static void main(String args[]) {
//        getConnection();
    }
    
    private static String url = "jdbc:mysql://localhost:3306/software_test_project_atm_db";
    
    public static Connection getConnection(){
        Connection userConn = null;
        try{
            userConn = DriverManager.getConnection("jdbc:derby:software_test_project_atm_db;");
//            System.out.println("Connected to DB");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e,"Can't connect to database",2);
//            System.out.println(e);
        }
        
        return userConn;
    }
    
    public static Connection getConnection3(String a){
        Connection userConn = null;
        try{
            userConn = DriverManager.getConnection("jdbc:derby:trans_db_test;");
//            System.out.println("Connected to DB");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e,a,2);
//            System.out.println(e);
        }
        
        return userConn;
    }
    
    public static Connection getConnection2(){
        Connection userConn = null;
        try{
            userConn = DriverManager.getConnection("jdbc:derby:transaction_db;");
//            System.out.println("Connected to DB");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e,"Can't connect to transaction database",2);
//            System.out.println(e);
        }
        
        return userConn;
    }
    
    public static void shutConn(){
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        }catch (SQLException ex) {
            if (((ex.getErrorCode() == 50000) &&("XJ015".equals(ex.getSQLState())))) {
               System.out.println("Derby shut down normally");
            }else {
                System.err.println("Derby did not shut down normally");
                System.err.println(ex.getMessage());
            }
        }
    }
}
