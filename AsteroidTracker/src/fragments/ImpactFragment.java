package fragments;

import utils.LoadingDialogHelper;

import com.vitruviussoftware.bunifish.asteroidtracker.R;
import domains.Impact;
import activities.fragment.AsteroidTabFragments;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ImpactFragment extends ListFragment {

    public ListView listViewAsteroid;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
            return (LinearLayout)inflater.inflate(R.layout.lists, container, false);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.listViewAsteroid = new ListView(AsteroidTabFragments.cText);
        if(AsteroidTabFragments.UseGitService){
            processImpactFeed();
        }
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    
    public void processImpactFeed(){
        Thread ImpactUpdate = new Thread() {
            public void run() {
//                    if(AsteroidTabFragments.UseGitService){
//                        AsteroidTabFragments.contentManager.List_NASA_IMPACT = AsteroidTabFragments.GitService.getImpactData();
//                        AsteroidTabFragments.contentManager.loadAdapters_NEO_Impact(AsteroidTabFragments.cText);
//                    } else {
//                        String HTTP_IMPACT_DATA =  AsteroidTabFragments.contentManager.neo_AstroidFeed.getAstroidFeedDATA(AsteroidTabFragments.contentManager.neo_AstroidFeed.URL_NASA_NEO_IMPACT_FEED);
//                        AsteroidTabFragments.contentManager.List_NASA_IMPACT = AsteroidTabFragments.contentManager.neo_AstroidFeed.getImpactList(HTTP_IMPACT_DATA);
//                    }
//                    AsteroidTabFragments.contentManager.loadAdapters_NEO_Impact(AsteroidTabFragments.cText);
                        AsteroidTabFragments.contentManager.List_NASA_IMPACT = AsteroidTabFragments.GitService.getImpactData();
                        AsteroidTabFragments.contentManager.loadAdapters_NEO_Impact(AsteroidTabFragments.cText);
                    ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() {
                    public void run() {
                            LoadingDialogHelper.dialog.setMessage("Loading NASA Impact Risk Feed...");
                            setListAdapter(AsteroidTabFragments.contentManager.adapter_IMPACT);
                            listViewAsteroid = getListView();
                            listViewAsteroid.setOnItemClickListener(ImpactRiskClickListener);
                            Log.i("closeDialog", "closeDialog Try to close Impact");
                            LoadingDialogHelper.closeDialog();
                        }
                });
            }};
        ImpactUpdate.start();
    }


    public OnItemClickListener ImpactRiskClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Object object = listViewAsteroid.getAdapter().getItem(position);    
            Impact asteroidEntity = (Impact) object;
            Intent openArticleView = new Intent(AsteroidTabFragments.cText, activities.ImpactRiskDetailView.class);
            openArticleView.putExtra("position", position);
            startActivity(openArticleView);
        };
    };

}
