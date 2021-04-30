package uk.ac.tees.mad.w9501736.ui.fragment.circleDetailPage;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.adapters.TabPagerAdapter;
import uk.ac.tees.mad.w9501736.adapters.UserListAdapter;
import uk.ac.tees.mad.w9501736.data.model.Resource;
import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;
import uk.ac.tees.mad.w9501736.data.remote.GroupApiService;
import uk.ac.tees.mad.w9501736.data.remote.WiseLiApiClient;
import uk.ac.tees.mad.w9501736.models.CircleData;
import uk.ac.tees.mad.w9501736.models.FriendsList;
import uk.ac.tees.mad.w9501736.models.UserFriendsList;
import uk.ac.tees.mad.w9501736.ui.BaseFragment;
import uk.ac.tees.mad.w9501736.ui.activity.LandingActivity;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link CircleDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CircleDetailFragment extends BaseFragment implements AdapterInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAB = "param2";
    public TabPagerAdapter tabPagerAdapter;
    ChipGroup chipGroup;
    Chip newChip;
    RecyclerView recyclerview;
    Integer userID;
    String userName = "";
    Disposable dUserList;
    Button btnOk;
    Button btnCancel;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;
    private String circleTitle = "";
    private Integer circleID = 0;
    private AdapterInterface adapterInterface;
    private Dialog dialog;


    public CircleDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CircleDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CircleDetailFragment newInstance(String param1, String param2) {
        CircleDetailFragment fragment = new CircleDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_circle_detail, container, false);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_circle_detail;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        if (getArguments() != null) {
            circleTitle = getArguments().getString("circle_name");
            circleID = getArguments().getInt("circle_id");
            if (circleTitle != null) {
                ((LandingActivity) getActivity()).setToolbarTitle(circleTitle);
            }
        }

        tabLayout = view.findViewById(R.id.tl);
        viewPager = view.findViewById(R.id.vp);
        ImageView spino = view.findViewById(R.id.addUserBtn);


        tabLayout.addTab(tabLayout.newTab().setText("Active"));
        tabLayout.addTab(tabLayout.newTab().setText("Inactive"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        adapterInterface = this;

        chipGroup = view.findViewById(R.id.chipgroup);

        getCircleDetailFromAPISetData();

        spino.setOnClickListener(v -> {
            getUserListApiCall();
        });

        tabPagerAdapter = new TabPagerAdapter(getContext(), getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabPagerAdapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabPagerAdapter.setCircleID(circleID);

    }

    @Override
    public void onItemClicked(String userName, Integer userID) {
        this.userID = userID;
        this.userName = userName;
        Toast.makeText(view.getContext(), userName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteCtaClicked(Integer id) {

    }

    @Override
    public void setEditableText(Integer id, String name) {

    }

    private void getUserListApiCall() {
        showProgressBar(true);
        Retrofit retrofit = new WiseLiApiClient().getRetrofitClient();
        final GroupApiService webServices = retrofit.create(GroupApiService.class);
        Observable<Resource<List<UserFriendsList>>> likedObservable = webServices.getFriendsListCircle1(getWiseLiUser().getToken(), circleID);
        likedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUserList());
    }

    private Observer<Resource<List<UserFriendsList>>> getUserList() {
        return new Observer<Resource<List<UserFriendsList>>>() {

            @Override
            public void onSubscribe(Disposable d) {
                dUserList = d;
                Log.d("getUserList", " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Resource<List<UserFriendsList>> value) {
                Log.d("getUserList", " onNext : value : " + value);
                if (value.result) {
                    if (value.data != null && value.data.size() > 0) {
                        setDialogData(value.data);
                    } else {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.user_friends_list_null), Snackbar.LENGTH_LONG).show();
                    }

                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), value.getMessage(), Snackbar.LENGTH_LONG).show();
                    showProgressBar(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                showProgressBar(false);
                Snackbar.make(getActivity().findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG).show();
                Log.d("getUserList", " onError : value : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("getUserList", " onComplete");
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dUserList != null) {
            dUserList.dispose();
        }
    }

    public void getCircleDetailFromAPISetData() {
        showProgressBar(false);

        getApiListForChipView();
    }

    public void getApiListForChipView() {
        showProgressBar(true);
        Retrofit retrofit = new WiseLiApiClient().getRetrofitClient();
        final GroupApiService webServices = retrofit.create(GroupApiService.class);
        Observable<Resource<CircleData>> likedObservable = webServices.getCircleDetails2(getWiseLiUser().getToken(), circleID);
        likedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getCircleDetails());
    }

    private Observer<Resource<CircleData>> getCircleDetails() {
        return new Observer<Resource<CircleData>>() {
            @Override
            public void onSubscribe(Disposable d) {
                dUserList = d;
                Log.d("getUserList", " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Resource<CircleData> value) {
                Log.d("getUserList", " onNext : value : " + value);
                if (value.result) {
                    if (value.data != null) {
                        if (value.data.getFriendsList() != null && value.data.getFriendsList().size() > 0) {
                            addChips(value.data.getFriendsList());
                            value.data.getCircleId();
                        }
                    }
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), value.getMessage(), Snackbar.LENGTH_LONG).show();
                    showProgressBar(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                showProgressBar(false);
                Snackbar.make(getActivity().findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG).show();
                Log.d("getUserList", " onError : value : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("getUserList", " onComplete");
            }
        };
    }

    private void setDialogData(List<UserFriendsList> userFriendsLists) {
        showProgressBar(false);
        initDialog();
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerview.setAdapter(new UserListAdapter(userFriendsLists, adapterInterface));
        initDialogClicks(userFriendsLists);
    }

    void initDialog() {
        dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.custom_dialog_list_user);
        btnOk = dialog.findViewById(R.id.btnOk);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        recyclerview = dialog.findViewById(R.id.homeRecyclerView);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
    }

    void initDialogClicks(List<UserFriendsList> userFriendsLists) {
        dialog.show();
        dialog.setOnDismissListener(dialog1 -> userName = "");
        btnOk.setOnClickListener(v1 -> {
            if (userFriendsLists != null && userFriendsLists.size() > 0) {
                dialog.dismiss();
                addUserToChipView();
            } else {
                Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.user_friends_list_null), Snackbar.LENGTH_LONG).show();
            }
        });
        btnCancel.setOnClickListener(v12 -> dialog.dismiss());
    }

    private void addUserToChipView() {
        Retrofit retrofit = new WiseLiApiClient().getRetrofitClient();
        final GroupApiService webServices = retrofit.create(GroupApiService.class);
        Observable<Resource<WiseLiUser>> likedObservable = webServices.addCircleUser(getWiseLiUser().getToken(), circleID, userID);
        likedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(addFriendToCircle());
    }

    private Observer<Resource<WiseLiUser>> addFriendToCircle() {
        return new Observer<Resource<WiseLiUser>>() {
            @Override
            public void onSubscribe(Disposable d) {
                dUserList = d;
                Log.d("getUserList", " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Resource<WiseLiUser> value) {
                Log.d("getUserList", " onNext : value : " + value);
                if (value.result) {
                    chipGroup.removeAllViews();
                    getCircleDetailFromAPISetData();
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), value.getMessage(), Snackbar.LENGTH_LONG).show();
                    showProgressBar(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                showProgressBar(false);
                Snackbar.make(getActivity().findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG).show();
                Log.d("getUserList", " onError : value : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("getUserList", " onComplete");
            }
        };
    }

    void addChips(List<FriendsList> userFriendsLists) {
        for (FriendsList friendsList : userFriendsLists) {
            newChip = new Chip(getContext());
            newChip.setText(friendsList.getFullName());
            newChip.setCloseIconVisible(true);
            chipGroup.addView(newChip);
            if (friendsList.isOwner()) {
                newChip.setCloseIconVisible(false);
            }
            newChip.setOnCloseIconClickListener(v -> {
                apiRemoveCircleUser(friendsList.getUserId());
            });
        }
        showProgressBar(false);
    }

    void apiRemoveCircleUser(Integer userID) {
        Retrofit retrofit = new WiseLiApiClient().getRetrofitClient();
        final GroupApiService webServices = retrofit.create(GroupApiService.class);
        Observable<Resource<WiseLiUser>> likedObservable = webServices.removeCircleUser(getWiseLiUser().getToken(), circleID, userID);
        likedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiRemoveCircleUserObserve());
    }

    private Observer<Resource<WiseLiUser>> apiRemoveCircleUserObserve() {
        return new Observer<Resource<WiseLiUser>>() {
            @Override
            public void onSubscribe(Disposable d) {
                dUserList = d;
                Log.d("getUserList", " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Resource<WiseLiUser> value) {
                Log.d("getUserList", " onNext : value : " + value);
                if (value.result) {
                    chipGroup.removeAllViews();
                    getCircleDetailFromAPISetData();
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), value.getMessage(), Snackbar.LENGTH_LONG).show();
                    showProgressBar(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                showProgressBar(false);
                Snackbar.make(getActivity().findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG).show();
                Log.d("getUserList", " onError : value : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("getUserList", " onComplete");
            }
        };
    }
}
