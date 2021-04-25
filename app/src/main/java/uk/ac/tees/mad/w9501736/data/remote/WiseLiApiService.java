package uk.ac.tees.mad.w9501736.data.remote;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import retrofit2.http.Field;
import retrofit2.http.GET;
import uk.ac.tees.mad.w9501736.data.model.Resource;
import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;
import uk.ac.tees.mad.w9501736.utils.ApiResponse;

public interface WiseLiApiService {

    @GET("user/signup")
    LiveData<ApiResponse<Resource<WiseLiUser>>> registerUser(
            @Nullable @Field("first_name") String firstName,
            @Nullable @Field("last_name") String lastName,
            @Nullable @Field("email") String email,
            @Nullable @Field("gender") String gender,
            @Nullable @Field("profile_image") String imageP,
            @Nullable @Field("phone_number") String mobNo,
            @Nullable @Field("username") String userName,
            @Nullable @Field("password") String password,
            @Nullable @Field("device_id") String deviceId,
            @Nullable @Field("device_type") String deviceType,
            @Nullable @Field("latitude") String latitude,
            @Nullable @Field("longitude") String longitude);
}
