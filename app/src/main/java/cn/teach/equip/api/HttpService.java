package cn.teach.equip.api;

import java.util.List;
import java.util.Map;

import cn.teach.equip.bean.BaseResult;
import cn.teach.equip.bean.pojo.BannerBO;
import cn.teach.equip.bean.pojo.ProvinceBO;
import cn.teach.equip.bean.pojo.UserBO;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wuliang on 2017/3/9.
 * <p>
 * 此处存放后台服务器的所有接口数据
 */

public interface HttpService {

    String URL = "http://shjz.yingjin.pro/";


    /**
     * 注册接口
     */
    @POST("api/visitor/register")
    Observable<BaseResult<UserBO>> register(@Body Map<String, Object> params);


    /**
     * 登录
     */
    @POST("api/visitor/login")
    Observable<BaseResult<UserBO>> login(@Body Map<String, Object> params);

    /**
     * 发送短信验证码
     */
    @POST("api/visitor/sendSmsCode")
    Observable<BaseResult<String>> sendSmsCode(@Body Map<String, Object> params);

    /**
     * 退出登录
     */
    @POST("api/user/logout")
    Observable<BaseResult<String>> logout();


    /**
     * 获取用户信息
     */
    @POST("api/user/getUserInfo")
    Observable<BaseResult<UserBO>> getUserInfo();

    /**
     * 保存用户信息
     */
    @Multipart
    @POST("api/user/saveUserInfo")
    Observable<BaseResult<UserBO>> saveUserInfo(@Query("userName") String userName,
                                                @Part MultipartBody.Part file);

    /**
     * 检查更新
     */
    @POST("api/vistor/getVersionInfo")
    Observable<BaseResult<String>> getVersionInfo();

    /**
     * 意见反馈
     */
    @POST("api/user/feedback")
    Observable<BaseResult<String>> feedback(@Body Map<String, Object> params);

    /**
     * 获取省市区
     */
    @POST("api/visitor/getLocationList")
    Observable<BaseResult<List<ProvinceBO>>> getLocationList(@Body Map<String, Object> params);

    /**
     * 获取单位
     */
    @POST("api/visitor/getUnitList")
    Observable<BaseResult<String>> getUnitList();

    /**
     * 获取banner
     */
    @POST("api/visitor/getBannerList")
    Observable<BaseResult<List<BannerBO>>> getBannerList(@Body Map<String, Object> params);

    /**
     * 获取文章列表
     */
    @POST("api/visitor/getArticleList")
    Observable<BaseResult<String>> getArticleList(@Body Map<String, Object> params);

    /**
     * 获取我的文章收藏列表
     */
    @POST("api/article/getArticleCollectList")
    Observable<BaseResult<String>> getArticleCollectList(@Body Map<String, Object> params);

    /**
     * 获取分类产品列表
     */
    @POST("api/visitor/getProductList")
    Observable<BaseResult<String>> getProductList(@Body Map<String, Object> params);

    /**
     * 获取我的产品收藏列表
     */
    @POST("api/product/getProductCollectList")
    Observable<BaseResult<String>> getProductCollectList(@Body Map<String, Object> params);

    /**
     * 获取发现视频列表
     */
    @POST("api/visitor/getVideoList")
    Observable<BaseResult<String>> getVideoList(@Body Map<String, Object> params);


    /**
     * 视频点赞/取消点赞
     */
    @POST("api/video/videoUp")
    Observable<BaseResult<String>> videoUp(@Body Map<String, Object> params);

    /**
     * 获取下载目录
     */
    @POST("api/visitor/getDownloadFileList")
    Observable<BaseResult<String>> getDownloadFileList(@Body Map<String, Object> params);

    /**
     * 全文搜索
     */
    @POST("api/visitor/search")
    Observable<BaseResult<String>> search(@Body Map<String, Object> params);

    /**
     * 收藏文章产品/取消收藏文章产品
     */
    @POST("api/product/productCollect")
    Observable<BaseResult<String>> productCollect(@Body Map<String, Object> params);
}
