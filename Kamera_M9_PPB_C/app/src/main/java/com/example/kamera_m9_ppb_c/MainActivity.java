package com.example.kamera_m9_ppb_c;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Uri imageUri;
    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        ImageView iv_hasil = (ImageView) findViewById(R.id.imageView);
                        iv_hasil.setImageURI(imageUri);

                        Bitmap imageBitmap = null;
                        try {
                            imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                        byte[] byteArray = stream.toByteArray();

                        File externalDestination = new File(
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                                "test.png");

                        FileOutputStream fo = null;

                        try {
                            fo = new FileOutputStream(externalDestination);
                            fo.write(byteArray);
                            fo.flush();
                            fo.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(MainActivity.this, "Gambar berhasil diambil", Toast.LENGTH_SHORT).show();
                    }
                }
            });

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

        Button btn_ambil_gambar = (Button) findViewById(R.id.button);

        btn_ambil_gambar.setOnClickListener(v -> {
            Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File saveDestination = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM);
            File imageFileName = new File(
                    saveDestination,
                    (DateFormat.format("dd-MM-yyyy HH-mm-ss", new Date().getTime())).toString() + ".jpg");
            imageUri = FileProvider.getUriForFile(
                    MainActivity.this,
                    "com.example.kamera_m9_ppb_c.provider",
                    imageFileName);

            it.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            resultLauncher.launch(it);
        });
    }
}