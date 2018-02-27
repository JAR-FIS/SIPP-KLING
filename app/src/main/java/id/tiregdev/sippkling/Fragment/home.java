package id.tiregdev.sippkling.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.zip.Inflater;

import id.tiregdev.sippkling.Activity.login;
import id.tiregdev.sippkling.R;

/**
 * Created by Muhammad63 on 2/5/2018.
 */

public class home extends Fragment {

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        popup();
        return v;
    }

    public void popup(){
        Button bt = v.findViewById(R.id.popupBtn);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LayoutInflater factory = LayoutInflater.from(getActivity());
                final View exitDialogView = factory.inflate(R.layout.popup_home, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(getActivity()).create();
                exitDialog.setView(exitDialogView);
                exitDialogView.findViewById(R.id.btClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                    }
                });
                exitDialogView.findViewById(R.id.rumahSehat).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        rumah_sehat llf = new rumah_sehat();
                        ft.replace(R.id.content_frame, llf);
                        ft.commit();
                        exitDialog.dismiss();
                    }
                });
                exitDialogView.findViewById(R.id.ttu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ttu llf = new ttu();
                        ft.replace(R.id.content_frame, llf);
                        ft.commit();
                        exitDialog.dismiss();
                    }
                });
                exitDialogView.findViewById(R.id.pkl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        pkl llf = new pkl();
                        ft.replace(R.id.content_frame, llf);
                        ft.commit();
                        exitDialog.dismiss();
                    }
                });
                exitDialogView.findViewById(R.id.tpm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        tpm llf = new tpm();
                        ft.replace(R.id.content_frame, llf);
                        ft.commit();
                        exitDialog.dismiss();
                    }
                });
                exitDialog.show();
            }
        });
    }
}
