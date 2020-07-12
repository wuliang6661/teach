package cn.teach.equip.view.navigation;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.bean.pojo.FlagBO;
import cn.teach.equip.mvp.MVPBaseActivity;
import cn.teach.equip.view.WebActivity;
import cn.teach.equip.view.mulu.MuluActivity;
import cn.teach.equip.view.personmessage.PersonMessageActivity;
import cn.teach.equip.view.setting.SettingActivity;


/**
 * MVPPlugin
 * 综合导航
 */

public class NavigationActivity extends MVPBaseActivity<NavigationContract.View, NavigationPresenter>
        implements NavigationContract.View {

    @BindView(R.id.chanpin_leibie)
    TagFlowLayout chanpinLeibie;
    @BindView(R.id.hexin_tag)
    TagFlowLayout hexinTag;
    @BindView(R.id.qita_tag)
    TagFlowLayout qitaTag;
    @BindView(R.id.tuijian_tag)
    TagFlowLayout tuijianTag;

    @Override
    protected int getLayout() {
        return R.layout.act_navigation;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("综合导航");
    }

    @Override
    protected void onResume() {
        super.onResume();
        setChanPinAdapter();
        setHexinAdapter();
        setQitaAdapter();
        getTuijian();
    }

    /**
     * 产品导航
     */
    private void setChanPinAdapter() {
        List<String> chanpings = new ArrayList<>();
        chanpings.add("教学仪器");
        chanpings.add("信息化设备");
        chanpings.add("玩教具");
        chanpings.add("配套材料");
        TagAdapter<String> adapter = new TagAdapter<String>(chanpings) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                LayoutInflater inflater = LayoutInflater.from(NavigationActivity.this);
                TextView tv = (TextView) inflater.inflate(R.layout.item_flag, parent, false);
                tv.setText(s);
                return tv;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                TextView tv = (TextView) view;
                tv.setBackgroundResource(R.drawable.daohang_select_flag);
                tv.setTextColor(Color.parseColor("#F69223"));
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                TextView tv = (TextView) view;
                tv.setBackgroundResource(R.drawable.daohang_flag);
                tv.setTextColor(Color.parseColor("#4D4D4D"));
            }
        };
        chanpinLeibie.setAdapter(adapter);
    }


    /**
     * 核心功能
     */
    private void setHexinAdapter() {
        List<String> chanpings = new ArrayList<>();
        chanpings.add("目录下载");
        chanpings.add("我的收藏");
        chanpings.add("发现");
        chanpings.add("扫描");
        TagAdapter<String> adapter = new TagAdapter<String>(chanpings) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                LayoutInflater inflater = LayoutInflater.from(NavigationActivity.this);
                TextView tv = (TextView) inflater.inflate(R.layout.item_flag, parent, false);
                tv.setText(s);
                return tv;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                TextView tv = (TextView) view;
                tv.setBackgroundResource(R.drawable.daohang_select_flag);
                tv.setTextColor(Color.parseColor("#F69223"));
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                TextView tv = (TextView) view;
                tv.setBackgroundResource(R.drawable.daohang_flag);
                tv.setTextColor(Color.parseColor("#4D4D4D"));
            }
        };
        hexinTag.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                switch (position) {
                    case 0:
                        gotoActivity(MuluActivity.class, false);
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                }
                return false;
            }
        });
        hexinTag.setAdapter(adapter);
    }


    /**
     * 产品导航
     */
    private void setQitaAdapter() {
        List<String> chanpings = new ArrayList<>();
        chanpings.add("个人信息");
        chanpings.add("设置");
        TagAdapter<String> adapter = new TagAdapter<String>(chanpings) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                LayoutInflater inflater = LayoutInflater.from(NavigationActivity.this);
                TextView tv = (TextView) inflater.inflate(R.layout.item_flag, parent, false);
                tv.setText(s);
                return tv;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                TextView tv = (TextView) view;
                tv.setBackgroundResource(R.drawable.daohang_select_flag);
                tv.setTextColor(Color.parseColor("#F69223"));
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                TextView tv = (TextView) view;
                tv.setBackgroundResource(R.drawable.daohang_flag);
                tv.setTextColor(Color.parseColor("#4D4D4D"));
            }
        };
        qitaTag.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                switch (position) {
                    case 0:
                        gotoActivity(PersonMessageActivity.class, false);
                        break;
                    case 1:
                        gotoActivity(SettingActivity.class, false);
                        break;
                }
                return false;
            }
        });
        qitaTag.setAdapter(adapter);
    }

    /**
     * 获取推荐
     */
    private void getTuijian() {
        HttpServerImpl.getNavigationHotList().subscribe(new HttpResultSubscriber<List<FlagBO>>() {
            @Override
            public void onSuccess(List<FlagBO> s) {
                setTuijianAdapter(s);
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }


    private void setTuijianAdapter(List<FlagBO> s) {
        TagAdapter<FlagBO> adapter = new TagAdapter<FlagBO>(s) {
            @Override
            public View getView(FlowLayout parent, int position, FlagBO s) {
                LayoutInflater inflater = LayoutInflater.from(NavigationActivity.this);
                TextView tv = (TextView) inflater.inflate(R.layout.item_flag, parent, false);
                tv.setText(s.getTitle());
                return tv;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                TextView tv = (TextView) view;
                tv.setBackgroundResource(R.drawable.daohang_select_flag);
                tv.setTextColor(Color.parseColor("#F69223"));
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                TextView tv = (TextView) view;
                tv.setBackgroundResource(R.drawable.daohang_flag);
                tv.setTextColor(Color.parseColor("#4D4D4D"));
            }
        };
        tuijianTag.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Bundle bundle = new Bundle();
                bundle.putString("url", s.get(position).getUrl());
                bundle.putString("title", s.get(position).getTitle());
                gotoActivity(WebActivity.class, bundle, false);
                return false;
            }
        });
        tuijianTag.setAdapter(adapter);
    }

}
