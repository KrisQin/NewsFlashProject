package com.blockadm.common.comstomview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.blockadm.common.R;
import com.blockadm.common.bean.NewsArticleCommentDTO;

import java.util.ArrayList;

/**
 * Created by hp on 2019/1/15.
 */

public class TagLayout   extends GridView{
    public TagLayout(Context context) {
        this(context,null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {


    }

    public void  addTag(final ArrayList<NewsArticleCommentDTO.NewsArticleBean.SysLableListBean> sysLableList  ){
        BaseAdapter baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return sysLableList.size();
            }

            @Override
            public Object getItem(int position) {
                return sysLableList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag,null);
                TextView  textView =  view.findViewById(R.id.tv_tagname);
                textView.setText(sysLableList.get(position).getSysLableName());
                return view;
            }
        };

      setAdapter(baseAdapter);
    }
}
