package com.example.is2_10518083;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
public class MainActivity extends AppCompatActivity implements android.widget.ListView.OnItemClickListener{
    private android.widget.ListView listView;
    private String JSON_STRING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();

    }


    private void showContact(){ JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){ JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Konfigurasi.TAG_ID);
                String nama = jo.getString(Konfigurasi.TAG_NAMA);
                String nohp = jo.getString(Konfigurasi.TAG_NOHP);
                String angkatan = jo.getString(Konfigurasi.TAG_ANGKATAN);
                String kelas = jo.getString(Konfigurasi.TAG_KELAS);

                HashMap<String,String> contacts = new HashMap<>();
                contacts.put(Konfigurasi.TAG_ID,id);
                contacts.put(Konfigurasi.TAG_NAMA,nama);
                contacts.put(Konfigurasi.TAG_NOHP,nohp);
                contacts.put(Konfigurasi.TAG_ANGKATAN,angkatan);
                contacts.put(Konfigurasi.TAG_KELAS,kelas);
                list.add(contacts);
            }

        } catch (JSONException e) { e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                MainActivity.this, list, R.layout.activity_list_view,
                new String[]{Konfigurasi.TAG_NAMA,Konfigurasi.TAG_NOHP,Konfigurasi.TAG_ANGKATAN,Konfigurasi.TAG_KELAS},
                new int[]{R.id.nama, R.id.nohp, R.id.angkatan, R.id.kelas});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class getJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading; @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) { super.onPostExecute(s); loading.dismiss();
                JSON_STRING = s;
                showContact();
            }

            @Override
            protected String doInBackground(Void... params) { RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Konfigurasi.URL_GET_ALL);
                return s;
            }
        }
        getJSON gj = new getJSON(); gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DetailActivity.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String empId = map.get(Konfigurasi.TAG_ID).toString();
        intent.putExtra(Konfigurasi.CON_ID,empId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_read) {
            Intent iread = new Intent(this, MainActivity.class); startActivity(iread);
            return true;
        } else if (id == R.id.action_create) {
            Intent icreate = new Intent(this, CreateActivity.class);
            startActivity(icreate);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}