package id.tiregdev.sippkling.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import id.tiregdev.sippkling.R;
import id.tiregdev.sippkling.utils.AppConfig;
import id.tiregdev.sippkling.utils.AppController;
import id.tiregdev.sippkling.utils.SQLiteHandler;

public class form_sabpompa extends AppCompatActivity {

    EditText frmNamaPuskesmas, frmKodeSarana, frmKodeSampel, frmGolongan, frmPemilikSarana;
    RadioGroup rgSampelAir;
    RadioGroup[] rg = new RadioGroup[12];
    RadioButton[] rb;
    Button btnSend;
    private double lat;
    private double lng;
    SimpleDateFormat sdf;
    String txtAlamat;
    SQLiteHandler db;
    private ProgressDialog pDialog;
    private static final String TAG = form_sabpompa.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_sab_pompa);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setInit();

    }

    private void setInit(){
        frmNamaPuskesmas = (EditText)findViewById(R.id.namaPuskesmas);
        frmKodeSarana = (EditText)findViewById(R.id.kodeSarana);
        frmPemilikSarana = (EditText)findViewById(R.id.pemilikSarana);
        frmKodeSampel = (EditText)findViewById(R.id.noKode);
        frmGolongan = (EditText)findViewById(R.id.golongan);
        rgSampelAir = (RadioGroup)findViewById(R.id.sampelAir);
        sdf = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a");
        db = new SQLiteHandler(this);
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
        }
        btnSend = (Button)findViewById(R.id.send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });

    }

    private void onSubmit(){
        if(!frmNamaPuskesmas.getText().toString().trim().equals("") && !frmKodeSarana.getText().toString().trim().equals("")
                && !frmPemilikSarana.getText().toString().trim().equals("") && !frmKodeSampel.getText().toString().trim().equals("")
                && !frmGolongan.getText().toString().trim().equals("") && rgSampelAir.getCheckedRadioButtonId() != -1){
        sendData();

        }else{
            Toast.makeText(this, "Error harap isi semua opsi!", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendData(){
        final String txtNamaPuskesmas = frmNamaPuskesmas.getText().toString().trim();
        final String txtKodeSarana = frmKodeSarana.getText().toString().trim();
        final String txtPemilikSarana = frmPemilikSarana.getText().toString().trim();
        final String txtKodeSampel = frmKodeSampel.getText().toString().trim();
        final String txtGolongan = frmGolongan.getText().toString().trim();
        final String idPetugas = db.getUserDetails().get("id_petugas");
        final String txtSudahDiambil = ((RadioButton)findViewById(rgSampelAir.getCheckedRadioButtonId())).getText().toString();
        final String waktu = sdf.format(Calendar.getInstance().getTime().getTime());
        int totalNilai = 0;
        String status = "";
        boolean hasValue = true;
        String[] txtRB = new String[rg.length];
        String[] nilaiRB = new String[rg.length];
        final HashMap<String, String> nilaiSAB = new HashMap<>();
        for(int i = 0; i<rg.length; i++)
        {
            if(rg[i].getCheckedRadioButtonId() != -1){
                txtRB[i] = ((RadioButton)findViewById(rg[i].getCheckedRadioButtonId())).getText().toString();
                if(txtRB[i].equals("YA")){
                    nilaiRB[i] = "1";
                }else {
                    nilaiRB[i] = "0";
                }
                nilaiSAB.put("nilai_" + i , nilaiRB[i]);
                totalNilai = totalNilai + Integer.valueOf(nilaiRB[i]);
            }else{
                hasValue = false;
            }
        }

        if(hasValue){
            if(totalNilai >= 8){
                status = "Amat Tinggi (AT)";
            }else if(totalNilai >= 6 && totalNilai < 8 ) {
                status = "Tinggi (T)";
            }else if(totalNilai >= 3 && totalNilai < 6 ) {
                status = "Sedang (S)";
            }else if(totalNilai >= 0 && totalNilai < 3 ) {
                status = "Rendah (R)";
            }

            final String txtStatus = status;
            String tag_string_req = "req_sab";

            pDialog.setMessage("Mengirim Permintaan ...");
            showDialog();

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.SEND_DATA_SAB, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Register Response: " + response.toString());
                    hideDialog();

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
                    hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting params to register url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id_sab", getIntent().getExtras().getString("idSAB"));
                    params.put("id_rumah_sehat",getIntent().getExtras().getString("idRS"));
                    params.put("id_petugas", idPetugas);
                    params.put("alamat", getIntent().getExtras().getString("alamat"));
                    params.put("golongan", txtGolongan);
                    params.put("kategori",getIntent().getExtras().getString("kategori"));
                    params.put("kode_sampel",txtKodeSampel);
                    params.put("kode_sarana", txtKodeSarana);
                    params.put("koordinat", getIntent().getExtras().getString("koordinat"));
                    params.put("nama_puskesmas", txtNamaPuskesmas);
                    params.put("pemilik_sarana", txtPemilikSarana);
                    params.put("status",txtStatus);
                    params.put("sudah_diambil", txtSudahDiambil);
                    params.put("waktu", waktu);
                    params.putAll(nilaiSAB);
                    params.put("nilai_12","0");
                    params.put("nilai_13","0");
                    params.put("nilai_14","0");

                    return params;
                }

            };

            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        }else{
            Toast.makeText(this, "Error harap check semua opsi!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                form_sabpompa.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
