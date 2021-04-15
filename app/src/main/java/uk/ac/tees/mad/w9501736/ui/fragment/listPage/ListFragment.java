package uk.ac.tees.mad.w9501736.ui.fragment.listPage;


import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.adapters.CommentAdapter;
import uk.ac.tees.mad.w9501736.adapters.ItemAdapter;
import uk.ac.tees.mad.w9501736.common.CommonEditableTextView;
import uk.ac.tees.mad.w9501736.models.Comment;
import uk.ac.tees.mad.w9501736.models.Item;
import uk.ac.tees.mad.w9501736.ui.activity.LandingActivity;
import uk.ac.tees.mad.w9501736.utils.UtilHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    ArrayList<String> items, qty;
    ArrayList<Item> itemList;

    RecyclerView rvList;
    RecyclerView rvComments;
    ArrayList<Comment> commentArrayList;
    AppCompatEditText etComment;
    AppCompatTextView tvShopAddress;
    AppCompatImageView ivCommentSend;
    AppCompatImageView ivLocChose;
    CommentAdapter commentAdapter;
    ItemAdapter itemAdapter;
    CommonEditableTextView cetvTotal;

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
        commentArrayList = new ArrayList<>();

        commentArrayList.add(new Comment("Good", "Brian", ""));


        etComment = view.findViewById(R.id.etComment);
        ivCommentSend = view.findViewById(R.id.ivCommentSend);
        rvList = view.findViewById(R.id.listrv);
        rvComments = view.findViewById(R.id.rvCommentsList);
        ivLocChose = view.findViewById(R.id.ivLocChose);
        tvShopAddress = view.findViewById(R.id.tvShopAddress);
        cetvTotal = view.findViewById(R.id.cetvTotal);
        cetvTotal.hideImageDeleteBtn(true);
        cetvTotal.setEditableNumberInputType();
        cetvTotal.setEditableHintText("Enter the total Amount");

        if (getArguments() != null) {
            String title = getArguments().getString("caption");
            if (title != null) {
                ((LandingActivity) getActivity()).setToolbarTitle(title);
            }
        }

        commentAdapter = new CommentAdapter(commentArrayList);
        itemAdapter = new ItemAdapter(itemList);

        addItem("Oil", "10");
        addItem("Potato", "20");
        addItem("Powder", "50");

        rvList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rvComments.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        rvList.setAdapter(itemAdapter);
        rvComments.setAdapter(commentAdapter);

        ivCommentSend.setOnClickListener(v -> {
            String comment = etComment.getText().toString();
            if (comment.length() > 1) {
                addCommentItem(etComment.getText().toString(), "", "ravi");
                etComment.setText("");
                etComment.setHint("Please add a comment");
                etComment.clearFocus();
                UtilHelper.hideKeyboardFrom(getContext(), view);
            }
        });

        ivLocChose.setOnClickListener(v -> {
            Dexter.withContext(getActivity())
                    .withPermissions(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    ).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {
                        Navigation.findNavController(view).navigate(R.id.action_listFragment_to_mapFragment);
                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        });

    }

    private void addItem(String Item, String quantity) {
        itemList.add(new Item(Item, quantity));
        itemAdapter.notifyDataSetChanged();
    }

    private void addCommentItem(String comment, String imgUrl, String userName) {
        commentArrayList.add(new Comment(comment, userName, imgUrl));
        commentAdapter.notifyDataSetChanged();
    }
}
