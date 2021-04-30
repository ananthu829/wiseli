package uk.ac.tees.mad.w9501736.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @SerializedName("circle_id")
    private int circleId;
    @SerializedName("circle_name")
    private String name;
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
