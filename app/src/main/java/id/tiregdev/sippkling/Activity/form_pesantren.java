package id.tiregdev.sippkling.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import id.tiregdev.sippkling.R;

public class form_pesantren extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pesantren);
        Button submit = findViewById(R.id.send);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LayoutInflater factory = LayoutInflater.from(form_pesantren.this);
                final View exitDialogView = factory.inflate(R.layout.popup_yakin, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(form_pesantren.this).create();
                exitDialog.setView(exitDialogView);
                exitDialogView.findViewById(R.id.ya).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                        final LayoutInflater factory = LayoutInflater.from(form_pesantren.this);
                        final View exitDialogView = factory.inflate(R.layout.popup_sukses, null);
                        final AlertDialog exitDialogs = new AlertDialog.Builder(form_pesantren.this).create();
                        exitDialogs.setView(exitDialogView);
                        exitDialogView.findViewById(R.id.sukses).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                exitDialogs.dismiss();
                                Toast.makeText(form_pesantren.this, "Pengisian Sukses", Toast.LENGTH_SHORT).show();
                                form_pesantren.this.finish();
                            }
                        });
                        exitDialogs.show();
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
        setToolbar();
    }

    public void setToolbar(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                form_pesantren.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
