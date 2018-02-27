package id.tiregdev.sippkling.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import id.tiregdev.sippkling.R;

public class sab extends AppCompatActivity {

    RelativeLayout pompa, sumurgali, sumugaliplus, perpipaan, mataAir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sab);
        declare();
    }

    public void declare(){
        pompa = findViewById(R.id.pompa);
        sumurgali = findViewById(R.id.sumurgali);
        sumugaliplus = findViewById(R.id.sumurgaliplus);
        perpipaan = findViewById(R.id.perpipaan);
        mataAir = findViewById(R.id.mataAir);

        pompa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(sab.this, form_sabpompa.class);
                startActivity(i);
            }
        });

        sumurgali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(sab.this, form_sab_sumurgali.class);
                startActivity(i);
            }
        });

        sumugaliplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(sab.this, form_sab_sumurgaliplus.class);
                startActivity(i);
            }
        });

        perpipaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(sab.this, "Perpipaan", Toast.LENGTH_SHORT).show();
            }
        });

        mataAir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(sab.this, "Mata Air", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Silahkan pilih jenis SAB yang digunakan terlebih dahulu!", Toast.LENGTH_SHORT).show();
    }
}
