package fragments;

import activities.fragment.AsteroidTabFragments;
import android.os.Bundle;

public class NewsFragment extends AsteroidFragmentBase {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(AsteroidTabFragments.contentManager.adapter_NEWS);
    }

}