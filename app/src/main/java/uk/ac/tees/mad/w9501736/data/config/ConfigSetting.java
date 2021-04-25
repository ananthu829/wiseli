package uk.ac.tees.mad.w9501736.data.config;

public enum ConfigSetting {

    REGISTER_USER("user/signup", null);


    public final String code;
    public final String defaultValue;

    ConfigSetting(String code, String defaultValue) {
        this.code = code;
        this.defaultValue = defaultValue;
    }
}
