package uk.ac.tees.mad.w9501736.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginModel implements Serializable {

    @SerializedName("result")

    private String result;

    @SerializedName("msg")

    private String msg;

    @SerializedName("data")

    private LoginModelDB data;

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

    public LoginModelDB getLoginDetails() {
        return data;
    }

    public void setLoginDetails(LoginModelDB email) {
        this.data = email;
    }

    @Entity
    public class LoginModelDB implements Serializable {
        @SerializedName("token")

        private String token;

        @SerializedName("user_id")
        @PrimaryKey
        @NonNull
        private String user_id;

        @SerializedName("first_name")

        private String first_name;

        @SerializedName("last_name")

        private String last_name;

        @SerializedName("email")

        private String email;

        @SerializedName("username")

        private String username;


        @SerializedName("gender")

        private String gender;

        @SerializedName("phone_number")

        private String phone_number;


        @SerializedName("profile_pic")

        private String profile_pic;


        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
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

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }
    }
}
