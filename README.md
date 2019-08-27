# AnimationDemo
Android Animation demo, contains simple examples for different Android animation types.

## Bitmap animation - AnimationDrawable
[Official documentation](https://developer.android.com/guide/topics/graphics/drawable-animation.html)

1. Create an .xml file describing the sequence of your frames:
```xml
<animation-list xmlns:android="http://schemas.android.com/apk/res/android"
    android:oneshot="false">

    <item android:drawable="@drawable/pic1" android:duration="80"/>
    <item android:drawable="@drawable/pic2" android:duration="80"/>
    <item android:drawable="@drawable/pic3" android:duration="80"/>

</animation-list>
```

2. Describe an ImageView in your activity's .xml file, and set the xml file you just created as its background resource:
```java
ImageView imageView = findViewById(R.id.imageView);
imageView.setBackgroundResource(R.drawable.my_custom_animation_xml);
```
3. Create an AnimationDrawable object using your ImageView object:
```java
AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
animationDrawable.start();
```

## Motion using spring physics - SpringAnimation
[Official documentation](https://developer.android.com/guide/topics/graphics/spring-animation.html)

1. Add the DynamicAnimation dependency:
```gradle
implementation 'com.android.support:support-dynamic-animation:28.0.0'
```
2. Create a method for creating a SpringAnimation object for the view given as a parameter, with the final position, stiffness and dampingRatio specified:
```java
private SpringAnimation createSpringAnimation(View view,
                                              DynamicAnimation.ViewProperty property,
                                              Float finalPosition, Float stiffness, Float dampingRatio){
    SpringForce springForce = new SpringForce(finalPosition);
    springForce.setDampingRatio(dampingRatio);
    springForce.setStiffness(stiffness);
    SpringAnimation springAnimation = new SpringAnimation(view, property);
    springAnimation.setSpring(springForce);
    return springAnimation;
}
```
3. Create an x-axis and or y-axis SpringAnimation for the ImageView you want to animate:
```java
final View imageView = findViewById(R.id.bug);
imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
    @Override
    public void onGlobalLayout() {
        xAnimation = createSpringAnimation(imageView, SpringAnimation.X, imageView.getX(), STIFFNESS, DAMPING_RATIO);
        yAnimation = createSpringAnimation(imageView, SpringAnimation.Y, imageView.getY(), STIFFNESS, DAMPING_RATIO);
        imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }
});
```
4. Override onTouch event for your imageView:
```java
imageView.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                xAnimation.cancel();
                yAnimation.cancel();
                break;
            case MotionEvent.ACTION_MOVE:
                imageView.animate()
                        .x(event.getRawX() + dX)
                        .y(event.getRawY() + dY)
                        .setDuration(0)
                        .start();
                break;
            case MotionEvent.ACTION_UP:
                xAnimation.start();
                yAnimation.start();
                break;
        }
        return true;
    }
});
```
## Moving Views - FlingAnimation
[Official documentation](https://developer.android.com/guide/topics/graphics/fling-animation.html)
1. Add the DynamicAnimation dependency:
```gradle
implementation 'com.android.support:support-dynamic-animation:28.0.0'
```
2. Create a method that returns a FlingAnimation object for the view given as parameter, using the ViewProperty, velocity and friction given as parameters:
```java
private FlingAnimation createFlingAnimation(View view,
                                            DynamicAnimation.ViewProperty property,
                                            Float velocity, Float friction) {
    FlingAnimation flingAnimation = new FlingAnimation(view, property);
    flingAnimation.setStartVelocity(velocity);
    flingAnimation.setFriction(friction);
    return flingAnimation;
}
```
3. Fetch the ImageView you want to animate and override its onTouch method:
```java
imageView.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                downXValue = event.getX();
                downYValue = event.getY();
                break;
            }
            case MotionEvent.ACTION_UP: {
                float currentX = event.getX();
                float currentY = event.getY();
                animX = createFlingAnimation(imageView, DynamicAnimation.X, currentX - downXValue, 0.5f);
                animX.start();
                animY = createFlingAnimation(imageView, DynamicAnimation.Y, currentY - downYValue, 0.5f);
                animY.start();
                break;
            }
        }
        return true;
    }
});
```
## Crossfade Animation
[Official documentation](https://developer.android.com/training/animation/reveal-or-hide-view.html#Crossfade)
1. In your activity's layout file add the two views that you want to interchange:
```xml
<ImageView
    android:id="@+id/cross_image_1"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_centerInParent="true"
    android:background="#30a000"/>
<ImageView
    android:id="@+id/cross_image_2"
    android:layout_width="match_parent"
    android:layout_height="362dp"
    android:layout_alignParentBottom="true"
    android:src="@drawable/pepe" />
```
2. Set the visibility of the background view to View.GONE:
```java
foreground = findViewById(R.id.cross_image_1);
background = findViewById(R.id.cross_image_2);
background.setVisibility(View.GONE);
```
3. To crossfade, set the transparency of the background view to 0 and make it visible. Using .animate(), change the transparency of the background view to 1, and the transparency of the foreground view to 0:
```java
background.setAlpha(0f);
background.setVisibility(View.VISIBLE);
background.animate()
        .alpha(1f)
        .setDuration(longAnimationDuration)
        .setListener(null);
foreground.animate()
        .alpha(0f)
        .setDuration(longAnimationDuration)
        .setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                foreground.setVisibility(View.GONE);
            }
        });
```
## Card Flip Animation
[Official documentation](https://developer.android.com/training/animation/reveal-or-hide-view.html#CardFlip)
1. Create four animator .xml files, two describing the animation to the left and two the animation to the right:
card_flip_left_in.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- Before rotating, immediately set the alpha to 0. -->
    <objectAnimator
        android:valueFrom="1.0"
        android:valueTo="0.0"
        android:propertyName="alpha"
        android:duration="0" />

    <!-- Rotate. -->
    <objectAnimator
        android:valueFrom="-180"
        android:valueTo="0"
        android:propertyName="rotationY"
        android:interpolator="@android:interpolator/accelerate_decelerate"
        android:duration="@integer/card_flip_time_full" />

    <!-- Half-way through the rotation (see startOffset), set the alpha to 1. -->
    <objectAnimator
        android:valueFrom="0.0"
        android:valueTo="1.0"
        android:propertyName="alpha"
        android:startOffset="@integer/card_flip_time_half"
        android:duration="1" />
</set>
```
card_flip_left_out.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- Rotate. -->
    <objectAnimator
        android:valueFrom="0"
        android:valueTo="180"
        android:propertyName="rotationY"
        android:interpolator="@android:interpolator/accelerate_decelerate"
        android:duration="@integer/card_flip_time_full" />

    <!-- Half-way through the rotation (see startOffset), set the alpha to 0. -->
    <objectAnimator
        android:valueFrom="1.0"
        android:valueTo="0.0"
        android:propertyName="alpha"
        android:startOffset="@integer/card_flip_time_half"
        android:duration="1" />
</set>
```
card_flip_right_in.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- Before rotating, immediately set the alpha to 0. -->
    <objectAnimator
        android:valueFrom="1.0"
        android:valueTo="0.0"
        android:propertyName="alpha"
        android:duration="0" />

    <!-- Rotate. -->
    <objectAnimator
        android:valueFrom="180"
        android:valueTo="0"
        android:propertyName="rotationY"
        android:interpolator="@android:interpolator/accelerate_decelerate"
        android:duration="@integer/card_flip_time_full" />

    <!-- Half-way through the rotation (see startOffset), set the alpha to 1. -->
    <objectAnimator
        android:valueFrom="0.0"
        android:valueTo="1.0"
        android:propertyName="alpha"
        android:startOffset="@integer/card_flip_time_half"
        android:duration="1" />
</set>
```
card_flip_right_out.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- Rotate. -->
    <objectAnimator
        android:duration="@integer/card_flip_time_full"
        android:interpolator="@android:interpolator/accelerate_decelerate"
        android:propertyName="rotationY"
        android:valueFrom="0"
        android:valueTo="-180" />

    <!-- Half-way through the rotation (see startOffset), set the alpha to 0. -->
    <objectAnimator
        android:duration="1"
        android:propertyName="alpha"
        android:startOffset="@integer/card_flip_time_half"
        android:valueFrom="1.0"
        android:valueTo="0.0" />
</set>
```
2. Create the main card flip activity that extends FragmentActivity, and write the two fragments:
```java
public class CardFlipAnimationActivity extends FragmentActivity {
...
    public static class CardFrontFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_card_front, container, false);
        }
    }
    /**
     * A fragment representing the back of the card.
     */
    public static class CardBackFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_card_back, container, false);
        }
    }
}
```
3. Create an empty RelativeLayout in your CardFlipAnimationActivity's layout file:
```xml
<RelativeLayout
    android:id="@+id/activity_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```
4. Create your fragment's layout files.
5. Set the CardFrontFragment as the starting fragment:
```java
getSupportFragmentManager()
        .beginTransaction()
        .add(R.id.activity_layout, new CardFrontFragment())
        .commit();
```
6. Change fragments by flipping them:
```java
if (showingBack) {
            getSupportFragmentManager().popBackStack();
            showingBack = false;
            return;
}
showingBack = true;

getSupportFragmentManager().beginTransaction()
        .setCustomAnimations(
            R.animator.card_flip_right_in,
            R.animator.card_flip_right_out,
            R.animator.card_flip_left_in,
            R.animator.card_flip_left_out)
        .replace(R.id.activity_layout, new CardBackFragment())
        .addToBackStack(null)
        .commit();
 ```
