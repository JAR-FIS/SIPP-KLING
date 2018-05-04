package id.tiregdev.sippkling.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.tiregdev.sippkling.R;
import id.tiregdev.sippkling.utils.AppConfig;
import id.tiregdev.sippkling.utils.AppController;
import id.tiregdev.sippkling.utils.SQLiteHandler;
import id.tiregdev.sippkling.utils.SessionManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViews();
    }

    private EditText email;
    private EditText pass;
    private Button btnLogin;
    private TextView daftar;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private static final String TAG = LoginActivity.class.getSimpleName();

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-03-18 17:55:01 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        email = (EditText)findViewById( R.id.email );
        pass = (EditText)findViewById( R.id.pass );
        btnLogin = (Button)findViewById( R.id.btnLogin );
        daftar = (TextView)findViewById( R.id.daftar );

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
        btnLogin.setOnClickListener( this );
        daftar.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-03-18 17:55:01 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == btnLogin ) {
            // Handle clicks for btnLogin
             onLogInUser();
//            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
//            startActivity(intent);
        } else if (v == daftar) {
            final LayoutInflater factory = LayoutInflater.from(LoginActivity.this);
            final View exitDialogView = factory.inflate(R.layout.popup_daftar, null);
            final AlertDialog exitDialog = new AlertDialog.Builder(LoginActivity.this).create();
            exitDialog.setView(exitDialogView);
            exitDialogView.findViewById(R.id.btClose).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exitDialog.dismiss();
                }
            });
            exitDialog.show();
        }
    }
    private void onLogInUser() {
        String emailUser = email.getText().toString().trim();
        String passUser = pass.getText().toString().trim();
        // Check for empty data in the form
        if (!emailUser.isEmpty() && !passUser.isEmpty()) {
            // LoginActivity user
            checkLogin(emailUser, passUser);
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext(),
                    "Please enter the credentials!", Toast.LENGTH_LONG)
                    .show();
        }
    }
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.USER_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create LoginActivity session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("id_petugas");

                        JSONObject user = jObj.getJSONObject("user");
                        String nama = user.getString("nama");
                        String email = user.getString("email");
                        String kecamatan = user.getString("kecamatan");
                        String kelurahan = user.getString("kelurahan");

                        // Inserting row in users table
                        db.addUser(nama, email, uid, kecamatan, kelurahan);

                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this,
                                MenuActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in LoginActivity. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                // Posting parameters to LoginActivity url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
