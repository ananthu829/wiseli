package com.promatas.wiseli;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.promatas.wiseli.adapters.CircleAdapter;
import com.promatas.wiseli.models.CircleInfo;
import com.promatas.wiseli.models.User;

import java.util.ArrayList;

public class MainActivity extends Activity {

    RecyclerView circles;
    ArrayList<CircleInfo> infos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infos=new ArrayList<>();

        infos.add(new CircleInfo("Circle 1"));
        infos.add(new CircleInfo("Circle 2"));
        infos.add(new CircleInfo("Circle 2"));


        circles= findViewById(R.id.homeRecyclerView);


        circles.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        circles.setAdapter(new CircleAdapter(this, infos));
    }
}
