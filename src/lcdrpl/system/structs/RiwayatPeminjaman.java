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
public class RiwayatPeminjaman {
    
    public int nomor;
    public int status;
    public int lcd;
    public int kabel;
    public Timestamp waktuPinjam;
    public String namaPeminjam;
    public boolean nomorHpTerblacklist;
    public Timestamp waktuHarusKembali;
    public Timestamp waktuKembali;
}
