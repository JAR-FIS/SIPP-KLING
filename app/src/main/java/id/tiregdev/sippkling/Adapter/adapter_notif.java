package id.tiregdev.sippkling.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.tiregdev.sippkling.Model.object_notif;
import id.tiregdev.sippkling.R;

/**
 * Created by Muhammad63 on 2/7/2018.
 */

public class adapter_notif extends RecyclerView.Adapter<adapter_notif.holder_notif> {

    private List<object_notif> itemList;
    private Context context;

    public adapter_notif(Context context, List<object_notif> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public holder_notif onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notif,null);
        holder_notif hn = new holder_notif(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(holder_notif holder, int position){
        holder.judul.setText(itemList.get(position).getJudul());
        holder.perihal.setText(itemList.get(position).getPerihal());
        holder.waktu.setText(itemList.get(position).getWaktu());
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_notif extends RecyclerView.ViewHolder {
        public TextView judul, perihal, waktu;

        public holder_notif(View itemView){
            super(itemView);

            judul = itemView.findViewById(R.id.titleNotif);
            perihal = itemView.findViewById(R.id.perihal);
            waktu = itemView.findViewById(R.id.time);
        }
    }

}
