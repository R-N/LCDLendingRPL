/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lcdrpl.database;

import java.sql.SQLException;
import lcdrpl.Util;

/**
 *
 * @author Lenovo2
 */
public class Database implements AutoCloseable{
    static final String username = "client";
    static final String password = "asdfasdf";
    static final String connectionString = "jdbc:mysql://localhost/lcd_rpl";
    
    java.sql.Connection conn = null;
    
    static Database getConnection(){
        Database ret = new Database();
        ret.open();
        return ret;
    }
    
    
    public static PreparedStatement prepareStatement(String sql){
        try{
            Database conn = getConnection();
            return new PreparedStatement(conn, conn.conn.prepareStatement(sql));
        }catch(java.sql.SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public static CallableStatement prepareCall(String sql){
        try{
            Database conn = getConnection();
            return new CallableStatement(conn, conn.conn.prepareCall(sql));
        }catch(java.sql.SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public void open() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = java.sql.DriverManager.getConnection(connectionString, username, password);
        }catch(ClassNotFoundException | SQLException ex){
            conn = null;
            Util.showError("Fatal Error", "Tidak dapat menyambung ke database.\nDetail:\n" + ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    public void close(){
        try{
            if(conn != null){
                conn.close();
            }
        }catch(java.sql.SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }finally{
            conn = null;
        }
    }
    
    
}
