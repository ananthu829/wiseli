package uk.ac.tees.mad.w9501736.ui.fragment.landingPage;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.adapters.CircleAdapter;
import uk.ac.tees.mad.w9501736.models.BasicResponse;
import uk.ac.tees.mad.w9501736.models.CircleModel;
import uk.ac.tees.mad.w9501736.ui.BaseFragment;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class LandingFragment extends BaseFragment implements AdapterInterface {

    RecyclerView circles;
    ArrayList<CircleModel.CircleData> infos = new ArrayList<>();
    CircleAdapter circleAdapter;
    private AdapterInterface listener;
    private View view;
    FloatingActionButton Fab;


    Dialog dialog;

    public LandingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_landing, container, false);

        return v;
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_landing;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        getCircle();


        Fab = view.findViewById(R.id.fab);
        circles = view.findViewById(R.id.homeRecyclerView);
        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.custom_dialog_add_circle);
                Button btnOk = dialog.findViewById(R.id.btnOk);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                TextInputLayout txt = dialog.findViewById(R.id.edtLastName);
                dialog.show();

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (txt.getEditText().getText().toString().isEmpty()) {
                            Toast.makeText(view.getContext(), getString(R.string.please_provide), Toast.LENGTH_SHORT).show();
                        } else {
                            addCircle(txt.getEditText().getText().toString());
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });



    }

    private void addCircle(String text) {
        showProgressBar(true);

        Call<BasicResponse> api = mRetrofitService.addCircle(getWiseLiUser().getToken(), text, getWiseLiUser().getLatitude(), getWiseLiUser().getLongitude());


        api.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> responseCall, Response<BasicResponse> response) {
                showProgressBar(false);

                if (response.body() != null) {
                    dialog.dismiss();
                    getCircle();
                } else {
                    Log.d("tag1", "Failed---");
                    dialog.dismiss();

                }


            }

            @Override
            public void onFailure(Call<BasicResponse> responseCall, Throwable t) {
                t.printStackTrace();
                showProgressBar(false);
            }


        });
    }


    private void deleteCircle(String id) {
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


    private void editCircle(String id, String name) {
        showProgressBar(true);

        Call<BasicResponse> api = mRetrofitService.editCircle(getWiseLiUser().getToken(), id, name);


        api.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> responseCall, Response<BasicResponse> response) {
                showProgressBar(false);

                if (response.body() != null) {
                    getCircle();
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


    private void getCircle() {
        showProgressBar(true);
        infos.clear();
        Call<CircleModel> api = mRetrofitService.getCircle(getWiseLiUser().getToken());


        api.enqueue(new Callback<CircleModel>() {
            @Override
            public void onResponse(Call<CircleModel> responseCall, Response<CircleModel> response) {
                showProgressBar(false);

                if (response.body() != null) {
                    infos.addAll(response.body().getData());
                    recycle();
                } else {
                    Log.d("tag1", "Failed---");
                    dialog.dismiss();

                }


            }

            @Override
            public void onFailure(Call<CircleModel> responseCall, Throwable t) {
                t.printStackTrace();
                showProgressBar(false);

            }


        });
    }

    public void recycle() {
        circles.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        circles.setAdapter(new CircleAdapter(infos, this));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AdapterInterface) context;
        } catch (ClassCastException castException) {

        }
    }

    @Override
    public void onItemClicked(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("caption", title);

        Navigation.findNavController(view).navigate(R.id.action_landingFragment_to_circularFragment, bundle);
    }

    @Override
    public void onDeleteCtaClicked(String id) {
        deleteCircle(id);
        for (int i = 0; i < infos.size(); i++) {
            if (infos.get(i).getCircle_id().equals(id)) {
                infos.remove(i);
            }
        }
        circles.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        circles.setAdapter(new CircleAdapter(infos, this));
    }

    @Override
    public void setEditableText(String id, String value) {
        editCircle(id,value);
    }
}
