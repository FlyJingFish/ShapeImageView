# ShapeImageView

[![Maven central](https://img.shields.io/maven-central/v/io.github.FlyJingFish/ShapeImageView)](https://central.sonatype.dev/search?q=io.github.FlyJingFish)
[![GitHub stars](https://img.shields.io/github/stars/FlyJingFish/ShapeImageView.svg)](https://github.com/FlyJingFish/ShapeImageView/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/FlyJingFish/ShapeImageView.svg)](https://github.com/FlyJingFish/ShapeImageView/network)
[![GitHub issues](https://img.shields.io/github/issues/FlyJingFish/ShapeImageView.svg)](https://github.com/FlyJingFish/ShapeImageView/issues)
[![GitHub license](https://img.shields.io/github/license/FlyJingFish/ShapeImageView.svg)](https://github.com/FlyJingFish/ShapeImageView/blob/master/LICENSE)


## [中文版使用说明](https://github.com/FlyJingFish/ShapeImageView/blob/master/README-zh.md)

## ShapeImageView supports circle or rounded corners, and can draw circle background borders or rounded box background borders. In addition to the built-in properties of ImageView, 4 new display modes are added;
## AlmightyShapeImageView supports the display of arbitrary graphics, only you can't think of it, you can't do it without it;
## ImageView in this library does not operate Bitmap, you can use it with confidence! ! !


ShapeImageView|AlmightyShapeImageView
 ----- | ----- 
<img src="https://github.com/FlyJingFish/ShapeImageView/blob/master/screenshot/Screenshot_20221011_144810.jpg" width="400px" height="800px" alt="show" />|<img src="https://github.com/FlyJingFish/ShapeImageView/blob/master/screenshot/Screenshot_20221031_123252.jpg" width="400px" height="800px" alt="show" />



## Special function

1、**ShapeImageView** supports gradient rounded borders or rounded borders, unlimited image settings, perfectly compatible with all image loading libraries

2、 The **ShapeImageView** rectangular image supports the independent setting of the angle value of the four corners, and the rectangular border also supports the independent setting of the angle value of the four corners.

3、**ShapeImageView** supports four display modes: startCrop, endCrop, autoStartCenterCrop, autoEndCenterCrop

（**AutoStartCenterCrop and autoEndCenterCrop display mode can be automatically switched between startCrop and centerCrop (endCrop and centerCrop) after setting autoCrop_height_width_ratio**）

4、**AlmightyShapeImageView** is perfectly compatible with all image loading libraries, supports defining arbitrary graphics, only you can't think of it without it



## The first step, the root directory build.gradle

```gradle
    allprojects {
        repositories {
            ...
            maven { url "https://s01.oss.sonatype.org/content/groups/public" }
        }
    }
```
## The second step, you need to reference build.gradle

```gradle
    dependencies {
        implementation 'io.github.FlyJingFish:ShapeImageView:1.4.9'
    }
```
## The third step, instructions for use

# 一、ShapeImageView Instructions for Use

### ShapeImageView example
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

### Outer frame use

**1，You need to set padding specially, otherwise some ScaleType of the picture will be filled with ImageView by default.**

2，The color distribution of the gradient frame can be set through **setGradientPositions**, and the default null value is evenly distributed

3，Gradient color If you want to customize more colors, you can set it through **setGradientColors**

### List of properties

| attr                             |  format   |                                         description                                         |
|----------------------------------|:---------:|:-------------------------------------------------------------------------------------------:|
| shape                            |   enum    |                           The picture is a rectangle/oval circle                            |
| shape_radius                     | dimension |                          The four corners of the image are rounded                          |
| shape_left_top_radius            | dimension |                        The upper left corner of the image is rounded                        |
| shape_right_top_radius           | dimension |                    Round corners in the upper right corner of the image                     |
| shape_right_bottom_radius        | dimension |                       The bottom right corner of the image is rounded                       |
| shape_left_bottom_radius         | dimension |                       The bottom left corner of the image is rounded                        |
| shape_start_top_radius           | dimension |                       image top left (Rtl: top right) corner rounded                        |
| shape_end_top_radius             | dimension |                       image top right (Rtl: top left) corner rounded                        |
| shape_end_bottom_radius          | dimension |                   picture bottom right (Rtl: bottom left) corner rounded                    |
| shape_start_bottom_radius        | dimension |                   picture bottom left (Rtl: bottom right) corner rounded                    |
| shape_border                     |   enum    | The background border drawing shape is none without drawing/rectangle rectangle/oval circle |
| shape_border_radius              | dimension |                    The four corners of the background border are rounded                    |
| shape_border_left_top_radius     | dimension |                   The top left corner of the background border is rounded                   |
| shape_border_right_top_radius    | dimension |             rounded corners in the upper right corner of the background border              |
| shape_border_right_bottom_radius | dimension |                 The bottom right corner of the background border is rounded                 |
| shape_border_left_bottom_radius  | dimension |                 The bottom left corner of the background border is rounded                  |
| shape_border_start_top_radius    | dimension |                 background border top left (Rtl: top right) corner rounded                  |
| shape_border_end_top_radius      | dimension |                 background border top right (Rtl: top left) corner rounded                  |
| shape_border_end_bottom_radius   | dimension |              background border bottom right (Rtl: bottom left) corner rounded               |
| shape_border_start_bottom_radius | dimension |       The bottom left (Rtl: bottom right) corner of the background border is rounded        |
| shape_border_color               |   color   |                               Background border drawing color                               |
| shape_border_gradient            |  boolean  |                  Whether the background border is drawn in gradient color                   |
| shape_border_startColor          |   color   |                    The background border draw gradient color start color                    |
| shape_border_centerColor         |   color   |                    The background border draws the gradient middle color                    |
| shape_border_endColor            |   color   |                  The background border draws the gradient color end color                   |
| shape_border_angle               |   float   |                   Start angle of background border drawing gradient color                   |
| shape_border_rtl_angle           |  boolean  |    Does the gradient start angle of the background border support mirror Rtl adaptation     |
| shape_border_strokeWidth         | dimension |                          width of background border drawing brush                           |
| autoCrop_height_width_ratio      |   float   |                The image aspect ratio is a multiple of the View aspect ratio                |
| shapeScaleType                   |   enum    |          If you set the new display mode to set this, the details are as follows:           |

| shapeScaleType      |                      description                      |
|---------------------|:-----------------------------------------------------:|
| startCrop           |                  Crop start top left                  |
| endCrop             |                Crop start bottom right                |
| autoStartCenterCrop | Automatically switch between startCrop and centerCrop |
| autoEndCenterCrop   |  Automatically switch between endCrop and centerCrop  |

# 二、AlmightyShapeImageView Instructions for Use

### AlmightyShapeImageView example（Built-in library ❤️ [ic_vector_heart](https://github.com/FlyJingFish/ShapeImageView/tree/master/library/src/main/res/drawable) 和 ⭐️ [ic_vector_star](https://github.com/FlyJingFish/ShapeImageView/tree/master/library/src/main/res/drawable)）

**The key to use is to set a graphic resource map (that is, almighty_shape_resource). If you want to display a picture of any shape, you can achieve it as long as you set a resource map.**

```xml
<com.flyjingfish.shapeimageviewlib.AlmightyShapeImageView
    android:id="@+id/iv1"
    android:layout_width="110dp"
    android:layout_height="110dp"
    android:layout_marginStart="5dp"
    android:src="@mipmap/ic_launcher"
    app:almighty_shape_resource="@drawable/ic_vector_heart"
    android:scaleType="centerCrop" />
```

### List of properties

| attr                     |  format   |               description                |
|--------------------------|:---------:|:----------------------------------------:|
| almighty_shape_resource  | reference |              Shape Resource              |
| almighty_shape_scaleType |   enum    | The display type of the drawing resource |


| almighty_shape_scaleType         |                                                                                           description                                                                                            |
|----------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| followImageViewKeepResourceScale |                                       The graphics resource follows the ImageView's ScaleType type and maintains the aspect ratio of the graphics resource                                       |
| followImageViewFullImage         |                          The graphics resource follows the ImageView's ScaleType but fills the width and height of the image (the graphics may be stretched relatively)                          |
| alwaysFixXY                      | The graphics resource will not follow the ScaleType of the ImageView, and always fill the ImageView container (setting this property to some ScaleType will cause the graphics to be incomplete) |

### Method

| method           |      type      |                     description                      |
|------------------|:--------------:|:----------------------------------------------------:|
| setShapeResource |    Drawable    |               Shape Resource Drawable                |
| setShapeResource |  DrawableRes   |                  Shape resource id                   |
| setShapeResource | ShapeScaleType | Sets the display type for drawing graphics resources |

### Graphic resource setting prompt

**almighty_shape_resource** is the image resource that allows the UI to export the graphics in advance. It can be a shape, a vector, or a png image, but **It is strongly recommended to use shape or vector vector graphics for better results**

If you use png or svg resources, you can convert them to vector. For details, see my blog:

- [Blog Instructions](https://blog.csdn.net/u013077428/article/details/127613904)

## Common problem

1. In some graphics, the pictures are not displayed completely after changing attributes such as rotation, rotationX, rotationY, etc.

- Solution: Set the margin property to solve

### Extra episode: If you want to use network images for your graphics resource map (that is, not packaged into apk)

You can download network pictures through Glide and then call **setShapeResource** to set the graphics

For example, download and set via Glide:

```java
Glide.with(context).load("Internet connection").into(new CustomTarget<Drawable>() {
    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
        imageView.setShapeResource(resource);
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {

    }
});
```

# Finally, I recommend another library I wrote, which can easily realize the animation zoom effect of clicking on the small image to view the large image in the application

- [OpenImage](https://github.com/FlyJingFish/OpenImage) (current library built in)

- [Homepage view more open source libraries](https://github.com/FlyJingFish)


