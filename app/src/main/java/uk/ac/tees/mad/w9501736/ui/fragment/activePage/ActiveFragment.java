package uk.ac.tees.mad.w9501736.ui.fragment.activePage;

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

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.adapters.UserAdapter;
import uk.ac.tees.mad.w9501736.data.model.Resource;
import uk.ac.tees.mad.w9501736.data.remote.GroupApiService;
import uk.ac.tees.mad.w9501736.data.remote.WiseLiApiClient;
import uk.ac.tees.mad.w9501736.models.ActiveInActiveBody;
import uk.ac.tees.mad.w9501736.ui.BaseFragment;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link ActiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActiveFragment extends BaseFragment implements AdapterInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CIRCLEID = "circle_id";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView active;
    List<ActiveInActiveBody> activeList;
    Disposable dUserList;
    private String mParam2;
    private View view;
    UserAdapter userAdapter;
    // TODO: Rename and change types of parameters
    private Integer circleId = 0;

    public ActiveFragment() {
    }

    public ActiveFragment(Integer circleId) {
        this.circleId = circleId;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ActiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActiveFragment newInstance(Integer circleId) {
        ActiveFragment fragment = new ActiveFragment();
        Bundle args = new Bundle();
        args.putInt(CIRCLEID, circleId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            circleId = getArguments().getInt(CIRCLEID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_active, container, false);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_active;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        activeList = new ArrayList<>();
        active = view.findViewById(R.id.activeRv);

        getActiveList();

        active.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        userAdapter = new UserAdapter(activeList, true, this);
        active.setAdapter(userAdapter);

    }

    @Override
    public void onItemClicked(String title, Integer Id) {
        Bundle bundle = new Bundle();
        bundle.putString("caption", title);
        Navigation.findNavController(view).navigate(R.id.action_circleDetailFragment_to_listFragment, bundle);
    }

    @Override
    public void onDeleteCtaClicked(Integer id) {

    }

    @Override
    public void setEditableText(Integer id, String name) {

    }

    private void getActiveList() {
        Retrofit retrofit = new WiseLiApiClient().getRetrofitClient();
        final GroupApiService webServices = retrofit.create(GroupApiService.class);
        Observable<Resource<List<ActiveInActiveBody>>> likedObservable = webServices.getActiveList(getWiseLiUser().getToken(), circleId);
        likedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getActiveListObserve());
    }

    private Observer<Resource<List<ActiveInActiveBody>>> getActiveListObserve() {
        return new Observer<Resource<List<ActiveInActiveBody>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                dUserList = d;
                Log.d("getUserList", " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Resource<List<ActiveInActiveBody>> value) {
                Log.d("getUserList", " onNext : value : " + value);
                if (value.result) {
                    activeList = value.getData();
                    userAdapter.notifyDataSetChanged();
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
