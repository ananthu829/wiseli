package com.promatas.wiseli.ui.fragment.circleDetailPage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.promatas.wiseli.R;
import com.promatas.wiseli.adapters.UserAdapter;
import com.promatas.wiseli.models.User;
import com.promatas.wiseli.ui.helper.AdapterInterface;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link CircleDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CircleDetailFragment extends Fragment implements AdapterInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView title;
    RecyclerView active, inactive;
    ArrayList<User> chips, activeList, inactiveLists;
    ChipGroup chipGroup;
    Chip newChip;
    TabHost tabs;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AdapterInterface listener;
    private View view;


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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_circle_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        //String caption = getArguments().getString("caption", "Circle Details");
        tabs = view.findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.ACTIVE);
        spec.setIndicator("Active");
        tabs.addTab(spec);
        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.INACTIVE);
        spec.setIndicator("Inactive");
        tabs.addTab(spec);

        chips = new ArrayList<>();
        activeList = new ArrayList<>();
        inactiveLists = new ArrayList<>();

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
            chipGroup.addView(newChip);

        }


        title = view.findViewById(R.id.titleTxt);
        title.setText("Circle Details");

        active = view.findViewById(R.id.activeRv);
        inactive = view.findViewById(R.id.inactiveRv);


        activeList.add(new User("User 1"));
        inactiveLists.add(new User("User 2"));
        activeList.add(new User("User 3"));
        inactiveLists.add(new User("User 4"));
        activeList.add(new User("User 5"));
        inactiveLists.add(new User("User 6"));

        active.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        inactive.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        active.setAdapter(new UserAdapter(activeList, this));
        inactive.setAdapter(new UserAdapter(inactiveLists, this));


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
    public void buttonPressed() {

        Navigation.findNavController(view).navigate(R.id.action_circleDetailFragment_to_listFragment);
    }
}
