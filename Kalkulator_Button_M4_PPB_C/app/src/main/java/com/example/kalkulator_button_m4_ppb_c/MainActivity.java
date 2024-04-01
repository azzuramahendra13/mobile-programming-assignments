package com.example.kalkulator_button_m4_ppb_c;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtOperan1, edtOperan2;
    private TextView tvOperator, tvHasil;
    private Button btnTambah, btnKurang, btnKali, btnBagi;

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

        edtOperan1 = (EditText) findViewById(R.id.edt_bil1);
        edtOperan2 = (EditText) findViewById(R.id.edt_bil2);
        tvOperator = (TextView) findViewById(R.id.tv_operator);
        tvHasil = (TextView) findViewById(R.id.tv_hasil);
        btnTambah = (Button) findViewById(R.id.btn_tambah);
        btnKurang = (Button) findViewById(R.id.btn_kurang);
        btnKali = (Button) findViewById(R.id.btn_kali);
        btnBagi = (Button) findViewById(R.id.btn_bagi);

        btnTambah.setOnClickListener(this);
        btnKurang.setOnClickListener(this);
        btnKali.setOnClickListener(this);
        btnBagi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        float bil1, bil2, hasil;
        boolean isValid = true;

        if (TextUtils.isEmpty(edtOperan1.getText().toString())) {
            edtOperan1.setError("Harap isi operan 1");
            isValid = false;
        } else if (TextUtils.isEmpty(edtOperan2.getText().toString())) {
            edtOperan2.setError("Harap isi operan 2");
            isValid = false;
        }

        if (isValid) {
            bil1 = Float.parseFloat(edtOperan1.getText().toString());
            bil2 = Float.parseFloat(edtOperan2.getText().toString());

            if (v.getId() == R.id.btn_tambah) {
                hasil = bil1 + bil2;
                tvOperator.setText("+");
            } else if (v.getId() == R.id.btn_kurang) {
                hasil = bil1 - bil2;
                tvOperator.setText("-");
            } else if (v.getId() == R.id.btn_kali) {
                hasil = bil1 * bil2;
                tvOperator.setText("x");
            } else {
                hasil = bil1 / bil2;
                tvOperator.setText("/");
            }

            tvHasil.setText(String.valueOf(hasil));
        }
    }
}