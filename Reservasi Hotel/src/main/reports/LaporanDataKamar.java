/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.reports;

/**
 *
 * @author Samsoe
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

public class LaporanDataKamar {
     public class ReportDataKamar{
        private String nomor_ruangan;
        private int kapasitas;
        private int jumlah_kamar;
        private String type;
        private int harga;
        private int tersedia;
        
        public ReportDataKamar(
        String nomor_ruangan,
        int kapasitas,
        int jumlah_kamar,
        String type,
        int harga,
        int tersedia
        ){
            this.nomor_ruangan = nomor_ruangan;
            this.kapasitas = kapasitas;
            this.jumlah_kamar = jumlah_kamar;
            this.type = type;
            this.harga = harga;
            this.tersedia = tersedia;
        }
        
        public String getNomor_ruangan(){
            return nomor_ruangan;
        }
        public int getKapasitas(){
            return kapasitas;
        }
        public int getJumlah_kamar(){
            return jumlah_kamar;
        }
        public String getType(){
            return type;
        }
        public int getHarga(){
            return harga;
        }
        public int getTersedia(){
            return tersedia;
        }
    }
     
    public void Laporan(){
        try {
            String nem = "src/main/reports/LaporanDataKamar.jrxml";  // Correct the path to match your project
            Connection conn = new Connect().connect();  // Ensure your Connect class works correctly
            String sql = "SELECT room.*, room.kapasitas, room.jumlah_kamar, room.type, room.harga, (room.jumlah_kamar - COUNT(CASE WHEN book.payment IS NOT NULL THEN book.id END)) AS room_tersisa FROM room LEFT JOIN book ON book.room = room.id GROUP BY room.id, room.kapasitas, room.jumlah_kamar, room.type, room.harga;";            
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery(sql);
            
            
            List<ReportDataKamar> dataKamar = new ArrayList<>();
            while(result.next()){
                dataKamar.add(new ReportDataKamar(
                   result.getString("nomor_ruangan"),
                   result.getInt("kapasitas"),
                   result.getInt("jumlah_kamar"),
                   result.getString("type"),
                   result.getInt("harga"),
                   result.getInt("room_tersisa")
                ));
            }
            HashMap<String, Object> parameter = new HashMap<>();
            parameter.put("id", 1);
            
            // DataResource
           
            
            
            
            // Create JRBeanCollectionDataSource for JasperReports
            JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(dataKamar);
            // Compile the .jrxml to .jasper
            File reportFile = new File(nem);
            if (!reportFile.exists()) {
                System.out.println("Report file not found: " + reportFile.getPath());
                return;
            }
            
            // Compile report to JasperReport object
            JasperReport jasperReport = JasperCompileManager.compileReport(reportFile.getPath());
            
            // Fill the report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, dataSource2);
            
            // Display the report in a viewer
            JasperViewer.viewReport(jasperPrint, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
            
        } catch (Exception err) {
            System.out.println(err);
            err.printStackTrace();  // Print the full stack trace for better debugging
        }
    }
    
    public static void main(String args[]){
        LaporanDataKamar main = new LaporanDataKamar();
     //   main.Laporan();
    }
}
