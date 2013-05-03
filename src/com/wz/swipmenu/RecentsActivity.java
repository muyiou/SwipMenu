package com.wz.swipmenu;

import android.app.Activity;
import android.os.Bundle;

public class RecentsActivity extends Activity {

    private RecentsPanelView mRecentsPanel;
    private boolean mShowing;

    @Override
    public void onStop() {
        mShowing = false;
        if (mRecentsPanel != null) {
            mRecentsPanel.onUiHidden();
        }
        super.onStop();
    }

    @Override
    public void onStart() {
        mShowing = true;
        if (mRecentsPanel != null) {
            mRecentsPanel.refreshViews();
        }
        super.onStart();
    }


    @Override
    public void onBackPressed() {
        dismissAndGoBack();
    }


    public void dismissAndGoBack() {
        if (mRecentsPanel != null) {
            mRecentsPanel.show(false);
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.status_bar_recent_panel);
        mRecentsPanel = (RecentsPanelView) findViewById(R.id.recents_root);

        final RecentTasksLoader recentTasksLoader = RecentTasksLoader.getInstance(this);
        recentTasksLoader.setRecentsPanel(mRecentsPanel, mRecentsPanel);

        mRecentsPanel.show(true);
        super.onCreate(savedInstanceState);
    }

    boolean isActivityShowing() {
         return mShowing;
    }
}