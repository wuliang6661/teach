package cn.teach.equip.weight;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.teach.equip.R;

/**
 * 为适应点击之后显示完全不同图标的标题栏
 */
public class TabLinerLayout extends RelativeLayout implements View.OnClickListener {


    private RelativeLayout oneLayout;
    private RelativeLayout twoLayout;
    private RelativeLayout threeLayout;
    private RelativeLayout fourLayout;

    private TextView oneText;
    private TextView twoText;
    private TextView threeText;
    private TextView fourText;

    private TextView[] titles;

    private onClickBarListener listener;

    public TabLinerLayout(Context context) {
        super(context);
        initView(context);
    }

    public TabLinerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TabLinerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TabLinerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initView(context);
    }


    /**
     * 初始化布局
     */
    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tab_bar_layout, this);
        setClickable(true);
        oneLayout = findViewById(R.id.one_layout);
        twoLayout = findViewById(R.id.two_layout);
        threeLayout = findViewById(R.id.three_layout);
        fourLayout = findViewById(R.id.four_layout);
        oneText = findViewById(R.id.one_text);
        twoText = findViewById(R.id.two_text);
        threeText = findViewById(R.id.three_text);
        fourText = findViewById(R.id.four_text);

        titles = new TextView[]{oneText, twoText, threeText, fourText};
        oneLayout.setOnClickListener(this);
        twoLayout.setOnClickListener(this);
        threeLayout.setOnClickListener(this);
        fourLayout.setOnClickListener(this);
        onClick(threeLayout);
    }


    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one_layout:
                switchTitleStyle(0);
                if (listener != null) {
                    listener.clickBar(0);
                }
                oneLayout.setBackgroundResource(R.drawable.select_bg);
                twoLayout.setBackgroundResource(R.drawable.two_press);
                threeLayout.setBackgroundResource(R.drawable.three_unpress);
                fourLayout.setBackgroundResource(R.drawable.four_unpress);
                break;
            case R.id.two_layout:
                switchTitleStyle(1);
                if (listener != null) {
                    listener.clickBar(1);
                }
                oneLayout.setBackgroundResource(R.drawable.two_one);
                twoLayout.setBackgroundResource(R.drawable.select_bg);
                threeLayout.setBackgroundResource(R.drawable.two_three);
                fourLayout.setBackgroundResource(R.drawable.two_four);
                break;
            case R.id.three_layout:
                switchTitleStyle(2);
                if (listener != null) {
                    listener.clickBar(2);
                }
                oneLayout.setBackgroundResource(R.drawable.three_one);
                twoLayout.setBackgroundResource(R.drawable.three_two);
                threeLayout.setBackgroundResource(R.drawable.select_bg);
                fourLayout.setBackgroundResource(R.drawable.three_four);
                break;
            case R.id.four_layout:
                switchTitleStyle(3);
                if (listener != null) {
                    listener.clickBar(3);
                }
                oneLayout.setBackgroundResource(R.drawable.four_one);
                twoLayout.setBackgroundResource(R.drawable.four_two);
                threeLayout.setBackgroundResource(R.drawable.four_three);
                fourLayout.setBackgroundResource(R.drawable.select_bg);
                break;
        }
    }


    /**
     * 设置切换文字颜色显示
     */
    private void switchTitleStyle(int click) {
        for (int i = 0; i < titles.length; i++) {
            if (click == i) {
                titles[i].setTextColor(Color.parseColor("#25519A"));
                titles[i].setTextSize(15);
            } else {
                titles[i].setTextColor(Color.parseColor("#ffffff"));
                titles[i].setTextSize(14);
            }
        }
    }

    /**
     * 设置标题1显示
     */
    public void setTitle1(String title) {
        oneText.setText(title);
    }


    /**
     * 设置标题2显示
     */
    public void setTitle2(String title) {
        twoText.setText(title);
    }

    /**
     * 设置标题3显示
     */
    public void setTitle3(String title) {
        threeText.setText(title);
    }

    /**
     * 设置标题4显示
     */
    public void setTitle4(String title) {
        fourText.setText(title);
    }

    /**
     * 设置监听点击
     */
    public void setListener(onClickBarListener listener) {
        this.listener = listener;
        onClick(threeLayout);
    }


    public interface onClickBarListener {

        void clickBar(int position);
    }
}
