package cn.teach.equip.view.main.findnew;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.teach.equip.R;
import cn.teach.equip.bean.pojo.VideoFeiLeiBO;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/8/1810:40
 * desc   : 视频侧边栏适配器
 * version: 1.0
 */
public class VideoMenuAdapter extends BaseExpandableListAdapter {

    private Context context;

    private List<VideoFeiLeiBO> fenLeiBOS;

    /**
     * 选中的父级菜单
     */
    private int selectGroup = 0;

    /**
     * 选中的子级菜单
     */
    private int selectChild = 0;

    public VideoMenuAdapter(Context context, List<VideoFeiLeiBO> fenLeiBOS) {
        this.context = context;
        this.fenLeiBOS = fenLeiBOS;
    }

    @Override
    public int getGroupCount() {
        return fenLeiBOS.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return fenLeiBOS.get(groupPosition).getSubList().size();
    }

    @Override
    public VideoFeiLeiBO getGroup(int groupPosition) {
        return fenLeiBOS.get(groupPosition);
    }

    public void setSelectGroup(int selectGroup) {
        this.selectGroup = selectGroup;
    }

    public void setSelectChild(int selectChild) {
        this.selectChild = selectChild;
    }


    @Override
    public VideoFeiLeiBO.SubListBean getChild(int groupPosition, int childPosition) {
        return fenLeiBOS.get(groupPosition).getSubList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return fenLeiBOS.get(groupPosition).getVideoTypeId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return fenLeiBOS.get(groupPosition).getSubList().get(childPosition).getVideoTypeId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shengfen, parent, false);
            holder = new GroupHolder();
            holder.selectImg = convertView.findViewById(R.id.select_img);
            holder.fenleiText = convertView.findViewById(R.id.proce_name);
            holder.itemView = convertView.findViewById(R.id.item_layout);
            holder.buttomLine = convertView.findViewById(R.id.buttom_view);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        if (groupPosition == selectGroup) {
            holder.selectImg.setVisibility(View.VISIBLE);
            holder.fenleiText.setTextColor(Color.parseColor("#F7931E"));
            holder.itemView.setBackgroundColor(Color.parseColor("#F4F4F4"));
            holder.buttomLine.setVisibility(View.VISIBLE);
        } else {
            holder.selectImg.setVisibility(View.GONE);
            holder.fenleiText.setTextColor(Color.parseColor("#7F7F7F"));
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.buttomLine.setVisibility(View.GONE);
        }
        holder.fenleiText.setText(getGroup(groupPosition).getName());
        TextPaint tp = holder.fenleiText.getPaint();
        tp.setFakeBoldText(true);
        return convertView;
    }

    class GroupHolder {
        ImageView selectImg;
        TextView fenleiText;
        RelativeLayout itemView;
        View buttomLine;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fenlei_child, parent, false);
            holder = new ChildHolder();
            holder.childLine = convertView.findViewById(R.id.selct_line);
            holder.childText = convertView.findViewById(R.id.child_text);
            holder.buttomLine = convertView.findViewById(R.id.buttom_view);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        if (childPosition == selectChild && selectGroup == groupPosition) {
            holder.childLine.setVisibility(View.VISIBLE);
        } else {
            holder.childLine.setVisibility(View.GONE);
        }
        if (getChildrenCount(groupPosition) - 1 == childPosition) {
            holder.buttomLine.setVisibility(View.VISIBLE);
        } else {
            holder.buttomLine.setVisibility(View.GONE);
        }
        holder.childText.setText(getChild(groupPosition, childPosition).getName());
        return convertView;
    }


    class ChildHolder {
        TextView childText;
        View childLine;
        View buttomLine;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
