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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.tiregdev.sippkling.Activity.form_rumahsehat;
import id.tiregdev.sippkling.Adapter.adapter_pkl;
import id.tiregdev.sippkling.Model.object_dataObyek;
import id.tiregdev.sippkling.R;
import id.tiregdev.sippkling.utils.AppConfig;
import id.tiregdev.sippkling.utils.AppController;

/**
 * Created by Muhammad63 on 2/5/2018.
 */

public class pkl extends Fragment {

    View v;
    RecyclerView rView;
    LinearLayoutManager lLayout;
    object_dataObyek dataObjek;
    adapter_pkl rcAdapter;
    JSONObject jsonObject;

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
                        Intent i = new Intent(getActivity(), form_rumahsehat.class);
                        startActivity(i);
                    }
                });
                exitDialog.show();
            }
        });
        setupAdapter();
        displayData();
        return v;
    }

    public void setupAdapter(){
        lLayout = new LinearLayoutManager(getContext());
        rView = v.findViewById(R.id.rview);
        rView.setLayoutManager(lLayout);
        rView.setAdapter(rcAdapter);
        rView.setNestedScrollingEnabled(false);
    }

    private void displayData(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.DISPLAY_DATA + "?kategori=pkl", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<object_dataObyek> allItems = new ArrayList<>();
                for (int i = 0; i < response.length(); i++){
                    dataObjek = new object_dataObyek();
                    jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        dataObjek.setNama(jsonObject.getString("nama"));
                        allItems.add(new object_dataObyek(
                                jsonObject.getString("nama"),
                                jsonObject.getString("kasus"),
                                jsonObject.getString("alamat"),
                                jsonObject.getString("waktu"),
                                "Sehat"));


                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                rcAdapter = new adapter_pkl(getContext(),allItems);
                rView.setAdapter(rcAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }
}
