package uk.ac.tees.mad.w9501736.ui.fragment.circleDetailPage;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.adapters.CircleAdapter;
import uk.ac.tees.mad.w9501736.adapters.TabPagerAdapter;
import uk.ac.tees.mad.w9501736.adapters.UserListAdapter;
import uk.ac.tees.mad.w9501736.models.AvailableUserList;
import uk.ac.tees.mad.w9501736.models.CircleInfo;
import uk.ac.tees.mad.w9501736.models.User;
import uk.ac.tees.mad.w9501736.ui.activity.LandingActivity;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link CircleDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CircleDetailFragment extends Fragment  implements AdapterInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAB = "param2";
    public TabPagerAdapter tabPagerAdapter;
    ArrayList<User> chips;
    ChipGroup chipGroup;
    Chip newChip;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;
    FloatingActionButton addUser;
    ArrayList data;
    RecyclerView recyclerview;
    String  Title ="";


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
        addUser = view.findViewById(R.id.fab);
        ImageView spino = view.findViewById(R.id.addUserBtn);




        tabLayout.addTab(tabLayout.newTab().setText("Active"));
        tabLayout.addTab(tabLayout.newTab().setText("Inactive"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        AdapterInterface adapterInterface =this;



        spino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.custom_dialog_list_user);
                Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
                recyclerview = (RecyclerView) dialog.findViewById(R.id.homeRecyclerView);
                data = new ArrayList<>();
                data.add(new AvailableUserList("User 1",false));
                data.add(new AvailableUserList("User 2",false));
                data.add(new AvailableUserList("User 3",false));
                data.add(new AvailableUserList("User 4",false));
                dialog.getWindow().setLayout(600,400);
                recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                recyclerview.setAdapter(new UserListAdapter(data, adapterInterface));
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                dialog.show();
                dialog.setOnDismissListener( new DialogInterface.OnDismissListener(){

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Title ="";
                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Title.isEmpty())
                        {
                            Toast.makeText(view.getContext(),getString(R.string.please_provide),Toast.LENGTH_SHORT).show();

                        }
                        else {
                            dialog.dismiss();
                            Bundle bundle = new Bundle();
                            bundle.putString("caption", Title);
                            Navigation.findNavController(view).navigate(R.id.action_circleDetailFragment_to_listFragment, bundle);
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
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.custom_dialog_add_user);
                Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
                TextInputLayout txt = (TextInputLayout) dialog.findViewById(R.id.edtLastName);


                dialog.show();
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(txt.getEditText().getText().toString().isEmpty())
                        {
                            Toast.makeText(view.getContext(),getString(R.string.please_provide),Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dialog.dismiss();
                            Bundle bundle = new Bundle();
                            bundle.putString("caption", txt.getEditText().getText().toString());
                            Navigation.findNavController(view).navigate(R.id.action_circleDetailFragment_to_listFragment, bundle);
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
    public void onItemClicked(String title) {
        Title = title;
        Toast.makeText(view.getContext(),title,Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onDeleteCtaClicked(String id) {

    }

    @Override
    public void setEditableText(String id,String name) {

    }
}
