package com.zip.screentransition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

/**
 * Created by chun.yang on 2018/5/30.
 */

public class TestActivity extends AppCompatActivity {
    private FloatingActionButton fabCircle;
    private RelativeLayout root, realContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        fabCircle = (FloatingActionButton) findViewById(R.id.fab_circle);
        root = (RelativeLayout) findViewById(R.id.root);
        realContent = (RelativeLayout) findViewById(R.id.realContent);
        initData();
    }

    private void initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpEnterAnimation(); // 入场动画
            setUpExitAnimation(); // 退场动画
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpEnterAnimation() {
        Transition transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.arc_motion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    /**
     * 展示动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealShow() {
        int cx = (root.getLeft() + root.getRight()) / 2;
        int cy = (root.getTop() + root.getBottom()) / 2;

        int finalRadius = (int) Math.hypot(root.getWidth(), root.getHeight());

        // 设置圆形显示动画
        Animator anim = ViewAnimationUtils.createCircularReveal(root, cx, cy, root.getWidth() / 2, finalRadius);
        anim.setDuration(300);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                root.setBackgroundColor(ContextCompat.getColor(TestActivity.this, R.color.colorAccent));
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                root.setVisibility(View.VISIBLE);

                Animation animation1 = AnimationUtils.loadAnimation(TestActivity.this, android.R.anim.fade_in);
                animation1.setDuration(3000);
                realContent.startAnimation(animation1);
                realContent.setVisibility(View.VISIBLE);
//                listener.onRevealShow()
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpExitAnimation() {
        Fade fade = new Fade();
        getWindow().setReturnTransition(fade);
        fade.setDuration(300);
    }
}
