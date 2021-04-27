package uk.ac.tees.mad.w9501736.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.tees.mad.w9501736.data.config.RemoteConfig;
import uk.ac.tees.mad.w9501736.network.RetrofitHelper;
import uk.ac.tees.mad.w9501736.utils.AppConstants;

public class WiseLiApiClient {
    public static String BASE_URL = AppConstants.API_BASE_URL;
    private static Retrofit retrofit = null;
    private static Retrofit pathRetrofit = null;
    private static OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(300, TimeUnit.SECONDS).
            readTimeout(300, TimeUnit.SECONDS).
            build();

    private RemoteConfig remoteConfig;
    private RetrofitHelper retrofitHelper;


    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                logging.getLevel().toString();

            OkHttpClient client = new OkHttpClient.Builder().
                    addInterceptor(logging).
                    connectTimeout(300, TimeUnit.SECONDS).
                    readTimeout(300, TimeUnit.SECONDS).
                    build();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit;
    }



}
