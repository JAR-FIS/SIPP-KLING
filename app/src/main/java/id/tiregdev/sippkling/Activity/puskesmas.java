package id.tiregdev.sippkling.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.tiregdev.sippkling.Adapter.adapter_ttu_2;
import id.tiregdev.sippkling.Model.object_dataObyek;
import id.tiregdev.sippkling.R;

public class puskesmas extends AppCompatActivity {

    RecyclerView rView;
    LinearLayoutManager lLayout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puskesmas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.mainLayout).requestFocus();
        setupAdapter();
        FloatingActionButton tambah = findViewById(R.id.tambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LayoutInflater factory = LayoutInflater.from(puskesmas.this);
                final View exitDialogView = factory.inflate(R.layout.popup_tambahdata, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(puskesmas.this).create();
                exitDialog.setView(exitDialogView);
                exitDialogView.findViewById(R.id.tidak).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                    }
                });
                exitDialogView.findViewById(R.id.ya).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                        Intent i = new Intent(puskesmas.this, form_puskesmas.class);
                        startActivity(i);
                    }
                });
                exitDialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                puskesmas.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        puskesmas.this.finish();
    }

    public void setupAdapter() {
        List<object_dataObyek> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(getBaseContext());

        rView = findViewById(R.id.rview);
        rView.setLayoutManager(lLayout);

        adapter_ttu_2 rcAdapter = new adapter_ttu_2(getBaseContext(), rowListItem);
        rView.setAdapter(rcAdapter);
        rView.setNestedScrollingEnabled(false);
    }

    private List<object_dataObyek> getAllItemList() {
        List<object_dataObyek> allItems = new ArrayList<>();
        allItems.add(new object_dataObyek("Puskesmas An Nur", "Ahmad Abdul", getResources().getString(R.string.almt1), "06/11/2017", "Layak"));
        allItems.add(new object_dataObyek("Puskesmas At Taqwa", "Abu Abdul", getResources().getString(R.string.almt2), "10/10/2017", "Layak"));
        allItems.add(new object_dataObyek("Puskesmas Nurul Huda", "Muhammad Abdul", getResources().getString(R.string.almt3), "12/10/2017", "Tidak Layak"));
        allItems.add(new object_dataObyek("Puskesmas Al Barkah", "Al Abdul", getResources().getString(R.string.almt1), "12/10/2017", "Layak"));
        allItems.add(new object_dataObyek("Puskesmas ST. Maria", "Si Abdul", getResources().getString(R.string.almt2), "12/10/2017", "Tidak Layak"));
        allItems.add(new object_dataObyek("Puskesmas Al Fajr", "Dul Abdul", getResources().getString(R.string.almt3), "13/10/2017", "Layak"));
        allItems.add(new object_dataObyek("Puskesmas Baiturrahman", "Koh Abdul", getResources().getString(R.string.almt1), "15/10/2017", "Tidak Layak"));

        return allItems;
    }
}

