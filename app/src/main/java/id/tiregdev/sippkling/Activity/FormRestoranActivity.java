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
import id.tiregdev.sippkling.utils.DialogUtil;
import id.tiregdev.sippkling.utils.ProgressDialogUtil;
import id.tiregdev.sippkling.utils.SQLiteHandler;

public class FormRestoranActivity extends AppCompatActivity {

    EditText frmNamaTempat, frmNamaPengusaha, frmJmlKaryawan, frmJmlPenjamah, frmAlamat, frmNoIzinUsaha;
    Button btnSend;
    RadioGroup[] rg = new RadioGroup[130];
    RadioButton[] rb;
    double[] nTPM = new double[] {6, 4,
            4, 2, 2, 2,
            4, 2, 1, 1, 1, 1,
            4, 2, 1, 1, 1, 1,
            4, 3, 3,
            5, 3, 2,
            5, 3, 2,
            5, 3, 2,
            4, 4, 2,
            4, 3, 3,
            5, 2, 2, 1,
            3, 3, 2, 2,
            3, 2, 2, 2, 1,
            4, 3, 2, 1,
            5, 3, 2,
            2, 2, 2, 4,
            5, 3, 2,
            2, 3, 3, 2,
            3, 2, 2, 3,
            3, 2, 2, 1, 1, 1,
            3, 2, 2, 2, 1,
            4, 2, 2, 2,
            3, 3, 2, 2,
            4, 3, 3,
            5, 3, 2,
            3, 2, 2, 2, 1,
            6, 4,
            3, 3, 2, 2,
            4, 2, 2, 1, 1,
            2, 2, 4, 2,
            3, 2, 2, 3, 0,
            3, 2, 1, 2, 2,
            3, 3, 2, 2 };

    double[] bTPM = new double[] {2, 2,
            2, 2, 2, 2,
            1, 1, 1, 1, 1, 1,
            0.5, 0.5, 0.5, 0.5, 0.5, 0.5,
            0.5, 0.5, 0.5,
            1, 1, 1,
            1, 1, 1,
            0.5, 0.5, 0.5,
            0.5, 0.5, 0.5,
            1, 1, 1,
            3, 3, 3, 3,
            2, 2, 2, 2,
            1, 1, 1, 1, 1,
            2, 2, 2, 2,
            2, 2, 2,
            1, 1, 1, 1,
            1, 1, 1,
            1, 1, 1, 1,
            2, 2, 2, 2,
            7, 7, 7, 7, 7, 7,
            5, 5, 5, 5, 5,
            3, 3, 3, 3,
            5, 5, 5, 5,
            6, 6, 6,
            5, 5, 5,
            4, 4, 4, 4, 4,
            5, 5,
            5, 5, 5, 5,
            15, 15, 15, 15, 15,
            4, 4, 4, 4,
            2, 2, 2, 2, 2,
            2, 2, 2, 2, 2,
            7, 7, 7, 7 };

    private double lat;
    private double lng;
    SimpleDateFormat sdf;
    SQLiteHandler db;
    private ProgressDialog pDialog;

