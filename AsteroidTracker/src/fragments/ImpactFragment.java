package fragments;

import domains.Impact;
import activities.AsteroidTrackerActivity;
import activities.fragment.AsteroidTabFragments;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ImpactFragment extends AsteroidFragmentBase {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(ImpactRiskClickListener);
    }

    public OnItemClickListener ImpactRiskClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
//            Object object = getListAdapter().getItem(position);    
//            Impact asteroidEntity = (Impact) object;
            Intent openArticleView = new Intent(getActivity(), activities.ImpactRiskDetailView.class);
            openArticleView.putExtra("position", position);
            startActivity(openArticleView);
        };
    };
}