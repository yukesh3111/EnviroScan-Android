package com.example.enviroscan.api;

import com.example.enviroscan.serverresponse.Articledetails;
import com.example.enviroscan.serverresponse.CommonResponse;
import com.example.enviroscan.serverresponse.GreenSpace;
import com.example.enviroscan.serverresponse.TreeCounting;
import com.example.enviroscan.serverresponse.UserData;
import com.example.enviroscan.serverresponse.Userdetails;
import com.example.enviroscan.serverresponse.ViewArticleModel;
import com.example.enviroscan.serverresponse.comparewithexisting;
import com.example.enviroscan.serverresponse.speciesidentification;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Interface {

    @GET("/apihome")
    Call<UserData> getUserData();

    @FormUrlEncoded
    @POST("/apilogin")
    Call<CommonResponse> login(@Field("login_username") String login_username, @Field("login_password")String login_password);

    @FormUrlEncoded
    @POST("/apisignup")
    Call<CommonResponse> signup(@Field("username") String username, @Field("name") String name, @Field("surename") String surename, @Field("phone_no") String phone_no,@Field("email") String email, @Field("password") String password,@Field("address") String address,@Field("city") String city,@Field("state") String state);

    @GET("/apiarticle/{uid}")
    Call<Articledetails> getarticle(@Path("uid") String uid);

    @GET("/apiuserprofile/{user}")
    Call<Userdetails> getUserDetails(@Path("user") String user);

    @GET("/apiviewallarticle")
    Call<ViewArticleModel> getviewarticle();

    @FormUrlEncoded
    @POST("/apiuseredirprofile/{user}")
    Call<CommonResponse> editProfile(@Path("user") String user, @Field("name") String name, @Field("surename") String surename, @Field("phone_no") String phone_no,@Field("email") String email, @Field("address") String address,@Field("city") String city,@Field("state") String state);

    @GET("/apilogout")
    Call<CommonResponse> logout();

    @GET("/api/greet")
    Call<CommonResponse> checking();

    @Multipart
    @POST("/apitree_counting") // Replace with your actual endpoint
    Call<TreeCounting> uploadImage(
            @Part MultipartBody.Part image,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude
    );

    @Multipart
    @POST("/apigreenlandspace") // Replace with your actual endpoint
    Call<GreenSpace> greenuploadImage(@Part MultipartBody.Part image);

    @Multipart
    @POST("/apitree_species") // Replace with your actual endpoint
    Call<speciesidentification> speciesuploadImage(@Part MultipartBody.Part image);

    @Multipart
    @POST("/apicomparewithexisting") // Replace with your actual endpoint
    Call<comparewithexisting> compareuploadImage(@Part MultipartBody.Part image, @Part("latitude") RequestBody latitude, @Part("longitude") RequestBody longitude);


}
