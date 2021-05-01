package uk.ac.tees.mad.w9501736.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;
import uk.ac.tees.mad.w9501736.models.LoginModel;


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

    public String getToken() {
        return mPref.getString("token", "");
    }

    public void setToken(String token) {
        mPref.edit().putString("token", token).apply();
    }



    public Boolean isActive() {
        return mPref.getBoolean("active", false);
    }

    public void setActive(Boolean token) {
        mPref.edit().putBoolean("active", token).apply();
    }


    public void setUserDetails(LoginModel.LoginModelDB loginDetails) {
        try {
            mPref.edit().putString("login_details", new Gson().toJson(loginDetails)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LoginModel.LoginModelDB getLoginDetails() {
        try {
            String json = mPref.getString("login_details", null);
            return new Gson().fromJson(json, LoginModel.LoginModelDB.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public WiseLiUser getUserCashedInfo() {
        Gson gson = new Gson();
        String json = mPref.getString("UserDetails", null);
        WiseLiUser wiseLiUsers = new WiseLiUser();
        if (json != null) {
            wiseLiUsers = gson.fromJson(json, WiseLiUser.class);
        }
        return wiseLiUsers;
    }

    public void setUserCashedInfo(WiseLiUser wiseLiUsers) {
        clear();

        SharedPreferences.Editor editor = mPref.edit();
        Gson gson = new Gson();
        String jsonString = gson.toJson(wiseLiUsers);
        //UserDetails user1 = gson.fromJson(jsonString,UserDetails.class);
        if (jsonString != null) {
            editor.putString("UserDetails", jsonString);
            editor.commit();
        }

        editor.apply();
    }

    public void clear() {
        mPref.edit().clear().commit();
    }


}

