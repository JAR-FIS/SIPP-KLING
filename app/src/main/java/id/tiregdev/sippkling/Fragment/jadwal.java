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

import id.tiregdev.sippkling.Adapter.adapter_jadwal;
import id.tiregdev.sippkling.Model.object_jadwal;
import id.tiregdev.sippkling.R;

/**
 * Created by Muhammad63 on 2/5/2018.
 */

public class jadwal extends Fragment {

    View v;
    RecyclerView rView;
    LinearLayoutManager lLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_jadwal, container, false);
        setupAdapter();
        return v;
    }

    public void setupAdapter(){
        List<object_jadwal> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(getContext());

        rView = v.findViewById(R.id.rview);
        rView.setLayoutManager(lLayout);

        adapter_jadwal rcAdapter = new adapter_jadwal(getContext(), rowListItem);
        rView.setAdapter(rcAdapter);
        rView.setNestedScrollingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), lLayout.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.line_shape));
        rView.addItemDecoration(dividerItemDecoration);
    }

    private List<object_jadwal> getAllItemList(){
        List<object_jadwal> allItems = new ArrayList<>();
        allItems.add(new object_jadwal("Januari 2018", getResources().getString(R.string.isiJadwal)));
        allItems.add(new object_jadwal("Juni 2018", getResources().getString(R.string.isiJadwal2)));

        return allItems;
    }
}
