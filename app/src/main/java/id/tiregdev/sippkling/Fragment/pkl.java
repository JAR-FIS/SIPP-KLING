package id.tiregdev.sippkling.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.tiregdev.sippkling.Activity.form_pkl;
import id.tiregdev.sippkling.Adapter.adapter_pkl;
import id.tiregdev.sippkling.Model.object_dataObyek;
import id.tiregdev.sippkling.R;

/**
 * Created by Muhammad63 on 2/5/2018.
 */

public class pkl extends Fragment {

    View v;
    RecyclerView rView;
    LinearLayoutManager lLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pkl, container, false);
        v.findViewById(R.id.mainLayout).requestFocus();
        FloatingActionButton add = v.findViewById(R.id.tambah);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LayoutInflater factory = LayoutInflater.from(getActivity());
                final View exitDialogView = factory.inflate(R.layout.popup_tambahdata, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(getActivity()).create();
                exitDialog.setView(exitDialogView);
                exitDialogView.findViewById(R.id.tidak).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                    }
                });
                exitDialogView.findViewById(R.id.ya).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                        Intent i = new Intent(getActivity(), form_pkl.class);
                        startActivity(i);
                    }
                });
                exitDialog.show();
            }
        });
        setupAdapter();
        return v;
    }

    public void setupAdapter(){
        List<object_dataObyek> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(getContext());

        rView = v.findViewById(R.id.rview);
        rView.setLayoutManager(lLayout);

        adapter_pkl rcAdapter = new adapter_pkl(getContext(), rowListItem);
        rView.setAdapter(rcAdapter);
        rView.setNestedScrollingEnabled(false);
    }

    private List<object_dataObyek> getAllItemList(){
        List<object_dataObyek> allItems = new ArrayList<>();
        allItems.add(new object_dataObyek("Ahmad Abdul", "Demam",getResources().getString(R.string.almt1),"06/11/2017","Sehat"));
        allItems.add(new object_dataObyek("Abu Abdul", "Saluran jamban di luar gedung",getResources().getString(R.string.almt2),"10/10/2017","Sehat"));
        allItems.add(new object_dataObyek("Muhammad Abdul", "ISPA",getResources().getString(R.string.almt3),"12/10/2017","Sehat"));
        allItems.add(new object_dataObyek("Al Abdul", "Diare",getResources().getString(R.string.almt1),"12/10/2017","Sehat"));
        allItems.add(new object_dataObyek("Si Abdul", "Kulit",getResources().getString(R.string.almt2),"12/10/2017","Sehat"));
        allItems.add(new object_dataObyek("Dul Abdul", "TB",getResources().getString(R.string.almt3),"13/10/2017","Sehat"));
        allItems.add(new object_dataObyek("Koh Abdul", "Diare",getResources().getString(R.string.almt1),"15/10/2017","Sehat"));
        allItems.add(new object_dataObyek("Bebdul", "TB",getResources().getString(R.string.almt2),"16/10/2017","Sehat"));
        allItems.add(new object_dataObyek("Abdul Khoir", "ISPA",getResources().getString(R.string.almt3),"16/10/2017","Sehat"));

        return allItems;
    }
}
