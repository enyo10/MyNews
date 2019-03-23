package ch.openclassrooms.enyo1.mynews.controller.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.controller.fragments.BusinessFragment;
import ch.openclassrooms.enyo1.mynews.controller.fragments.MostPopFragment;
import ch.openclassrooms.enyo1.mynews.controller.fragments.TopStoriesFragment;
import ch.openclassrooms.enyo1.mynews.view.MyPagerAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.toolbar)Toolbar mToolbar;
    @BindView(R.id.activity_main_drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.activity_main_nav_view) NavigationView mNavigationView;
    @BindView(R.id.activity_main_tabs)TabLayout mTabLayout;
    @BindView(R.id.activity_main_view_pager)ViewPager mViewPager;

    private  FragmentPagerAdapter adapterViewPager;

    // Identify each fragment of the ViewPager with a number
    private static final int FRAGMENT_TOP_STORIES = 0;
    private static final int FRAGMENT_MOST_POPULAR = 1;
    private static final int FRAGMENT_BUSINESS = 2;

    public static String POSITION="POSITION";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        createNotificationChannel();

        // Set up the toolbar.
        configureToolBar ();
        configureViewPagerAndTabs();
        configureDrawerLayout();
        configureNavigationView();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("TAG"," selected position " + mTabLayout.getSelectedTabPosition());
        outState.putInt(POSITION, mTabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("TAG","position restore "+savedInstanceState.getInt(POSITION));
        mViewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();

        switch (id){
            case R.id.menu_activity_main_toolbar_search:
                callSearchActivity();
                return true;
            case R.id.activity_main_menu_toolbar_overflow_notifications:
                callNotificationActivity();
                return true;

            case R.id.activity_main_menu_toolbar_overflow_about:
                callSearchActivity();
                return true;

            case R.id.activity_main_menu_toolbar_overflow_help:
                return true;
            default:return super.onOptionsItemSelected(item);
        }


    }

    /**
     * This method to configure the toolbar.
     */
    public void configureToolBar(){
        setSupportActionBar(mToolbar);

    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
       // mDrawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    /**
     *
     */

    private void configureViewPagerAndTabs(){
       // ViewPager vpPager = findViewById(R.id.activity_main_view_pager);
        adapterViewPager = new MyPagerAdapter (getSupportFragmentManager());
        ((MyPagerAdapter) adapterViewPager).addFragment(new TopStoriesFragment(),"TOP STORIES");
        ((MyPagerAdapter) adapterViewPager).addFragment(new MostPopFragment(),"MOST POPULAR");
        ((MyPagerAdapter) adapterViewPager).addFragment(new BusinessFragment(),"BUSINESS");
        mViewPager.setAdapter(adapterViewPager);

        //Glue TabLayout and ViewPager together
        mTabLayout.setupWithViewPager(mViewPager);
        //Design purpose. Tabs have the same width
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        mNavigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // 4 - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id){
            case R.id.activity_main_drawer_top_stories :
                mViewPager.setCurrentItem(FRAGMENT_TOP_STORIES);
                break;
            case R.id.activity_main_drawer_most_popular:
                mViewPager.setCurrentItem(FRAGMENT_MOST_POPULAR);
                break;
            case R.id.activity_main_drawer_business:
                mViewPager.setCurrentItem(FRAGMENT_BUSINESS);
                break;
            default:
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /**
     * This method to call the search activity.
     */
    public void callSearchActivity(){
        Intent searchActivity = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(searchActivity);
    }

    /**
     * This method to call the notification activity.
     */
    protected void callNotificationActivity(){
        Intent notificationActivity = new Intent(MainActivity.this, NotificationsActivity.class);
        startActivity(notificationActivity);
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //CharSequence name = getString(R.string.channel_name);
            //String description = getString(R.string.channel_description);
            CharSequence name = "Channel";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Chanel_id", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
