package service;

import java.util.List;

import activities.fragment.AsteroidTabFragments;
import adapters.ShareAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

public class SharingService {

    PackageManager pkgMngr;
    public String TAG = "SharingService";

    public void createAndShowShareIntent(final String headline, final String message) {
        pkgMngr = AsteroidTabFragments.cText.getPackageManager();
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");

        List<ResolveInfo> activityList = pkgMngr.queryIntentActivities(intent, 0);
        Log.d("share", "activityList: "+ activityList.size());
        final ShareAdapter adapter = new ShareAdapter((Activity)AsteroidTabFragments.cText, R.layout.share, activityList);

        AlertDialog.Builder builder = new AlertDialog.Builder(AsteroidTabFragments.cText);
        builder.setTitle("Share Via...");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() 
        {
            public void onClick(DialogInterface dialog, int which) {
                    ResolveInfo info = (ResolveInfo) adapter.getItem(which);
                        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        intent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, headline);
                        intent.putExtra(Intent.EXTRA_TEXT, message);
                        ((Activity)AsteroidTabFragments.cText).startActivity(intent);
            }
        });
        builder.create().show();
        }

    public Intent createShareIntent(String headline, String message) {
        Intent I= new Intent(android.content.Intent.ACTION_SEND);
        I.setType("text/plain");
        I.putExtra(Intent.EXTRA_SUBJECT, headline);
        I.putExtra(Intent.EXTRA_TEXT, message);
        return I;
    }
    
    
    public Intent createTwitterShareIntent(String message, String shareLink, Context context) {
        pkgMngr = context.getPackageManager();
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");

        try {
            List<ResolveInfo> activityList = pkgMngr.queryIntentActivities(intent, 0);
            Log.d("share", "activityList: "+ activityList.size());
            int listSize = activityList.size();
            for (int i = 0; i < listSize; i++) {
                final ResolveInfo app = activityList.get(i);
                if ("com.twitter.android.PostActivity".equals(app.activityInfo.name)) {
                    final ActivityInfo activity=app.activityInfo;
                    final ComponentName name=new ComponentName(activity.applicationInfo.packageName, activity.name);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    intent.setComponent(name);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.putExtra(Intent.EXTRA_TEXT, message);
//                    throw new ActivityNotFoundException();
                    break;
                }
            }
        }
        catch(final ActivityNotFoundException e) {
            Log.i(TAG, "twitter not found",e );
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(shareLink));
            return browserIntent;
        }

        return intent;
    }

}

