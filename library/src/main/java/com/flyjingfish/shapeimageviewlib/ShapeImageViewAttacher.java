package com.flyjingfish.shapeimageviewlib;

import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;


public class ShapeImageViewAttacher implements View.OnLayoutChangeListener {
    private final ShapeImageView mImageView;
    private final Matrix mBaseMatrix = new Matrix();
    private final Matrix mDrawMatrix = new Matrix();
    private ShapeImageView.ShapeScaleType mScaleType;
    private float mAutoCropHeightWidthRatio;

    public ShapeImageViewAttacher(ShapeImageView imageView) {
        this.mImageView = imageView;
        imageView.addOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (left != oldLeft || top != oldTop || right != oldRight || bottom != oldBottom) {
            update();
        }
    }

    public void update() {
        Drawable drawable = mImageView.getDrawable();
        updateBaseMatrix(drawable);
    }

    private void updateBaseMatrix(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        final float viewWidth = getImageViewWidth(mImageView);
        final float viewHeight = getImageViewHeight(mImageView);
        final int drawableWidth = drawable.getIntrinsicWidth();
        final int drawableHeight = drawable.getIntrinsicHeight();
        final float widthScale = viewWidth / drawableWidth;
        final float heightScale = viewHeight / drawableHeight;
        if (mScaleType == ShapeImageView.ShapeScaleType.START_CROP) {
            mBaseMatrix.reset();
            float scale = Math.max(widthScale, heightScale);
            mBaseMatrix.postScale(scale, scale);
            mBaseMatrix.postTranslate(0, 0);
            resetMatrix();
        } else if (mScaleType == ShapeImageView.ShapeScaleType.END_CROP) {
            mBaseMatrix.reset();
            float scale = Math.max(widthScale, heightScale);
            mBaseMatrix.postScale(scale, scale);
            mBaseMatrix.postTranslate((viewWidth - drawableWidth * scale),
                    (viewHeight - drawableHeight * scale));
            resetMatrix();
        } else if (mScaleType == ShapeImageView.ShapeScaleType.AUTO_START_CENTER_CROP) {
            float imageHeightWidthRatio = drawableHeight * 1f / drawableWidth;
            float viewHeightWidthRatio = viewHeight / viewWidth;
            float ratio = imageHeightWidthRatio/viewHeightWidthRatio;
            mBaseMatrix.reset();
            float scale = Math.max(widthScale, heightScale);
            mBaseMatrix.postScale(scale, scale);
            if (ratio >= mAutoCropHeightWidthRatio) {
                mBaseMatrix.postTranslate(0, 0);
            } else {
                mBaseMatrix.postTranslate((viewWidth - drawableWidth * scale) / 2,
                        (viewHeight - drawableHeight * scale) / 2);
            }
            resetMatrix();
        } else if (mScaleType == ShapeImageView.ShapeScaleType.AUTO_END_CENTER_CROP) {
            float imageHeightWidthRatio = drawableHeight * 1f / drawableWidth;
            float viewHeightWidthRatio = viewHeight / viewWidth;
            float ratio = imageHeightWidthRatio/viewHeightWidthRatio;
            mBaseMatrix.reset();
            float scale = Math.max(widthScale, heightScale);
            mBaseMatrix.postScale(scale, scale);
            if (ratio >= mAutoCropHeightWidthRatio) {
                mBaseMatrix.postTranslate((viewWidth - drawableWidth * scale),
                        (viewHeight - drawableHeight * scale));
            } else {
                mBaseMatrix.postTranslate((viewWidth - drawableWidth * scale) / 2,
                        (viewHeight - drawableHeight * scale) / 2);
            }
            resetMatrix();
        } else {
            ImageView.ScaleType scaleType = ShapeImageView.ShapeScaleType.getScaleType(mScaleType);
            if (scaleType != null){
                mImageView.setScaleType(scaleType);
            }
        }


    }

    private void resetMatrix() {
        setImageViewMatrix(getDrawMatrix());
    }

    private Matrix getDrawMatrix() {
        mDrawMatrix.set(mBaseMatrix);
        return mDrawMatrix;
    }

    private void setImageViewMatrix(Matrix matrix) {
        mImageView.setImageMatrix(matrix);
    }

    public void setScaleType(ShapeImageView.ShapeScaleType scaleType) {
        if (scaleType != mScaleType) {
            mScaleType = scaleType;
            update();
        }
    }

    public ShapeImageView.ShapeScaleType getShapeScaleType() {
        return mScaleType;
    }

    private int getImageViewWidth(ImageView imageView) {
        return imageView.getWidth() - ViewUtils.getViewPaddingLeft(imageView) - ViewUtils.getViewPaddingRight(imageView);
    }

    private int getImageViewHeight(ImageView imageView) {
        return imageView.getHeight() - imageView.getPaddingTop() - imageView.getPaddingBottom();
    }

    public Matrix getImageMatrix() {
        return mDrawMatrix;
    }

    public void setAutoCropHeightWidthRatio(float autoCropHeightWidthRatio) {
        this.mAutoCropHeightWidthRatio = autoCropHeightWidthRatio;
    }
}
