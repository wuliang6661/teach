package cn.teach.equip.view.navigation;

import android.content.Context;

import cn.teach.equip.mvp.BasePresenter;
import cn.teach.equip.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NavigationContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
