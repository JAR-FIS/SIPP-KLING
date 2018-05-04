package id.tiregdev.sippkling.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import id.tiregdev.sippkling.Model.DataModel;
import id.tiregdev.sippkling.R;

/**
 * Created by Muhammad63 on 2/7/2018.
 */

public class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.holder_rumahsehat> implements Filterable {

    private List<DataModel> itemList;
    private List<DataModel> mFilteredList;
    private Context context;

    public ListDataAdapter(Context context, List<DataModel> itemList){
        this.itemList = itemList;
        this.mFilteredList = new ArrayList<>();
        this.mFilteredList.addAll(itemList);
        this.context = context;
    }

    @Override
    public holder_rumahsehat onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data,null);
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

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        itemList.clear();
        if (charText.length() == 0) {
            itemList.addAll(mFilteredList);
        } else {
            for (DataModel dataObyek : mFilteredList) {
                if (dataObyek.getIdentitas().toLowerCase().contains(charText) || dataObyek.getNama().toLowerCase().contains(charText) || dataObyek.getAlamat().toLowerCase().contains(charText)) {
                    itemList.add(dataObyek);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = itemList;
                } else {

                    ArrayList<DataModel> filteredList = new ArrayList<>();

                    for (DataModel dataObyek : itemList) {

                        if (dataObyek.getIdentitas().toLowerCase().contains(charString) || dataObyek.getNama().toLowerCase().contains(charString) || dataObyek.getAlamat().toLowerCase().contains(charString)) {

                            filteredList.add(dataObyek);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<DataModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
