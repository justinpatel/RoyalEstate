package com.justin.perfect.royalestate;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.justin.perfect.royalestate.adapter.HorizontalAdapter;
import com.justin.perfect.royalestate.util.Upload;
import com.squareup.picasso.Picasso;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.net.URI;

import dmax.dialog.SpotsDialog;

public class BuyBudgetAndPropertyTypeActivity extends AppCompatActivity implements HorizontalAdapter.RecyclerViewClickListener {

    TextView toolbar_tv,min_tv,max_tv;
    RangeSeekBar rangeSeekBar;
    LinearLayout bedrooms_layout,land_layout,shop_layout,upload_photo_layout;
    RadioGroup radioGroup;
    RadioButton checkedRadioButton;
    EditText acres_et,sqft_et;
    Button nextBtn,upload_photo_btn,choose_photo_btn;
    String area,city,uniqid,photo;
    public static final int PICK_IMAGE_REQUEST = 1;
    ImageView imageView;
    private Uri imageUri;

    private StorageReference storageReference;
    int pos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_budget_and_property_type);

        area = getIntent().getStringExtra("area");
        city = getIntent().getStringExtra("city");
        uniqid = getIntent().getStringExtra("uniqid");

        //Toast.makeText(this,uniqid,Toast.LENGTH_SHORT).show();

        toolbar_tv = (TextView) findViewById(R.id.toolbar_tv);

        upload_photo_layout = (LinearLayout) findViewById(R.id.upload_photo_layout);

        if (uniqid.equals("getOnRent")){
            toolbar_tv.setText("Rent Property in "+city);
            upload_photo_layout.setVisibility(View.INVISIBLE);
        }
        if (uniqid.equals("Seller")){
            toolbar_tv.setText("Sell Property in "+city);
            upload_photo_layout.setVisibility(View.VISIBLE);
        }
        if (uniqid.equals("Buyer")){
            toolbar_tv.setText("Buy Property in "+city);
            upload_photo_layout.setVisibility(View.INVISIBLE);
        }
        if (uniqid.equals("GiveOnRent")){
            toolbar_tv.setText("Give My property on rent in "+city);
            upload_photo_layout.setVisibility(View.VISIBLE);
        }

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
        upload_photo_btn = (Button) findViewById(R.id.upload_photo_btn);
        choose_photo_btn = (Button) findViewById(R.id.choose_photo_btn);
        imageView = (ImageView) findViewById(R.id.image_view);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        choose_photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFileChooser();
            }
        });

        upload_photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });

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

                Intent intent = new Intent(BuyBudgetAndPropertyTypeActivity.this,ContactDetailsActivity.class);
                intent.putExtra("min",min_tv.getText().toString());
                intent.putExtra("max",max_tv.getText().toString());
                intent.putExtra("position",pos+"");
                intent.putExtra("area",area);
                intent.putExtra("city",city);
                intent.putExtra("uniqid",uniqid);
                intent.putExtra("photo",photo);

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

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadFile() {
        if (imageUri != null){
            final android.app.AlertDialog waitingDialog = new SpotsDialog(BuyBudgetAndPropertyTypeActivity.this);
            waitingDialog.show();
            final StorageReference fileRef = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
            fileRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(BuyBudgetAndPropertyTypeActivity.this,"Upload successful",Toast.LENGTH_LONG).show();
                                    photo = uri.toString();
                                    waitingDialog.dismiss();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(BuyBudgetAndPropertyTypeActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            waitingDialog.dismiss();
                        }
                    });
        }else {
            Toast.makeText(this,"No File Selected",Toast.LENGTH_SHORT).show();
        }
    }

    private void OpenFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();

            Picasso.with(this).load(imageUri).into(imageView);

        }
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
       //Snackbar.make(view,"position "+position,Snackbar.LENGTH_SHORT).show();
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
