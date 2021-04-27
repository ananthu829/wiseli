package uk.ac.tees.mad.w9501736.data.remote;

import androidx.annotation.Nullable;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import uk.ac.tees.mad.w9501736.data.model.Resource;
import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;

import static uk.ac.tees.mad.w9501736.utils.AppConstants.API.API_REGISTER;

public interface WiseLiApiService {

/*    @GET("user/signup")
    LiveData<ApiResponse<Resource<WiseLiUser>>> registerUser(
            @Nullable @Field("first_name") String firstName,
            @Nullable @Field("last_name") String lastName,
            @Nullable @Field("email") String email,
            @Nullable @Field("gender") String gender,
            @Part MultipartBody.Part file,
            @Nullable @Field("phone_number") String mobNo,
            @Nullable @Field("username") String userName,
            @Nullable @Field("password") String password,
            @Nullable @Field("device_id") String deviceId,
            @Nullable @Field("device_type") String deviceType,
            @Nullable @Field("latitude") String latitude,
            @Nullable @Field("longitude") String longitude);*/


    @Multipart
    @POST(API_REGISTER)
    Observable<Resource<WiseLiUser>> registerUser(
            @Nullable @Query("first_name") String firstName,
            @Nullable @Query("last_name") String lastName,
            @Nullable @Query("email") String email,
            @Nullable @Query("gender") String gender,
            @Part MultipartBody.Part file,
            @Nullable @Query("phone_number") String mobNo,
            @Nullable @Query("username") String userName,
            @Nullable @Query("password") String password,
            @Nullable @Query("device_id") String deviceId,
            @Nullable @Query("device_type") String deviceType,
            @Nullable @Query("latitude") String latitude,
            @Nullable @Query("longitude") String longitude);
}
