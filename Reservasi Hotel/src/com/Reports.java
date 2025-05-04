/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com;

/**
 *
 * @author Samsu
 */

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import main.connection.Connect;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;

public class Reports {
    
    public class ReportTest{
        private String nama;
        private String umur;
        
        public ReportTest(String nama,String umur){
            this.nama = nama;
            this.umur = umur;
        }
        
        public String getNama() {
            return nama;
        }
        
        public String getUmur() {
            return umur;
        } 
    }
    
    
   
   
    
    
    public void TestReports(){
       
    }
    
   
    
    
    
    
    public static void main(String[] args){
         Reports tes = new Reports();
         System.out.println(2); 

    }
    
}
