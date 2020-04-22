package com.example.a15632.poetrydemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.a15632.poetrydemo.Entity.Poetry;
import com.example.a15632.poetrydemo.Entity.Sorts;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

public class ViewRecommendFragment extends Fragment {
    private View fragment;
    private XBanner xBanner;
    private View view;
    private ListView listView;
    private ArrayList<Poetry> rec = new ArrayList<>();
    private MyAdapter<Poetry> myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.view_recommend_fragment, container, false);
        //code begin
        initData();
        findView();
        action();


        //code end
        ViewGroup p = (ViewGroup) fragment.getParent();
        if (p != null) {
            p.removeView(fragment);
        }

        return fragment;
    }

    private void initData() {
        rec.add(new Poetry("木兰花慢", "佚名", "问花花不语，为谁开，为谁落"));
        rec.add(new Poetry("秋风词", "唐代/李白", "落叶聚还散，寒鸦栖复惊。"));
        rec.add(new Poetry("定风波", "欧阳修", "把酒花前欲问君，世间何计可留春？"));
        rec.add(new Poetry("木兰花慢", "佚名", "问花花不语，为谁开，为谁落"));
        rec.add(new Poetry("秋风词", "唐代/李白", "落叶聚还散，寒鸦栖复惊。"));
        rec.add(new Poetry("定风波", "欧阳修", "把酒花前欲问君，世间何计可留春？"));
    }


    private void findView() {
        //轮播图备份
//        xBanner=fragment.findViewById(R.id.xbanner);
//        List<LocalImageEntity> lo=new ArrayList<>();
//        lo.add(new LocalImageEntity(R.drawable.banner_1));
//        lo.add(new LocalImageEntity(R.drawable.banner_2));
//        lo.add(new LocalImageEntity(R.drawable.banner_3));
//
//        xBanner.setBannerData(lo);
//        xBanner.loadImage(new XBanner.XBannerAdapter() {
//            @Override
//            public void loadBanner(XBanner banner, Object model, View view, int position) {
//                ((ImageView)view).setImageResource(((LocalImageEntity)model).getXBannerUrl());
//            }
//        });
        listView = fragment.findViewById(R.id.listview_recommend);
    }

    private void action() {
        myAdapter = new MyAdapter<Poetry>(rec, R.layout.item_recommend) {
            @Override
            public void bindView(ViewHolder holder,
                                 Poetry obj) {
                holder.setText(R.id.tv_recommend_name, obj.getName());
                holder.setText(R.id.tv_recommend_author,obj.getAuthor());
                holder.setText(R.id.tv_recommend_content,obj.getContent());
            }
        };
        listView.setAdapter(myAdapter);
    }
}
