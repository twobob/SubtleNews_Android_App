package in.ac.dtu.subtlenews;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    public static final String PREFS_NAME = "MainPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean mainRun = settings.getBoolean("MainRun", false);

        if(!mainRun){
            Bundle bundle = new Bundle();
            new NewsAutoRefresh(this, bundle, 0);

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("MainRun", true);
            editor.commit();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        mTitle = "Subtle News";
        switch (number) {
            case 1:
                mTitle = mTitle + "/India";
                break;
            case 2:
                mTitle = mTitle + "/World";
                break;
            case 3:
                mTitle = mTitle + "/Entertainment";
                break;
            case 4:
                mTitle = mTitle + "/Technology";
                break;
            case 5:
                mTitle = mTitle + "/Business";
                break;
            case 6:
                mTitle = mTitle + "/Science";
                break;
            case 7:
                mTitle = mTitle + "/Sports";
                break;
            case 8:
                mTitle = mTitle + "/Health";
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_refresh:
                if(Utils.isNetworkConnected(MainActivity.this)){
                    new UpdateNews(MainActivity.this).execute();
                    MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                    mainFragment.updateView();
                } else {
                    Toast.makeText(MainActivity.this, "Please turn on your internet connection first.", Toast.LENGTH_SHORT).show();
                }

                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
