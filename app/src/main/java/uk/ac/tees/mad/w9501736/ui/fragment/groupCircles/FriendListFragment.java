package uk.ac.tees.mad.w9501736.ui.fragment.groupCircles;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.tees.mad.w9501736.Database.DatabaseFactory;
import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.adapters.FriendsAdapter;
import uk.ac.tees.mad.w9501736.data.model.Resource;
import uk.ac.tees.mad.w9501736.models.BasicResponse;
import uk.ac.tees.mad.w9501736.models.UserFriendsList;
import uk.ac.tees.mad.w9501736.ui.BaseFragment;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendListFragment extends BaseFragment implements AdapterInterface {

    ArrayList<UserFriendsList> infos = new ArrayList<>();

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.rvUser)
    RecyclerView rvUser;

    AdapterInterface adapterInterface;

    public FriendListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group_circles, container, false);
    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_group_circles;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapterInterface = this;
        loadFunctionality();

        btnClick(view);
    }

    private void loadFunctionality() {
        if (isNetworkAvailable(getContext())) {
            fab.setEnabled(true);
            getFriendsList();
        } else {
            fab.setEnabled(false);
            infos.clear();
            DatabaseFactory.getInstance().getFriendsDataFromDatabase(result -> {
                if (result.getValue() != null) {
                    if (result.getValue().size() != 0) {
                        infos.addAll((Collection<? extends UserFriendsList>) result);
                        recycle();

                    }
                }
            });
        }
    }

    private void getFriendsList() {
        showProgressBar(true);
        infos.clear();
        Call<Resource<ArrayList<UserFriendsList>>> api = mRetrofitService.getFriendsList(getWiseLiUser().getToken());


        api.enqueue(new Callback<Resource<ArrayList<UserFriendsList>>>() {
            @Override
            public void onResponse(Call<Resource<ArrayList<UserFriendsList>>> responseCall, Response<Resource<ArrayList<UserFriendsList>>> response) {
                showProgressBar(false);

                if (response.body() != null) {
                    for (int i = 0; i < response.body().data.size(); i++) {
                        DatabaseFactory.getInstance().insertFriendsData(response.body().data.get(i));
                    }
                    infos.addAll(response.body().getData());
                    recycle();
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

    public void btnClick(View view) {
        fab.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_groupCirclesFragment_to_findNewUserFragment));
    }

    private void deleteCircle(Integer id) {
        showProgressBar(true);

        Call<BasicResponse> api = mRetrofitService.deleteCircle(getWiseLiUser().getToken(), id);


        api.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> responseCall, Response<BasicResponse> response) {
                showProgressBar(false);

                if (response.body() != null) {
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
    public void onItemClicked(String title, Integer circleID) {

    }

    public void recycle() {
        rvUser.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rvUser.setAdapter(new FriendsAdapter(infos, this));
    }

    @Override
    public void onDeleteCtaClicked(Integer id) {
        deleteCircle(id);
        for (int i = 0; i < infos.size(); i++) {
            if (infos.get(i).getUserId() == id) {
                infos.remove(i);
            }
        }
        recycle();
    }

    @Override
    public void setEditableText(Integer id, String value) {
    }
}
