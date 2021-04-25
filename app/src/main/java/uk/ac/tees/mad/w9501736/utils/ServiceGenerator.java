package uk.ac.tees.mad.w9501736.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.tees.mad.w9501736.data.remote.WiseLiApiService;

import static uk.ac.tees.mad.w9501736.utils.AppConstants.API_BASE_URL;
import static uk.ac.tees.mad.w9501736.utils.AppConstants.CONNECTION_TIMEOUT;
import static uk.ac.tees.mad.w9501736.utils.AppConstants.READ_TIMEOUT;
import static uk.ac.tees.mad.w9501736.utils.AppConstants.WRITE_TIMEOUT;

public class ServiceGenerator {

    private static OkHttpClient client = new OkHttpClient.Builder()

            // establish connection to server
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)

            // time between each byte read from the server
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)

            // time between each byte sent to server
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)

            .retryOnConnectionFailure(false)

            .build();


    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static WiseLiApiService wiseLiApi = retrofit.create(WiseLiApiService.class);

    public static WiseLiApiService getWiseLiApi() {
        return wiseLiApi;
    }
}
