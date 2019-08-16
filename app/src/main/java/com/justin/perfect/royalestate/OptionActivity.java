package com.justin.perfect.royalestate;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OptionActivity extends AppCompatActivity {

    //FirebaseUser user;
    RelativeLayout relativeLayout;
    TextView buy,sell,giveOnRent,getPropOnRent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        //user = FirebaseAuth.getInstance().getCurrentUser();

        relativeLayout = (RelativeLayout) findViewById(R.id.rl);
        //Snackbar.make(relativeLayout,""+user.getUid()+" "+user.getPhoneNumber(),Snackbar.LENGTH_SHORT).show();

        buy = (TextView) findViewById(R.id.buy_prop_tv);
        sell = (TextView) findViewById(R.id.sell_prop_tv);
        giveOnRent = (TextView) findViewById(R.id.rent_my_prop_tv);
        getPropOnRent = (TextView) findViewById(R.id.get_prop_on_rent_tv);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OptionActivity.this,BuyActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this,SellActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });

        giveOnRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this,GiveOnRentActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });

        getPropOnRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this,GetOnRentActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });
    }
}
