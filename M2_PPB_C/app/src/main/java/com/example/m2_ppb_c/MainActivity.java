package com.example.m2_ppb_c;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtPanjang, edtLebar;
    private TextView tvLuas;
    private Button btnHitung;

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

        edtPanjang = (EditText) findViewById(R.id.edt_panjang);
        edtLebar = (EditText) findViewById(R.id.edt_lebar);
        tvLuas = (TextView) findViewById(R.id.tv_luas);
        btnHitung = (Button) findViewById(R.id.btn_hitung);

        btnHitung.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_hitung) {
            String panjang = edtPanjang.getText().toString().trim();
            String lebar = edtLebar.getText().toString().trim();

            boolean isInputEmpty = false;

            if (TextUtils.isEmpty(panjang)) {
                isInputEmpty = true;
                edtPanjang.setError("Harap isi nilai panjang");
            }

            if (TextUtils.isEmpty(lebar)) {
                isInputEmpty = true;
                edtLebar.setError("Harap isi nilai lebar");
            }

            if (!isInputEmpty) {
                Double luas = Double.parseDouble(panjang) * Double.parseDouble(lebar);

                tvLuas.setText(String.valueOf(luas));
            }
        }
    }
}