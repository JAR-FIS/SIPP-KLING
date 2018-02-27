package id.tiregdev.sippkling.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import id.tiregdev.sippkling.R;

public class login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        declare();
        didTapButton();
    }

    public void declare(){
        EditText email = findViewById(R.id.email);
        EditText pass = findViewById(R.id.pass);
        Button login = findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(id.tiregdev.sippkling.Activity.login.this, Menu_utama.class);
                startActivity(i);
            }
        });
    }

    public void didTapButton() {
        TextView daftar = findViewById(R.id.daftar);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LayoutInflater factory = LayoutInflater.from(login.this);
                final View exitDialogView = factory.inflate(R.layout.popup_daftar, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(login.this).create();
                exitDialog.setView(exitDialogView);
                exitDialogView.findViewById(R.id.btClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                    }
                });
                exitDialog.show();
            }
        });
    }
}
