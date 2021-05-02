package uk.ac.tees.mad.w9501736.ui.fragment.activePage;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

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
import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;
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
    private static final String TAG = "ActiveFragment";
    private static final String CIRCLEID = "circle_id";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView active;
    List<ActiveInActiveBody> activeList;
    Disposable dUserList;
    UserAdapter userAdapter;
    FloatingActionButton addUser;
    private String mParam2;
    private View view;
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
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_active, container, false);
    }

    @Override
    public void onResume() {
        getActiveList();

        super.onResume();
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
        addUser = view.findViewById(R.id.fab);

        loadGetActiveList();

        addUser.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.custom_dialog_add_user);
            Button btnOk = dialog.findViewById(R.id.btnOk);
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            TextInputLayout edtShopName = dialog.findViewById(R.id.edtShopName);
            TextInputLayout edtPurchaseListName = dialog.findViewById(R.id.edtPurchaseListName);

            dialog.show();
            btnOk.setOnClickListener(v13 -> {
                if (edtShopName.getEditText().getText().toString().length() == 0 && edtPurchaseListName.getEditText().getText().toString().length() == 0) {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.please_provide), Snackbar.LENGTH_LONG).show();
                } else {
                    dialog.dismiss();
                    createShoppingList(edtShopName.getEditText().getText().toString(), edtPurchaseListName.getEditText().getText().toString());
                }
            });
            btnCancel.setOnClickListener(v14 -> dialog.dismiss());
        });

    }

    private void loadGetActiveList() {
        if (isNetworkAvailable(getContext())) {
            addUser.setEnabled(true);
            getActiveList();
        } else {
            addUser.setEnabled(false);
            activeList.clear();
            DatabaseFactory.getInstance().getCircleActiveInactiveDataFromDatabase(true,circleId, result -> {
                    if (result.size() != 0) {
                        activeList.addAll(result);
                        Observable<List<ActiveInActiveBody>> listObservable = Observable.just(activeList);
                        listObservable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableObserver<List<ActiveInActiveBody>>() {
                                    @Override
                                    public void onNext(@NonNull List<ActiveInActiveBody> activeList) {
                                        // You can access your Book objects here
                                        adapterSet();
                                        userAdapter.updateListItem(activeList);
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
    public void onItemClicked(String shoppingListName, Integer listID) {
        if (isNetworkAvailable(getContext())) {
            mAppPreferences.setActive(true);
            Bundle bundle = new Bundle();
            bundle.putInt(CIRCLEID, circleId);
            bundle.putInt("list_id", listID);
            bundle.putString("list_name", shoppingListName);
            Navigation.findNavController(view).navigate(R.id.action_circleDetailFragment_to_listFragment, bundle);
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.snack_error_network), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDeleteCtaClicked(Integer listID) {
        Log.d(TAG, "onDeleteCtaClicked: listID: " + listID);

    }

    @Override
    public void setEditableText(Integer listID, String lastName) {
        Log.d(TAG, "setEditableText: listID : " + listID);
        if (isNetworkAvailable(getContext())) {
            editShoppingList(listID, lastName);
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.snack_error_network), Snackbar.LENGTH_LONG).show();
        }
    }

    private void getActiveList() {
        showProgressBar(true);
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
                    adapterSet();
                    activeList.clear();
                    for (ActiveInActiveBody activeBody : value.getData()) {
                        activeBody.setCircleId(circleId);
                        activeBody.setActive(true);
                        activeList.add(activeBody);
                    }
                    DatabaseFactory.getInstance().insertCircleActiveInactiveData(activeList);
                    userAdapter.updateListItem(activeList);
                } else {

                    Snackbar.make(getActivity().findViewById(android.R.id.content), value.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                showProgressBar(false);
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

    public void adapterSet() {
        active.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        userAdapter = new UserAdapter(activeList, true, this);
        active.setAdapter(userAdapter);
    }

    private void createShoppingList(String shopName, String listName) {
        showProgressBar(true);
        Retrofit retrofit = new WiseLiApiClient().getRetrofitClient();
        final GroupApiService webServices = retrofit.create(GroupApiService.class);
        Observable<Resource<WiseLiUser>> likedObservable = webServices.createShoppingList(getWiseLiUser().getToken(), circleId, shopName, listName);
        likedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createShoppingListObserve());
    }

    private Observer<Resource<WiseLiUser>> createShoppingListObserve() {
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
                    getActiveList();
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
                showProgressBar(false);
                Log.d("getUserList", " onComplete");
            }
        };
    }

    private void editShoppingList(Integer listId, String listName) {
        Retrofit retrofit = new WiseLiApiClient().getRetrofitClient();
        final GroupApiService webServices = retrofit.create(GroupApiService.class);
        Observable<Resource<WiseLiUser>> likedObservable = webServices.editShoppingList(getWiseLiUser().getToken(), listId, listName);
        likedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(editShoppingListObserve());
    }

    private Observer<Resource<WiseLiUser>> editShoppingListObserve() {
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
                    getActiveList();
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), value.getMessage(), Snackbar.LENGTH_LONG).show();
                    showProgressBar(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG).show();
                Log.d("getUserList", " onError : value : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("getUserList", " onComplete");
            }
        };
    }

    private void deleteShoppingList(Integer listId) {
        showProgressBar(true);
        Retrofit retrofit = new WiseLiApiClient().getRetrofitClient();
        final GroupApiService webServices = retrofit.create(GroupApiService.class);
        Observable<Resource<WiseLiUser>> likedObservable = webServices.deleteShoppingList(getWiseLiUser().getToken(), listId);
        likedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleteShoppingListObserve());
    }

    private Observer<Resource<WiseLiUser>> deleteShoppingListObserve() {
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
                    getActiveList();
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
