package activities;

import java.util.ArrayList;

import com.vitruviussoftware.bunifish.asteroidtracker.R;
import com.vitruviussoftware.bunifish.asteroidtracker.R.layout;

import domains.AboutAsteroidTracker;
import domains.Impact;
import adapters.AboutAdapter;
import adapters.ImpactRiskDetailAdapter;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class About extends ListActivity {

	public static Drawable drawableAbout;
	static AboutAdapter AboutDapter;
	ArrayList<AboutAsteroidTracker> aboutEntityList = new ArrayList();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("AsteroidWatch About");
		AboutAsteroidTracker about = new AboutAsteroidTracker();
		aboutEntityList.add(about);

		final ProgressDialog ArtcleDialog = ProgressDialog.show(this, "","", true);
		final Handler Artclehandler = new Handler() {
			public void handleMessage(Message msg) {
				ArtcleDialog.dismiss();
			}
		};
    	Thread checkUpdate = new Thread() {
    		public void run() {
    			Artclehandler.sendEmptyMessage(0);
    			About.this.runOnUiThread(new Runnable() {
    	               public void run() {
    	           		AboutDapter = new AboutAdapter(About.this, R.layout.about_main, aboutEntityList);
    	               	setListAdapter(About.this.AboutDapter);
    	               }
    	           });
    		}
    	};
    	checkUpdate.start();
	}

	private OnClickListener GoToNASANeoSite = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://neo.jpl.nasa.gov"));
			startActivity(intent);
		}
	};
	
	private OnClickListener GoToBFsite = new OnClickListener() {
		public void onClick(View v) {
			Log.i("GoToWeb", "Calling");
			String url = "";
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			startActivity(i);
		}
	};
}
