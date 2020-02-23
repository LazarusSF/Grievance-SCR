package com.sf.lazarus.grievance_scr;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class user_home extends AppCompatActivity {

    private int pfno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        String name= (String) getIntent().getCharSequenceExtra("name");
        Toast.makeText(this,name,Toast.LENGTH_LONG);
        Bundle getpfno=getIntent().getExtras();
        pfno=getpfno.getInt("pfno_4use");
    }
    public void click_new_grievance(View view){
        Intent intent_newgrievance_obj=new Intent(this,new_grievance.class);
        Bundle bundle=new Bundle();
        bundle.putInt("pfno_4use",pfno);
        intent_newgrievance_obj.putExtras(bundle);


        startActivity(intent_newgrievance_obj);
    }
    public void click_past_grievance_list(View view){
        Intent intent_pastgrievancelist_obj=new Intent(this,past_grievance_list.class);
        Bundle bundle=new Bundle();
        String now=String.valueOf(pfno);
        bundle.putString("pfnosent", now);
        intent_pastgrievancelist_obj.putExtras(bundle);
        startActivity(intent_pastgrievancelist_obj);
    }

    public void click_change_pwd_user_home(View view){
        startActivity(new Intent(this,reset_password.class));
    }

    public void click_logout_user_home(View view){

        AlertDialog.Builder user_logout=new AlertDialog.Builder(user_home.this);
        user_logout.setIcon(R.drawable.ic_error_outline_black_24dp)
        .setCancelable(false)
        .setMessage("Logout from User Account ?")
                    .setPositiveButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(user_home.this, user_login.class);
                        finish();
                        startActivity(myIntent);
                    }
                });
        AlertDialog home_alert_obj= user_logout.create();
        home_alert_obj.setTitle("Alert");
        home_alert_obj.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder user_logout=new AlertDialog.Builder(user_home.this);
        user_logout.setIcon(R.drawable.ic_error_outline_black_24dp)
                .setCancelable(false)
                .setMessage("Logout from User Account ?")
                .setPositiveButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(user_home.this, user_login.class);
                        finish();
                        startActivity(myIntent);
                    }
                });
        AlertDialog home_alert_obj= user_logout.create();
        home_alert_obj.setTitle("Alert");
        home_alert_obj.show();

    }
}
