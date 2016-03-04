package com.mncomunity1.service;


import com.mncomunity1.model.Post;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiService {

    @GET("/community_service/select_train_list.php?user=chonlakant")
    void getTrain(Callback<Post> callback);

    @GET("/community_service/select_train.php?user=chonlakant&art=wh")
    void getLogistics(Callback<Post> callback);

    @GET("/community_service/select_train.php?user=chonlakant&art=qc")
    void getQuality(Callback<Post> callback);

    @GET("/community_service/select_train.php?user=chonlakant&art=sa")
    void getSafet(Callback<Post> callback);

    @GET("/community_service/select_train.php?user=chonlakant&art=pd")
    void getProduction(Callback<Post> callback);

    @GET("/community_service/select_train.php?user=chonlakant&art=mt")
    void getMainten(Callback<Post> callback);

    @GET("/community_service/select_train.php?user=chonlakant&art=ma")
    void getManagment(Callback<Post> callback);

    @GET("/community_service/select_train.php?user=chonlakant&art=is")
    void getIso(Callback<Post> callback);

    @GET("/community_service/select_train.php?user=chonlakant&art=pc")
    void getPurchase(Callback<Post> callback);

    @GET("/community_service/select_train.php?user=chonlakant&art=sm")
    void getSale(Callback<Post> callback);

    @GET("/community_service/select_art_list.php")
    void getArticles(@Query("user")String id,Callback<Post> callback);

    @GET("/community_service/select_new.php")
    void getNews(@Query("user")String id,Callback<Post> callback);

    @GET("/community_service/select_tip.php")
    void getTip(@Query("user")String id,Callback<Post> callback);

    @GET("/community_service/select_train_list.php")
    void getTraing(@Query("user")String id,Callback<Post> callback);

    @GET("/community_service/select_energy.php")
    void getEnnigy(@Query("user")String id,Callback<Post> callback);

    @GET("/community_service/select_success.php")
    void getSuccess(@Query("user")String id,Callback<Post> callback);


    @GET("/community_service/select_feed.php")
    void getFeed(Callback<Post> callback);

    @GET("/community_service/baner_feed.php")
    void getPhoto(Callback<Post> callback);



}
