package id.tiregdev.sippkling.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.tiregdev.sippkling.Model.HistoryModel;
import id.tiregdev.sippkling.R;

/**
 * Created by Muhammad63 on 2/7/2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.holder_history> {

    private List<HistoryModel> itemList;
    private Context context;

    public HistoryAdapter(Context context, List<HistoryModel> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public holder_history onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_history,null);
        holder_history hn = new holder_history(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(holder_history holder, int position){
        holder.aktifitas.setText(itemList.get(position).getAktivitas());
        holder.obyek.setText(itemList.get(position).getObyek());
        holder.idSubmit.setText(itemList.get(position).getIdSubmit());
        holder.time.setText(itemList.get(position).getTime());
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_history extends RecyclerView.ViewHolder {
        public TextView aktifitas, obyek, idSubmit, time;

        public holder_history(View itemView){
            super(itemView);

            aktifitas = itemView.findViewById(R.id.aktivitas);
            obyek = itemView.findViewById(R.id.obyek);
            time = itemView.findViewById(R.id.time);
            idSubmit = itemView.findViewById(R.id.idSubmit);
        }
    }

}
