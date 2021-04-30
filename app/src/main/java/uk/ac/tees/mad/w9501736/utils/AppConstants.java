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
        public static final String API_GET_FRIENDS_LISTS_CIRCLE = "circle/friends/get";
        public static final String API_ADD_CIRCLE_USER = "circle/friend/create";
        public static final String API_GET_CIRCLE_DETAILS = "circle/details/get";
        public static final String API_REMOVE_CIRCLE_USER = "circle/remove/user";
        public static final String API_ACTIVE_LIST = "active/list/get";
        public static final String API_INACTIVE_LIST = "inactive/list/get";
        public static final String API_DELETE_SHOPPING_LIST = "shoppinglist/delete";
        private static String API_VERSION = "1.0";
        public static final String PROFILE_EDIT_API ="user/profile/edit";

    }


}
