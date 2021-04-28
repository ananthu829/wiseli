package uk.ac.tees.mad.w9501736.network;




import androidx.annotation.Nullable;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import uk.ac.tees.mad.w9501736.models.LoginModel;


/**
 * Created by nithin on 20/9/20
 **/
public interface RestService {



//    @POST("/api/user/login")
//    Call<LoginModel> getOTP(@Body LoginModel requestOTP);

    @FormUrlEncoded
    @POST("/api/user/login")
    Call<LoginModel> login(@Nullable @Field("username") String name,
                                   @Nullable @Field("password") String id,
                                   @Nullable @Field("device_id") String password,
                                   @Nullable @Field("device_type") String phone,
                                   @Nullable @Field("latitude") String country,
                                   @Nullable @Field("longitude") String city);

}
