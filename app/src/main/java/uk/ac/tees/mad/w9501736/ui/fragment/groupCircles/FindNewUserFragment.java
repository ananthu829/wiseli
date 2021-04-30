package uk.ac.tees.mad.w9501736.ui.fragment.groupCircles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.adapters.UserListAdapter;
import uk.ac.tees.mad.w9501736.models.AvailableUserList;
import uk.ac.tees.mad.w9501736.ui.BaseFragment;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;


public class FindNewUserFragment extends BaseFragment implements AdapterInterface {


    @BindView(R.id.rvUser)
    RecyclerView rvUser;

    @BindView(R.id.rlSearchView)
    SearchView searchView;

    AdapterInterface adapterInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_new_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapterInterface = this;

        ArrayList data = new ArrayList<>();
        data.add(new AvailableUserList("User 1", false));
        data.add(new AvailableUserList("User 2", false));
        data.add(new AvailableUserList("User 3", false));
        data.add(new AvailableUserList("User 4", false));
        rvUser.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rvUser.setAdapter(new UserListAdapter(data, adapterInterface));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(data.contains(query)){
                    //adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(getContext(), "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }
        });
    }



    @Override
    protected int layoutRes() {
        return R.layout.fragment_find_new_user;
    }

    @Override
    public void onItemClicked(String title) {

    }

    @Override
    public void onDeleteCtaClicked(String id) {

    }

    @Override
    public void setEditableText(String id, String name) {

    }
}