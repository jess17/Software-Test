/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.softwaretestproject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class AccSearchOperations {

    public static void fillTable(JTable table, String key, String sortBy){
        Connection conn;
        PreparedStatement ps;
        
        try{
            conn = DerbyConn.getConnection();
//            String query = "SELECT accNumb, \"NAME\", balance, accType, dateCreated FROM users "
//                    + "WHERE accNumb|| \"NAME\"|| balance|| accType|| dateCreated"
//                    + "LIKE '%%'";

            key = key.toLowerCase();
            String query = "SELECT accNumb, \"NAME\", balance, accType, dateCreated "
                    + "FROM users WHERE id>1 AND LOWER(\"NAME\") LIKE '%"+ key+"%' ORDER BY " + sortBy;
            ps = conn.prepareStatement(query);

//            ps.setString(1, "%"+key+"%" );

            ResultSet rs = ps.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            
            Object[] row;
            
            while(rs.next()){
                row = new Object[5];
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                Date d = rs.getDate(5);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                row[4] = dateFormat.format(d);
                model.addRow(row);
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public static void fillTableFilter(JTable table, String key, String sortBy, String filter){
        Connection conn;
        PreparedStatement ps;
        
        try{
            conn = DerbyConn.getConnection();

            key = key.toLowerCase();
            
            String query = "SELECT accNumb, \"NAME\", balance, accType, dateCreated "
                    + "FROM users WHERE id>1 AND LOWER(\"NAME\") AND " + filter + " LIKE '%"+ key+"%' ORDER BY " + sortBy;
            
            ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            
            Object[] row;
            
            while(rs.next()){
                row = new Object[5];
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                Date d = rs.getDate(5);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                row[4] = dateFormat.format(d);
                model.addRow(row);
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
}
