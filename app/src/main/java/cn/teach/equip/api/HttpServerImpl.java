package cn.teach.equip.api;

import java.util.HashMap;
import java.util.Map;

import cn.teach.equip.api.rx.RxBus;
import cn.teach.equip.api.rx.RxResultHelper;
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
    public static Observable<String> register(String phone, String smsCode, String provinceId, String cityId, String userName,
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
    public static Observable<String> login(String phone, String smsCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("smsCode", smsCode);
        return getService().login(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 发送短信验证码
     */
    public static Observable<String> sendSmsCode(String phone) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        return getService().sendSmsCode(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 退出登录
     */
    public static Observable<String> logout(){
        return getService().logout().compose(RxResultHelper.httpRusult());
    }




}
