package com.justin.perfect.royalestate;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GiveOnRentActivity extends AppCompatActivity {

    EditText area_et,city_et;
    Button next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_on_rent);

        area_et = (EditText) findViewById(R.id.area_et);
        city_et = (EditText) findViewById(R.id.city_et);
        next_btn = (Button) findViewById(R.id.buy_next_btn);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String area = area_et.getText().toString();
                String city = city_et.getText().toString();
                if (area.isEmpty()){
                    Snackbar.make(view,"Please Enter area detail", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (city.isEmpty()){
                    Snackbar.make(view,"Please Enter city detail", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Intent intent = new Intent(GiveOnRentActivity.this,BuyBudgetAndPropertyTypeActivity.class);
                    intent.putExtra("area",area);
                    intent.putExtra("city",city);
                    intent.putExtra("uniqid","GiveOnRent");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            }
        });
    }

    public void goBack(View view) {
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
