/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wz.swipmenu;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
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
            final ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

            final List<ActivityManager.RecentTaskInfo> recentTasks =
                    am.getRecentTasks(2,
                            ActivityManager.RECENT_WITH_EXCLUDED |
                            ActivityManager.RECENT_IGNORE_UNAVAILABLE);
            if (recentTasks.size() > 1 &&
                    mRecentsPanel.simulateClick(recentTasks.get(1).persistentId)) {
                // recents panel will take care of calling show(false) through simulateClick
                return;
            }
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