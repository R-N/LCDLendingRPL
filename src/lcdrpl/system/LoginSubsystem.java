/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lcdrpl.system;

import java.sql.Timestamp;
import lcdrpl.Util;
import lcdrpl.database.CallableStatement;
import lcdrpl.database.Database;
import lcdrpl.system.structs.SesiAdmin;

/**
 *
 * @author Lenovo2
 */
public class LoginSubsystem {
    private static SesiAdmin sesiAdmin = null;
    public Timestamp cekSesi(){
        if(sesiAdmin == null) return null;
        if(sesiAdmin.timeout.before(Util.now())) return null;
        updateTimeout(pingSesi(sesiAdmin.sesi));
        return sesiAdmin.timeout;
    }
    private static String login(String username, String password){
        try(CallableStatement stmt = Database.prepareCall(
           "CALL LOGIN_PETUGAS(?, ?, ?, ?, ?)"
        )){
            stmt.setString(1, username);
            stmt.setString(2, Util.md5(password));
            
            stmt.registerOutParameter(3, java.sql.Types.CHAR);
            stmt.registerOutParameter(4, java.sql.Types.VARCHAR);
            stmt.registerOutParameter(5, java.sql.Types.TIMESTAMP);
            
            stmt.executeUpdate();
            
            SesiAdmin sesi = new SesiAdmin();
            
            sesi = new SesiAdmin();
            
            sesi.sesi = stmt.getString(3);
            sesi.nama = stmt.getString(4);
            sesi.timeout = stmt.getDateTime(5);
            
            sesiAdmin = sesi;
            
            return sesi.nama;
        }
        
    }
    private static Timestamp pingSesi(String sesi){
        Timestamp timeout = null;
        try(CallableStatement stmt = Database.prepareCall(
           "CALL PING_SESI(?, ?)"
        )){
            stmt.setString(1, sesi);
            
            stmt.registerOutParameter(2, java.sql.Types.TIMESTAMP);
            
            stmt.executeUpdate();
        }
        return timeout;
    }
    public static Timestamp pingSesi(){
        return pingSesi(sesiAdmin.sesi);
    }
    public static void updateTimeout(Timestamp timeout){
        sesiAdmin.timeout = timeout;
    }
    public static String getSesi(){
        return sesiAdmin.sesi;
    }
    public static Timestamp getTimeout(){
        return sesiAdmin.timeout;
    }
}
