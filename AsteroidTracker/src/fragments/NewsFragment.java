package fragments;

import domains.News;
import activities.AsteroidTrackerActivity;
import activities.fragment.AsteroidTabFragments;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class NewsFragment extends AsteroidFragmentBase {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(Asteroid_NewsArticle_ClickListener);
    }
    
    public OnItemClickListener Asteroid_NewsArticle_ClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Object object = getListAdapter().getItem(position);    
            News asteroidEntity = (News) object;
            Intent i = new Intent(Intent.ACTION_VIEW);
            try {
                i.setData(Uri.parse(asteroidEntity.artcileUrl));
                startActivity(i);
            } catch (ActivityNotFoundException e){
                Log.d("News", "ActivityNotFound on news article listner", e);
            }
        };
    };
}