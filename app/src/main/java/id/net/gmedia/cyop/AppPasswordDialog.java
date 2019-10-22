package id.net.gmedia.cyop;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class AppPasswordDialog{
    private Activity activity;
    private DialogListener listener;
    private Dialog dialog;

    AppPasswordDialog(Activity activity, DialogListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public void show(){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);

        int device_TotalWidth = size.x;

        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_password);
        if(dialog.getWindow() != null){
            dialog.getWindow().setLayout(device_TotalWidth * 40 / 100, WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        final EditText txt_password = dialog.findViewById(R.id.txt_password);
        Button btn_proses = dialog.findViewById(R.id.btn_proses);

        btn_proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txt_password.getText().toString().isEmpty()){
                    cekPassword(activity, txt_password.getText().toString());
                }
            }
        });

        dialog.show();
    }

    private void cekPassword(Activity activity, String password){
        AppLoading.getInstance().showLoading(activity);
        if(password.equals("admin")){
            AppLoading.getInstance().stopLoading();
            listener.onSuccess();
            dialog.dismiss();
        }
        else{
            AppLoading.getInstance().stopLoading();
            listener.onFailed();
        }
    }

    interface DialogListener {
        void onSuccess();
        void onFailed();
    }
}
