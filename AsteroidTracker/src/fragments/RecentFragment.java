/*
 * @(#)RecentFragOld
 *
 * Copyright 2012 by Constant Contact Inc.,
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
package fragments;

import com.actionbarsherlock.widget.ShareActionProvider;
import com.actionbarsherlock.widget.ShareActionProvider.OnShareTargetSelectedListener;

import domains.Impact;
import domains.NearEarthObject;
import activities.fragment.AsteroidTabFragments;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class RecentFragment extends AsteroidFragmentBase  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(clickListener);
    }

    
    public OnItemClickListener clickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            NearEarthObject neo = (NearEarthObject) getListAdapter().getItem(position);

            String headline = "Asteroid " + neo.getName();
            String message = "Asteroid " + neo.getName() + ",missDistance is " + neo.getMissDistance_AU_Kilometers() + "(km) " +
                    "Check it out " + neo.getURL() + " #AsteroidTracker http://bit.ly/nkxCx1";
            AsteroidTabFragments.shareSvc.createShareIntent(headline, message, getActivity().getApplicationContext());
//            startActivity(Intent.createChooser( AsteroidTabFragments.shareSvc.createShareIntent(headline, message, getActivity().getApplicationContext()), "Share via"));
        
        };
    };
}

