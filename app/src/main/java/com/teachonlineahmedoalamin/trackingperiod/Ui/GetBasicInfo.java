package com.teachonlineahmedoalamin.trackingperiod.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.teachonlineahmedoalamin.trackingperiod.Model.TrackedInfoLocal;
import com.teachonlineahmedoalamin.trackingperiod.R;
import com.teachonlineahmedoalamin.trackingperiod.Utiles.DatabaseHandler;

import java.util.Calendar;


public class GetBasicInfo extends AppCompatActivity {


    private Button blus1,min1,blus2,min2,trackButtom;
    private FirebaseAuth mAuth;
    private TextView mDisplayDate,text1,text2;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    int x =1,y=28;
    String mMonth="",mDay="",mYear="";

    private DatabaseHandler db;
   TrackedInfoLocal data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_basic_info);

        blus1 =(Button) findViewById(R.id.buPlus1);
        min1 = (Button) findViewById(R.id.buMin1);
        blus2 = (Button) findViewById(R.id.buPlus2);
        min2 = (Button) findViewById(R.id.buMin2);
        trackButtom =(Button) findViewById(R.id.buTrack);
        text1 = (TextView) findViewById(R.id.textNum1);
        text2 = (TextView) findViewById(R.id.textNum2);

        mDisplayDate = (TextView) findViewById(R.id.etId);

        db = new DatabaseHandler(GetBasicInfo.this);
        db.openDatabase();

        data = new TrackedInfoLocal();
       //data = db.getAllData();

        final Bundle bundle = getIntent().getExtras();



        trackButtom.setOnClickListener(new View.OnClickListener() {
            private static final String TAG = "Basic Activiy";

            @Override
            public void onClick(View view) {
                if(mDay == ""){
                    Toast.makeText(GetBasicInfo.this,"Please select a Date",Toast.LENGTH_SHORT).show();
                }else{

                    //data.setId(1);
                    data.setPeriodDays(Integer.parseInt(text1.getText().toString()));
                    data.setLastDateLocal(mDisplayDate.getText().toString());
                    data.setDaysOfCycle(Integer.parseInt(text2.getText().toString()));

                    // you should put these info as exstras
                    if (mAuth != null && bundle == null) {
                        data.setLocalName( mAuth.getCurrentUser().getDisplayName());
                      //  db.updateLocalName(1, mAuth.getCurrentUser().getDisplayName());
                    }else {
                        data.setLocalName(bundle.getString("name"));
                        //db.updateLocalName(1,bundle.getString("name"));
                    }
//                    db.updatePeriodDays(1,Integer.parseInt(text1.getText().toString()));
//                    db.updateDaysOfCycle(1,Integer.parseInt(text2.getText().toString()));
//                    db.updateLastDateLocal(1,mDisplayDate.getText().toString());

                    db.insertLocalperiodData(data);


                    Log.d(TAG, "pojo track button : ID is:  "+data.getId());
                    Intent intent = new Intent(GetBasicInfo.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        blus1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (x == 10){
                    text1.setText(String.valueOf(x));
                }
                else{
                    x++;
                    text1.setText(String.valueOf(x));


                }
            }
        });

        min1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (x == 1){

                    text1.setText(String.valueOf(x));
                }
                else{
                    x--;
                    text1.setText(String.valueOf(x));

                }
            }
        });


        blus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(y == 45){
                    text2.setText(String.valueOf(y));
                }else{
                    y++;
                    text2.setText(String.valueOf(y));
                }
            }
        });

        min2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(y == 16){
                    text2.setText(String.valueOf(y));
                }else{
                    y--;
                    text2.setText(String.valueOf(y));
                }
            }
        });




        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        GetBasicInfo.this,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = + day +  "/"+ month+ "/" + year;
                mMonth = String.valueOf(month);
                mDay = String.valueOf(day);
                mYear = String.valueOf(year);
                mDisplayDate.setText(date);
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        // Get firebase Root Object
        mAuth = FirebaseAuth.getInstance();

    }
}