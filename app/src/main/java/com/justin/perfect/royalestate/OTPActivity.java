package com.justin.perfect.royalestate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    Button verifyOTP_btn;
    EditText code_et;
    RelativeLayout relativeLayout;
    private String verificationid;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        auth = FirebaseAuth.getInstance();

        String phonenumber = getIntent().getStringExtra("phonenumber");
       // Toast.makeText(OTPActivity.this,phonenumber,Toast.LENGTH_SHORT).show();
        sendVerificationCode(phonenumber);

        progressBar = findViewById(R.id.progressbar);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl);
        verifyOTP_btn = (Button) findViewById(R.id.login_verifyOTP_btn);
        code_et = (EditText) findViewById(R.id.login_otp_et);

        verifyOTP_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = code_et.getText().toString().trim();

                if ((code.isEmpty() || code.length() < 6)){

                    code_et.setError("Enter code...");
                    code_et.requestFocus();
                    return;
                }
                verifyCode(code);

            }
        });
    }

    private void sendVerificationCode(String number){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                progressBar.setVisibility(View.VISIBLE);
                code_et.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Snackbar.make(relativeLayout,e.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
            //Toast.makeText(OTPActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();

        }
    };

    private void verifyCode(String code){
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
            signInWithCredential(credential);
        } catch (Exception e) {
            //Log.i("exception",e.toString());
            Snackbar.make(relativeLayout,"Invalid credentials",Snackbar.LENGTH_SHORT).show();
           // Toast.makeText(OTPActivity.this,"Invalid credentials",Toast.LENGTH_LONG).show();
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(OTPActivity.this, OptionActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);

                        } else {
                            Snackbar.make(relativeLayout,task.getException().getMessage(),Snackbar.LENGTH_SHORT).show();
                            //Toast.makeText(OTPActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }
}
