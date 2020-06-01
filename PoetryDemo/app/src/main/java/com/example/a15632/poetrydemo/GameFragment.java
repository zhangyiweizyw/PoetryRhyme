package com.example.a15632.poetrydemo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.a15632.poetrydemo.Entity.GamePaiHangBang;

import java.util.ArrayList;

public class GameFragment extends Fragment {
    private View fragment;
    private Button intoGame;
    private Button intoDoubleGame;
//    private LinearLayout paihang;
    private ImageView imgclose;
    private ListView paihangbanglv;
    private ArrayList<GamePaiHangBang> gamePaiHangBangs=new ArrayList<>();
    private MyAdapter<GamePaiHangBang> myAdapterGame;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragment=inflater.inflate(R.layout.tab_game_layout,container,false);

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
        intoGame=fragment.findViewById(R.id.btn_into_game);
        intoDoubleGame=fragment.findViewById(R.id.btn_into_double_game);

    }

    private void action(){
        //点击跳转事件
        intoGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),GameActivity.class);
               startActivity(intent);
            }
        });
        intoDoubleGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),DoublePlayActivity.class));
            }
        });

    }
    public void showPaiHangDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View view=getLayoutInflater().inflate(R.layout.dialog_game_paihangbang,null);
        builder.setView(view);
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();

        initPaiHangBangData();
        imgclose=view.findViewById(R.id.img_paihang_back);
        paihangbanglv=view.findViewById(R.id.lv_game_paihangbang);
        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
       //listview
        myAdapterGame=new MyAdapter<GamePaiHangBang>(gamePaiHangBangs,R.layout.item_game_paihangbang) {
            @Override
            public void bindView(ViewHolder holder, GamePaiHangBang obj) {
                holder.setText(R.id.tv_item_gamer_name,obj.getName());
                holder.setText(R.id.tv_item_gamer_score,obj.getScore()+"");
                holder.setImageResource(R.id.img_item_gamer_avator,obj.getGameimg());
            }
        };
        paihangbanglv.setAdapter(myAdapterGame);

    }
    private void initPaiHangBangData(){
        gamePaiHangBangs.add(new GamePaiHangBang(1,"张三",R.drawable.avatar_unlogin,20));
        gamePaiHangBangs.add(new GamePaiHangBang(2,"李四",R.drawable.avatar_unlogin,30));
        gamePaiHangBangs.add(new GamePaiHangBang(3,"王五",R.drawable.avatar_unlogin,40));
    }
}
