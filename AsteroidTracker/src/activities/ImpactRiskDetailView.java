package activities;

import java.util.ArrayList;

import service.ContentManager;
import service.neoAstroidFeed;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import domains.nasa_neoImpactEntity;

import adapters.impactRisk_DetailAdapter;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ImpactRiskDetailView extends ListActivity implements OnClickListener{

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
	static impactRisk_DetailAdapter adapter_IMPACTRisk_DetailView;
	nasa_neoImpactEntity asteroid =  new nasa_neoImpactEntity();
	ArrayList<nasa_neoImpactEntity> NASA_IMPACT_DetailPage = new ArrayList();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Asteroid Tracker: Impact Risk Summary");
		extras = getIntent().getExtras();
		int asteroidList = extras.getInt("position");
		NASA_IMPACT_DetailPage.add(ContentManager.List_NASA_IMPACT.get(asteroidList));
		
		Button button_AsteroidDetailPageButton = (Button) findViewById(R.id.ImpactRisk_Button_AsteroidDetailPage);
	 	Button button_AsteroidOrbitalPageButton = (Button) findViewById(R.id.ImpactRisk_Button_AsteroidOrbitDiagrams);

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
    	               	adapter_IMPACTRisk_DetailView = new impactRisk_DetailAdapter(ImpactRiskDetailView.this, R.layout.impactrisk_detail_view, NASA_IMPACT_DetailPage);
    	               	setListAdapter(ImpactRiskDetailView.this.adapter_IMPACTRisk_DetailView);
    	               }
    	           });
    		}
    	};
    	checkUpdate.start();
  }
	
	public void asteroidDetailsPage(View view) {
//		Log.i("GoToWeb", "Calling");
//		Log.i("GoToWeb", neoAstroidFeed.URL_NEOImpact_base+NASA_IMPACT_DetailPage.get(0).getName().toLowerCase().replace(" ", "")+".html");
		String url = neoAstroidFeed.URL_NEOImpact_base+NASA_IMPACT_DetailPage.get(0).getName().toLowerCase().replace(" ", "")+".html";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
		}
	
	public void asteroidOrbitPage(View view) {
		String name = NASA_IMPACT_DetailPage.get(0).getName().replace(" ", "+");
		String URL = neoAstroidFeed.URL_NEOImpact_OrbitalBase.replace("{NEONAME}", name);
//		Log.i("GoToWeb", "Calling");
//		Log.i("GoToWeb", URL);
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(URL));
		startActivity(i);
		}
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.impactdetails, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about:
			openAbout();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
}
	
	public void openAbout() {
		Intent i = new Intent(ImpactRiskDetailView.this, about.class);
        startActivity(i);	
       }

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
