package uk.ac.tees.mad.w9501736.ui.fragment.inActivePage;


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
import uk.ac.tees.mad.w9501736.adapters.UserAdapter;
import uk.ac.tees.mad.w9501736.models.User;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InactiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InactiveFragment extends Fragment implements AdapterInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView inactive;
    ArrayList<User> inactiveLists;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;


    public InactiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InactiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InactiveFragment newInstance(String param1, String param2) {
        InactiveFragment fragment = new InactiveFragment();
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
        return inflater.inflate(R.layout.fragment_inactive, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        inactiveLists = new ArrayList<>();
        inactive = view.findViewById(R.id.inActiveRv);
        inactive.setHasFixedSize(true);
        inactiveLists.add(new User("User 2"));
        inactiveLists.add(new User("User 4"));
        inactiveLists.add(new User("User 6"));
        inactiveLists.add(new User("User 8"));
        inactiveLists.add(new User("User 10"));
        inactiveLists.add(new User("User 12"));
        inactiveLists.add(new User("User 14"));
        inactiveLists.add(new User("User 16"));
        inactiveLists.add(new User("User 18"));

        inactive.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        inactive.setAdapter(new UserAdapter(inactiveLists, false, this));

    }

    @Override
    public void onItemClicked(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("caption", title);
        Navigation.findNavController(view).navigate(R.id.action_circleDetailFragment_to_listFragment, bundle);
    }

    @Override
    public void onDeleteCtaClicked() {

    }
}
