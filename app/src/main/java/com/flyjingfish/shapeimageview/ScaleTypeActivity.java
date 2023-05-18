package com.flyjingfish.shapeimageview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.request.target.Target;
import com.flyjingfish.shapeimageview.databinding.ActivityScaleTypeBinding;


public class ScaleTypeActivity extends AppCompatActivity {

    private ActivityScaleTypeBinding binding;
    private String itemData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScaleTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        itemData = "https://pics4.baidu.com/feed/50da81cb39dbb6fd95aa0c599b8d0d1e962b3708.jpeg?token=bf17224f51a6f4bb389e787f9c487940";
        setData();
        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float radio = progress *1f/2 +1;
                binding.tvRadioValue.setText("autoCrop_height_width_ratio = " + radio);
                binding.ivAutoStartCrop.setAutoCropHeightWidthRatio(radio);
                binding.ivAutoEndCrop.setAutoCropHeightWidthRatio(radio);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //自定义渐变色
        binding.ivCenterInside.setGradientColors(new int[]{getResources().getColor(R.color.purple_700),getResources().getColor(R.color.white),getResources().getColor(R.color.purple_200)});
        //渐变色分布
        binding.ivCenterInside.setGradientPositions(new float[]{0f,0.5f,1});

        binding.ivFitStart.setOnClickListener(v -> {});
        binding.ivFitCenter.setOnClickListener(v -> {});
    }


    private void setData() {
        MyImageLoader.getInstance().load(binding.ivCenter, itemData, Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.ivCenterCrop, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.ivCenterInside, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.ivFitStart, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.ivFitCenter, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.ivFitEnd, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.ivStartCrop, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.ivEndCrop, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.ivAutoStartCrop, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.ivAutoEndCrop, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
    }

    public void onPicClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pic1:
                binding.tvPic1.setBackgroundColor(getResources().getColor(R.color.teal_200));
                binding.tvPic2.setBackgroundColor(Color.TRANSPARENT);
                binding.tvPic3.setBackgroundColor(Color.TRANSPARENT);
                itemData = "https://pics4.baidu.com/feed/50da81cb39dbb6fd95aa0c599b8d0d1e962b3708.jpeg?token=bf17224f51a6f4bb389e787f9c487940";
                break;
            case R.id.tv_pic2:
                binding.tvPic2.setBackgroundColor(getResources().getColor(R.color.teal_200));
                binding.tvPic1.setBackgroundColor(Color.TRANSPARENT);
                binding.tvPic3.setBackgroundColor(Color.TRANSPARENT);
                itemData = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp0.itc.cn%2Fq_70%2Fimages03%2F20210227%2F6687c969b58d486fa2f23d8488b96ae4.jpeg&refer=http%3A%2F%2Fp0.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661701773&t=19043990158a1d11c2a334146020e2ce";
                break;
            case R.id.tv_pic3:
                binding.tvPic3.setBackgroundColor(getResources().getColor(R.color.teal_200));
                binding.tvPic1.setBackgroundColor(Color.TRANSPARENT);
                binding.tvPic2.setBackgroundColor(Color.TRANSPARENT);
                itemData = "https://img1.baidu.com/it/u=3124924291,3476865151&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=4841";
                break;
        }
        setData();
    }

}
