package cn.teach.equip.view.register;


import cn.teach.equip.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 注册界面
 */

public class RegisterActivity extends MVPBaseActivity<RegisterContract.View, RegisterPresenter>
        implements RegisterContract.View {

    @Override
    protected int getLayout() {
        return 0;
    }
}
