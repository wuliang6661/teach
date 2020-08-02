package cn.teach.equip.weight;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import cn.teach.equip.R;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/7/2412:24
 * desc   : 收藏底部的删除弹窗
 * version: 1.0
 */
public class PopDeleteWindow extends PopupWindow {

    private View dialogView;

    private onClickListener listener;

    public PopDeleteWindow(Activity context) {
        super(context);

        dialogView = LayoutInflater.from(context).inflate(R.layout.pop_delete, null);
        LinearLayout deleteLayout = dialogView.findViewById(R.id.delete_layout);
        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick();
                }
            }
        });
        this.setBackgroundDrawable(new ColorDrawable(0));
        this.setContentView(dialogView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(false);
        this.setOutsideTouchable(false);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.anim_menu_bottombar);
    }


    /***
     * 显示时将屏幕置为透明
     */
    public void showAtLocation(View parent) {
        super.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }


    public void setListener(onClickListener listener) {
        this.listener = listener;
    }

    public interface onClickListener {

        void onClick();
    }


}
