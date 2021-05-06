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
import static uk.ac.tees.mad.w9501736.utils.AppConstants.API.PROFILE_EDIT_API;

public interface WiseLiApiService {

    @Multipart
    @POST(API_REGISTER)
    Observable<Resource<WiseLiUser>> registerUser(@PartMap() Map<String, RequestBody> partMap,
                                                  @Part MultipartBody.Part file);

    @Multipart
    @POST(PROFILE_EDIT_API)
    Observable<Resource<WiseLiUser>> editUser(@PartMap() Map<String, RequestBody> partMap,
                                                  @Part MultipartBody.Part file);
}
