package cn.opensrc.comnlib.utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * Author:       sharp
 * Created on:   8/12/16 4:38 PM
 * Description:  视图动画生成
 * Revisions:
 */
public final class AnimationUtils {
    private AnimationUtils() {
    }

    private static AnimationDrawable mAnimationDrawable;

    /**
     * 播放帧动画
     *
     * @param iv the ImageView
     */
    public static void playDrawableAnimation(ImageView iv) {
        mAnimationDrawable = (AnimationDrawable) iv.getBackground();
        mAnimationDrawable.start();
    }

    /**
     * 停止播放的帧动画
     */
    public static void stopDrawableAnimation() {
        if (mAnimationDrawable == null) return;
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        mAnimationDrawable = null;
    }

    /**
     * 获取AlphaAnimation
     *
     * @param fromAlpha Starting alpha value for the animation, where 1.0 means fully opaque and 0.0 means fully transparent.
     * @param toAlpha   Ending alpha value for the animation.
     * @return AlphaAnimation Object
     */
    public static Animation getAlphaAnimation(float fromAlpha, float toAlpha) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        return alphaAnimation;
    }

    /**
     * @param fromDegrees Rotation offset to apply at the start of the animation.
     * @param toDegrees   Rotation offset to apply at the end of the animation.
     * @param pivotXType  Specifies how pivotXValue should be interpreted.
     *                    One of Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF, or Animation.RELATIVE_TO_PARENT.
     * @param pivotXValue The X coordinate of the point about which the object is being rotated,
     *                    specified as an absolute number where 0 is the left edge.
     *                    This value can either be an absolute number if pivotXType is ABSOLUTE,
     *                    or a percentage (where 1.0 is 100%) otherwise.
     * @param pivotYType  Specifies how pivotYValue should be interpreted.
     *                    One of Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF, or Animation.RELATIVE_TO_PARENT.
     * @param pivotYValue The Y coordinate of the point about which the object is being rotated, specified as an absolute number where 0 is the top edge.
     *                    This value can either be an absolute number if pivotYType is ABSOLUTE, or a percentage (where 1.0 is 100%) otherwise.
     * @return a RotateAnimation instance
     */
    public static Animation getRotateAnimation(float fromDegrees,
                                               float toDegrees,
                                               int pivotXType,
                                               float pivotXValue,
                                               int pivotYType,
                                               float pivotYValue) {

        return new RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);

    }

    /**
     * @param fromX       Horizontal scaling factor to apply at the start of the animation
     * @param toX         Horizontal scaling factor to apply at the end of the animation
     * @param fromY       Vertical scaling factor to apply at the start of the animation
     * @param toY         Vertical scaling factor to apply at the end of the animation
     * @param pivotXType  Specifies how pivotXValue should be interpreted. One of Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF, or Animation.RELATIVE_TO_PARENT.
     * @param pivotXValue The X coordinate of the point about which the object is being scaled, specified as an absolute number where 0 is the left edge.
     *                    (This point remains fixed while the object changes size.) This value can either be an absolute number if pivotXType is ABSOLUTE,
     *                    or a percentage (where 1.0 is 100%) otherwise.
     * @param pivotYType  Specifies how pivotYValue should be interpreted. One of Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF, or Animation.RELATIVE_TO_PARENT.
     * @param pivotYValue The Y coordinate of the point about which the object is being scaled, specified as an absolute number where 0 is the top edge.
     *                    (This point remains fixed while the object changes size.) This value can either be an absolute number if pivotYType is ABSOLUTE,
     *                    or a percentage (where 1.0 is 100%) otherwise.
     * @return a ScaleAnimation instance
     */
    public static Animation getScaleAnimation(float fromX,
                                              float toX,
                                              float fromY,
                                              float toY,
                                              int pivotXType,
                                              float pivotXValue,
                                              int pivotYType,
                                              float pivotYValue) {

        return new ScaleAnimation(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType, pivotYValue);

    }

    /**
     * @param fromXType  Specifies how fromXValue should be interpreted. One of Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF, or Animation.RELATIVE_TO_PARENT.
     * @param fromXValue Change in X coordinate to apply at the start of the animation. This value can either be an absolute number if fromXType is ABSOLUTE,
     *                   or a percentage (where 1.0 is 100%) otherwise.
     * @param toXType    Specifies how toXValue should be interpreted. One of Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF, or Animation.RELATIVE_TO_PARENT.
     * @param toXValue   Change in X coordinate to apply at the end of the animation. This value can either be an absolute number if toXType is ABSOLUTE,
     *                   or a percentage (where 1.0 is 100%) otherwise.
     * @param fromYType  Specifies how fromYValue should be interpreted. One of Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF, or Animation.RELATIVE_TO_PARENT.
     * @param fromYValue Change in Y coordinate to apply at the start of the animation. This value can either be an absolute number if fromYType is ABSOLUTE,
     *                   or a percentage (where 1.0 is 100%) otherwise.
     * @param toYType    Specifies how toYValue should be interpreted. One of Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF, or Animation.RELATIVE_TO_PARENT.
     * @param toYValue   Change in Y coordinate to apply at the end of the animation.
     *                   This value can either be an absolute number if toYType is ABSOLUTE, or a percentage (where 1.0 is 100%) otherwise.
     * @return a TranslateAnimation instance
     */
    public static Animation getTranslateAnimation(int fromXType,
                                                  float fromXValue,
                                                  int toXType,
                                                  float toXValue,
                                                  int fromYType,
                                                  float fromYValue,
                                                  int toYType,
                                                  float toYValue) {

        return new TranslateAnimation(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue, toYType, toYValue);

    }

    /**
     * @param animation      the needed set attributes Animation Object.
     * @param durationMillis Duration in milliseconds.
     * @param fillAfter      true if the animation should apply its transformation after it ends.
     * @param interpolator   The interpolator which defines the acceleration curve.
     * @param repeatCount    the number of times the animation should be repeated.
     * @param startOffset    When this Animation should start, in milliseconds from the start time of the root AnimationSet.
     * @return the first Animation parameter.
     */
    public static Animation setAnimationAttrs(Animation animation,
                                              long durationMillis,
                                              boolean fillAfter,
                                              Interpolator interpolator,
                                              int repeatCount,
                                              long startOffset) {

        animation.setDuration(durationMillis);
        animation.setFillAfter(fillAfter);
        if (interpolator == null)
            interpolator = new LinearInterpolator();
        animation.setInterpolator(interpolator);
        animation.setRepeatCount(repeatCount);
        animation.setStartOffset(startOffset);
        return animation;

    }

    /**
     * @param context Application Context
     * @param id      the animation xml id
     * @return Animation object from xml
     */
    public static Animation buildAnimationFromXml(Context context, int id) {

        return android.view.animation.AnimationUtils.loadAnimation(context, id);

    }

    /**
     * @param context Application Context
     * @param id      the layoutAnimation xml id
     * @return LayoutAnimationController Object
     */
    public static LayoutAnimationController buildLayoutAnimationControllerFromXml(Context context, int id) {
        return android.view.animation.AnimationUtils.loadLayoutAnimation(context, id);
    }

    /**
     * @param controller   need set attrs LayoutAnimationController object
     * @param order        one of ORDER_NORMAL, ORDER_REVERSE or ORDER_RANDOM
     * @param delay        a fraction of the animation duration
     * @param interpolator the interpolator
     * @return the first parameter of this method
     */
    public static LayoutAnimationController setLayoutAnimationControllerAttrs(LayoutAnimationController controller, int order, float delay, Interpolator interpolator) {
        controller.setOrder(order);
        controller.setDelay(delay);
        controller.setInterpolator(interpolator);
        return controller;
    }

    /**
     * @param context Application Context
     * @param id      the gridLayoutAnimation xml id
     * @return GridLayoutAnimationController Object
     */
    public static GridLayoutAnimationController buildGridLayoutAnimationControllerFromXml(Context context, int id) {
        return (GridLayoutAnimationController) android.view.animation.AnimationUtils.loadLayoutAnimation(context, id);
    }

    /**
     * controller need set attrs GridLayoutAnimationController object
     *
     * @param controller GridLayoutAnimationController object
     * @param rowDelay          a fraction of the animation duration
     * @param columnDelay       a fraction of the animation duration
     * @param direction         the direction of the animation
     * @param directionPriority the direction priority of the animation
     * @return the first parameter of this method
     */
    public static GridLayoutAnimationController setGridLayoutAnimationControllerAttrs(GridLayoutAnimationController controller,
                                                                                      float rowDelay,
                                                                                      float columnDelay,
                                                                                      int direction,
                                                                                      int directionPriority) {

        controller.setRowDelay(rowDelay);
        controller.setColumnDelay(columnDelay);
        controller.setDirection(direction);
        controller.setDirectionPriority(directionPriority);
        return controller;

    }

}
