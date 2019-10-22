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

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Variabel UI
    private EditText txt_username, txt_password, txt_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inisialisasi UI
        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
        txt_phone = findViewById(R.id.txt_phone);

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_username.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Username masih kososng", Toast.LENGTH_SHORT).show();
                }
                else if(txt_password.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Password masih kososng", Toast.LENGTH_SHORT).show();
                }
                else if(txt_phone.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Nomor telepon masih kososng", Toast.LENGTH_SHORT).show();
                }
                else{
                    submit();
                }
            }
        });
    }

    private void submit(){
        AppLoading.getInstance().showLoading(this);
        String parameter = String.format(Locale.getDefault(), "?phone=%s&username=%s&password=%s",
                txt_phone.getText().toString(), txt_username.getText().toString(), txt_password.getText().toString());
        ApiVolleyManager.getInstance().addRequest(this, Constant.URL_CREATE + parameter, ApiVolleyManager.METHOD_GET,
                Constant.getTokenHeader(AppSharedPreferences.getUser(this), AppSharedPreferences.getToken(this)),
                new AppRequestCallback(new AppRequestCallback.RequestListener() {
                    @Override
                    public void onEmpty(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        AppLoading.getInstance().stopLoading();
                    }

                    @Override
                    public void onSuccess(String result) {
                        try{
                            JSONObject response = new JSONObject(result);

                            Intent i = new Intent(MainActivity.this, SuccessActivity.class);
                            i.putExtra(Constant.EXTRA_USERNAME, response.getString("username"));
                            i.putExtra(Constant.EXTRA_PASSWORD, response.getString("password"));
                            i.putExtra(Constant.EXTRA_PASSCODE, response.getString("passcode"));
                            i.putExtra(Constant.EXTRA_HANDPHONE, txt_phone.getText().toString());

                            Toast.makeText(MainActivity.this, "Berhasil membuat password", Toast.LENGTH_SHORT).show();
                            startActivity(i);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                        catch (JSONException e){
                            Toast.makeText(MainActivity.this, "Terjadi kesalahan data", Toast.LENGTH_SHORT).show();
                            Log.e(Constant.TAG, e.getMessage());
                        }
                        AppLoading.getInstance().stopLoading();
                    }

                    @Override
                    public void onFail(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        AppLoading.getInstance().stopLoading();
                    }
                }));
    }

    @Override
    protected void onResume() {
        txt_phone.setText("");
        txt_password.setText("");
        txt_username.setText("");
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        new AppPasswordDialog(this, new AppPasswordDialog.DialogListener() {
            @Override
            public void onSuccess() {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }

            @Override
            public void onFailed() {
                Toast.makeText(MainActivity.this, "Autentifikasi gagal", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }
}
