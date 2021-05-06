package uk.ac.tees.mad.w9501736.ui.fragment.inActivePage;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import uk.ac.tees.mad.w9501736.Database.DatabaseFactory;
import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.adapters.UserAdapter;
import uk.ac.tees.mad.w9501736.data.model.Resource;
import uk.ac.tees.mad.w9501736.data.remote.GroupApiService;
import uk.ac.tees.mad.w9501736.data.remote.WiseLiApiClient;
import uk.ac.tees.mad.w9501736.models.ActiveInActiveBody;
import uk.ac.tees.mad.w9501736.ui.BaseFragment;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;

public class InactiveFragment extends BaseFragment implements AdapterInterface {

    RecyclerView inactive;
    List<ActiveInActiveBody> inactiveLists;
    Disposable dUserList;
    UserAdapter userAdapter;
    private String mParam2;
    private View view;
    // TODO: Rename and change types of parameters
    private Integer circleId = 0;

    public InactiveFragment() {
    }

    public InactiveFragment(Integer circleId) {
        this.circleId = circleId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inactive, container, false);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_inactive;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        inactiveLists = new ArrayList<>();
        inactive = view.findViewById(R.id.inActiveRv);

        loadGetInactiveList();

        inactive.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        userAdapter = new UserAdapter(inactiveLists, false, this);
        inactive.setAdapter(userAdapter);

    }

    private void loadGetInactiveList() {
        if (isNetworkAvailable(getContext())) {
            getInactiveList();
        } else {
            inactiveLists.clear();
            DatabaseFactory.getInstance().getCircleActiveInactiveDataFromDatabase(false, circleId, result -> {
                if (result.size() != 0) {
                    inactiveLists.addAll(result);
                    Observable<List<ActiveInActiveBody>> listObservable = Observable.just(inactiveLists);
                    listObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<List<ActiveInActiveBody>>() {
                                @Override
                                public void onNext(@NonNull List<ActiveInActiveBody> inactiveLists) {
                                    // You can access your Book objects here
                                    userAdapter.updateListItem(inactiveLists);
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    // Handler errors here
                                }

                                @Override
                                public void onComplete() {
                                    // All your book objects have been fetched. Done!
                                }
                            });
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.snack_error_network) + " and you have no local data", Snackbar.LENGTH_LONG).show();
                }

            });
        }
    }

    @Override
    public void onItemClicked(String title, Integer id) {
        mAppPreferences.setActive(false);
        Bundle bundle = new Bundle();
        bundle.putString("caption", title);
        bundle.putInt("list_id", id);
        bundle.putString("list_name", title);
        Navigation.findNavController(view).navigate(R.id.action_circleDetailFragment_to_listFragment, bundle);
    }

    @Override
    public void onDeleteCtaClicked(Integer id) {

    }

    @Override
    public void setEditableText(Integer id, String name) {

    }

    private void getInactiveList() {
        Retrofit retrofit = new WiseLiApiClient().getRetrofitClient();
        final GroupApiService webServices = retrofit.create(GroupApiService.class);
        Observable<Resource<List<ActiveInActiveBody>>> likedObservable = webServices.getInactiveList(getWiseLiUser().getToken(), circleId);
        likedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getInActiveListObserve());
    }

    private Observer<Resource<List<ActiveInActiveBody>>> getInActiveListObserve() {
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
                    inactiveLists.clear();
                    for (ActiveInActiveBody inActiveBody : value.getData()) {
                        inActiveBody.setCircleId(circleId);
                        inActiveBody.setActive(false);
                        inactiveLists.add(inActiveBody);
                    }
                    DatabaseFactory.getInstance().insertCircleActiveInactiveData(inactiveLists);
                    userAdapter.updateListItem(inactiveLists);
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
