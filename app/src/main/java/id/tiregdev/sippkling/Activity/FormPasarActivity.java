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

public class FormPasarActivity extends AppCompatActivity {
    EditText frmNamaPasar, frmNamaPengelola, frmAlamat, frmJmlKios, frmJmlPedagang, frmJmlAsosiasi;
    Button btnSend;
    CheckBox[] cb = new CheckBox[172];
    double[] bobot = new double[]{5, 5, 5, 5, 5,
            0.5,
            4, 4, 4, 4, 4,
            0.5, 0.5, 0.5,
            4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
            1, 1, 1, 1, 1, 1, 1, 1, 1,
            0.5, 0.5, 0.5, 0.5, 0.5,
            0.5, 0.5, 0.5, 0.5, 0.5, 0.5,
            0.5, 0.5, 0.5, 0.5, 0.5, 0.5,
            0.5, 0.5, 0.5, 0.5,
            1,
            0.5,
            0.5,
            4, 4, 4, 4,
            4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
            4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
            4, 4, 4, 4, 4,
            4, 4, 4,
            3, 3, 3, 3, 3,
            4, 4, 4, 4, 4, 4, 4, 4, 4,
            3, 3,
            15, 15, 15, 15,
            10, 10,
            5,
            3, 3, 3, 3, 3, 3,
            2, 2,
            2, 2, 2,
            5, 5, 5, 5, 5, 5, 5, 5,
            3};
    double[] nilai = new double[]{20, 20, 20, 20, 20,
            100,
            25, 5, 15, 0, 15,
            40, 40, 20,
            4, 2, 2, 2, 4, 15, 15, 8, 6, 8, 5, 5, 4, 3, 3, 10,
            10, 5, 5, 20, 0, 5, 5, 0, 14, 20,
            4, 3, 3, 3, 3, 6, 14, 6, 4, 4, 6, 10, 5, 5, 0, 2, 2, 10,
            15, 10, 10, 0, 10, 15, 10, 10, 10,
            20, 10, 10, 40, 20,
            15, 15, 10, 20, 20, 0,
            15, 15, 15, 10, 15, 30,
            40, 20, 20, 20,
            100,
            100,
            0,
            40, 30, 20, 10,
            5, 5, 10, 10, 0, 10, 10, 10, 10, 10, 10,
            0, 5, 5, 4, 3, 3, 8, 7, 4, 4, 4, 3, 10, 10, 10,
            30, 30, 10, 10, 10,
            40, 0, 40,
            15, 20, 20, 20, 20,
            20, 10, 10, 5, 10, 10, 10, 10, 10,
            50, 50,
            5, 20, 0, 40,
            50, 0,
            50,
            20, 10, 10, 30, 20, 10,
            50, 50,
            40, 40, 20,
            20, 10, 10, 10, 10, 10, 10, 20,
            50};


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
        setContentView(R.layout.activity_form_pasar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setInit();
    }

    private void setInit() {
        frmNamaPasar = (EditText) findViewById(R.id.namaPasar);
        frmNamaPengelola = (EditText) findViewById(R.id.namaPJ);
        frmAlamat = (EditText) findViewById(R.id.alamatPasar);
        frmJmlKios = (EditText) findViewById(R.id.jumlahKios);
        frmJmlPedagang = (EditText) findViewById(R.id.jumlahPedagang);
        frmJmlAsosiasi = (EditText) findViewById(R.id.jumlahAsosiasi);
        btnSend = (Button) findViewById(R.id.send);

        for (int i = 0; i < cb.length; i++) {
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
                final LayoutInflater factory = LayoutInflater.from(FormPasarActivity.this);
                final View exitDialogView = factory.inflate(R.layout.popup_yakin, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(FormPasarActivity.this).create();
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
        if (!frmNamaPasar.getText().toString().trim().equals("") && !frmNamaPengelola.getText().toString().trim().equals("")
                && !frmAlamat.getText().toString().trim().equals("") && !frmJmlKios.getText().toString().trim().equals("")
                && !frmJmlPedagang.getText().toString().trim().equals("") && !frmJmlAsosiasi.getText().toString().trim().equals("")) {

            final String namaPasar = frmNamaPasar.getText().toString().trim();
            final String namaPengelola = frmNamaPengelola.getText().toString().trim();
            final String alamat = frmAlamat.getText().toString().trim();
            final String jumlahKios = frmJmlKios.getText().toString().trim();
            String jumlahPedagang = frmJmlPedagang.getText().toString().trim();
            final String jumlahAsosiasi = frmJmlAsosiasi.getText().toString().trim();
            final String idPetugas = db.getUserDetails().get("id_petugas");
            double totalNilai = 0;
            String status = "";
            final String waktu = sdf.format(Calendar.getInstance().getTime().getTime());
            final String koordinat = String.valueOf(lat) + ", " + String.valueOf(lng);
            String[] nilaiCB = new String[cb.length];
            final HashMap<String, String> nilaiTPM = new HashMap<>();
            for (int i = 0; i < cb.length; i++) {
                if (cb[i].isChecked()) {
                    nilaiCB[i] = String.valueOf(nilai[i] * bobot[i]);
                } else {
                    nilaiCB[i] = "0";
                }
                nilaiTPM.put("nilai_" + i, nilaiCB[i]);
                totalNilai = totalNilai + Double.valueOf(nilaiCB[i]);
            }
            if (totalNilai < 6000) {
                status = "Tidak Sehat";
            } else if (totalNilai >= 6000 && totalNilai < 7500) {
                status = "Kurang Sehat";
            } else if (totalNilai >= 7500 && totalNilai <= 10000) {
                status = "Sehat";
            }

            final String txtTotalNilai = String.valueOf(totalNilai);

            String tag_string_req = "req_dam";

            final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(this, ProgressDialog.STYLE_SPINNER, false);
            progressDialogUtil.show();

            final String finalStatus = status;

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.SEND_DATA_PASAR, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Register Response: " + response.toString());
                    progressDialogUtil.dismiss();
                    DialogUtil dialogUtil = new DialogUtil();
                    dialogUtil.showDialog(FormPasarActivity.this, finalStatus);

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
                    params.put("jumlah_asosiasi", jumlahAsosiasi);
                    params.put("jumlah_kios", jumlahKios);
                    params.put("jumlah_pedagang", jumlahAsosiasi);
                    params.put("koordinat", koordinat);
                    params.put("nama_pengelola", namaPengelola);
                    params.put("nama_tempat", namaPasar);
                    params.put("status", finalStatus);
                    params.put("total_nilai", txtTotalNilai);
                    params.put("waktu", waktu);
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
                FormPasarActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
