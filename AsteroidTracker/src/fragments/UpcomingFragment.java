package fragments;

import com.vitruviussoftware.bunifish.asteroidtracker.R;
import activities.fragment.AsteroidTabFragments;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class UpcomingFragment extends AsteroidFragmentBase  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(AsteroidTabFragments.contentManager.adapter_UPCOMING);
    }

}