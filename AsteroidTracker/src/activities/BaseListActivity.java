/*
 * @(#)BaseListActivity
 *
 * Copyright 2013 by Constant Contact Inc.,
 * Waltham, MA 02451, USA
 * Phone: (781) 472-8100
 * Fax: (781) 472-8101
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Constant Contact, Inc. created for Constant Contact, Inc.
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Constant Contact, Inc.
 * 
 * History
 *
 * Date         Author      Comments
 * ====         ======      ========
 *
 * 
 **/
package activities;

import com.actionbarsherlock.app.SherlockListActivity;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

public class BaseListActivity extends SherlockListActivity {

    protected Tracker defaultTracker;

    @Override
    public void onStart() {
      super.onStart();
      EasyTracker.getInstance().activityStart(this);
      defaultTracker = EasyTracker.getTracker();
    }

    @Override
    public void onStop() {
      super.onStop();
      EasyTracker.getInstance().activityStop(this);
    }


    public void sentTrackingEvent(String Category, String Action, String Label, Long value) {
        try {
            defaultTracker.sendEvent(Category, Action, Label, value);
        } catch(NullPointerException e) {
            EasyTracker.getInstance().setContext(this);
            defaultTracker = EasyTracker.getTracker();
            defaultTracker.sendEvent(Category, Action, Label, value);
        }
    }
}
