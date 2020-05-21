/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package atm.softwaretestproject;
//
///**
// *
// * @author ASUS
// */
//public class ATMSoftwareTestProject {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        // TODO code application logic here
//    }
//    
//}

package atm.softwaretestproject.test;

public class test{
    
    
    
    static int a=100; 
    public static void main(String[] args){
//        int a=100;

        sth(600);
        System.out.println(a);
    }
    
    public static void sth(int a){
        test.a = a;
        System.out.println(a);
    }
    
    
}
