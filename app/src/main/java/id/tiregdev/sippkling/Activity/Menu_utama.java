package id.tiregdev.sippkling.Activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import id.tiregdev.sippkling.Fragment.history;
import id.tiregdev.sippkling.Fragment.home;
import id.tiregdev.sippkling.Fragment.jadwal;
import id.tiregdev.sippkling.Fragment.notif;
import id.tiregdev.sippkling.Fragment.pkl;
import id.tiregdev.sippkling.Fragment.reward;
import id.tiregdev.sippkling.Fragment.rumah_sehat;
import id.tiregdev.sippkling.Fragment.tpm;
import id.tiregdev.sippkling.Fragment.ttu;
import id.tiregdev.sippkling.R;

public class Menu_utama extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String LOG_TAG = Menu_utama.class.getName();
    public static Context mainContext;
    private boolean backPressedToExitOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mainContext = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //add this line to display menu1 when the activity is loaded
        displaySelectedScreen(R.id.nav_menu1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (backPressedToExitOnce) {
                finishAffinity();
            } else {
                this.backPressedToExitOnce = true;
                Toast.makeText(mainContext, "Tekan 2 kali untuk keluar aplikasi", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backPressedToExitOnce = false;
                    }
                }, 2000);
            }
        }
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;
        TextView title = findViewById(R.id.toolbarTitle);

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_menu1:
                fragment = new home();
                title.setText("Home");
                break;
            case R.id.nav_menu2:
                fragment = new notif();
                title.setText("Pemberitahuan");
                break;
            case R.id.nav_menu3:
                fragment = new history();
                title.setText("Riwayat Pengisian");
                break;
            case R.id.nav_menu4:
                fragment = new jadwal();
                title.setText("Jadwal");
                break;
            case R.id.nav_menu5:
                fragment = new rumah_sehat();
                title.setText("Rumah Sehat");
                break;
            case R.id.nav_menu6:
                fragment = new tpm();
                title.setText("Tempat Pengelolaan Makanan");
                break;
            case R.id.nav_menu7:
                fragment = new ttu();
                title.setText("Tempat-tempat Umum");
                break;
            case R.id.nav_menu8:
                fragment = new pkl();
                title.setText("Pelayanan Kesehatan Lingkungan");
                break;
            case R.id.nav_menu9:
                fragment = new reward();
                title.setText("Penghargaan");
                break;
            case R.id.nav_menu10:
                Toast.makeText(mainContext, "Logout Sukses", Toast.LENGTH_SHORT).show();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }
}
