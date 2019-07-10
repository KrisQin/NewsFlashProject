package com.blockadm.adm.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView;

/**
 * Created by Kris on 2019/6/19
 *
 * @Describe TODO {  }
 */
public class MySimplePagerTitleView  extends TextView implements IMeasurablePagerTitleView {
    protected int mSelectedColor;
    protected int mNormalColor;

    public MySimplePagerTitleView(Context context) {
        super(context, (AttributeSet)null);
        this.init(context);
    }

    private void init(Context context) {
        this.setGravity(17);
        int padding = UIUtil.dip2px(context, 10.0D);
        this.setPadding(padding, 0, padding, 0);
        this.setSingleLine();
        this.setEllipsize(TruncateAt.END);
    }

    public void onSelected(int index, int totalCount) {
        this.setTextColor(this.mSelectedColor);
    }

    public void onDeselected(int index, int totalCount) {
        this.setTextColor(this.mNormalColor);
    }

    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
    }

    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
    }

    public int getContentLeft() {
        Rect bound = new Rect();
        this.getPaint().getTextBounds(this.getText().toString(), 0, this.getText().length(), bound);
        int contentWidth = bound.width();
        return this.getLeft() + this.getWidth() / 2 - contentWidth / 2;
    }

    public int getContentTop() {
        FontMetrics metrics = this.getPaint().getFontMetrics();
        float contentHeight = metrics.bottom - metrics.top;
        return (int)((float)(this.getHeight() / 2) - contentHeight / 2.0F);
    }

    public int getContentRight() {
        Rect bound = new Rect();
        this.getPaint().getTextBounds(this.getText().toString(), 0, this.getText().length(), bound);
        int contentWidth = bound.width();
        return this.getLeft() + this.getWidth() / 2 + contentWidth / 2;
    }

    public int getContentBottom() {
        FontMetrics metrics = this.getPaint().getFontMetrics();
        float contentHeight = metrics.bottom - metrics.top;
        return (int)((float)(this.getHeight() / 2) + contentHeight / 2.0F);
    }

    public int getSelectedColor() {
        return this.mSelectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.mSelectedColor = selectedColor;
    }

    public int getNormalColor() {
        return this.mNormalColor;
    }

    public void setNormalColor(int normalColor) {
        this.mNormalColor = normalColor;
    }
}

