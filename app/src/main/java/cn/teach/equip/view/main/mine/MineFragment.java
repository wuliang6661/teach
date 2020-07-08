package cn.teach.equip.view.main.mine;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.base.MyApplication;
import cn.teach.equip.bean.pojo.UserBO;
import cn.teach.equip.mvp.MVPBaseFragment;
import cn.teach.equip.util.AppManager;
import cn.teach.equip.view.login.LoginActivity;
import cn.teach.equip.view.mulu.MuluActivity;
import cn.teach.equip.view.personmessage.PersonMessageActivity;
import cn.teach.equip.view.setting.SettingActivity;
import cn.teach.equip.weight.AlertDialog;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MineFragment extends MVPBaseFragment<MineContract.View, MinePresenter> implements MineContract.View {


    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_id)
    TextView userId;
    @BindView(R.id.setting_img)
    ImageView settingImg;
    @BindView(R.id.user_msg)
    CardView userMsg;
    @BindView(R.id.my_down_load)
    RelativeLayout myDownLoad;
    @BindView(R.id.my_shoucang)
    CardView myShoucang;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_mine, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        getUserInfo();
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        HttpServerImpl.getUserInfo().subscribe(new HttpResultSubscriber<UserBO>() {
            @Override
            public void onSuccess(UserBO userBO) {
                MyApplication.userBO = userBO;
                showUI();
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }


    /**
     * 设置用户信息显示
     */
    private void showUI() {
        Glide.with(getActivity()).load(MyApplication.userBO.getAvatarUrl()).into(userImg);
        userName.setText(MyApplication.userBO.getUserName());
        userId.setText("ID:" + MyApplication.userBO.getUserId());
    }


    @OnClick(R.id.my_down_load)
    public void muLu() {
        gotoActivity(MuluActivity.class, false);
    }


    @OnClick(R.id.user_msg)
    public void goUserMsg() {
        gotoActivity(PersonMessageActivity.class, false);
    }

    @OnClick(R.id.setting_img)
    public void clickSetting() {
        gotoActivity(SettingActivity.class, false);
    }

    @OnClick(R.id.bt_logout)
    public void logout() {
        new AlertDialog(getActivity()).builder().setGone().setMsg("确认退出账号？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", v -> {
                    HttpServerImpl.logout().subscribe(new HttpResultSubscriber<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Intent intent = new Intent();
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setClass(getActivity(), LoginActivity.class);
                            AppManager.getAppManager().finishAllActivity();
                            startActivity(intent);
                        }

                        @Override
                        public void onFiled(String message) {
                            showToast2(message);
                        }
                    });
                }).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
