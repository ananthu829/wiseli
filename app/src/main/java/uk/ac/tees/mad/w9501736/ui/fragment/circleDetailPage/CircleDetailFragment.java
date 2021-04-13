package uk.ac.tees.mad.w9501736.ui.fragment.circleDetailPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.adapters.TabPagerAdapter;
import uk.ac.tees.mad.w9501736.models.User;
import uk.ac.tees.mad.w9501736.ui.activity.LandingActivity;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link CircleDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CircleDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAB = "param2";
    ArrayList<User> chips;
    ChipGroup chipGroup;
    Chip newChip;
    private TabLayout tabLayout;
    private ViewPager viewPager;
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

        if (getArguments() != null) {
            String title = getArguments().getString("caption");
            if (title != null) {
                ((LandingActivity) getActivity()).setToolbarTitle(title);
            }
        }

        tabLayout = view.findViewById(R.id.tl);
        viewPager = view.findViewById(R.id.vp);

        tabLayout.addTab(tabLayout.newTab().setText("Active"));
        tabLayout.addTab(tabLayout.newTab().setText("Inactive"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final TabPagerAdapter adapter = new TabPagerAdapter(getContext(), getParentFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

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
}
