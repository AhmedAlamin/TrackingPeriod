package com.teachonlineahmedoalamin.trackingperiod.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.teachonlineahmedoalamin.trackingperiod.Model.TrackedInfoLocal;
import com.teachonlineahmedoalamin.trackingperiod.R;
import com.teachonlineahmedoalamin.trackingperiod.Utiles.DatabaseHandler;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton googleSignIn;
    private ProgressBar br;
    private EditText defultName;
    private Button bu;

    private DatabaseHandler db;
    TrackedInfoLocal data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// local data base

        //period days 5
        //days of cycle 28
        //---------------
       //last date 12/11/2020
       //----------------
        //to day is 12/11/2020



        defultName = (EditText) findViewById(R.id.nameid);
        bu = (Button) findViewById(R.id.goButton);


        data = new TrackedInfoLocal();


        br = (ProgressBar) findViewById(R.id.progressBar);

        // Get firebase Root Object
        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // end Google Sign In



        googleSignIn = findViewById(R.id.google_sign_in);

        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (defultName.getText().toString().equals("")) {
                    defultName.setError("Enter your Name");
                } else {
                    String name = defultName.getText().toString();

//                    data.setLocalName(name);
//
//                    db.insertLocalperiodData(data);

                    startActivity(new Intent(MainActivity.this, GetBasicInfo.class).putExtra("name",name));
                    finish();
                }
            }
        });

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                br.setVisibility(View.VISIBLE);
                signIn();
            }
        });
    }



    private void updateUI(FirebaseUser user) {

        db = new DatabaseHandler(this);
        db.openDatabase();

        data = new TrackedInfoLocal();
        data = db.getAllData();



        //Change UI according to user data.
        if (user != null && data.getDaysOfCycle() !=0) {
            Toast.makeText(this, "You Signed In successfully", Toast.LENGTH_SHORT).show();
            // TODO: 12/10/2020 please handle when user press google login and getbasic activity show if he kill the app he will getback to the getbasic activity even he entered the informaion
            startActivity(new Intent(this, HomeActivity.class)); // check here if it's first time to login or not
            finish();

        }
        else if (user != null && data.getDaysOfCycle() ==0) {

            Log.i(TAG, "pojo 1updateUI: ID is:  "+data.getId());
            Toast.makeText(this, "ID is : "+ data.getId(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, GetBasicInfo.class));
            finish();

        }
        else if (data.getLocalName() != null && data.getPeriodDays() == 0) {
            Log.i(TAG, "pojo 2 updateUI: ID is:  "+data.getId());
            Toast.makeText(this, "ID is : "+ data.getId(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, GetBasicInfo.class));
            finish();

        }
        else if (data.getLocalName() != null && data.getPeriodDays() != 0 ) {
            Log.i(TAG, "pojo 3 updateUI: ID is:  "+data.getId());
            Toast.makeText(this, "ID is : "+ data.getId(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomeActivity.class));
            finish();

        }

        else {
            br.setVisibility(View.INVISIBLE);

            Toast.makeText(this,"Sign In Please",Toast.LENGTH_LONG).show();
        }

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        // [START_EXCLUDE silent]
        br.setVisibility(View.VISIBLE);
        // [END_EXCLUDE]
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());


                            Toast.makeText(MainActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        br.setVisibility(View.INVISIBLE);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]


    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }

    @Override
    public void onStart() {
        // Check if user is signed in (non-null) and update UI accordingly.
        super.onStart();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onStop() {
        super.onStop();
        br.setVisibility(View.INVISIBLE);
    }




}
