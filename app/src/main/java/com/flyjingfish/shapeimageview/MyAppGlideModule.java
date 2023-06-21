package com.flyjingfish.shapeimageview;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.caverock.androidsvg.SVG;
import com.flyjingfish.shapeimageview.svg.SvgDecoder;
import com.flyjingfish.shapeimageview.svg.SvgDrawableTranscoder;

import java.io.InputStream;

@GlideModule
public class MyAppGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(
            @NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry
                .register(SVG.class, PictureDrawable.class, new SvgDrawableTranscoder())
                .append(InputStream.class, SVG.class, new SvgDecoder());
    }

    // Disable manifest parsing to avoid adding similar modules twice.
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
