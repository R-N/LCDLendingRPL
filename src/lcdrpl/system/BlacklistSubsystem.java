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
import lcdrpl.system.structs.Blacklist;
import lcdrpl.system.structs.FilterBlacklist;
import lcdrpl.system.structs.LCD;

/**
 *
 * @author Lenovo2
 */
public class BlacklistSubsystem {
    public static void hapusBlacklist(String nomorHP){
        nomorHP = Util.preparePhoneNumber(nomorHP);
        try(CallableStatement stmt = Database.prepareCall(
           "CALL DELETE_BLACKLIST(?, ?, ?)"
        )){
            //stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setString(1, nomorHP);
            stmt.setString(2, LoginSubsystem.getSesi());
            
            stmt.registerOutParameter(3, java.sql.Types.TIMESTAMP);
            
            stmt.executeUpdate();
            
            Timestamp timeout = stmt.getDateTime(2);
            LoginSubsystem.updateTimeout(timeout);
        }
    }
    
    public static void tambahBlacklist(int nomorPeminjaman, String keterangan){
        try(CallableStatement stmt = Database.prepareCall(
           "CALL UPDATE_LCD(?, ?, ?, ?)"
        )){
            //stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setInt(1, nomorPeminjaman);
            stmt.setString(2, keterangan);
            stmt.setString(3, LoginSubsystem.getSesi());
            
            stmt.registerOutParameter(4, java.sql.Types.TIMESTAMP);
            
            stmt.executeUpdate();
            
            Timestamp timeout = stmt.getDateTime(2);
            LoginSubsystem.updateTimeout(timeout);
        }
    }
    
    public static void updateBlacklist(Blacklist blacklist){
        blacklist.nomorHP = Util.preparePhoneNumber(blacklist.nomorHP);
        try(CallableStatement stmt = Database.prepareCall(
           "CALL UPDATE_LCD(?, ?, ?, ?)"
        )){
            //stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setString(1, blacklist.nomorHP);
            stmt.setString(2, blacklist.keterangan);
            stmt.setString(3, LoginSubsystem.getSesi());
            
            stmt.registerOutParameter(4, java.sql.Types.TIMESTAMP);
            
            stmt.executeUpdate();
            
            Timestamp timeout = stmt.getDateTime(2);
            LoginSubsystem.updateTimeout(timeout);
        }
    }
    
    public static Blacklist[] getBlacklist(FilterBlacklist filter){
        Blacklist[] ret = null;
        String query = "SELECT * FROM Blacklist";
        
        if(filter != null){
            List<String> where = new LinkedList<String>();
            if(!Util.isNullOrEmpty(filter.nomor)) where.add("Nomor_HP_Blacklist=?");
            if(!Util.isNullOrEmpty(filter.keterangan)) where.add("Keterangan_Kabel=?");
            if(where.size() > 0){
                query += " WHERE " + String.join(" AND ", where);
            }
        }
        
        query += " ORDER BY Nomor_Kabel";
        
        if(filter != null){
            if(filter.limit != null) query += " LIMIT ?";
            query += " OFFSET ?";
        }
        
        
        List<Blacklist> ret1 = new LinkedList<Blacklist>();
        try(PreparedStatement stmt = Database.prepareStatement(
                query
            )){
            
            int i = 0;if(filter != null){
                if(!Util.isNullOrEmpty(filter.nomor)) stmt.setString(++i, String.format("'%%%s%%'", filter.nomor));
                if(!Util.isNullOrEmpty(filter.keterangan)) stmt.setString(++i,  String.format("'%%%s%%'", filter.keterangan));
                if(filter.limit != null) stmt.setInt(++i, filter.limit);
                stmt.setInt(++i, filter.offset);
            }
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    Blacklist b = new Blacklist();
                    b.nomorHP = rs.getString(1);
                    b.keterangan = rs.getString(2);
                    ret1.add(b);
                }
            }
        }
        ret = ret1.toArray(ret);
        return ret;
    }
    
    public static String validasiNoHP(String nomorHP){
        nomorHP = Util.preparePhoneNumber(nomorHP);
        try(PreparedStatement stmt = Database.prepareStatement(
           "SELECT Keterangan_Blacklist FROM Blacklist WHERE Nomor_HP_Blacklist=?"
        )){
            //stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setString(1, nomorHP);
            
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getString(1);
                }
            }
        }
        return null;
    }
}
