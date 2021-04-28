package com.project.certificate.RestService;


import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by akhil on 19/6/19
 **/
public class RestClient extends AppConstants {

    private static RestService REST_SERVICE;
    private static RestService REST_SERVICE_AUTH;
    private static X509TrustManager x509TrustManager;

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

        /*x509TrustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };*/

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.sslSocketFactory(getFactory(), x509TrustManager)
        httpClient.connectTimeout(60000, TimeUnit.SECONDS);
        httpClient.readTimeout(120000, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(chain -> {
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Content-Type", "application/x-www-form-urlencoded");

            Request request = builder.build();

            return chain.proceed(request);
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        REST_SERVICE_AUTH = retrofit.create(RestService.class);
    }


    private static void setupRestClient() {

        /*x509TrustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };*/

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.sslSocketFactory(getFactory(), x509TrustManager)
        httpClient.connectTimeout(60000, TimeUnit.SECONDS);
        httpClient.readTimeout(120000, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(chain -> {
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Content-Type", "application/x-www-form-urlencoded");

            Request request = builder.build();

            return chain.proceed(request);
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        REST_SERVICE = retrofit.create(RestService.class);
    }

    private static SSLSocketFactory getFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new X509TrustManager[]{x509TrustManager}, new SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
