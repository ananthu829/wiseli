package com.promatas.wiseli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.promatas.wiseli.adapters.UserAdapter;
import com.promatas.wiseli.models.User;

import java.util.ArrayList;

public class CircleDetails extends AppCompatActivity {

    TextView title;
    RecyclerView active, inactive;
    ArrayList<User> chips, activeList, inactiveLists;
    ChipGroup chipGroup;
    Chip newChip;
    TabHost tabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_details);

        Bundle bundle=getIntent().getExtras();
tabs=findViewById(R.id.tabhost);
tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.ACTIVE);
        spec.setIndicator("Active");
        tabs.addTab(spec);
        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.INACTIVE);
        spec.setIndicator("Inactive");
        tabs.addTab(spec);

        chips=new ArrayList<>();
        activeList=new ArrayList<>();
        inactiveLists= new ArrayList<>();

        chipGroup= findViewById(R.id.chipgroup);


        chips.add(new User("User 1"));
        chips.add(new User("User 2"));
        chips.add(new User("User 3"));
        chips.add(new User("User 4"));
        chips.add(new User("User 5"));
        chips.add(new User("User 6"));

        for(User u: chips)
        {
            newChip=new Chip(this);
            newChip.setText(u.getCaption());
            chipGroup.addView(newChip);

        }

        String caption= bundle.getString("caption", "Circle Details");

        title=findViewById(R.id.titleTxt);
        title.setText(caption);

        active=findViewById(R.id.activeRv);
        inactive=findViewById(R.id.inactiveRv);



        activeList.add(new User("User 1"));
        inactiveLists.add(new User("User 2"));
        activeList.add(new User("User 3"));
        inactiveLists.add(new User("User 4"));
        activeList.add(new User("User 5"));
        inactiveLists.add(new User("User 6"));

        active.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        inactive.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        active.setAdapter(new UserAdapter(this, activeList, this));
        inactive.setAdapter(new UserAdapter(this, inactiveLists, this));





    }
}