package activities.fragment;

import java.util.List;
import java.util.Vector;
import service.AsteroidTrackerService;
import service.ContentManager;
import service.DownloadManager;
import utils.LoadingDialogHelper;
import utils.NetworkUtil;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.viewpagerindicator.PageIndicator;
import com.vitruviussoftware.bunifish.asteroidtracker.R;

import activities.About;
import activities.AsteroidTrackerActivity;
import activities.BaseActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import fragments.ImpactFragment;
import fragments.NewsFragment;
import fragments.RecentFragment;
import fragments.UpcomingFragment;


public class AsteroidTabFragments extends BaseActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener{
    ViewPager mPager;
    PageIndicator mIndicator;
    private TabHost mTabHost;
    private FragPageAdapter mPagerAdapter;
    private ViewPager mViewPager;
    
    ListView ls1_ListView_Recent;
    public TabSpec TabSpec1_Recent;
    public TabSpec TabSpec2_Upcoming;
    public TabSpec TabSpec3_Impact;
    public TabSpec TabSpec4_News;
    public static ContentManager contentManager = new ContentManager();
    public static AsteroidTrackerService GitService = new AsteroidTrackerService();
    public static boolean UseGitService;
    DownloadManager dManager = new DownloadManager(); 
    NetworkUtil nUtil = new NetworkUtil();
    public static Context cText;
    public static Drawable drawable;
    ActionBar actionBar;
    LayoutInflater inflater;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_viewpager_layout);
        actionBar=getSupportActionBar();
        cText = this;

        LoadingDialogHelper.progressDialog(this, "", "Checking Asteroid Service");
        boolean networkAvailable = nUtil.IsNetworkAvailable(this);
        if(networkAvailable){
            UseGitService = GitService.isGitServiceAvailable();
        }

        initTabHost(savedInstanceState);
        initFragmentAndPading();
        drawable = getResources().getDrawable(R.drawable.asteroid);
    }

    private void initTabHost(Bundle args) {

        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();

        ls1_ListView_Recent = new ListView(AsteroidTabFragments.this); 
//        TabSpec1_Recent=mTabHost.newTabSpec("Tab1");
//        TabSpec1_Recent.setIndicator("RECENT");
//        TabSpec1_Recent.setContent(R.id.tabthis);

        TabSpec1_Recent = setupTabSpec(new TextView(this), "RECENT");
        TabSpec2_Upcoming = setupTabSpec(new TextView(this), "UPCOMING");
        TabSpec3_Impact = setupTabSpec(new TextView(this), "IMPACT RISK");
        TabSpec4_News = setupTabSpec(new TextView(this), "NEWS");
        
        AsteroidTabFragments.AddTab(this, this.mTabHost, TabSpec1_Recent);
        AsteroidTabFragments.AddTab(this, this.mTabHost, TabSpec2_Upcoming);
        AsteroidTabFragments.AddTab(this, this.mTabHost, TabSpec3_Impact);
        AsteroidTabFragments.AddTab(this, this.mTabHost, TabSpec4_News);
        mTabHost.setOnTabChangedListener(this);
    }

    private TabSpec setupTabSpec(final View view, final String tag) {
            View tabview = createTabView(mTabHost.getContext(), tag);
            TabSpec TabSpec;
            TabSpec=mTabHost.newTabSpec("tag");
            TabSpec.setIndicator(tabview);
            TabSpec.setContent(
                    new TabContentFactory() {public View createTabContent(String tag) {return view;}}
            );
//            TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(
//                    new TabContentFactory() {public View createTabContent(String tag) {return view;}}
//                    );
//            mTabHost.addTab(setContent);
            return TabSpec;
        }

    private static View createTabView(final Context context, final String text) {
            View view = LayoutInflater.from(context).inflate(R.layout.tabs_custom, null);
            TextView tv = (TextView) view.findViewById(R.id.tabsText);
            tv.setText(text);
            return view;
        }

    
    public void initFragmentAndPading(){
        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, RecentFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, UpcomingFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, ImpactFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, NewsFragment.class.getName()));
        this.mPagerAdapter  = new FragPageAdapter(super.getSupportFragmentManager(), fragments);
        this.mViewPager = (ViewPager)super.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }

    class TabFactory implements TabContentFactory {
                private final Context mContext;

                public TabFactory(Context context) {
                    mContext = context;
                }
                /** (non-Javadoc)
                 * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
                 */
                public View createTabContent(String tag) {
                    View v = new View(mContext);
                    v.setMinimumWidth(0);
                    v.setMinimumHeight(0);
                    return v;
                }
    }

    private static void AddTab(AsteroidTabFragments activity, TabHost tabHost, TabHost.TabSpec tabSpec) {
        tabSpec.setContent(activity.new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    @Override
    public void onTabChanged(String tabId) {
                int pos = this.mTabHost.getCurrentTab();
                Log.i("tabz", "tabz: "+pos);
                this.mViewPager.setCurrentItem(pos);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub
        
    }

    public void onPageSelected(int position) {
            this.mTabHost.setCurrentTab(position);
        }

    public void setRecentAdapter(){
        TabSpec1_Recent.setContent(new TabHost.TabContentFactory(){
        public View createTabContent(String tag)
        {
            ls1_ListView_Recent.setAdapter(contentManager.adapter_RECENT);
            return ls1_ListView_Recent;
        }       
    });
      }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }
    
//    @Override
//    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
//        switch (item.getItemId()) {
//        case R.id.quit:
//            finish();
//            return true;
//        case R.id.about:
//            openAbout();
//            return true;
//        case R.id.refresh:
////            refresh = false;
////            LoadingDialogHelper.closeDialog = 0;
////            processFeeds();
//            return true;
//        default:
//            return super.onOptionsItemSelected(item);
//        }
//}
    
//    public void openAbout() {
//        Intent i = new Intent(AsteroidTabFragments.this, About.class);
//        startActivity(i);    
//       }
}