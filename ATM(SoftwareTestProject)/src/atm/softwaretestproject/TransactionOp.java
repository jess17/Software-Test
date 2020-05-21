/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.softwaretestproject;

import static atm.softwaretestproject.test.transDBTest.getConnectionTrans;
import static atm.softwaretestproject.test.transDBTest.insert;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class TransactionOp {
    public static void main(String args[]) {
//        getConnection();
        
//        createTable(createSQL);
        
//        insert();
    }
    
    public static void createTable(int accNumb){
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
        
        Connection conn = null;
        Statement stmt;
        PreparedStatement pstmt;
        
        try{
            conn = DerbyConn.getConnection2();
            stmt = conn.createStatement();
            stmt.execute(createSQL);
            
            System.out.println("Table Created...");
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
    
    public static void insertDepWith(String tableName, int accNumb, String name, String type, 
                                String description, Double amt_in, Double amt_out, Double balance){
        Connection conn = null;
        PreparedStatement pstmt;
        Statement stmt;
        ResultSet rs = null;
        
        try{
            String SQL = "INSERT INTO "+tableName +"("
                    + "ACCNUMB, "
                    + "\"NAME\", "
                    + "TYPE, "
                    + "DESCRIPTION, "
                    + "AMT_IN, "
                    + "AMT_OUT, "
                    + "BALANCE, "
                    + "DATE)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
            conn = getConnectionTrans();
            pstmt = conn.prepareStatement(SQL);

            
            pstmt.setInt(1, accNumb); //ACCNUMB
            pstmt.setString(2, name); //NAME
            pstmt.setString(3, type); //TYPE
            pstmt.setString(4, description); //DESCRIPTION 
            pstmt.setDouble(5, amt_in); //AMT_IN
            pstmt.setDouble(6, amt_out); //AMT_OUT
            pstmt.setDouble(7, balance); //BALANCE
            
            pstmt.executeUpdate();
            
            System.out.println("New Row Inserted...");
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
    
    public static void insertTrans(String tableName, int accNumb, String name, String type, 
                                String description, Double amt_in, Double amt_out, Double balance, int targetAcc, String targetName, Double targetNewBalance){
        Connection conn = null;
        PreparedStatement pstmt;
        Statement stmt;
        ResultSet rs = null;
        
        try{
            String SQL = "INSERT INTO "+tableName +"("
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

            
            pstmt.setInt(1, accNumb); //ACCNUMB
            pstmt.setString(2, name); //NAME
            pstmt.setString(3, type); //TYPE
            pstmt.setString(4, description); //DESCRIPTION 
            pstmt.setDouble(5, amt_in); //AMT_IN
            pstmt.setDouble(6, amt_out); //AMT_OUT
            pstmt.setDouble(7, balance); //BALANCE
            pstmt.setInt(8, targetAcc); //TARGETACC
            
            
            pstmt.executeUpdate();
            
            System.out.println("New Row Inserted...(current acc number)");
            
            String targetTableName = "history_" + targetAcc;
            insertDepWith(targetTableName, targetAcc, targetName, type, description, amt_out, amt_in, targetNewBalance);
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
    
    public static void fillTable(JTable table, String accNumb){
        Connection conn;
        PreparedStatement ps;
        
        try{
            conn = DerbyConn.getConnection2();

            String tableName = "history_"+accNumb;
            
            String query = "SELECT date, description, amt_in, amt_out, balance "
                    + "FROM "+tableName+" ORDER BY date";
            
            ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            
            Object[] row;
            
            while(rs.next()){
                row = new Object[5];
                Date d = rs.getDate(1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                row[0] = dateFormat.format(d);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                
                model.addRow(row);
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public String getName(String accNumb){
        String name="";
        PreparedStatement st;
        ResultSet rs;

        String query = "SELECT * FROM users WHERE accNumb=?";

        try{
            st = DerbyConn.getConnection().prepareStatement(query);
            
            st.setInt(1, Integer.parseInt(accNumb));
            rs = st.executeQuery();

            while(rs.next()){
                name = rs.getString("name");
            }
        }catch (SQLException ex) {
//            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
              System.out.println(ex);
              JOptionPane.showMessageDialog(null, ex,"Get Name Error",2);
        }
        
        return name;
    
    }
        
}
