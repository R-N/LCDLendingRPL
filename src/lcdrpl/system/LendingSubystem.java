/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lcdrpl.system;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import lcdrpl.Util;
import lcdrpl.database.CallableStatement;
import lcdrpl.database.Database;
import lcdrpl.database.PreparedStatement;
import lcdrpl.database.ResultSet;
import lcdrpl.system.structs.FilterPeminjaman;
import lcdrpl.system.structs.KelasPinjam;
import lcdrpl.system.structs.Peminjaman;
import lcdrpl.system.structs.RiwayatPeminjaman;

/**
 *
 * @author Lenovo2
 */
public class LendingSubystem {
    public static RiwayatPeminjaman[] getRiwayatPeminjaman(FilterPeminjaman filter){
        RiwayatPeminjaman[] ret = null;
        String query = "SELECT "
                + String.join(", ", 
                        new String[]{
                            "P.Nomor_Peminjaman",
                            "P.Status_Peminjaman",
                            "P.Nomor_LCD",
                            "P.Nomor_Kabel",
                            "P.Waktu_Pinjam",
                            "P.Nama_Peminjam",
                            "P.Waktu_Harus_Kembali",
                            "P.Waktu_Kembali",
                            "(B.Keterangan_Blacklist IS NOT NULL) AS No_HP_Terblacklist"
                        }
                    )
                + " FROM Peminjaman P"
                + " LEFT JOIN Blacklist B"
                + " ON P.Nomor_HP_Peminjam=B.Nomor_HP_Blacklist";
        
        if(filter != null){
            List<String> where = new LinkedList<String>();
    
            if(filter.status != null) where.add("P.Status_Peminjaman=?");
            if(filter.lcd != null) where.add("P.Nomor_LCD=?");
            if(filter.kabel != null) where.add("P.Nomor_Kabel=?");
            if(!Util.isNullOrEmpty(filter.namaPeminjam)) where.add("P.Nama_Peminjam=?");
            if(!Util.isNullOrEmpty(filter.nimPeminjam)) where.add("P.Nama_Peminjam=?");
            if(!Util.isNullOrEmpty(filter.keterangan)) where.add("P.Keterangan_Peminjaman=?");
            if(
                    filter.rangeWaktu != null
                    && filter.rangeWaktu.length == 2
                    && filter.rangeWaktu[0] != null
                    && filter.rangeWaktu[1] != null
                ){
                where.add(
                        "P.Waktu_Pinjam BETWEEN ? AND ?"
                        + " OR (P.Waktu_Kembali IS NULL AND P.Waktu_Harus_Kembali BETWEEN ? AND ?)"
                        + " OR (P.Waktu_Kembali IS NOT NULL AND P.Waktu_Kembali BETWEEN ? AND ?)"
                );
            }
            if(
                    !Util.isNullOrEmpty(filter.namaMatkul)
                    || !Util.isNullOrEmpty (filter.namaDosen)
                    || !Util.isNullOrEmpty(filter.ruangKelas)
                ){
                List<String> where2 = new LinkedList<String>();
                where2.add("KP.Nomor_Peminjaman=P.Nomor_Peminjaman");
                if(!Util.isNullOrEmpty(filter.namaMatkul)) where2.add("KP.Keterangan_Peminjaman=?");
                if(!Util.isNullOrEmpty(filter.namaDosen)) where2.add("KP.Nama_Dosen=?");
                if(!Util.isNullOrEmpty(filter.ruangKelas)) where2.add("KP.Nama_Matkul=?");
                
                where.add("EXISTS (SELECT * FROM Kelas_Pinjam KP WHERE " + String.join (" AND ", where2) + ")");
            }
            if(filter.noHPTerblacklist != null) where.add("(B.Keterangan_Blacklist IS NULL) IS ?");
            
            if(where.size() > 0){
                query += " WHERE " + String.join(" AND ", where);
            }
            
        }
        
        query += " ORDER BY P.Waktu_Pinjam";
        
        if(filter != null){
            if(filter.limit != null) query += " LIMIT ?";
            query += " OFFSET ?";
        }
        
        List<RiwayatPeminjaman> ret1 = new LinkedList<RiwayatPeminjaman>();
        try(PreparedStatement stmt = Database.prepareStatement(
                query
        )){
            
            if(filter != null){
                int i = 0;

                if(filter.status != null) stmt.setInt(++i, filter.status);
                if(filter.lcd != null) stmt.setInt(++i, filter.lcd);
                if(filter.kabel != null) stmt.setInt(++i, filter.kabel);
                if(!Util.isNullOrEmpty(filter.namaPeminjam)) stmt.setString(++i, String.format("'%%%s%%'", filter.namaPeminjam));
                if(!Util.isNullOrEmpty(filter.nimPeminjam)) stmt.setString(++i, String.format("'%%%s%%'", filter.nimPeminjam));
                if(!Util.isNullOrEmpty(filter.keterangan)) stmt.setString(++i, String.format("'%%%s%%'", filter.keterangan));
                if(
                        filter.rangeWaktu != null
                        && filter.rangeWaktu.length == 2
                        && filter.rangeWaktu[0] != null
                        && filter.rangeWaktu[1] != null
                    ){
                    for(int j = 0; i < 3; ++i){
                        stmt.setDateTime(++i, filter.rangeWaktu[0]);
                        stmt.setDateTime(++i, filter.rangeWaktu[1]);
                    }
                }
                
                if(!Util.isNullOrEmpty(filter.namaMatkul)) stmt.setString(++i, String.format("'%%%s%%'", filter.namaMatkul));
                if(!Util.isNullOrEmpty(filter.namaDosen)) stmt.setString(++i, String.format("'%%%s%%'", filter.namaDosen));
                if(!Util.isNullOrEmpty(filter.ruangKelas)) stmt.setString(++i, String.format("'%%%s%%'", filter.ruangKelas));
                
                if(filter.noHPTerblacklist != null) stmt.setBoolean(++i, filter.noHPTerblacklist);
                
                if(filter.limit != null) stmt.setInt(++i, filter.limit);
                stmt.setInt(++i, filter.offset);
            }
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    RiwayatPeminjaman rp = new RiwayatPeminjaman();
                    rp.nomor = rs.getInt("Nomor_Peminjaman");
                    rp.status = rs.getInt("Status_Peminjaman");
                    rp.lcd = rs.getInt("Nomor_LCD");
                    rp.kabel = rs.getInt("Nomor_Kabel");
                    rp.waktuPinjam = rs.getDateTime("Waktu_Pinjam");
                    rp.namaPeminjam = rs.getString("Nama_Peminjam");
                    rp.waktuHarusKembali = rs.getDateTime("Waktu_Harus_Kembali");
                    rp.waktuKembali = rs.getDateTime("Waktu_Kembali");
                    rp.nomorHpTerblacklist = rs.getBoolean("No_HP_Terblacklist");
                    ret1.add(rp);
                }
            }
        }
        ret = ret1.toArray(ret);
        return ret;
    }
    public static Peminjaman getDetailPeminjaman(int nomorPeminjaman){
        try(PreparedStatement stmt = Database.prepareStatement(
                "SELECT "
                + String.join(", ",
                        new String[]{
                            "P.Nomor_Peminjaman",
                            "P.Status_Peminjaman",
                            "P.Nomor_LCD",
                            "P.Nomor_Kabel",
                            "P.Waktu_Pinjam",
                            "P.Nama_Peminjam",
                            "P.NIM_Peminjam",
                            "P.Nomor_HP_Peminjam",
                            "(SELECT B.Keterangan_Blacklist FROM Blacklist B WHERE B.Nomor_HP_Blacklist=P.Nomor_HP_Peminjam) AS Keterangan_Blacklist",
                            "P.Keterangan_Peminjaman",
                            "P.Waktu_Harus_Kembali",
                            "P.Waktu_Kembali",
                        }
                    )
                + " FROM Peminjaman P "
                + " WHERE P.Sedang_Diproses IS NULL AND P.Nomor_Peminjaman=?"
        )){
            stmt.setInt(1, nomorPeminjaman);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    Peminjaman p = new Peminjaman();
                    p.nomor = rs.getInt("Nomor_Peminjaman");
                    p.status = rs.getInt("Status_Peminjaman");
                    p.lcd = rs.getInt("Nomor_LCD");
                    p.kabel = rs.getInt("Nomor_Kabel");
                    p.waktuPinjam = rs.getDateTime("Waktu_Pinjam");
                    p.namaPeminjam = rs.getString("Nama_Peminjam");
                    p.nimPeminjam = rs.getString("NIM_Peminjam");
                    p.nomorHpPeminjam = rs.getString("Nomor_HP_Peminjam");
                    p.keteranganBlacklist = rs.getString("Keterangan_Blacklist");
                    p.keteranganPeminjaman = rs.getString("Keterangan_Peminjaman");
                    p.waktuHarusKembali = rs.getDateTime("Waktu_Harus_Kembali");
                    p.waktuKembali = rs.getDateTime("Waktu_Kembali");
                    
                    //kelas pinjam
                    List<KelasPinjam> kps = new LinkedList<KelasPinjam>();
                    try(PreparedStatement stmt2 = Database.prepareStatement(
                            "SELECT * FROM Kelas_Pinjam KP WHERE KP.Nomor_Peminjaman=? ORDER BY KP.Waktu_Mulai"
                        )){
                        stmt2.setInt(1, nomorPeminjaman);
                        try(ResultSet rs2 = stmt2.executeQuery()){
                            while(rs2.next()){
                                KelasPinjam kp = new KelasPinjam();
                                kp.id = rs2.getInt("Id_Kelas_Pinjam");
                                kp.namaDosen = rs2.getString("Nama_Dosen");
                                kp.namaMataKuliah = rs2.getString("Nama_Mata_Kuliah");
                                kp.ruangKelas = rs2.getString("Ruang_Kelas");
                                kp.waktuMulai = rs2.getDateTime("Waktu_Mulai");
                                kp.waktuSelesai = rs2.getDateTime("Waktu_Selesai");
                                kps.add(kp);
                            }
                        }
                    }
                    p.kelasPinjam = kps.toArray(p.kelasPinjam);
                    return p;
                }
            }
        }
        throw new RuntimeException("Peminjaman tidak ditemukan atau sedang diproses");
    }
    public static int tambahPeminjaman(Peminjaman peminjaman){
        
        try(CallableStatement stmt = Database.prepareCall(
           "CALL INSERT_PEMINJAMAN(?, ?, ?, ?, ?, ?, ?, ?, ?)"
        )){
            //stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setInt(1, peminjaman.lcd);
            stmt.setInt(2, peminjaman.kabel);
            stmt.setString(3, peminjaman.namaPeminjam);
            stmt.setString(4, peminjaman.nimPeminjam);
            stmt.setString(5, peminjaman.nomorHpPeminjam);
            stmt.setString(6, peminjaman.keteranganPeminjaman);
            stmt.setInt(7, peminjaman.kelasPinjam.length);
            
            stmt.registerOutParameter(8, java.sql.Types.INTEGER);
            stmt.registerOutParameter(9, java.sql.Types.CHAR);
            
            stmt.executeUpdate();
            
            peminjaman.nomor = stmt.getInt(8);
            String insertKey = stmt.getString(9);
            
            try(CallableStatement stmt2 = Database.prepareCall(
               "CALL INSERT_PEMINJAMAN(?, ?, ?, ?, ?, ?)"
            )){
                for(int i = 0; i < peminjaman.kelasPinjam.length; ++i){
                    KelasPinjam kp = peminjaman.kelasPinjam[i];
                    stmt2.setString(1, insertKey);
                    stmt2.setString(2, kp.namaMataKuliah);
                    stmt2.setString(3, kp.namaDosen);
                    stmt2.setString(4, kp.ruangKelas);
                    stmt2.setDateTime(5, kp.waktuMulai);
                    stmt2.setDateTime(6, kp.waktuSelesai);
                    
                    stmt2.executeUpdate();
                }
            }
        }
        
        return peminjaman.nomor;
    }
    public static boolean cekPerubahanKelasPinjam(KelasPinjam lama, KelasPinjam baru){
        if(baru.id == null) return true;
        if(!lama.namaDosen.equals(baru.namaDosen)) return true;
        if(!lama.namaMataKuliah.equals(baru.namaMataKuliah)) return true;
        if(!lama.ruangKelas.equals(baru.ruangKelas)) return true;
        if(!lama.waktuMulai.equals(baru.waktuMulai)) return true;
        if(!lama.waktuSelesai.equals(baru.waktuSelesai)) return true;
        return false;
    }
    public static boolean cekPerubahanKelasPinjam(Peminjaman lama, Peminjaman baru){
        if(lama.kelasPinjam.length != baru.kelasPinjam.length) return true;
        for(int i = 0; i < lama.kelasPinjam.length; ++i){
            if(!cekPerubahanKelasPinjam(lama, baru))return true;
        }
        return false;
    }
    public static void ubahDetailPeminjaman(Peminjaman peminjaman){
        Peminjaman lama = getDetailPeminjaman(peminjaman.nomor);
        try(CallableStatement stmt = Database.prepareCall(
           "CALL INSERT_PEMINJAMAN(?, ?, ?, ?, ?, ?, ?, ?, ?)"
        )){
            //stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setInt(1, peminjaman.lcd);
            stmt.setInt(2, peminjaman.kabel);
            stmt.setString(3, peminjaman.namaPeminjam);
            stmt.setString(4, peminjaman.nimPeminjam);
            stmt.setString(5, peminjaman.nomorHpPeminjam);
            stmt.setString(6, peminjaman.keteranganPeminjaman);
            stmt.setInt(7, peminjaman.kelasPinjam.length);
            
            stmt.registerOutParameter(8, java.sql.Types.INTEGER);
            stmt.registerOutParameter(9, java.sql.Types.CHAR);
            
            stmt.executeUpdate();
            
            peminjaman.nomor = stmt.getInt(8);
            String insertKey = stmt.getString(9);
            
            try(CallableStatement stmt2 = Database.prepareCall(
               "CALL INSERT_PEMINJAMAN(?, ?, ?, ?, ?, ?)"
            )){
                for(int i = 0; i < peminjaman.kelasPinjam.length; ++i){
                    KelasPinjam kp = peminjaman.kelasPinjam[i];
                    stmt2.setString(1, insertKey);
                    stmt2.setString(2, kp.namaMataKuliah);
                    stmt2.setString(3, kp.namaDosen);
                    stmt2.setString(4, kp.ruangKelas);
                    stmt2.setDateTime(5, kp.waktuMulai);
                    stmt2.setDateTime(6, kp.waktuSelesai);
                    
                    stmt2.executeUpdate();
                }
            }
        }
        
    }
    public static void batalkanPeminjaman(int nomorPeminjaman){
        
    }
    public static void selesaikanPeminjaman(int nomorPeminjaman){
        
    }
}
