package uk.ac.tees.mad.w9501736.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import javax.inject.Inject;

import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;
import uk.ac.tees.mad.w9501736.data.presistance.UserDao;
import uk.ac.tees.mad.w9501736.data.presistance.UserDatabase;
import uk.ac.tees.mad.w9501736.data.remote.WiseLiApiClient;
import uk.ac.tees.mad.w9501736.utils.ApiResponse;
import uk.ac.tees.mad.w9501736.utils.AppExecutors;
import uk.ac.tees.mad.w9501736.utils.NetworkBoundResource;
import uk.ac.tees.mad.w9501736.utils.Resource;

public class WiseLiRepository {

    private static final String TAG = "WiseLiRepository";
    private static WiseLiRepository instance;
    private WiseLiApiClient wiseLiApiClient;
    private UserDao userDao;

    private WiseLiRepository(Context context) {
        userDao = UserDatabase.getInstance(context).getRecipeDao();
    }

    @Inject
    public WiseLiRepository(WiseLiApiClient wiseLiApiClient) {
        this.wiseLiApiClient = wiseLiApiClient;
    }

    public static WiseLiRepository getInstance(Context context) {
        if (instance == null) {
            instance = new WiseLiRepository(context);
        }
        return instance;
    }

    public LiveData<Resource<WiseLiUser>> registerUser(WiseLiUser wiseLiUser) {
        return new NetworkBoundResource<WiseLiUser, uk.ac.tees.mad.w9501736.data.model.Resource<WiseLiUser>>(AppExecutors.getInstance()) {
            @Override
            protected void saveCallResult(@NonNull uk.ac.tees.mad.w9501736.data.model.Resource<WiseLiUser> item) {
                userDao.insertUser(item.data);

            }

            @Override
            protected boolean shouldFetch(@Nullable WiseLiUser data) {
                return false;
            }


            @NonNull
            @Override
            protected LiveData<WiseLiUser> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<uk.ac.tees.mad.w9501736.data.model.Resource<WiseLiUser>>> createCall() {
                return null;
            }
        }.getAsLiveData();
    }

/*
    public LiveData<Resource<WiseLiUser>> getRegisterUserData(WiseLiUser wiseLiUsers) {
        final MediatorLiveData<Resource<WiseLiUser>> result = new MediatorLiveData<>();
        wiseLiApiClient.registerUser(wiseLiUsers, new Callback<Resource<WiseLiUser>>() {
            @Override
            public void onResponse(@NotNull Call<Resource<WiseLiUser>> call, @NotNull Response<Resource<WiseLiUser>> response) {
                if (response.body() != null) {
                    result.setValue(response.body());
                }

            }

            @Override
            public void onFailure(@NotNull Call<Resource<WiseLiUser>> call, @NotNull Throwable t) {
                result.setValue(new Resource<>(false, "Error message: " + t.getMessage(), null));
            }
        });
        return result;
    }
*/


    /*    public LiveData<Resource<Recipe>> searchRecipesApi(final String recipeId){
        return new NetworkBoundResource<Recipe, RecipeResponse>(AppExecutors.getInstance()){
            @Override
            protected void saveCallResult(@NonNull RecipeResponse item) {

                // will be null if API key is expired
                if(item.getRecipe() != null){
                    item.getRecipe().setTimestamp((int)(System.currentTimeMillis() / 1000));
                    userDao.insertUser(item.getRecipe());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable Recipe data) {
                Log.d(TAG, "shouldFetch: recipe: " + data.toString());
                int currentTime = (int)(System.currentTimeMillis() / 1000);
                Log.d(TAG, "shouldFetch: current time: " + currentTime);
                int lastRefresh = data.getTimestamp();
                Log.d(TAG, "shouldFetch: last refresh: " + lastRefresh);
                Log.d(TAG, "shouldFetch: it's been " + ((currentTime - lastRefresh) / 60 / 60 / 24) +
                        " days since this recipe was refreshed. 30 days must elapse before refreshing. ");
                if((currentTime - data.getTimestamp()) >= Constants.RECIPE_REFRESH_TIME){
                    Log.d(TAG, "shouldFetch: SHOULD REFRESH RECIPE?! " + true);
                    return true;
                }
                Log.d(TAG, "shouldFetch: SHOULD REFRESH RECIPE?! " + false);
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Recipe> loadFromDb() {
                return userDao.getRecipe(recipeId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RecipeResponse>> createCall() {
                return ServiceGenerator.getWiseLiApi().getRecipe(
                        Constants.API_KEY,
                        recipeId
                );
            }
        }.getAsLiveData();
    }*/
}
