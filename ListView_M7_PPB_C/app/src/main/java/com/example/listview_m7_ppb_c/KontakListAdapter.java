package com.example.listview_m7_ppb_c;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KontakListAdapter extends RecyclerView.Adapter<KontakListAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClicked(Kontak data);
    }

    private ArrayList<Kontak> dataSet;
    private OnItemClickListener onItemClickListener;
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nama;
        private final TextView noHP;

        public ViewHolder(View view, OnItemClickListener onItemClickListener) {
            super(view);
            nama = (TextView) view.findViewById(R.id.tv_nama);
            noHP = (TextView) view.findViewById(R.id.tv_no_hp);

            itemView.setOnClickListener(v -> onItemClickListener.onItemClicked(dataSet.get(getAdapterPosition())));
        }

        public TextView getNama() {
            return nama;
        }

        public TextView getNoHP() {
            return noHP;
        }
    }

    public KontakListAdapter(ArrayList<Kontak> dataSet) { this.dataSet = dataSet; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kontak_row_item, parent, false);
        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getNama().setText(dataSet.get(position).getNama());
        holder.getNoHP().setText(dataSet.get(position).getNoHP());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
