package id.tiregdev.sippkling.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import id.tiregdev.sippkling.Activity.ListDataActivity;
import id.tiregdev.sippkling.R;

/**
 * Created by Muhammad63 on 2/5/2018.
 */

public class TTUFragment extends Fragment {

    View v;
    RelativeLayout tempatibadahs, pasars, sekolahs, pesantrens, kolamrenangs, faskess, hotels, hotelmelatis;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_ttu, container, false);
        declare();
        return v;
    }

    public void declare(){
        tempatibadahs = v.findViewById(R.id.tempatIbadah);
        pasars = v.findViewById(R.id.pasar);
        sekolahs = v.findViewById(R.id.sekolah);
        pesantrens = v.findViewById(R.id.pesantren);
        kolamrenangs = v.findViewById(R.id.kolamrenang);
        faskess = v.findViewById(R.id.faskes);
        hotels = v.findViewById(R.id.hotel);
        hotelmelatis = v.findViewById(R.id.hotelMelati);

        tempatibadahs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ListDataActivity.class);
                i.putExtra("TITLE", "tempatibadah");
                startActivity(i);
            }
        });

        pasars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ListDataActivity.class);
                i.putExtra("TITLE", "pasar");
                startActivity(i);
            }
        });

        sekolahs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ListDataActivity.class);
                i.putExtra("TITLE", "sekolah");
                startActivity(i);
            }
        });

        pesantrens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ListDataActivity.class);
                i.putExtra("TITLE", "pesantren");
                startActivity(i);
            }
        });

        kolamrenangs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ListDataActivity.class);
                i.putExtra("TITLE", "kolam");
                startActivity(i);
            }
        });

        faskess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LayoutInflater factory = LayoutInflater.from(getActivity());
                final View exitDialogView = factory.inflate(R.layout.popup_faskes, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(getActivity()).create();
                exitDialog.setView(exitDialogView);
                exitDialogView.findViewById(R.id.puskesmas).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), ListDataActivity.class);
                        i.putExtra("TITLE", "puskesmas");
                        startActivity(i);
                    }
                });
                exitDialogView.findViewById(R.id.rumahsakit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), ListDataActivity.class);
                        i.putExtra("TITLE", "rumahsakit");
                        startActivity(i);
                    }
                });
                exitDialogView.findViewById(R.id.klinik).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), ListDataActivity.class);
                        i.putExtra("TITLE", "klinik");
                        startActivity(i);
                    }
                });
                exitDialog.show();
            }
        });

        hotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ListDataActivity.class);
                i.putExtra("TITLE", "hotel");
                startActivity(i);
            }
        });

        hotelmelatis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ListDataActivity.class);
                i.putExtra("TITLE", "hotelmelati");
                startActivity(i);
            }
        });
    }
}
