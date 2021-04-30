package uk.ac.tees.mad.w9501736.models;

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
}
