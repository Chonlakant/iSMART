package ismart.is.com.ismart.service;


import ismart.is.com.ismart.model.Post;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ApiService {

    @GET("/mn_com/service/course_list.php?cat=WH")
    void getLogistics(Callback<Post> callback);

    @GET("/mn_com/service/course_list.php?cat=QC")
    void getQuality(Callback<Post> callback);

    @GET("/mn_com/service/course_list.php?cat=SA")
    void getSafet(Callback<Post> callback);

    @GET("/mn_com/service/course_list.php?cat=PD")
    void getProduction(Callback<Post> callback);

    @GET("/mn_com/service/course_list.php?cat=MT")
    void getMainten(Callback<Post> callback);

    @GET("/mn_com/service/course_list.php?cat=MA")
    void getManagment(Callback<Post> callback);

    @GET("/mn_com/service/course_list.php?cat=IS")
    void getIso(Callback<Post> callback);

    @GET("/mn_com/service/course_list.php?cat=PC")
    void getPurchase(Callback<Post> callback);

    @GET("/mn_com/service/course_list.php?cat=SM")
    void getSale(Callback<Post> callback);

}
