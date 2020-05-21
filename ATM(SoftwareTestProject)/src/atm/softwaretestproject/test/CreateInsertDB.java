/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.softwaretestproject.test;

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

public class CreateInsertDB {
    public static void main(String args[]) {
//        String createSQL = "ALTER TABLE users MODIFY COLUMN accNumb BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 10000000, INCREMENT BY 1)";
        
//        String createSQL = "CREATE TABLE users ("
//        + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
//        + "userType VARCHAR(10) NOT NULL, accNumb BIGINT NOT NULL GENERATED ALWAYS AS (id+99999999), password VARCHAR(40),"
//        + "name VARCHAR(100), accType VARCHAR(100), balance DOUBLE, interestRate DOUBLE, "
//        + "creditLine DOUBLE, transFee DOUBLE, dateCreated TIMESTAMP, "
//        + "CONSTRAINT primary_key PRIMARY KEY (id))";

//        String createSQL = "CREATE TABLE Alpha_Hist("
//            + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
//            + "accNumb BIGINT,"
//            + "name VARCHAR(100), accType VARCHAR(100), balance DOUBLE, interestRate DOUBLE, "
//            + "creditLine DOUBLE, transFee DOUBLE, dateCreated TIMESTAMP, "
//            + "CONSTRAINT primary_key PRIMARY KEY (id))";
//        createTable(createSQL);
        
//        insert();
        
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
    
    public static Connection getConnection(){
        Connection userConn = null;
        try{
            userConn = DriverManager.getConnection("jdbc:derby:software_test_project_atm_db;create=true");
//            System.out.println("Connected to DB");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e,"Can't connect to database",2);
//            System.out.println(e);
        }
        
        return userConn;
    }
    
    public static void createTable(String SQL){
        Connection conn = null;
        Statement stmt;
        
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            stmt.execute(SQL);
            
            System.out.println("Table Created...");
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
    
    public static void insert(){
        Connection conn = null;
        PreparedStatement pstmt;
        Statement stmt;
        ResultSet rs = null;
        
        try{
            String custSQL = "INSERT INTO users (USERTYPE, PASSWORD, \"NAME\", "
                    + "ACCTYPE, BALANCE, INTERESTRATE, CREDITLINE, TRANSFEE, DATECREATED)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
            
//            String clerkSQL = "INSERT INTO users (USERTYPE, PASSWORD, \"NAME\", DATECREATED)" 
//                               + "VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
            conn = getConnection();
            pstmt = conn.prepareStatement(custSQL);
//            pstmt = conn.prepareStatement(clerkSQL);
//            pstmt.setString(1, "clerk");
//            pstmt.setString(2, "1");
//            pstmt.setString(3, "Someone");
            
            pstmt.setString(1, "cust"); //userType
            pstmt.setString(2, "123456"); //Password
            pstmt.setString(3, "Alpha"); //name
            pstmt.setString(4, "Saving Account"); //Account type
            pstmt.setDouble(5, 500); //Balance
            pstmt.setDouble(6, 1.5); //Interest rate
            pstmt.setDouble(7, 0); //Credit Line
            pstmt.setDouble(8, 0); //Transfee
            
            
            pstmt.executeUpdate();
            
            System.out.println("New Row Inserted...");
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
}
