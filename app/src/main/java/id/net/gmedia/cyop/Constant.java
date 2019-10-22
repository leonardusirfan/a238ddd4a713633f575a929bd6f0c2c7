package id.net.gmedia.cyop;

import android.util.Base64;
import android.util.Log;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Constant {
    public final static Map<String, String> HEADER_AUTH = new HashMap<String, String>(){
        {
            put("Auth-Key", "frontend_client");
            put("Client-Service", "Gmedia_OWNPASSWORD");
        }
    };

    public final static String TAG = "cyop_log";

    //EXTRA
    public final static String EXTRA_USERNAME = "username";
    public final static String EXTRA_PASSWORD = "password";
    public final static String EXTRA_PASSCODE = "passcode";
    public final static String EXTRA_HANDPHONE = "handphone";

    //URL
    private final static String BASE_URL = "https://itgsmg.gmedia.id/api_ownpassword/";

    public final static String URL_LOGIN = BASE_URL + "authentication/login";
    public final static String URL_CREATE = BASE_URL + "hotspot/create_account";

    //Token heaader dengan enkripsi
    public static Map<String, String> getTokenHeader(String user, String token){
        Map<String, String> header = new HashMap<>();
        header.put("Auth-Key", "frontend_client");
        header.put("Client-Service", "Gmedia_OWNPASSWORD");
        header.put("User-Id", user);
        header.put("Token", token);

        /*String timestamp =  new SimpleDateFormat("SSSHHyyyyssMMddmm", Locale.getDefault()).format(new Date());
        String signature = sha256(id+"&"+timestamp,id+"die");*/

        /*System.out.println("UUID : " + uuid);
        System.out.println("Timestamp : " + timestamp);
        System.out.println("Signature : " + signature);*/

        /*header.put("Timestamp", timestamp);
        header.put("Signature", signature);*/
        return header;
    }

    private static String sha256(String message, String key) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKey);
            return Base64.encodeToString(sha256_HMAC.doFinal(message.getBytes()), Base64.DEFAULT);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

        Log.w("SHA256", "Return string kosong");
        return "";
    }
}