package ismart.is.com.ismart.service;


import java.util.Map;

import ismart.is.com.ismart.model.Post;
import ismart.is.com.ismart.model.PostDetail;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public interface ApiService {

    @GET("/mn_com/service/course_list.php?cat=WH")
    void getLogistics(Callback<Post> callback);

    @GET("/mn_com/service/course_list.php?cat=QC")
    void getQuality(Callback<Post> callback);

    @GET("/mn_com/service/course_list.php?cat=SA")
    void getSafet(Callback<Post> callback);

    @GET("/mn_com/service/course_list.php?cat=PD")
    void getProduction(Callback<Post> callback);

    @GET("/mn_com/service/course_list.php?cat=MA")
    void getMainten(Callback<Post> callback);

    @GET("/mn_com/service/course_list.php?cat=MA")
    void getManagment(Callback<Post> callback);

    @GET("/mn_com/service/course_list.php?cat=IS")
    void getIso(Callback<Post> callback);

    @GET("/mn_com/service/course_list.php?cat=PC")
    void getPurchase(Callback<Post> callback);

    @GET("/mn_com/service/course_list.php?cat=mk")
    void getSale(Callback<Post> callback);

    @GET("/mn_com/community_service/select_art_list.php")
    void getArticles(@Query("user")String id,Callback<Post> callback);

    @GET("/mn_com/community_service/select_new.php")
    void getNews(@Query("user")String id,Callback<Post> callback);

    @GET("/mn_com/community_service/select_tip.php")
    void getTip(@Query("user")String id,Callback<Post> callback);

    @GET("/mn_com/community_service/select_train_list.php")
    void getTraing(@Query("user")String id,Callback<Post> callback);

    @GET("/mn_com/community_service/select_energy.php")
    void getEnnigy(@Query("user")String id,Callback<Post> callback);

    @GET("/mn_com/community_service/select_success.php")
    void getSuccess(@Query("user")String id,Callback<Post> callback);




}
