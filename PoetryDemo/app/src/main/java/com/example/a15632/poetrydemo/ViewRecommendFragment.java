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

import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

public class ViewRecommendFragment extends Fragment {
    private View fragment;
    private XBanner xBanner;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment=inflater.inflate(R.layout.view_recommend_fragment,container,false);
        //code begin
        findView();
        action();






        //code end
        ViewGroup p=(ViewGroup)fragment.getParent();
        if(p!=null){
            p.removeView(fragment);
        }

        return fragment;
    }


    private void findView() {
        xBanner=fragment.findViewById(R.id.xbanner);
        List<LocalImageEntity> lo=new ArrayList<>();
        lo.add(new LocalImageEntity(R.drawable.banner_1));
        lo.add(new LocalImageEntity(R.drawable.banner_2));
        lo.add(new LocalImageEntity(R.drawable.banner_3));

        xBanner.setBannerData(lo);
        xBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                ((ImageView)view).setImageResource(((LocalImageEntity)model).getXBannerUrl());
            }
        });
    }
    private void action() {
    }
}
