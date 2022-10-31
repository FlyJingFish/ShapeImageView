package com.flyjingfish.shapeimageviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class AlmightyShapeImageView extends AppCompatImageView {
    private Drawable mShapeDrawable;
    private Paint mShapePaint;

    public AlmightyShapeImageView(@NonNull Context context) {
        this(context,null);
    }

    public AlmightyShapeImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AlmightyShapeImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AlmightyShapeImageView);
        mShapeDrawable = a.getDrawable(R.styleable.AlmightyShapeImageView_almighty_shape_resource);
        a.recycle();
        mShapePaint = new Paint();
        mShapePaint.setColor(Color.WHITE);
        mShapePaint.setAntiAlias(true);
        mShapePaint.setStyle(Paint.Style.FILL);
        mShapePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mShapeDrawable != null) {
            mShapePaint.setXfermode(null);
            canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), mShapePaint, Canvas.ALL_SAVE_FLAG);
            drawShape(canvas);
            super.onDraw(canvas);
            canvas.restore();
        } else {
            super.onDraw(canvas);
        }

    }

    private void drawShape(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();
        int paddingLeft = ViewUtils.getViewPaddingLeft(this);
        int paddingRight = ViewUtils.getViewPaddingRight(this);
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        mShapePaint.setXfermode(null);
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), mShapePaint, Canvas.ALL_SAVE_FLAG);
        mShapeDrawable.setBounds(paddingLeft,paddingTop,width-paddingRight,height-paddingBottom);
        mShapeDrawable.draw(canvas);
        mShapePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), mShapePaint, Canvas.ALL_SAVE_FLAG);
    }

    public Drawable getShapeDrawable() {
        return mShapeDrawable;
    }

    public void setShapeDrawable(Drawable shapeDrawable) {
        this.mShapeDrawable = shapeDrawable;
        invalidate();
    }

    public void setShapeDrawable(@DrawableRes int shapeDrawableRes) {
        setShapeDrawable(getResources().getDrawable(shapeDrawableRes));
    }


}
