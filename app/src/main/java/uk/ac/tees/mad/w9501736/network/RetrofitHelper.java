/* (c) Disney. All rights reserved. */
package uk.ac.tees.mad.w9501736.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.tees.mad.w9501736.BuildConfig;
import uk.ac.tees.mad.w9501736.utils.AppConstants;

/**
 * @author pedro.vicente
 */
public class RetrofitHelper {

    private static final String CACHE_DIR = "http-cache";
    private static final int CACHE_SIZE = 10 * 1024 * 1024; //10MB

    private final Cache okHttpCache;

    @Inject
    public RetrofitHelper(Cache okHttpCache) {
        this.okHttpCache = okHttpCache;
    }

    /**
     * Create a custom client for Retrofit.
     */
    private static OkHttpClient buildOkHttpClient(Cache cache) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            logging.getLevel().toString();
        }
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.MILLISECONDS)
                .readTimeout(300, TimeUnit.MILLISECONDS)
                .writeTimeout(300, TimeUnit.MILLISECONDS)
                .cache(cache)
                .addInterceptor(logging);


        return clientBuilder.build();
    }

    public static <T> T createBasicService(String baseUrl, final Class<T> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();

        return retrofit.create(service);
    }

    public static Cache getCache(Context context) {
        final File file = new File(context.getCacheDir(), RetrofitHelper.CACHE_DIR);
        return new Cache(file, RetrofitHelper.CACHE_SIZE);
    }

    private static Retrofit buildRetrofit(Cache cache) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(AppConstants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(buildOkHttpClient(cache))
                .build();
    }

    //region Static methods

    public <T> T createService(final Class<T> service) {
        return createService(service);
    }

    public <T> T createService(final String baseUrl, Class<T> service) {
        return createService(baseUrl, service);
    }


    //endregion
}
