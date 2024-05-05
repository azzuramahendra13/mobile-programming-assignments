package com.example.json_parser_m8_ppb_c;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {
    private ArrayList<Contact> dataSet;
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView name;
        private final TextView email;
        private final TextView mobile;

        public ViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.tv_id);
            name = (TextView) view.findViewById(R.id.tv_name);
            email = (TextView) view.findViewById(R.id.tv_email);
            mobile = (TextView) view.findViewById(R.id.tv_mobile);
        }

        public TextView getId() { return id; }
        public TextView getName() {
            return name;
        }

        public TextView getEmail() {
            return email;
        }
        public TextView getMobile() { return mobile; }
    }

    public ContactListAdapter(ArrayList<Contact> dataSet) { this.dataSet = dataSet; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getId().setText(dataSet.get(position).getId());
        holder.getName().setText(dataSet.get(position).getName());
        holder.getEmail().setText(dataSet.get(position).getEmail());
        holder.getMobile().setText(dataSet.get(position).getMobile());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
