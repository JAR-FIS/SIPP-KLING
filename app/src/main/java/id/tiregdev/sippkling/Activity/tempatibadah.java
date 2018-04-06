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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.tiregdev.sippkling.Adapter.adapter_ttu_1;
import id.tiregdev.sippkling.Model.object_dataObyek;
import id.tiregdev.sippkling.R;
import id.tiregdev.sippkling.utils.AppConfig;
import id.tiregdev.sippkling.utils.AppController;
import id.tiregdev.sippkling.utils.SQLiteHandler;

public class tempatibadah extends AppCompatActivity {

    RecyclerView rView;
    LinearLayoutManager lLayout;
    object_dataObyek dataObjek;
    adapter_ttu_1 rcAdapter;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempatibadah);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        findViewById(R.id.mainLayout).requestFocus();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupAdapter();
        FloatingActionButton tambah = findViewById(R.id.tambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LayoutInflater factory = LayoutInflater.from(tempatibadah.this);
                final View exitDialogView = factory.inflate(R.layout.popup_tambahdata, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(tempatibadah.this).create();
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
                        Intent i = new Intent(tempatibadah.this, form_tempatibadah.class);
                        startActivity(i);
                    }
                });
                exitDialog.show();
            }
        });
        displayData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                tempatibadah.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        tempatibadah.this.finish();
    }

    public void setupAdapter() {
        lLayout = new LinearLayoutManager(getBaseContext());

        rView = findViewById(R.id.rview);
        rView.setLayoutManager(lLayout);
        rView.setNestedScrollingEnabled(false);
    }

    private void displayData(){
        SQLiteHandler db;
        db = new SQLiteHandler(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.DISPLAY_DATA + "?kategori=tempatibadah&id=" + db.getUserDetails().get("id_petugas"), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<object_dataObyek> allItems = new ArrayList<>();
                for (int i = 0; i < response.length(); i++){
                    dataObjek = new object_dataObyek();
                    jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        dataObjek.setNama(jsonObject.getString("nama"));
                        allItems.add(new object_dataObyek(
                                jsonObject.getString("nama_tempat"),
                                jsonObject.getString("nama_pemilik"),
                                jsonObject.getString("alamat"),
                                jsonObject.getString("waktu"),
                                jsonObject.getString("status")));


                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                rcAdapter = new adapter_ttu_1(getApplicationContext(),allItems);
                rView.setAdapter(rcAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }
}
