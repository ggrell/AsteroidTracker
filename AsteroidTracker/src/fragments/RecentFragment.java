package fragments;

import com.actionbarsherlock.widget.ShareActionProvider;
import com.actionbarsherlock.widget.ShareActionProvider.OnShareTargetSelectedListener;
import com.vitruviussoftware.bunifish.asteroidtracker.R;

import domains.Impact;
import domains.NearEarthObject;
import activities.fragment.AsteroidTabFragments;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RecentFragment extends AsteroidFragmentBase  {

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//    

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(clickListener);
        Toast.makeText(AsteroidTabFragments.cText, "TEST onActivityCreated" , Toast.LENGTH_LONG).show();
    }

    public void onResume(Bundle savedInstanceState) {
      //setAdap(AsteroidTabFragments.contentManager.adapter_RECENT);
      Toast.makeText(AsteroidTabFragments.cText, "TEST ONRESUME" , Toast.LENGTH_LONG).show();
    }


    public void onStop(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        clearAdap();
        Toast.makeText(AsteroidTabFragments.cText, "ONSTOP" , Toast.LENGTH_LONG).show();

    }

    public OnItemClickListener clickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            NearEarthObject neo = (NearEarthObject) getListAdapter().getItem(position);

            String headline = "Asteroid " + neo.getName();
            String message = "Asteroid " + neo.getName() + ",missDistance is " + neo.getMissDistance_AU_Kilometers() + "(km) " +
                    "Check it out " + neo.getURL() + " #AsteroidTracker http://bit.ly/nkxCx1";
            AsteroidTabFragments.shareSvc.createAndShowShareIntent(headline, message);
        };
    };
}

