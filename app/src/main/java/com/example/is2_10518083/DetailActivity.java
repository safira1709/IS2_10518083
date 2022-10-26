package com.example.is2_10518083;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextNama; private EditText editTextNohp;
    private EditText editTextAngkatan; private EditText editTextKelas;

    private Button buttonUpdate; private Button buttonDelete;
    private String id; @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        id = intent.getStringExtra(Konfigurasi.CON_ID);

        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextNohp = (EditText) findViewById(R.id.editTextNohp);
        editTextAngkatan = (EditText) findViewById(R.id.editTextAngkatan);
        editTextKelas = (EditText) findViewById(R.id.editTextKelas);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonUpdate.setOnClickListener(this); buttonDelete.setOnClickListener(this);

        getContact();
    }

    private void getContact(){
        class GetContact extends AsyncTask<Void,Void,String> { ProgressDialog loading;
            @Override
            protected void onPreExecute() { super.onPreExecute(); loading =
                    ProgressDialog.show(DetailActivity.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) { super.onPostExecute(s); loading.dismiss();
                showContact(s);
            }

            @Override
            protected String doInBackground(Void... params) { RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_GET_CON,id);
                return s;
            }
        }
        GetContact ge = new GetContact(); ge.execute();
    }

    private void showContact(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            JSONObject c = result.getJSONObject(0);
            String nama = c.getString(Konfigurasi.TAG_NAMA);
            String nohp = c.getString(Konfigurasi.TAG_NOHP);
            String angkatan = c.getString(Konfigurasi.TAG_ANGKATAN);
            String kelas = c.getString(Konfigurasi.TAG_KELAS);

            editTextNama.setText(nama);
            editTextNohp.setText(nohp);
            editTextAngkatan.setText(angkatan);
            editTextKelas.setText(kelas);

        } catch (JSONException e) { e.printStackTrace();
        }
    }

    private void updateContact(){
        final String nama = editTextNama.getText().toString().trim();
        final String nohp = editTextNohp.getText().toString().trim();
        final String angkatan = editTextAngkatan.getText().toString().trim();
        final String kelas = editTextKelas.getText().toString().trim();

        class UpdateContact extends AsyncTask<Void,Void,String>{ ProgressDialog loading;
            @Override
            protected void onPreExecute() { super.onPreExecute(); loading =
                    ProgressDialog.show(DetailActivity.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) { super.onPostExecute(s); loading.dismiss();
                Toast.makeText(DetailActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) { HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put(Konfigurasi.KEY_CON_ID,id);
            hashMap.put(Konfigurasi.KEY_CON_NAMA,nama);
            hashMap.put(Konfigurasi.KEY_CON_NOHP,nohp);
            hashMap.put(Konfigurasi.KEY_CON_ANGKATAN,angkatan);
            hashMap.put(Konfigurasi.KEY_CON_KELAS,kelas);


                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Konfigurasi.URL_UPDATE_CON,hashMap);

                return s;
            }
        }

        UpdateContact ue = new UpdateContact(); ue.execute();
    }

    private void deleteContact(){
        class DeleteContact extends AsyncTask<Void,Void,String> { ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailActivity.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) { super.onPostExecute(s); loading.dismiss();
                Toast.makeText(DetailActivity.this, s, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(Void... params) { RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_DELETE_CON, id); return s;
            }
        }

        DeleteContact de = new DeleteContact(); de.execute();
    }

    private void confirmDeleteContact(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Data KM ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() { @Override
                public void onClick(DialogInterface arg0, int arg1) { deleteContact();
                    startActivity(new Intent(DetailActivity.this,MainActivity.class));
                }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() { @Override
                public void onClick(DialogInterface arg0, int arg1) {

                }
                });

        AlertDialog alertDialog = alertDialogBuilder.create(); alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonUpdate){ updateContact();
        }

        if(v == buttonDelete){ confirmDeleteContact();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { getMenuInflater().inflate(R.menu.menu_main, menu); return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_read) {
            Intent iread = new Intent(this, MainActivity.class); startActivity(iread);
            return true;
        } else if (id == R.id.action_create) {
            Intent icreate = new Intent(this, CreateActivity.class); startActivity(icreate);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
