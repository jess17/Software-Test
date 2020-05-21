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

public class ShowHist {
    public static void main(String[] args) {
      show();
   }
    public static void show(){
        Connection conn = null;
        Statement stmt;
        ResultSet rs = null;
        
        try{
            conn = atm.softwaretestproject.DerbyConn.getConnection3("lalala");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM history_10001");
            
            System.out.printf("%10s %10s %20s %10s %25s %20s %10s %10s %10s %30s \n",
               "id", "accNumb", "name", "type", "description","amt_in","amt_out"
               ,"balance","targetAcc", "date");
            
            while (rs.next()) {
               System.out.printf("%10d %10s %20s %10s %25s %20s %10s %10s %10s %30s \n",
               rs.getInt(1), rs.getString(2),
               rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
               rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
        
    }
}
