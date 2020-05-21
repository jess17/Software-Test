/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.softwaretestproject;

import static atm.softwaretestproject.test.CreateInsertDB.getConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ASUS
 */
public class ExecQuery {
    public static void exec(String query){
        Connection conn = null;
        Statement stmt;
        
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            stmt.execute(query);
            
            System.out.println("Query:" + query + " executed");
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
}
