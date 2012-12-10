/*
 * @(#)ImpactDetailFragment
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

import service.NeoAstroidFeed;
import activities.ImpactRiskDetailView;
import activities.fragment.AsteroidTabFragments;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import domains.Impact;

public class ImpactDetailFragment extends AsteroidFragmentBase {

    ActionBar actionBar;
    Bundle extras;
    Impact asteroid;
    String DetailPageURL;
    String OrbitPageURL;
    String shareMessage;
    public static ShareActionProvider shareActionProvider;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        MenuInflater inflater = getActivity().getSupportMenuInflater();
        inflater.inflate(R.menu.impactsummary, menu);
        MenuItem menuItem = menu.findItem(R.id.share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        shareActionProvider.setShareHistoryFileName(null);
        shareActionProvider.setShareIntent(AsteroidTabFragments.shareSvc.createShareIntent("AsteroidTracker", shareMessage));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.Impact_Detail_ViewTitle));
        actionBar = getSherlockActivity().getSupportActionBar();
        extras = getActivity().getIntent().getExtras();
        int asteroidList = extras.getInt("position");
        asteroid = (Impact) ImpactFragment.dataList.get(asteroidList);
        
        DetailPageURL = NeoAstroidFeed.URL_NEOImpact_base+asteroid.getName().toLowerCase().replace(" ", "")+".html";
        OrbitPageURL = NeoAstroidFeed.URL_NEOImpact_OrbitalBase.replace("{NEONAME}", asteroid.getName().replace(" ", "+"));

        TextView tv = (TextView) getActivity().findViewById(R.id.shareHolder);
        String StringBy = " #AsteroidTracker <http://bit.ly/S7t9Wv>";
        shareMessage = "Asteroid(" + asteroid.getName() + ") risk level: " +getHazardLevel(Integer.parseInt(asteroid.getTorinoScale()))+ ", See Details "+DetailPageURL +" "+StringBy;
    }

    public String getHazardLevel(int torinoScale) {
        if(torinoScale == 0) {
            return getActivity().getBaseContext().getString(R.string.Impact_Level_NoHazard);
        }else if(torinoScale == 1){
            return getActivity().getBaseContext().getString(R.string.Impact_Level_Normal);
        }else if(torinoScale >= 2 || torinoScale <= 4){
            getActivity().getBaseContext().getString(R.string.Impact_Level_MeritsAttention);
        }else if(torinoScale >= 5 || torinoScale <= 7){
            getActivity().getBaseContext().getString(R.string.Impact_Level_Threatening);
        }else if(torinoScale >= 8|| torinoScale <= 10){
            getActivity().getBaseContext().getString(R.string.Impact_Level_CertainCollisions);
        }
        return "";
    }
}
