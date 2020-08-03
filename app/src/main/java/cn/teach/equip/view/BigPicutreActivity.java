package cn.teach.equip.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import cn.teach.equip.R;
import cn.teach.equip.base.BaseActivity;

/**
 * 查看大图
 */
public class BigPicutreActivity extends BaseActivity {

    @BindView(R.id.image_pager)
    ViewPager imagePager;

    List<String> imageBOS;

    private int selectPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        imageBOS = getIntent().getStringArrayListExtra("imageBos");
        selectPosition = getIntent().getIntExtra("selectPosition", 0);

        setTitleText("查看图片");
        imagePager.setAdapter(new MyPagerAdapter());
        imagePager.setCurrentItem(selectPosition);
    }



    @Override
    protected int getLayout() {
        return R.layout.act_big_picture;
    }



    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageBOS.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View groupView = LayoutInflater.from(BigPicutreActivity.this).inflate(R.layout.act_big_img, null);
            ImageView imageView = groupView.findViewById(R.id.iv_big_image);
            Glide.with(BigPicutreActivity.this).load(imageBOS.get(position)).into(imageView);
            view.addView(groupView);
            return groupView;
        }

    }

}
