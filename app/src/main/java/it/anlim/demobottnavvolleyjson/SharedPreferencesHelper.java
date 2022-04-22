package it.anlim.demobottnavvolleyjson;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class SharedPreferencesHelper {
    // Set data to SharedPreferences
    public static void setSession(Context context, String key, String value){
        if (context == null)
            return;

        try {
            SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor param = session.edit();
            param.putString(key, value);
            param.apply();
        }
        catch (Exception e){
            Log.e("Error","Can't save data to SharedPreferences");
        }
    }

    // Get data to SharedPreferences
    public static String getSession(Context context, String key){
        String result = "";
        if (context == null)
            return result;

        try {
            SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(context);
            result = session.getString(key, "");
        } catch (Exception e) {
            Log.e("Error","Can't get data from SharedPreferences");
        }

        return result;
    }
}
