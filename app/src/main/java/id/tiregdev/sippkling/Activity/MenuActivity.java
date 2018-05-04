package id.tiregdev.sippkling.Activity;

import android.content.Context;
import android.content.Intent;
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

import id.tiregdev.sippkling.Fragment.HistoryFragment;
import id.tiregdev.sippkling.Fragment.HomeFragment;
import id.tiregdev.sippkling.Fragment.JadwalFragment;
import id.tiregdev.sippkling.Fragment.NotifFragment;
import id.tiregdev.sippkling.Fragment.RewardFragment;
import id.tiregdev.sippkling.Fragment.ListDataFragment;
import id.tiregdev.sippkling.Fragment.TPMFragment;
import id.tiregdev.sippkling.Fragment.TTUFragment;
import id.tiregdev.sippkling.R;
import id.tiregdev.sippkling.utils.SQLiteHandler;
import id.tiregdev.sippkling.utils.SessionManager;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String LOG_TAG = MenuActivity.class.getName();
    public static Context mainContext;
    private boolean backPressedToExitOnce = false;
    private SQLiteHandler db;
    private SessionManager session;

    private Fragment fHome = new HomeFragment();
    private Fragment fNotif = new NotifFragment();
    private Fragment fHistory = new HistoryFragment();
    private Fragment fJadwal = new JadwalFragment();
    private Fragment fRS = ListDataFragment.newInstance("rumahsehat");
    private Fragment fTPM = new TPMFragment();
    private Fragment fTTU = new TTUFragment();
    private Fragment fPKL = ListDataFragment.newInstance("pkl");
    private Fragment fReward = new RewardFragment();

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

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
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
                fragment = fHome;
                title.setText("Home");
                break;
            case R.id.nav_menu2:
                fragment = fNotif;
                title.setText("Pemberitahuan");
                break;
            case R.id.nav_menu3:
                fragment = fHistory;
                title.setText("Riwayat Pengisian");
                break;
            case R.id.nav_menu4:
                fragment = fJadwal;
                title.setText("Jadwal");
                break;
            case R.id.nav_menu5:
                fragment = fRS;
                title.setText("Rumah Sehat");
                break;
            case R.id.nav_menu6:
                fragment = fTPM;
                title.setText("Tempat Pengelolaan Makanan");
                break;
            case R.id.nav_menu7:
                fragment = fTTU;
                title.setText("Tempat-tempat Umum");
                break;
            case R.id.nav_menu8:
                fragment = fPKL;
                title.setText("Pelayanan Kesehatan Lingkungan");
                break;
            case R.id.nav_menu9:
                fragment = fReward;
                title.setText("Penghargaan");
                break;
            case R.id.nav_menu10:
                Toast.makeText(mainContext, "Logout Sukses", Toast.LENGTH_SHORT).show();
                logoutUser();
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

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the LoginActivity activity
        Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
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
