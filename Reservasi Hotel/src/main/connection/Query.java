/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.connection;

/**
 *
 * @author Samsu
 */

import main.connection.Connect;
import java.sql.*;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;


public class Query {
    private Connection conn = new Connect().connect();
    
    public boolean UpdateData(String nr,int kapasitas,long harga, String type, int jumlah_kamar){
        try{
            String sql = "UPDATE room SET kapasitas = ?, harga = ?, jumlah_kamar = ?, type = ? WHERE nomor_ruangan = ?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setLong(2, harga);
            state.setInt(1, kapasitas);
            state.setInt(3, jumlah_kamar);
            state.setString(4, type);
            state.setString(5,nr);
            state.executeUpdate();
            return true;
        }catch(Exception er){
            return false;
        }
    }
    
    public boolean InsertDataRoom(String nr,int kapasitas,long harga, String type, int jumlah_kamar){
        try{
            String sql = "INSERT INTO room (kapasitas,harga,jumlah_kamar,nomor_ruangan,type) VALUES (?,?,?,?,?)";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setLong(2, harga);
            state.setInt(1, kapasitas);
            state.setInt(3, jumlah_kamar);
            state.setString(4,nr);
            state.setString(5, type);
            state.executeUpdate();            
            return true;
        }catch(Exception er){
            System.out.println(er);
            return false;
        }
    }
    
    public boolean DeleteRoom(String nr){
         try{
            String sql = "DELETE FROM room WHERE nomor_ruangan = ?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1,nr);
            state.executeUpdate();
            return true;
         }catch(Exception er){
             return false;
         }
    }
    
    public boolean InsertDataDiskon(String kode,int diskon,int jumlah_pengguna){
        try{
            String sql = "INSERT INTO diskon (kode,discount,max) VALUES (?,?,?)";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setLong(2, diskon);
            state.setString(1, kode);
            state.setInt(3, jumlah_pengguna);
            state.executeUpdate();            
            return true;
        }catch(Exception er){
            System.out.println(er);
            return false;
        }
    }
    
    public boolean UpdateDataDiskon(int id,String kode,int discount, int max){
        try{
            String sql = "UPDATE diskon SET kode = ?, discount = ?, max = ? WHERE id = ?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setInt(2, discount);
            state.setString(1, kode);
            state.setInt(3, max);
            state.setInt(4, id);
            state.executeUpdate();
            return true;
        }catch(Exception er){
            return false;
        }
    }
    public boolean InsertDataTamu(int no_identitas,boolean kelamin,String nama,int telpon, String alamat){
        try{
            String sql = "INSERT INTO tamu (nama,no_identitas,kelamin,telpon,alamat) VALUES (?,?,?,?,?)";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setInt(2, no_identitas);
            state.setString(1, nama);
            state.setBoolean(3, kelamin);           
            state.setInt(4,telpon);
            state.setString(5, alamat);
            state.executeUpdate();            
            return true;
        }catch(Exception er){
            System.out.println(er);
            return false;
        }
    }
    
