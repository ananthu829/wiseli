package uk.ac.tees.mad.w9501736.ui.fragment.landingPage;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.adapters.CircleAdapter;
import uk.ac.tees.mad.w9501736.models.CircleInfo;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class LandingFragment extends Fragment implements AdapterInterface {

    RecyclerView circles;
    ArrayList<CircleInfo> infos;
    private AdapterInterface listener;
    private View view;

    public LandingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        infos = new ArrayList<>();

        infos.add(new CircleInfo("Circle 1"));
        infos.add(new CircleInfo("Circle 2"));
        infos.add(new CircleInfo("Circle 2"));


        circles = view.findViewById(R.id.homeRecyclerView);


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
    public void buttonPressed(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("caption", title);
        Navigation.findNavController(view).navigate(R.id.action_landingFragment_to_circularFragment, bundle);
    }
}
