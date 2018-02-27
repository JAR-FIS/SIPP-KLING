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

import id.tiregdev.sippkling.Activity.hotel;
import id.tiregdev.sippkling.Activity.hotelmelati;
import id.tiregdev.sippkling.Activity.klinik;
import id.tiregdev.sippkling.Activity.kolamrenang;
import id.tiregdev.sippkling.Activity.pasar;
import id.tiregdev.sippkling.Activity.pesantren;
import id.tiregdev.sippkling.Activity.puskesmas;
import id.tiregdev.sippkling.Activity.rumahsakit;
import id.tiregdev.sippkling.Activity.sekolah;
import id.tiregdev.sippkling.Activity.tempatibadah;
import id.tiregdev.sippkling.R;

/**
 * Created by Muhammad63 on 2/5/2018.
 */

public class ttu extends Fragment {

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
                Intent i = new Intent(getActivity(), tempatibadah.class);
                startActivity(i);
            }
        });

        pasars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), pasar.class);
                startActivity(i);
            }
        });

        sekolahs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), sekolah.class);
                startActivity(i);
            }
        });

        pesantrens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), pesantren.class);
                startActivity(i);
            }
        });

        kolamrenangs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), kolamrenang.class);
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
                        Intent i = new Intent(getActivity(), puskesmas.class);
                        startActivity(i);
                    }
                });
                exitDialogView.findViewById(R.id.rumahsakit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), rumahsakit.class);
                        startActivity(i);
                    }
                });
                exitDialogView.findViewById(R.id.klinik).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), klinik.class);
                        startActivity(i);
                    }
                });
                exitDialog.show();
            }
        });

        hotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), hotel.class);
                startActivity(i);
            }
        });

        hotelmelatis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), hotelmelati.class);
                startActivity(i);
            }
        });
    }
}
