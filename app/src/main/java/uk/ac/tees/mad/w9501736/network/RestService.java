package com.project.certificate.RestService;



import com.project.certificate.model.User;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;


/**
 * Created by nithin on 20/9/20
 **/
public interface RestService {


    @GET("/join/id")
    Call<User> getCust(@Query("id") String search);




}
