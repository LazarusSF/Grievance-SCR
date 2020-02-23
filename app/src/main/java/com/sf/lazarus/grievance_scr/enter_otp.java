package com.sf.lazarus.grievance_scr;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tapadoo.alerter.Alert;
import com.tapadoo.alerter.Alerter;

public class enter_otp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

    /*
        Button enter_otp_confirm_button_obj = (Button) findViewById(R.id.enter_otp_confirm_button);

        enter_otp_confirm_button_obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert show = Alerter.create(enter_otp.this)
                        .setIcon(R.drawable.ic_action_name)
                        .setTitle("Railway Grievance")
                        .setText("Correct OTP...\n click here to reset password")
                        .setTextTypeface(Typeface.MONOSPACE)
                        .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                        .enableInfiniteDuration(true)
                        .enableProgress(true)
                        .disableOutsideTouch()
                        .enableVibration(true)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        })
                        .show();

            }
        });
    */
    }

    public void click_confirm_enter_otp(View view){

        Intent intent_click_confirm_enter_otp_obj=new Intent(this,new_password.class);
        startActivity(intent_click_confirm_enter_otp_obj);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent_back_enter_otp_obj=new Intent(this,user_login.class);
        startActivity(intent_back_enter_otp_obj);
    }


}
