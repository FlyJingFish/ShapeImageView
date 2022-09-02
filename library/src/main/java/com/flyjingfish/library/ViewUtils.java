package com.flyjingfish.library;

import android.util.LayoutDirection;
import android.view.View;

import androidx.core.text.TextUtilsCompat;

import java.util.Locale;

public class ViewUtils {

    public static int getViewPaddingLeft(View view){
        boolean isRtl = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == LayoutDirection.RTL;
        int paddingStart = view.getPaddingStart();
        int paddingEnd = view.getPaddingEnd();
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();
        int paddingLeftMax;
        if (isRtl) {
            paddingLeftMax = Math.max(paddingEnd, paddingLeft);
        } else {
            paddingLeftMax = Math.max(paddingStart, paddingLeft);
        }


        return paddingLeftMax;
    }

    public static int getViewPaddingRight(View view){
        boolean isRtl = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == LayoutDirection.RTL;
        int paddingStart = view.getPaddingStart();
        int paddingEnd = view.getPaddingEnd();
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();
        int paddingRightMax;
        if (isRtl) {
            paddingRightMax = Math.max(paddingStart, paddingRight);
        } else {
            paddingRightMax = Math.max(paddingEnd, paddingRight);
        }


        return paddingRightMax;
    }
}