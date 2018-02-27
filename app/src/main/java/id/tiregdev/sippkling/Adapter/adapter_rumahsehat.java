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

public class adapter_rumahsehat extends RecyclerView.Adapter<adapter_rumahsehat.holder_rumahsehat> {

    private List<object_dataObyek> itemList;
    private Context context;

    public adapter_rumahsehat(Context context, List<object_dataObyek> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public holder_rumahsehat onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rumahsehat,null);
        holder_rumahsehat hn = new holder_rumahsehat(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(holder_rumahsehat holder, int position){
        holder.nama.setText(itemList.get(position).getNama());
        holder.nomorObyek.setText(itemList.get(position).getIdentitas());
        holder.alamat.setText(itemList.get(position).getAlamat());
        holder.lastUpdate.setText(itemList.get(position).getLastUpdate());
        holder.status.setText(itemList.get(position).getStatus());
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_rumahsehat extends RecyclerView.ViewHolder {
        public TextView nama, nomorObyek, alamat, lastUpdate, status;

        public holder_rumahsehat(View itemView){
            super(itemView);

            nama = itemView.findViewById(R.id.nama);
            nomorObyek = itemView.findViewById(R.id.nmrKK);
            alamat = itemView.findViewById(R.id.alamat);
            lastUpdate = itemView.findViewById(R.id.lastUpdate);
            status = itemView.findViewById(R.id.status);
        }
    }

}
