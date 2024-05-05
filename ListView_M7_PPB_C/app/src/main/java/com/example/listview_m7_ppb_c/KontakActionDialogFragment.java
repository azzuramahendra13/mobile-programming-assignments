package com.example.listview_m7_ppb_c;

import android.database.sqlite.SQLiteDatabase;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public abstract class KontakActionDialogFragment extends DialogFragment {
    public SQLiteDatabase db;
    public KontakListAdapter adapter;
    public ArrayList<Kontak> dataset;
    public Kontak itemData;

    public KontakActionDialogFragment(SQLiteDatabase db, KontakListAdapter adapter,
                                      ArrayList<Kontak> dataset, Kontak itemData) {
        super();
        this.db = db;
        this.adapter = adapter;
        this.dataset = dataset;
        this.itemData = itemData;
    }
}
