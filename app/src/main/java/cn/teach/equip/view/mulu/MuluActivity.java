package cn.teach.equip.view.mulu;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.bean.pojo.MuluListBO;
import cn.teach.equip.constans.FileConfig;
import cn.teach.equip.mvp.MVPBaseActivity;
import cn.teach.equip.util.FileUtils;
import cn.teach.equip.util.OpenFileUtil;
import cn.teach.equip.util.ShareUtils;
import cn.teach.equip.weight.TabLinerLayout;
import cn.teach.equip.weight.lgrecycleadapter.LGRecycleViewAdapter;
import cn.teach.equip.weight.lgrecycleadapter.LGViewHolder;


/**
 * MVPPlugin
 * 目录下载页面
 */

public class MuluActivity extends MVPBaseActivity<MuluContract.View, MuluPresenter> implements MuluContract.View {

    private static final String TAG = "MuluActivity";
    @BindView(R.id.right_img)
    LinearLayout rightImg;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.srl_page)
    SmartRefreshLayout srlPage;
    @BindView(R.id.tab_linerlayout)
    TabLinerLayout tabLinerlayout;
    @BindView(R.id.et_sousuo)
    EditText etSousuo;

    private int pageNum = 1;
    private int selectMenu = 1; //默认是1

    private List<MuluListBO.PageListBean> list;
    LGRecycleViewAdapter<MuluListBO.PageListBean> adapter;

    /**
     * 已下载的文件
     */
    private Map<String, File> fileMap = new HashMap<>();

    @Override
    protected int getLayout() {
        return R.layout.act_mulu;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("目录下载");
//        rightImg.setVisibility(View.VISIBLE);

        recycleView.setLayoutManager(new LinearLayoutManager(this));
        addListener();
        tabLinerlayout.setListener(new TabLinerLayout.onClickBarListener() {
            @Override
            public void clickBar(int position) {
                selectMenu = position + 1;
                pageNum = 1;
                getDownLoadList("");
            }
        });
        tabLinerlayout.setSelectTab(2);
        requestPermission();
    }


    @OnClick(R.id.bt_sousuo)
    public void sousuo() {
        String searchContent = etSousuo.getText().toString().trim();
        getDownLoadList(searchContent);
    }


    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                ) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 1);
        } else {
            fileMap = FileUtils.getAllFiles(null);
            getDownLoadList("");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "onRequestPermissionsResult granted");
                    fileMap = FileUtils.getAllFiles(null);
                    getDownLoadList("");
                } else {
                    Log.i(TAG, "onRequestPermissionsResult denied");
                    showWaringDialog();
                }
                return;
            }
        }
    }

    private void showWaringDialog() {
        new AlertDialog.Builder(this)
                .setTitle("警告！")
                .setMessage("请前往设置->应用->教育装备->权限中打开相关权限，否则功能无法正常运行！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 一般情况下如果用户不授权的话，功能是无法运行的，做退出处理
                        finish();
                    }
                }).show();
    }

    private void addListener() {
        srlPage.setEnableAutoLoadMore(true);
        srlPage.setEnableLoadMore(true);
//        srlPage.setRefreshHeader(new FunG)
        srlPage.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                srlPage.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageNum++;
                        getDownLoadList("");
                    }
                }, 1000);

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srlPage.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageNum = 1;
                        getDownLoadList("");
                    }
                }, 1000);
            }
        });
    }


    private void getDownLoadList(String searchContent) {
        HttpServerImpl.getDownloadFileList(selectMenu, pageNum, searchContent).subscribe(new HttpResultSubscriber<MuluListBO>() {
            @Override
            public void onSuccess(MuluListBO s) {
                if (pageNum == 1) {
                    list = s.getPageList();
                    setAdapter();
                } else {
                    if (s.getPageList().isEmpty()) {
                        pageNum--;
                    }
                    list.addAll(s.getPageList());
                    adapter.setData(list);
                }
                srlPage.finishLoadMore();
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
        adapter = new LGRecycleViewAdapter<MuluListBO.PageListBean>(list) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_mulu;
            }

            @Override
            public void convert(LGViewHolder holder, MuluListBO.PageListBean s, int position) {
                holder.setText(R.id.mulu_name, s.getTitle());
                TextView downText = (TextView) holder.getView(R.id.progress_text);
                if (fileMap.containsKey(s.getCode() + "." + s.getSuffix())) {
                    holder.getView(R.id.down_wancheng_layout).setVisibility(View.VISIBLE);
                    holder.setImageResurce(R.id.item_img, R.drawable.xiazaiwancheng);
                    holder.setText(R.id.progress_text, "已下载");
                    holder.getView(R.id.down_load_layout).setEnabled(false);
                    downText.setTextColor(Color.parseColor("#CCCCCC"));
                } else {
                    holder.getView(R.id.down_wancheng_layout).setVisibility(View.GONE);
                    holder.setImageResurce(R.id.item_img, R.drawable.xiazai);
                    holder.setText(R.id.progress_text, "下载");
                    holder.getView(R.id.down_load_layout).setEnabled(true);
                    downText.setTextColor(Color.parseColor("#25519A"));
                }
            }
        };
        adapter.setOnItemClickListener(R.id.down_load_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                downloadFile(position);
            }
        });
        adapter.setOnItemClickListener(R.id.open_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                String filePath = new File(FileConfig.getMlFile(), adapter.getItem(position).getCode() + "." +
                        adapter.getItem(position).getSuffix()).getAbsolutePath();
                Intent intent = OpenFileUtil.openFile(filePath);
                startActivity(intent);
            }
        });
        adapter.setOnItemClickListener(R.id.zhuanfa_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                String filePath = new File(FileConfig.getMlFile(), adapter.getItem(position).getCode() + "." +
                        adapter.getItem(position).getSuffix()).getAbsolutePath();
                ShareUtils.shareFile(adapter.getItem(position).getTitle(), adapter.getItem(position).getDesc(), filePath);
            }
        });
        recycleView.setAdapter(adapter);
    }


    private void downloadFile(int position) {
        MuluListBO.PageListBean item = list.get(position);
        FileUtils.downloadFile(FileConfig.getMlFile(), item.getCode() + "." + item.getSuffix(),
                item.getUrl(), progress -> {
                    RelativeLayout layout = (RelativeLayout) recycleView.getChildAt(position);
                    LinearLayout layout_fuceng = layout.findViewById(R.id.layout_fuceng);
                    layout_fuceng.setVisibility(View.GONE);
                    ProgressBar progressBar = layout.findViewById(R.id.progress_bar);
                    ImageView image = layout.findViewById(R.id.item_img);
                    TextView itemText = layout.findViewById(R.id.progress_text);
                    progressBar.setProgress(progress);
                    if (progress == 100) {
                        layout_fuceng.setVisibility(View.VISIBLE);
                        image.setImageResource(R.drawable.xiazaiwancheng);
                        itemText.setText("已下载");
                        fileMap = FileUtils.getAllFiles(null);
                        adapter.notifyDataSetChanged();
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileUtils.maps.clear();
    }
}
