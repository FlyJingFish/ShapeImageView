package com.flyjingfish.shapeimageviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
    private Drawable mShapeResource;
    private final Paint mShapePaint;

    public AlmightyShapeImageView(@NonNull Context context) {
        this(context, null);
    }

    public AlmightyShapeImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlmightyShapeImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AlmightyShapeImageView);
        mShapeResource = a.getDrawable(R.styleable.AlmightyShapeImageView_almighty_shape_resource);
        a.recycle();
        mShapePaint = new Paint();
        mShapePaint.setColor(Color.WHITE);
        mShapePaint.setAntiAlias(true);
        mShapePaint.setStyle(Paint.Style.FILL);
        mShapePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mShapeResource != null) {
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
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }

        Matrix matrix = getImageMatrix();
        int left;
        int top;
        int right;
        int bottom;
        if (matrix == null) {
            left = paddingLeft;
            top = paddingTop;
            right = width - paddingRight;
            bottom = height - paddingBottom;
        } else {
            float[] matrixValues = new float[9];
            matrix.getValues(matrixValues);
            int drawableWidth = drawable.getIntrinsicWidth();
            int drawableHeight = drawable.getIntrinsicHeight();
            float drawHWScale = drawableHeight * 1f / drawableWidth;

            int shapeResourceWidth = mShapeResource.getIntrinsicWidth();
            int shapeResourceHeight = mShapeResource.getIntrinsicHeight();

            float shapeHWScale = shapeResourceHeight * 1f / shapeResourceWidth;

            float viewHWScale = height * 1f / width;

            float pictureWidth;
            float pictureHeight;
            float shapeWidth;
            float shapeHeight;


            float transX;
            float transY;

            ScaleType scaleType = getScaleType();
            if (scaleType == ScaleType.FIT_XY) {
                transX = paddingLeft;
                transY = paddingTop;
                pictureWidth = width - paddingLeft - paddingRight;
                pictureHeight = height - paddingTop - paddingBottom;
                shapeWidth = pictureWidth;
                shapeHeight = pictureHeight;
            } else if (scaleType == ScaleType.CENTER_CROP) {
                transX = paddingLeft;
                transY = paddingTop;
                pictureWidth = width - paddingLeft - paddingRight;
                pictureHeight = height - paddingTop - paddingBottom;
                if (viewHWScale > shapeHWScale) {//按drawable宽走
                    shapeWidth = pictureWidth;
                    shapeHeight = shapeWidth * shapeHWScale;
                } else {//按drawable高走
                    shapeHeight = pictureHeight;
                    shapeWidth = shapeHeight / shapeHWScale;
                }
            } else if (scaleType == ScaleType.CENTER) {
                if (drawableWidth < width || drawableHeight < height) {
                    if (drawableWidth < width) {
                        pictureWidth = drawableWidth;
                    } else {
                        pictureWidth = width;
                    }
                    if (drawableHeight < height) {
                        pictureHeight = drawableHeight;
                    } else {
                        pictureHeight = height;
                    }
                    float pictureHWScale = pictureHeight / pictureWidth;
                    if (pictureHWScale > shapeHWScale) {//按drawable宽走
                        shapeWidth = pictureWidth;
                        shapeHeight = shapeWidth * shapeHWScale;
                    } else {//按drawable高走
                        shapeHeight = pictureHeight;
                        shapeWidth = shapeHeight / shapeHWScale;
                    }
                    transX = (width - pictureWidth) / 2;
                    transY = (height - pictureHeight) / 2;
                } else {
                    transX = paddingLeft;
                    transY = paddingTop;
                    pictureWidth = width - paddingLeft - paddingRight;
                    pictureHeight = height - paddingTop - paddingBottom;
                    if (viewHWScale > shapeHWScale) {//按drawable宽走
                        shapeWidth = pictureWidth;
                        shapeHeight = shapeWidth * shapeHWScale;
                    } else {//按drawable高走
                        shapeHeight = pictureHeight;
                        shapeWidth = shapeHeight / shapeHWScale;
                    }
                }
            } else {
                transX = (int) matrixValues[2] + paddingLeft;
                transY = (int) matrixValues[5] + paddingTop;

                pictureWidth = drawableWidth * matrixValues[0];
                pictureHeight = drawableHeight * matrixValues[4];
                if (drawHWScale > shapeHWScale) {//按drawable宽走
                    shapeWidth = pictureWidth;
                    shapeHeight = shapeWidth * shapeHWScale;
                } else {//按drawable高走
                    shapeHeight = pictureHeight;
                    shapeWidth = shapeHeight / shapeHWScale;
                }
            }

            left = ((int) (transX + (pictureWidth - shapeWidth) / 2));
            top = ((int) (transY + (pictureHeight - shapeHeight) / 2));
            right = ((int) (shapeWidth + transX + (pictureWidth - shapeWidth) / 2));
            bottom = ((int) (shapeHeight + transY + (pictureHeight - shapeHeight) / 2));
        }

        mShapePaint.setXfermode(null);
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), mShapePaint, Canvas.ALL_SAVE_FLAG);
        mShapeResource.setBounds(left, top, right, bottom);
        mShapeResource.draw(canvas);
        mShapePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), mShapePaint, Canvas.ALL_SAVE_FLAG);
    }

    public Drawable getShapeDrawable() {
        return mShapeResource;
    }

    public void setShapeResource(Drawable shapeResource) {
        this.mShapeResource = shapeResource;
        invalidate();
    }

    public void setShapeResource(@DrawableRes int shapeResourceRes) {
        setShapeResource(getResources().getDrawable(shapeResourceRes));
    }


}
