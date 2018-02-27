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

public class adapter_ttu_1 extends RecyclerView.Adapter<adapter_ttu_1.holder_jasaboga> {

    private List<object_dataObyek> itemList;
    private Context context;

    public adapter_ttu_1(Context context, List<object_dataObyek> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public holder_jasaboga onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ttu_satu,null);
        holder_jasaboga hn = new holder_jasaboga(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(holder_jasaboga holder, int position){
        holder.nama.setText(itemList.get(position).getNama());
        holder.identitas.setText(itemList.get(position).getIdentitas());
        holder.alamat.setText(itemList.get(position).getAlamat());
        holder.lastUpdate.setText(itemList.get(position).getLastUpdate());
        holder.status.setText(itemList.get(position).getStatus());
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_jasaboga extends RecyclerView.ViewHolder {
        public TextView nama, identitas, alamat, lastUpdate, status;

        public holder_jasaboga(View itemView){
            super(itemView);

            nama = itemView.findViewById(R.id.nama);
            identitas = itemView.findViewById(R.id.namaPemilik);
            alamat = itemView.findViewById(R.id.alamat);
            lastUpdate = itemView.findViewById(R.id.lastUpdate);
            status = itemView.findViewById(R.id.status);
        }
    }

}
