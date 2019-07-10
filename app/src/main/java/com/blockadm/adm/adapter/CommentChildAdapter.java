package com.blockadm.adm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.bean.CommentReplyListBean;
import com.blockadm.common.utils.StringInterceptionUtil;

import java.util.List;


/**
 * Created by hp on 2019/1/15.
 */

public class CommentChildAdapter extends BaseAdapter{

    private List<CommentReplyListBean>  commentReplyList;

    private Context context;
    public CommentChildAdapter(List<CommentReplyListBean> commentReplyList) {

        this.commentReplyList = commentReplyList;
    }

    @Override
    public int getCount() {
        if (commentReplyList!=null){
            if (commentReplyList.size()>3){
                return 3;
            }else{
                return commentReplyList.size();
            }

        }
        return 0;
    }

    @Override
    public CommentReplyListBean getItem(int position) {
        return commentReplyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context =parent.getContext();
        CommentReplyListBean  commentReplyListBean  =   getItem(position);
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_child,null);
       // TextView tvNickName = view.findViewById(R.id.tv_nickName);
        TextView tvContent = view.findViewById(R.id.tv_content);

        //TextView tvBackName = view.findViewById(R.id.tv_back_name);
       if (commentReplyListBean.getCommentType()==0){
         //  tvNickName.setText(commentReplyListBean.getName());
           tvContent.setText(commentReplyListBean.getNickName()+"："+commentReplyListBean.getContent());
       }else{
         //  tvNickName.setText(commentReplyListBean.getName());
           CharSequence text  = StringInterceptionUtil.matcherSearchText(context.getResources()
                   .getColor(R.color.color_FF507DAF),commentReplyListBean.getNickName()
                   +"：回复@"+commentReplyListBean.getMasterNickName()+"："+commentReplyListBean.getContent(),commentReplyListBean.getMasterNickName());
           tvContent.setText(text);
           //tvContent.setText("：回复@"+commentReplyListBean.getMasterNickName()+"："+commentReplyListBean.getContent());
       }

        return view;
    }
}
