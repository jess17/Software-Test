/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.softwaretestproject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class Operations {
    
//    public static void main(String[] args){
//        deposit(100, 2);
//    }
    
    public static void deposit(double currBalance, int id, double depositAmt, String accNumb, String name){
        double newBalance = currBalance + depositAmt;
        String query = "UPDATE users set balance=" + newBalance +" where id="+id;
        ExecQuery.exec(query);
        
        String tableName = "history_"+accNumb;
        TransactionOp.insertDepWith(tableName, Integer.parseInt(accNumb), name, "Deposit", "", depositAmt, 0.0, newBalance);
        
        JOptionPane.showMessageDialog(null, "$" +depositAmt + " successfully deposited to account number: " + accNumb, "Successful",3);
    }
    
    public static void withdraw(double currBalance, int id, double withdrawAmt, double creditLine, String accNumb, String name){
        double newBalance = currBalance - withdrawAmt;
//        if(newBalance>=(0-creditLine)){
//            String query = "UPDATE users set balance=" + newBalance +" where id="+id;
//            ExecQuery.exec(query);
//            JOptionPane.showMessageDialog(null, "$" +withdrawAmt + " has been successfully withdrawn from account number: " + accNumb, "Successful",3);
//        }else{
//            System.out.print("Credit Line: "+creditLine);
//            JOptionPane.showMessageDialog(null, "Insufficient Balance", "Error",3);
//        }
        String query = "UPDATE users set balance=" + newBalance +" where id="+id;
        ExecQuery.exec(query);
        
        String tableName = "history_"+accNumb;
        TransactionOp.insertDepWith(tableName, Integer.parseInt(accNumb), name, "Withdraw", "", 0.0, withdrawAmt, newBalance);
        
        JOptionPane.showMessageDialog(null, "$" +withdrawAmt + " has been successfully withdrawn from account number: " + accNumb, "Successful",3);
    }
    
    public static void transfer(double currBalance, int id, double transferAmt, String targetAccNumb, String accNumb, String name, String desc, String targetName){
        double newBalance = currBalance - transferAmt;
        //checks if the target account number exists or not

        if(newBalance>=0){
            String query1 = "UPDATE users set balance=" + newBalance +" where id="+id;
            ExecQuery.exec(query1);
            System.out.println("Withdrew from id: " + id);

            double targetCurrBalance = getBalance(targetAccNumb);
            double targetAccNewBalance = targetCurrBalance + transferAmt;
            String query2 = "UPDATE users set balance=" + targetAccNewBalance +" where accNumb="+targetAccNumb;
            ExecQuery.exec(query2);
            System.out.println("Transfered to account number: " + targetAccNumb);
            
            String tableName = "history_"+accNumb;
            TransactionOp.insertTrans(tableName, Integer.parseInt(accNumb), name, "Transfer", desc, 0.0, transferAmt, newBalance, Integer.parseInt(targetAccNumb), targetName, targetAccNewBalance);
            
            JOptionPane.showMessageDialog(null, "$" +transferAmt + " has been successfully transfered to account number: " + targetAccNumb, "Successful",3);
        }else{
            JOptionPane.showMessageDialog(null, "Insufficient Balance", "Error",3);
        }
    }
    
    public static double getBalance(String accNumb){
        double balance=0.0;
        PreparedStatement st;
        ResultSet rs;

        String query = "SELECT * FROM users WHERE accNumb=?";

        try{
            st = DerbyConn.getConnection().prepareStatement(query);
            int accNum = Integer.parseInt(accNumb);
            st.setInt(1, accNum);
            rs = st.executeQuery();

            while(rs.next()){
                balance = rs.getDouble("balance");
            }
        }catch (SQLException ex) {
//            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
              System.out.println(ex);
              JOptionPane.showMessageDialog(null, ex,"Get Balance Error",2);
        }
        
        return balance;
    }
    
    //Not used
    public static boolean isExist(String accNumb){
        PreparedStatement st;
        ResultSet rs;
        
        String query = "SELECT * FROM users WHERE accNumb=?";
        
        boolean exist = false;
        try{
            st = DerbyConn.getConnection().prepareStatement(query);
            st.setString(1, accNumb);
            rs = st.executeQuery();

            if(rs.next()){
                return true;
            }else{
                return false;
            }
        }catch (SQLException ex) {
//            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
              System.out.println(ex);
              JOptionPane.showMessageDialog(null, ex,"Account Nmber doesn't exist error",2);
        }
        return exist;
    }
   
}
