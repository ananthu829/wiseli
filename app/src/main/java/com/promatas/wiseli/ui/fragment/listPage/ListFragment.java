package com.promatas.wiseli.ui.fragment.listPage;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.promatas.wiseli.R;
import com.promatas.wiseli.adapters.ItemAdapter;
import com.promatas.wiseli.models.Item;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    ArrayList<String> items, qty, comments;
    ArrayList<Item> itemList;

    RecyclerView rvList;
    ListView commentList;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        items = new ArrayList<>();
        qty = new ArrayList<>();
        itemList = new ArrayList<>();
        comments = new ArrayList<>();


        rvList = view.findViewById(R.id.listrv);
        commentList = view.findViewById(R.id.commentsList);

        addItem("Oil", "10");
        addItem("Potato", "20");
        addItem("Powder", "50");
        comments.add("Good");
        comments.add("Excellet");

        rvList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        rvList.setAdapter(new ItemAdapter(getContext(), itemList));


        commentList.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, comments));

    }

    private void addItem(String Item, String quantity) {
        itemList.add(new Item(Item, quantity));
    }
}
