package com.flyjingfish.shapeimageview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.request.target.Target;
import com.flyjingfish.shapeimageview.databinding.ActivityAlmightyBinding;


public class AlmightyImageActivity extends AppCompatActivity {

    private ActivityAlmightyBinding binding;
    private String itemData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlmightyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        itemData = "https://img2.baidu.com/it/u=2415498875,118078114&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=889";
        setData();
    }


    private void setData() {
        MyImageLoader.getInstance().load(binding.iv1, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.iv2, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.iv3, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.iv4, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.iv5, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.iv6, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.iv7, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.iv8, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.iv9, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
    }

}
