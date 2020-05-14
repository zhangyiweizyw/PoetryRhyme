package com.example.a15632.poetrydemo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.AlertDialog;

import com.example.a15632.poetrydemo.util.ImageUtils;
import com.example.a15632.poetrydemo.util.ScreenUtils;
import com.jaeger.library.StatusBarUtil;

import java.io.FileNotFoundException;

public class AddCommunity extends AppCompatActivity {

    private EditText title;
    private  EditText content;
    private ImageView picture;
    private TextView tv_type;
    private ImageView iv_send;
    private ImageView iv_delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.addcommunity);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorback));

        findViews();

        Intent intent=this.getIntent();
        int type=intent.getIntExtra("type",0);
        if(type==1){
            tv_type.setText("原创诗词");
        }
        else if(type==2){
            tv_type.setText("社区话题");
        }
        else{
            tv_type.setText("发布类型");
        }

        MyListener myListener=new MyListener();
        picture.setOnClickListener(myListener);
        iv_delete.setOnClickListener(myListener);
        iv_send.setOnClickListener(myListener);
    }

    private void findViews(){
        title=findViewById(R.id.e_title);
        content=findViewById(R.id.e_content);
        picture=findViewById(R.id.iv_image);
        tv_type=findViewById(R.id.tv_type);
        iv_send=findViewById(R.id.iv_send);
        iv_delete=findViewById(R.id.iv_back);
    }


    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.iv_image:
                    ActivityCompat.requestPermissions(AddCommunity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                    break;
                case R.id.iv_back:
                    new AlertDialog.Builder(AddCommunity.this)
                            .setTitle("您确定退出发布吗?")
                            .setMessage("您的发布将保存，可在我的草稿内进行查看。")
                            .setPositiveButton("退出发布",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    finish();
                                }
                            })
                            .setNegativeButton("继续发布", null)
                            .show();
                    break;
                case R.id.iv_send:
                    new AlertDialog.Builder(AddCommunity.this)
                            .setTitle("您确定发布吗？")
                            .setPositiveButton("确定发布",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    finish();
                                }
                            })
                            .setNegativeButton("返回修改", null)
                            .show();

            }
        }
    }

    //用户允许权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,200);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bm = null;
        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();
        if (requestCode == 200 && resultCode == RESULT_OK) {
            try{
                // 获得图片的uri
                Uri originalUri = data.getData();
                //bm = MediaStore.Images.Media.getBitmap(resolver,originalUri);
                // String[] proj = {MediaStore.Images.Media.DATA};
                // 好像是android多媒体数据库的封装接口，具体的看Android文档
                // Cursor cursor = managedQuery(originalUri,proj,null,null,null);
                Cursor cursor = getContentResolver().query(originalUri, null, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if(cursor.moveToFirst()){
                    String path = cursor.getString(cursor.getColumnIndex("_data"));
                    insertImg(path);
                }
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(AddCommunity.this,"图片插入失败",Toast.LENGTH_SHORT).show();
            }


        }

    }

    private void insertImg(String path){
        String tagPath = "<img src=\""+path+"\"/>";//为图片路径加上<img>标签
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if(bitmap != null){
            SpannableString ss = getBitmapMime(path,tagPath);
            insertPhotoToEditText(ss);
            content.append("\n");
        }
    }
    //endregion

    //region 将图片插入到EditText中
    private void insertPhotoToEditText(SpannableString ss){
        Editable et = content.getText();
        int start = content.getSelectionStart();
        et.insert(start,ss);
        content.setText(et);
        content.setSelection(start+ss.length());
        content.setFocusableInTouchMode(true);
        content.setFocusable(true);
    }
    //endregion

    private SpannableString getBitmapMime(String path, String tagPath) {
        SpannableString ss = new SpannableString(tagPath);//这里使用加了<img>标签的图片路径

        int width = ScreenUtils.getScreenWidth(AddCommunity.this);
        int height = ScreenUtils.getScreenHeight(AddCommunity.this);

        Bitmap bitmap = ImageUtils.getSmallBitmap(path,width,480);
        ImageSpan imageSpan = new ImageSpan(this, bitmap);
        ss.setSpan(imageSpan, 0, tagPath.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }







}
