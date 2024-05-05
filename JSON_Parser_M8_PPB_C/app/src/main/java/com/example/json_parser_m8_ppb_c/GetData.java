package com.example.json_parser_m8_ppb_c;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GetData extends AsyncTask<Void, Void, Void> {
    private Context context;
    private String url;
    private ArrayList<Contact> dataset;
    private RecyclerView rv_contact_list;

    public GetData(Context context, String url, RecyclerView rv_contact_list) {
        this.context = context;
        this.url = url;
        this.rv_contact_list = rv_contact_list;
        this.dataset = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context, "Downloading data", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpHandler handler = new HttpHandler();
        String jsonStr = handler.makeServiceCall(url);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONObject files = jsonObj.getJSONObject("files");
                JSONObject contacts = files.getJSONObject("contacts");

                JSONObject content = new JSONObject(contacts.getString("content"));
                JSONArray data = content.getJSONArray("contacts");

                for (int i = 0; i < data.length(); i++) {
                    JSONObject item = data.getJSONObject(i);
                    String id = item.getString("id");
                    String name = item.getString("name");
                    String email = item.getString("email");

                    JSONObject phone = item.getJSONObject("phone");
                    String mobile = phone.getString("mobile");

                    Contact c = new Contact(id, name, email, mobile);
                    dataset.add(c);
                }
            } catch (JSONException e) {
                Log.e("Main", "JSON paring error: " + e.getMessage());
            }
        } else {
            Log.e("Main", "Couldn't get JSON from server.");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        ContactListAdapter adapter = new ContactListAdapter(dataset);
        rv_contact_list.setLayoutManager(new LinearLayoutManager(context));
        rv_contact_list.setAdapter(adapter);
    }
}
