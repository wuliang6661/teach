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
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.base.MyApplication;
import cn.teach.equip.bean.event.SwitchEvent;
import cn.teach.equip.bean.pojo.UserBO;
import cn.teach.equip.mvp.MVPBaseFragment;
import cn.teach.equip.view.BigPicutreActivity;
import cn.teach.equip.view.ContactFragment;
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
    CardView myDownLoad;
    @BindView(R.id.my_shoucang)
    CardView myShoucang;
    Unbinder unbinder;
    @BindView(R.id.lianxi)
    CardView lianxi;

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
        Glide.with(getActivity()).load(MyApplication.userBO.getAvatarUrl())
                .error(R.drawable.person_defalt_img)
                .placeholder(R.drawable.person_defalt_img).into(userImg);
        userName.setText(MyApplication.userBO.getUserName());
        userId.setText("ID:" + MyApplication.userBO.getUserId());
        if (MyApplication.userBO.getUserType() == 2) {  //企业用户
            myDownLoad.setVisibility(View.GONE);
        }
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

    @OnClick(R.id.my_shoucang)
    public void clickShouCang() {
        EventBus.getDefault().post(new SwitchEvent(1));
    }

    @OnClick(R.id.user_img)
    public void clickUserImg() {
        if (StringUtils.isEmpty(MyApplication.userBO.getAvatarUrl())) {
            return;
        }
        ArrayList<String> images = new ArrayList<>();
        images.add(MyApplication.userBO.getAvatarUrl());
        Intent intent = new Intent(getActivity(), BigPicutreActivity.class);
        intent.putStringArrayListExtra("imageBos", images);
        startActivity(intent);
    }

    @OnClick(R.id.bt_logout)
    public void logout() {
        new AlertDialog(getActivity()).builder().setGone().setMsg("确认退出账号？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", v -> {
                    HttpServerImpl.logout().subscribe(new HttpResultSubscriber<String>() {
                        @Override
                        public void onSuccess(String s) {
                            JPushInterface.deleteAlias(getActivity(), 1);
                            JPushInterface.cleanTags(getActivity(), 1);
//                            Intent intent = new Intent();
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            intent.setClass(getActivity(), LoginActivity.class);
//                            AppManager.getAppManager().finishAllActivity();
//                            startActivity(intent);
                            MyApplication.token = null;
                            MyApplication.spUtils.remove("token");
                            EventBus.getDefault().post(new SwitchEvent(0));
                        }

                        @Override
                        public void onFiled(String message) {
                            showToast2(message);
                        }
                    });
                }).show();
    }

    @OnClick(R.id.lianxi)
    public void lianxi() {
        gotoActivity(ContactFragment.class, false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
