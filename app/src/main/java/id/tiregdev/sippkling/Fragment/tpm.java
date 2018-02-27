package id.tiregdev.sippkling.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import id.tiregdev.sippkling.Activity.dam;
import id.tiregdev.sippkling.Activity.jasaboga;
import id.tiregdev.sippkling.Activity.restoran;
import id.tiregdev.sippkling.R;

/**
 * Created by Muhammad63 on 2/5/2018.
 */

public class tpm extends Fragment {

    View v;
    RelativeLayout jb, resto, depot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tpm, container, false);
        declare();
        return v;
    }

    public void declare(){
        jb = v.findViewById(R.id.jasaboga);
        resto = v.findViewById(R.id.restoran);
        depot = v.findViewById(R.id.dam);

        jb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), jasaboga.class);
                startActivity(i);
            }
        });

        resto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), restoran.class);
                startActivity(i);
            }
        });

        depot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), dam.class);
                startActivity(i);
            }
        });
    }
}