    public boolean UpdateDataTamu(int no_identitas,boolean kelamin,String nama,int telpon, String alamat,int ids){
        try{
            String sql = "UPDATE tamu SET nama=?,no_identitas=?,kelamin=?,telpon=?,alamat=? WHERE id=?";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1, nama);
            state.setInt(2, no_identitas);            
            state.setBoolean(3, kelamin);           
            state.setInt(4,telpon);
            state.setString(5, alamat);
            state.setInt(6, ids);
            state.executeUpdate();            
            return true;
        }catch(Exception err){
            System.out.println(err);
            return false;
        }
    }
    
    public boolean CheckTamuOnReservasi(int nik_pas){
        try{
            String sql = "SELECT * FROM tamu WHERE no_identitas = ? AND status IS NULL";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setInt(1,nik_pas);
            ResultSet result = state.executeQuery();
            if(result.next()){                
                return true;
            }else{
                return false;
            }            
        }catch(Exception err){
            System.out.println(err);
            return false;
        }
    }
    
    public boolean InsertDataReservasi(String kode,int nik_pas, long tgl_masuk, int lama_nginap,int kodeKamar,long tgl_keluar){
        try{
            String sql = "INSERT INTO book (kode_reservasi,nik_passport,total_malam,tanggal_masuk,tanggal_keluar,room) VALUES(?,?,?,?,?,?)";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1,kode);
            state.setInt(2, nik_pas);
            state.setInt(3, lama_nginap);
            state.setTimestamp(4,new Timestamp(tgl_masuk));
            state.setTimestamp(5, new Timestamp(tgl_keluar));
            state.setInt(6, kodeKamar);
            int rowsAffected = state.executeUpdate();
            return rowsAffected > 0;
        }catch(Exception er){
            System.out.println(er);
            return false;
        }
    }
     public boolean InsertDataPembayaran(String kodereservasi,int diskon,String jenispembayaran){
         
        try{
            String sql = "INSERT INTO pembayaran (kode_reservasi,diskon,jenis_bayar,waktu_bayar) VALUES(?,?,?,?)";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1,kodereservasi);
            if(diskon > 0){
                state.setInt(2, diskon);
            }else{
                state.setNull(2, diskon);
            }
            state.setString(3, jenispembayaran);
            
            ZoneId zoneId = ZoneId.of("Asia/Jakarta");
            ZonedDateTime nowWIB = ZonedDateTime.now(zoneId);
            Timestamp timestamp = Timestamp.from(nowWIB.toInstant());
            state.setTimestamp(4,timestamp);
                    
            int rowsAffected = state.executeUpdate();
            if(rowsAffected > 0){
                String sql2 = "UPDATE book SET payment = 'SUKSES' WHERE kode_reservasi = ?";
                PreparedStatement state2 = conn.prepareStatement(sql2);
                state2.setString(1, kodereservasi);
                int rowsAffected2 = state2.executeUpdate();
                return rowsAffected2 > 0;
            }
            return false;
        }catch(Exception er){
            System.out.println(er);
            return false;
        }
    }
    public boolean Checkkodereservasi(String kode){
    try{
            String sql = "SELECT * FROM book where kode_reservasi = ? ";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1,kode);
            ResultSet result = state.executeQuery();
            if(result.next()){                
                return true;
            }else{
                return false;
            }            
        }catch(Exception err){
            System.out.println(err);
            return false;
        }
    }
    public boolean CheckIn(String kode){
        try{
            String sql = "SELECT * FROM book WHERE kode_reservasi = ? AND payment IS NOT NULL";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1,kode);
            ResultSet result = state.executeQuery();
            if(result.next()){                
                int jumlahdata = result.getInt(1);
                if(jumlahdata>1){
                    return true;
                   
                }else{return false;}
            }else{
                return false;
            }            
        }catch(Exception err){
            System.out.println(err);
            return false;
        }
    }
    
    
    
    
    
    
    // Discount
    public class GetDisc{
            private String kode;
            private int id;
            private int disc;
            private int max;
            
            public GetDisc(
               String kode,
               int id,
               int disc,
               int max
            ){
                this.kode = kode;
                this.id = id;
                this.disc = disc;
                this.max = max;
            }
            
            public String getKode(){
                return kode;                
            }
            public int getId(){
                return id;
            }
            public int getDisc(){
                return disc;
            }
            public int getMax(){
                return max;
            }
    }
    
    public GetDisc GetKodeDiskon(String kode){
        
        try{
            String sql = "SELECT * FROM diskon WHERE kode = ? AND max > 0";
            PreparedStatement state = conn.prepareStatement(sql);
            state.setString(1,kode);
            ResultSet result = state.executeQuery();
            
            if(result.next()){                
                int jumlahdata = result.getInt(1);
                if(jumlahdata>1){
                    GetDisc main = new GetDisc(
                       result.getString("kode"),
                       result.getInt("id"),
                       result.getInt("discount"),
                       result.getInt("max")
                    );
                    return main;
                   
                }else{return null;}
            }else{
                return null;
            }  
            
        }catch(Exception err){
            return null;
        }
    }
    
    public boolean UpdateStatusCheckIn(String kode){
        try{
            String sql = "SELECT nik_passport FROM book WHERE kode_reservasi = ? AND payment IS NOT NULL";
            PreparedStatement  state1 = this.conn.prepareStatement(sql);
            state1.setString(1, kode);
            ResultSet hasil2 = state1.executeQuery();
            if(hasil2.next()){
                String sql2 = "UPDATE tamu SET status = 'IN' WHERE no_identitas = ?";
                PreparedStatement state = this.conn.prepareStatement(sql2);
                state.setString(1, hasil2.getString("nik_passport"));
                int hasil = state.executeUpdate();
                return hasil > 0;
            }else{
                return false;
            }
            
            
        }catch(Exception err){
            System.out.println(err);
            return false;
        }
    }
}
