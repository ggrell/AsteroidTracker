package activities;

import java.util.ArrayList;
import java.util.regex.Matcher;
import service.NeoAstroidFeed;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import domains.Impact;
import fragments.ImpactFragment;
import activities.fragment.AsteroidTabFragments;
import adapters.ImpactRiskDetailAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.util.Linkify.TransformFilter;
import android.util.Log;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ImpactRiskDetailView extends SherlockListActivity implements OnClickListener {

    public ShareActionProvider shareActionProvider;
    Bundle extras;
    public Intent asteroidDetails;
    TextView name;
    TextView year_range;
    TextView potential_impacts;
    TextView impact_prob;
    TextView vinfinity;
    TextView absolute_magnitude;
    TextView estimated_diameter;
    TextView torino_scale;
    TextView palermo_scale;
    ListView ls1;
    static ImpactRiskDetailAdapter adapter_IMPACTRisk_DetailView;
    Impact asteroid;
    ActionBar actionBar;
    ArrayList<Impact> NASA_IMPACT_DetailPage = new ArrayList<Impact>();
    String DetailPageURL;
    String OrbitPageURL;
    String shareMessage;

    TransformFilter filter = new TransformFilter() {
        public final String transformUrl(final Matcher match, String url) {
            return match.group();
        }
    };
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.Impact_Detail_ViewTitle));
        actionBar=getSupportActionBar();
        extras = getIntent().getExtras();
        int asteroidList = extras.getInt("position");
        asteroid = (Impact) ImpactFragment.dataList.get(asteroidList);
        NASA_IMPACT_DetailPage.add(asteroid);
        DetailPageURL = NeoAstroidFeed.URL_NEOImpact_base+asteroid.getName().toLowerCase().replace(" ", "")+".html";
        OrbitPageURL = NeoAstroidFeed.URL_NEOImpact_OrbitalBase.replace("{NEONAME}", asteroid.getName().replace(" ", "+"));

        //TODO Intl string to prop files
        TextView tv = (TextView) findViewById(R.id.shareHolder);
        String StringBy = " #AsteroidTracker <http://bit.ly/S7t9Wv>";
        shareMessage = "Asteroid(" + asteroid.getName() + ") risk level: " +getHazardLevel(Integer.parseInt(asteroid.getTorinoScale()))+ ", See Details "+DetailPageURL +" "+StringBy;

        //TODO Fix how this adap is loaded.  SAD PANDA
        final ProgressDialog ArtcleDialog = ProgressDialog.show(this, "","Loading Asteroid Data...", true);
        final Handler Artclehandler = new Handler() {
            public void handleMessage(Message msg) {
                ArtcleDialog.dismiss();
            }
        };
        Thread checkUpdate = new Thread() {
            public void run() {
                Artclehandler.sendEmptyMessage(0);
                ImpactRiskDetailView.this.runOnUiThread(new Runnable() {
                       public void run() {
                           adapter_IMPACTRisk_DetailView = new ImpactRiskDetailAdapter(ImpactRiskDetailView.this, R.layout.impactrisk_detail_view, NASA_IMPACT_DetailPage);
                           setListAdapter(ImpactRiskDetailView.this.adapter_IMPACTRisk_DetailView);
                       }
                   });
            }
        };
        checkUpdate.start();
  }
    
    public void asteroidDetailsPage(View view) {
        String url = DetailPageURL;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        }
    
    public void asteroidOrbitPage(View view) {
        String URL = OrbitPageURL;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(URL));
        startActivity(i);
        }

    public void openAbout() {
        Intent i = new Intent(ImpactRiskDetailView.this, About.class);
        startActivity(i);    
       }

    public void onClick(View v) {}

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        Log.d("impactFrag", "setup onCreateOptionsMenu");

        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.impactsummary, menu);
        MenuItem menuItem = menu.findItem(R.id.share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        shareActionProvider.setShareHistoryFileName(null);
        shareActionProvider.setShareIntent(AsteroidTabFragments.shareSvc.createShareIntent("AsteroidTracker", shareMessage));
        return super.onCreateOptionsMenu(menu);
    }

    public String getHazardLevel(int torinoScale) {
        if(torinoScale == 0) {
            return ImpactRiskDetailView.this.getBaseContext().getString(R.string.Impact_Level_NoHazard);
        }else if(torinoScale == 1){
            return ImpactRiskDetailView.this.getBaseContext().getString(R.string.Impact_Level_Normal);
        }else if(torinoScale >= 2 || torinoScale <= 4){
            ImpactRiskDetailView.this.getBaseContext().getString(R.string.Impact_Level_MeritsAttention);
        }else if(torinoScale >= 5 || torinoScale <= 7){
            ImpactRiskDetailView.this.getBaseContext().getString(R.string.Impact_Level_Threatening);
        }else if(torinoScale >= 8|| torinoScale <= 10){
            ImpactRiskDetailView.this.getBaseContext().getString(R.string.Impact_Level_CertainCollisions);
        }
        return "";
    }
}
