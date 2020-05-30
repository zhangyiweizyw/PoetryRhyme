package com.example.a15632.poetrydemo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a15632.poetrydemo.Entity.Constant;
import com.example.a15632.poetrydemo.Entity.Poetry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stx.xhb.xbanner.XBanner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ViewRecommendFragment extends Fragment {
    private View fragment;
    private View view;
    private ListView listView;
    private ArrayList<Poetry> rec = new ArrayList<>();
    private MyAdapter<Poetry> myAdapter;
    //  交互
    private OkHttpClient client;
    //weather
    private final int REQUEST_GPS = 1;
    private LinearLayout layout;
    private String search_weather = "";
    //day
    private TextView tv_tianqi;
    private TextView tv_day;
    private TextView tv_moon;
    private TextView poetry_one;
    private TextView poetry_two;
    private int MOON;
    private int DAY;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.view_recommend_fragment, container, false);
        //code begin
        client = new OkHttpClient();
        findView();
        //weather
        HeConfig.init("HE2005212341181595", "f4cbb7844d664d12b8f4149ea875ad57");
        HeConfig.switchToFreeServerNode();

        //day
        Date date = new Date();
        MOON = date.getMonth() + 1;
        DAY = date.getDate();
        tv_moon.setText(MOON + "");
        tv_day.setText(DAY + "");

        //动态申请GPS权限
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_GPS);

        getWeather();
        initData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PoemDetail.class);
                intent.putExtra("poem", rec.get(position));
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
        getAsync();
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("天气诗句","开始");
                Log.e("天气诗句",search_weather);

                search();

            }
        }, 1000);*/
        search();
        /*rec.add(new Poetry("木兰花慢", "佚名", "问花花不语，为谁开，为谁落。","问花花不说话，为谁零落为谁而开。就算有三分春色，一半已随水流去，一半化为尘埃。人生能有多少欢笑，故友相逢举杯畅饮却莫辞推。整个春天翠围绿绕，繁花似锦把天地遮盖。\n" +
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
        rec.add(new Poetry("定风波", "欧阳修", "把酒花前欲问君，世间何计可留春？","暂无。"));*/
    }


    //GET
    private void getAsync() {
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.lcIp + "poetry/get")//设置网络请求的URL地址
                .get()
                .build();
        //3.创建Call对象
        Call call = client.newCall(request);
        //4.发送请求
        //异步请求，不需要创建线程
        call.enqueue(new Callback() {
            @Override
            //请求失败时回调
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {
                //不能直接更新界面
//                Log.e("异步GET请求结果",response.body().string());
                String jsonStr = response.body().string();
                Log.e("结果", "-" + jsonStr);

                /* User msg = new Gson().fromJson(jsonStr, User.class);*/
                List<Poetry> list = new Gson().fromJson(jsonStr, new TypeToken<List<Poetry>>() {
                }.getType());


                for (Poetry poetry : list) {
                    Log.e("得到的诗句", poetry.toString());
                    poetry.setTranslate("我是译文");
                    rec.add(poetry);
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        action();
                    }
                });

//                response.close();


            }
        });
    }

    private void findView() {
        listView = fragment.findViewById(R.id.listview_recommend);
        layout = fragment.findViewById(R.id.weather_img);
        tv_day = fragment.findViewById(R.id.tv_day);
        tv_moon = fragment.findViewById(R.id.tv_moon);
        poetry_one = fragment.findViewById(R.id.tv_poetry_everyday);
        poetry_two = fragment.findViewById(R.id.tv_poetry_everyday2);
        tv_tianqi = fragment.findViewById(R.id.tv_tianqi);
    }

    private void action() {
        myAdapter = new MyAdapter<Poetry>(rec, R.layout.item_recommend) {
            @Override
            public void bindView(ViewHolder holder,
                                 Poetry obj) {
                holder.setText(R.id.tv_recommend_name, obj.getName());
                holder.setText(R.id.tv_recommend_author, obj.getAuthor());
                holder.setText(R.id.tv_recommend_content, obj.getContent());
            }
        };
        listView.setAdapter(myAdapter);
    }

    //申请权限回调方法
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_GPS) {
            getWeather();

        }
    }

    //获取天气
    public void getWeather() {
        HeWeather.getWeatherNow(getActivity(), Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherNowBeanListener() {
            public static final String TAG = "he_feng_now";

            @Override
            public void onError(Throwable e) {
                if (MOON >= 3 && MOON <= 5) {
                    layout.setBackgroundResource(R.drawable.spring);
                    search_weather = "春";
                } else if (MOON >= 6 && MOON <= 8) {
                    layout.setBackgroundResource(R.drawable.summer);
                    search_weather = "夏";

                } else if (MOON >= 9 && MOON <= 11) {
                    layout.setBackgroundResource(R.drawable.autumn);
                    search_weather = "秋";

                } else {
                    layout.setBackgroundResource(R.drawable.winter);
                    search_weather = "冬";
                }
                Log.e(TAG, "onError: ", e);
                System.out.println("Weather Now Error:" + new Gson());

            }

            @Override
            public void onSuccess(Now dataObject) {
                Log.e(TAG, " Weather Now onSuccess: " + new Gson().toJson(dataObject));
                String jsonData = new Gson().toJson(dataObject);
                String tianqi = null, wendu = null, tianqicode = null;
                if (dataObject.getStatus().equals("ok")) {
                    String JsonNow = new Gson().toJson(dataObject.getNow());
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(JsonNow);
                        tianqi = jsonObject.getString("cond_txt");
                        Log.e("tinaqi", tianqi);
                        wendu = jsonObject.getString("tmp");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    tv_tianqi.setText(tianqi);

                    if (tianqi.equals("晴")) {
                        layout.setBackgroundResource(R.drawable.sunny);
                        search_weather = "晴";
                    } else if (tianqi.contains("云")) {
                        layout.setBackgroundResource(R.drawable.duoyun);
                        search_weather = "云";
                        Log.e("weather_str",search_weather);
                    } else if (tianqi.equals("阴")) {
                        layout.setBackgroundResource(R.drawable.cloudy);
                        search_weather = "陰";
                    } else if (tianqi.contains("雪")) {
                        layout.setBackgroundResource(R.drawable.snow);
                        search_weather = "雪";
                    } else if (tianqi.contains("雨")) {
                        layout.setBackgroundResource(R.drawable.rain);
                        search_weather = "雨";
                    } else {
                        if (MOON >= 3 && MOON <= 5) {
                            layout.setBackgroundResource(R.drawable.spring);
                            search_weather = "春";
                        } else if (MOON >= 6 && MOON <= 8) {
                            layout.setBackgroundResource(R.drawable.summer);
                            search_weather = "夏";

                        } else if (MOON >= 9 && MOON <= 11) {
                            layout.setBackgroundResource(R.drawable.autumn);
                            search_weather = "秋";

                        } else {
                            layout.setBackgroundResource(R.drawable.winter);
                            search_weather = "冬";
                        }


                    }

                } else {
                    Toast.makeText(getActivity(), "有错误", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    //weatherPoetry
    //search
    private void search() {

        Log.e("天气诗句开启", "运行");
        String jsonStr = null;
        Log.e("天气诗句查找", search_weather);
        jsonStr = new Gson().toJson(search_weather);

        //2.创建RequestBody请求体对象
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),
                jsonStr);
        //3.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.lcIp + "poetry/weather")
                .post(body)//请求方式POST
                .build();
        //4.创建Call对象
        Call call = client.newCall(request);
        Log.e("天气诗句", "运行前");
        //5.发送请求(可以同步或者异步)
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonStr = response.body().string();
                Log.e("查询的诗句", "-" + jsonStr);
                final Poetry weaPoetry = new Gson().fromJson(jsonStr, Poetry.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] strs = weaPoetry.getContent().split("。");
                        String[] poes = strs[0].split("，");
                        poetry_one.setText(poes[0]);
                        poetry_two.setText(poes[1]);
                    }
                });


            }
        });
    }
}
