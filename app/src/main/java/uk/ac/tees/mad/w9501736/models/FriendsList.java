package uk.ac.tees.mad.w9501736.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
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
    @PrimaryKey
    @NonNull
    private int circleId;
    private boolean isActive;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("username")
    private String username;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("is_owner")
    private boolean isOwner;

    @Ignore
    public FriendsList() {
    }

    public FriendsList(int circleId, boolean isActive, int userId, String username, String fullName, boolean isOwner) {
        this.circleId = circleId;
        this.isActive = isActive;
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.isOwner = isOwner;
    }

    protected FriendsList(Parcel in) {
        this.isActive = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.userId = in.readInt();
        this.circleId = in.readInt();
        this.username = in.readString();
        this.fullName = in.readString();
        this.isOwner = in.readByte() != 0;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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
        dest.writeValue(this.isActive);
        dest.writeInt(this.circleId);
        dest.writeInt(this.userId);
        dest.writeString(this.username);
        dest.writeString(this.fullName);
        dest.writeByte(this.isOwner ? (byte) 1 : (byte) 0);
    }

}
