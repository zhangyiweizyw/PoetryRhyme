package com.example.a15632.poetrydemo;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class MyAdapter<T> extends BaseAdapter {
    public static final  Map<Integer,Boolean> map=new HashMap<>();
    //设置checkbox的相关事件
//    public class checkListning implements View.OnClickListener{
//
//        @Override
//        public void onClick(View v) {
//
//        }
//    }
    private ArrayList<T> mData;
    private int mLayoutRes;           //布局id



    public MyAdapter() {
    }

    public MyAdapter(ArrayList<T> mData, int mLayoutRes) {
        this.mData = mData;
        this.mLayoutRes = mLayoutRes;
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.bind(parent.getContext(), convertView, parent, mLayoutRes
                , position);
        bindView(holder, getItem(position));
        return holder.getItemView();
    }

    public abstract void bindView(ViewHolder holder, T obj);

    //添加一个元素
    public void add(T data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(data);
        notifyDataSetChanged();
    }

    //往特定位置，添加一个元素
    public void add(int position, T data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(position, data);
        notifyDataSetChanged();
    }

    public void remove(T data) {
        if (mData != null) {
            mData.remove(data);
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (mData != null) {
            mData.remove(position);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if (mData != null) {
            mData.clear();
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder {

        private SparseArray<View> mViews;   //存储ListView 的 item中的View
        private View item;                  //存放convertView
        private int position;               //游标
        private Context context;            //Context上下文

//        CheckBox checkBox=item.findViewById(R.id.item_checkbox);

        public class checkListning implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    map.put(position,true);
                }else {
                    map.remove(position);
                }
            }
        }







        //构造方法，完成相关初始化
        private ViewHolder(Context context, ViewGroup parent, int layoutRes) {
            mViews = new SparseArray<>();
            this.context = context;
            View convertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
            convertView.setTag(this);
            item = convertView;
        }

        //绑定ViewHolder与item
        public static ViewHolder bind(Context context, View convertView, ViewGroup parent,
                                      int layoutRes, int position) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder(context, parent, layoutRes);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.item = convertView;
            }
            holder.position = position;
            return holder;
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int id) {
            T t = (T) mViews.get(id);
            if (t == null) {
                t = (T) item.findViewById(id);
                mViews.put(id, t);
            }
            return t;
        }


        /**
         * 获取当前条目
         */
        public View getItemView() {
            return item;
        }

        /**
         * 获取条目位置
         */
        public int getItemPosition() {
            return position;
        }

        /**
         * 设置文字
         */
        public ViewHolder setText(int id, CharSequence text) {
            View view = getView(id);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
            return this;
        }

        /**
         * 设置不可见
         */
        public ViewHolder setVisonNoSee(int id) {
            View view = getView(id);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setVisibility(View.INVISIBLE);
            }
            return this;
        }
        /**
         * 设置可见
         */
        public ViewHolder setVisonCanSee(int id) {
            View view = getView(id);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setVisibility(View.VISIBLE);
            }
            return this;
        }

        /**
         *设置checkbox的事件
         */
        public ViewHolder setCheck(int id) {
            View view = getView(id);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setOnClickListener(new checkListning());
                if(map!=null&&map.containsKey(position)){
                    ((CheckBox) view).setChecked(true);
                }else{
                    ((CheckBox) view).setChecked(false);
                }
            }
            return this;
        }

        /**
         * 设置date文字，然后根据情况设置背景颜色
         * 2020年2月1日 发现又bug 待修改
         */
        public ViewHolder setTextByDate(int id, int layoutid,CharSequence text) {
            //获得今日日期
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date dates=new Date(System.currentTimeMillis());
            String dateData;//要传入服务端的日期的数据
            dateData=simpleDateFormat.format(dates);

            View view = getView(id);
            View view1=getView(layoutid);

            if (view instanceof TextView) {
                ((TextView) view).setText(text);
                if(text.equals(dateData)){//如果日期是今日的话，设置不一样的背景颜色
                    view1.setBackgroundColor(Color.RED);//暂且设为红色
                }
            }
            return this;
        }
        /**
         * 设置图片
         */
        public ViewHolder setImageResource(int id, int drawableRes) {
            View view = getView(id);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(drawableRes);
            } else {
                view.setBackgroundResource(drawableRes);
            }
            return this;
        }

        /**
         * 设置点击监听
         */
        public ViewHolder setOnClickListener(int id, View.OnClickListener listener) {
            getView(id).setOnClickListener(listener);
            return this;
        }

        /**
         * 设置可见
         */
        public ViewHolder setVisibility(int id, int visible) {
            getView(id).setVisibility(visible);
            return this;
        }

        /**
         * 设置标签
         */
        public ViewHolder setTag(int id, Object obj) {
            getView(id).setTag(obj);
            return this;
        }

        //其他方法可自行扩展

    }

}
