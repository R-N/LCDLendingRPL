/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lcdrpl.system.structs;

import java.sql.Timestamp;

/**
 *
 * @author Lenovo2
 */
public class Peminjaman {
    public static class Status{
        public static final int AKTIF = 1;
        public static final int SELESAI = 2;
        public static final int DIBATALKAN = 3;
        
        public static String[] TO_STRING = new String[]{
            "Aktif",
            "Selesai",
            "Dibatalkan"
        };
    }
    
    public Integer nomor;
    public int status;
    public int lcd;
    public Integer kabel;
    public Timestamp waktuPinjam;
    public String namaPeminjam;
    public String nimPeminjam;
    public String nomorHpPeminjam;
    public String keteranganBlacklist;
    public String keteranganPeminjaman;
    public Timestamp waktuHarusKembali;
    public Timestamp waktuKembali;
    public KelasPinjam[] kelasPinjam;
}
