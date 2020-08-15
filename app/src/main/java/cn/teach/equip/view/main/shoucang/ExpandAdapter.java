package cn.teach.equip.view.main.shoucang;

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
import cn.teach.equip.bean.pojo.FenLeiBO;

public class ExpandAdapter extends BaseExpandableListAdapter {

    private Context context;

    private List<FenLeiBO> fenLeiBOS;

    /**
     * 选中的父级菜单
     */
    private int selectGroup = 0;

    /**
     * 选中的子级菜单
     */
    private int selectChild = 0;

    public ExpandAdapter(Context context, List<FenLeiBO> fenLeiBOS) {
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
    public FenLeiBO getGroup(int groupPosition) {
        return fenLeiBOS.get(groupPosition);
    }

    public void setSelectGroup(int selectGroup) {
        this.selectGroup = selectGroup;
    }

    public void setSelectChild(int selectChild) {
        this.selectChild = selectChild;
    }


    @Override
    public FenLeiBO.SubListBean getChild(int groupPosition, int childPosition) {
        return fenLeiBOS.get(groupPosition).getSubList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return fenLeiBOS.get(groupPosition).getLevelId2();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return fenLeiBOS.get(groupPosition).getSubList().get(childPosition).getLevelId3();
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
            holder.fenleiText.setTextColor(Color.parseColor("#25519A"));
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.buttomLine.setVisibility(View.GONE);
        }
        holder.fenleiText.setText(getGroup(groupPosition).getLevelName2());
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
        holder.childText.setText(getChild(groupPosition, childPosition).getLevelName3());
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
