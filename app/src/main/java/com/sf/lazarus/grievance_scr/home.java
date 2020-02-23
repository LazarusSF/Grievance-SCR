package com.sf.lazarus.grievance_scr;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class home extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void click_user_login(View view){
        Intent intent_userlogin_obj=new Intent(this,user_login.class);
        startActivity(intent_userlogin_obj);
    }
    public void click_admin_login(View view){
        Intent intent_adminlogin_obj=new Intent(this,admin_login.class);
        startActivity(intent_adminlogin_obj);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder home_alert=new AlertDialog.Builder(home.this);
        home_alert.setIcon(R.drawable.ic_error_outline_black_24dp)
                .setCancelable(false)
                .setMessage("Exit from Application")
                .setPositiveButton("stay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Leave", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finishAffinity();
                    }
                });
        AlertDialog home_alert_obj= home_alert.create();
        home_alert_obj.setTitle("Alert");
        home_alert_obj.show();
    }

}
