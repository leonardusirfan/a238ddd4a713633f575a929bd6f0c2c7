package id.net.gmedia.cyop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        String text = " Jika hotspot menggunakan username dan password, gunakan :" +
                "\n\t- Username : " + getIntent().getStringExtra(Constant.EXTRA_USERNAME) +
                "\n\t- Password : " + getIntent().getStringExtra(Constant.EXTRA_PASSWORD) +
                "\n\n" +
                "Jika hotspot menggunakan passcode, gunakan :" +
                "\n\t- Passcode : " + getIntent().getStringExtra(Constant.EXTRA_PASSCODE);
        ((TextView)findViewById(R.id.txt_info)).setText(text);
        kirimWa(getIntent().getStringExtra(Constant.EXTRA_HANDPHONE), text);

        findViewById(R.id.btn_kembali).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void kirimWa(String nomor, String pesan){
        JSONBuilder body = new JSONBuilder();
        body.add("nomor", nomor);
        body.add("pesan", pesan);

        String url = "http://gmedia.bz/wablast/index.php/Main/api_whatsapp";
        ApiVolleyManager.getInstance().addRequest(this, url, ApiVolleyManager.METHOD_POST,
                new HashMap<String, String>(), body.create(), new AppRequestCallback(new AppRequestCallback.SimpleRequestListener() {
                    @Override
                    public void onSuccess(String result) {

                    }

                    @Override
                    public void onFail(String message) {

                    }
                }));
    }

}
