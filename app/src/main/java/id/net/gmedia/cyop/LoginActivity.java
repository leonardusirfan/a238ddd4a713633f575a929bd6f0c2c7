package id.net.gmedia.cyop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    //Variabel UI
    private EditText txt_customer, txt_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inisialisasi UI
        txt_customer = findViewById(R.id.txt_customer);
        txt_service = findViewById(R.id.txt_service);

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_customer.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Client ID kosong", Toast.LENGTH_SHORT).show();
                }
                else if(txt_service.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Service ID kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    login();
                }
            }
        });
    }

    private void login(){
        AppLoading.getInstance().showLoading(this);
        JSONBuilder body = new JSONBuilder();
        body.add("customer_id", txt_customer.getText().toString());
        body.add("service_id", txt_service.getText().toString());

        ApiVolleyManager.getInstance().addRequest(this, Constant.URL_LOGIN,
                ApiVolleyManager.METHOD_POST, Constant.HEADER_AUTH, body.create(),
                new AppRequestCallback(new AppRequestCallback.RequestListener() {
                    @Override
                    public void onEmpty(String message) {
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        AppLoading.getInstance().stopLoading();
                    }

                    @Override
                    public void onSuccess(String result) {
                        try{
                            JSONObject response = new JSONObject(result);
                            AppSharedPreferences.Login(LoginActivity.this, response.getString("user_id"),
                                    response.getString("token"));
                            Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                            finish();
                        }
                        catch (JSONException e){
                            Toast.makeText(LoginActivity.this, "Terjadi kesalahan data", Toast.LENGTH_SHORT).show();
                            Log.e(Constant.TAG, e.getMessage());
                        }

                        AppLoading.getInstance().stopLoading();
                    }

                    @Override
                    public void onFail(String message) {
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        AppLoading.getInstance().stopLoading();
                    }
                }));
    }

    @Override
    public void onBackPressed() {
        new AppPasswordDialog(this, new AppPasswordDialog.DialogListener() {
            @Override
            public void onSuccess() {
                LoginActivity.super.onBackPressed();
            }

            @Override
            public void onFailed() {
                Toast.makeText(LoginActivity.this, "Autentifikasi gagal", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }
}
