package id.tiregdev.sippkling.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import id.tiregdev.sippkling.Adapter.adapter_reward;
import id.tiregdev.sippkling.Model.object_reward;
import id.tiregdev.sippkling.R;

/**
 * Created by Muhammad63 on 2/5/2018.
 */

public class reward extends Fragment {

    View v;
    RecyclerView rView;
    LinearLayoutManager lLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_reward, container, false);
        setupAdapter();
        return v;
    }

    public void setupAdapter(){
        List<object_reward> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(getContext());

        rView = v.findViewById(R.id.rview);
        rView.setLayoutManager(lLayout);

        adapter_reward rcAdapter = new adapter_reward(getContext(), rowListItem);
        rView.setAdapter(rcAdapter);
        rView.setNestedScrollingEnabled(false);
    }

    private List<object_reward> getAllItemList(){
        List<object_reward> allItems = new ArrayList<>();
        allItems.add(new object_reward("1", "Siti Badriah","Cilodong","Kalimulya","297"));
        allItems.add(new object_reward("2", "Nunung Ningsih","Limo","Grogol","277"));
        allItems.add(new object_reward("3", "Huswatun Hasanah","Cilodong","Sukmajaya","243"));
        allItems.add(new object_reward("4", "Nur Kamila","Beji","Beji Timur","237"));
        allItems.add(new object_reward("5", "Esri Prandayan","Bojongsari","Tapos","225"));
        allItems.add(new object_reward("6", "Siti Sima","Cilodong","Kalimulya","217"));
        allItems.add(new object_reward("7", "Khairunnisa","Sawangan","Sawangan","186"));
        allItems.add(new object_reward("8", "Annisa Pratiwi","Beji","Kemiri Muka","168"));
        allItems.add(new object_reward("9", "Khadijah","Cilodong","Kalimulya","159"));
        allItems.add(new object_reward("10", "Romlah Wati","Pancoran Mas","Depok","151"));

        return allItems;
    }
}
