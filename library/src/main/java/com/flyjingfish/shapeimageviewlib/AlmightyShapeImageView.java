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
    private ShapeScaleType mShapeScaleType;
    private boolean isDrawShapeClear;

    public AlmightyShapeImageView(@NonNull Context context) {
        this(context, null);
    }

    public AlmightyShapeImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlmightyShapeImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AlmightyShapeImageView);
        mShapeResource = a.getDrawable(R.styleable.AlmightyShapeImageView_FlyJFish_almighty_shape_resource);
        mShapeScaleType = ShapeScaleType.getType(a.getInt(R.styleable.AlmightyShapeImageView_FlyJFish_almighty_shape_scaleType, 0));
        a.recycle();
        mShapePaint = new Paint();
        mShapePaint.setColor(Color.WHITE);
        mShapePaint.setAntiAlias(true);
        mShapePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mShapeResource != null){
            final int[] drawableState = getDrawableState();
            boolean inval = mShapeResource.setState(drawableState);
            if (inval){
                invalidate();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mShapeResource != null) {
            preDrawShaper(canvas);
            drawShape(canvas);
            super.onDraw(canvas);
            canvas.restore();
        } else {
            super.onDraw(canvas);
        }

    }

    private void preDrawShaper(Canvas canvas) {
        if (mShapeScaleType != ShapeScaleType.ALWAYS_FIX_XY){
            return;
        }
        int height = getHeight();
        int width = getWidth();
        int paddingLeft = ViewUtils.getViewPaddingLeft(this);
        int paddingRight = ViewUtils.getViewPaddingRight(this);
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        Drawable drawable = getDrawable();
        Matrix matrix = getImageMatrix();
        if (drawable == null || matrix == null) {
            return;
        }

        float[] matrixValues = new float[9];
        matrix.getValues(matrixValues);
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();


        float pictureWidth;
        float pictureHeight;


        float transX;
        float transY;

        ScaleType scaleType = getScaleType();
        if (scaleType == ScaleType.FIT_XY || scaleType == ScaleType.CENTER_CROP) {
            transX = paddingLeft;
            transY = paddingTop;
            pictureWidth = width - paddingLeft - paddingRight;
            pictureHeight = height - paddingTop - paddingBottom;
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
                transX = (width - pictureWidth) / 2;
                transY = (height - pictureHeight) / 2;
            } else {
                transX = paddingLeft;
                transY = paddingTop;
                pictureWidth = width - paddingLeft - paddingRight;
                pictureHeight = height - paddingTop - paddingBottom;
            }
        } else {
            transX = (int) matrixValues[2] + paddingLeft;
            transY = (int) matrixValues[5] + paddingTop;

            pictureWidth = drawableWidth * matrixValues[0];
            pictureHeight = drawableHeight * matrixValues[4];
        }
        int left = (int) (transX );
        int top = (int) (transY);
        int right = ((int) (pictureWidth + transX ));
        int bottom = ((int) (pictureHeight + transY));

        mShapePaint.setXfermode(null);
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), mShapePaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRect(left,top,right,bottom,mShapePaint);

        mShapePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), mShapePaint, Canvas.ALL_SAVE_FLAG);
        mShapeResource.setBounds(paddingLeft, paddingTop, width - paddingRight, height - paddingBottom);
        mShapeResource.draw(canvas);

        isDrawShapeClear = true;
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
        if (matrix == null || mShapeScaleType == ShapeScaleType.ALWAYS_FIX_XY) {
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
            if (scaleType == ScaleType.FIT_XY || scaleType == ScaleType.CENTER_CROP) {
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
            if (mShapeScaleType == ShapeScaleType.FOLLOW_IMAGEVIEW_FULL_IMAGE){
                shapeWidth = pictureWidth;
                shapeHeight = pictureHeight;
            }
            left = ((int) (transX + (pictureWidth - shapeWidth) / 2));
            top = ((int) (transY + (pictureHeight - shapeHeight) / 2));
            right = ((int) (shapeWidth + transX + (pictureWidth - shapeWidth) / 2));
            bottom = ((int) (shapeHeight + transY + (pictureHeight - shapeHeight) / 2));
        }

        if (!isDrawShapeClear){
            mShapePaint.setXfermode(null);
            canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), mShapePaint, Canvas.ALL_SAVE_FLAG);
            mShapeResource.setBounds(left, top, right, bottom);
            mShapeResource.draw(canvas);
        }
        mShapePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), mShapePaint, Canvas.ALL_SAVE_FLAG);

        isDrawShapeClear = false;
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

    public ShapeScaleType getShapeScaleType() {
        return mShapeScaleType;
    }

    public void setShapeScaleType(ShapeScaleType shapeScaleType) {
        this.mShapeScaleType = shapeScaleType;
        invalidate();
    }

    public enum ShapeScaleType {
        FOLLOW_IMAGEVIEW_KEEP_RESOURCE_SCALE(0),FOLLOW_IMAGEVIEW_FULL_IMAGE(1), ALWAYS_FIX_XY(2);

        ShapeScaleType(int type) {
            this.type = type;
        }

        final int type;

        public int getType() {
            return type;
        }

        public static ShapeScaleType getType(int type) {
            if (type == 2) {
                return ALWAYS_FIX_XY;
            } else if (type == 1) {
                return FOLLOW_IMAGEVIEW_FULL_IMAGE;
            } else {
                return FOLLOW_IMAGEVIEW_KEEP_RESOURCE_SCALE;
            }
        }
    }
}
