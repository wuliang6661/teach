package cn.teach.equip.util;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.bean.pojo.VersionBO;
import cn.teach.equip.constans.FileConfig;
import cn.teach.equip.view.main.MainActivity;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/6/2111:14
 * desc   : App检查更新的工具类
 * version: 1.0
 */
public class UpdateUtils {


    private Activity context;

    private boolean mIsCancel = false;
    private String version = "teach.apk";

    public void checkUpdate(Activity context, onUpdateListener listener) {
        this.context = context;
        HttpServerImpl.getVersionInfo().subscribe(new HttpResultSubscriber<VersionBO>() {
            @Override
            public void onSuccess(VersionBO s) {
                if (s == null) {
                    if (listener != null) {
                        listener.noUpdate();
                    }
                    return;
                }
                if (Integer.parseInt(s.getLatestVersion()) > AppUtils.getAppVersionCode()) {
                    if (s.getIsForceUpdate() == 1) { //强制更新
                        checkPrission(s.getDownloadUrl());
                    } else {
                        createCustomDialogTwo(s);
                    }
                } else {
                    if (listener != null) {
                        listener.noUpdate();
                    }
                }
            }

            @Override
            public void onFiled(String message) {
                // 首页不显示异常弹窗，只有检测更新时弹出
                if (StringUtils.isEmpty(message) || AppManager.getAppManager().curremtActivity()
                        instanceof MainActivity) {
                    return;
                }
                ToastUtils.showShort(message);
            }
        });
    }


    public interface onUpdateListener {
        void noUpdate();
    }


    private ProgressDialog progressDialog;

    /*
     * 显示正在下载对话框
     */
    private void showDownloadDialog(String url) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("正在下载...");
        progressDialog.setCancelable(false);//不能手动取消下载进度对话框
        progressDialog.setProgressNumberFormat("");
        progressDialog.show();

        // 下载文件
        downloadAPK(url);
    }


    private void createCustomDialogTwo(VersionBO versionBO) {
        PopupWindow popupWindow = new PopupWindow();
        View dialogView = LayoutInflater.from(context).inflate(R.layout.layout_update_pop, null);
        TextView update = dialogView.findViewById(R.id.update);
        TextView cancle = dialogView.findViewById(R.id.cancle);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                ToastUtils.showShort("开始下载新版本");
                checkPrission(versionBO.getDownloadUrl());
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setContentView(dialogView);
        //设置PopupWindow弹出窗体的宽
        popupWindow.setWidth(SizeUtils.dp2px(102));
        //设置PopupWindow弹出窗体的高
        popupWindow.setHeight(SizeUtils.dp2px(210));
        //设置PopupWindow弹出窗体可点击
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }


    private void checkPrission(String url) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                ) {

            ActivityCompat.requestPermissions(context,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 1);
        } else {
            downloadAPK(url);
        }
    }


    /* 开启新线程下载apk文件
     */
    public void downloadAPK(String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mIsCancel = false;
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        File dir = new File(FileConfig.getApkFile());
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        // 下载文件
                        HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
//                        int length = conn.getContentLength();

                        File apkFile = new File(FileConfig.getApkFile(), version);
                        FileOutputStream fos = new FileOutputStream(apkFile);

                        int count = 0;
                        byte[] buffer = new byte[1024];

                        while (!mIsCancel) {
                            int numread = is.read(buffer);
                            count += numread;
                            Message message = Message.obtain();
                            message.obj = count;
                            handler.sendMessage(message);
                            // 下载完成
                            if (numread < 0) {
                                handler.sendEmptyMessage(0x22);
                                AppUtils.installApp(apkFile);
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtils.e(Thread.currentThread().getName(), "2");
        }
    };


}
