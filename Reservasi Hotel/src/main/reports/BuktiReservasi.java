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

public class BuktiReservasi {
    
    public static String formatRupiah(double amount) {
        Locale indonesia = new Locale("id", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(indonesia);
        return formatter.format(amount);
    }
    public class ReportDataBuktiReservasi{
        private String kode_reservasi;
        private String nama;
        private String kelamin;
        private String tanggal_buat;
        private String tanggal_masuk;
        private String tanggal_keluar;
        private String type;
        private String harga;
        private String kapasitas;
        private String no_identitas;
        private String telpon;
        
        public ReportDataBuktiReservasi(
            String kode,
            String nama,
            boolean kelamin,
            Timestamp tanggalMasuk,
            Timestamp tanggalKeluar,
            String type,
            int harga,
            int kapasitas,
            int noIdentitas,
            int telp
        ){
            this.kode_reservasi = kode;
            this.nama = nama;
            this.type = type;
            
            
            if(kelamin){
                this.kelamin = "Laki-Laki";
            }else{
                this.kelamin = "Perempuan";
            }
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd / MMMM / yyyy", new Locale("id", "ID"));
            Timestamp tanggalBuat = Timestamp.valueOf("2025-01-12 00:00:00");
            this.tanggal_buat = String.valueOf(dateFormat.format(tanggalBuat));
            this.tanggal_keluar = String.valueOf(dateFormat.format(tanggalKeluar));
            this.tanggal_masuk = String.valueOf(dateFormat.format(tanggalMasuk));
            
            this.harga = String.valueOf(formatRupiah(Double.valueOf(harga))) + " / Malam";
            this.kapasitas = String.valueOf(kapasitas);
            this.no_identitas = String.valueOf(noIdentitas);
            this.telpon = String.valueOf(telp);
            
        }
        
        
        public String getKode_reservasi() {
            return kode_reservasi;
        }

        public String getNama() {
            return nama;
        }

        public String getKelamin() {
            return kelamin;
        }

        public String getTanggal_buat() {
            return tanggal_buat;
        }

        public String getTanggal_masuk() {
            return tanggal_masuk;
        }

        public String getTanggal_keluar() {
            return tanggal_keluar;
        }

        public String getType() {
            return type;
        }

        public String getHarga() {
            return harga;
        }

        public String getKapasitas() {
            return kapasitas;
        }

        public String getNo_identitas() {
            return no_identitas;
        }

        public String getTelpon() {
            return telpon;
        }
        
        
    }
        
     public void ReportBuktiBooking(){
         try {
            String nem = "src/main/reports/BuktiReservasi.jrxml";  // Correct the path to match your project
            Connection conn = new Connect().connect();  // Ensure your Connect class works correctly            
            String sql2 = "SELECT book.kode_reservasi, book.room, book.total_malam, book.tanggal_masuk, book.tanggal_keluar, book.payment, room.type, room.kapasitas, room.harga, room.nomor_ruangan, tamu.nama, tamu.no_identitas, tamu.alamat, tamu.telpon, tamu.kelamin FROM book LEFT JOIN room ON room.id = book.room LEFT JOIN tamu ON tamu.no_identitas = book.nik_passport ORDER BY book.id DESC LIMIT 1";
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery(sql2);
            
            
            List<ReportDataBuktiReservasi> dataKamar = new ArrayList<>();
            if(result.next()){
                dataKamar.add(new ReportDataBuktiReservasi(
                    result.getString("kode_reservasi"),
                    result.getString("nama"),
                    result.getBoolean("kelamin"),
                    result.getTimestamp("tanggal_masuk"),
                    result.getTimestamp("tanggal_keluar"),
                    result.getString("type"),
                    result.getInt("harga"),
                    result.getInt("kapasitas"),
                    result.getInt("no_identitas"),
                    result.getInt("telpon")
                ));
                System.out.println(result.getInt("telpon"));
            }

            HashMap<String, Object> parameter = new HashMap<>();                 
                        
            
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
            err.printStackTrace();  // Print the full stack trace for better debugging
        }
    }
    
}
