package com.rku.tutorial11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class UserData extends AppCompatActivity {

    TextView txtId, txtName, txtUsername, txtEmail, txtAddress, txtPhone, txtWebsite, txtCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        txtId = findViewById(R.id.txtId);
        txtName = findViewById(R.id.txtName);
        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtAddress = findViewById(R.id.txtAddress);
        txtPhone = findViewById(R.id.txtPhone);
        txtWebsite = findViewById(R.id.txtWebsite);
        txtCompany = findViewById(R.id.txtCompany);

        Intent intent = getIntent();
        int position = intent.getIntExtra("userdata",0);
        try {
            JSONObject userObj = Util.userdata.getJSONObject(position);
            JSONObject addObj = userObj.getJSONObject("address");
            JSONObject comObj = userObj.getJSONObject("company");
            txtId.setText(userObj.getString("id"));
            txtName.setText(userObj.getString("name"));
            txtUsername.setText(userObj.getString("username"));
            txtEmail.setText(userObj.getString("email"));
            txtAddress.setText(addObj.getString("street")+", "+addObj.getString("city"));
            txtPhone.setText(userObj.getString("phone"));
            txtWebsite.setText(userObj.getString("website"));
            txtCompany.setText(comObj.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}