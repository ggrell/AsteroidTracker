package fragments;

import utils.LoadingDialogHelper;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import activities.fragment.AsteroidTabFragments;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;

public class UpcomingFragment extends ListFragment  {
    
    public static ListView ls2_ListView_Upcoming;
    
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
        
        ls2_ListView_Upcoming = new ListView(AsteroidTabFragments.cText);
        if(AsteroidTabFragments.UseGitService){
            processNEOFeedUpcoming();
        }
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setListAdapter(AsteroidTabFragments.contentManager.adapter_UPCOMING);
    }

    public void processNEOFeedUpcoming(){
        Thread checkUpdate = new Thread() {
        public void run() {
            AsteroidTabFragments.contentManager.List_NASA_UPCOMING =  AsteroidTabFragments.GitService.getNEOList(AsteroidTabFragments.GitService.URI_UPCOMING);
            AsteroidTabFragments.contentManager.loadAdapters_NEO_Upcoming(AsteroidTabFragments.cText);
            ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() {
                   public void run() {
                       LoadingDialogHelper.dialog.setMessage("Loading NASA NEO Upcoming Feed...");
                       setListAdapter(AsteroidTabFragments.contentManager.adapter_UPCOMING);
//                       AsteroidTabFragments.TabSpec2_Upcoming.setContent(new TabHost.TabContentFactory(){
//                           public View createTabContent(String tag)
//                           {
//                               Log.i("contenttest", "size"+AsteroidTabFragments.contentManager.adapter_UPCOMING.getCount());
//                               ls2_ListView_Upcoming.setAdapter(AsteroidTabFragments.contentManager.adapter_UPCOMING);
//                               return ls2_ListView_Upcoming;
//                           }
//                       });
                       LoadingDialogHelper.closeDialog();
                   }
            });
        }};
        checkUpdate.start();
        }
    
}
