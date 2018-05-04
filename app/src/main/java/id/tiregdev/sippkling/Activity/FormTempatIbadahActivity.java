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

public class FormTempatIbadahActivity extends AppCompatActivity {

    EditText frmNamaTempat, frmNamaPengurus, frmAlamat, frmJmlJamaah;
    Button btnSend;
    RadioGroup[] rg = new RadioGroup[37];
    RadioButton[] rb;
    double[] nTPM = new double[] {5, 5,
            4, 3, 3,
            4, 3, 3,
            5, 3, 2,
            6, 4,
            3, 3, 2, 2,
            5, 3, 2,
            6, 4,
            5, 5,
            10, 10,
            5, 3, 2,
            5, 5,
            3, 3, 2, 2,
            6, 4,
            5, 5,
            4, 3, 3 };

    double[] bTPM = new double[] {4, 4,
            4, 4, 4,
            5, 5, 5,
            5, 5, 5,
            5, 5,
            2, 2, 2, 2,
            5, 5, 5,
            4, 4,
            8, 8,
            5, 5,
            8, 8, 8,
            12, 12,
            7, 7, 7, 7,
            8, 8,
            8, 8,
            10, 10, 10 };
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
        setContentView(R.layout.activity_form_tempatibadah);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setInit();
    }

    private void setInit(){
        frmNamaTempat = (EditText)findViewById(R.id.namaTempat);
        frmNamaPengurus = (EditText)findViewById(R.id.namaPengurus);
        frmAlamat = (EditText)findViewById(R.id.alamat);
        frmJmlJamaah = (EditText)findViewById(R.id.jmlJamaah);
        sdf = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a");
        db = new SQLiteHandler(this);
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
                final LayoutInflater factory = LayoutInflater.from(FormTempatIbadahActivity.this);
                final View exitDialogView = factory.inflate(R.layout.popup_yakin, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(FormTempatIbadahActivity.this).create();
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
        if(!frmNamaTempat.getText().toString().trim().equals("") && !frmNamaPengurus.getText().toString().trim().equals("")
                && !frmAlamat.getText().toString().trim().equals("") && !frmJmlJamaah.getText().toString().trim().equals("")){

            final String txtNamaTempat = frmNamaTempat.getText().toString().trim();
            final String txtNamaPengurus = frmNamaPengurus.getText().toString().trim();
            final String txtAlamat = frmAlamat.getText().toString().trim();
            final String txtJmlJamaah = frmJmlJamaah.getText().toString().trim();
            final String idPetugas = db.getUserDetails().get("id_petugas");
            double totalNilai = 0;
            final String status;
            final String waktu = sdf.format(Calendar.getInstance().getTime().getTime());
            final String koordinat = String.valueOf(lat) + ", " + String.valueOf(lng);
            boolean hasValue = true;
            String[] txtRB = new String[37];
            String[] nilaiRB = new String[37];
            final HashMap<String, String> nilaiTTU = new HashMap<>();
            for(int i = 0; i<rg.length; i++)
            {
                if(rg[i].getCheckedRadioButtonId() != -1){
                    txtRB[i] = ((RadioButton)findViewById(rg[i].getCheckedRadioButtonId())).getText().toString();
                    if(txtRB[i].equals("YA")){
                        nilaiRB[i] = String.valueOf(nTPM[i] * bTPM[i]);
                    }else {
                        nilaiRB[i] = "0";
                    }
                    nilaiTTU.put("nilai_" + i , nilaiRB[i]);
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

                String tag_string_req = "req_dam";

                final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(this, ProgressDialog.STYLE_SPINNER, false);
                progressDialogUtil.show();

                StringRequest strReq = new StringRequest(Request.Method.POST,
                        AppConfig.SEND_DATA_TI, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Register Response: " + response.toString());
                        progressDialogUtil.dismiss();
                        DialogUtil dialogUtil = new DialogUtil();
                        dialogUtil.showDialog(FormTempatIbadahActivity.this, status);

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
                        params.put("jumlah_jamaah", txtJmlJamaah);
                        params.put("koordinat", koordinat);
                        params.put("nama_pengurus", txtNamaPengurus);
                        params.put("nama_tempat", txtNamaTempat);
                        params.put("status", status);
                        params.put("total_nilai", txtTotalNilai);
                        params.put("waktu", waktu);
                        params.putAll(nilaiTTU);

                        return params;
                    }

                };

                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


            }else{
                Toast.makeText(this, "Error harap check semua opsi!", Toast.LENGTH_SHORT).show();

            }

        }
        else{
            Toast.makeText(this, "Error harap isi semua opsi!", Toast.LENGTH_SHORT).show();
        }


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                FormTempatIbadahActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
