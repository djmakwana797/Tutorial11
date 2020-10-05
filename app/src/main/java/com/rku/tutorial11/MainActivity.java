package com.rku.tutorial11;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ListView lstData;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstData = findViewById(R.id.lstData);
        lstData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,UserData.class);
                intent.putExtra("userdata",position);
                startActivity(intent);
            }
        });

        new MyAsyncTask().execute();
    }

    class MyAsyncTask extends AsyncTask {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading data");
            dialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            StringBuffer response = new StringBuffer();
            try {
                URL url = new URL(Util.URL_USERS);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader ir = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(ir);
                String inputLine = null;

                while((inputLine = br.readLine())!=null){
                    response.append(inputLine);
                }
                br.close();
                ir.close();

                Util.userdata = new JSONArray(response.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            adapter = new CustomAdapter(MainActivity.this,Util.userdata);
            lstData.setAdapter(adapter);

            if(dialog.isShowing())dialog.dismiss();
        }
    }
}