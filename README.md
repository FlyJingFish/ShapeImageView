# ShapeImageView
## 支持圆图或圆角图，可绘制圆环背景边框或圆角框背景边框，除ImageView自带属性外新增4中显示模式，本库中的ShapeImageView没有操作Bitmap，可以放心使用！！！

[![](https://jitpack.io/v/FlyJingFish/ShapeImageView.svg)](https://jitpack.io/#FlyJingFish/ShapeImageView)


<img src="https://github.com/FlyJingFish/ShapeImageView/blob/master/screenshot/Screenshot_2022_0902_181056.jpg" width="405px" height="842px" alt="show" />

## 特色功能
1，支持渐变色圆角框或圆形框

2，圆角框 支持四个角独立设置角度值，圆角图像 同样支持四个角独立设置角度值

3，新增支持startCrop、endCrop、autoStartCenterCrop、autoEndCenterCrop四种显示模式

**4，autoStartCenterCrop和autoEndCenterCrop显示模式可通过设置autoCrop_height_width_ratio之后，自动在startCrop和centerCrop（endCrop和centerCrop）之间切换**

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

### 示例

```xml
<com.flyjingfish.shapeimageviewlib.ShapeImageView
    android:id="@+id/iv_centerCrop"
    android:layout_width="110dp"
    android:layout_height="110dp"
    android:layout_marginStart="10dp"
    android:padding="10dp"
    app:shape="rectangle"
    app:shape_border="rectangle"
    app:shape_border_width="3dp"
    app:shape_border_angle="45"
    app:shape_left_top_radius="8dp"
    app:shape_right_top_radius="12dp"
    app:shape_right_bottom_radius="16dp"
    app:shape_left_bottom_radius="20dp"
    app:shape_border_left_top_radius="10dp"
    app:shape_border_right_top_radius="15dp"
    app:shape_border_right_bottom_radius="20dp"
    app:shape_border_left_bottom_radius="25dp"
    app:shape_border_gradient="true"
    app:shape_border_startColor="@color/purple_200"
    app:shape_border_endColor="@color/teal_700"
    android:scaleType="centerCrop" />
```

### 外框使用

**1，需要特别设置一下padding，否则图片有些ScaleType默认显示充满ImageView**

2，渐变色外框颜色分布可通过setGradientPositions设置，默认null值均匀分布

3，渐变色想自定义更多颜色可通过setGradientColors设置

### 属性一览

|attr|format|description|
|---|:---:|:---:|
|shape|enum|图片是 rectangle矩形/oval圆形|
|shape_radius|dimension|图片四个角圆角|
|shape_left_top_radius|dimension|图片左上角圆角|
|shape_right_top_radius|dimension|图片右上角圆角|
|shape_right_bottom_radius|dimension|图片右下角圆角|
|shape_left_bottom_radius|dimension|图片左下角圆角|
|shape_start_top_radius|dimension|图片左上(Rtl:右上)角圆角|
|shape_end_top_radius|dimension|图片右上(Rtl:左上)角圆角|
|shape_end_bottom_radius|dimension|图片右下(Rtl:左下)角圆角|
|shape_start_bottom_radius|dimension|图片左下(Rtl:右下)角圆角|
|shape_border|enum|背景边框绘制形状是 none不绘制/rectangle矩形/oval圆形|
|shape_border_radius|dimension|背景边框四个角圆角|
|shape_border_left_top_radius|dimension|背景边框左上角圆角|
|shape_border_right_top_radius|dimension|背景边框右上角圆角|
|shape_border_right_bottom_radius|dimension|背景边框右下角圆角|
|shape_border_left_bottom_radius|dimension|背景边框左下角圆角|
|shape_border_start_top_radius|dimension|背景边框左上(Rtl:右上)角圆角|
|shape_border_end_top_radius|dimension|背景边框右上(Rtl:左上)角圆角|
|shape_border_end_bottom_radius|dimension|背景边框右下(Rtl:左下)角圆角|
|shape_border_start_bottom_radius|dimension|背景边框左下(Rtl:右下)角圆角|
|shape_border_color|color|背景边框绘制颜色|
|shape_border_gradient|boolean|背景边框绘制是否渐变色|
|shape_border_startColor|color|背景边框绘制渐变色开始颜色|
|shape_border_centerColor|color|背景边框绘制渐变色中间颜色|
|shape_border_endColor|color|背景边框绘制渐变色结束颜色|
|shape_border_angle|float|背景边框绘制渐变色开始角度|
|shape_border_rtl_angle|boolean|背景边框绘制渐变色开始角度是否支持镜像Rtl适配|
|shape_border_strokeWidth|dimension|背景边框绘制画笔宽度|
|autoCrop_height_width_ratio|float|图像高宽比是View高宽比的倍数|
|shapeScaleType|enum|如果设置新增显示模式设置这个，详情如下：|

|shapeScaleType|description|
|---|:---:|
|startCrop|裁剪开始左上|
|endCrop|裁剪开始右下|
|autoStartCenterCrop|自动在startCrop和centerCrop切换|
|autoEndCenterCrop|自动在endCrop和centerCrop切换|

# 最后推荐我写的另一个库，轻松实现在应用内点击小图查看大图的动画放大效果

- [OpenImage](https://github.com/FlyJingFish/OpenImage)


