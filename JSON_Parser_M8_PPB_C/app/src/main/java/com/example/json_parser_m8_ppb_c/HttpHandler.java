package com.example.json_parser_m8_ppb_c;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpHandler {
    public HttpHandler() {}

    public String makeServiceCall(String reqURL) {
        String response = null;

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e("httpHandler", "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e("httpHandler", "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e("httpHandler", "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e("httpHandler", "Exception: " + e.getMessage());
        }

        return response;
    }

    private String convertStreamToString(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder builder = new StringBuilder();

        String line;

        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return builder.toString();
    }
}
