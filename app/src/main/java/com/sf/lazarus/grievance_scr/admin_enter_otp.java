package com.sf.lazarus.grievance_scr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class admin_enter_otp extends AppCompatActivity {
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_enter_otp);
        Bundle extras=getIntent().getExtras();
        mobile= extras.getString("mobile");

    }
    public void click_confirm_admin_enter_otp(View view){

        Intent intent_click_confirm_admin_enter_otp_obj=new Intent(this,admin_changepassword.class);
        intent_click_confirm_admin_enter_otp_obj.putExtra("mobile",mobile);
        startActivity(intent_click_confirm_admin_enter_otp_obj);

    }
}
