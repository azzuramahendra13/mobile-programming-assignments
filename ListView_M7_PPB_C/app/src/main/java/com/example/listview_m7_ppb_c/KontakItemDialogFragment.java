package com.example.listview_m7_ppb_c;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Collections;

public class KontakItemDialogFragment extends KontakActionDialogFragment {
    public KontakItemDialogFragment(SQLiteDatabase db,
                                    KontakListAdapter adapter, ArrayList<Kontak> dataset, Kontak itemData) {
        super(db, adapter, dataset, itemData);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View input_dialog = inflater.inflate(R.layout.kontak_item_dialog, null);

        TextView tv_action_nama = (TextView) input_dialog.findViewById(R.id.action_nama);
        TextView tv_action_no_hp = (TextView) input_dialog.findViewById(R.id.action_no_hp);
        ImageButton btn_edit = (ImageButton) input_dialog.findViewById(R.id.btn_edit);
        ImageButton btn_hapus = (ImageButton) input_dialog.findViewById(R.id.btn_hapus);
        ImageButton btn_telepon = (ImageButton) input_dialog.findViewById(R.id.btn_telepon);

        tv_action_nama.setText(itemData.getNama());
        tv_action_no_hp.setText(itemData.getNoHP());

        btn_edit.setOnClickListener(v -> {
            new InputKontakDialogFragment("Edit", db, adapter, dataset, itemData)
                    .show(requireActivity().getSupportFragmentManager(), "EDIT_KONTAK");
            dismiss();
        });

        btn_hapus.setOnClickListener(v -> {
            new InputKontakDialogFragment("Hapus", db, adapter, dataset, itemData)
                    .show(requireActivity().getSupportFragmentManager(), "EDIT_KONTAK");
            dismiss();
        });

        btn_telepon.setOnClickListener(v -> {
            Intent dialPhone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + itemData.getNoHP()));
            startActivity(dialPhone);
        });

        builder.setView(input_dialog)
                .setTitle("Detail kontak")
                .setNegativeButton(R.string.tutup, (dialog, which) -> dialog.cancel());

        return builder.create();
    }
}
