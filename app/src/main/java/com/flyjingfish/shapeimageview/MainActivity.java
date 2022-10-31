package com.flyjingfish.shapeimageview;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.flyjingfish.shapeimageview.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.AlmightyShapeImageView.setOnClickListener(v -> {
            startActivity(new Intent(this,AlmightyImageActivity.class));
        });
        binding.ShapeImageView.setOnClickListener(v -> {
            startActivity(new Intent(this,ScaleTypeActivity.class));
        });
    }
}
