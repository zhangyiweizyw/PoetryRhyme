package com.example.a15632.poetrydemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.a15632.poetrydemo.Entity.Sorts;

import java.util.ArrayList;

public class ViewSortFragment extends Fragment {
    private GridView gv1;
    private GridView gv2;
    private GridView gv3;
    private View fragment;
    private ArrayList<Sorts> sorts_person=new ArrayList<>();
    private ArrayList<Sorts> sorts_time=new ArrayList<>();
    private ArrayList<Sorts> sorts_theme=new ArrayList<>();
    private MyAdapter<Sorts> myAdapter1;
    private MyAdapter<Sorts> myAdapter2;
    private MyAdapter<Sorts> myAdapter3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment=inflater.inflate(R.layout.view_sort_fragment,container,false);
        //code begin
        initData();
        findView();
        action();





        //code end
        ViewGroup p=(ViewGroup)fragment.getParent();
        if(p!=null){
            p.removeView(fragment);
        }

        return fragment;
    }


    private void initData() {
        sorts_person.add(new Sorts(R.drawable.avatar_unlogin,"人物1"));
        sorts_person.add(new Sorts(R.drawable.avatar_unlogin,"人物2"));
        sorts_person.add(new Sorts(R.drawable.avatar_unlogin,"人物3"));
        sorts_person.add(new Sorts(R.drawable.avatar_unlogin,"人物4"));

        sorts_time.add(new Sorts(R.drawable.avatar_unlogin,"时代1"));
        sorts_time.add(new Sorts(R.drawable.avatar_unlogin,"时代2"));
        sorts_time.add(new Sorts(R.drawable.avatar_unlogin,"时代3"));
        sorts_time.add(new Sorts(R.drawable.avatar_unlogin,"时代4"));

        sorts_theme.add(new Sorts(R.drawable.avatar_unlogin,"题材1"));
        sorts_theme.add(new Sorts(R.drawable.avatar_unlogin,"题材2"));
        sorts_theme.add(new Sorts(R.drawable.avatar_unlogin,"题材3"));
        sorts_theme.add(new Sorts(R.drawable.avatar_unlogin,"题材4"));
    }

    private void findView() {
        gv1=fragment.findViewById(R.id.gv_sort_by_person);
        gv2=fragment.findViewById(R.id.gv_sort_by_time);
        gv3=fragment.findViewById(R.id.gv_sort_by_theme);
    }
    private void action() {
        //1
        myAdapter1=new MyAdapter<Sorts>(sorts_person,R.layout.item_sort) {
            @Override
            public void bindView(ViewHolder holder, Sorts obj) {
                holder.setImageResource(R.id.item_sorts_img,obj.getImg());
                holder.setText(R.id.item_sorts_title,obj.getTitle());
            }
        };
        gv1.setAdapter(myAdapter1);
        //2
        myAdapter2=new MyAdapter<Sorts>(sorts_time,R.layout.item_sort) {
            @Override
            public void bindView(ViewHolder holder, Sorts obj) {
                holder.setImageResource(R.id.item_sorts_img,obj.getImg());
                holder.setText(R.id.item_sorts_title,obj.getTitle());
            }
        };
        gv2.setAdapter(myAdapter2);
        //3
        myAdapter3=new MyAdapter<Sorts>(sorts_theme,R.layout.item_sort) {
            @Override
            public void bindView(ViewHolder holder, Sorts obj) {
                holder.setImageResource(R.id.item_sorts_img,obj.getImg());
                holder.setText(R.id.item_sorts_title,obj.getTitle());
            }
        };
        gv3.setAdapter(myAdapter3);

    }

}
