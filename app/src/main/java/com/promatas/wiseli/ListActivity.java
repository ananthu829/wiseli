package com.promatas.wiseli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.promatas.wiseli.adapters.ItemAdapter;
import com.promatas.wiseli.adapters.UserAdapter;
import com.promatas.wiseli.models.Item;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<String> items, qty, comments;
    ArrayList<Item> itemList;

RecyclerView rvList;
ListView commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        items=new ArrayList<>();
        qty=new ArrayList<>();
        itemList=new ArrayList<>();
        comments=new ArrayList<>();



        rvList=findViewById(R.id.listrv);
        commentList=findViewById(R.id.commentsList);

        addItem("Oil", "10");
        addItem("Potato", "20");
        addItem("Powder", "50");
        comments.add("Good");
        comments.add("Excellet");

        rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        rvList.setAdapter(new ItemAdapter(this, itemList));


        commentList.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, comments));













    }

    private void addItem(String Item, String quantity) {

        itemList.add(new Item(Item, quantity));

    }
}