package cn.teach.equip.view.selectcity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import cn.teach.equip.R;
import cn.teach.equip.bean.pojo.ProvinceBO;
import me.yokeyword.indexablerv.IndexableAdapter;


/**
 * Created by YoKey on 16/10/8.
 */
public class ContactAdapter extends IndexableAdapter<ProvinceBO.CityListBean> {
    private LayoutInflater mInflater;

    public ContactAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_contact, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_city, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, ProvinceBO.CityListBean entity) {
        ContentVH vh = (ContentVH) holder;
        vh.city_name.setText(entity.getCityName());
    }

    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;

        public IndexVH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_index);
        }
    }

    private class ContentVH extends RecyclerView.ViewHolder {
        TextView city_name;

        public ContentVH(View itemView) {
            super(itemView);
            city_name = (TextView) itemView.findViewById(R.id.city_name);
        }
    }
}
