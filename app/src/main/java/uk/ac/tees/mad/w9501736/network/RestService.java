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
import uk.ac.tees.mad.w9501736.models.ItemsList;
import uk.ac.tees.mad.w9501736.models.LoginModel;
import uk.ac.tees.mad.w9501736.models.ShoppingList;
import uk.ac.tees.mad.w9501736.models.UserFriendsList;

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


    @GET("/api/category/items/get?")
    Call<ItemsList> getItems(@Nullable @Query("token") String token);

    @GET("/api/shoppinglist/details/get?")
    Call<ShoppingList> getShoppingList(@Nullable @Query("token") String token, @Nullable @Query("shoppinglist_id") Integer list_id);


    @FormUrlEncoded
    @POST("/api/add/shoppinglist/item")
    Call<BasicResponse> addShoppingList(@Nullable @Field("token") String token,
                                        @Nullable @Field("shoppinglist_id") Integer id,
                                        @Nullable @Field("item_id") String item_id,
                                        @Nullable @Field("quantity") String quantity
    );

    @FormUrlEncoded
    @POST("/api/shoppinglist/item/delete")
    Call<BasicResponse> deleteListShopping(@Nullable @Field("token") String token,
                                           @Nullable @Field("listitem_id") Integer listitem_id
    );


    @FormUrlEncoded
    @POST("/api/shoppinglist/name/edit")
    Call<BasicResponse> editListShopping(@Nullable @Field("token") String token,
                                           @Nullable @Field("list_id") Integer list_id,
                                         @Nullable @Field("list_name") String list_name
    );


    @FormUrlEncoded
    @POST("/api/shoppinglist/edit")
    Call<BasicResponse> saveData(@Nullable @Field("token") String token,
                                 @Nullable @Field("list_name") String listitem_name,
                                 @Nullable @Field("amount") String amount,
                                 @Nullable @Field("latitude") String latitude,
                                 @Nullable @Field("longitude") String longitude,

                                 @Nullable @Field("shoppinglist_id") Integer shoppinglist_id,
                                 @Nullable @Field("is_closed") String is_closed
    );



    @GET("/api/user/search?")
    Call<Resource<ArrayList<UserFriendsList>>> findFriends(@Nullable @Query("token") String name, @Nullable @Query("username") String username);

    @FormUrlEncoded
    @POST("/api/friend/create")
    Call<BasicResponse> addFriend(@Nullable @Field("token") String name, @Nullable @Field("friend_id") Integer friendId);

    @GET("/api/friends/list/get")
    Call<Resource<ArrayList<UserFriendsList>>> getFriendsList(@Nullable @Query("token") String token);


}
