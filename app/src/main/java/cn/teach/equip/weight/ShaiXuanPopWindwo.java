package cn.teach.equip.weight;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.teach.equip.R;
import cn.teach.equip.bean.pojo.TagBO;
import cn.teach.equip.weight.lgrecycleadapter.LGRecycleViewAdapter;
import cn.teach.equip.weight.lgrecycleadapter.LGViewHolder;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/8/1913:46
 * desc   : 配套条件筛选的弹窗
 * version: 1.0
 */
public class ShaiXuanPopWindwo extends PopupWindow {

    private View dialogView;

    RecyclerView recyclerView;

    private Activity mContext;

    private Map<Integer, Set<Integer>> selectTags;

    private Map<String, Integer> select;

    private List<TagBO> tagBOS;

    public ShaiXuanPopWindwo(Activity context, List<TagBO> tagBOS) {
        super(context);
        mContext = context;
        selectTags = new HashMap<>();
        select = new HashMap<>();
        this.tagBOS = tagBOS;
        dialogView = LayoutInflater.from(context).inflate(R.layout.pop_shaoxuan, null);
        recyclerView = dialogView.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        setAdapter();
        TextView resetBt = dialogView.findViewById(R.id.reset_bt);
        TextView commitBt = dialogView.findViewById(R.id.commit_bt);
        resetBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTags.clear();
                select.clear();
                setAdapter();
                if (listener != null) {
                    listener.onCommit(selectTags, select);
                }
                dismiss();
            }
        });
        commitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCommit(selectTags, select);
                }
                dismiss();
            }
        });

        this.setBackgroundDrawable(new ColorDrawable(0));
        this.setContentView(dialogView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(SizeUtils.dp2px(400));
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.anim_menu_bottombar);
        this.setOnDismissListener(() -> backgroundAlpha(1f));
    }


    /**
     * 设置上次选中的项
     */
    public void setSelectTags(Map<Integer, Set<Integer>> selectTags) {
        this.selectTags = selectTags;
        setAdapter();
    }


    private void setAdapter() {
        LGRecycleViewAdapter<TagBO> adapter = new LGRecycleViewAdapter<TagBO>(tagBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_shaixuan;
            }

            @Override
            public void convert(LGViewHolder holder, TagBO tagBO, int position) {
                holder.setText(R.id.tag_class_name, tagBO.getName());
                TagFlowLayout tagFlowLayout = (TagFlowLayout) holder.getView(R.id.tag_flow_layout);
                setTagAdapter(tagBO.getTagId(), tagBO.getTags(), tagFlowLayout);
            }
        };
        recyclerView.setAdapter(adapter);
    }


    private void setTagAdapter(int id, List<TagBO.TagsBean> tagBeans, TagFlowLayout tagFlowLayout) {
        TagAdapter<TagBO.TagsBean> adapter = new TagAdapter<TagBO.TagsBean>(tagBeans) {
            @Override
            public View getView(FlowLayout parent, int position, TagBO.TagsBean s) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                TextView tv = (TextView) inflater.inflate(R.layout.item_flag, parent, false);
                tv.setText(s.getName());
                return tv;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                TextView tv = (TextView) view;
                tv.setBackgroundResource(R.drawable.daohang_select_flag);
                tv.setTextColor(Color.parseColor("#F69223"));
                select.put(tagBeans.get(position).getName(), tagBeans.get(position).getTagId());
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                TextView tv = (TextView) view;
                tv.setBackgroundResource(R.drawable.daohang_flag);
                tv.setTextColor(Color.parseColor("#4D4D4D"));
                select.remove(tagBeans.get(position).getName());
            }
        };
        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                selectTags.put(id, selectPosSet);
            }
        });
        adapter.setSelectedList(selectTags.get(id));
        tagFlowLayout.setAdapter(adapter);
    }


    @Override
    public void showAsDropDown(View anchor) {//为了适配android 7.0以上的showAsDropDown失效的问题
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
//        backgroundAlpha(0.5f);
        super.showAsDropDown(anchor);
    }


    /**
     * 设置添加屏幕的背景透明度
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mContext.getWindow().setAttributes(lp);
    }

    private onCommitListener listener;

    public void setCommitListener(onCommitListener listener) {
        this.listener = listener;
    }


    public interface onCommitListener {

        void onCommit(Map<Integer, Set<Integer>> session, Map<String, Integer> ids);
    }

}
