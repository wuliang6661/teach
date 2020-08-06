package cn.teach.equip.api;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.teach.equip.api.rx.RxResultHelper;
import cn.teach.equip.bean.pojo.BannerBO;
import cn.teach.equip.bean.pojo.ChanPinBO;
import cn.teach.equip.bean.pojo.FenLeiBO;
import cn.teach.equip.bean.pojo.FlagBO;
import cn.teach.equip.bean.pojo.MuluListBO;
import cn.teach.equip.bean.pojo.ProvinceBO;
import cn.teach.equip.bean.pojo.UnitBO;
import cn.teach.equip.bean.pojo.UserBO;
import cn.teach.equip.bean.pojo.VideoListBO;
import cn.teach.equip.bean.pojo.WenZhangListBo;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

public class HttpServerImpl {

    private static HttpService service;

    private static HttpService downLoadService;

    /**
     * 获取代理对象
     *
     * @return
     */
    public static HttpService getService() {
        if (service == null)
            service = ApiManager.getInstance().configRetrofit(HttpService.class, HttpService.URL);
        return service;
    }

    /**
     * 获取代理对象
     *
     * @return
     */
    public static HttpService getDownLoadService(DownloadResponseBody.DownloadListener listener) {
        downLoadService = ApiManager.getInstance().downloadConfigRetrofit(HttpService.class, HttpService.URL, listener);
        return downLoadService;

    }


    /**
     * 注册
     */
    public static Observable<UserBO> register(String phone, String smsCode, String provinceId, String cityId, String userName,
                                              String unitName, String unitId, String userType) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("smsCode", smsCode);
        params.put("provinceId", provinceId);
        params.put("cityId", cityId);
        params.put("userName", userName);
        params.put("unitName", unitName);
        params.put("unitId", unitId);
        params.put("userType", userType);
        return getService().register(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 登录
     */
    public static Observable<UserBO> login(String phone, String smsCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("smsCode", smsCode);
        return getService().login(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 发送短信验证码
     */
    public static Observable<String> sendSmsCode(String phone, int type) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("type", type);
        return getService().sendSmsCode(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 退出登录
     */
    public static Observable<String> logout() {
        return getService().logout().compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取省市区
     */
    public static Observable<List<ProvinceBO>> getLocationList(String provinceId, String cityId, String provinceFirstLetter,
                                                               String cityFirstLetter, String provinceName, String cityName) {
        Map<String, Object> params = new HashMap<>();
        params.put("cityId", cityId);
        params.put("cityFirstLetter", cityFirstLetter);
        params.put("cityName", cityName);
        return getService().getLocationList(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取首页banner
     */
    public static Observable<List<BannerBO>> getBannerList() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", 1);
        return getService().getBannerList(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取用户信息
     */
    public static Observable<UserBO> getUserInfo() {
        return getService().getUserInfo().compose(RxResultHelper.httpRusult());
    }

    /**
     * 反馈
     */
    public static Observable<String> feedback(String fankui) {
        Map<String, Object> params = new HashMap<>();
        params.put("content", fankui);
        return getService().feedback(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 保存用户信息
     */
    public static Observable<UserBO> saveUserInfo(String userName, File file) {
        MultipartBody.Part body = MultipartBody.Part.createFormData("", "");
        if (file != null) {
            File compressedImageFile;
            try {
                compressedImageFile = new Compressor(Utils.getApp()).setQuality(30).compressToFile(file);
            } catch (IOException e) {
                e.printStackTrace();
                compressedImageFile = file;
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), compressedImageFile);
            body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        }
        return getService().saveUserInfo(userName, body).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取文章列表
     */
    public static Observable<WenZhangListBo> getArticleList(int type, int pageNum) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("page", pageNum);
        params.put("size", 20);
        return getService().getArticleList(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取发现视频列表
     */
    public static Observable<VideoListBO> getVideoList(int page) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("size", 20);
        return getService().getVideoList(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取产品分类
     */
    public static Observable<List<FenLeiBO>> getProductList(int levelType, int page) {
        Map<String, Object> params = new HashMap<>();
        params.put("levelType", levelType);
        params.put("page", page);
        params.put("size", 100);
        return getService().getProductList(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取主题教室分类
     */
    public static Observable<List<FenLeiBO>> getProductList(int levelType, int levelId2, int page) {
        Map<String, Object> params = new HashMap<>();
        params.put("levelType", levelType);
        params.put("page", page);
        params.put("levelId2", levelId2);
        params.put("size", 10);
        return getService().getProductList(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取收藏产品列表
     */
    public static Observable<ChanPinBO> getProductCollectList(int levelId3, int page) {
        Map<String, Object> params = new HashMap<>();
        params.put("levelId3", levelId3);
        params.put("page", page);
        params.put("size", 100);
        return getService().getProductCollectList(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取产品列表
     */
    public static Observable<ChanPinBO> getProductInfoList(int levelId3, int page) {
        Map<String, Object> params = new HashMap<>();
        params.put("levelId3", levelId3);
        params.put("page", page);
        params.put("size", 100);
        return getService().getProductInfoList(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取下载目录
     */
    public static Observable<MuluListBO> getDownloadFileList(int type, int pageNum, String searchContent) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("page", pageNum);
        params.put("size", 20);
        params.put("searchContent", searchContent);
        return getService().getDownloadFileList(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取推荐列表
     */
    public static Observable<List<FlagBO>> getNavigationHotList() {
        return getService().getNavigationHotList().compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取单位
     */
    public static Observable<List<UnitBO>> getUnitList(String unitName, String provinceId, String cityId) {
        Map<String, Object> params = new HashMap<>();
        params.put("unitName", unitName);
        if (StringUtils.isEmpty(unitName)) {
            params.put("provinceId", provinceId);
            params.put("cityId", cityId);
        }
        return getService().getUnitList(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 全文搜索
     */
    public static Observable<WenZhangListBo> search(int type, int isCollect, String searchContent) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("isCollect", isCollect);
        params.put("searchContent", searchContent);
        params.put("page", 1);
        params.put("size", 100);
        return getService().search(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取文章是否更新
     */
    public static Observable<String> getArticleListInfo() {
        return getService().getArticleListInfo().compose(RxResultHelper.httpRusult());
    }


    /**
     * 设置视频是否点赞
     */
    public static Observable<String> videoUp(String code, int type) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("code", code);
        return getService().videoUp(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 取消收藏产品
     */
    public static Observable<String> productCollect(String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", 0);
        params.put("codes", code);
        return getService().productCollect(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 检查更新
     */
    public static Observable<String> getVersionInfo() {
        return getService().getVersionInfo().compose(RxResultHelper.httpRusult());
    }


    /**
     * 下载
     *
     * @param url              下载地址，全路径
     * @param downloadListener 进度监听
     * @param file             保存地址文件
     * @return
     */
    public static Observable<ResponseBody> downLoad(String url, DownloadResponseBody.DownloadListener
            downloadListener, File file) {
        //url = "http://172.18.100.26:8080/manager/images/kkk.7z";
        return getDownLoadService(downloadListener).download(url).compose(RxResultHelper.downRequest(file));
    }
}
