package id.tiregdev.sippkling.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.tiregdev.sippkling.Model.object_dataObyek;
import id.tiregdev.sippkling.R;

/**
 * Created by Muhammad63 on 2/7/2018.
 */

public class adapter_pkl extends RecyclerView.Adapter<adapter_pkl.holder_pkl> {

    private List<object_dataObyek> itemList;
    private Context context;

    public adapter_pkl(Context context, List<object_dataObyek> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public holder_pkl onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pkl,null);
        holder_pkl hn = new holder_pkl(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(holder_pkl holder, int position){
        holder.nama.setText(itemList.get(position).getNama());
        holder.kasus.setText(itemList.get(position).getIdentitas());
        holder.alamat.setText(itemList.get(position).getAlamat());
        holder.lastUpdate.setText(itemList.get(position).getLastUpdate());
        holder.status.setText(itemList.get(position).getStatus());
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_pkl extends RecyclerView.ViewHolder {
        public TextView nama, kasus, alamat, lastUpdate, status;

        public holder_pkl(View itemView){
            super(itemView);

            nama = itemView.findViewById(R.id.nama);
            kasus = itemView.findViewById(R.id.kasus);
            alamat = itemView.findViewById(R.id.alamat);
            lastUpdate = itemView.findViewById(R.id.lastUpdate);
            status = itemView.findViewById(R.id.status);
        }
    }

}
