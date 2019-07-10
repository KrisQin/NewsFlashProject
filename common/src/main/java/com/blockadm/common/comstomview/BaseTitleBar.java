package com.blockadm.common.comstomview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockadm.common.R;


public class BaseTitleBar extends LinearLayout {

    /**
     * 声明titlebar属性
     *
     * @param context
     */
    private RelativeLayout titlebarlinear;
    private TextView leftTextview;
    private TextView centerTextView;
    private TextView rightTextView;
    private ImageView leftimage;
    private ImageView rightimage;
    private int lefttext_color = Color.WHITE;
    private int righttext_color = Color.WHITE;
    private int back_color = Color.parseColor("#FFFFFF");
    private boolean lefticon_isshow = false;
    private boolean line_is_show = true;
    private boolean righticon_isshow = false;
    private boolean lefttext_isshow = true;
    private boolean righttext_isshow = true;
    private String lefttext;
    private String centertext;
    private String righttext;
    private int lefticon;
    private int righticon;

    private RelativeLayout layout_left;
    private RelativeLayout layout_right;
    private View bottom_line;


    public BaseTitleBar(Context context) {
        super(context);
        init(context);
    }

    public BaseTitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypeArrar = context.obtainStyledAttributes(attrs, R.styleable.BaseTitleBar);
        lefttext_color = mTypeArrar.getColor(R.styleable.BaseTitleBar_lefttext_color, Color.WHITE);
        righttext_color = mTypeArrar.getColor(R.styleable.BaseTitleBar_righttext_color, Color.WHITE);
        lefttext_isshow = mTypeArrar.getBoolean(R.styleable.BaseTitleBar_lefttext_is_show, false);
        righttext_isshow = mTypeArrar.getBoolean(R.styleable.BaseTitleBar_righttext_is_show, false);
        lefticon_isshow = mTypeArrar.getBoolean(R.styleable.BaseTitleBar_lefticon_is_show, false);
        line_is_show = mTypeArrar.getBoolean(R.styleable.BaseTitleBar_bottomline_is_show, false);
        righticon_isshow = mTypeArrar.getBoolean(R.styleable.BaseTitleBar_righticon_is_show, false);
        lefticon = mTypeArrar.getResourceId(R.styleable.BaseTitleBar_lefticon_src, 0);
        righticon = mTypeArrar.getResourceId(R.styleable.BaseTitleBar_righticon_src, 0);
        lefttext = mTypeArrar.getString(R.styleable.BaseTitleBar_lefttext);
        righttext = mTypeArrar.getString(R.styleable.BaseTitleBar_righttext);
        centertext = mTypeArrar.getString(R.styleable.BaseTitleBar_centertext);
        back_color = mTypeArrar.getColor(R.styleable.BaseTitleBar_titlebarback_color,Color.parseColor("#FFFFFF"));

        init(context);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public BaseTitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypeArrar = context.obtainStyledAttributes(attrs, R.styleable.BaseTitleBar);
        lefttext_color = mTypeArrar.getColor(R.styleable.BaseTitleBar_lefttext_color, Color.WHITE);
        righttext_color = mTypeArrar.getColor(R.styleable.BaseTitleBar_righttext_color, Color.WHITE);
        lefttext_isshow = mTypeArrar.getBoolean(R.styleable.BaseTitleBar_lefttext_is_show, false);
        righttext_isshow = mTypeArrar.getBoolean(R.styleable.BaseTitleBar_righttext_is_show, false);
        lefticon_isshow = mTypeArrar.getBoolean(R.styleable.BaseTitleBar_lefticon_is_show, false);
        line_is_show = mTypeArrar.getBoolean(R.styleable.BaseTitleBar_bottomline_is_show, false);
        righticon_isshow = mTypeArrar.getBoolean(R.styleable.BaseTitleBar_righticon_is_show, false);
        lefticon = mTypeArrar.getResourceId(R.styleable.BaseTitleBar_lefticon_src, 0);
        righticon = mTypeArrar.getResourceId(R.styleable.BaseTitleBar_righticon_src, 0);
        lefttext = mTypeArrar.getString(R.styleable.BaseTitleBar_lefttext);
        righttext = mTypeArrar.getString(R.styleable.BaseTitleBar_righttext);
        centertext = mTypeArrar.getString(R.styleable.BaseTitleBar_centertext);
        back_color = mTypeArrar.getColor(R.styleable.BaseTitleBar_titlebarback_color,Color.parseColor("#FFFFFF"));

        mTypeArrar.recycle();
        init(context);
    }

    /**
     * 初始化布局
     */
    public void init(Context context) {
        View v =LayoutInflater.from(context).inflate(R.layout.activity_basetitle,this,true);
        leftTextview = v.findViewById(R.id.lefttext);
        centerTextView = v.findViewById(R.id.ceterntext);
        rightTextView = v.findViewById(R.id.righttext);
        leftimage = v.findViewById(R.id.lefticon);
        rightimage = v.findViewById(R.id.right_icon);
        titlebarlinear = v.findViewById(R.id.titlebarlinear);

        bottom_line = v.findViewById(R.id.bottom_line);
        layout_right = v.findViewById(R.id.layout_right);
        layout_left = v.findViewById(R.id.layout_left);

        if (line_is_show) {
            bottom_line.setVisibility(View.VISIBLE);
        }else {
            bottom_line.setVisibility(View.GONE);
        }

      /*   centerTextView.setTextColor(centerColor);
       Drawable dra= getResources().getDrawable(centerSrc);
        dra.setBounds( 0, 0, dra.getMinimumWidth(),dra.getMinimumHeight());
        centerTextView.setCompoundDrawables(dra,null,null,null);*/
        /**
         * 设置属性
         */
        if (lefttext_isshow){
            leftTextview.setVisibility(View.VISIBLE);
            leftTextview.setText(lefttext);
            leftTextview.setTextColor(lefttext_color);
        }
        if (righttext_isshow){
            rightTextView.setVisibility(View.VISIBLE);
            rightTextView.setText(righttext);
            rightTextView.setTextColor(righttext_color);
        }
        if (lefticon_isshow){
            leftimage.setVisibility(View.VISIBLE);
            if (lefticon!=0){
                leftimage.setImageDrawable(getResources().getDrawable(lefticon));
            }
        }
        if (righticon_isshow){
            rightimage.setVisibility(View.VISIBLE);
            if (righticon!=0){
                rightimage.setImageDrawable(getResources().getDrawable(righticon));
            }

        }
        titlebarlinear.setBackgroundColor(back_color);
        centerTextView.setText(centertext);
        centerTextView.setTextColor(lefttext_color);


    }

    public  void  setRightimageShow(int  isShow){
        rightimage.setVisibility(isShow);

    }

    public  void  setTitle(String   title){
        centerTextView.setText(title);

    }

    public  void  setRightText(String   text){
        rightTextView.setText(text);

    }

    public  void  setLeftImageShow(boolean  isShow){
        if (isShow) {
            leftimage.setVisibility(VISIBLE);
        }else {
            leftimage.setVisibility(GONE);
        }

    }

    /**
     * 左边监听事件
     */
    public void setOnLeftOnclickListener(OnClickListener onLeftOnclickListener){
        if (lefticon_isshow || lefttext_isshow){
            layout_left.setOnClickListener(onLeftOnclickListener);
        }
    }

    /**
     * 右边监听事件
     */
    public void setOnRightOnclickListener(OnClickListener onRightOnclickListener){
        if (righticon_isshow || righttext_isshow){
            layout_right.setOnClickListener(onRightOnclickListener);
        }
    }


}
