package uk.ac.tees.mad.w9501736.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class ActiveInActiveBody {




    private int circleId;
    @PrimaryKey
    @SerializedName("list_id")
    private int listId;

    @SerializedName("list_name")
    private String listName;

    @SerializedName("isActive")
    private Boolean isActive;

    @Ignore
    public ActiveInActiveBody() {
    }

    public ActiveInActiveBody(int circleId, int listId, String listName, Boolean isActive) {
        this.circleId = circleId;
        this.listId = listId;
        this.listName = listName;
        this.isActive = isActive;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
