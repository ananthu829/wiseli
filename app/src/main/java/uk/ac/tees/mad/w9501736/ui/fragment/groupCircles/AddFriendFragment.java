package uk.ac.tees.mad.w9501736.ui.fragment.groupCircles;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.adapters.UserListAdapter;
import uk.ac.tees.mad.w9501736.data.model.Resource;
import uk.ac.tees.mad.w9501736.models.BasicResponse;
import uk.ac.tees.mad.w9501736.models.UserFriendsList;
import uk.ac.tees.mad.w9501736.ui.BaseFragment;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;


public class AddFriendFragment extends BaseFragment implements AdapterInterface {


    @BindView(R.id.rvUser)
    RecyclerView rvUser;

    @BindView(R.id.rlSearchView)
    SearchView searchView;


    @BindView(R.id.btnAdd)
    AppCompatButton btnAdd;

    AdapterInterface adapterInterface;

    String SearchData = "";

    Integer friendId = 0;

    ArrayList<UserFriendsList> friendsList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_new_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapterInterface = this;

        findFriend(SearchData);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (friendId != 0) {
                    addUser(friendId);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchData = query;
                findFriend(SearchData);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void findFriend(String userName) {
        showProgressBar(true);
        friendsList.clear();
        Call<Resource<ArrayList<UserFriendsList>>> api = mRetrofitService.findFriends(getWiseLiUser().getToken(), userName);

        api.enqueue(new Callback<Resource<ArrayList<UserFriendsList>>>() {
            @Override
            public void onResponse(Call<Resource<ArrayList<UserFriendsList>>> call, Response<Resource<ArrayList<UserFriendsList>>> response) {
                showProgressBar(false);

                if (response.body() != null) {
                    friendsList.addAll(response.body().getData());
                    recycle();
                } else {
                    Log.d("tag1", "Failed---");
                }
            }

            @Override
            public void onFailure(Call<Resource<ArrayList<UserFriendsList>>> call, Throwable t) {
                t.printStackTrace();
                showProgressBar(false);

            }
        });

    }

    public void recycle() {
        rvUser.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rvUser.setAdapter(new UserListAdapter(friendsList, this));
    }

    private void addUser(Integer friendId) {
        showProgressBar(true);

        Call<BasicResponse> api = mRetrofitService.addFriend(getWiseLiUser().getToken(), friendId);

        api.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> responseCall, Response<BasicResponse> response) {
                showProgressBar(false);
                if (response.body() != null) {
                    Navigation.findNavController(rvUser).navigateUp();
                } else {
                    Log.d("tag1", "Failed---");
                }
            }

            @Override
            public void onFailure(Call<BasicResponse> responseCall, Throwable t) {
                t.printStackTrace();
                showProgressBar(false);
            }


        });
    }

    private void getFriendsList() {
        showProgressBar(true);
        Call<Resource<ArrayList<UserFriendsList>>> api = mRetrofitService.getFriendsList(getWiseLiUser().getToken());
        api.enqueue(new Callback<Resource<ArrayList<UserFriendsList>>>() {
            @Override
            public void onResponse(Call<Resource<ArrayList<UserFriendsList>>> responseCall, Response<Resource<ArrayList<UserFriendsList>>> response) {
                showProgressBar(false);
                if (response.body() != null) {

                } else {
                    Log.d("tag1", "Failed---");
                }
            }

            @Override
            public void onFailure(Call<Resource<ArrayList<UserFriendsList>>> responseCall, Throwable t) {
                t.printStackTrace();
                showProgressBar(false);
            }
        });
    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_find_new_user;
    }


    @Override
    public void onItemClicked(String title, Integer friendId) {
        SearchData = title;
        this.friendId = friendId;

    }

    @Override
    public void onDeleteCtaClicked(Integer id) {

    }

    @Override
    public void setEditableText(Integer id, String name) {

    }
}