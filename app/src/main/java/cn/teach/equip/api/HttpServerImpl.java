package cn.teach.equip.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.teach.equip.api.rx.RxResultHelper;
import cn.teach.equip.bean.pojo.BannerBO;
import cn.teach.equip.bean.pojo.ProvinceBO;
import cn.teach.equip.bean.pojo.UserBO;
import rx.Observable;

public class HttpServerImpl {

    private static HttpService service;

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
        params.put("provinceId", provinceId);
        params.put("cityId", cityId);
        params.put("provinceFirstLetter", provinceFirstLetter);
        params.put("cityFirstLetter", cityFirstLetter);
        params.put("provinceName", provinceName);
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
    public static Observable<UserBO> getUserInfo(){
        return getService().getUserInfo().compose(RxResultHelper.httpRusult());
    }
}
