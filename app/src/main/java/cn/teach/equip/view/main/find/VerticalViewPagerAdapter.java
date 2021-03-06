package cn.teach.equip.view.main.find;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.teach.equip.bean.pojo.VideoListBO;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 作者： ch
 * 时间： 2018/7/30 0030-下午 3:42
 * 描述：
 * 来源：
 */


public class VerticalViewPagerAdapter extends PagerAdapter {

    private FragmentManager fragmentManager;
    private FragmentTransaction mCurTransaction;
    private SupportFragment mCurrentPrimaryItem = null;
    private List<VideoListBO.PageListBean> urlList;

    public void setUrlList(List<VideoListBO.PageListBean> urlList) {
        this.urlList = urlList;
    }


    public VerticalViewPagerAdapter(FragmentManager fm) {
        this.fragmentManager = fm;
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mCurTransaction == null) {
            mCurTransaction = fragmentManager.beginTransaction();
        }
        VideoFragment fragment = new VideoFragment();
        if (urlList != null && urlList.size() > 0) {
            Bundle bundle = new Bundle();
            if (position >= urlList.size()) {
                bundle.putSerializable("video", urlList.get(position % urlList.size()));
            } else {
                bundle.putSerializable("video", urlList.get(position));
            }
            fragment.setArguments(bundle);
        }
        mCurTransaction.add(container.getId(), fragment,
                makeFragmentName(container.getId(), position));
        fragment.setUserVisibleHint(false);
        return fragment;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mCurTransaction == null) {
            mCurTransaction = fragmentManager.beginTransaction();
        }
        mCurTransaction.detach((Fragment) object);
        mCurTransaction.remove((Fragment) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return ((Fragment) object).getView() == view;
    }

    private String makeFragmentName(int viewId, int position) {
        return "android:switcher:" + viewId + position;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        SupportFragment fragment = (SupportFragment) object;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }


    public void setHintVideo(){
        mCurrentPrimaryItem.onSupportInvisible();
    }

    public void showVideo(){
        mCurrentPrimaryItem.onSupportVisible();
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        if (mCurTransaction != null) {
            mCurTransaction.commitNowAllowingStateLoss();
            mCurTransaction = null;
        }
    }
}
