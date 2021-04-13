package uk.ac.tees.mad.w9501736.ui.fragment.activePage;

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
 * Activities that contain this fragment must implement the
 * Use the {@link ActiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActiveFragment extends Fragment implements AdapterInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView active;
    ArrayList<User> activeList;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;


    public ActiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActiveFragment newInstance(String param1, String param2) {
        ActiveFragment fragment = new ActiveFragment();
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
        return inflater.inflate(R.layout.fragment_active, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        activeList = new ArrayList<>();
        active = view.findViewById(R.id.activeRv);

        activeList.add(new User("User 1"));
        activeList.add(new User("User 3"));
        activeList.add(new User("User 5"));
        activeList.add(new User("User 7"));
        activeList.add(new User("User 9"));
        activeList.add(new User("User 11"));
        activeList.add(new User("User 13"));
        activeList.add(new User("User 15"));
        activeList.add(new User("User 17"));

        active.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        active.setAdapter(new UserAdapter(activeList, true, this));

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
