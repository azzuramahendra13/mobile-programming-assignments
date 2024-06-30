package com.example.kamera_server_m10_ppb_c;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URI;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class KirimGambarDialogFragment extends DialogFragment {
    private final File imageFilePath;
    private final WeakReference<Activity> context;
    public final String API_URL = "http://192.168.1.102:5000";

    public static class Response {
        public final String status;

        public Response(String status) {
            this.status = status;
        }
    }
    public interface Server {
        @Multipart
        @POST("/upload")
        Call<Response> upload(@Part MultipartBody.Part image, @Part("sender") RequestBody sender);
    }

    public KirimGambarDialogFragment(File imageFilePath, Activity context) {
        super();
        this.imageFilePath = imageFilePath;
        this.context = new WeakReference<>(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.konfirmasi)
                .setMessage(R.string.kirim_gambar_ke_server)
                .setNegativeButton("Batal", ((dialog, which) -> dialog.cancel()))
                .setPositiveButton("Kirim", (dialog, which) -> {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(API_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Server server = retrofit.create(Server.class);

                    MultipartBody.Part image = MultipartBody.Part.createFormData(
                                            "image",
                                            imageFilePath.getName(),
                                            RequestBody.create(MultipartBody.FORM, imageFilePath));
                    RequestBody sender = RequestBody.create(MultipartBody.FORM, Build.MODEL);

                    Call<Response> call = server.upload(image, sender);

                    ProgressBar progressBar = new ProgressBar();
                    dialog.dismiss();
                    progressBar.show(requireActivity().getSupportFragmentManager(), "KIRIM_GAMBAR_PROGRESS");

                    call.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            if (response.isSuccessful())
                                Toast.makeText(context.get(), "Gambar berhasil terkirim", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(context.get(), "Gambar gagal dikirim", Toast.LENGTH_SHORT).show();
                            progressBar.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable throwable) {
                            Toast.makeText(context.get(), "Server tidak dapat dihubungi", Toast.LENGTH_LONG).show();
                            progressBar.dismiss();
                        }
                    });
                });

        return builder.create();
    }
}
