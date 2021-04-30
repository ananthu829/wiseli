package uk.ac.tees.mad.w9501736.ui.fragment.listPage;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.adapters.CommentAdapter;
import uk.ac.tees.mad.w9501736.adapters.ItemAdapter;
import uk.ac.tees.mad.w9501736.common.CommonEditableTextView;
import uk.ac.tees.mad.w9501736.models.BasicResponse;
import uk.ac.tees.mad.w9501736.models.Comment;
import uk.ac.tees.mad.w9501736.models.Item;
import uk.ac.tees.mad.w9501736.models.ItemsList;
import uk.ac.tees.mad.w9501736.models.ShoppingList;
import uk.ac.tees.mad.w9501736.ui.BaseFragment;
import uk.ac.tees.mad.w9501736.ui.activity.LandingActivity;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;
import uk.ac.tees.mad.w9501736.utils.UtilHelper;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends BaseFragment implements AdapterView.OnItemSelectedListener, AdapterInterface {
    ArrayList<String> items, qty;
    List<String> list = new ArrayList<>();
    List<ItemsList.ListItem> listItem = new ArrayList<>();
    String listName = "";
    ArrayList<Item> itemList;
    Spinner spinner;
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
    TextView btnAddProduct;
    FrameLayout searchMaps;
    Button save;
    String[] courses = {"C", "Data structures",
            "Interview prep", "Algorithms",
            "DSA with java", "OS"};
    String itemId;
    Boolean isclosed = false;

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
    protected int layoutRes() {
        return R.layout.fragment_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        items = new ArrayList<>();
        qty = new ArrayList<>();
        itemList = new ArrayList<>();
        commentArrayList = new ArrayList<>();
        getItems();

        commentArrayList.add(new Comment("Good", "Brian", ""));
        if (!Places.isInitialized()) {
            Places.initialize(getContext(), "AIzaSyAvUIS0LUpyZ3yDDacbvH7smR1ubMFo8mc", Locale.US);
        }
        etComment = view.findViewById(R.id.etComment);
        ivCommentSend = view.findViewById(R.id.ivCommentSend);
        rvList = view.findViewById(R.id.listrv);
        searchMaps = view.findViewById(R.id.view1);
        rvComments = view.findViewById(R.id.rvCommentsList);
        ivLocChose = view.findViewById(R.id.ivLocChose);
        tvShopAddress = view.findViewById(R.id.tvShopAddress);
        cetvTotal = view.findViewById(R.id.cetvTotal);
        btnAddProduct = view.findViewById(R.id.textView4);
        save = view.findViewById(R.id.save);
        cetvTotal.hideImageDeleteBtn(true);
        cetvTotal.setEditableNumberInputType();
        cetvTotal.setEditableHintText("Enter the total Amount");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(isclosed);
            }
        });
        if (getArguments() != null) {
            String title = getArguments().getString("caption");
            if (title != null) {
                ((LandingActivity) getActivity()).setToolbarTitle(title);
            }
        }
        searchMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMaps();
            }
        });

        getShoppingList();
        recy();

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
        List<String> yesOrNo = new ArrayList<>();
        yesOrNo.add("No");
        yesOrNo.add("Yes");
        Spinner spinnerOffline = view.findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, yesOrNo);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerOffline.setAdapter(adapter);

        spinnerOffline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Toast.makeText(getContext(), yesOrNo.get(position), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.custom_dialog_add_product);
                Button btnOk = dialog.findViewById(R.id.btnOk);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);
