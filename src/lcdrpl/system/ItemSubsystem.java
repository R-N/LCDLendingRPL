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
import lcdrpl.system.structs.FilterBarang;
import lcdrpl.system.structs.Kabel;
import lcdrpl.system.structs.LCD;
import lcdrpl.system.structs.OverallStatusLCD;

/**
 *
 * @author Lenovo2
 */
public class ItemSubsystem {
    public static OverallStatusLCD getOverallStatusLCD(){
        OverallStatusLCD ret = new OverallStatusLCD();
        try(PreparedStatement stmt = Database.prepareStatement(
                "SELECT COUNT(*) FROM LCD WHERE Status_LCD=0;"
        )){
            try(ResultSet rs = stmt.executeQuery()){
                rs.next();
                ret.availableCount = rs.getInt(1);
            }
        }
        try(PreparedStatement stmt = Database.prepareStatement(
                "SELECT COUNT(*) FROM Peminjaman P WHERE P.Status_Peminjaman=1 AND P.Waktu_Harus_Kembali<NOW();"
        )){
            try(ResultSet rs = stmt.executeQuery()){
                rs.next();
                ret.lateCount = rs.getInt(1);
            }
        }
        try(PreparedStatement stmt = Database.prepareStatement(
                "SELECT COUNT(*) FROM LCD WHERE Status_LCD>1;"
        )){
            try(ResultSet rs = stmt.executeQuery()){
                rs.next();
                ret.unavailableCount = rs.getInt(1);
            }
        }
        return ret;
    }

    
    public static LCD[] getLCD(FilterBarang filter){
        LCD[] ret = null;
        String query = "SELECT * FROM LCD";
        
        if(filter != null){
            List<String> where = new LinkedList<String>();
            if(filter.status != null) where.add("Status_LCD=?");
            if(!Util.isNullOrEmpty(filter.keterangan)) where.add("Keterangan_LCD=?");
            if(where.size() > 0){
                query += " WHERE " + String.join(" AND ", where);
            }
        }
        
        query += " ORDER BY Nomor_LCD";
        
        if(filter != null){
            if(filter.limit != null) query += " LIMIT ?";
            query += " OFFSET ?";
        }
        
        List<LCD> ret1 = new LinkedList<LCD>();
        try(PreparedStatement stmt = Database.prepareStatement(
                query
        )){
            if(filter != null){
                int i = 0;
                if(filter.status != null) stmt.setInt(++i, filter.status);
                if(!Util.isNullOrEmpty(filter.keterangan)) stmt.setString(++i, String.format("'%%%s%%'", filter.keterangan));
                if(filter.limit != null) stmt.setInt(++i, filter.limit);
                stmt.setInt(++i, filter.offset);
            }
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    LCD l = new LCD();
                    l.nomor = rs.getInt(1);
                    l.status = rs.getInt(2);
                    l.keterangan = rs.getString(3);
                    ret1.add(l);
                }
            }
        }
        ret = ret1.toArray(ret);
        return ret;
    }
    public static Kabel[] getKabel(FilterBarang filter){
        Kabel[] ret = null;
        String query = "SELECT * FROM Kabel";
        
        if(filter != null){
            List<String> where = new LinkedList<String>();
            if(filter.status != null) where.add("Status_Kabel=?");
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
        
        List<Kabel> ret1 = new LinkedList<Kabel>();
        try(PreparedStatement stmt = Database.prepareStatement(
                query
        )){
            if(filter != null){
                int i = 0;
                if(filter.status != null) stmt.setInt(++i, filter.status);
                if(!Util.isNullOrEmpty(filter.keterangan)) stmt.setString(++i, String.format("'%%%s%%'", filter.keterangan));
                if(filter.limit != null) stmt.setInt(++i, filter.limit);
                stmt.setInt(++i, filter.offset);
            }
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    Kabel l = new Kabel();
                    l.nomor = rs.getInt(1);
                    l.status = rs.getInt(2);
                    l.keterangan = rs.getString(3);
                    ret1.add(l);
                }
            }
        }
        ret = ret1.toArray(ret);
        return ret;
    }
    
    public static int InsertLCDKabel(Integer nomor, String keteranganLCD, String keteranganKabel){
        
        try(CallableStatement stmt = Database.prepareCall(
           "CALL INSERT_LCD(?, ?, ?, ?, ?)"
        )){
            //stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setInt(1, nomor);
            stmt.setNull(2, java.sql.Types.INTEGER);
            stmt.setString(3, keteranganLCD);
            stmt.setString(4, LoginSubsystem.getSesi());
            
            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            stmt.registerOutParameter(5, java.sql.Types.TIMESTAMP);
            
            stmt.executeUpdate();
            
            nomor = stmt.getInt(1);
            
            Timestamp timeout = stmt.getDateTime(2);
            LoginSubsystem.updateTimeout(timeout);
        }
        try(CallableStatement stmt = Database.prepareCall(
           "CALL INSERT_KABEL(?, ?, ?, ?, ?)"
        )){
            //stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setInt(1, nomor);
            stmt.setNull(2, java.sql.Types.INTEGER);
            stmt.setString(3, keteranganKabel);
            stmt.setString(4, LoginSubsystem.getSesi());
            
            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            stmt.registerOutParameter(5, java.sql.Types.TIMESTAMP);
            
            stmt.executeUpdate();
            
            nomor = stmt.getInt(1);
            
            Timestamp timeout = stmt.getDateTime(2);
            LoginSubsystem.updateTimeout(timeout);
        }
        
        return nomor;
        
    }
    
    public static void ubahLCD(LCD item){
        try(CallableStatement stmt = Database.prepareCall(
           "CALL UPDATE_LCD(?, ?, ?, ?, ?)"
        )){
            //stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setInt(1, item.nomor);
            stmt.setInt(2, item.status);
            stmt.setString(3, item.keterangan);
            stmt.setString(4, LoginSubsystem.getSesi());
            
            stmt.registerOutParameter(5, java.sql.Types.TIMESTAMP);
            
            stmt.executeUpdate();
            
            Timestamp timeout = stmt.getDateTime(2);
            LoginSubsystem.updateTimeout(timeout);
        }
    }
    
    public static void ubahKabel(Kabel item){
        try(CallableStatement stmt = Database.prepareCall(
           "CALL UPDATE_KABEL(?, ?, ?, ?, ?)"
        )){
            //stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setInt(1, item.nomor);
            stmt.setInt(2, item.status);
            stmt.setString(3, item.keterangan);
            stmt.setString(4, LoginSubsystem.getSesi());
            
            stmt.registerOutParameter(5, java.sql.Types.TIMESTAMP);
            
            stmt.executeUpdate();
            
            Timestamp timeout = stmt.getDateTime(2);
            LoginSubsystem.updateTimeout(timeout);
        }
    }
}
