package fragments;

import java.util.List;
import domains.NearEarthObject;
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

public class RecentFragment extends ListFragment {

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
            processNEOFeedRecent();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void processNEOFeedRecent(){
        Thread checkUpdate = new Thread() {
        public void run() {
                AsteroidTabFragments.contentManager.List_NASA_RECENT = (List<NearEarthObject>) AsteroidTabFragments.GitService.getNEOList(AsteroidTabFragments.GitService.URI_RECENT);
                AsteroidTabFragments.contentManager.loadAdapters_NEO_Recent(AsteroidTabFragments.cText);
                ((Activity) AsteroidTabFragments.cText).runOnUiThread(new Runnable() {
                   public void run() {
                       LoadingDialogHelper.dialog.setMessage("Loading NASA NEO Recent Feed...");
                       setListAdapter(AsteroidTabFragments.contentManager.adapter_RECENT);
                       Log.i("closeDialog", "closeDialog Try to close Recent");
                       LoadingDialogHelper.closeDialog();
                       }
               });
        }};
        checkUpdate.start();
    }
}
