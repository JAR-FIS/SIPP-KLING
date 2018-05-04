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
import android.widget.CheckBox;
import android.widget.EditText;
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
import id.tiregdev.sippkling.utils.DialogUtil;
import id.tiregdev.sippkling.utils.ProgressDialogUtil;
import id.tiregdev.sippkling.utils.SQLiteHandler;

public class FormHotelActivity extends AppCompatActivity {

    EditText frmNamaTempat, frmNamaPengusaha, frmNoTelp, frmJmlKaryawan, frmAlamat, frmNoIzinUsaha;
    Button btnSend;
    CheckBox[] cb = new CheckBox[134];
    double[] bobot = new double[] {5,5,5,5,5,5,5,5,5,5,
            5,5,5,5,5,5,5,5,5,5,
            5,5,5,5,5,5,5,5,5,5,
            5,5,5,5,5,5,5,5,5,5,
            5,5,5,5,5,5,5,5,5,5,
            5,5,5,5,5,5,5,5,5,5,
            5,5,5,5,5,5,5,5,5,5,
            5,5,5,5,5,5,5,5,5,5,
            5,5,5,5,5,5,5,5,5,5,
            5,5,5,5,5,5,5,5,5,5,
            5,5,5,5,5,5,5,5,5,5,
            5,5,5,5,5,5,5,5,5,5,
            5,5,5,5,5,5,5,5,5,5,
            5,5,5,5};
    double[] nilai = new double[] {10,9,8,7,6,5,4,3,2,1,
            10,9,8,7,6,5,4,3,2,1,
            10,9,8,7,6,5,4,3,2,1,
            10,9,8,7,6,5,4,3,2,1,
            10,9,8,7,6,5,4,3,2,1,
            10,9,8,7,6,5,4,3,2,1,
            10,9,8,7,6,5,4,3,2,1,
            10,9,8,7,6,5,4,3,2,1,
            10,9,8,7,6,5,4,3,2,1,
            10,9,8,7,6,5,4,3,2,1,
            10,9,8,7,6,5,4,3,2,1,
            10,9,8,7,6,5,4,3,2,1,
            10,9,8,7,6,5,4,3,2,1,
            10,9,8,7};
    private double lat;
    private double lng;
    SimpleDateFormat sdf;

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    SQLiteHandler db;
    private ProgressDialog pDialog;

    private static final String TAG = FormJasabogaActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_hotel);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setInit();
    }

    private void setInit() {
        frmNamaTempat = (EditText)findViewById(R.id.namaTempat);
        frmNamaPengusaha = (EditText)findViewById(R.id.namaPimpinan);
        frmJmlKaryawan = (EditText)findViewById(R.id.jumlahKaryawan);
        frmAlamat = (EditText)findViewById(R.id.alamat);
        frmNoIzinUsaha = (EditText)findViewById(R.id.nmrIzin);
        frmNoTelp = (EditText)findViewById(R.id.tlp);
        btnSend = (Button)findViewById(R.id.send);

        for(int i=0;i<cb.length;i++){
            int rID = getResources().getIdentifier("cb" + (i + 1), "id", this.getBaseContext().getPackageName());
            cb[i] = (CheckBox) findViewById(rID);
            cb[i].setChecked(true);
        }
        db = new SQLiteHandler(this);
        sdf = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a");
        btnSend = (Button) findViewById(R.id.send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LayoutInflater factory = LayoutInflater.from(FormHotelActivity.this);
                final View exitDialogView = factory.inflate(R.layout.popup_yakin, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(FormHotelActivity.this).create();
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
        frmAlamat.setOnClickListener(new View.OnClickListener() {
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
                frmAlamat.setText(addressPlace.trim());
                LatLng latLng = place.getLatLng();
                lat = latLng.latitude;
                lng = latLng.longitude;
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e("TAG", "Error: Status = " + status.toString());
            }
        }
    }

    private void onSubmit() {
        if(!frmNamaTempat.getText().toString().trim().equals("") && !frmNamaPengusaha.getText().toString().trim().equals("")
                && !frmJmlKaryawan.getText().toString().trim().equals("") && !frmNoTelp.getText().toString().trim().equals("")
                && !frmAlamat.getText().toString().trim().equals("") && !frmNoIzinUsaha.getText().toString().trim().equals("")){

            final String namaTempat = frmNamaTempat.getText().toString().trim();
            final String namaPengelola = frmNamaPengusaha.getText().toString().trim();
            final String alamat = frmAlamat.getText().toString().trim();
            final String jumlahKaryawan = frmJmlKaryawan.getText().toString().trim();
            final String noTelp = frmNoTelp.getText().toString().trim();
            final String noIzinUsaha = frmNoIzinUsaha.getText().toString().trim();
            final String idPetugas = db.getUserDetails().get("id_petugas");
            double totalNilai = 0;
            String status = "";
            final String waktu = sdf.format(Calendar.getInstance().getTime().getTime());
            final String koordinat = String.valueOf(lat) + ", " + String.valueOf(lng);
            String[] nilaiCB = new String[cb.length];
            final HashMap<String, String> nilaiTPM = new HashMap<>();
            for(int i=0;i<cb.length;i++){
                if(cb[i].isChecked()){
                    nilaiCB[i] = String.valueOf(nilai[i] * bobot[i]);
                }else{
                    nilaiCB[i] = "0";
                }
                nilaiTPM.put("nilai_" + i , nilaiCB[i]);
                totalNilai = totalNilai + Double.valueOf(nilaiCB[i]);
            }
            if(totalNilai < 6000){
                status = "Tidak Sehat";
            }else if(totalNilai >= 6000 && totalNilai < 7500){
                status = "Kurang Sehat";
            }else if(totalNilai >= 7500 && totalNilai <= 10000){
                status = "Sehat";
            }

            final String txtTotalNilai = String.valueOf(totalNilai);

            String tag_string_req = "req_dam";

            final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(this, ProgressDialog.STYLE_SPINNER, false);
            progressDialogUtil.show();

            final String finalStatus = status;

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.SEND_DATA_HOTEL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Register Response: " + response.toString());
                    progressDialogUtil.dismiss();
                    DialogUtil dialogUtil = new DialogUtil();
                    dialogUtil.showDialog(FormHotelActivity.this, finalStatus);

                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");
                        if (!error) {

                            Toast.makeText(getApplicationContext(), "Data berhasil terkirim!", Toast.LENGTH_LONG).show();
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
                    params.put("alamat", alamat);
                    params.put("id_petugas", idPetugas);
                    params.put("jumlah_karyawan", jumlahKaryawan);
                    params.put("nama_pemilik", namaPengelola);
                    params.put("koordinat", koordinat);
                    params.put("nama_tempat", namaTempat);
                    params.put("status", finalStatus);
                    params.put("no_izin_usaha", noIzinUsaha);
                    params.put("total_nilai", txtTotalNilai);
                    params.put("waktu", waktu);
                    params.put("no_telp", noTelp);
                    params.putAll(nilaiTPM);

                    return params;
                }

            };

            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


        } else {
            Toast.makeText(this, "Error harap check semua opsi!", Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                FormHotelActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
