package com.flyjingfish.shapeimageviewlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.LayoutDirection;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.text.TextUtilsCompat;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class ShapeImageView extends AppCompatImageView {
    private ShapeImageViewAttacher mAttacher;
    private ShapeScaleType mPendingScaleType;
    private final List<ColorStateList> gradientColorStates = new ArrayList<>();
    private float mAutoCropHeightWidthRatio;
    private float leftTopRadius;
    private float leftBottomRadius;
    private float rightTopRadius;
    private float rightBottomRadius;
    private final Paint mImagePaint;
    private ShapeType shapeType;
    private final Paint mBgPaint;
    private final float mBgPaintWidth;
    private ShapeType bgShapeType;
    private int[] gradientColors;
    private float gradientAngle;
    private boolean gradientRtlAngle;
    private boolean isGradient;
    private float bgLeftTopRadius;
    private float bgLeftBottomRadius;
    private float bgRightTopRadius;
    private float bgRightBottomRadius;
    private float[] gradientPositions;
    private boolean isRtl = false;
    private float startTopRadius;
    private float startBottomRadius;
    private float endTopRadius;
    private float endBottomRadius;
    private float bgStartTopRadius;
    private float bgStartBottomRadius;
    private float bgEndTopRadius;
    private float bgEndBottomRadius;
    private ColorStateList bgShapeColor;
    private int curBgShapeColor;
    private static final PorterDuffXfermode SRC_IN = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private RectF mDrawRectF;
    private final Path mDrawPath = new Path();
    private final Path mDrawBgPath = new Path();
    private RectF mBgRectF;

    public ShapeImageView(Context context) {
        this(context, null);
    }

    public ShapeImageView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public ShapeImageView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            isRtl = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == LayoutDirection.RTL;
        }
        TypedArray a = context.obtainStyledAttributes(attr, R.styleable.ShapeImageView);
        mPendingScaleType = ShapeScaleType.getType(a.getInt(R.styleable.ShapeImageView_FlyJFish_shapeScaleType, 0));
        mAutoCropHeightWidthRatio = a.getFloat(R.styleable.ShapeImageView_FlyJFish_autoCrop_height_width_ratio, 2f);
        float radius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_radius, 0);
        leftTopRadius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_left_top_radius, radius);
        leftBottomRadius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_left_bottom_radius, radius);
        rightTopRadius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_right_top_radius, radius);
        rightBottomRadius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_right_bottom_radius, radius);

        startTopRadius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_start_top_radius, radius);
        startBottomRadius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_start_bottom_radius, radius);
        endTopRadius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_end_top_radius, radius);
        endBottomRadius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_end_bottom_radius, radius);

        shapeType = ShapeType.getType(a.getInt(R.styleable.ShapeImageView_FlyJFish_shape, 1));
        bgShapeType = ShapeType.getType(a.getInt(R.styleable.ShapeImageView_FlyJFish_shape_border, 0));
        ColorStateList startColor = a.getColorStateList(R.styleable.ShapeImageView_FlyJFish_shape_border_startColor);
        ColorStateList centerColor = a.getColorStateList(R.styleable.ShapeImageView_FlyJFish_shape_border_centerColor);
        ColorStateList endColor = a.getColorStateList(R.styleable.ShapeImageView_FlyJFish_shape_border_endColor);
        bgShapeColor = a.getColorStateList(R.styleable.ShapeImageView_FlyJFish_shape_border_color);
        gradientAngle = a.getFloat(R.styleable.ShapeImageView_FlyJFish_shape_border_angle, 0);
        gradientRtlAngle = a.getBoolean(R.styleable.ShapeImageView_FlyJFish_shape_border_rtl_angle, false);
        isGradient = a.getBoolean(R.styleable.ShapeImageView_FlyJFish_shape_border_gradient, false);
        mBgPaintWidth = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_border_width, 1);
        float bgRadius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_border_radius, 0);
        bgLeftTopRadius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_border_left_top_radius, bgRadius);
        bgLeftBottomRadius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_border_left_bottom_radius, bgRadius);
        bgRightTopRadius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_border_right_top_radius, bgRadius);
        bgRightBottomRadius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_border_right_bottom_radius, bgRadius);

        bgStartTopRadius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_border_start_top_radius, bgRadius);
        bgStartBottomRadius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_border_start_bottom_radius, bgRadius);
        bgEndTopRadius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_border_end_top_radius, bgRadius);
        bgEndBottomRadius = a.getDimension(R.styleable.ShapeImageView_FlyJFish_shape_border_end_bottom_radius, bgRadius);

        a.recycle();

        if (startColor != null){
            gradientColorStates.add(startColor);
        }
        if (centerColor != null){
            gradientColorStates.add(centerColor);
        }
        if (endColor != null){
            gradientColorStates.add(endColor);
        }
        if (gradientColorStates.size() == 1){
            gradientColorStates.add(ColorStateList.valueOf(Color.TRANSPARENT));
        }
        if (bgShapeColor == null){
            bgShapeColor = ColorStateList.valueOf(Color.BLACK);
        }

        updateColors();
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(curBgShapeColor);
        mBgPaint.setStrokeWidth(mBgPaintWidth);
        mBgPaint.setStyle(Paint.Style.STROKE);

        mImagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mImagePaint.setXfermode(null);

        init();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateColors();
    }

    private boolean updateColors(){
        boolean inval = false;
        final int[] drawableState = getDrawableState();
        int color = bgShapeColor.getColorForState(drawableState, 0);
        if (color != curBgShapeColor) {
            curBgShapeColor = color;
            inval = true;
            if (mBgPaint != null){
                mBgPaint.setColor(curBgShapeColor);
            }
        }
        if (gradientColorStates != null && gradientColorStates.size() > 0){
            int[] gradientCls = new int[gradientColorStates.size()];
            for (int i = 0; i < gradientColorStates.size(); i++) {
                int gradientColor = gradientColorStates.get(i).getColorForState(drawableState, 0);
                gradientCls[i] = gradientColor;
            }
            if (gradientColors == null) {
                gradientColors = gradientCls;
                inval = true;
            } else if (gradientColors.length != gradientCls.length){
                gradientColors = gradientCls;
                inval = true;
            } else {
                boolean equals = true;
                for (int i = 0; i < gradientColors.length; i++) {
                    if (gradientColors[i] != gradientCls[i]){
                        equals = false;
                        break;
                    }
                }
                if (!equals){
                    gradientColors = gradientCls;
                    inval = true;
                }
            }
        }

        if (inval){
            invalidate();
        }
        return inval;
    }
    private void init() {
        mAttacher = new ShapeImageViewAttacher(this);
        mAttacher.setAutoCropHeightWidthRatio(mAutoCropHeightWidthRatio);
        if (mPendingScaleType != null) {
            super.setScaleType(ScaleType.MATRIX);
            setShapeScaleType(mPendingScaleType);
            mPendingScaleType = null;
        } else {
            setShapeScaleType(ShapeScaleType.getType(getScaleType()));
        }
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        super.setScaleType(scaleType);
    }

    @Override
    public Matrix getImageMatrix() {
        return mAttacher.getImageMatrix();
    }

    public void setShapeScaleType(ShapeScaleType scaleType) {
        if (mAttacher == null) {
            mPendingScaleType = scaleType;
        } else {
            mAttacher.setScaleType(scaleType);
        }
    }

    public float getAutoCropHeightWidthRatio() {
        return mAutoCropHeightWidthRatio;
    }

    public void setAutoCropHeightWidthRatio(float autoCropHeightWidthRatio) {
        this.mAutoCropHeightWidthRatio = autoCropHeightWidthRatio;
        if (mAttacher != null) {
            mAttacher.setAutoCropHeightWidthRatio(autoCropHeightWidthRatio);
            mAttacher.update();
        }

    }

    public ShapeScaleType getShapeScaleType() {
        return mAttacher.getShapeScaleType();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        // setImageBitmap calls through to this method
        if (mAttacher != null) {
            mAttacher.update();
        }
    }


    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        if (mAttacher != null) {
            mAttacher.update();
        }
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        if (mAttacher != null) {
            mAttacher.update();
        }
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        boolean changed = super.setFrame(l, t, r, b);
        if (changed) {
            mAttacher.update();
        }
        return changed;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int paddingLeft = ViewUtils.getViewPaddingLeft(this);
        int paddingRight = ViewUtils.getViewPaddingRight(this);
        mDrawRectF = new RectF(paddingLeft, getPaddingTop(), w - paddingRight, h - getPaddingBottom());
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        drawBgShape(canvas);
        clipPadding(canvas);
        mDrawPath.reset();
        if (shapeType == ShapeType.OVAL) {
            mDrawPath.addOval(mDrawRectF, Path.Direction.CCW);
        } else if (shapeType == ShapeType.RECTANGLE) {
            float leftTopRadius = ViewUtils.getRtlValue(isRtl ? endTopRadius : startTopRadius, this.leftTopRadius);
            float rightTopRadius = ViewUtils.getRtlValue(isRtl ? startTopRadius : endTopRadius, this.rightTopRadius);
            float rightBottomRadius = ViewUtils.getRtlValue(isRtl ? startBottomRadius : endBottomRadius, this.rightBottomRadius);
            float leftBottomRadius = ViewUtils.getRtlValue(isRtl ? endBottomRadius : startBottomRadius, this.leftBottomRadius);
            float[] radius = new float[]{leftTopRadius,leftTopRadius,rightTopRadius,rightTopRadius,rightBottomRadius,rightBottomRadius,leftBottomRadius,leftBottomRadius};
            mDrawPath.addRoundRect(mDrawRectF,radius,Path.Direction.CCW);
        }

        if (shapeType == ShapeType.OVAL || shapeType == ShapeType.RECTANGLE) {
            canvas.saveLayer(mDrawRectF, mImagePaint, Canvas.ALL_SAVE_FLAG);
            canvas.drawPath(mDrawPath,mImagePaint);
            mImagePaint.setXfermode(SRC_IN);
            canvas.saveLayer(mDrawRectF, mImagePaint, Canvas.ALL_SAVE_FLAG);
            super.onDraw(canvas);
            canvas.restore();
            mImagePaint.setXfermode(null);
        } else {
            super.onDraw(canvas);
        }

    }

    private void drawBgShape(Canvas canvas) {
        if (bgShapeType == null || bgShapeType == ShapeType.NONE) {
            return;
        }

        int height = getHeight();
        int width = getWidth();
        if (mBgRectF == null){
            mBgRectF = new RectF(mBgPaintWidth / 2, mBgPaintWidth / 2, width - mBgPaintWidth / 2, height - mBgPaintWidth / 2);
        }else {
            mBgRectF.set(mBgPaintWidth / 2, mBgPaintWidth / 2, width - mBgPaintWidth / 2, height - mBgPaintWidth / 2);
        }
        if (isGradient && gradientColors != null) {
            float currentAngle = gradientAngle;
            if (gradientRtlAngle && isRtl){
                currentAngle = - gradientAngle;
            }
            float angle = currentAngle % 360;
            if (angle < 0) {
                angle = 360 + angle;
            }
            float x0, y0, x1, y1;
            if (angle >= 0 && angle <= 45) {
                float percent = angle / 45;
                x0 = width / 2 + width / 2 * percent;
                y0 = 0;
            } else if (angle <= 90) {
                float percent = (angle - 45) / 45;
                x0 = width;
                y0 = height / 2 * percent;
            } else if (angle <= 135) {
                float percent = (angle - 90) / 45;
                x0 = width;
                y0 = height / 2 * percent + height / 2;
            } else if (angle <= 180) {
                float percent = (angle - 135) / 45;
                x0 = width / 2 + width / 2 * (1-percent);
                y0 = height;
            } else if (angle <= 225) {
                float percent = (angle - 180) / 45;
                x0 = width / 2 - width / 2 * percent;
                y0 = height;
            } else if (angle <= 270) {
                float percent = (angle - 225) / 45;
                x0 = 0;
                y0 = height - height / 2 * percent;
            } else if (angle <= 315) {
                float percent = (angle - 270) / 45;
                x0 = 0;
                y0 = height / 2 - height / 2 * percent;
            } else {
                float percent = (angle - 315) / 45;
                x0 = width / 2 * percent;
                y0 = 0;
            }
            x1 = width - x0;
            y1 = height - y0;
            LinearGradient linearGradient = new LinearGradient(x0, y0, x1, y1, gradientColors, gradientPositions, Shader.TileMode.CLAMP);
            mBgPaint.setShader(linearGradient);
        }
        if (bgShapeType == ShapeType.OVAL) {
            mDrawBgPath.addOval(mBgRectF, Path.Direction.CCW);
        } else {
            float bgLeftTopRadius = ViewUtils.getRtlValue(isRtl ? bgEndTopRadius : bgStartTopRadius, this.bgLeftTopRadius);
            float bgRightTopRadius = ViewUtils.getRtlValue(isRtl ? bgStartTopRadius : bgEndTopRadius, this.bgRightTopRadius);
            float bgRightBottomRadius = ViewUtils.getRtlValue(isRtl ? bgStartBottomRadius : bgEndBottomRadius, this.bgRightBottomRadius);
            float bgLeftBottomRadius = ViewUtils.getRtlValue(isRtl ? bgEndBottomRadius : bgStartBottomRadius, this.bgLeftBottomRadius);
            float[] radius = new float[]{bgLeftTopRadius,bgLeftTopRadius,bgRightTopRadius,bgRightTopRadius,bgRightBottomRadius,bgRightBottomRadius,bgLeftBottomRadius,bgLeftBottomRadius};
            mDrawBgPath.addRoundRect(mBgRectF,radius,Path.Direction.CCW);
        }
        canvas.drawPath(mDrawBgPath,mBgPaint);

    }

    private void clipPadding(Canvas canvas) {
        ShapeScaleType shapeScaleType = mAttacher.getShapeScaleType();
        boolean isShapeCrop = shapeScaleType == ShapeScaleType.START_CROP
                || shapeScaleType == ShapeScaleType.END_CROP
                || shapeScaleType == ShapeScaleType.AUTO_START_CENTER_CROP
                || shapeScaleType == ShapeScaleType.AUTO_END_CENTER_CROP
                || getScaleType() == ScaleType.CENTER
                || getScaleType() == ScaleType.CENTER_CROP;
        int paddingLeft = ViewUtils.getViewPaddingLeft(this);
        int paddingRight = ViewUtils.getViewPaddingRight(this);
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        if (isShapeCrop && (paddingLeft > 0 || paddingRight > 0 || paddingTop > 0 || paddingBottom > 0)) {
            canvas.clipRect(mDrawRectF);
        }

    }

    public enum ShapeScaleType implements Serializable {
        FIT_XY(1), FIT_START(2), FIT_CENTER(3), FIT_END(4),
        CENTER(5), CENTER_CROP(6), CENTER_INSIDE(7), START_CROP(8),
        END_CROP(9), AUTO_START_CENTER_CROP(10), AUTO_END_CENTER_CROP(11);

        ShapeScaleType(int ni) {
            type = ni;
        }

        public static ShapeScaleType getType(int ni) {
            if (ni == 1) {
                return FIT_XY;
            } else if (ni == 2) {
                return FIT_START;
            } else if (ni == 3) {
                return FIT_CENTER;
            } else if (ni == 4) {
                return FIT_END;
            } else if (ni == 5) {
                return CENTER;
            } else if (ni == 6) {
                return CENTER_CROP;
            } else if (ni == 7) {
                return CENTER_INSIDE;
            } else if (ni == 8) {
                return START_CROP;
            } else if (ni == 9) {
                return END_CROP;
            } else if (ni == 10) {
                return AUTO_START_CENTER_CROP;
            } else if (ni == 11) {
                return AUTO_END_CENTER_CROP;
            } else {
                return null;
            }
        }

        public static ShapeScaleType getType(ScaleType scaleType) {
            if (scaleType == ScaleType.FIT_XY) {
                return FIT_XY;
            } else if (scaleType == ScaleType.FIT_START) {
                return FIT_START;
            } else if (scaleType == ScaleType.FIT_CENTER) {
                return FIT_CENTER;
            } else if (scaleType == ScaleType.FIT_END) {
                return FIT_END;
            } else if (scaleType == ScaleType.CENTER) {
                return CENTER;
            } else if (scaleType == ScaleType.CENTER_CROP) {
                return CENTER_CROP;
            } else if (scaleType == ScaleType.CENTER_INSIDE) {
                return CENTER_INSIDE;
            } else {
                return null;
            }
        }

        public static ScaleType getScaleType(ShapeScaleType scaleType) {
            if (scaleType == ShapeScaleType.FIT_XY) {
                return ScaleType.FIT_XY;
            } else if (scaleType == ShapeScaleType.FIT_START) {
                return ScaleType.FIT_START;
            } else if (scaleType == ShapeScaleType.FIT_CENTER) {
                return ScaleType.FIT_CENTER;
            } else if (scaleType == ShapeScaleType.FIT_END) {
                return ScaleType.FIT_END;
            } else if (scaleType == ShapeScaleType.CENTER) {
                return ScaleType.CENTER;
            } else if (scaleType == ShapeScaleType.CENTER_CROP) {
                return ScaleType.CENTER_CROP;
            } else if (scaleType == ShapeScaleType.CENTER_INSIDE) {
                return ScaleType.CENTER_INSIDE;
            } else {
                return null;
            }
        }

        final int type;

        public int getType() {
            return type;
        }

    }

    public enum ShapeType {
        NONE(0), RECTANGLE(1), OVAL(2);

        ShapeType(int type) {
            this.type = type;
        }

        final int type;

        public int getType() {
            return type;
        }

        public static ShapeType getType(int type) {
            if (type == 1) {
                return RECTANGLE;
            } else if (type == 2) {
                return OVAL;
            } else {
                return NONE;
            }
        }
    }

    public int getBgShapeColor() {
        return curBgShapeColor;
    }

    public void setBgShapeColor(@ColorInt int bgShapeColor) {
        setBgShapeColors(ColorStateList.valueOf(bgShapeColor));
    }

    public void setBgShapeColors(ColorStateList bgShapeColor) {
        if (bgShapeColor == null){
            return;
        }
        this.bgShapeColor = bgShapeColor;
        updateColors();
    }

    public float getLeftTopRadius() {
        return leftTopRadius;
    }

    public void setLeftTopRadius(float leftTopRadius) {
        this.leftTopRadius = leftTopRadius;
        invalidate();
    }

    public float getLeftBottomRadius() {
        return leftBottomRadius;
    }

    public void setLeftBottomRadius(float leftBottomRadius) {
        this.leftBottomRadius = leftBottomRadius;
        invalidate();
    }

    public float getRightTopRadius() {
        return rightTopRadius;
    }

    public void setRightTopRadius(float rightTopRadius) {
        this.rightTopRadius = rightTopRadius;
        invalidate();
    }

    public float getRightBottomRadius() {
        return rightBottomRadius;
    }

    public void setRightBottomRadius(float rightBottomRadius) {
        this.rightBottomRadius = rightBottomRadius;
        invalidate();
    }

    public void setRadius(int radius) {
        this.leftTopRadius = radius;
        this.rightTopRadius = radius;
        this.leftBottomRadius = radius;
        this.rightBottomRadius = radius;
        invalidate();
    }

    public void setRadius(int leftTopRadius,int rightTopRadius,int rightBottomRadius,int leftBottomRadius) {
        this.leftTopRadius = leftTopRadius;
        this.rightTopRadius = rightTopRadius;
        this.rightBottomRadius = rightBottomRadius;
        this.leftBottomRadius = leftBottomRadius;
        invalidate();
    }
    public void setRelativeRadius(int startTopRadius,int endTopRadius,int endBottomRadius,int startBottomRadius) {
        this.startTopRadius = startTopRadius;
        this.endTopRadius = endTopRadius;
        this.endBottomRadius = endBottomRadius;
        this.startBottomRadius = startBottomRadius;
        invalidate();
    }
    public ShapeType getShapeType() {
        return shapeType;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
        invalidate();
    }

    public float getBgPaintWidth() {
        return mBgPaintWidth;
    }

    public ShapeType getBgShapeType() {
        return bgShapeType;
    }

    public void setBgShapeType(ShapeType bgShapeType) {
        this.bgShapeType = bgShapeType;
        invalidate();
    }

    public float getGradientAngle() {
        return gradientAngle;
    }

    public void setGradientAngle(float gradientAngle) {
        this.gradientAngle = gradientAngle;
        invalidate();
    }

    public boolean isGradientRtlAngle() {
        return gradientRtlAngle;
    }

    public void setGradientRtlAngle(boolean gradientRtlAngle) {
        this.gradientRtlAngle = gradientRtlAngle;
        invalidate();
    }

    public boolean isGradient() {
        return isGradient;
    }

    public void setGradient(boolean gradient) {
        isGradient = gradient;
        invalidate();
    }

    public void setBgRadius(float bgRadius) {
        bgLeftTopRadius = bgRadius;
        bgLeftBottomRadius = bgRadius;
        bgRightTopRadius = bgRadius;
        bgRightBottomRadius = bgRadius;
        invalidate();
    }

    public void setBgRadius(float bgLeftTopRadius,float bgRightTopRadius,float bgRightBottomRadius,float bgLeftBottomRadius) {
        this.bgLeftTopRadius = bgLeftTopRadius;
        this.bgRightTopRadius = bgRightTopRadius;
        this.bgRightBottomRadius = bgRightBottomRadius;
        this.bgLeftBottomRadius = bgLeftBottomRadius;
        invalidate();
    }

    public void setBgRelativeRadius(float bgStartTopRadius,float bgEndTopRadius,float bgEndBottomRadius,float bgStartBottomRadius) {
        this.bgStartTopRadius = bgStartTopRadius;
        this.bgEndTopRadius = bgEndTopRadius;
        this.bgEndBottomRadius = bgEndBottomRadius;
        this.bgStartBottomRadius = bgStartBottomRadius;
        invalidate();
    }

    public float getBgLeftTopRadius() {
        return bgLeftTopRadius;
    }

    public void setBgLeftTopRadius(float bgLeftTopRadius) {
        this.bgLeftTopRadius = bgLeftTopRadius;
        invalidate();
    }

    public float getBgLeftBottomRadius() {
        return bgLeftBottomRadius;
    }

    public void setBgLeftBottomRadius(float bgLeftBottomRadius) {
        this.bgLeftBottomRadius = bgLeftBottomRadius;
        invalidate();
    }

    public float getBgRightTopRadius() {
        return bgRightTopRadius;
    }

    public void setBgRightTopRadius(float bgRightTopRadius) {
        this.bgRightTopRadius = bgRightTopRadius;
        invalidate();
    }

    public float getBgRightBottomRadius() {
        return bgRightBottomRadius;
    }

    public void setBgRightBottomRadius(float bgRightBottomRadius) {
        this.bgRightBottomRadius = bgRightBottomRadius;
        invalidate();
    }

    public float getStartTopRadius() {
        return startTopRadius;
    }

    public void setStartTopRadius(float startTopRadius) {
        this.startTopRadius = startTopRadius;
        invalidate();
    }

    public float getStartBottomRadius() {
        return startBottomRadius;
    }

    public void setStartBottomRadius(float startBottomRadius) {
        this.startBottomRadius = startBottomRadius;
        invalidate();
    }

    public float getEndTopRadius() {
        return endTopRadius;
    }

    public void setEndTopRadius(float endTopRadius) {
        this.endTopRadius = endTopRadius;
        invalidate();
    }

    public float getEndBottomRadius() {
        return endBottomRadius;
    }

    public void setEndBottomRadius(float endBottomRadius) {
        this.endBottomRadius = endBottomRadius;
        invalidate();
    }

    public float getBgStartTopRadius() {
        return bgStartTopRadius;
    }

    public void setBgStartTopRadius(float bgStartTopRadius) {
        this.bgStartTopRadius = bgStartTopRadius;
        invalidate();
    }

    public float getBgStartBottomRadius() {
        return bgStartBottomRadius;
    }

    public void setBgStartBottomRadius(float bgStartBottomRadius) {
        this.bgStartBottomRadius = bgStartBottomRadius;
        invalidate();
    }

    public float getBgEndTopRadius() {
        return bgEndTopRadius;
    }

    public void setBgEndTopRadius(float bgEndTopRadius) {
        this.bgEndTopRadius = bgEndTopRadius;
        invalidate();
    }

    public float getBgEndBottomRadius() {
        return bgEndBottomRadius;
    }

    public void setBgEndBottomRadius(float bgEndBottomRadius) {
        this.bgEndBottomRadius = bgEndBottomRadius;
        invalidate();
    }


    public float[] getGradientPositions() {
        return gradientPositions;
    }

    /**
     * 渐变色比重，需要和渐变色数量相等
     *
     * @param gradientPositions 渐变色分布
     */
    public void setGradientPositions(@Nullable float[] gradientPositions) {
        this.gradientPositions = gradientPositions;
        invalidate();
    }

    public int[] getGradientColors() {
        return gradientColors;
    }
    public List<ColorStateList> getGradientColorStates() {
        return gradientColorStates;
    }
    /**
     * @param gradientColors 渐变色
     */
    public void setGradientColors(@Size(min = 2) @NonNull @ColorInt int[] gradientColors) {
        ColorStateList[] colorStateLists = new ColorStateList[gradientColors.length];
        for (int i = 0; i < gradientColors.length; i++) {
            colorStateLists[i] = ColorStateList.valueOf(gradientColors[i]);
        }
        setGradientColors(colorStateLists);
    }

    public void setGradientColors(@NonNull ColorStateList[] colorStateLists) {
        gradientColorStates.clear();
        gradientColorStates.addAll(Arrays.asList(colorStateLists));
        if (gradientColorStates.size() == 1){
            gradientColorStates.add(ColorStateList.valueOf(Color.TRANSPARENT));
        }
        if (gradientPositions != null && gradientColorStates.size() != gradientPositions.length){
            this.gradientPositions = null;
        }
        updateColors();
    }

}