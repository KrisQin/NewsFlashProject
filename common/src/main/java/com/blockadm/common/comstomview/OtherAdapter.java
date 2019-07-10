package com.blockadm.common.comstomview;

/**
 * Created by hp on 2019/1/26.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blockadm.common.R;
import com.blockadm.common.bean.TagBeanDto;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by fuweiwei on 2016/1/8.
 */
public class OtherAdapter extends BaseAdapter {

    private Context context;
    public List<TagBeanDto> channelList;
    private TextView item_text;
    /** 是否可见 在移动动画完毕之前不可见，动画完毕后可见*/
    boolean isVisible = true;
    /** 要删除的position */
    public int remove_position = -1;
    /** 是否是用户频道 */
    private boolean isUser = false;

    public OtherAdapter(Context context, ArrayList<TagBeanDto> channelList , boolean isUser) {
        this.context = context;
        this.channelList = channelList;
        this.isUser = isUser;
    }

    @Override
    public int getCount() {
        return channelList == null ? 0 : channelList.size();
    }

    @Override
    public TagBeanDto getItem(int position) {
        if (channelList != null && channelList.size() != 0) {
            return channelList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_mygridview_item_other, null);
        item_text = (TextView) view.findViewById(R.id.text_item);
        TagBeanDto channel = getItem(position);
        item_text.setText(channel.getName());
        if(isUser){
            if ((position == 0) || (position == 1)){
                item_text.setEnabled(false);
            }
        }
        if (!isVisible && (position == -1 + channelList.size())){
            item_text.setText("");
            item_text.setSelected(true);
            item_text.setEnabled(true);
        }
        if(remove_position == position){
            item_text.setText("");
        }
        return view;
    }

    /** 获取频道列表 */
    public List<TagBeanDto> getChannnelLst() {
        return channelList;
    }

    /** 添加频道列表 */
    public void addItem(TagBeanDto channel) {

        channelList.add(channel);
        notifyDataSetChanged();
    }

    /** 设置删除的position */
    public void setRemove(int position) {
        remove_position = position;
        notifyDataSetChanged();
        // notifyDataSetChanged();
    }

    /** 删除频道列表 */
    public void remove() {

        try{
            channelList.remove(remove_position);
            remove_position = -1;
            notifyDataSetChanged();
        }catch (Exception e){

        }

    }
    /** 设置频道列表 */
    public void setListDate(List<TagBeanDto> list) {
        channelList = list;
    }

    /** 获取是否可见 */
    public boolean isVisible() {
        return isVisible;
    }

    /** 设置是否可见 */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

}

