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
}
