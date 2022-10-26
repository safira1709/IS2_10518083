package com.example.is2_10518083;
import android.app.ProgressDialog;
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

import java.util.HashMap;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextNama; private EditText editTextNohp;
    private EditText editTextAngkatan; private EditText editTextKelas;

    private Button buttonAdd; private Button buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextNohp = (EditText) findViewById(R.id.editTextNohp);
        editTextAngkatan = (EditText) findViewById(R.id.editTextAngkatan);
        editTextKelas = (EditText) findViewById(R.id.editTextKelas);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
    }

    private void addContact() {

        final String nama = editTextNama.getText().toString().trim();
        final String nohp = editTextNohp.getText().toString().trim();
        final String angkatan = editTextAngkatan.getText().toString().trim();
        final String kelas = editTextKelas.getText().toString().trim();

        class AddContact extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CreateActivity.this,"Menambahkan...", "Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(CreateActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_CON_NAMA,nama);
                params.put(Konfigurasi.KEY_CON_NOHP,nohp);
                params.put(Konfigurasi.KEY_CON_ANGKATAN,angkatan);
                params.put(Konfigurasi.KEY_CON_KELAS,kelas);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Konfigurasi.URL_ADD, params);
                return res;
            }
        }

        AddContact ae = new AddContact();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){ addContact();
        }

        if(v == buttonView){
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_read) {
            Intent iread = new Intent(this, MainActivity.class);
            startActivity(iread);
            return true;
        } else if (id == R.id.action_create) {
            Intent icreate = new Intent(this, CreateActivity.class);
            startActivity(icreate);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}