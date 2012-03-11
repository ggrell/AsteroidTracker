package com.vitruviussoftware.bunifish.asteroidtracker;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import com.vitruviussoftware.bunifish.asteroidtracker.R;

public class menu extends Activity implements OnClickListener{

	// Inflater for the Options Menu
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}
	// Actions for Options Menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.quit:
			finish();
			return true;
		case R.id.about:
			openAbount();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public void onClick(View arg0) {}
	
	public void openAbount() {}
}
