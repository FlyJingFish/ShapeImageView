# ShapeImageView
## 支持圆图或圆角图，可绘制圆环背景或圆角框背景，除ImageView自带属性外新增4中显示模式，本库中的ShapeImageView没有操作Bitmap，可以放心使用！！！

[![](https://jitpack.io/v/FlyJingFish/ShapeImageView.svg)](https://jitpack.io/#FlyJingFish/ShapeImageView)


<img src="https://github.com/FlyJingFish/ShapeImageView/blob/master/screenshot/Screenshot_2022_0902_181056.jpg" width="405px" height="842px" alt="show" />

## 特色功能
1，支持渐变色圆角框或圆形框

2，圆角框 支持四个角独立设置角度值，圆角图像 同样支持四个角独立设置角度值

3，新增支持startCrop、endCrop、autoStartCenterCrop、autoEndCenterCrop四种显示模式

4，autoStartCenterCrop和autoEndCenterCrop显示模式可通过设置autoCrop_height_width_ratio之后，自动在startCrop和centerCrop（endCrop和centerCrop）之间切换

## 第一步，根目录build.gradle

```gradle
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```
## 第二步，需要引用的build.gradle （最新版本[![](https://jitpack.io/v/FlyJingFish/ShapeImageView.svg)](https://jitpack.io/#FlyJingFish/ShapeImageView)）

```gradle
    dependencies {
        implementation 'com.github.FlyJingFish:ShapeImageView:latest.release.here'
    }
```
## 第三步，使用说明

### 外框使用

1，需要特别设置一下padding，否则图片有些ScaleType默认显示充满ImageView

2，渐变色外框颜色分布可通过setGradientPositions设置，默认null值均匀分布

3，渐变色想自定义更多颜色可通过setGradientColors设置

### 属性一览

```xml
    <attr name="shapeScaleType" format="enum">
        <enum name="fitXY" value="1" />
        <enum name="fitStart" value="2" />
        <enum name="fitCenter" value="3" />
        <enum name="fitEnd" value="4" />
        <enum name="center" value="5" />
        <enum name="centerCrop" value="6" />
        <enum name="centerInside" value="7" />
        <enum name="startCrop" value="8" />
        <enum name="endCrop" value="9" />
        <enum name="autoStartCenterCrop" value="10" />
        <enum name="autoEndCenterCrop" value="11" />
    </attr>
    <attr name="shapeImage_left_top_radius" format="dimension" />
    <attr name="shapeImage_left_bottom_radius" format="dimension" />
    <attr name="shapeImage_right_top_radius" format="dimension" />
    <attr name="shapeImage_right_bottom_radius" format="dimension" />
    <attr name="shapeImage_radius" format="dimension" />
    <!-- 图像高宽比是View高宽比的倍数 -->
    <attr name="autoCrop_height_width_ratio" format="float" />
    <!-- 图像绘制形状 -->
    <attr name="shapeImage_shape" format="enum">
        <enum name="rectangle" value="1" />
        <enum name="oval" value="2" />
    </attr>
    <!-- 背景绘制形状 -->
    <attr name="shapeImage_shape_bg" format="enum">
        <enum name="none" value="0" />
        <enum name="rectangle" value="1" />
        <enum name="oval" value="2" />
    </attr>
    <!-- 背景绘制颜色 -->
    <attr name="shapeImage_shape_bg_color" format="color"/>
    <!-- 背景绘制渐变色开始颜色 -->
    <attr name="shapeImage_shape_bg_startColor" format="color" />
    <!-- 背景绘制渐变色中间颜色 -->
    <attr name="shapeImage_shape_bg_centerColor" format="color" />
    <!-- 背景绘制渐变色结束颜色 -->
    <attr name="shapeImage_shape_bg_endColor" format="color" />
    <!-- 背景绘制渐变色开始角度 -->
    <attr name="shapeImage_shape_bg_angle" format="float" />
    <!-- 背景绘制是否渐变色 -->
    <attr name="shapeImage_shape_bg_gradient" format="boolean" />
    <!-- 背景绘制画笔宽度 -->
    <attr name="shapeImage_shape_bg_strokeWidth" format="dimension" />
    <!-- 背景绘制rectangle时的角度 -->
    <attr name="shapeImage_shape_bg_radius" format="dimension" />
    <!-- 以下是背景绘制rectangle时的各个角度 -->
    <attr name="shapeImage_shape_bg_left_top_radius" format="dimension" />
    <attr name="shapeImage_shape_bg_left_bottom_radius" format="dimension" />
    <attr name="shapeImage_shape_bg_right_top_radius" format="dimension" />
    <attr name="shapeImage_shape_bg_right_bottom_radius" format="dimension" />
```

# 最后推荐我写的另一个查看大图库
https://github.com/FlyJingFish/OpenImage

- [OpenImage](https://github.com/FlyJingFish/OpenImage)


