/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.softwaretestproject.test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class transDBTest {
    public static void main(String args[]) {
//        getConnection();
        int accNumb = 100000007;
        String tableName = "history_"+Integer.toString(accNumb);
        String createSQL = "CREATE TABLE "+ tableName + "("
                    + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "accNumb BIGINT, "
                    + "name VARCHAR(100),"
                    + "type VARCHAR(25), "
                    + "description VARCHAR(100), "
                    + "amt_in DOUBLE,"
                    + "amt_out DOUBLE,"
                    + "balance DOUBLE, "
                    + "targetAcc BIGINT,"
                    + "date TIMESTAMP, "
                    + "CONSTRAINT primary_key_"+accNumb+ " PRIMARY KEY (id))";
        createTable(createSQL);
        
//        insert();
//        

//        getConnectionTrans();
//        try {
//            DriverManager.getConnection("jdbc:derby:;shutdown=true");
//        }catch (SQLException ex) {
//            if (((ex.getErrorCode() == 50000) &&("XJ015".equals(ex.getSQLState())))) {
//               System.out.println("Derby shut down normally");
//            }else {
//                System.err.println("Derby did not shut down normally");
//                System.err.println(ex.getMessage());
//            }
//        }
        
    }
    
    public static Connection getConnectionTrans(){
        Connection userConn = null;
        try{
            userConn = DriverManager.getConnection("jdbc:derby:transaction_db;create=true");
            System.out.println("Connected to DB");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e,"Can't connect to database",2);
//            System.out.println(e);
        }
        
        return userConn;
    }
    
    public static void createTable(String SQL){
        Connection conn = null;
        Statement stmt;
        PreparedStatement pstmt;
        
        try{
            conn = getConnectionTrans();
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
            String SQL = "INSERT INTO history_10001 ("
                    + "ACCNUMB, "
                    + "\"NAME\", "
                    + "TYPE, "
                    + "DESCRIPTION, "
                    + "AMT_IN, "
                    + "AMT_OUT, "
                    + "BALANCE, "
                    + "TARGETACC, DATE)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
            conn = getConnectionTrans();
            pstmt = conn.prepareStatement(SQL);

            
            pstmt.setInt(1, 10001); //ACCNUMB
            pstmt.setString(2, "Robert"); //NAME
            pstmt.setString(3, "Withdraw"); //TYPE
            pstmt.setString(4, ""); //DESCRIPTION 
            pstmt.setDouble(5, 0); //AMT_IN
            pstmt.setDouble(6, 100); //AMT_OUT
            pstmt.setDouble(7, 400); //BALANCE
            pstmt.setInt(8, 10002); //TARGETACC
            
            
            pstmt.executeUpdate();
            
            System.out.println("New Row Inserted...");
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
}
