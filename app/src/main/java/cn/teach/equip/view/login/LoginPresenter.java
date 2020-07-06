package cn.teach.equip.view.login;

import android.content.Context;

import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.bean.pojo.UserBO;
import cn.teach.equip.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {


    public void login(String phone, String smsCode) {
        HttpServerImpl.login(phone, smsCode).subscribe(new HttpResultSubscriber<UserBO>() {
            @Override
            public void onSuccess(UserBO s) {
                if (mView != null) {
                    mView.loginSourcess(s);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }

}
