package uk.ac.tees.mad.w9501736.data.config;

import android.content.Context;

import javax.inject.Inject;

public class RemoteConfig {

    private final WiseLiSettings wiseLiSettings;
    private Context context;

    @Inject
    public RemoteConfig(WiseLiSettings wiseLiSettings,
                        Context context) {
        this.wiseLiSettings = wiseLiSettings;
        this.context = context;
    }


    private String getSetting(ConfigSetting key) {
        return wiseLiSettings.getSetting(key.code, key.defaultValue);
    }

    private void putSetting(ConfigSetting key, String value) {
        wiseLiSettings.putSetting(key.code, value);
    }

    public String getRegisterEndPointPath() {
        return getSetting(ConfigSetting.REGISTER_USER);
    }


}
