package com.cins.daily.mvp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.cins.daily.R;
import com.cins.daily.mvp.ui.activities.base.BaseActivity;
import com.vansuita.materialabout.builder.AboutMe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eric on 2017/2/15.
 */

public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        loadAboutMe();
    }

    private void loadAboutMe() {
        final FrameLayout flHolder = (FrameLayout) findViewById(R.id.aboutme);

        flHolder.addView(
                AboutMe.with(this)
                        .setAppIcon(R.mipmap.ic_launcher)
                        .setAppName(R.string.app_name)
                        .setPhoto(R.mipmap.profile_picture)
                        .setCover(R.mipmap.profile_cover)
                        .setLinksAnimated(false)
                        .setDividerDashGap(13)
                        .setName("Eric Jin")
                        .setSubTitle("jinjianping2613@163.com")
                        .setLinksColumnsCount(4)
                        .setBrief("I'm warmed of mobile technologies. Ideas maker, curious and nature lover.")
                        .addGooglePlayStoreLink("")
                        .addGitHubLink("spring2613")
                        .addEmailLink("jinjianping2613@163.com")
                        .addWebsiteLink("http://spring2613.github.io/")
                        .addFiveStarsAction()
                        .addMoreFromMeAction("Eric-Jin")
                        .setVersionAsAppTitle()
                        .addShareAction(R.string.app_name)
                        .addUpdateAction()
                        .setActionsColumnsCount(2)
                        .addFeedbackAction("jinjianping2613@163.com")
                        .addIntroduceAction((Intent) null)
                        .addHelpAction((Intent) null)
                        .addChangeLogAction((Intent) null)
                        .addRemoveAdsAction((Intent) null)
                        .addDonateAction((Intent) null)
                        .build());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initInjector() {
    }

    @Override
    public void initViews() {

    }
}
