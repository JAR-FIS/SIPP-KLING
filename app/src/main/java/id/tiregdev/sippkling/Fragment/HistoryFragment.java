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

import id.tiregdev.sippkling.Adapter.HistoryAdapter;
import id.tiregdev.sippkling.Model.HistoryModel;
import id.tiregdev.sippkling.R;

/**
 * Created by Muhammad63 on 2/5/2018.
 */

public class HistoryFragment extends Fragment {

    RecyclerView rView;
    LinearLayoutManager lLayout;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_history, container, false);
        setupAdapter();
        return v;
    }

    public void setupAdapter(){
        List<HistoryModel> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(getContext());

        rView = v.findViewById(R.id.rview);
        rView.setLayoutManager(lLayout);

        HistoryAdapter rcAdapter = new HistoryAdapter(getContext(), rowListItem);
        rView.setAdapter(rcAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), lLayout.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.line_shape));
        rView.addItemDecoration(dividerItemDecoration);
    }

    private List<HistoryModel> getAllItemList(){
        List<HistoryModel> allItems = new ArrayList<>();
        allItems.add(new HistoryModel("Menambahkan ", "Rumah Sehat","702D", "03/02/2018"));
        allItems.add(new HistoryModel("Mengupdate ", "Rumah Makan","312D", "02/02/2018"));
        allItems.add(new HistoryModel("Menambahkan ", "Tempat Ibadah","421E", "02/02/2018"));
        allItems.add(new HistoryModel("Menambahkan ", "Rumah Sehat","211F", "01/02/2018"));
        allItems.add(new HistoryModel("Mengupdate ", "Hotel","734A", "28/01/2018"));

        return allItems;
    }
}
