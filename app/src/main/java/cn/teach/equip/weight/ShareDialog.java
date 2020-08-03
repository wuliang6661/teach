package cn.teach.equip.weight;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.SizeUtils;

import cn.teach.equip.R;
import cn.teach.equip.util.AppManager;

public class ShareDialog {

    Activity activity;

    public ShareDialog() {
        activity = AppManager.getAppManager().curremtActivity();
    }


    public void showShareDialog(OnClickShare clickShare) {
        PopupWindow popupWindow = new PopupWindow();
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.layout_share_pop, null);
        LinearLayout weixin = dialogView.findViewById(R.id.weixin);
        LinearLayout weixin_pengyouquan = dialogView.findViewById(R.id.weixin_pengyouquan);
        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                 if(clickShare != null){
                     clickShare.share(0);
                 }
            }
        });
        weixin_pengyouquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if(clickShare != null){
                    clickShare.share(1);
                }
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
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
//        backgroundAlpha(0.5f);
//        popupWindow.setOnDismissListener(() -> backgroundAlpha(1f));
    }


    /**
     * 设置添加屏幕的背景透明度
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }


    public interface OnClickShare {

        void share(int flag);
    }

}
