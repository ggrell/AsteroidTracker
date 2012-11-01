package fragments;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import domains.NearEarthObject;
import activities.fragment.AsteroidTabFragments;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class UpcomingFragment extends AsteroidFragmentBase  {

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
            String headline = "Asteroid " + " " + neo.getName() + ",";
            String message = "#Asteroid " + neo.getName() + ",missDistance is " + neo.getMissDistance_AU_Kilometers() + "km " +
                    "See Details "  + neo.getURL() + " #AsteroidTracker<http://bit.ly/nkxCx1>" ;
            AsteroidTabFragments.shareSvc.createAndShowShareIntent(headline, message);
        };
    };

}