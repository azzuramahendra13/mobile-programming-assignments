package com.example.listview_m7_ppb_c;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class InputKontakDialogFragment extends KontakActionDialogFragment {
    public String action;
    public InputKontakDialogFragment(String action, SQLiteDatabase db, KontakListAdapter adapter,
                                     ArrayList<Kontak> dataset, Kontak itemData) {
        super(db, adapter, dataset, itemData);
        this.action = action;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View input_dialog = inflater.inflate(R.layout.input_dialog, null);
        builder.setTitle(action + " kontak")
                .setNegativeButton(R.string.batal, (dialog, which) -> dialog.cancel());

        if (action.equals("Hapus")) {
            builder.setMessage(R.string.hapus_kontak_message)
                    .setPositiveButton("Hapus", (dialog, which) -> {
                        String selection = KontakContract.KontakList.COLUMN_NAMA + " = ? AND " +
                                KontakContract.KontakList.COLUMN_NO_HP + " = ?";
                        String[] selectionArgs = {itemData.getNama(), itemData.getNoHP()};

                        int deletedRows = db.delete(KontakContract.KontakList.TABLE_NAME, selection, selectionArgs);
                        Kontak kontak_values = new Kontak(itemData.getNama(), itemData.getNoHP());

                        dataset.remove(kontak_values);
                        Collections.sort(dataset);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Kontak dihapus", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    });
        } else if (action.equals("Edit")) {
            EditText input_nama = (EditText) input_dialog.findViewById(R.id.edt_input_nama);
            EditText input_no_hp = (EditText) input_dialog.findViewById(R.id.edt_input_no_hp);

            input_nama.setText(itemData.getNama());
            input_no_hp.setText(itemData.getNoHP());

            builder.setView(input_dialog).setPositiveButton("Edit", (dialog, which) -> {
                ContentValues values = new ContentValues();
                values.put(KontakContract.KontakList.COLUMN_NAMA,
                        input_nama.getText().toString());
                values.put(KontakContract.KontakList.COLUMN_NO_HP,
                        input_no_hp.getText().toString());

                String selection = KontakContract.KontakList.COLUMN_NAMA + " = ? AND " +
                        KontakContract.KontakList.COLUMN_NO_HP + " = ?";
                String[] selectionArgs = {itemData.getNama(), itemData.getNoHP()};

                int count = db.update(
                        KontakContract.KontakList.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);

                Kontak kontak_values = new Kontak(itemData.getNama(), itemData.getNoHP());
                Kontak new_kontak_values = new Kontak(input_nama.getText().toString(), input_no_hp.getText().toString());
                int old_item_idx = dataset.indexOf(kontak_values);
                dataset.set(old_item_idx, new_kontak_values);
                Collections.sort(dataset);
//                adapter.notifyItemChanged(dataset.indexOf(new_kontak_values));
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Kontak diedit", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
        } else {
            builder.setView(input_dialog).setPositiveButton(R.string.ok, (dialog, which) -> {
                EditText input_nama = (EditText) input_dialog.findViewById(R.id.edt_input_nama);
                EditText input_no_hp = (EditText) input_dialog.findViewById(R.id.edt_input_no_hp);

                if (action.equals("Tambah")) {
                    ContentValues values = new ContentValues();
                    values.put(KontakContract.KontakList.COLUMN_NAMA,
                            input_nama.getText().toString());
                    values.put(KontakContract.KontakList.COLUMN_NO_HP,
                            input_no_hp.getText().toString());

                    db.insert(KontakContract.KontakList.TABLE_NAME, null, values);

                    Kontak kontak_values = new Kontak(input_nama.getText().toString(), input_no_hp.getText().toString());
                    dataset.add(kontak_values);
                    Collections.sort(dataset);
//                    adapter.notifyItemInserted(dataset.indexOf(kontak_values));
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Kontak ditambahkan", Toast.LENGTH_SHORT).show();
                } else if (action.equals("Cari")) {
                    ArrayList<Kontak> search_list = new ArrayList<Kontak>();
                    String input_nama_text = input_nama.getText().toString();
                    String input_no_hp_text = input_no_hp.getText().toString();

                    if (input_nama_text.isEmpty() && input_no_hp_text.isEmpty()) {
                        String[] projection = {
                                BaseColumns._ID,
                                KontakContract.KontakList.COLUMN_NAMA,
                                KontakContract.KontakList.COLUMN_NO_HP
                        };

                        String sortOrder = KontakContract.KontakList.COLUMN_NAMA + " ASC";
                        Cursor cursor = db.query(KontakContract.KontakList.TABLE_NAME,
                                projection,
                                null,
                                null,
                                null,
                                null,
                                sortOrder);

                        while (cursor.moveToNext()) {
                            String nama = cursor.getString(
                                    cursor.getColumnIndexOrThrow(KontakContract.KontakList.COLUMN_NAMA));
                            String no_hp = cursor.getString(
                                    cursor.getColumnIndexOrThrow(KontakContract.KontakList.COLUMN_NO_HP));

                            search_list.add(new Kontak(nama, no_hp));
                        }
                        cursor.close();

                    } else {
                        for (Kontak kontak : dataset) {
                            if (kontak.getNama().equals(input_nama_text) ||
                                    kontak.getNoHP().equals(input_no_hp_text)) {
                                search_list.add(kontak);
                            }
                        }
                    }

                    dataset.clear();
                    dataset.addAll(search_list);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Ditemukan " + dataset.size() + " data", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            });
        }
        return builder.create();
    }
}
