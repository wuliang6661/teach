package cn.teach.equip.weight;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;

import java.util.concurrent.TimeUnit;

import cn.teach.equip.R;
import cn.teach.equip.util.AppManager;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ToastManager {

//    public static void showShort(String message) {
//
//        Handler mainHandler = new Handler(Looper.getMainLooper());
//        mainHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                //已在主线程中，可以更新UI
////                Context context = AppManager.getAppManager().curremtActivity();
//                Context context = Utils.getApp();
//                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                //使用布局加载器，将编写的toast_layout布局加载进来
//                View view = layoutInflater.inflate(R.layout.toast_view, null);
//                //获取TextView
//                TextView title = view.findViewById(R.id.toast_tv);
//                //设置显示的内容
//                title.setText(message);
//                Toast toast = new Toast(context);
//                //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
////                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 280);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                //设置显示时间
//                toast.setDuration(Toast.LENGTH_SHORT);
//
//                toast.setView(view);
//                toast.getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//                toast.show();
//            }
//        });
//    }


    /**
     * 全屏展示纯文本toast
     * AppManager.getInstance().app//自己项目的application的Context'
     *
     * @param text
     */
    public static void showShort(CharSequence text) {

        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                Context context = Utils.getApp();
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.toast_view, null);
                TextView tvMessage = view.findViewById(R.id.toast_tv);
                tvMessage.setText(text);
                int heiht = context.getResources().getDisplayMetrics().heightPixels;
                final PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        popupWindow.setAnimationStyle(R.style.scale_pop_window_anim_style);//添加动画效果
                popupWindow.setFocusable(false);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ffffff")));
                popupWindow.setClippingEnabled(false);
                popupWindow.showAtLocation(AppManager.getAppManager().curremtActivity().getWindow().getDecorView(),
                        Gravity.NO_GRAVITY, 0, -20);

                //用RxJava实现定时3秒
                Observable.timer(2000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

}

