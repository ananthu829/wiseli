package uk.ac.tees.mad.w9501736.utils;

import uk.ac.tees.mad.w9501736.BuildConfig;

public class AppConstants {

    public static final String API_BASE_URL = BuildConfig.BASE_URL;
    public static final Long SPLASH_SCREEN_TIME_OUT = 1000L;
    public static final int CONNECTION_TIMEOUT = 10; // 10 seconds
    public static final int READ_TIMEOUT = 2; // 2 seconds
    public static final int WRITE_TIMEOUT = 2; // 2 seconds


    //WARNING :  DO NOT EDIT THIS
    public static class API {

        //store API's
        public static final String API_REGISTER = "user/signup";
        private static String API_VERSION = "1.0";
        public static final String PROFILE_EDIT_API ="user/profile/edit";

    }


}
