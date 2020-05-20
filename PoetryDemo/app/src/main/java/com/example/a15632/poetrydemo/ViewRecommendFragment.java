package com.example.a15632.poetrydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),PoemDetail.class);
                intent.putExtra("poem",rec.get(position));
                startActivity(intent);
            }
        });


        //code end
        ViewGroup p = (ViewGroup) fragment.getParent();
        if (p != null) {
            p.removeView(fragment);
        }

        return fragment;
    }

    private void initData() {
        rec.add(new Poetry("木兰花慢", "佚名", "问花花不语，为谁开，为谁落。","问花花不说话，为谁零落为谁而开。就算有三分春色，一半已随水流去，一半化为尘埃。人生能有多少欢笑，故友相逢举杯畅饮却莫辞推。整个春天翠围绿绕，繁花似锦把天地遮盖。\n" +
                "回首高台烟树隔断昏暗一片。不见美人踪影令人更伤情怀。拚命畅饮挽留春光却挽留不住，乘着酒醉春天又偷偷离开。" +
                "西楼斜帘半卷夕阳映照，奇怪的是燕子衔着花片仿佛把春带来。刚刚在枕上做着欢乐的美梦，却又让无情的风雨声破坏。"));
        rec.add(new Poetry("秋风词", "唐代/李白", "落叶聚还散，寒鸦栖复惊。","秋风凄清，秋月明朗。" +
                "风中的落叶时而聚集时而扬散，寒鸦本已栖息也被这声响惊起。" +
                "盼着你我能再相见，却不知在什么时候，此时此刻实在难耐心中的孤独悲伤，叫我情何以堪。" +
                "如果有人也这么思念过一个人，就知道这种相思之苦。" +
                "永远的相思永远的回忆，短暂的相思却也无止境，" +
                "早知相思如此的在心中牵绊，不如当初就不要相识。"));
        rec.add(new Poetry("定风波", "欧阳修", "把酒花前欲问君，世间何计可留春？","我面对着花举着酒杯发问：世界上有什么办法能留住春天呢？纵然留得住年轻的岁度月，也不过是自欺欺人罢了，不懂感情的花尚要凋谢，何况为情所绊的人呢。\n" +
                "从古至今美人能有被珍专惜多久！今年的春天难得来到，应该好好珍惜。要知道花朵不会鲜艳很久。等到我由醉转醒时，你已经不在了。对你的爱恋思念只能随风而逝，随水而流。"));
        rec.add(new Poetry("木兰花慢", "佚名", "问花花不语，为谁开，为谁落。","暂无。"));
        rec.add(new Poetry("秋风词", "唐代/李白", "落叶聚还散，寒鸦栖复惊。","暂无。"));
        rec.add(new Poetry("定风波", "欧阳修", "把酒花前欲问君，世间何计可留春？","暂无。"));
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
