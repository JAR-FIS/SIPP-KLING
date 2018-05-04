package id.tiregdev.sippkling.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import id.tiregdev.sippkling.Adapter.RewardAdapter;
import id.tiregdev.sippkling.Model.RewardModel;
import id.tiregdev.sippkling.R;
import id.tiregdev.sippkling.utils.AppConfig;
import id.tiregdev.sippkling.utils.AppController;
import id.tiregdev.sippkling.utils.SQLiteHandler;

/**
 * Created by Muhammad63 on 2/5/2018.
 */

public class RewardFragment extends Fragment {

    private View v;
    private RecyclerView rView;
    private LinearLayoutManager lLayout;
    private RewardModel dataObjek;
    private RewardAdapter rcAdapter;
    private JSONObject jsonObject;
    private ProgressDialog pDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_reward, container, false);
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
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Memuat Data");
        pDialog.setCancelable(false);

    }

    private void displayData(){
        SQLiteHandler db;
        db = new SQLiteHandler(getContext());
        showDialog();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.DISPLAY_DATA, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hideDialog();
                List<RewardModel> allItems = new ArrayList<>();
                for (int i = 0; i < response.length(); i++){
                    dataObjek = new RewardModel();
                    jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        allItems.add(new RewardModel(
                                String.valueOf(i + 1),
                                jsonObject.getString("nama"),
                                jsonObject.getString("kecamatan"),
                                jsonObject.getString("kelurahan"),
                                jsonObject.getString("total_input")));


                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                rcAdapter = new RewardAdapter(getContext(),allItems);
                rView.setAdapter(rcAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);



    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
