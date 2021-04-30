package uk.ac.tees.mad.w9501736.ui.fragment.circleDetailPage;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

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
import uk.ac.tees.mad.w9501736.models.AvailableUserList;
import uk.ac.tees.mad.w9501736.models.User;
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
    ArrayList<User> chips;
    ChipGroup chipGroup;
    Chip newChip;
    FloatingActionButton addUser;
    ArrayList data;
    RecyclerView recyclerview;
    String Title = "";
    Disposable dUserList;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;
    private String circleTitle = "";
    private Integer circleID = 0;
    private AdapterInterface adapterInterface;


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
        addUser = view.findViewById(R.id.fab);
        ImageView spino = view.findViewById(R.id.addUserBtn);


        tabLayout.addTab(tabLayout.newTab().setText("Active"));
        tabLayout.addTab(tabLayout.newTab().setText("Inactive"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        adapterInterface = this;


        spino.setOnClickListener(v -> {
            getUserListApiCall();
        });
        addUser.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.custom_dialog_add_user);
            Button btnOk = dialog.findViewById(R.id.btnOk);
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            TextInputLayout txt = dialog.findViewById(R.id.edtLastName);


            dialog.show();
            btnOk.setOnClickListener(v13 -> {
                if (txt.getEditText().getText().toString().isEmpty()) {
                    Toast.makeText(view.getContext(), getString(R.string.please_provide), Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Bundle bundle = new Bundle();
                    bundle.putString("caption", txt.getEditText().getText().toString());
                    Navigation.findNavController(view).navigate(R.id.action_circleDetailFragment_to_listFragment, bundle);
                }
            });
            btnCancel.setOnClickListener(v14 -> dialog.dismiss());
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

        chips = new ArrayList<>();

        chipGroup = view.findViewById(R.id.chipgroup);


        chips.add(new User("User 1"));
        chips.add(new User("User 2"));
        chips.add(new User("User 3"));
        chips.add(new User("User 4"));
        chips.add(new User("User 5"));
        chips.add(new User("User 6"));

        for (User u : chips) {
            newChip = new Chip(getContext());
            newChip.setText(u.getCaption());
            newChip.setCloseIconVisible(true);
            chipGroup.addView(newChip);
            newChip.setOnCloseIconClickListener(v -> {
                Toast.makeText(getContext(), u.getCaption() + "will get deleted.", Toast.LENGTH_SHORT).show();
            });
        }

    }

    @Override
    public void onItemClicked(String title, Integer circleID) {
        Title = title;
        Toast.makeText(view.getContext(), title, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteCtaClicked(String id) {

    }

    @Override
    public void setEditableText(String id, String name) {

    }

    private void getUserListApiCall() {
        showProgressBar(true);
        Retrofit retrofit = new WiseLiApiClient().getRetrofitClient();
        final GroupApiService webServices = retrofit.create(GroupApiService.class);
        Observable<Resource<ArrayList<WiseLiUser>>> likedObservable = webServices.getFriendsListCircle1(getWiseLiUser().getToken(), circleID);
        likedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUserList());
    }

    private Observer<Resource<ArrayList<WiseLiUser>>> getUserList() {
        return new Observer<Resource<ArrayList<WiseLiUser>>>() {

            @Override
            public void onSubscribe(Disposable d) {
                dUserList = d;
                Log.d("getUserList", " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Resource<ArrayList<WiseLiUser>> value) {
                Log.d("getUserList", " onNext : value : " + value);
                if (value.result) {
                    showUserListDialog();
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

    public void showUserListDialog() {
        showProgressBar(false);

        getApiListForDialog();

    }

    public void getApiListForDialog() {
        showProgressBar(true);
        Retrofit retrofit = new WiseLiApiClient().getRetrofitClient();
        final GroupApiService webServices = retrofit.create(GroupApiService.class);
        Observable<Resource<ArrayList<WiseLiUser>>> likedObservable = webServices.getCircleDetails2(getWiseLiUser().getToken(), circleID);
        likedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getCircleDetails());
    }

    private Observer<Resource<ArrayList<WiseLiUser>>> getCircleDetails() {
        return new Observer<Resource<ArrayList<WiseLiUser>>>() {

            @Override
            public void onSubscribe(Disposable d) {
                dUserList = d;
                Log.d("getUserList", " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Resource<ArrayList<WiseLiUser>> value) {
                Log.d("getUserList", " onNext : value : " + value);
                if (value.result) {
                    final Dialog dialog = new Dialog(view.getContext());
                    dialog.setContentView(R.layout.custom_dialog_list_user);
                    Button btnOk = dialog.findViewById(R.id.btnOk);
                    Button btnCancel = dialog.findViewById(R.id.btnCancel);
                    recyclerview = dialog.findViewById(R.id.homeRecyclerView);
                    data = new ArrayList<>();
                    data.add(new AvailableUserList("User 1", false));
                    data.add(new AvailableUserList("User 2", false));
                    data.add(new AvailableUserList("User 3", false));
                    data.add(new AvailableUserList("User 4", false));

                    recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    recyclerview.setAdapter(new UserListAdapter(data, adapterInterface));
                    dialog.show();
                    dialog.setOnDismissListener(dialog1 -> Title = "");
                    btnOk.setOnClickListener(v1 -> {
                        if (Title.isEmpty()) {
                            Toast.makeText(view.getContext(), getString(R.string.please_provide), Toast.LENGTH_SHORT).show();

                        } else {
                            dialog.dismiss();
                            Bundle bundle = new Bundle();
                            bundle.putString("caption", Title);
                            Navigation.findNavController(view).navigate(R.id.action_circleDetailFragment_to_listFragment, bundle);
                        }

                    });
                    btnCancel.setOnClickListener(v12 -> dialog.dismiss());
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
