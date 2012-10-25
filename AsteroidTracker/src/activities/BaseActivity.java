package activities;

import utils.LoadingDialogHelper;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.vitruviussoftware.bunifish.asteroidtracker.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class BaseActivity  extends SherlockFragmentActivity {

    ActionBar actionBar;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar=getSupportActionBar();
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
        case R.id.quit:
            finish();
            return true;
        case R.id.about:
            openAbout(this);
            return true;
        case R.id.refresh:
//            refresh = false;
//            LoadingDialogHelper.closeDialog = 0;
//            processFeeds();
            return true;
        case R.id.reload:
          LoadingDialogHelper.closeDialog = 0;
//          processFeeds();
          return true;
        default:
            return super.onOptionsItemSelected(item);
        }
}
    
    public void openAbout(Context thisclass) {
        Intent i = new Intent(thisclass, About.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);    
       }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }


}
