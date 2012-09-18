package fragments;

import java.util.List;
import utils.LoadingDialogHelper;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import activities.fragment.AsteroidTabFragments;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class NewsFragment extends ListFragment {

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
                AsteroidTabFragments.contentManager.List_NASA_News = AsteroidTabFragments.GitService.getLatestNews();
                AsteroidTabFragments.contentManager.loadAdapters_NEO_News(AsteroidTabFragments.cText);
                ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() {
                       public void run() {
                           LoadingDialogHelper.dialog.setMessage("Loading NASA News Feed...");
                           setListAdapter(AsteroidTabFragments.contentManager.adapter_NEWS);
                           Log.i("closeDialog", "closeDialog Try to close NEWS");
                           LoadingDialogHelper.closeDialog();
                       }
                   });
            }};
            NewsUpdate.start();
    }
}

