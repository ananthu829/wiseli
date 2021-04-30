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
import uk.ac.tees.mad.w9501736.models.BasicResponse;
import uk.ac.tees.mad.w9501736.models.CircleModel;
import uk.ac.tees.mad.w9501736.models.ItemsList;
import uk.ac.tees.mad.w9501736.models.LoginModel;
import uk.ac.tees.mad.w9501736.models.ShoppingList;


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


    @FormUrlEncoded
    @POST("/api/circle/create")
    Call<BasicResponse> addCircle(@Nullable @Field("token") String name,
                                  @Nullable @Field("circle_name") String id,
                                  @Nullable @Field("latitude") String latitude,
                                  @Nullable @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("/api/circle/edit")
    Call<BasicResponse> editCircle(@Nullable @Field("token") String token,
                                  @Nullable @Field("circle_id") String id,
                                   @Nullable @Field("circle_name") String name
                                );


    @FormUrlEncoded
    @POST("/api/circle/delete")
    Call<BasicResponse> deleteCircle(@Nullable @Field("token") String token,
                                     @Nullable @Field("circle_id") String id
    );


    @GET("/api/circle/list/get?")
    Call<CircleModel> getCircle(@Nullable @Query("token") String name);


    @GET("/category/items/get?")
    Call<ItemsList> getItems(@Nullable @Query("token") String token, @Nullable @Query("category_id") String categoryId);

    @GET("/api/shoppinglist/details/get?")
    Call<ShoppingList> getShoppingList(@Nullable @Query("token") String token, @Nullable @Query("shoppinglist_id") String categoryId);


    @FormUrlEncoded
    @POST("/api/add/shoppinglist/item")
    Call<BasicResponse> addShoppingList(@Nullable @Field("token") String token,
                                     @Nullable @Field("shoppinglist_id") String id,
                                        @Nullable @Field("item_id") String item_id,
                                        @Nullable @Field("quantity") String quantity
    );

    @FormUrlEncoded
    @POST("/api/shoppinglist/item/delete")
    Call<BasicResponse> deleteListShopping(@Nullable @Field("token") String token,
                                     @Nullable @Field("listitem_id") String listitem_id
    );


    @FormUrlEncoded
    @POST("/api/shoppinglist/edit")
    Call<BasicResponse> saveData(@Nullable @Field("token") String token,
                                           @Nullable @Field("list_name") String listitem_id,
                                 @Nullable @Field("shoppinglist_id") String shoppinglist_id,
                                   @Nullable @Field("is_closed") String is_closed
    );



}
