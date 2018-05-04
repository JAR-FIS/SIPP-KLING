package id.tiregdev.sippkling.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.tiregdev.sippkling.Model.RewardModel;
import id.tiregdev.sippkling.R;

/**
 * Created by Muhammad63 on 2/7/2018.
 */

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.holder_reward> {

    private List<RewardModel> itemList;
    private Context context;

    public RewardAdapter(Context context, List<RewardModel> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public holder_reward onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_reward,null);
        holder_reward hn = new holder_reward(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(holder_reward holder, int position){
        holder.nmrPeringkat.setText(itemList.get(position).getNmrPeringkat());
        holder.nama.setText(itemList.get(position).getNama());
        holder.kec.setText(itemList.get(position).getKec());
        holder.kel.setText(itemList.get(position).getKel());
        holder.jumlahData.setText(itemList.get(position).getJumlahData());
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_reward extends RecyclerView.ViewHolder {
        public TextView nmrPeringkat, nama, kec, kel, jumlahData;

        public holder_reward(View itemView){
            super(itemView);

            nmrPeringkat = itemView.findViewById(R.id.nmrPeringkat);
            nama = itemView.findViewById(R.id.nama);
            kec = itemView.findViewById(R.id.kecamatan);
            kel = itemView.findViewById(R.id.kelurahan);
            jumlahData = itemView.findViewById(R.id.jumlahData);
        }
    }

}
