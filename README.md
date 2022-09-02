# ShapeImageView
圆图或圆角图，可绘制圆环背景或圆角框背景

[![](https://jitpack.io/v/FlyJingFish/ShapeImageView.svg)](https://jitpack.io/#FlyJingFish/ShapeImageView)


<img src="https://github.com/FlyJingFish/ShapeImageView/blob/master/screenshot/Screenshot_20220819_135240.jpg" width="405px" height="842px" alt="show" />


使用示例：
    可以看示例代码Demo

第一步，根目录build.gradle

```gradle
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```
第二步，需要引用的build.gradle （最新版本[![](https://jitpack.io/v/FlyJingFish/ShapeImageView.svg)](https://jitpack.io/#FlyJingFish/ShapeImageView)）

```gradle
    dependencies {
        implementation 'com.github.FlyJingFish:FormatTextViewLib:latest.release.here'
    }
```
第三步，使用

属性一览

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
    <attr name="shapeImage_shape" format="enum">
        <enum name="rectangle" value="1" />
        <enum name="oval" value="2" />
    </attr>
    <attr name="shapeImage_shape_bg" format="enum">
        <enum name="none" value="0" />
        <enum name="rectangle" value="1" />
        <enum name="oval" value="2" />
    </attr>
    <attr name="shapeImage_shape_bg_color" format="color"/>
    <attr name="shapeImage_shape_bg_startColor" format="color" />
    <attr name="shapeImage_shape_bg_centerColor" format="color" />
    <attr name="shapeImage_shape_bg_endColor" format="color" />
    <attr name="shapeImage_shape_bg_angle" format="float" />
    <attr name="shapeImage_shape_bg_gradient" format="boolean" />
    <attr name="shapeImage_shape_bg_strokeWidth" format="dimension" />
    <attr name="shapeImage_shape_bg_radius" format="dimension" />
    <attr name="shapeImage_shape_bg_left_top_radius" format="dimension" />
    <attr name="shapeImage_shape_bg_left_bottom_radius" format="dimension" />
    <attr name="shapeImage_shape_bg_right_top_radius" format="dimension" />
    <attr name="shapeImage_shape_bg_right_bottom_radius" format="dimension" />
```