//                TextInputLayout itm = (TextInputLayout) dialog.findViewById(R.id.edtItem);
                TextInputLayout quantity = dialog.findViewById(R.id.edtQuantity2);
                dialog.show();
                spinner = dialog.findViewById(R.id.edtItem);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        Toast.makeText(getContext(), list.get(position), Toast.LENGTH_SHORT).show();
                        itemId = listItem.get(position).getItem_id();
                        isclosed = position != 0;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (itm.getEditText().getText().toString().isEmpty() || quantity.getEditText().getText().toString().isEmpty()) {
//                            Toast.makeText(view.getContext(), getString(R.string.please_provide), Toast.LENGTH_SHORT).show();
//                        } else {
//                            dialog.dismiss();
//                            Navigation.findNavController(view).navigate(R.id.action_listFragment_to_mapFragment);
//                        }
                        addItemToShoppingList();
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
        ivLocChose.setOnClickListener(v -> {
            Dexter.withContext(getActivity())
                    .withPermissions(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    ).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {
//                        Navigation.findNavController(view).navigate(R.id.action_listFragment_to_mapFragment);
                        if (placeName.length() != 0) {
                            Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?q=" + placeName);
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }
                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        });

    }

    private void saveData(Boolean isclosed) {
        showProgressBar(true);
        Call<BasicResponse> api = mRetrofitService.saveData(mAppPreferences.getToken(), "2", listName, String.valueOf(isclosed));


        api.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NotNull Call<BasicResponse> responseCall, Response<BasicResponse> response) {
                showProgressBar(false);
                Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(@NotNull Call<BasicResponse> responseCall, Throwable t) {
                t.printStackTrace();
                showProgressBar(false);

            }


        });
    }

    private void addItem(String Item, String quantity, Integer listid) {
        itemList.add(new Item(Item, quantity, listid));
        itemAdapter.notifyDataSetChanged();
    }

    private void addCommentItem(String comment, String imgUrl, String userName) {
        commentArrayList.add(new Comment(comment, userName, imgUrl));
        commentAdapter.notifyDataSetChanged();
    }

    private static int AUTOCOMPLETE_REQUEST_CODE = 1;

    private void searchMaps() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(getContext());
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    private String placeName = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                final LatLng location = place.getLatLng();
                Log.i("TAG", "Place: " + place.getAddress() + ", " + place.getId());
                tvShopAddress.setText(place.getName() + "");
//                String strUri = "http://maps.google.com/maps?q=loc:" + place.getLatLng().latitude + "," + place.getLatLng().longitude + " (" + "Label which you want" + ")";
                placeName = place.getName();

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("TAGs", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(),
                courses[i],
                Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getItems() {
        showProgressBar(true);
        Call<ItemsList> api = mRetrofitService.getItems(mAppPreferences.getToken(), "1");


        api.enqueue(new Callback<ItemsList>() {
            @Override
            public void onResponse(Call<ItemsList> responseCall, Response<ItemsList> response) {
                showProgressBar(false);
                if (response.body() != null) {
                    listItem = response.body().getData();

                    for (int i = 0; i < response.body().getData().size(); i++) {

                        list.add(response.body().getData().get(i).getItem_name());
                    }


                } else {


                }


            }

            @Override
            public void onFailure(Call<ItemsList> responseCall, Throwable t) {
                t.printStackTrace();
                showProgressBar(false);

            }


        });
    }


    private void getShoppingList() {
        showProgressBar(true);
        Call<ShoppingList> api = mRetrofitService.getShoppingList(mAppPreferences.getToken(), "3");


        api.enqueue(new Callback<ShoppingList>() {
            @Override
            public void onResponse(Call<ShoppingList> responseCall, Response<ShoppingList> response) {
                showProgressBar(false);
                if (response.body() != null) {
                    if (response.body().getData() != null) {
                        listName = response.body().getData().getName();
                        for (int i = 0; i < response.body().getData().getItemData().size(); i++) {
                            addItem(response.body().getData().getItemData().get(i).getItem_name(), response.body().getData().getItemData().get(i).getQuantity(), response.body().getData().getItemData().get(i).getListitem_id());
                        }

                    }
                } else {


                }


            }

            @Override
            public void onFailure(Call<ShoppingList> responseCall, Throwable t) {
                t.printStackTrace();
                showProgressBar(false);

            }


        });
    }


    private void addItemToShoppingList() {
        showProgressBar(true);
        Call<BasicResponse> api = mRetrofitService.addShoppingList(mAppPreferences.getToken(), "1", itemId, "10");


        api.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> responseCall, Response<BasicResponse> response) {
                showProgressBar(false);
                Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<BasicResponse> responseCall, Throwable t) {
                t.printStackTrace();
                showProgressBar(false);

            }


        });
    }


    private void deleteShoppingList(Integer listId) {
        showProgressBar(true);
        Call<BasicResponse> api = mRetrofitService.deleteListShopping(mAppPreferences.getToken(), listId);


        api.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NotNull Call<BasicResponse> responseCall, Response<BasicResponse> response) {
                showProgressBar(false);
                Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(@NotNull Call<BasicResponse> responseCall, Throwable t) {
                t.printStackTrace();
                showProgressBar(false);

            }


        });
    }

    public void recy() {
        commentAdapter = new CommentAdapter(commentArrayList);
        itemAdapter = new ItemAdapter(itemList, this);
    }

    @Override
    public void onItemClicked(String title, Integer circleID) {

    }

    @Override
    public void onDeleteCtaClicked(Integer id) {
        deleteShoppingList(id);
    }

    @Override
    public void setEditableText(Integer id, String name) {

    }
}
