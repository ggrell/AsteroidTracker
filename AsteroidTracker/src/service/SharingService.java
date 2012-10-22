/*
 * @(#)SharingService
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
package service;

import java.util.List;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import activities.fragment.AsteroidTabFragments;
import adapters.ShareAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

public class SharingService {

    PackageManager pkgMngr;

    public void createShareIntent(final String headline, final String message, final Context cText)
    {
        
        pkgMngr = cText.getPackageManager();        
        Intent intent=new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, headline);
//        intent.putExtra(android.content.Intent.EXTRA_TEXT, message);

        List<ResolveInfo> activityList = pkgMngr.queryIntentActivities(intent, 0);
//        Log.e("share", "activityList: "+ activityList.size());
        AlertDialog.Builder builder = new AlertDialog.Builder(AsteroidTabFragments.cText);
        builder.setTitle("Share Via...");
//        try {
            final ShareAdapter adapter = new ShareAdapter((Activity)AsteroidTabFragments.cText, R.layout.share, activityList);

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() 
        {
            public void onClick(DialogInterface dialog, int which) {
                    ResolveInfo info = (ResolveInfo) adapter.getItem(which);
                    if(info.activityInfo.packageName.contains("facebook")) {
//                        new PostToFacebookDialog(context, body).show();
                    } else {
                        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        intent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, headline);
                        intent.putExtra(Intent.EXTRA_TEXT, message);
                        ((Activity)AsteroidTabFragments.cText).startActivity(intent);
                    }
            }
        });
            builder.create().show();
//        } catch(Exception e)
//        {
//            Log.e("share", "ISSUE" +e.getMessage());
//            Log.e("share", "ISSUE" +e.getCause());
//            throw new Exception();
//        }
//        return intent;
        }

    public Intent createShareIntent() {
        Intent I= new Intent(Intent.ACTION_SEND);
        I.setType("text/plain");
        I.putExtra(android.content.Intent.EXTRA_SUBJECT, "TEST - Disregard");
        I.putExtra(android.content.Intent.EXTRA_TEXT, Uri.parse("http://test.com"));
        return I;
    }
}
