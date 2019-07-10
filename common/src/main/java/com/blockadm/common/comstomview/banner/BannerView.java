package com.blockadm.common.comstomview.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockadm.common.R;
/*import com.blockadm.common.utils.ImageUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;*/
import com.blockadm.common.bean.BannerListDto;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.L;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.ArrayList;


/**
 * Description:
 */
public class BannerView extends RelativeLayout {
    private BannerViewPager mBannerViewPager;
    //底部的指示器的View
    private LinearLayout mDotContainerView;

    private Context mContext;
    //选中的drawable
    private Drawable mIndicatorFocusDrawable;
    //未被选中的drawable
    private Drawable mIndicatorNormalDrawable;
    //当前页面的位置
    private int mCurrentPosition;
    //指示器的位置
    private int mDotGravity = 0;
    //指示器的大小
    private int mDotSize = 6;
    //指示器的间距
    private int mDotDistance = 2;
    //底部颜色默认透明
    private int mBottomColor = Color.TRANSPARENT;
    private View mBannerBottomView;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.banner_layout, this);
        this.mContext = context;
        initAttribute(attrs);
        initView();

    }

    /**
     * 初始化自定义属性
     *
     * @param attrs
     */
    private void initAttribute(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);
        mDotGravity = typedArray.getInt(R.styleable.BannerView_dotGravity, 0);
        Log.i("BannerView_dotGravity", mDotGravity + "");
        mIndicatorFocusDrawable = typedArray.getDrawable(R.styleable.BannerView_dotIndicatorFocus);
        if (mIndicatorFocusDrawable == null) {
            mIndicatorFocusDrawable = new ColorDrawable(Color.RED);
        }
        mIndicatorNormalDrawable =
                typedArray.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
        if (mIndicatorNormalDrawable == null) {
            mIndicatorNormalDrawable = new ColorDrawable(Color.WHITE);
        }
        mDotSize = (int) typedArray.getDimension(R.styleable.BannerView_dotSize,
                DensityUtil.dp2px(6));
        mDotDistance = (int) typedArray.getDimension(R.styleable.BannerView_dotDistance,
                DensityUtil.dp2px(2));
        mBottomColor = typedArray.getColor(R.styleable.BannerView_bottomColor, mBottomColor);

        typedArray.recycle();
    }

    /**
     * 初始化View
     */
    private void initView() {
        mBannerViewPager = findViewById(R.id.bannerViewPager);
        mDotContainerView = findViewById(R.id.dot_container);
        mBannerBottomView = findViewById(R.id.bannerBottomView);
        TextView textView = findViewById(R.id.tv_desc);
        textView.setVisibility(GONE);
        //mBannerBottomView.setBackgroundColor(mBottomColor);
        //mBannerViewPager.setPageTransformer(false, new ScalePageTransformer());
    }

    /**
     * 设置适配器adapter
     */
    public void setPictrues(ArrayList<BannerListDto> pictrues) {

        if (pictrues != null) {
            this.mUrls.clear();
            this.mUrls.addAll(pictrues);
        }

        mBannerViewPager.setAdapter(mAdapter);
        mBannerViewPager.setCurrentItem(mBannerViewPager.getChildCount() / 2);
        initDotIndicator();
        mBannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //监听下当前的位置
                super.onPageSelected(position);

                try {

                    DotIndicatorView dotIndicatorView = (DotIndicatorView) mDotContainerView.
                            getChildAt(mCurrentPosition);
                    if (dotIndicatorView != null) {
                        dotIndicatorView.setDrawable(mIndicatorNormalDrawable);
                    }
                    mCurrentPosition = position % mAdapter.getCount();
                    DotIndicatorView mCurrentIndicatorView = (DotIndicatorView) mDotContainerView.
                            getChildAt(mCurrentPosition);
                    mCurrentIndicatorView.setDrawable(mIndicatorFocusDrawable);
                } catch (Exception e) {

                }

            }
        });
    }

    private ArrayList<BannerListDto> mUrls = new ArrayList<>();
    BannerAdapter mAdapter = new BannerAdapter() {
        @Override
        public View getView(final int position, View convertView) {
            ImageView bannerIv;
            if (convertView == null) {
                bannerIv = new ImageView(mContext);
                bannerIv.setScaleType(ImageView.ScaleType.FIT_XY);

            } else {
                bannerIv = (ImageView) convertView;
                Log.i("TAG", "getView: 界面复用" + bannerIv);
            }

            bannerIv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBannerClickListener != null) {
                        onBannerClickListener.onBannerItemClick(position);
                    }
                }
            });

            try {
                Glide.with(mContext).load(mUrls.get(position).getBannerUrl())
                        .error(R.mipmap.picture_default)
                        .transform(new ImageUtils.GlideRoundTransform(mContext, 6)).into(bannerIv);
            } catch (Exception e) {
                L.d(" BannerView e.toString() --> " + e.toString());
            }


            return bannerIv;
        }

        @Override
        public int getCount() {
            return mUrls == null ? 0 : mUrls.size();
        }
    };

    private OnBannerClickListener onBannerClickListener;

    public void setItemClick(OnBannerClickListener itemClick) {
        onBannerClickListener = itemClick;
    }

    public interface OnBannerClickListener {
        void onBannerItemClick(int position);
    }


    public void startLoop() {
        mBannerViewPager.startLoop();
    }

    public void setScrollerDuration(int scrollerDuration) {
        mBannerViewPager.setScrollerDuration(scrollerDuration);
    }

    /**
     * 初始化指示器
     */
    private void initDotIndicator() {
        //获取广告位的数量
        int count = mAdapter.getCount();
        //设置指示器的位置
        mDotContainerView.setGravity(getDotGravity());
        for (int i = 0; i < count; i++) {
            DotIndicatorView dot = new DotIndicatorView(mContext);
            //设置指示器的形状
            dot.setShape(2);
            LinearLayout.LayoutParams param = null;
            //矩形
            if (dot.getShape() == 1) {
                //给指示器指定大小
                param = new LinearLayout.LayoutParams(mDotSize * 3, DensityUtil.dp2px(2));
                //圆形
            } else if (dot.getShape() == 2) {
                param = new LinearLayout.LayoutParams(mDotSize, mDotSize);
            }
            //设置间距
            param.leftMargin = param.rightMargin = mDotDistance;
            param.gravity = mDotGravity;
            dot.setLayoutParams(param);
            if (i == 0) {
                dot.setDrawable(mIndicatorFocusDrawable);
            } else {
                dot.setDrawable(mIndicatorNormalDrawable);
            }
            mDotContainerView.addView(dot);
        }
    }

    public int getDotGravity() {
        switch (mDotGravity) {
            case 0:
                return Gravity.CENTER;
            case 1:
                return Gravity.RIGHT;
            case -1:
                return Gravity.LEFT;
        }
        return Gravity.CENTER;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
