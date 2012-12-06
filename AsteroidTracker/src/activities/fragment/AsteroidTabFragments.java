package activities.fragment;

import java.util.List;
import java.util.Vector;

import service.AsteroidTrackerService;
import service.ContentManager;
import service.DownloadManager;
import service.SharingService;
import utils.LoadingDialogHelper;
import utils.NetworkUtil;
import activities.BaseActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.actionbarsherlock.widget.ShareActionProvider.OnShareTargetSelectedListener;
import com.viewpagerindicator.PageIndicator;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import com.vitruviussoftware.bunifish.asteroidtracker.R.menu;

import fragments.AsteroidFragmentBase;
import fragments.ImpactFragment;
import fragments.NewsFragment;
import fragments.RecentFragment;
import fragments.UpcomingFragment;

public class AsteroidTabFragments extends BaseActivity // implements ViewPager.OnPageChangeListener 
{
//    private ViewPager mPager;
    PageIndicator mIndicator;
    private TabHost mTabHost;
    public static FragPageAdapter mPagerAdapter;
    public static ViewPager mViewPager;
    public static ContentManager contentManager = new ContentManager();
    public DownloadManager dManager = new DownloadManager(); 
    public static Context cText;
    public static Drawable drawable;
    ActionBar actionBar;
    String[] tabNames = {"RECENT", "UPCOMING", "IMPACT RISK", "NEWS"};
    public static SharingService shareSvc = new SharingService();
    static com.actionbarsherlock.view.MenuItem reloadItem;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_viewpager_layout);
        actionBar=getSupportActionBar();
        cText = this;
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();

//        reloadItem = (MenuItem) findViewById(R.id.reload);
//        reloadItem.setVisible(false);
        
//        menu.findItem(R.id.refresh);
        initFragmentAndPading();
//        LoadingDialogHelper.dialog = new ProgressDialog(this);
//        LoadingDialogHelper.progressDialog(this, "", "Checking Asteroid Service");
        drawable = getResources().getDrawable(R.drawable.asteroid);

//        dManager.setFragPageAdapter(this.mPagerAdapter);
//        dManager.startDownloads();
    }

    public FragPageAdapter getAdap(){
        return this.mPagerAdapter;
    }

    public void initFragmentAndPading()
    {
        mViewPager = (ViewPager)super.findViewById(R.id.viewpager);
        mPagerAdapter = new FragPageAdapter(this, mTabHost, mViewPager);
        mPagerAdapter.addTab(mTabHost.newTabSpec("RECENT").setIndicator("Recent"),RecentFragment.class, null);
        mPagerAdapter.addTab(mTabHost.newTabSpec("UPCOMING").setIndicator("Upcoming"),UpcomingFragment.class, null);
        mPagerAdapter.addTab(mTabHost.newTabSpec("IMPACTRISK").setIndicator("Impact Risk"),ImpactFragment.class, null);
        mPagerAdapter.addTab(mTabHost.newTabSpec("News").setIndicator("News"),NewsFragment.class, null);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
    }

    public static void setRefreshIcon( boolean IsEnabled ) {
        if(IsEnabled) {
            reloadItem.setEnabled(false);
            reloadItem.setActionView(R.layout.inderterminate_progress);
        }else {
            reloadItem.setEnabled(true);
            reloadItem.setActionView(null);
        }
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
        case R.id.reload:
            int position = mViewPager.getCurrentItem();
            if( position == 0 ) {
//              RecentFragment frag = (RecentFragment) getSupportFragmentManager().findFragmentByTag("RECENT");
//              frag.refreshFragment();
            }
            Toast.makeText(AsteroidTabFragments.cText, "Found recen fragment "+mViewPager.getCurrentItem() , Toast.LENGTH_LONG).show();
            //Log.e("frag", "Found recen fragment");
            //Toast.makeText(AsteroidTabFragments.cText, "Found recen fragment" , Toast.LENGTH_LONG).show();
//            setRefreshIcon(true);
//          LoadingDialogHelper.closeDialog = 0;
//          LoadingDialogHelper.progressDialog(this, "", "Checking Asteroid Service");
//          dManager.startDownloads();
          return true;
        default:
            return super.onOptionsItemSelected(item);
        }
}

}