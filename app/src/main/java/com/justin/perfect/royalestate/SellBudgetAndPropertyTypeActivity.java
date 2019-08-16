package com.justin.perfect.royalestate;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.justin.perfect.royalestate.adapter.HorizontalAdapter;

import org.florescu.android.rangeseekbar.RangeSeekBar;

public class SellBudgetAndPropertyTypeActivity extends AppCompatActivity implements HorizontalAdapter.RecyclerViewClickListener {

    TextView toolbar_tv,min_tv,max_tv;
    RangeSeekBar rangeSeekBar;
    SeekBar sellPriceSeekbar;
    LinearLayout bedrooms_layout,land_layout,shop_layout;
    RadioGroup radioGroup;
    RadioButton checkedRadioButton;
    EditText acres_et,sqft_et;
    Button nextBtn;
    String area,city;

    int pos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_budget_and_property_type);

        area = getIntent().getStringExtra("area");
        city = getIntent().getStringExtra("city");

        toolbar_tv = (TextView) findViewById(R.id.toolbar_tv);
        toolbar_tv.setText("Sell Property in "+city);

        final RecyclerView types = findViewById(R.id.property_type_list);
        types.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        types.setAdapter(new HorizontalAdapter(new String[]{"Apartment", "House/Villa", "Land", "Shop"},this));

        rangeSeekBar = (RangeSeekBar) findViewById(R.id.range_seekbar);
        bedrooms_layout = (LinearLayout) findViewById(R.id.bedrooms_layout);
        land_layout = (LinearLayout)findViewById(R.id.land_layout);
        shop_layout = (LinearLayout) findViewById(R.id.shop_layout);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        //RadioButton checkedRadioButton = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        //Toast.makeText(this,checkedRadioButton.getText().toString(),Toast.LENGTH_SHORT).show();
        acres_et = (EditText) findViewById(R.id.acre_et);
        sqft_et = (EditText) findViewById(R.id.sqft_et) ;
        nextBtn = (Button) findViewById(R.id.buy2_next_btn);

        min_tv = (TextView) findViewById(R.id.min_tv);
        max_tv = (TextView) findViewById(R.id.max_tv);

        rangeSeekBar.setSelectedMaxValue(100);
        rangeSeekBar.setSelectedMinValue(0);

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Number min_value = bar.getSelectedMinValue();
                Number max_value = bar.getSelectedMaxValue();

                int min = (int) min_value;
                int max = (int) max_value;

                min_tv.setText(min + " Lacs");
                max_tv.setText(max+" Lacs");

                if (min == 100){
                    min_tv.setText(min/100+"+ Crores");
                }
                if(max == 100){
                    max_tv.setText(max/100+"+ Crores");
                }

                //Toast.makeText(BuyBudgetAndPropertyTypeActivity.this,min_tv.getText().toString()+" "+max_tv.getText().toString(),Toast.LENGTH_SHORT).show();

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                checkedRadioButton = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                //Toast.makeText(BuyBudgetAndPropertyTypeActivity.this,checkedRadioButton.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String acres = acres_et.getText().toString();
                String sqft = sqft_et.getText().toString();

                Intent intent = new Intent(SellBudgetAndPropertyTypeActivity.this,ContactDetailsActivity.class);
                intent.putExtra("min",min_tv.getText().toString());
                intent.putExtra("max",max_tv.getText().toString());
                intent.putExtra("position",pos+"");
                intent.putExtra("area",area);
                intent.putExtra("city",city);
                intent.putExtra("uniqid","Seller");

                if (pos == 1 || pos == 0){
                    if (checkedRadioButton == null) {
                        Snackbar.make(view,"Please choose no. of Bedrooms",Snackbar.LENGTH_SHORT).show();
                    }else {
                        //Toast.makeText(BuyBudgetAndPropertyTypeActivity.this, "min"+min_tv.getText().toString()+" max"+max_tv.getText().toString(), Toast.LENGTH_SHORT).show();
                        intent.putExtra("bedrooms",checkedRadioButton.getText().toString());
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    }
                }
                else if (pos == 2){
                    if (acres.equals("")) {
                        Snackbar.make(view,"Please Enter how many acres land you want",Snackbar.LENGTH_SHORT).show();
                    }else {
                        //Toast.makeText(BuyBudgetAndPropertyTypeActivity.this, acres, Toast.LENGTH_SHORT).show();
                        intent.putExtra("acres",acres);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    }
                }else if (pos == 3){
                    if (sqft.equals("")) {
                        Snackbar.make(view,"Please Enter how much square ft shop you want",Snackbar.LENGTH_SHORT).show();
                    }else {
                        //Toast.makeText(BuyBudgetAndPropertyTypeActivity.this, sqft, Toast.LENGTH_SHORT).show();
                        intent.putExtra("sqft",sqft);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    }
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

    @Override
    public void onClick(View view, int position) {
        pos = position;
        if (position == 0){
            bedrooms_layout.setVisibility(View.VISIBLE);
            land_layout.setVisibility(View.GONE);
            shop_layout.setVisibility(View.GONE);
        }
        if (position == 1){
            bedrooms_layout.setVisibility(View.VISIBLE);
            land_layout.setVisibility(View.GONE);
            shop_layout.setVisibility(View.GONE);
        }
        if (position == 2){
            bedrooms_layout.setVisibility(View.GONE);
            land_layout.setVisibility(View.VISIBLE);
            shop_layout.setVisibility(View.GONE);
        }
        if (position == 3){
            bedrooms_layout.setVisibility(View.GONE);
            land_layout.setVisibility(View.GONE);
            shop_layout.setVisibility(View.VISIBLE);
        }
    }
}
