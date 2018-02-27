package id.tiregdev.sippkling.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.tiregdev.sippkling.Model.object_jadwal;
import id.tiregdev.sippkling.R;

/**
 * Created by Muhammad63 on 2/7/2018.
 */

public class adapter_jadwal extends RecyclerView.Adapter<adapter_jadwal.holder_jadwal> {

    private List<object_jadwal> itemList;
    private Context context;

    public adapter_jadwal(Context context, List<object_jadwal> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public holder_jadwal onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jadwal,null);
        holder_jadwal hn = new holder_jadwal(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(holder_jadwal holder, int position){
        holder.titleJadwal.setText(itemList.get(position).getTitleJadwal());
        holder.isiJadwal.setText(itemList.get(position).getIsiJadwal());
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_jadwal extends RecyclerView.ViewHolder {
        public TextView titleJadwal, isiJadwal;

        public holder_jadwal(View itemView){
            super(itemView);

            titleJadwal = itemView.findViewById(R.id.titleJadwal);
            isiJadwal = itemView.findViewById(R.id.isiJadwal);
        }
    }

}
