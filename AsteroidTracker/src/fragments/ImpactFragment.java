package fragments;

import utils.LoadingDialogHelper;

import com.vitruviussoftware.bunifish.asteroidtracker.R;
import activities.AsteroidTrackerActivity;
import activities.fragment.AsteroidTabFragments;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;

public class ImpactFragment extends ListFragment {

    public ListView ls3_ListView_Upcoming;
    
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
        
        ls3_ListView_Upcoming = new ListView(AsteroidTabFragments.cText);
        if(AsteroidTabFragments.UseGitService){
            processImpactFeed();
        }
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setListAdapter(AsteroidTabFragments.contentManager.adapter_IMPACT);
    }
    
    public void processImpactFeed(){
        Thread ImpactUpdate = new Thread() {
            public void run() {
                    if(AsteroidTabFragments.UseGitService){
                        AsteroidTabFragments.contentManager.List_NASA_IMPACT = AsteroidTabFragments.GitService.getImpactData();
                        AsteroidTabFragments.contentManager.loadAdapters_NEO_Impact(AsteroidTabFragments.cText);
                    } else {
                        String HTTP_IMPACT_DATA =  AsteroidTabFragments.contentManager.neo_AstroidFeed.getAstroidFeedDATA(AsteroidTabFragments.contentManager.neo_AstroidFeed.URL_NASA_NEO_IMPACT_FEED);
                        AsteroidTabFragments.contentManager.List_NASA_IMPACT = AsteroidTabFragments.contentManager.neo_AstroidFeed.getImpactList(HTTP_IMPACT_DATA);
                    }
                    AsteroidTabFragments.contentManager.loadAdapters_NEO_Impact(AsteroidTabFragments.cText);
                    ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() {
                    public void run() {
                        LoadingDialogHelper.dialog.setMessage("Loading NASA Impact Risk Feed...");
                        setListAdapter(AsteroidTabFragments.contentManager.adapter_IMPACT);
//                        AsteroidTabFragments.TabSpec3_Impact.setContent(new TabHost.TabContentFactory(){
//                            public View createTabContent(String tag)
//                            {
//                                Log.i("contenttest", "size"+AsteroidTabFragments.contentManager.adapter_IMPACT.getCount());
//                                ls3_ListView_Upcoming.setAdapter(AsteroidTabFragments.contentManager.adapter_IMPACT);
//                                return ls3_ListView_Upcoming;
//                            }
//                        });
                        LoadingDialogHelper.closeDialog();
                        }
                });
            }};
        ImpactUpdate.start();
    }
    
}
