package id.net.gmedia.cyop;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSharedPreferences {
    private static final String LOGIN_PREF = "login_status";
    private static final String USER_PREF = "user";
    private static final String TOKEN_PREF = "token";

    private static SharedPreferences getPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getUser(Context context){
        return getPreferences(context).getString(USER_PREF, "");
    }

    public static String getToken(Context context){
        return getPreferences(context).getString(TOKEN_PREF, "");
    }

    public static boolean isLoggedIn(Context context){
        return getPreferences(context).getBoolean(LOGIN_PREF, false);
    }

    public static void Login(Context context, String user, String token){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGIN_PREF, true);
        editor.putString(USER_PREF, user);
        editor.putString(TOKEN_PREF, token);
        editor.apply();
    }

    public static void Logout(Context context){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGIN_PREF, false);
        editor.putString(USER_PREF, "");
        editor.putString(TOKEN_PREF, "");
        editor.apply();
    }
}
