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



public class BuktiReservasiInvoice {
    
    
    public static String formatRupiah(double amount) {
        Locale indonesia = new Locale("id", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(indonesia);
        return formatter.format(amount).replace("Rp", "Rp. ").trim();
    }
    
    public class Invoice{
        private String kode_reservasi;
        private String no_invoice;
        private String typeroom;
        private String nama_lengkap;
        private String alamat;
        private String tanggal_masuk;
        private String tanggal_keluar;
        private String waktu_bayar;
        private String total_bayar;
        private String total_diskon;
        
        private int jumlah_malam;
        private int harga;
        private int diskon;
        private int telp;
        private int no_identitas;
        
        public Invoice(String kode_reservasi, String no_invoice, String typeroom,
                   String nama_lengkap, String alamat, int jumlah_malam,
                   int harga, int diskon, int telp, int no_identitas, String tgl_masuk, String tgl_keluar,
                    String waktu_bayar) {
            this.kode_reservasi = kode_reservasi;
            this.no_invoice = no_invoice;
            this.typeroom = typeroom;
            this.nama_lengkap = nama_lengkap;
            this.alamat = alamat;
            this.jumlah_malam = jumlah_malam;
            this.harga = harga;
            this.diskon = diskon;
            this.telp = telp;
            this.no_identitas = no_identitas;
            this.tanggal_keluar = tgl_keluar;
            this.tanggal_masuk = tgl_masuk;
            this.waktu_bayar = waktu_bayar;
        }
        
        // Getter methods
        public String getTotal_diskon(){
            if(diskon > 0){
                double total = ((double) diskon / 100) * (harga * jumlah_malam);
                return formatRupiah( total);
            }else{
                return "Rp 0";
            }
        }
        
        public String getTotal_bayar(){
            if(diskon > 0){
                double total = ((double) diskon / 100) * (harga * jumlah_malam);
                return formatRupiah((harga * jumlah_malam) - total).replace("Rp. ","".trim());
            }else{
                return formatRupiah(harga * jumlah_malam).replace("Rp. ","".trim());
            }
        }
        
        public String getWaktu_bayar(){
            return waktu_bayar;
        }
        
        public String getTanggal_masuk(){
            return tanggal_masuk;
        }
        
        public String getTanggal_keluar(){
            return tanggal_keluar;
        }
        
        public String getKode_reservasi() {
            return kode_reservasi;
        }
        
        public String getNo_invoice() {
            int hasil = 0000 + Integer.valueOf(no_invoice);
            return "INV"+String.valueOf(hasil);
        }

        public String getTyperoom() {
            return typeroom;
        }

        public String getNama_lengkap() {
            return nama_lengkap;
        }

        public String getAlamat() {
            return alamat;
        }

        public int getJumlah_malam() {
            return jumlah_malam;
        }

        public String getHarga() {
            return formatRupiah(Double.valueOf(harga)) + "  X" + jumlah_malam;
        }

        public int getDiskon() {
            
            return diskon;
        }

        public int getTelp() {
            return telp;
        }

        public int getNo_identitas() {
            return no_identitas;
        }
        
        
    }
    
    
    public void Reports(){
        try{
            String nem = "src/main/reports/BuktiReservasiInvoice.jrxml";
            Connection conn = new Connect().connect();
            String sql = "SELECT pembayaran.*, book.kode_reservasi, book.total_malam, book.tanggal_masuk, book.tanggal_keluar, book.payment, room.type, room.harga, room.nomor_ruangan, tamu.nama, tamu.no_identitas, tamu.alamat, tamu.telpon, tamu.kelamin, tamu.status, diskon.discount FROM pembayaran LEFT JOIN book ON book.kode_reservasi = pembayaran.kode_reservasi LEFT JOIN room ON room.id = book.room LEFT JOIN tamu ON tamu.no_identitas = book.nik_passport LEFT JOIN diskon ON pembayaran.diskon = diskon.id ORDER BY pembayaran.id DESC LIMIT 1";
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery(sql);
            
            
            List<Invoice> data = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd / MMMM / yyyy", new Locale("id", "ID"));
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
            if(result.next()){
                data.add(new Invoice(
                    result.getString("kode_reservasi"),
                    String.valueOf(result.getInt("id")),
                    result.getString("type"),
                    result.getString("nama"),
                    result.getString("alamat"),
                    result.getInt("total_malam"),
                    result.getInt("harga"),
                    (result.getInt("discount") > 0) ? result.getInt("discount") : 0,
                    result.getInt("telpon"),
                    result.getInt("no_identitas"),
                    String.valueOf(dateFormat.format(result.getTimestamp("tanggal_masuk"))),
                    String.valueOf(dateFormat.format(result.getTimestamp("tanggal_keluar"))),
                    String.valueOf(dateFormat2.format(result.getTimestamp("waktu_bayar")))
                ));
            }
             HashMap<String, Object> parameter = new HashMap<>();                 
             parameter.put("tes", "123");
            
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
        BuktiReservasiInvoice main = new BuktiReservasiInvoice();
        System.out.print(true);
        main.Reports();
    }
    
}
