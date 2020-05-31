package com.example.a15632.poetrydemo.drawer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a15632.poetrydemo.Entity.Constant;
import com.example.a15632.poetrydemo.Entity.User;
import com.example.a15632.poetrydemo.MainActivity;
import com.example.a15632.poetrydemo.R;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SettingActivity extends AppCompatActivity {
    //弹窗
    private PopupWindow popupWindow = null;
    private View popupView = null;
    //头像
    private ImageView head;
    private final int REQUEST_CAMERA = 1;
    private final int REQUEST_PHOTO = 2;
    //昵称
    private TextView name;
    private String newName;
    //手机
    private Button change_phone;
    //密码
    private Button change_pwd;
    //退出
    private Button exit;

    private SharedPreferences sharedPreferences;

    //  交互
    private OkHttpClient client;

    private EditText name_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_setting);
        client = new OkHttpClient();
        find();
        listener();

    }

    private void find() {
        head = findViewById(R.id.set_head);
        name = findViewById(R.id.set_name);
        change_pwd = findViewById(R.id.set_change_pwd);
        change_phone = findViewById(R.id.set_change_phone);
        exit = findViewById(R.id.set_exit);
    }


    private void listener() {
        //head

        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null || !popupWindow.isShowing())
                    showHead();

            }
        });
        //name
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null || !popupWindow.isShowing())
                    showName();
            }
        });
        //注销
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示PopupWindow
                if (popupWindow == null || !popupWindow.isShowing())
                    showExit();
            }
        });
        //密码
        change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, PasswordChooseActivity.class);

                startActivity(intent);
            }
        });


        //手机
        change_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, CheckActivity.class);
                intent.putExtra("next", "phone");
                startActivity(intent);
            }
        });


    }

    private void showExit() {
        // 创建popupWindow对象
        popupWindow = new PopupWindow();
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 通过布局填充器创建View
        popupView = getLayoutInflater()
                .inflate(R.layout.popup_exit, null);
        // 设置PopupWindow显示的内容视图
        popupWindow.setContentView(popupView);
        // 设置PopupWindow是否能响应外部点击事件
        popupWindow.setOutsideTouchable(false);
        // 设置PopupWindow是否相应点击事件
        popupWindow.setTouchable(true);

        // 获取按钮并添加监听器

        Button btnTrue = popupView.findViewById(R.id.exit_true);
        Button btnCancel = popupView.findViewById(R.id.exit_cancel);

        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                sharedPreferences = getSharedPreferences("loginInfo",
                        MODE_PRIVATE);//只在本程序内部访问
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(getApplicationContext(), "您已成功退出登陆！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        // 在指定控件下方显示PopupWindow
        popupWindow.showAsDropDown(head);

    }

    @SuppressLint("WrongConstant")
    private void showName() {
        // 创建popupWindow对象
        popupWindow = new PopupWindow();
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 通过布局填充器创建View
        popupView = getLayoutInflater()
                .inflate(R.layout.popup_name, null);
        // 设置PopupWindow显示的内容视图
        popupWindow.setContentView(popupView);
        // 设置PopupWindow是否能响应外部点击事件
        popupWindow.setOutsideTouchable(false);
        // 设置PopupWindow是否相应点击事件
        popupWindow.setTouchable(true);

        // 获取按钮并添加监听器

        Button btnTrue = popupView.findViewById(R.id.name_true);
        Button btnCancel = popupView.findViewById(R.id.name_cancel);
        name_et = popupView.findViewById(R.id.name_et);
        //防止PopupWindow被软件盘挡住
        popupWindow.setFocusable(true);
        /*popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //这里给它设置了弹出的时间，
        imm.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);*/

        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                sharedPreferences = getSharedPreferences("loginInfo",
                        MODE_PRIVATE);//只在本程序内部访问
                newName = name_et.getText().toString();


                changeName();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        // 在指定控件下方显示PopupWindow
        popupWindow.showAsDropDown(head);

    }


    private void changeName() {
        Log.e("修改name", "运行");
        String jsonStr = null;
        int id = sharedPreferences.getInt("id", 0);
        String phone = sharedPreferences.getString("phone", "");
        String password = sharedPreferences.getString("password", "");

        Log.e("修改", newName);
        User user = new User(id, newName, password, phone);
        jsonStr = new Gson().toJson(user);

        //2.创建RequestBody请求体对象
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),
                jsonStr);
        //3.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.lcIp + "user/updateName")
                .post(body)//请求方式POST
                .build();
        //4.创建Call对象
        Call call = client.newCall(request);
        Log.e("change", "name");
        //5.发送请求(可以同步或者异步)
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonStr = response.body().string();
                Log.e("查询Name", "-" + jsonStr);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", newName);
                editor.commit();
                Toast.makeText(getApplicationContext(), "昵称修改成功！", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();

            }
        });
    }

    //头像
    private void showHead() {
        // 创建popupWindow对象
        popupWindow = new PopupWindow();
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 通过布局填充器创建View
        popupView = getLayoutInflater()
                .inflate(R.layout.popup_head, null);
        // 设置PopupWindow显示的内容视图
        popupWindow.setContentView(popupView);
        // 设置PopupWindow是否能响应外部点击事件
        popupWindow.setOutsideTouchable(false);
        // 设置PopupWindow是否相应点击事件
        popupWindow.setTouchable(true);

        // 获取按钮并添加监听器

        Button headPhoto = popupView.findViewById(R.id.head_photo);
        Button headSelect = popupView.findViewById(R.id.head_select);
        Button btnCancel = popupView.findViewById(R.id.head_cancel);

        //相机
        headPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //动态申请GPS权限
                ActivityCompat.requestPermissions(SettingActivity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);

            }
        });

        headSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册
                //动态申请GPS权限
                ActivityCompat.requestPermissions(SettingActivity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PHOTO);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        // 在指定控件下方显示PopupWindow
        popupWindow.showAsDropDown(head);

    }

    //申请权限回调方法
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//调用android自带的照相机
            Uri photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            startActivityForResult(intent, 1);

        } else if (requestCode == REQUEST_PHOTO) {
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//调用android的图库
            startActivityForResult(i, 2);
        }
    }

    //head回调
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {  // 拍照
                Bundle extras = data.getExtras();
                Bitmap photoBit = (Bitmap) extras.get("data");
                Bitmap option = BitmapOption.bitmapOption(photoBit, 5);
                head.setImageBitmap(option);
                saveBitmap2file(option, "0001.jpg");
                final File file = new File("/sdcard/" + "head.jpg");

                Log.e("TAG", "file " + file.getName());
                //开始联网上传的操作

            } else if (requestCode == 2) { // 相册
                try {
                    Uri uri = data.getData();
                    String[] pojo = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(uri, pojo, null, null, null);
                    if (cursor != null) {
                        ContentResolver cr = this.getContentResolver();
                        int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String path = cursor.getString(colunm_index);
                        final File file = new File(path);
                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        Bitmap option = BitmapOption.bitmapOption(bitmap, 5);
                        head.setImageBitmap(option);//设置为头像的背景
                        Log.e("TAG", "fiel" + file.getName());

                        //开始联网上传的操作

                    }
                } catch (Exception e) {

                }
            }
        }
    }

    static boolean saveBitmap2file(Bitmap bmp, String filename) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream("/sdcard/" + filename);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bmp.compress(format, quality, stream);
    }

   /* //弹出输入法
    private void showSoft() {
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {

            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) name_et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(name_et, 0);
            }
        }, 0);
    }*/

}
