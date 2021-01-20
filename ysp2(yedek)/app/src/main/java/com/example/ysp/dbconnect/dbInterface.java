package com.example.ysp.dbconnect;

import com.example.ysp.classes.ReturnCategory;
import com.example.ysp.classes.ReturnControl;
import com.example.ysp.classes.ReturnStory;
import com.example.ysp.classes.ReturnUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface  dbInterface {
   
    String webServerBase="controller.php";


    @POST(webServerBase)
    @FormUrlEncoded
    Call<ReturnUser> getUser(@Field("Method") String Method,@Field("userMail") String userMail,@Field("userPass") String userPass);

    @POST(webServerBase)
    @FormUrlEncoded
    Call<ReturnUser> getUser(@Field("Method") String Method,@Field("UserId") String UserId);


    @POST(webServerBase)
    @FormUrlEncoded
    Call<ReturnUser> getUsers(@Field("Method") String Method);

    @POST(webServerBase)
    @FormUrlEncoded
    Call<ReturnStory> getStories(@Field("Method") String Method);

    @POST(webServerBase)
    @FormUrlEncoded
    Call<ReturnStory> getStories(@Field("Method") String Method,@Field("userMail") String userMail);



    @POST(webServerBase)
    @FormUrlEncoded
    Call<ReturnControl> getCategoryCount(@Field("Method") String Method,@Field("CategoriesId") String CategoriesId);


    @POST(webServerBase)
    @FormUrlEncoded
    Call<ReturnCategory> getCategory(@Field("Method") String Method,@Field("CategoriesId") String CategoriesId);

    @POST(webServerBase)
    @FormUrlEncoded
    Call<ReturnCategory> getCategory(@Field("Method") String Method);


    @POST(webServerBase)
    @FormUrlEncoded
    Call<ReturnCategory> getCategory(@Field("Method") String Method,@Field("type") String type,@Field("userMail") String userMail,@Field("storiesID") String storiesID);

    @POST(webServerBase)
    @FormUrlEncoded
    Call<ReturnCategory> getCategory(@Field("Method") String Method,@Field("storiesID") String storiesID,@Field("Type") String Type);

    @POST(webServerBase)
    @FormUrlEncoded
    Call<ReturnControl> addCategory(@Field("Method") String Method,
                                    @Field("storiesId") String storiesId,
                                    @Field("categoryType") String categoryType,
                                    @Field("FinishDate") String FinishDate,
                                    @Field("categoryText") String categoryText,
                                    @Field("categoryTitle") String categoryTitle,
                                    @Field("categoryResUId") String categoryResUId);

    @POST(webServerBase)
    @FormUrlEncoded
    Call<ReturnControl> addStory(@Field("Method") String Method,
                                    @Field("storyCraUId") String storyCraUId,
                                    @Field("storyTitle") String storyTitle,
                                    @Field("storyText") String storyText,
                                    @Field("storyCraDate") String storyCraDate);

    @POST(webServerBase)
    @FormUrlEncoded
    Call<ReturnControl> changeCayegoryType(@Field("Method") String Method,
                                 @Field("location") String location,
                                 @Field("categoryId") String categoryId);
}
