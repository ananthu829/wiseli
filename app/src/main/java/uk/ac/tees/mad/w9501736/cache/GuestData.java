package uk.ac.tees.mad.w9501736.cache;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

import com.google.gson.Gson;

import javax.inject.Inject;

import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;
import uk.ac.tees.mad.w9501736.utils.SingleLiveEvent;

public class GuestData {

    public static final String PREFS_NAME = "authenticatedGuest";
    private static final String PREFS_KEY_IS_AUTHENTICATED = "isAuthenticated";
    private static final String PREFS_KEY_IS_DEACTIVATED = "isDeactivated";
    private static GuestData instance;
    private SingleLiveEvent<Boolean> guestDeactivateLiveData;
    private SharedPreferences prefs;

    private GuestData(Application application) {
        this(application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE));
    }

    @Inject
    public GuestData(SharedPreferences prefs) {
        this.prefs = prefs;
        guestDeactivateLiveData = new SingleLiveEvent<>();
    }

    public static void init(Application application) {
        instance = new GuestData(application);
    }

    public static GuestData newInstance(Application application) {
        if (instance == null) {
            instance = new GuestData(application);
        }
        return instance;
    }

    public void clear() {
        prefs.edit().clear().commit();
    }

    public boolean isLoggedIn() {
        return isAuthenticated() && !isDeactivated();
    }

    public boolean isAuthenticated() {
        return prefs.getBoolean(PREFS_KEY_IS_AUTHENTICATED, false);
    }

    public boolean isDeactivated() {
        return prefs.getBoolean(PREFS_KEY_IS_DEACTIVATED, false);
    }

    public LiveData<Boolean> isDeactivatedLiveData() {
        return guestDeactivateLiveData;
    }

    /**
     * This method is used to activate a minor.
     */
    public void activate() {
        prefs.edit().putBoolean(PREFS_KEY_IS_DEACTIVATED, false).apply();
        guestDeactivateLiveData.postValue(false);
    }

    /**
     * This method is used to deactivate a minor also setting him as
     * not authenticated without clearing all his data.
     */
    public void deactivate() {
        prefs.edit().putBoolean(PREFS_KEY_IS_DEACTIVATED, true).apply();
        prefs.edit().putBoolean(PREFS_KEY_IS_AUTHENTICATED, false).apply();
        guestDeactivateLiveData.postValue(true);
    }

    public WiseLiUser getUserCashedInfo() {
        Gson gson = new Gson();
        String json = prefs.getString("UserDetails", null);
        WiseLiUser wiseLiUsers = new WiseLiUser();
        if (json != null) {
            wiseLiUsers = gson.fromJson(json, WiseLiUser.class);
        }
        return wiseLiUsers;
    }

    public void setUserCashedInfo(WiseLiUser wiseLiUsers) {
        clear();

        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String jsonString = gson.toJson(wiseLiUsers);
        //UserDetails user1 = gson.fromJson(jsonString,UserDetails.class);
        if (jsonString != null) {
            editor.putString("UserDetails", jsonString);
            editor.commit();
        }
        editor.putBoolean(PREFS_KEY_IS_AUTHENTICATED, true);
        editor.apply();
    }

}
