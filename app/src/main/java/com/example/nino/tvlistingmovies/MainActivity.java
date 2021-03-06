package com.example.nino.tvlistingmovies;

import android.app.SearchManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.nino.tvlistingmovies.utils.MovieContent;

/**
 * Main Activity will hold both Fragments, MoviesListingFragment, MovieDetailsFragment
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MoviesListingFragment.OnListFragmentInteractionListener, MovieDetailsFragment.OnFragmentInteractionListener {


    static Toast lastToast = null;  //Used for reference for the Toast Pop-up Message.

    ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the View Activity
        setContentView(R.layout.activity_main);

        //Enable ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Load Fragment for Listing Movies
        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = MoviesListingFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                //TODO Sliding Content
            }
        };

        drawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        Toggle back Button
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        mDrawerToggle.syncState();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Tool Bar Action search button click
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }


    /**
     * Checks with button from the navigation drawer was pressed.
     *
     * @param item Reference to the menu item pressed
     * @return boolean
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_camera:
            case R.id.nav_gallery:
            case R.id.nav_slideshow:
            case R.id.nav_manage:
                comingSoon();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Used to detect when the back button has been pressed.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Clear Toast before exiting
        if (!isToastNotRunning()){
            lastToast.cancel();
        }
        //Close Drawer
        else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        //Default Behaviour
        else  {
            super.onBackPressed();
        }
    }

    /**
     * Check if the Toast is not running
     *
     * @return boolean
     */
    boolean isToastNotRunning() {
        return (lastToast == null || lastToast.getView().getWindowVisibility() != View.VISIBLE);
    }

    /**
     * Displays Toast POP-up message, in case there is no active Toast.
     *
     * @param message String message to be attached to the Toast pop-up.
     */
    public void showToastFromBackground(final String message) {
        if(isToastNotRunning()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lastToast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG);
                    lastToast.show();
                }
            });
        }
    }

    /**
     * Display Toast pop-up message.
     */
    private void comingSoon(){
        showToastFromBackground("Awesome feature coming soon");
    }

    /**
     * Replaces fragment onto this activity, used to display movie details.
     * @param details Fragment to be loaded in the activity
     */
    public void loadFragment(Fragment details){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, details)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(MovieContent.MovieItem item) {
    }

}
