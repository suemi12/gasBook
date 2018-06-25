package app.ejemplos.com.gasstation.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import app.ejemplos.com.gasstation.R;
import app.ejemplos.com.gasstation.model.GasBook;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.GasBookViewHolder> {
    private final OnItemSelected listener;
    private List<GasBook> items;


    class GasBookViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView street;
        TextView rfc;
        TextView dateInsert;
        TextView regular;
        TextView colonia;
        TextView numPermit;
        TextView appDate;
        TextView idPermit;
        TextView premium;
        TextView bussinesName;
        TextView cp;
        TextView diesel;
        CardView containerItem;



        GasBookViewHolder(View v) {
            super(v);
            bussinesName = v.findViewById(R.id.bussines_name);
            colonia = v.findViewById(R.id.colonia);
            street = v.findViewById(R.id.street);
            cp = v.findViewById(R.id.cp);
            containerItem = v.findViewById(R.id.container_item_history);
        }
    }

    public HistoryAdapter(List<GasBook> items, OnItemSelected listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public GasBookViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.items, viewGroup, false);
        return new GasBookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GasBookViewHolder viewHolder, int i) {
        final GasBook item = items.get(i);
        viewHolder.bussinesName.setText(item.getBusinessName());
        viewHolder.colonia.setText(item.getColony());
        viewHolder.street.setText(item.getStreet());
        viewHolder.cp.setText(item.getCp());
        viewHolder.containerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemOnClick(item);
            }
        });
    }

    public interface OnItemSelected{
        void onItemOnClick(GasBook gasBook);
    }

}