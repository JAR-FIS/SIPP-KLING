package id.tiregdev.sippkling.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import id.tiregdev.sippkling.Activity.ListDataActivity;
import id.tiregdev.sippkling.R;

/**
 * Created by Muhammad63 on 2/5/2018.
 */

public class TPMFragment extends Fragment {

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
                Intent i = new Intent(getActivity(), ListDataActivity.class);
                i.putExtra("TITLE", "jasaboga");
                startActivity(i);
            }
        });

        resto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ListDataActivity.class);
                i.putExtra("TITLE", "restoran");
                startActivity(i);
            }
        });

        depot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ListDataActivity.class);
                i.putExtra("TITLE", "dam");
                startActivity(i);
            }
        });
    }
}
