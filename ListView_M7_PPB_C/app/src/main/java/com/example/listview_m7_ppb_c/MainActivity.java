package com.example.listview_m7_ppb_c;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    KontakDbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new KontakDbHelper(this);
        db = dbHelper.getWritableDatabase();

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

        ArrayList<Kontak> dataset = new ArrayList<Kontak>();
        while (cursor.moveToNext()) {
            String nama = cursor.getString(
                    cursor.getColumnIndexOrThrow(KontakContract.KontakList.COLUMN_NAMA));
            String no_hp = cursor.getString(
                    cursor.getColumnIndexOrThrow(KontakContract.KontakList.COLUMN_NO_HP));

            dataset.add(new Kontak(nama, no_hp));
        }

        cursor.close();

        KontakListAdapter adapter = new KontakListAdapter(dataset);
        adapter.setOnItemClickListener(data -> new KontakItemDialogFragment(db, adapter, dataset, data)
                .show(getSupportFragmentManager(), "ITEM_DETAIL"));

        RecyclerView recyclerView = findViewById(R.id.list_kontak);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ImageButton btn_tambah = (ImageButton) findViewById(R.id.btn_tambah);
        btn_tambah.setOnClickListener(v -> {
            new InputKontakDialogFragment("Tambah", db, adapter, dataset, null)
                .show(getSupportFragmentManager(), "TAMBAH_KONTAK");
        });

        ImageButton btn_cari = (ImageButton) findViewById(R.id.btn_cari);
        btn_cari.setOnClickListener(v -> new InputKontakDialogFragment("Cari", db, adapter, dataset, null)
                .show(getSupportFragmentManager(), "CARI_KONTAK"));
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}