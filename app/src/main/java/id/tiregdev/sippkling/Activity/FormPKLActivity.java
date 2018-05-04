package id.tiregdev.sippkling.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import id.tiregdev.sippkling.R;
import id.tiregdev.sippkling.utils.AppConfig;
import id.tiregdev.sippkling.utils.AppController;
import id.tiregdev.sippkling.utils.ProgressDialogUtil;
import id.tiregdev.sippkling.utils.SQLiteHandler;

public class FormPKLActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    EditText nama, umur, alamat, kasus, namaTempat, noKIS;
    RadioGroup jenisKelamin, jenis;
    Button btnSend;
    private double lat;
    private double lng;
    SimpleDateFormat sdf;
    SQLiteHandler db;
    private ProgressDialog pDialog;

    private static final String TAG = FormJasabogaActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pkl);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setInit();
    }

    private void setInit(){
        nama = (EditText)findViewById(R.id.nama);
        umur = (EditText)findViewById(R.id.umur);
        alamat = (EditText)findViewById(R.id.alamat);
        kasus = (EditText)findViewById(R.id.kasus);
        namaTempat = (EditText)findViewById(R.id.tempat);
        jenisKelamin = (RadioGroup)findViewById(R.id.jk);
        jenis = (RadioGroup)findViewById(R.id.jenis);
        db = new SQLiteHandler(this);
        sdf = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a");
        pDialog = new ProgressDialog(this);
        btnSend = (Button)findViewById(R.id.send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LayoutInflater factory = LayoutInflater.from(FormPKLActivity.this);
                final View exitDialogView = factory.inflate(R.layout.popup_yakin, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(FormPKLActivity.this).create();
                exitDialog.setView(exitDialogView);
                exitDialogView.findViewById(R.id.ya).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                        onSubmit();

                    }
                });
                exitDialogView.findViewById(R.id.tidak).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                    }
                });
                exitDialog.show();
            }
        });
        alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAutocompleteActivity();
            }
        });
    }

    private void openAutocompleteActivity() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Log.e("ERROR", message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i("TAG", "Place Selected: " + place.getName());
                String addressPlace = String.valueOf(place.getAddress());
                alamat.setText(addressPlace.trim());
                LatLng latLng = place.getLatLng();
                lat = latLng.latitude;
                lng = latLng.longitude;
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e("TAG", "Error: Status = " + status.toString());
            }
        }
    }
    private void onSubmit(){

        if(jenisKelamin.getCheckedRadioButtonId() != -1 && jenis.getCheckedRadioButtonId() != -1
                && !nama.getText().toString().trim().equals("") && !umur.getText().toString().trim().equals("")
                && !alamat.getText().toString().trim().equals("") && !kasus.getText().toString().trim().equals("")
                && !namaTempat.getText().toString().trim().equals("")){

            final String txtNama = nama.getText().toString().trim();
            final String txtUmur = umur.getText().toString().trim();
            final String txtAlamat = alamat.getText().toString().trim();
            final String txtKasus = kasus.getText().toString().trim();
            final String txtNamaTempat = namaTempat.getText().toString().trim();
            final String txtJenisKelamin = ((RadioButton)findViewById(jenisKelamin.getCheckedRadioButtonId())).getText().toString();
            final String txtJenis = ((RadioButton)findViewById(jenis.getCheckedRadioButtonId())).getText().toString();
            final String idPetugas = db.getUserDetails().get("id_petugas");
            final String waktu = sdf.format(Calendar.getInstance().getTime().getTime());
            final String koordinat = String.valueOf(lat) + ", " + String.valueOf(lng);

            String tag_string_req = "req_pkl";

            final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(this, ProgressDialog.STYLE_SPINNER, false);
            progressDialogUtil.show();

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.SEND_DATA_PKL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Register Response: " + response.toString());
                 progressDialogUtil.dismiss();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");
                        if (!error) {

                            Toast.makeText(getApplicationContext(), "Data berhasil terkirim!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {

                            // Error occurred in registration. Get the error
                            // message
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Failed with error msg:\t" + error.getMessage());
                    Log.d(TAG, "Error StackTrace: \t" + error.getStackTrace());
                    // edited here
                    try {
                        byte[] htmlBodyBytes = error.networkResponse.data;
                        Log.e(TAG, new String(htmlBodyBytes), error);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    progressDialogUtil.dismiss();

                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting params to register url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("alamat", txtAlamat);
                    params.put("id_petugas", idPetugas);
                    params.put("jenis", txtJenis);
                    params.put("jenis_kelamin", txtJenisKelamin);
                    params.put("kasus", txtKasus);
                    params.put("koordinat", koordinat);
                    params.put("nama_pkl", txtNama);
                    params.put("nama_tempat", txtNamaTempat);
                    params.put("umur", txtUmur);
                    params.put("waktu", waktu);

                    return params;
                }

            };

            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        }else{
            Toast.makeText(this, "Error harap check semua opsi!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                FormPKLActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
