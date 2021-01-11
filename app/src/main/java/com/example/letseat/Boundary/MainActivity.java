package com.example.letseat.Boundary;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.letseat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/*
UI which enables the user to register or login based on his/her choice
 */
public class MainActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Button mForgetBtn= (Button) findViewById(R.id.forget_btn);

       Button mRegisterBtn = (Button)findViewById(R.id.register_btn);
       Button mLoginBtn = (Button)findViewById(R.id.login_btn);
       final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();



        mForgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                showRecoverPasswordDialog(pd,mAuth);
                //go to register activity

            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to register activity
                startActivity(new Intent(MainActivity.this, RegisterUI.class));
                finish();
            }
        });

        //Click on login
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginUI.class));
                finish();
            }
        });
    }


    @Override
    protected void onStart() {
        //check at start of app
        super.onStart();
    }


    private void showRecoverPasswordDialog(final ProgressDialog pd, final FirebaseAuth mAuth){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout linearLayout = new LinearLayout(this);
        builder.setTitle("Recover Password");
        final EditText emailET = new EditText(this);
        emailET.setHint("Email");
        emailET.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailET.setMinEms(18);

        linearLayout.addView(emailET);
        linearLayout.setPadding(20,10,10,10);
        builder.setView(linearLayout);

        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //key in email
                String email = emailET.getText().toString().trim();
                pwRecovery(email, pd, mAuth);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }


    private void pwRecovery(String email, final ProgressDialog pd, FirebaseAuth mAuth){
        pd.setMessage("Sending email...");
        pd.show();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Email sent!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Failed to send email", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
