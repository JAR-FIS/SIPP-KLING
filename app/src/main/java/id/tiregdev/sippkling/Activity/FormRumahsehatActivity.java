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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import id.tiregdev.sippkling.R;
import id.tiregdev.sippkling.utils.AppConfig;
import id.tiregdev.sippkling.utils.AppController;
import id.tiregdev.sippkling.utils.DialogUtil;
import id.tiregdev.sippkling.utils.ProgressDialogUtil;
import id.tiregdev.sippkling.utils.SQLiteHandler;

public class FormRumahsehatActivity extends AppCompatActivity {

    private EditText frmNamaKK, frmAlamat, frmJmlAnggota, frmNoRumah, frmNIK, frmNoKIS;
    private Spinner spinnerRT, spinnerRW, spinnerSAB;
    private Button btnSend;
    private SimpleDateFormat sdf;
    private RadioGroup[] rg = new RadioGroup[17];
    private RadioGroup rg22, rg21, rg20, rg19, rgStatus;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private double lat, lng;
    private SQLiteHandler db;
    private ProgressDialogUtil progressDialogUtil;
    private static final String TAG = FormRumahsehatActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_rumahsehat);
        setToolbar();
        setInit();
    }

    public void setToolbar(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void setInit(){
        frmNamaKK = (EditText)findViewById(R.id.namaKK);
        frmAlamat = (EditText)findViewById(R.id.alamatKK);
        frmNIK = (EditText)findViewById(R.id.nik);
        frmJmlAnggota = (EditText)findViewById(R.id.jmlAnggota);
        frmNoRumah = (EditText)findViewById(R.id.noRumah);
        spinnerRT = (Spinner)findViewById(R.id.spinner_rt);
        spinnerRW = (Spinner)findViewById(R.id.spinner_rw);
        spinnerSAB = (Spinner)findViewById(R.id.spinner_sab);
        rg22 = (RadioGroup)findViewById(R.id.rg22);
        rg21 = (RadioGroup)findViewById(R.id.rg21);
        rg20 = (RadioGroup)findViewById(R.id.rg20);
        rg19 = (RadioGroup)findViewById(R.id.rg19);
        rgStatus = (RadioGroup)findViewById(R.id.statusRumah);
        db = new SQLiteHandler(this);
        sdf = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a");
        progressDialogUtil = new ProgressDialogUtil(this, ProgressDialog.STYLE_SPINNER, false);
        for(int i = 1; i<=17; i++)
        {
            int rID = getResources().getIdentifier("rg" + i, "id", this.getBaseContext().getPackageName());
            rg[i-1] = (RadioGroup)findViewById(rID);
            rg[i-1].check(rg[i-1].getChildAt(0).getId());


        }
        btnSend = (Button)findViewById(R.id.send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LayoutInflater factory = LayoutInflater.from(FormRumahsehatActivity.this);
                final View exitDialogView = factory.inflate(R.layout.popup_yakin, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(FormRumahsehatActivity.this).create();
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
        if(!frmJmlAnggota.getText().toString().trim().equals("") && !frmNoRumah.getText().toString().trim().equals("")
                && !frmAlamat.getText().toString().trim().equals("")
                && !frmNIK.getText().toString().trim().equals("") && rg19.getCheckedRadioButtonId() != -1
                && rgStatus.getCheckedRadioButtonId() != -1 && rg20.getCheckedRadioButtonId() != -1 && rg21.getCheckedRadioButtonId() != -1
                && rg22.getCheckedRadioButtonId() != -1 ) {
            sendData();
        }


        else{
            Toast.makeText(this, "Error harap isi semua opsi!", Toast.LENGTH_SHORT).show();
        }


    }

    private void sendData(){

        final String txtNoRumah = frmNoRumah.getText().toString().trim();
        final String txtRT = spinnerRT.getSelectedItem().toString().trim();
        final String txtRW = spinnerRW.getSelectedItem().toString().trim();
        final String idPetugas = db.getUserDetails().get("id_petugas");
        final String waktu = sdf.format(Calendar.getInstance().getTime().getTime());
        final String koordinat = String.valueOf(lat) + ", " + String.valueOf(lng);
        final String alamat = frmAlamat.getText().toString().trim();
        final String namaKK = frmNamaKK.getText().toString().trim();
        String nik = frmNIK.getText().toString().trim();
        final String statusRumah = ((RadioButton)findViewById(rgStatus.getCheckedRadioButtonId())).getText().toString();
        final String jamban = ((RadioButton)findViewById(rg21.getCheckedRadioButtonId())).getText().toString();
        final String spal = ((RadioButton)findViewById(rg22.getCheckedRadioButtonId())).getText().toString();
        final String pjb = ((RadioButton)findViewById(rg20.getCheckedRadioButtonId())).getText().toString();
        final String sampah = ((RadioButton)findViewById(rg19.getCheckedRadioButtonId())).getText().toString();

        final String idSAB = UUID.randomUUID().toString();

        int totalNilai = 0;
        int jmlAnggota = 0;
        boolean hasValue = true;
        final String status;
        String[] txtRB = new String[17];
        String[] nilaiRB = new String[17];
        final HashMap<String, String> nilaiRS = new HashMap<>();

        for(int i = 0; i<=16; i++)
        {
            if(rg[i].getCheckedRadioButtonId() != -1 && rg19.getCheckedRadioButtonId() != -1
                    && rg20.getCheckedRadioButtonId() != -1 && rg21.getCheckedRadioButtonId() != -1){
                if(i < 8){
                    txtRB[i] = ((RadioButton)findViewById(rg[i].getCheckedRadioButtonId())).getText().toString().trim().substring(0, 1);
                    nilaiRB[i] = String.valueOf(setNilai(txtRB[i]) * 31);
                }else if(i >= 8 && i < 12){
                    txtRB[i] = ((RadioButton)findViewById(rg[i].getCheckedRadioButtonId())).getText().toString().trim().substring(0, 1);
                    nilaiRB[i] = String.valueOf(setNilai(txtRB[i]) * 25);
                }else if(i >= 12 && i <= 16){
                    txtRB[i] = ((RadioButton)findViewById(rg[i].getCheckedRadioButtonId())).getText().toString().trim().substring(0, 1);
                    nilaiRB[i] = String.valueOf(setNilai(txtRB[i]) * 44);
                }

                nilaiRS.put("nilai_" + i , nilaiRB[i]);
                totalNilai = totalNilai + Integer.parseInt(nilaiRB[i]);
            }else{
                hasValue = false;
            }
        }
        if(hasValue){
            final String pushRS = UUID.randomUUID().toString();
            if(frmNamaKK.getText().toString().contains(",") && frmJmlAnggota.getText().toString().contains(",") && frmNIK.getText().toString().contains(",")){
                String[] splitNamaKK = frmNamaKK.getText().toString().replace(" ", "").trim().trim().split(",");
                String[] splitJmlAnggota = frmJmlAnggota.getText().toString().replace(" ", "").trim().split(",");
                String[] splitNik = frmNIK.getText().toString().replace(" ", "").trim().split(",");
                for(int i = 0;i<splitNamaKK.length;i++){
                        submitKK(splitNamaKK[i], splitJmlAnggota[i], idPetugas, pushRS, splitNik[i]);
                        jmlAnggota = jmlAnggota + Integer.parseInt(splitJmlAnggota[i]);
                }
            }else{
                    String txtNamaKK = frmNamaKK.getText().toString().trim();
                    String txtJmlAnggota = frmJmlAnggota.getText().toString().trim();
                    String txtNik = frmNIK.getText().toString().trim();

                    submitKK(txtNamaKK, txtJmlAnggota, idPetugas, pushRS, txtNik);

                    jmlAnggota = Integer.parseInt(frmJmlAnggota.getText().toString().trim());
            }

            if(totalNilai <= 1068){
                status = "Rumah Tidak Sehat";
            }else {
                status = "Rumah Sehat";
            }

            final String txtTotalNilai = String.valueOf(totalNilai);
            final String txtxJmlAnggota = String.valueOf(jmlAnggota);

            String tag_string_req = "req_rs";
            progressDialogUtil.show();

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.SEND_DATA_RS, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Register Response: " + response.toString());


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
                    params.put("id_rumah_sehat",pushRS);
                    params.put("alamat",alamat);
                    params.put("id_petugas",idPetugas);
                    params.put("id_sab", idSAB);
                    params.put("jamban", jamban);
                    params.put("jumlah_anggota", txtxJmlAnggota);
                    params.put("koordinat",koordinat);
                    params.put("nama_kk",namaKK);
                    params.put("no_rumah", txtNoRumah);
                    params.put("pjb", pjb);
                    params.put("rt", txtRT);
                    params.put("rw",txtRW);
                    params.put("sampah",sampah);
                    params.put("spal", spal);
                    params.put("status", status);
                    params.put("status_rumah", statusRumah);
                    params.put("total_nilai", txtTotalNilai);
                    params.put("waktu", waktu);
                    params.putAll(nilaiRS);

                    return params;
                }

            };

            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

            progressDialogUtil.dismiss();
            DialogUtil dialogUtil = new DialogUtil();
            dialogUtil.showDialog(FormRumahsehatActivity.this, status);

            if(spinnerSAB.getSelectedItemPosition() == 0){
                Toast.makeText(this, "Data berhasil dikirim!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), FormSABPompaActivity.class);
                intent.putExtra("idSAB", idSAB);
                intent.putExtra("idRS", pushRS);
                intent.putExtra("koordinat", koordinat);
                intent.putExtra("alamat", frmAlamat.getText().toString().trim());
                intent.putExtra("kategori", "A");
                startActivity(intent);
                finish();
            }else if(spinnerSAB.getSelectedItemPosition() == 1){
                Toast.makeText(this, "Data berhasil dikirim!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), FormSABSumurGaliActivity.class);
                intent.putExtra("idSAB", idSAB);
                intent.putExtra("idRS", pushRS);
                intent.putExtra("koordinat", koordinat);
                intent.putExtra("alamat", frmAlamat.getText().toString().trim());
                intent.putExtra("kategori", "B");
                startActivity(intent);
                finish();
            }else if(spinnerSAB.getSelectedItemPosition() == 2){
                Toast.makeText(this, "Data berhasil dikirim!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), FormSABSumurGaliPlusActivity.class);
                intent.putExtra("idSAB", idSAB);
                intent.putExtra("idRS", pushRS);
                intent.putExtra("koordinat", koordinat);
                intent.putExtra("alamat", frmAlamat.getText().toString().trim());
                intent.putExtra("kategori", "C");
                startActivity(intent);
                finish();
            }else if(spinnerSAB.getSelectedItemPosition() == 3){
                String kategori = "D";
//            SAB setSAB = new SAB(kategori, waktu, alamat, koordinat, idPetugas, pushRS);
                Toast.makeText(this, "Data berhasil dikirim!", Toast.LENGTH_SHORT).show();
                finish();
            }else if(spinnerSAB.getSelectedItemPosition() == 4) {
                String kategori = "E";
                Toast.makeText(this, "Data berhasil dikirim!", Toast.LENGTH_SHORT).show();
                finish();
            }


        }else{
            Toast.makeText(this, "Error harap check semua opsi!", Toast.LENGTH_SHORT).show();
        }

    }

    private void submitKK(final String txtNamaKK, final String txtJmlAnggota, final String idPetugas,
                          final String pushRS, final String txtNik)
    {
        String tag_string_req = "req_send_kk";
        progressDialogUtil.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.SEND_DATA_KK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "KK Response: " + response.toString());
                progressDialogUtil.dismiss();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Toast.makeText(getApplicationContext(), "Data KK berhasil terkirim!", Toast.LENGTH_LONG).show();
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
                params.put("id_petugas",idPetugas);
                params.put("id_rumah_sehat",pushRS);
                params.put("jumlah_anggota_perKK", txtJmlAnggota);
                params.put("nama_perKK", txtNamaKK);
                params.put("nik", txtNik);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }

    private int setNilai(String abc){
        int nilaiBobot = 0;
        switch (abc) {
            case "a":
                nilaiBobot = 0;
                break;
            case "b":
                nilaiBobot = 1;
                break;
            case "c":
                nilaiBobot = 2;
                break;
            case "d":
                nilaiBobot = 3;
                break;
            case "e":
                nilaiBobot = 4;
                break;
        }
        return nilaiBobot;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                FormRumahsehatActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
