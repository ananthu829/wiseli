package uk.ac.tees.mad.w9501736.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UserFriendsList implements Parcelable {
    public static final Parcelable.Creator<UserFriendsList> CREATOR = new Parcelable.Creator<UserFriendsList>() {
        @Override
        public UserFriendsList createFromParcel(Parcel source) {
            return new UserFriendsList(source);
        }

        @Override
        public UserFriendsList[] newArray(int size) {
            return new UserFriendsList[size];
        }
    };
    @SerializedName("user_id")
    private int userId;
    @SerializedName("username")
    private String username;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("profile_pic")
    private String profilePic;
    private boolean isSelected;

    public UserFriendsList(int userId, String username, String fullName, String profilePic, boolean isSelected) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.profilePic = profilePic;
        this.isSelected = isSelected;
    }

    protected UserFriendsList(Parcel in) {
        this.userId = in.readInt();
        this.username = in.readString();
        this.fullName = in.readString();
        this.profilePic = in.readString();
        this.isSelected = in.readByte() != 0;
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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.username);
        dest.writeString(this.fullName);
        dest.writeString(this.profilePic);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }
}
