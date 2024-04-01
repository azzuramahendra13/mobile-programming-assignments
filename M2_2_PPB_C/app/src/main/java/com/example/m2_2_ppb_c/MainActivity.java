package com.example.m2_2_ppb_c;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText edtInput;
    private Button btnHitung, btnKeluar;
    private TextView tvHasilAngka;
    private SQLiteOpenHelper Opendb;
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

        edtInput = (EditText) findViewById(R.id.edt_input);
        btnHitung = (Button) findViewById(R.id.btn_hitung);
        btnKeluar = (Button) findViewById(R.id.btn_keluar);
        tvHasilAngka = (TextView) findViewById(R.id.tv_hasil_angka);

        Opendb = new SQLiteOpenHelper(this, "db.sql", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {}

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
        };

        db = Opendb.getWritableDatabase();

        View.OnClickListener button_operation = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_hitung) {
                    String inp = edtInput.getText().toString().trim();
                    String res = "-";
                    boolean isValid = false;

                    if (TextUtils.isEmpty(inp)) {
                        isValid = true;
                        edtInput.setError("Harap diisi");
                    } else {
                        try {
                            Cursor cur = db.rawQuery("SELECT " + inp, null);

                            if (cur.getCount() > 0) {
                                cur.moveToFirst();
                                res = cur.getString(0);
                            }

                        } catch (Exception e) {
                            isValid = true;
                            edtInput.setError("Ekspresi tidak valid");
                        }

                    }

                    if (!isValid) {
                        tvHasilAngka.setText(res);
                    }

                } else if (v.getId() == R.id.btn_keluar) {
                    Toast.makeText(MainActivity.this, "Keluar aplikasi", Toast.LENGTH_SHORT).show();
                    finish();
                    System.exit(0);
                }
            }
        };

        btnHitung.setOnClickListener(button_operation);
        btnKeluar.setOnClickListener(button_operation);

    }

    @Override
    protected void onStop() {
        db.close();
        Opendb.close();
        super.onStop();

    }
}