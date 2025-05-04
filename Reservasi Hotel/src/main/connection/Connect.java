/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.connection;

/**
 *
 * @author Samsu
 */
import java.sql.*;

public class Connect {
    private Connection conn;
    public Connection connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Berhasil Connect");            
        }catch(Exception ex){
            System.out.println("Gagal Connect");  
        }
        String url = "jdbc:mysql://localhost/reservasi";
        try{
            conn = DriverManager.getConnection(url,"root","");
            System.out.println("Berhasil Connect Database");        
        }catch(Exception ex){
            System.out.println("Gagal Connect Database"); 
        }
        return conn;
    }
    
}
