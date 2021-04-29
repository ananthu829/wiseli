package uk.ac.tees.mad.w9501736.models;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreateModel implements Serializable {

    @SerializedName("token")

    private String token;

    @SerializedName("circle_name")

    private String circle_name;


    @SerializedName("latitude")

    private String latitude;


    @SerializedName("longitude")

    private String longitude;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCircle_name() {
        return circle_name;
    }

    public void setCircle_name(String circle_name) {
        this.circle_name = circle_name;
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
}
