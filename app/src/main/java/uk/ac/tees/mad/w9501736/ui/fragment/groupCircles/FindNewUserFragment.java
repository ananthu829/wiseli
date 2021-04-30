package uk.ac.tees.mad.w9501736.ui.fragment.groupCircles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

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


public class FindNewUserFragment extends BaseFragment implements AdapterInterface {


    @BindView(R.id.rvUser)
    RecyclerView rvUser;

    @BindView(R.id.rlSearchView)
    SearchView searchView;


    @BindView(R.id.btnAdd)
    AppCompatButton btnAdd;

    AdapterInterface adapterInterface;

    String SearchData ="";

    ArrayList<UserFriendsList> friendsList  = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_new_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapterInterface = this;

        getCircle(SearchData);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SearchData.isEmpty() && SearchData != null)
                {
                    addCircle(SearchData);
                }

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchData =query;
                getCircle(SearchData);
//                if(friendsList.contains(query)){
//                    getCircle("");
//                    //adapter.getFilter().filter(query);
//                }else{
//                    Toast.makeText(getContext(), "No Match found",Toast.LENGTH_LONG).show();
//                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    private void getCircle(String data) {
        showProgressBar(true);
        friendsList.clear();
        Call<Resource<ArrayList<UserFriendsList>>> api = mRetrofitService.findCircle(getWiseLiUser().getToken(),data);

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

    private void addCircle(String text) {
        showProgressBar(true);

        Call<BasicResponse> api = mRetrofitService.addCircle(getWiseLiUser().getToken(), text, getWiseLiUser().getLatitude(), getWiseLiUser().getLongitude());


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


    @Override
    protected int layoutRes() {
        return R.layout.fragment_find_new_user;
    }


    @Override
    public void onItemClicked(String title, Integer circleID) {
        SearchData =title;

    }

    @Override
    public void onDeleteCtaClicked(Integer id) {

    }

    @Override
    public void setEditableText(Integer id, String name) {

    }
}