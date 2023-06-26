package com.flyjingfish.shapeimageview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flyjingfish.shapeimageview.databinding.ActivityAlmightyListBinding;
import com.flyjingfish.shapeimageviewlib.AlmightyShapeImageView;

import java.util.ArrayList;
import java.util.List;

public class AlmightyListImageActivity extends AppCompatActivity {

    private ActivityAlmightyListBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlmightyListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add("https://img2.baidu.com/it/u=2415498875,118078114&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=889");
        }
        RvAdapter rvAdapter = new RvAdapter(list);
        binding.recyclerView.setAdapter(rvAdapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }


    private static class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyHolder> {
        List<String> datas;
        int[] srcs = new int[]{
            R.drawable.ic_vector_flower,
                R.drawable.ic_vector_pentagon,
                R.drawable.ic_vector_sector,
                R.drawable.ic_vector_snowflake,
                R.drawable.ic_vector_heart,
                R.drawable.ic_vector_star,
        };

        public RvAdapter(List<String> datas) {
            this.datas = datas;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RvAdapter.MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.ivImage.setShapeResource(srcs[position%srcs.length]);
            MyImageLoader.getInstance().load(holder.ivImage, datas.get(position), R.mipmap.img_load_placeholder, R.mipmap.img_load_placeholder);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            AlmightyShapeImageView ivImage;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                ivImage = itemView.findViewById(R.id.iv_image);
            }
        }
    }

}
