package uk.ac.tees.mad.w9501736.utils;

import android.content.Context;
import android.content.SharedPreferences;



public class AppPreferences {

    private static AppPreferences sInstance;
    private final SharedPreferences mPref;

    public AppPreferences(Context context) {
        String preferenceName = context.getPackageName();
        mPref = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }

    private static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AppPreferences(context);
        }
    }

    public static synchronized AppPreferences getInstance(Context mContext) {
        if (sInstance == null) {
            initializeInstance(mContext);
        }
        return sInstance;
    }

    public void setToken(String token) {
        mPref.edit().putString("token", token).apply();
    }

    public String getToken() {
        return mPref.getString("token", "");
    }



}

