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
import android.widget.RelativeLayout;
import android.widget.TabHost;

public class NewsFragment extends ListFragment {

    public ListView ls4_ListView_Upcoming;
    
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
        
        ls4_ListView_Upcoming = new ListView(AsteroidTabFragments.cText);
        if(AsteroidTabFragments.UseGitService){
            processAsteroidNewsFeed();
        }
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void processAsteroidNewsFeed(){
        Thread NewsUpdate = new Thread() {
            public void run() {
                    if(AsteroidTabFragments.UseGitService){
                        AsteroidTabFragments.contentManager.List_NASA_News = AsteroidTabFragments.GitService.getLatestNews();
                    } else {
                        String HTTP_NEWS_DATA = AsteroidTabFragments.contentManager.neo_AstroidFeed.getAstroidFeedDATA(AsteroidTabFragments.contentManager.neo_AstroidFeed.URL_JPL_AsteroidNewsFeed);
                        AsteroidTabFragments.contentManager.List_NASA_News = AsteroidTabFragments.contentManager.neo_AstroidFeed.parseNewsFeed(HTTP_NEWS_DATA);
                }
                    AsteroidTabFragments.contentManager.loadAdapters_NEO_News(AsteroidTabFragments.cText);
                    ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() {
                       public void run() {
                           LoadingDialogHelper.dialog.setMessage("Loading NASA News Feed...");
                           setListAdapter(AsteroidTabFragments.contentManager.adapter_NEWS);
//                           AsteroidTabFragments.TabSpec4_News.setContent(new TabHost.TabContentFactory(){
//                               public View createTabContent(String tag)
//                               {
//                                   Log.i("contenttest", "size"+AsteroidTabFragments.contentManager.adapter_NEWS.getCount());
//                                   ls4_ListView_Upcoming.setAdapter(AsteroidTabFragments.contentManager.adapter_NEWS);
//                                   return ls4_ListView_Upcoming;
//                               }
//                           });
                           LoadingDialogHelper.closeDialog();
                           }
                   });
            }};
            NewsUpdate.start();
    }
}
