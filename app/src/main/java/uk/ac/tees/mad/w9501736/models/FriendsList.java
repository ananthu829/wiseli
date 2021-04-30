package uk.ac.tees.mad.w9501736.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FriendsList implements Parcelable {
    public static final Creator<FriendsList> CREATOR = new Creator<FriendsList>() {
        @Override
        public FriendsList createFromParcel(Parcel source) {
            return new FriendsList(source);
        }

        @Override
        public FriendsList[] newArray(int size) {
            return new FriendsList[size];
        }
    };
    private boolean isSelected;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("username")
    private String username;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("is_owner")
    private boolean isOwner;

    public FriendsList(Boolean isSelected, int userId, String username, String fullName, boolean isOwner) {
        this.isSelected = isSelected;
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.isOwner = isOwner;
    }

    protected FriendsList(Parcel in) {
        this.isSelected = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.userId = in.readInt();
        this.username = in.readString();
        this.fullName = in.readString();
        this.isOwner = in.readByte() != 0;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.isSelected);
        dest.writeInt(this.userId);
        dest.writeString(this.username);
        dest.writeString(this.fullName);
        dest.writeByte(this.isOwner ? (byte) 1 : (byte) 0);
    }
}
