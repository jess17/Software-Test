/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.softwaretestproject.test;

import static atm.softwaretestproject.DerbyConn.getConnection2;
import static atm.softwaretestproject.test.CreateInsertDB.getConnection;
import static atm.softwaretestproject.test.transDBTest.getConnectionTrans;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ASUS
 */
public class ExecuteQuery {
    
    public static void main(String args[]) {
//        String createSQL = "ALTER TABLE users MODIFY COLUMN accNumb BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 10000000, INCREMENT BY 1)";
        
        String createSQL = "CREATE TABLE users ("
        + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
        + "userType VARCHAR(10) NOT NULL, accNumb BIGINT, password VARCHAR(40),"
        + "name VARCHAR(100), accType VARCHAR(100), balance DOUBLE, interestRate DOUBLE, "
        + "creditLine DOUBLE, transFee DOUBLE, dateCreated TIMESTAMP, "
        + "CONSTRAINT primary_key PRIMARY KEY (id))";
//        createTable(createSQL);
        exec();
    }
    
    public static void exec(){
        Connection conn = null;
        Statement stmt;
        PreparedStatement pstmt;
        
//        String query = "DBCC CHECKIDENT (users, RESEED, 100000002)";
//        String query = "ALTER TABLE users ALTER IDENTITY RESTART WITH 3";
        
        try{
            conn = getConnection2();
            stmt = conn.createStatement();
            
            String query = "DELETE FROM history_100000007 ";
            
            pstmt = conn.prepareStatement(query);
//            int accNumb = 10001;
//            String tableName = "history_" + Integer.toString(accNumb);
//            pstmt.setString(1, tableName);
//            stmt.execute(query);
            
            pstmt.execute();
            System.out.println("Query executed");
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
}