    private static final String TAG = FormJasabogaActivity.class.getSimpleName();
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_restoran);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setInit();
    }

    private void setInit(){
        frmNamaTempat = (EditText)findViewById(R.id.frmNamaTempat);
        frmNamaPengusaha = (EditText)findViewById(R.id.frmNamaPengusaha);
        frmJmlKaryawan = (EditText)findViewById(R.id.frmJmlKaryawan);
        frmJmlPenjamah = (EditText)findViewById(R.id.frmJmlPenjamah);
        frmAlamat = (EditText)findViewById(R.id.frmAlamat);
        frmNoIzinUsaha = (EditText)findViewById(R.id.frmNoIzin);
        db = new SQLiteHandler(this);
        sdf = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a");
        pDialog = new ProgressDialog(this);
        for(int i = 0; i<rg.length; i++)
        {
            int rID = getResources().getIdentifier("rg" + i, "id", this.getBaseContext().getPackageName());
            rg[i] = (RadioGroup)findViewById(rID);
            rg[i].removeAllViews();
            String[] yesNo = getResources().getStringArray(R.array.choice_array);
            rb = new RadioButton[2];

            for(int j = 0; j<rb.length; j++){
                rb[j] = new RadioButton(this);
                rb[j].setId(100 + j);
                rb[j].setText(yesNo[j]);
                rg[i].addView(rb[j]);
            }
            rg[i].check(rg[i].getChildAt(0).getId());

        }
        btnSend = (Button)findViewById(R.id.send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LayoutInflater factory = LayoutInflater.from(FormRestoranActivity.this);
                final View exitDialogView = factory.inflate(R.layout.popup_yakin, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(FormRestoranActivity.this).create();
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
    private void onSubmit(){
        if(!frmNamaTempat.getText().toString().trim().equals("") && !frmNamaPengusaha.getText().toString().trim().equals("")
                && !frmJmlKaryawan.getText().toString().trim().equals("") && !frmJmlPenjamah.getText().toString().trim().equals("")
                && !frmAlamat.getText().toString().trim().equals("") && !frmNoIzinUsaha.getText().toString().trim().equals("")){
            final String txtNamaTempat = frmNamaTempat.getText().toString().trim();
            final String txtNamaPengusaha = frmNamaPengusaha.getText().toString().trim();
            final String txtJmlKaryawan = frmJmlKaryawan.getText().toString().trim();
            final String txtJmlPenjamah = frmJmlPenjamah.getText().toString().trim();
            final String txtAlamat = frmAlamat.getText().toString().trim();
            final String txtNoIzinUsaha = frmNoIzinUsaha.getText().toString().trim();
            final String idPetugas = db.getUserDetails().get("id_petugas");
            double totalNilai = 0;
            boolean hasValue = true;
            final String status;
            final String waktu = sdf.format(Calendar.getInstance().getTime().getTime());
            final String koordinat = String.valueOf(lat) + ", " + String.valueOf(lng);
            String[] txtRB = new String[rg.length];
            String[] nilaiRB = new String[rg.length];
            final HashMap<String, String> nilaiTPM = new HashMap<>();
            for(int i = 0; i<rg.length; i++)
            {
                if(rg[i].getCheckedRadioButtonId() != -1){
                    txtRB[i] = ((RadioButton)findViewById(rg[i].getCheckedRadioButtonId())).getText().toString();
                    if(txtRB[i].equals("YA")){
                        nilaiRB[i] = String.valueOf(nTPM[i] * bTPM[i]);
                    }else {
                        nilaiRB[i] = "0";
                    }
                    nilaiTPM.put("nilai_" + i , nilaiRB[i]);
                    totalNilai = totalNilai + Double.valueOf(nilaiRB[i]);
                }else{
                    hasValue = false;
                }
            }
            if(hasValue){
                if(totalNilai >= 700){
                    status = "Laik Hygiene Sanitasi";
                }else {
                    status = "Tidak Laik Hygiene Sanitasi";
                }
                final String txtTotalNilai = String.valueOf(totalNilai);

                String tag_string_req = "req_restoran";

                final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(this, ProgressDialog.STYLE_SPINNER, false);
                progressDialogUtil.show();

                StringRequest strReq = new StringRequest(Request.Method.POST,
                        AppConfig.SEND_DATA_RM, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Register Response: " + response.toString());
                        progressDialogUtil.dismiss();
                        DialogUtil dialogUtil = new DialogUtil();
                        dialogUtil.showDialog(FormRestoranActivity.this, status);

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
                        params.put("alamat", txtAlamat);
                        params.put("id_petugas", idPetugas);
                        params.put("jumlah_karyawan", txtJmlKaryawan);
                        params.put("jumlah_penjamah", txtJmlPenjamah);
                        params.put("koordinat", koordinat);
                        params.put("nama_pemilik", txtNamaPengusaha);
                        params.put("nama_tempat", txtNamaTempat);
                        params.put("no_izin_usaha", txtNoIzinUsaha);
                        params.put("status", status);
                        params.put("total_nilai", txtTotalNilai);
                        params.put("waktu", waktu);
                        params.putAll(nilaiTPM);

                        return params;
                    }

                };

                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

                DialogUtil dialogUtil = new DialogUtil();
                dialogUtil.showDialog(FormRestoranActivity.this, status);

            }else{
                Toast.makeText(this, "Error harap check semua opsi!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Error semua harap diisi!", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                FormRestoranActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
