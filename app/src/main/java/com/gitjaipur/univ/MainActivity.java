package com.gitjaipur.univ;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ForceUpdateChecker.OnUpdateNeededListener  {




    public  int counter1;
    private static final String PREFS_NAME  = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );


        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.setDrawerListener( toggle );
        toggle.syncState();

        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );


        home he = new home();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace( R.id.main, he ).commit();






    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu( menu );



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int item1 = item.getItemId();
        if (item1 == R.id.action_notify) {
            startActivity( new Intent( this, notify.class ) );
        }
        return super.onOptionsItemSelected( item );
    }


    private boolean doubleBackToExitPressedOnce = true;

    @Override
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(" Exit ")
                .setMessage("Do you want to exit ?")
                .setPositiveButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create();
        dialog.show();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace( R.id.main, new home() ).commit();
            Toast.makeText( this, "Home", Toast.LENGTH_SHORT ).show();
        } else if (id == R.id.nav_result) {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace( R.id.main, new result() ).commit();
            Toast.makeText( this, "Result", Toast.LENGTH_SHORT ).show();
        } else if (id == R.id.nav_syllabus) {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace( R.id.main, new syllabus() ).commit();
            Toast.makeText( this, "Syllabus", Toast.LENGTH_SHORT ).show();

        } else if (id == R.id.nav_Questions) {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace( R.id.main, new questions() ).commit();
            Toast.makeText( this, "Questions", Toast.LENGTH_SHORT ).show();

        }
        else if(id == R.id.nav_contacts){
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace( R.id.main, new contacts() ).commit();
            Toast.makeText( this, "Contacts", Toast.LENGTH_SHORT ).show();

        }
        else if (id==R.id.nav_feedback){
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace( R.id.main, new feedback() ).commit();
            Toast.makeText( this, "feedback", Toast.LENGTH_SHORT ).show();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }



    @Override
    public void onUpdateNeeded(final String updateUrl) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New version available")
                .setMessage("Please, update app to new version to continue reposting.")
                .setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                redirectStore("rtuapk.co.nf");
                            }
                        }).setNegativeButton("No, thanks",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,"please update later",Toast.LENGTH_SHORT).show();
                            }
                        }).create();
        dialog.show();
    }

  private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}

