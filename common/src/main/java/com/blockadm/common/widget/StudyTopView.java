package com.blockadm.common.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockadm.common.R;
import com.blockadm.common.bean.AllListDto;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.StringUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kris on 2019/6/27
 *
 * @Describe TODO {  }
 */
public class StudyTopView {

    private ArrayList<AllListDto> mAllListDtos = new ArrayList<>();
    private Context mContext;
    private LinearLayout layout;

    public StudyTopView(Context context, ArrayList<AllListDto> allListDtos, LinearLayout layout) {
        if (this.mAllListDtos == null) {
            this.mAllListDtos = new ArrayList<>();
        } else {
            this.mAllListDtos.clear();
        }

        if (allListDtos != null) {
            this.mAllListDtos.addAll(allListDtos);
        }

        this.mContext = context;
        this.layout = layout;

    }

    public void notifyDataSetChanged() {
        if (layout == null) {
            return;
        }

        layout.removeAllViews();
        if (!mAllListDtos.isEmpty()) {
            for (int i = 0; i < mAllListDtos.size(); i++) {
                AllListDto allListDto = mAllListDtos.get(i);
                String title = allListDto.getNavigationTypeName();
                View mTitleView = LayoutInflater.from(mContext).inflate(R.layout.item_study_layout_title, null);
                TextView mTv_title = mTitleView.findViewById(R.id.tv_title);
                mTv_title.setText(title);
                layout.addView(mTitleView);

                AllListDto.PageListBean  pageListBean = allListDto.getPageList();
                if (pageListBean != null) {
                    List<AllListDto.PageListBean.RecordsBean> records = pageListBean.getRecords();
                    if (records != null) {
                        for (int j = 0; j < records.size(); j++) {
                            View contentView = LayoutInflater.from(mContext).inflate(R.layout.item_study_layout_content, null);
                            final AllListDto.PageListBean.RecordsBean recordsBean = records.get(j);
                            showData(contentView,recordsBean,allListDto.getObjectType());
                            
                            contentView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clickListener.callback(recordsBean);
                                }
                            });

                            layout.addView(contentView);
                        }
                    }
                }
            }
        }
    }
    
    private ClickItemListener clickListener;
    public void setOnItemClickListener(ClickItemListener clickListener) {
        this.clickListener = clickListener;
    }
    
    public interface ClickItemListener {
        void callback(AllListDto.PageListBean.RecordsBean recordsBean);
    }

    private void showData(View contentView, AllListDto.PageListBean.RecordsBean bean,int objectType) {
        ImageView ivImage = contentView.findViewById(R.id.iv_image);
        TextView tvTitle1 = contentView.findViewById(R.id.tv_title1);
        TextView tvLiulan = contentView.findViewById(R.id.tv_liulan);
        TextView tvPrice = contentView.findViewById(R.id.tv_price);
        TextView tvAuthor = contentView.findViewById(R.id.tv_author);
        TextView tvSchedule = contentView.findViewById(R.id.tv_schedule);
        TextView tvMemberPrice = contentView.findViewById(R.id.tv_member_price);
        View viewLine = contentView.findViewById(R.id.view_line);

        tvTitle1.setText(bean.getTitle());
        tvLiulan.setText("" + bean.getConvertReadCount());

        /*
（1）、免费 ：  memberId【创建者】 = memberId 【登录账户】 或者    accessStatus=0

（2）、会员免费 ： accessStatus=1 并且 vipMoney =0

（3）、付费情况 :   accessStatus=1 并且 vipMoney >0
        * */
        if (bean.getAccessStatus() == 0) {
            viewLine.setVisibility(View.GONE);
            tvPrice.setText("免费");
        } else if (bean.getAccessStatus() == 1 && bean.getVipMoney() == 0) {
            viewLine.setVisibility(View.VISIBLE);
            tvPrice.setText(StringUtils.getUnitAmount()+bean.getMoney());
            tvMemberPrice.setText("会员免费");
        } else {
            viewLine.setVisibility(View.VISIBLE);
            tvPrice.setText(StringUtils.getUnitAmount()+bean.getMoney() );
            tvMemberPrice.setText("会员价:" + bean.getVipMoney() + "");
        }


        tvAuthor.setText(bean.getNickName());
        if (objectType == 0) {
            tvSchedule.setText("更新至 " + bean.getFinishLessonsCount() + "节");
            tvSchedule.setVisibility(View.VISIBLE);
        } else {
            tvSchedule.setVisibility(View.GONE);
        }


        Glide.with(mContext).load(bean.getCoverImgs())
                .transform(new ImageUtils.GlideRoundTransform(mContext, 3)).into(ivImage);
        
    }

}
