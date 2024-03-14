package com.flyjingfish.shapeimageviewlib;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.view.View;
import android.widget.ImageView;


class ShapeImageViewAttacher implements View.OnLayoutChangeListener {
    private final ShapeImageView mImageView;
    private final Matrix mBaseMatrix = new Matrix();
    private final Matrix mDisplayMatrix = new Matrix();
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

    public void updateDisplayMatrix() {
        Drawable drawable = mImageView.getDrawable();
        if (drawable == null) {
            return;
        }
        final float viewWidth = getImageViewWidth(mImageView);
        final float viewHeight = getImageViewHeight(mImageView);
        final int drawableWidth = drawable.getIntrinsicWidth();
        final int drawableHeight = drawable.getIntrinsicHeight();
        mDisplayMatrix.reset();
        final float widthScale = viewWidth / drawableWidth;
        final float heightScale = viewHeight / drawableHeight;
        if (mScaleType == ShapeImageView.ShapeScaleType.CENTER) {
            mDisplayMatrix.postTranslate((viewWidth - drawableWidth) / 2F,
                    (viewHeight - drawableHeight) / 2F);

        } else if (mScaleType == ShapeImageView.ShapeScaleType.CENTER_CROP) {
            float scale = Math.max(widthScale, heightScale);
            mDisplayMatrix.postScale(scale, scale);
            mDisplayMatrix.postTranslate((viewWidth - drawableWidth * scale) / 2F,
                    (viewHeight - drawableHeight * scale) / 2F);

        } else if (mScaleType == ShapeImageView.ShapeScaleType.CENTER_INSIDE) {
            float scale = Math.min(1.0f, Math.min(widthScale, heightScale));
            mDisplayMatrix.postScale(scale, scale);
            mDisplayMatrix.postTranslate((viewWidth - drawableWidth * scale) / 2F,
                    (viewHeight - drawableHeight * scale) / 2F);

        } else if (this.mScaleType == ShapeImageView.ShapeScaleType.START_CROP||
                this.mScaleType == ShapeImageView.ShapeScaleType.END_CROP||
                this.mScaleType == ShapeImageView.ShapeScaleType.AUTO_START_CENTER_CROP||
                this.mScaleType == ShapeImageView.ShapeScaleType.AUTO_END_CENTER_CROP) {
            float scale = Math.max(widthScale, heightScale);
            mDisplayMatrix.postScale(scale, scale);
            mDisplayMatrix.postTranslate((viewWidth - drawableWidth * scale) / 2F,
                    (viewHeight - drawableHeight * scale) / 2F);
        } else {
            RectF mTempSrc = new RectF(0, 0, drawableWidth, drawableHeight);
            RectF mTempDst = new RectF(0, 0, viewWidth, viewHeight);
            switch (mScaleType) {
                case FIT_CENTER:
                    mDisplayMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.CENTER);
                    break;
                case FIT_START:
                    mDisplayMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.START);
                    break;
                case FIT_END:
                    mDisplayMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.END);
                    break;
                case FIT_XY:
                    mDisplayMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.FILL);
                    break;
                default:
                    break;
            }
        }
        getDisplayRect(mDisplayMatrix);
    }

    private final RectF mDisplayRectF = new RectF();

    private void getDisplayRect(Matrix matrix) {
        Drawable drawable = mImageView.getDrawable();
        if (drawable == null) {
            return;
        }
        mDisplayRectF.set(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        matrix.mapRect(mDisplayRectF);
        final float viewWidth = getImageViewWidth(mImageView);
        final float viewHeight = getImageViewHeight(mImageView);
        float paddingLeft = ViewUtils.getViewPaddingLeft(mImageView);
        float paddingTop = mImageView.getPaddingTop();
        float left = Math.max(mDisplayRectF.left,0)+paddingLeft;
        float top = Math.max(mDisplayRectF.top,0)+paddingTop;
        float right = Math.min(mDisplayRectF.right,viewWidth)+paddingLeft;
        float bottom = Math.min(mDisplayRectF.bottom,viewHeight)+paddingTop;
        mDisplayRectF.set((int) left, (int) top, (int) right, (int) bottom);
    }

    public RectF getDisplayRectF() {
        return mDisplayRectF;
    }

}
