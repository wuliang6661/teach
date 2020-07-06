package cn.teach.equip.view.login;

import android.content.Context;

import cn.teach.equip.bean.pojo.UserBO;
import cn.teach.equip.mvp.BasePresenter;
import cn.teach.equip.mvp.BaseRequestView;
import cn.teach.equip.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginContract {
    interface View extends BaseRequestView {

        void loginSourcess(UserBO userBO);
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
