package com.leifeng.lib.net;


import com.leifeng.lib.UserBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 描述:API
 *
 * @author leifeng
 *         2018/3/21 16:53
 */
public interface BaseAPI {
    /**
     * 方法注解，包含@GET、@POST、@PUT、@DELETE、@PATH、@HEAD、@OPTIONS、@HTTP。
     * 标记注解，包含@FormUrlEncoded、@Multipart、@Streaming。
     * 参数注解，包含@Query,@QueryMap、@Body、@Field，@FieldMap、@Part，@PartMap。
     * 其他注解，@Path、@Header,@Headers、@Url
     */
    // 动态的url访问@PATH
    //@Path：URL占位符，用于替换和动态更新,相应的参数必须使用相同的字符串被@Path进行注释
    @GET("group/{id}/users")
    Call<List<UserBean>> groupList(@Path("id") int groupId);

    //@Query,@QueryMap:查询参数，用于GET和Post查询,需要注意的是@QueryMap可以约定是否需要encode
    @GET("group/{id}/users")
    Call<List<UserBean>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @GET("group/{id}/users")
    Call<List<UserBean>> groupList(@Path("id") int groupId, @QueryMap Map<String, String> options);

    // 表单的方式传递键值对@FormUrlEncoded
    // @Field，@FieldMap:Post方式传递简单的键值对,需要添加@FormUrlEncoded表示表单提交Content-Type:application/x-www-form-urlencoded
    // 当函数有@FormUrlEncoded注解的时候，将会发送form-encoded数据，每个键-值对都要被含有名字的@Field注解和提供值的对象所标注
    @FormUrlEncoded
    @POST("user/edit")
    Call<UserBean> updateUser(@Field("first_name") String first, @Field("last_name") String last);

    // @Part，@PartMap：用于POST文件上传 其中@Part MultipartBody.Part代表文件，@Part("key") RequestBody代表参数 需要添加@Multipart表示支持文件上传的表单，Content-Type: multipart/form-data
    // 当函数有@Multipart注解的时候，将会发送multipart数据，Parts都使用@Part注解进行声明
    // Multipart parts要使用Retrofit的众多转换器之一或者实现RequestBody来处理自己的序列化
    @Multipart
    @PUT("user/photo")
    Call<UserBean> updateUser(@Part("photo") RequestBody photo, @Part("description") RequestBody description);

    @POST("getStoreUser")
    Observable<UserBean> getUser(@Query("store_id") String store_id);

}
