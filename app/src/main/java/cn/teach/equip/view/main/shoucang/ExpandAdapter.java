package cn.teach.equip.view.main.shoucang;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

import cn.teach.equip.bean.pojo.FenLeiBO;

public class ExpandAdapter extends BaseExpandableListAdapter {

    private Context context;

    private List<FenLeiBO> fenLeiBOS;

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
        return null;
    }

    class GroupHolder {



    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
