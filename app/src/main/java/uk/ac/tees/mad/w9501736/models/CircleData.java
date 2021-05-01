package uk.ac.tees.mad.w9501736.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class CircleData implements Parcelable {
    public static final Parcelable.Creator<CircleData> CREATOR = new Parcelable.Creator<CircleData>() {
        @Override
        public CircleData createFromParcel(Parcel source) {
            return new CircleData(source);
        }

        @Override
        public CircleData[] newArray(int size) {
            return new CircleData[size];
        }
    };
    @PrimaryKey
    @SerializedName("circle_id")
    private int circleId;
    @SerializedName("circle_name")
    private String name;
    @Ignore
    @SerializedName("friends_list")
    private List<FriendsList> friendsList;

    public CircleData(int circleId, String name, List<FriendsList> friendsList) {
        this.circleId = circleId;
        this.name = name;
        this.friendsList = friendsList;
    }

    protected CircleData(Parcel in) {
        this.circleId = in.readInt();
        this.name = in.readString();
        this.friendsList = in.createTypedArrayList(FriendsList.CREATOR);
    }

    public static Creator<CircleData> getCREATOR() {
        return CREATOR;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFriendsList(List<FriendsList> friendsList) {
        this.friendsList = friendsList;
    }

    public CircleData() {
    }

    public int getCircleId() {
        return circleId;
    }

    public String getName() {
        return name;
    }

    public List<FriendsList> getFriendsList() {
        return friendsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.circleId);
        dest.writeString(this.name);
        dest.writeTypedList(this.friendsList);
    }
}
