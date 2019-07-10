package com.blockadm.adm.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.InComeDto;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ChartUtil;
import com.blockadm.common.utils.StringUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/2/16.
 */

public class MyInComeActivty extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.tv_total_point)
    TextView tvTotalPoint;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_yes_point)
    TextView tvYesPoint;
    @BindView(R.id.tv_yes_price)
    TextView tvYesPrice;
    @BindView(R.id.tv_today_point)
    TextView tvTodayPoint;
    @BindView(R.id.tv_today_price)
    TextView tvTodayPrice;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.lineChart)
    LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_income);
        ButterKnife.bind(this);


        CommonModel.myEarnedUserAccount(new MyObserver<InComeDto>() {
            @Override
            public void onSuccess(BaseResponse<InComeDto> t) {
                List<String> xDataList = new ArrayList<>();// x轴数据源
                List<Entry> yDataList = new ArrayList<>();// y轴数据数据源
                  //给上面的X、Y轴数据源做假数据测试
                for (int i = 0; i < t.getData().getSevenDataList().size(); i++) {
                    // x轴显示的数据
                    xDataList.add("0"+(i+1));
                    //y轴生成float类型的随机数
                   int   earnedSum = t.getData().getSevenDataList().get(i).getEarnedSum();
                 yDataList.add(new Entry(earnedSum, i));
                }
                 //显示图表,参数（ 上下文，图表对象， X轴数据，Y轴数据，图表标题，曲线图例名称，坐标点击弹出提示框中数字单位）
                ChartUtil.showChart(MyInComeActivty.this, lineChart, xDataList, yDataList, "", "","A点");
                initView(t.getData());
            }
        });
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView(InComeDto data) {
        if (data!=null){
            tvTotalPoint.setText(data.getAllEarned()+"   A点");
            tvTotalPrice.setText("¥ "+ StringUtils.amountTwoZero(data.getAllEarned() * 1.0/100+""));
            tvYesPoint.setText(data.getYesterdayEarned()+"   A点");
            tvYesPrice.setText("¥ "+StringUtils.amountTwoZero(data.getYesterdayEarned() * 1.0/100+""));
            tvTodayPoint.setText(data.getDayEarned()+"   A点");
            tvTodayPrice.setText("¥ "+StringUtils.amountTwoZero(data.getDayEarned() * 1.0/100+""));
            tvNum.setText(data.getDaySize()+"");
        }
    }

}
