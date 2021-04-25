/* (c) Disney. All rights reserved. */
package uk.ac.tees.mad.w9501736.data.config;

import android.content.SharedPreferences;

import java.util.Map;

import javax.inject.Inject;

public class WiseLiSettings {

    public static final String PREFS_FILE_NAME = "wiseLiSettings";
    private static final String PREFS_ALREADY_WRITTEN_BY_CONFIG = "prefs_already_written_by_Config";

    private final SharedPreferences prefs;

    @Inject
    public WiseLiSettings(SharedPreferences prefs) {
        this.prefs = prefs;
    }


    public String getSetting(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    public void putSetting(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putSettings(Map<String, String> data) {
        SharedPreferences.Editor editor = prefs.edit();
        for (Map.Entry<String, String> dataEntry : data.entrySet()) {
            editor.putString(dataEntry.getKey(), dataEntry.getValue());
        }
        editor.apply();
        setValuesAlreadyWrittenByConfig();
    }

    private boolean getValuesAlreadyWrittenByConfig() {
        return prefs.getBoolean(PREFS_ALREADY_WRITTEN_BY_CONFIG, false);
    }

    private void setValuesAlreadyWrittenByConfig() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PREFS_ALREADY_WRITTEN_BY_CONFIG, true);
        editor.apply();
    }
}
