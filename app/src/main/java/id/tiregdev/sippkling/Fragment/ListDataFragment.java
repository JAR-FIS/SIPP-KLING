package id.tiregdev.sippkling.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.tiregdev.sippkling.Activity.FormPKLActivity;
import id.tiregdev.sippkling.Activity.FormRumahsehatActivity;
import id.tiregdev.sippkling.Adapter.ListDataAdapter;
import id.tiregdev.sippkling.Model.DataModel;
import id.tiregdev.sippkling.R;
import id.tiregdev.sippkling.utils.AppConfig;
import id.tiregdev.sippkling.utils.AppController;
import id.tiregdev.sippkling.utils.ProgressDialogUtil;
import id.tiregdev.sippkling.utils.SQLiteHandler;

/**
 * Created by Muhammad63 on 2/5/2018.
 */

public class ListDataFragment extends Fragment {

    private View v;
    private RecyclerView rView;
    private LinearLayoutManager lLayout;
    private DataModel dataObjek;
    private ListDataAdapter rcAdapter;
    private JSONObject jsonObject;
    private ProgressDialog pDialog;
    private SearchView searchView;

    public static ListDataFragment newInstance(String title){
        ListDataFragment fragment = new ListDataFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);

        return fragment;
    }


    public String getTitle() {
        Bundle args = getArguments();
        return args.getString("title", "NO TITLE FOUND");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_list_data, container, false);
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
                        if(getTitle().equals("rumahsehat")){
                            Intent i = new Intent(getActivity(), FormRumahsehatActivity.class);
                            startActivity(i);
                        }else if(getTitle().equals("pkl")){
                            Intent i = new Intent(getActivity(), FormPKLActivity.class);
                            startActivity(i);
                        }
                    }
                });
                exitDialog.show();
            }
        });
        setupAdapter();
        displayData();

        searchView = v.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    rcAdapter.filter(query);
                } catch (NullPointerException e){
                    Toast.makeText(getActivity(), "Belum Ada Data!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return v;
    }

    public void setupAdapter(){
        lLayout = new LinearLayoutManager(getActivity());
        rView = v.findViewById(R.id.rview);
        rView.setLayoutManager(lLayout);
        rView.setAdapter(rcAdapter);
        rView.setNestedScrollingEnabled(false);

    }

    private void displayData(){
        SQLiteHandler db;
        db = new SQLiteHandler(getContext());
        final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(getActivity(), ProgressDialog.STYLE_SPINNER, false);
        progressDialogUtil.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.DISPLAY_DATA + "?kategori=" + getTitle() + "&id=" + db.getUserDetails().get("id_petugas") , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialogUtil.dismiss();
                List<DataModel> allItems = new ArrayList<>();
                for (int i = 0; i < response.length(); i++){
                    dataObjek = new DataModel();
                    jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        if(getTitle().equals("rumahsehat")){
                            allItems.add(new DataModel(
                                    jsonObject.getString("nama_kk"),
                                    jsonObject.getString("no_rumah"),
                                    jsonObject.getString("alamat"),
                                    jsonObject.getString("waktu"),
                                    jsonObject.getString("status")));
                        }
                        else if (getTitle().equals("pkl")){
                            allItems.add(new DataModel(
                                    jsonObject.getString("nama_pkl"),
                                    jsonObject.getString("kasus"),
                                    jsonObject.getString("alamat"),
                                    jsonObject.getString("waktu"),
                                    "-"));
                        }
                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                rcAdapter = new ListDataAdapter(getContext(),allItems);
                rView.setAdapter(rcAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialogUtil.dismiss();
                Toast.makeText(getActivity(), "Belum Ada Data!", Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);



    }

}
