package com.cins.daily.mvp.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.cins.daily.R;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Eric on 2017/1/21.
 */

public class SplashActivity extends AppCompatActivity {

    private static final int ANIMATION_DURATION = 2000;
    private static final float SCALE_END = 1.13F;

    private static final int[] SPLASH_ARRAY = {
            R.drawable.splash0,
            R.drawable.splash1,
            R.drawable.splash2,
            R.drawable.splash3,
            R.drawable.splash4,

            R.drawable.splash6,
            R.drawable.splash7,
            R.drawable.splash8,
            R.drawable.splash9,
            R.drawable.splash10,
            R.drawable.splash11,
            R.drawable.splash12,
            R.drawable.splash13,
            R.drawable.splash14,
            R.drawable.splash15,
            R.drawable.splash16,
    };

    @BindView(R.id.iv_splash)
    ImageView mIvSplash;

    @BindView(R.id.splash_app_name)
    TextView mSplashAppName;
    @BindView(R.id.splash_slogan)
    TextView mSplashSlogan;
    @BindView(R.id.splash_version_name)
    TextView mSplashVersionName;
    @BindView(R.id.splash_copyright)
    TextView mSplashCopyright;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash1);
        ButterKnife.bind(this);

        Random r = new Random(SystemClock.elapsedRealtime());
        mIvSplash.setImageResource(SPLASH_ARRAY[r.nextInt(SPLASH_ARRAY.length)]);
        animateImage();
    }

    private void animateImage() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mIvSplash, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mIvSplash, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                NewsActivity.start(SplashActivity.this);
                SplashActivity.this.finish();
            }
        });
    }

    private void finishActivity() {
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        startActivity(new Intent(SplashActivity.this, NewsActivity.class));
                        overridePendingTransition(0, android.R.anim.fade_out);
                        finish();
                    }
                });
    }
}
