package id.tiregdev.sippkling.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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

import id.tiregdev.sippkling.Adapter.ListDataAdapter;
import id.tiregdev.sippkling.Model.DataModel;
import id.tiregdev.sippkling.R;
import id.tiregdev.sippkling.utils.AppConfig;
import id.tiregdev.sippkling.utils.AppController;
import id.tiregdev.sippkling.utils.ProgressDialogUtil;
import id.tiregdev.sippkling.utils.SQLiteHandler;

public class ListDataActivity extends AppCompatActivity {

    private View v;
    private RecyclerView rView;
    private LinearLayoutManager lLayout;
    private DataModel dataObjek;
    private ListDataAdapter rcAdapter;
    private JSONObject jsonObject;
    private String titleBar;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleBar = getIntent().getExtras().getString("TITLE");
        getSupportActionBar().setTitle(titleBar.toUpperCase());
        setupAdapter();
        FloatingActionButton tambah = findViewById(R.id.tambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LayoutInflater factory = LayoutInflater.from(ListDataActivity.this);
                final View exitDialogView = factory.inflate(R.layout.popup_tambahdata, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(ListDataActivity.this).create();
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
                        if(titleBar.equals("jasaboga")){
                            Intent i = new Intent(ListDataActivity.this, FormJasabogaActivity.class);
                            startActivity(i);
                        }else if(titleBar.equals("restoran")){
                            Intent i = new Intent(ListDataActivity.this, FormRestoranActivity.class);
                            startActivity(i);
                        }else if(titleBar.equals("dam")){
                            Intent i = new Intent(ListDataActivity.this, FormDamActivity.class);
                            startActivity(i);
                        }else if(titleBar.equals("tempatibadah")){
                            Intent i = new Intent(ListDataActivity.this, FormTempatIbadahActivity.class);
                            startActivity(i);
                        }else if(titleBar.equals("pasar")){
                            Intent i = new Intent(ListDataActivity.this, FormPasarActivity.class);
                            startActivity(i);
                        }else if(titleBar.equals("sekolah")){
                            Intent i = new Intent(ListDataActivity.this, FormSekolahActivity.class);
                            startActivity(i);
                        }else if(titleBar.equals("kolam")){
                            Intent i = new Intent(ListDataActivity.this, FormKolamActivity.class);
                            startActivity(i);
                        }else if(titleBar.equals("pesantren")){
                            Intent i = new Intent(ListDataActivity.this, FormPesantrenActivity.class);
                            startActivity(i);
                        }else if(titleBar.equals("puskesmas")){
                            Intent i = new Intent(ListDataActivity.this, FormPuskesmasActivity.class);
                            startActivity(i);
                        }else if(titleBar.equals("rumahsakit")){
                            Intent i = new Intent(ListDataActivity.this, FormRumahsakitActivity.class);
                            startActivity(i);
                        }else if(titleBar.equals("klinik")){
//                            Intent i = new Intent(ListDataActivity.this, FormKlinikActivity.class);
//                            startActivity(i);
                            Toast.makeText(getApplicationContext(), "Belum Tersedia!", Toast.LENGTH_SHORT).show();
                        }else if(titleBar.equals("hotel")){
                            Intent i = new Intent(ListDataActivity.this, FormHotelActivity.class);
                            startActivity(i);
                        }else if(titleBar.equals("hotelmelati")){
                            Intent i = new Intent(ListDataActivity.this, FormHotelMelatiActivity.class);
                            startActivity(i);
                        }
                    }
                });
                exitDialog.show();
            }
        });
        displayData();

        searchView = findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    rcAdapter.filter(query);
                } catch (NullPointerException e){
                    Toast.makeText(getApplicationContext(), "Belum Ada Data!", Toast.LENGTH_SHORT).show();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                ListDataActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        ListDataActivity.this.finish();
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
        final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(this, ProgressDialog.STYLE_SPINNER, false);
        progressDialogUtil.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.DISPLAY_DATA + "?kategori=" + titleBar +"&id=" + db.getUserDetails().get("id_petugas") , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialogUtil.dismiss();
                List<DataModel> allItems = new ArrayList<>();
                for (int i = 0; i < response.length(); i++){
                    dataObjek = new DataModel();
                    jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        if(titleBar.equals("jasaboga")){
                            allItems.add(new DataModel(
                                    jsonObject.getString("nama_tempat"),
                                    jsonObject.getString("nama_pengusaha"),
                                    jsonObject.getString("alamat"),
                                    jsonObject.getString("waktu"),
                                    jsonObject.getString("status")));
                        }else if(titleBar.equals("restoran")){
                            allItems.add(new DataModel(
                                    jsonObject.getString("nama_tempat"),
                                    jsonObject.getString("nama_pemilik"),
                                    jsonObject.getString("alamat"),
                                    jsonObject.getString("waktu"),
                                    jsonObject.getString("status")));

                        }else if(titleBar.equals("dam")){
                            allItems.add(new DataModel(
                                    jsonObject.getString("nama_depot"),
                                    jsonObject.getString("nama_pemilik"),
                                    jsonObject.getString("alamat"),
                                    jsonObject.getString("waktu"),
                                    jsonObject.getString("status")));
                        }else if(titleBar.equals("tempatibadah")){
                            allItems.add(new DataModel(
                                    jsonObject.getString("nama_tempat"),
                                    jsonObject.getString("nama_pengurus"),
                                    jsonObject.getString("alamat"),
                                    jsonObject.getString("waktu"),
                                    jsonObject.getString("status")));
                        }else if(titleBar.equals("pasar")){
                            allItems.add(new DataModel(
                                    jsonObject.getString("nama_tempat"),
                                    jsonObject.getString("nama_pengelola"),
                                    jsonObject.getString("alamat"),
                                    jsonObject.getString("waktu"),
                                    jsonObject.getString("status")));
                        }else if(titleBar.equals("sekolah")){
                            allItems.add(new DataModel(
                                    jsonObject.getString("nama_tempat"),
                                    jsonObject.getString("nama_kepala_sekolah"),
                                    jsonObject.getString("alamat"),
                                    jsonObject.getString("waktu"),
                                    jsonObject.getString("status")));

                        }else if(titleBar.equals("kolam")){
                            allItems.add(new DataModel(
                                    jsonObject.getString("nama_tempat"),
                                    jsonObject.getString("nama_pengelola"),
                                    jsonObject.getString("alamat"),
                                    jsonObject.getString("waktu"),
                                    jsonObject.getString("status")));
                        }else if(titleBar.equals("pesantren")){
                            allItems.add(new DataModel(
                                    jsonObject.getString("nama_tempat"),
                                    jsonObject.getString("nama_pengelola"),
                                    jsonObject.getString("alamat"),
                                    jsonObject.getString("waktu"),
                                    jsonObject.getString("status")));
                        }else if(titleBar.equals("puskesmas")){
                            allItems.add(new DataModel(
                                    jsonObject.getString("nama_tempat"),
                                    jsonObject.getString("nama_pimpinan"),
                                    jsonObject.getString("alamat"),
                                    jsonObject.getString("waktu"),
                                    jsonObject.getString("status")));
                        }else if(titleBar.equals("rumahsakit")){
                            allItems.add(new DataModel(
                                    jsonObject.getString("nama_tempat"),
                                    jsonObject.getString("nama_tempat"),
                                    jsonObject.getString("alamat"),
                                    jsonObject.getString("waktu"),
                                    jsonObject.getString("status")));
                        }else if(titleBar.equals("klinik")){
                            allItems.add(new DataModel(
                                    jsonObject.getString("nama_tempat"),
                                    jsonObject.getString("nama_pemilik"),
                                    jsonObject.getString("alamat"),
                                    jsonObject.getString("waktu"),
                                    jsonObject.getString("status")));
                        }else if(titleBar.equals("hotel")){
                            allItems.add(new DataModel(
                                    jsonObject.getString("nama_tempat"),
                                    jsonObject.getString("nama_pemilik"),
                                    jsonObject.getString("alamat"),
                                    jsonObject.getString("waktu"),
                                    jsonObject.getString("status")));
                        }else if(titleBar.equals("hotelmelati")){
                            allItems.add(new DataModel(
                                    jsonObject.getString("nama_tempat"),
                                    jsonObject.getString("nama_pimpinan"),
                                    jsonObject.getString("alamat"),
                                    jsonObject.getString("waktu"),
                                    jsonObject.getString("status")));
                        }


                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                rcAdapter = new ListDataAdapter(getApplicationContext(),allItems);
                rView.setAdapter(rcAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialogUtil.dismiss();
                Toast.makeText(getApplicationContext(), "Belum Ada Data!", Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }
}
