package com.example.a15632.poetrydemo;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a15632.poetrydemo.Entity.Community;
import com.example.a15632.poetrydemo.Entity.User;

import java.util.ArrayList;
import java.util.List;

public class CommunityAdapter extends BaseAdapter {
    private List<Community> communityList = new ArrayList<>();
    private List<User>userList=new ArrayList<>();
    private int itemResId;
    private Context mContext;
    private int type=1;

    public CommunityAdapter(Context context, List<Community> communities,List<User>userList, int itemResId,int type){
        this.mContext = context;
        this.communityList = communities;
        this.userList=userList;
        this.itemResId = itemResId;
        this.type=type;
    }

    @Override
    public int getCount() {
        if (null != communityList){
            return communityList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != communityList){
            return communityList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(itemResId,null);
            viewHolder = new ViewHolder();
            if(type==2){
                TextView tv=(TextView) convertView.findViewById(R.id.tv_title);
                TextView tv_type=(TextView) convertView.findViewById(R.id.c_type);
                LinearLayout lv=(LinearLayout)convertView.findViewById(R.id.middle);
                lv.setTop(20);
                tv.setGravity(Gravity.CENTER);
                tv_type.setText("原创诗词");
            }
            else{
                TextView tv_type=(TextView) convertView.findViewById(R.id.c_type);
                tv_type.setText("社区话题");
            }
            viewHolder.tvtitle=convertView.findViewById(R.id.tv_title);
            viewHolder.tvcontent=convertView.findViewById(R.id.tv_content);
            viewHolder.tvtime=convertView.findViewById(R.id.tv_time);
            viewHolder.seecount=convertView.findViewById(R.id.seecount);
            viewHolder.tvusername=convertView.findViewById(R.id.username);
            viewHolder.userhead=convertView.findViewById(R.id.iv_userhead);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvtitle.setText(communityList.get(position).getTitle());
        viewHolder.tvcontent.setText(communityList.get(position).getContent());
        viewHolder.tvtime.setText(communityList.get(position).getTime().toString());
        viewHolder.seecount.setText(communityList.get(position).getSeecount()+"");
        viewHolder.tvusername.setText(userList.get(position).getUsername());
        viewHolder.userhead.setImageResource(userList.get(position).getHeadimg());
        return convertView;
    }

    private class ViewHolder{
        public TextView tvtitle;
        public TextView tvcontent;
        public TextView tvusername;
        public TextView tvtime;
        public TextView seecount;
        public ImageView userhead;
        public TextView c_type;
    }
}

