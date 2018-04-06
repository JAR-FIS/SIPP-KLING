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

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import id.tiregdev.sippkling.R;
import id.tiregdev.sippkling.utils.SQLiteHandler;

public class form_restoran extends AppCompatActivity {

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

    private static final String TAG = form_jasaboga.class.getSimpleName();
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

        }
        btnSend = (Button)findViewById(R.id.send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
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
            String txtNamaTempat = frmNamaTempat.getText().toString().trim();
            String txtNamaPengusaha = frmNamaPengusaha.getText().toString().trim();
            String txtJmlKaryawan = frmJmlKaryawan.getText().toString().trim();
            String txtJmlPenjamah = frmJmlPenjamah.getText().toString().trim();
            String txtAlamat = frmAlamat.getText().toString().trim();
            String txtNoIzinUsaha = frmNoIzinUsaha.getText().toString().trim();
            String idPetugas = db.getUserDetails().get("id_petugas");
            double totalNilai = 0;
            boolean hasValue = true;
            String status;
            String waktu = sdf.format(Calendar.getInstance().getTime().getTime());
            String koordinat = String.valueOf(lat) + ", " + String.valueOf(lng);
            String[] txtRB = new String[rg.length];
            String[] nilaiRB = new String[rg.length];
            HashMap<String, String> nilaiTPM = new HashMap<>();
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
                submitTPM(txtNamaTempat, txtNamaPengusaha, txtJmlKaryawan, txtJmlPenjamah, txtNoIzinUsaha, waktu, txtAlamat, koordinat, idPetugas, totalNilai, status, nilaiTPM);
            }else{
                Toast.makeText(this, "Error harap check semua opsi!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Error semua harap diisi!", Toast.LENGTH_SHORT).show();
        }


    }
    private void submitTPM(String txtNamaTempat, String txtNamaPengusaha, String txtJmlKaryawan, String txtJmlPenjamah,
                           String txtNoIzinUsaha, String waktu, String txtAlamat, String koordinat, String idPetugas, double totalNilai, String status, HashMap<String, String> nilaiTPM){
//        RM setRM = new RM(txtNamaTempat, txtNamaPengusaha, txtJmlKaryawan, txtJmlPenjamah, txtNoIzinUsaha, waktu, txtAlamat, koordinat, idPetugas, totalNilai, status);
//        String pushID = mDatabase.child("tpm").child("rm").push().getKey();
//        mDatabase.child("tpm").child("rm").child(pushID).child("data").setValue(setRM);
//        mDatabase.child("tpm").child("rm").child(pushID).child("nilai").setValue(nilaiTPM);
        Toast.makeText(this, "Data berhasil dikirim!", Toast.LENGTH_SHORT).show();
        finish();
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
                form_restoran.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
