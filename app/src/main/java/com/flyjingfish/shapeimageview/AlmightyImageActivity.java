package com.flyjingfish.shapeimageview;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.request.target.Target;
import com.flyjingfish.shapeimageview.databinding.ActivityAlmightyBinding;
import com.flyjingfish.shapeimageviewlib.AlmightyShapeImageView;


public class AlmightyImageActivity extends AppCompatActivity {

    private ActivityAlmightyBinding binding;
    private String itemData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlmightyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        itemData = "https://img2.baidu.com/it/u=2415498875,118078114&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=889";
//        itemData = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F9%2F57c4f3db0ff7a_120_80.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1662179613&t=105ec8c89e77a853ba57e2a2dc056eab";
        setData();
    }


    private void setData() {
        MyImageLoader.getInstance().load(binding.iv1, itemData, Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL,R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.iv2, itemData, Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL,R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.iv3, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.iv4, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.iv5, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.iv6, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.iv7, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.iv8, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        MyImageLoader.getInstance().load(binding.iv9, itemData, R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
    }

    public void onChangeClick(View view){
        binding.iv1.setShapeResource(((AlmightyShapeImageView) view).getShapeDrawable());
    }
}
