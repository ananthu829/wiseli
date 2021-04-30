package uk.ac.tees.mad.w9501736.models;

import com.google.gson.annotations.SerializedName;

public class ActiveInActiveBody {
    @SerializedName("list_id")
    private int listId;

    @SerializedName("list_name")
    private String listName;

    public ActiveInActiveBody(int listId, String listName) {
        this.listId = listId;
        this.listName = listName;
    }

    public int getListId() {
        return listId;
    }

    public String getListName() {
        return listName;
    }
}
