package cn.teach.equip.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import butterknife.BindView;
import cn.teach.equip.R;
import cn.teach.equip.base.BaseActivity;

/**
 * 查看大图
 */
public class BigPicutreActivity extends BaseActivity {

    @BindView(R.id.image_pager)
    PhotoView imagePager;

    List<String> imageBOS;

    private int selectPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        imageBOS = getIntent().getStringArrayListExtra("imageBos");
        selectPosition = getIntent().getIntExtra("selectPosition", 0);

        setTitleText("查看图片");
//        imagePager.setAdapter(new MyPagerAdapter());
//        imagePager.setCurrentItem(selectPosition);
        imagePager.setMaximumScale(10);
        imagePager.setDrawingCacheEnabled(true);
        showProgress();
        Glide.with(BigPicutreActivity.this)
                .load(imageBOS.get(0))
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        stopProgress();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        stopProgress();
                        return false;
                    }
                }).into(imagePager);
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
            PhotoView imageView = groupView.findViewById(R.id.iv_big_image);
            Glide.with(BigPicutreActivity.this)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .load(imageBOS.get(position)).into(imageView);
//            Zoomy.Builder builder = new Zoomy.Builder(BigPicutreActivity.this)
//                    .target(imageView)
//                    .enableImmersiveMode(true)
//                    .animateZooming(true)
//                    .zoomListener(new ZoomListener() {
//                        @Override
//                        public void onViewStartedZooming(View view) {
//
//                        }
//
//                        @Override
//                        public void onViewEndedZooming(View view) {
//
//                        }
//                    });
//            builder.register();
            view.addView(groupView);
            return groupView;
        }

    }

}
