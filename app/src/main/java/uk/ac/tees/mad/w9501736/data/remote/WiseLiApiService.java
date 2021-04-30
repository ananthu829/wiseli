package uk.ac.tees.mad.w9501736.data.remote;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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
    Observable<Resource<WiseLiUser>> registerUser(@PartMap() Map<String, RequestBody> partMap,
                                                  @Part MultipartBody.Part file);

    @Multipart
    @POST(API_REGISTER)
    Observable<Resource<WiseLiUser>> editUser(@PartMap() Map<String, RequestBody> partMap,
                                                  @Part MultipartBody.Part file);
}
