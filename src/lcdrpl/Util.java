/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lcdrpl;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import javax.swing.JOptionPane;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Calendar;
/**
 *
 * @author Lenovo2
 */
public class Util {
    public static void showError(String title, String text){
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
    }
    public static String md5(String source){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(StandardCharsets.UTF_8.encode(source));
            md.update(source.getBytes());
            byte[] digest = md.digest();
            String myHash = String.format("%032x", new BigInteger(1, digest));
            return myHash;
        }catch(NoSuchAlgorithmException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    public static Timestamp now(){
        Calendar calendar = Calendar.getInstance();
        Timestamp currentTimestamp = new Timestamp(calendar.getTimeInMillis());
        return currentTimestamp;
    }
    public static Timestamp addMinute(Timestamp time, int minute){
        return new Timestamp(time.getTime() + (1000 * 60 * minute));
    }
    public static boolean isNullOrEmpty(String s){
        return s == null || s.trim().isEmpty();
    }
    public static String preparePhoneNumber(String number){
        return number.trim().replace("\\-", "").replace("\\+62", "").replace("\\)", "").replace("\\(", "").replace(" ", "");
    }
}
