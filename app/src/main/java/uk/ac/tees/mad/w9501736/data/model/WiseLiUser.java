package uk.ac.tees.mad.w9501736.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user")
public class WiseLiUser implements Parcelable {
    public static final Parcelable.Creator<WiseLiUser> CREATOR = new Parcelable.Creator<WiseLiUser>() {
        @Override
        public WiseLiUser createFromParcel(Parcel source) {
            return new WiseLiUser(source);
        }

        @Override
        public WiseLiUser[] newArray(int size) {
            return new WiseLiUser[size];
        }
    };
    @SerializedName("token")
    private String token;
    @PrimaryKey
    @NonNull
    @SerializedName("user_id")
    private int userId;
    @SerializedName("first_name")
    @ColumnInfo(name = "firstName")
    private String firstName;
    @SerializedName("last_name")
    @ColumnInfo(name = "lastName")
    private String lastName;
    @SerializedName("email")
    @ColumnInfo(name = "email")
    private String email;
    @SerializedName("username")
    @ColumnInfo(name = "username")
    private String username;
    @SerializedName("gender")
    @ColumnInfo(name = "gender")
    private String gender;
    @SerializedName("device_type")
    @ColumnInfo(name = "deviceType")
    private String deviceType;
    @SerializedName("device_id")
    @ColumnInfo(name = "deviceId")
    private String deviceId;
    @SerializedName("phone_number")
    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;
    @SerializedName("latitude")
    @ColumnInfo(name = "latitude")
    private String latitude;
    @SerializedName("longitude")
    @ColumnInfo(name = "longitude")
    private String longitude;
    @SerializedName("profile_pic")
    @ColumnInfo(name = "profilePic")
    private String profilePic;
    @SerializedName("password")
    @ColumnInfo(name = "password")
    private String password;

    public WiseLiUser(String token, int userId, String firstName, String lastName, String email,
                      String username, String gender, String deviceType, String deviceId, String phoneNumber,
                      String latitude, String longitude, String profilePic, String password) {
        this.token = token;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.deviceType = deviceType;
        this.deviceId = deviceId;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.profilePic = profilePic;
        this.password = password;
    }

    @Ignore
    public WiseLiUser() {
    }

    protected WiseLiUser(Parcel in) {
        this.token = in.readString();
        this.userId = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.username = in.readString();
        this.gender = in.readString();
        this.deviceType = in.readString();
        this.deviceId = in.readString();
        this.phoneNumber = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.profilePic = in.readString();
        this.password = in.readString();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeInt(this.userId);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
        dest.writeString(this.username);
        dest.writeString(this.gender);
        dest.writeString(this.deviceType);
        dest.writeString(this.deviceId);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.profilePic);
        dest.writeString(this.password);
    }

    @Override
    public String toString() {
        return "WiseLiUser{" +
                "token='" + token + '\'' +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
