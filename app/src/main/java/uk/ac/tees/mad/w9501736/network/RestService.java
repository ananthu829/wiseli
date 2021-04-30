package uk.ac.tees.mad.w9501736.network;


import androidx.annotation.Nullable;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import uk.ac.tees.mad.w9501736.data.model.Resource;
import uk.ac.tees.mad.w9501736.models.BasicResponse;
import uk.ac.tees.mad.w9501736.models.CircleData;
import uk.ac.tees.mad.w9501736.models.LoginModel;


/**
 * Created by nithin on 20/9/20
 **/
public interface RestService {


//    @POST("/api/user/login")
//    Call<LoginModel> getOTP(@Body LoginModel requestOTP);

    @FormUrlEncoded
    @POST("/api/user/login")
    Call<LoginModel> login(@Nullable @Field("username") String username,
                           @Nullable @Field("password") String password,
                           @Nullable @Field("device_id") String device_id,
                           @Nullable @Field("device_type") String device_type,
                           @Nullable @Field("latitude") String latitude,
                           @Nullable @Field("longitude") String longitude);


    @FormUrlEncoded
    @POST("/api/circle/create")
    Call<BasicResponse> addCircle(@Nullable @Field("token") String name,
                                  @Nullable @Field("circle_name") String id,
                                  @Nullable @Field("latitude") String latitude,
                                  @Nullable @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("/api/circle/edit")
    Call<BasicResponse> editCircle(@Nullable @Field("token") String token,
                                   @Nullable @Field("circle_id") Integer id,
                                   @Nullable @Field("circle_name") String name
                                );


    @FormUrlEncoded
    @POST("/api/circle/delete")
    Call<BasicResponse> deleteCircle(@Nullable @Field("token") String token,
                                     @Nullable @Field("circle_id") Integer id
    );


    @GET("/api/circle/list/get?")
    Call<Resource<ArrayList<CircleData>>> getCircle(@Nullable @Query("token") String name);
}
