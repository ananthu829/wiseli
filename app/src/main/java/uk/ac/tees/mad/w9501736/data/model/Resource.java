package uk.ac.tees.mad.w9501736.data.model;

import androidx.annotation.Nullable;

public class Resource<T> {

    @Nullable
    public Boolean result;

    @Nullable
    public String message;

    @Nullable
    public T data;

    public Resource(@Nullable Boolean result, @Nullable String message, @Nullable T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    @Nullable
    public Boolean getResult() {
        return result;
    }

    public void setResult(@Nullable Boolean result) {
        this.result = result;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public void setMessage(@Nullable String message) {
        this.message = message;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public void setData(@Nullable T data) {
        this.data = data;
    }
}
