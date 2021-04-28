package com.booking.hotelTriagler.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.booking.hotelTriagler.model.Bookingh;
import com.booking.hotelTriagler.model.HomeModel;
import com.booking.hotelTriagler.model.RoomList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by akhil on 19/6/19
 **/
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

    public void setRegister(boolean isRegister) {
        mPref.edit().putBoolean("register", isRegister).apply();
    }

    public boolean isRegister() {
        return mPref.getBoolean("register", false);
    }

    public void profileUpdated(boolean isRegister) {
        mPref.edit().putBoolean("profile", isRegister).apply();
    }

    public boolean isProfileUpdated() {
        return mPref.getBoolean("profile", false);
    }

    public void clearData() {
        mPref.edit().clear().apply();
    }

    public void setPhoneNum(String ph) {
        mPref.edit().putString("phone", ph).apply();

    }

    public String getPhoneNum() {
        return mPref.getString("phone", "");
    }


    public void setHotelId(String ph) {
        mPref.edit().putString("hotelid", ph).apply();

    }

    public String getHotelId() {
        return mPref.getString("hotelid", "");
    }

    public void setCaategorylId(String ph) {
        mPref.edit().putString("categoryId", ph).apply();

    }

    public String getCaategorylId() {
        return mPref.getString("categoryId", "");
    }

    public void setRoomId(String ph) {
        mPref.edit().putString("roomid", ph).apply();

    }

    public String getRoomId() {
        return mPref.getString("roomid", "");
    }


    public void setAccessToken(String ph) {
        mPref.edit().putString("accessToken", ph).apply();

    }

    public String getAccessToken() {
        return mPref.getString("accessToken", "");
    }


    public void setCustId(String ph) {
        mPref.edit().putString("custId", ph).apply();

    }

    public String getCustId() {
        return mPref.getString("custId", "");
    }
    public void setFavList(List<HomeModel> tariffs, Context context) {
        try {
            SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("fav", new Gson().toJson(tariffs));

            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<HomeModel> getFavList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);

        try {
            String json = prefs.getString("fav", null);
            ArrayList<HomeModel> tariffs = new Gson().fromJson(json, new TypeToken<ArrayList<HomeModel>>() {
            }.getType());

            return tariffs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setpos(List<Integer> tariffs, Context context) {
        try {
            SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("int", new Gson().toJson(tariffs));

            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Integer> getpos(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);

        try {
            String json = prefs.getString("int", null);
            ArrayList<Integer> tariffs = new Gson().fromJson(json, new TypeToken<ArrayList<Integer>>() {
            }.getType());

            return tariffs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setRoomTariff(RoomList tariff) {
        try {
            mPref.edit().putString("roomandhoteldetails", new Gson().toJson(tariff)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RoomList getRoomTariff() {
        try {
            String json = mPref.getString("roomandhoteldetails", null);
            return new Gson().fromJson(json, RoomList.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setBookedDetails(Bookingh tariff) {
        try {
            mPref.edit().putString("bookedroomandhoteldetails", new Gson().toJson(tariff)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bookingh getBookedDetails() {
        try {
            String json = mPref.getString("bookedroomandhoteldetails", null);
            return new Gson().fromJson(json, Bookingh.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setfeedback(Bookingh tariff) {
        try {
            mPref.edit().putString("feedbacks", new Gson().toJson(tariff)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bookingh getfeedback() {
        try {
            String json = mPref.getString("feedbacks", null);
            return new Gson().fromJson(json, Bookingh.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setImage(String ph) {
        mPref.edit().putString("img", ph).apply();

    }

    public String getImage() {
        return mPref.getString("img", "");
    }


}

