package com.example.database_m5_ppb_c;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNama, edtNRP;
    private Button btnSimpan, btnAmbil, btnUpdate, btnHapus;
    private SQLiteOpenHelper OpenDB;
    private SQLiteDatabase db;

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

        edtNama = (EditText) findViewById(R.id.edt_input_nama);
        edtNRP = (EditText) findViewById(R.id.edt_input_nrp);
        btnSimpan = (Button) findViewById(R.id.btn_simpan);
        btnAmbil = (Button) findViewById(R.id.btn_ambil);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnHapus = (Button) findViewById(R.id.btn_hapus);

        btnSimpan.setOnClickListener(this);
        btnAmbil.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnHapus.setOnClickListener(this);

        OpenDB = new SQLiteOpenHelper(this, "db.sql", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {}

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
        };

        db = OpenDB.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS Mahasiswa(nrp TEXT, nama TEXT); ");
    }

    @Override
    protected void onStop() {
        db.close();
        OpenDB.close();
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_simpan)
            DBSimpan();
        else if (v.getId() == R.id.btn_ambil)
            DBAmbil();
        else if (v.getId() == R.id.btn_update)
            DBUpdate();
        else
            DBHapus();
    }

    private void DBSimpan() {
        ContentValues data = new ContentValues();

        data.put("nrp", edtNRP.getText().toString());
        data.put("nama", edtNama.getText().toString());

        db.insert("Mahasiswa", null, data);
        Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show();
    }

    private void DBAmbil() {
        Cursor cur = db.rawQuery("SELECT * FROM Mahasiswa WHERE nrp='" +
                edtNRP.getText().toString() + "'", null);

        if (cur.getCount() > 0) {
            Toast.makeText(this, "Ditemukan "
                    + cur.getCount() + " data.", Toast.LENGTH_SHORT).show();

            cur.moveToFirst();
            edtNama.setText(cur.getString(1));
        } else {
            Toast.makeText(this, "Data tidak ditemukan.", Toast.LENGTH_SHORT).show();
        }
    }

    private void DBUpdate() {
        ContentValues data = new ContentValues();

        data.put("nrp", edtNRP.getText().toString());
        data.put("nama", edtNama.getText().toString());

        db.update("Mahasiswa", data, "nrp='" + edtNRP.getText().toString() + "'", null);
        Toast.makeText(this, "Data berhasil di-update!", Toast.LENGTH_SHORT).show();
    }

    private void DBHapus() {
        db.delete("Mahasiswa", "nrp='" + edtNRP.getText().toString() + "'", null);
        Toast.makeText(this, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show();
    }
}