package uk.ac.tees.mad.w9501736.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BasicResponse implements Serializable {

    @SerializedName("result")

    private Boolean result;

    @SerializedName("msg")

    private String msg;


    @SerializedName("data")

    private Object data;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
