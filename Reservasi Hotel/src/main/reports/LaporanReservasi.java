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

public class LaporanReservasi {
    
    public class DataSet{
        private String kode;
        private String type;
        private String status;
        private String nama;
        private String malam;
        private String identitas;
        
        public DataSet(
                String kode,
                String type,
                String nama,
                String status,
                String malam,
                String identitas
        ){
          this.kode = kode;
          this.type = type;
          this.nama = nama;
          this.status = status;
          this.malam = malam;
          this.identitas = identitas;  
        }
        
        public String getKode() {
        return kode;
    }

        public String getType() {
            return type;
        }

        public String getStatus() {
            return status;
        }

        public String getNama() {
            return nama;
        }

        public String getMalam() {
            return malam;
        }

        public String getIdentitas() {
            return identitas;
        }
    }
    
    public void Reports(){
        try{
            String nem = "src/main/reports/LaporanReservasi.jrxml"; 
            Connection conn = new Connect().connect();
            String sql = "SELECT book.*, tamu.nama, tamu.no_identitas, CASE WHEN book.payment IS NOT NULL THEN 'CONFIRM' ELSE 'NOT CONFIRM' END AS status, room.type FROM book LEFT JOIN tamu ON tamu.no_identitas = book.nik_passport LEFT JOIN room ON room.id = book.room LEFT JOIN pembayaran ON pembayaran.kode_reservasi = book.kode_reservasi ORDER BY book.id";
             Statement state = conn.createStatement();
            ResultSet result = state.executeQuery(sql);
            
            
            List<DataSet> data = new ArrayList<>();
            while(result.next()){
                data.add(new DataSet(
                   result.getString("kode_reservasi"),
                   result.getString("type"),
                   result.getString("nama"),
                   result.getString("status"),
                   String.valueOf(result.getInt("total_malam")),
                   String.valueOf(result.getInt("nik_passport"))
                ));
            }
            HashMap<String, Object> parameter = new HashMap<>();
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
        LaporanReservasi main = new LaporanReservasi();
        main.Reports();
    }
}
