package id.tiregdev.sippkling.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import id.tiregdev.sippkling.Adapter.adapter_notif;
import id.tiregdev.sippkling.Model.object_notif;
import id.tiregdev.sippkling.R;

/**
 * Created by Muhammad63 on 2/5/2018.
 */

public class notif extends Fragment {

    View v;
    RecyclerView rView;
    LinearLayoutManager lLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_notif, container, false);
        setupAdapter();
        return v;
    }

    public void setupAdapter(){
        List<object_notif> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(getContext());

        rView = v.findViewById(R.id.rview);
        rView.setLayoutManager(lLayout);

        adapter_notif rcAdapter = new adapter_notif(getContext(), rowListItem);
        rView.setAdapter(rcAdapter);
    }

    private List<object_notif> getAllItemList(){
        List<object_notif> allItems = new ArrayList<>();
        allItems.add(new object_notif("Jadwal Pendataan", "Pengumuman","02/02/2018"));
        allItems.add(new object_notif("Info Reward", "Pengumuman","01/02/2018"));
        allItems.add(new object_notif("Info Pendataan", "Pengumuman","01/02/2018"));
        allItems.add(new object_notif("Teguran", "Pribadi","15/01/2018"));

        return allItems;
    }
}
