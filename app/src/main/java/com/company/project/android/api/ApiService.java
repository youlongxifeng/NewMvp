package com.company.project.android.api;

import com.company.project.android.bean.DataResponse;
import com.company.project.android.bean.Gank;
import com.company.project.android.bean.User;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author Administrator
 * @name MyMvp
 * @class name：com.company.project.android.api
 * @class describe
 * @time 2018/2/8 17:14
 * @change
 * @class describe
 */

public interface ApiService {


    //http://gank.io/api/data/Android/10/1
    @GET("api/data/Android/10/{page}")
    Observable<Gank> getGank(@Path("page") String page);

    @GET("v1/ads/list?")
    Observable<DataResponse> getConfigInfoApi(@Query("device_id") String device_id,
                                              @Query("version") String version,
                                              @Query("ads_support") String ads_support,
                                              @Query("timetoken") long timetoken);

    @Multipart
    @PUT("v2/book/1220562")
    Observable<String> postBookString(@Part MultipartBody.Part photo,
                                      @Part("username") RequestBody username,
                                      @Part("password") RequestBody password );


    @FormUrlEncoded//
    @POST("/v1/token")
    Observable<JSONObject>accessToken(@Body RequestBody requestBody);

    /**
     * 上传单个文件
     * @param file
     * @param map
     * @return
     */
    @Multipart
    @POST("casuserroleapi/editUserInfo")
    Observable<User> uploadFile(@Part MultipartBody.Part file, @PartMap Map<String,Object> map);


    /**
     * 上传多个文件
     * @param maps
     * @param map
     * @return
     */
    @Multipart
    @POST("casuserroleapi/editUserInfo")
    Observable<User> uploadFilesWithParts(@PartMap() List<MultipartBody.Part> maps, @PartMap Map<String,Object> map);

    /**
     * 断点续传下载
     *
     * @param range 断点下载范围 bytes= start - end
     * @param url   下载地址
     * @return
     * @Streaming 防止内容写入内存, 大文件通过此注解避免OOM
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String range, @Url String url);

    /**
     * 下载
     * @param url   下载地址
     * @return
     * @Streaming 防止内容写入内存, 大文件通过此注解避免OOM
     */
    @Streaming
    @GET
    Observable<ResponseBody> download( @Url String url);
}

