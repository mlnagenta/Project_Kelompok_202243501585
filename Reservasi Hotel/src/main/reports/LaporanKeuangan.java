/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.reports;

/**
 *
 * @author Samsoe-PC
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

public class LaporanKeuangan {    
    
    public static String formatRupiah(double amount) {
        Locale indonesia = new Locale("id", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(indonesia);
        return formatter.format(amount).replace("Rp", "Rp. ").trim();
    }
    
    public class DataSet{
        private String bulan;
        private String total_pendapatan_f;
        private int total_pendapatan;
        private int total_reservasi;
        private String totalAll;
    
        public DataSet(
             String bulan,
             String total_p_f,
             int total_r,
             int total_p,
             String totalAll
        ){
            this.bulan = bulan;
            this.total_pendapatan_f = total_p_f;
            this.total_pendapatan = total_p;
            this.total_reservasi = total_r;
            this.totalAll = totalAll;
        }
        
        public String getTotalAll(){
            return totalAll;
        }
        
        public int getTotal_pendapatan(){
            return total_pendapatan;
        }
        
        public String getBulan(){
            return bulan;
        }
        
        public String getTotal_pendapatan_f(){
            return total_pendapatan_f;
        }
        
        public int getTotal_reservasi(){
            return total_reservasi;
        }
    }
    
    public void Reports(){
        try{
            String nem = "src/main/reports/LaporanKeuangan.jrxml";
            Connection conn = new Connect().connect();
            String sql = "SELECT DATE_FORMAT(pembayaran.waktu_bayar, '%M') AS bulan, COUNT(book.id) AS jumlah_reservasi, FORMAT(SUM( CASE WHEN pembayaran.diskon IS NULL THEN room.harga * book.total_malam ELSE ( diskon.discount / 100) * (room.harga * book.total_malam) END ), 0) AS total_pendapatan_f, SUM( CASE WHEN pembayaran.diskon IS NULL THEN room.harga * book.total_malam ELSE (diskon.discount/ 100) * (room.harga * book.total_malam) END ) AS total_pendapatan FROM pembayaran LEFT JOIN book ON book.kode_reservasi = pembayaran.kode_reservasi LEFT JOIN diskon ON diskon.id = pembayaran.diskon LEFT JOIN room ON room.id = book.room GROUP BY DATE_FORMAT(pembayaran.waktu_bayar, '%M') ORDER BY bulan";
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery(sql);
            HashMap<String, Object> parameter = new HashMap<>();                 
            parameter.put("tes", "123");
            int total = 0;
            List<DataSet> data = new ArrayList<>();
            while(result.next()){
                data.add(new DataSet(
                      result.getString("bulan"),
                      result.getString("total_pendapatan_f"),
                      result.getInt("jumlah_reservasi"),                      
                      result.getInt("total_pendapatan"),
                      formatRupiah(Double.valueOf(total += result.getInt("total_pendapatan")))
                ));
                
            }            
            System.out.println(total);
            // Create JRBeanCollectionDataSource for JasperReports
            JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(data);
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
        }catch(Exception err){
            System.out.println(err);
        }        
        
    }
    
    public static void main(String[] args){
        LaporanKeuangan main = new LaporanKeuangan();
        main.Reports();
    }
    
    
}
