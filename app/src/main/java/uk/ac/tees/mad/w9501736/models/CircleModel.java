package uk.ac.tees.mad.w9501736.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CircleModel implements Serializable {

    @SerializedName("result")

    private String result;

    @SerializedName("msg")

    private String msg;

    @SerializedName("data")

    private List<CircleData> data;


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

    public List<CircleData> getData() {
        return data;
    }

    public void setData(List<CircleData> data) {
        this.data = data;
    }

    public class CircleData implements Serializable {
        @SerializedName("circle_name")

        private String circle_name;

        @SerializedName("circle_id")

        private String circle_id;

        public String getCircle_name() {
            return circle_name;
        }

        public void setCircle_name(String circle_name) {
            this.circle_name = circle_name;
        }

        public String getCircle_id() {
            return circle_id;
        }

        public void setCircle_id(String circle_id) {
            this.circle_id = circle_id;
        }
    }
}
