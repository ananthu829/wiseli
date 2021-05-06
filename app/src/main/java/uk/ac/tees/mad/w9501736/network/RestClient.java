package uk.ac.tees.mad.w9501736.network;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.tees.mad.w9501736.utils.AppConstants;


public class RestClient extends AppConstants {

    private static RestService REST_SERVICE;
    private static RestService REST_SERVICE_AUTH;

    public RestClient() {

    }

    public static RestService getInstance() {
            setupRestClient();

        return REST_SERVICE;
    }

    public static RestService getapi() {
        if (REST_SERVICE_AUTH == null) {
            apisetupRestClient();
        }
        return REST_SERVICE_AUTH;
    }

    private static void apisetupRestClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(60000, TimeUnit.SECONDS);
        httpClient.readTimeout(120000, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(chain -> {
            Request.Builder builder = chain.request().newBuilder();

            Request request = builder.build();

            return chain.proceed(request);
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        REST_SERVICE_AUTH = retrofit.create(RestService.class);
    }


    private static void setupRestClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(60000, TimeUnit.SECONDS);
        httpClient.readTimeout(120000, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(chain -> {
            Request.Builder builder = chain.request().newBuilder();

            Request request = builder.build();

            return chain.proceed(request);
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        REST_SERVICE = retrofit.create(RestService.class);
    }
}
