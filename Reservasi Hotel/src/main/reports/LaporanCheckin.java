/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.reports;

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

public class LaporanCheckin {
    
    
    public class DataCheckinSource{
        private String nama_pengguna;
        private String status;
        private String nik;
        
        public DataCheckinSource(String nama_pengguna, String status, String nik) {
            this.nama_pengguna = nama_pengguna;
            if(status != null){
                this.status = status;
            }else{
                this.status = "Belum Check In";
            }
            this.nik = nik;
        }

        // Getter dan Setter untuk nama_pengguna
        public String getNama_pengguna() {
            return nama_pengguna;
        }

        public void setNama_pengguna(String nama_pengguna) {
            this.nama_pengguna = nama_pengguna;
        }

        // Getter dan Setter untuk status
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        // Getter dan Setter untuk nik
        public String getNik() {
            return nik;
        }

        public void setNik(String nik) {
            this.nik = nik;
        }
    }
    
    public void Laporan(){
        try {
            String nem = "src/main/reports/LaporanCheckin.jrxml";  // Correct the path to match your project
            Connection conn = new Connect().connect();  // Ensure your Connect class works correctly
            String sql = "SELECT * FROM tamu";
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery(sql);
            
            
            List<DataCheckinSource> dataKamar = new ArrayList<>();
            while(result.next()){
                dataKamar.add(new DataCheckinSource(
                   result.getString("nama"),
                   result.getString("status"),
                   result.getString("no_identitas")
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
    
}
