package uk.ac.tees.mad.w9501736.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CircleModel implements Serializable {

    @SerializedName("result")

    private String result;

    @SerializedName("msg")

    private String msg;

    @SerializedName("data")

    private CircleData data;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CircleData getData() {
        return data;
    }

    public void setData(CircleData data) {
        this.data = data;
    }

    @Entity
    public class CircleData implements Serializable {
        @SerializedName("circle_name")

        private String circle_name;

        @SerializedName("circle_id")

        private String circle_id;


    }
}
