package com.teachonlineahmedoalamin.trackingperiod.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teachonlineahmedoalamin.trackingperiod.Model.TrackedInfoLocal;
import com.teachonlineahmedoalamin.trackingperiod.R;
import com.teachonlineahmedoalamin.trackingperiod.Utiles.DatabaseHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;




public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;


    private DatabaseHandler db;
    TrackedInfoLocal data;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;

//    public static HomeActivity newInstance() {
//        return new HomeActivity();
//    }

    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // Navigation Main Drawable
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);


        name = (TextView) findViewById(R.id.name);
        // local data base

        db = new DatabaseHandler(this);
        db.openDatabase();


        mAuth = FirebaseAuth.getInstance();

        // TODO: 12/10/2020  will implement firebase data base after handling local database
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        //  String day="",month="",year="",dayLast="",cycleDays="";


        // to Update Local data
//        db.updateDaysOfCycle(Integer.parseInt("id"), Integer.parseInt(cycleDays));
//        db.updateLastDateLocal(Integer.parseInt("id"),dayLast);
//        db.updateLocalName(Integer.parseInt("id"),localName);
//


//    LocalDate today = LocalDate.now();
//    LocalDate lastStartOfPeriod = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
//
//    Period p = Period.between(lastStartOfPeriod, today);
//    long p2 = ChronoUnit.DAYS.between(lastStartOfPeriod, today);
//    System.out.println("You are " + p.getYears() + " years, " + p.getMonths() +
//            " months, and " + p.getDays() +
//            " days old. (" + p2 + " days total)");
//
//        // Get the number of days in that month
//        YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(year), Integer.parseInt(month));
//        int daysInMonth = yearMonthObject.lengthOfMonth(); //28
//
//    for (int i = 0 ;i<Integer.parseInt(dayLast);i++) {
//        System.out.println("your period days" + (Integer.parseInt(day)+i));
//    }


//         Get the number of days in that month
//            YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(year), Integer.parseInt(month));
//            int daysInMonth = yearMonthObject.lengthOfMonth(); //28


//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        Date date = calendar.getTime();
//        date = dateFormat.format(calendar.getTime());

//        try {
//            String CurrentDate= "09/24/2018";
//            String FinalDate= "09/26/2019";
//            Date date1;
//            Date date2;
//            SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
//            date1 = dates.parse(CurrentDate);
//            date2 = dates.parse(FinalDate);
//            long difference = Math.abs(date1.getTime() - date2.getTime());
//            long differenceDates = difference / (24 * 60 * 60 * 1000);
//            String dayDifference = Long.toString(differenceDates);
//            name.setText("The difference between two dates is " + dayDifference + " days");
//        } catch (Exception exception) {
//            Toast.makeText(this, "Unable to find difference", Toast.LENGTH_SHORT).show();
//        }

//        mInitialTime=  DateUtils.DAY_IN_MILLIS * 2 +
//                DateUtils.HOUR_IN_MILLIS * 9 +
//                DateUtils.MINUTE_IN_MILLIS * 3 +
//                DateUtils.SECOND_IN_MILLIS * 42;
//
//
//        mCountDownTimer = new CountDownTimer(mInitialTime, 1000) {
//            StringBuilder time = new StringBuilder();
//            @Override
//            public void onFinish() {
//                name.setText(DateUtils.formatElapsedTime(0));
//                //mTextView.setText("Times Up!");
//            }
//
//            @Override
//            public void onTick(long millisUntilFinished) {
//                time.setLength(0);
//                // Use days if appropriate
//                if(millisUntilFinished > DateUtils.DAY_IN_MILLIS) {
//                    long count = millisUntilFinished / DateUtils.DAY_IN_MILLIS;
//                    if(count > 1)
//                        time.append(count).append(" days ");
//                    else
//                        time.append(count).append(" day ");
//
//                    millisUntilFinished %= DateUtils.DAY_IN_MILLIS;
//                }
//
//                time.append(DateUtils.formatElapsedTime(Math.round(millisUntilFinished / 1000d)));
//                name.setText(time.toString());
//            }
//        }.start();

        data = new TrackedInfoLocal();
        data = db.getAllData();

        String StartingPeriodDate = data.getLastDateLocal();
        //  Log.i(TAG, "pojo onCreate: lastdate period is: "+ data.getLastDateLocal());

        Date date = null;
        try {
            date = new SimpleDateFormat("MM/dd/yyyy").parse(StartingPeriodDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal.setTime(date);
        cal2.setTime(date);


        // manipulate date
        cal.add(Calendar.DATE, data.getDaysOfCycle());
        //    Log.i(TAG, "onCreate: pojo the next date is : "+cal.getTime() );


        cal2.add(Calendar.DATE, -data.getDaysOfCycle());
        // Log.i(TAG, "onCreate: pojo the next date is : "+cal.getTime() );


        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        String dayOfMonthStr = String.valueOf(dayOfMonth);

        int dayOfMonth2 = cal2.get(Calendar.DAY_OF_MONTH);

        String dayOfWeek1 = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(cal.getTime());
        String dayOfWeek2 = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(cal2.getTime());

        // String lastDateLocal = data.getLastDateLocal();

        Log.i(TAG, "pojo ID is : " + data.getId() + "\n\nthe next month period : " + new SimpleDateFormat("MMM").format(cal.getTime()) +
                " : " + dayOfWeek1 + " : " + dayOfMonthStr +
                "\n\nthe pervios month period: " + new SimpleDateFormat("MMM").format(cal2.getTime()) +
                " : " + dayOfWeek2 + " : " + dayOfMonth2);


        name.setText("Welcom: " + data.getLocalName() + "  \n\nthe next month period : " + new SimpleDateFormat("MMM").format(cal.getTime()) +
                " : " + dayOfWeek1 + " : " + dayOfMonthStr +
                "\n\nthe pervios month period: " + new SimpleDateFormat("MMM").format(cal2.getTime()) +
                " : " + dayOfWeek2 + " : " + dayOfMonth2);


//        name.setText(data.getLocalName() +"\n ID: "+ data.getId()+"\n Period Days: "+data.getPeriodDays()+"\n Last Date: "+data.getLastDateLocal()+"\n Days of Cycle: "+
//                data.getDaysOfCycle());


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Fragment fragment = null;
                switch (id) {
                    case R.id.search:
                        fragment = new SearchFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.basket:
                        fragment = new BasketFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.favorite:
                        fragment = new FavFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.promo_code:
                        fragment = new PromoCodeFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.orders:
                        fragment = new OrderFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.setting:
                        fragment = new SettingFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.support:
                        fragment = new SupportFragment();
                        loadFragment(fragment);
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });


    }


    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction.addToBackStack(null);
    }






    private void signOut() {
        // Firebase sign out

        if (mAuth != null) {
            mAuth.signOut();

            // Google sign out
            mGoogleSignInClient.signOut().addOnCompleteListener(this,
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(HomeActivity.this, "signed out firebase", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(HomeActivity.this, MainActivity.class));
                            finish();
                        }
                    });
        }

        if( data != null){
            Toast.makeText(HomeActivity.this, "signed out local>>> ID" +data.getId(), Toast.LENGTH_LONG).show();
            db.deleteAll();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        }
    }

}
