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
public class FilterPeminjaman {
    
    public Integer status;
    public Integer lcd;
    public Integer kabel;
    public String namaPeminjam;
    public String nimPeminjam;
    public String keterangan;
    public Timestamp[] rangeWaktu;
    public String namaMatkul;
    public String namaDosen;
    public String ruangKelas;
    public Boolean noHPTerblacklist;
    public Integer limit;
    public int offset = 0;
}
