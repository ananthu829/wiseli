package uk.ac.tees.mad.w9501736.data.remote;

import androidx.annotation.Nullable;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import uk.ac.tees.mad.w9501736.data.model.Resource;
import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;
import uk.ac.tees.mad.w9501736.models.ActiveInActiveBody;
import uk.ac.tees.mad.w9501736.models.CircleData;
import uk.ac.tees.mad.w9501736.models.UserFriendsList;

import static uk.ac.tees.mad.w9501736.utils.AppConstants.API.API_ACTIVE_LIST;
import static uk.ac.tees.mad.w9501736.utils.AppConstants.API.API_ADD_CIRCLE_USER;
import static uk.ac.tees.mad.w9501736.utils.AppConstants.API.API_CREATE_SHOPPING_LIST;
import static uk.ac.tees.mad.w9501736.utils.AppConstants.API.API_DELETE_SHOPPING_LIST;
import static uk.ac.tees.mad.w9501736.utils.AppConstants.API.API_EDIT_SHOPPING_LIST;
import static uk.ac.tees.mad.w9501736.utils.AppConstants.API.API_GET_CIRCLE_DETAILS;
import static uk.ac.tees.mad.w9501736.utils.AppConstants.API.API_GET_FRIENDS_LISTS_CIRCLE;
import static uk.ac.tees.mad.w9501736.utils.AppConstants.API.API_INACTIVE_LIST;
import static uk.ac.tees.mad.w9501736.utils.AppConstants.API.API_REMOVE_CIRCLE_USER;

public interface GroupApiService {

    @GET(API_GET_FRIENDS_LISTS_CIRCLE)
    Observable<Resource<List<UserFriendsList>>> getFriendsListCircle1(@Nullable @Query("token") String token,
                                                                      @Nullable @Query("circle_id") Integer circleId);

    @FormUrlEncoded
    @POST(API_ADD_CIRCLE_USER)
    Observable<Resource<WiseLiUser>> addCircleUser(@Nullable @Field("token") String token,
                                                   @Nullable @Field("circle_id") Integer circleID,
                                                   @Nullable @Field("user_id") Integer userID);

    @GET(API_GET_CIRCLE_DETAILS)
    Observable<Resource<CircleData>> getCircleDetails2(@Nullable @Query("token") String token,
                                                       @Nullable @Query("circle_id") Integer circleId);

    @FormUrlEncoded
    @POST(API_REMOVE_CIRCLE_USER)
    Observable<Resource<WiseLiUser>> removeCircleUser(@Nullable @Field("token") String token,
                                                      @Nullable @Field("circle_id") Integer circleId,
                                                      @Nullable @Field("user_id") Integer userID);

    @GET(API_ACTIVE_LIST)
    Observable<Resource<List<ActiveInActiveBody>>> getActiveList(@Nullable @Query("token") String token,
                                                                 @Nullable @Query("circle_id") Integer propertyID);

    @GET(API_INACTIVE_LIST)
    Observable<Resource<List<ActiveInActiveBody>>> getInactiveList(@Nullable @Query("token") String token,
                                                                   @Nullable @Query("circle_id") Integer propertyID);

    @FormUrlEncoded
    @POST(API_DELETE_SHOPPING_LIST)
    Observable<Resource<WiseLiUser>> deleteShoppingList(@Nullable @Field("token") String token,
                                                        @Nullable @Field("list_id") Integer shoppingListID);

    @FormUrlEncoded
    @POST(API_EDIT_SHOPPING_LIST)
    Observable<Resource<WiseLiUser>> editShoppingList(@Nullable @Field("token") String token,
                                                      @Nullable @Field("list_id") Integer listId,
                                                      @Nullable @Field("list_name") String listName);

    @FormUrlEncoded
    @POST(API_CREATE_SHOPPING_LIST)
    Observable<Resource<WiseLiUser>> createShoppingList(@Nullable @Field("token") String token,
                                                        @Nullable @Field("circle_id") Integer circleID,
                                                        @Nullable @Field("shop_name") String shopName,
                                                        @Nullable @Field("list_name") String listName);


}
