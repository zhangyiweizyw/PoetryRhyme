package com.example.a15632.poetrydemo;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

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
                Log.e("title",tv.getText().toString());
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
           /* viewHolder.likecount=convertView.findViewById(R.id.likecount);
            viewHolder.commentcount=convertView.findViewById(R.id.commentcount);*/
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

       /* viewHolder.likecount.setText(communityList.get(position).getLikecount()+"");
        viewHolder.commentcount.setText(communityList.get(position).getCommentcount()+"");*/
        viewHolder.seecount.setText(communityList.get(position).getSeecount()+"");
        viewHolder.tvusername.setText(userList.get(position).getUsername());
        String imageUrl=userList.get(position).getHeadimg();
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_headimg)//请求过程中显示
                .error(R.drawable.default_headimg)//请求失败显示
                .fallback(R.drawable.default_headimg)//请求的URL为null时显示
                .circleCrop();//圆形剪裁
        Glide.with(mContext)
                .load(imageUrl)
                .apply(options)//应用请求选项
                .into(viewHolder.userhead);


        return convertView;
    }

    private class ViewHolder{

        public TextView tvtitle;
        public TextView tvcontent;
        public TextView tvusername;
        public TextView tvtime;
        /* public TextView likecount;
         public TextView commentcount;*/
        public TextView seecount;
        public ImageView userhead;
        public TextView c_type;
    }
}

