package com.sf.lazarus.grievance_scr;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class admin_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
    }
    public void click_admin_new_grievance_list(View view){
        Intent intent_admin_new_grievance_list = new Intent(this,admin_new_grievance_list.class);
        startActivity(intent_admin_new_grievance_list);
    }
    public void click_admin_forwarded_grievance_list(View view){
        Intent intent_admin_forwarded_grievance_list = new Intent(this,admin_forwarded_grievance_list.class);
        startActivity(intent_admin_forwarded_grievance_list);
    }
    public void click_admin_closed_grievance_list(View view){
        Intent intent_admin_closed_grievance_list = new Intent(this,admin_closed_grievance_list.class);
        startActivity(intent_admin_closed_grievance_list);
    }
    public void click_admin_change_pwd(View view){
        Intent intent_admin_change_pwd=new Intent(this,admin_reset_password.class);
        startActivity(intent_admin_change_pwd);
    }
    public void click_admin_logout(View view){

        AlertDialog.Builder admin_logout=new AlertDialog.Builder(admin_home.this);
        admin_logout.setIcon(R.drawable.ic_error_outline_black_24dp);
        admin_logout.setCancelable(false);
        admin_logout.setMessage("Logout from Admin Account ?");
        admin_logout.setPositiveButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        })
                .setNegativeButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(admin_home.this, admin_login.class);
                        startActivity(myIntent);
                    }
                });
        AlertDialog home_alert_obj= admin_logout.create();
        home_alert_obj.setTitle("Alert");
        home_alert_obj.show();    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder admin_logout=new AlertDialog.Builder(admin_home.this);
        admin_logout.setIcon(R.drawable.ic_error_outline_black_24dp);
        admin_logout.setCancelable(false);
        admin_logout.setMessage("Logout from Admin Account ?");
        admin_logout.setPositiveButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        })
                .setNegativeButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(admin_home.this, admin_login.class);
                        startActivity(myIntent);
                    }
                });
        AlertDialog home_alert_obj= admin_logout.create();
        home_alert_obj.setTitle("Alert");
        home_alert_obj.show();
    }
}
