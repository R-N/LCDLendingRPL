/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lcdrpl.system.structs;

/**
 *
 * @author Lenovo2
 */
public abstract class Barang {
    public static class Status{
        public static final int TERSEDIA = 0;
        public static final int DIPINJAM = 1;
        public static final int RUSAK = 2;
        
        public static String[] TO_STRING = new String[]{
            "Tersedia",
            "Dipinjam",
            "Rusak"
        };
    }
    public Integer nomor;
    public int status;
    public String keterangan;
    
}
