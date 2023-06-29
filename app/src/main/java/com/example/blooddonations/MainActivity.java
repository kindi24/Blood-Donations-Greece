package com.example.blooddonations;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import com.example.blooddonations.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    // Ορισμός false, ότι δεν εμφανίστηκε το μήνυμα
    private boolean msg = false;

    // Ορισμός TAG
    private static final String TAG = "MainActivity";

    private static FirebaseAuth mAuth;
    private static FirebaseAuth.AuthStateListener mAuthListener;
    static FirebaseUser user;

    // Το email και ο κωδικός του χρήστη που θα συνδεθεί
    String email = "";
    String pass = "";

    private EditText mEmail, mPassword;
    private Button LoginB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_bef_don, R.id.nav_compatibility,
                R.id.nav_blood_clubs, R.id.nav_choice_dates,
                R.id.nav_last_don, R.id.nav_service, R.id.nav_request_bottles, R.id.nav_req_don)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Δήλωση μεταβλητών LoginB, mEmail, mPassword
        mEmail = findViewById(R.id.EmailInsert);
        mPassword = findViewById(R.id.PasswordInsert);
        LoginB = findViewById(R.id.loginB);

        // Έλεγχος αν έχει εμφανιστεί το μήνυμα στο Toast
        if(!(msg)){
            // Εμφάνιση μηνύματος
            Toast.makeText(getApplicationContext(), "Καλώς ορίσατε", Toast.LENGTH_SHORT).show();

            // Ορισμός της μεταβλητής msg σε true, για να μην εμφανιστεί 2η φορά
            msg = true;
        }

        // Όταν πατηθεί το κουμπί της σύνδεσης
        LoginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Εισαγωγή τιμών στο email και pass
                email = mEmail.getText().toString();
                pass = mPassword.getText().toString();
                // Έλεγχος αν συμπληρώθηκαν και τα δύο πεδία
                if (!email.equals("") && !pass.equals(""))
                    mAuth.signInWithEmailAndPassword(email, pass);
                else {
                    Toast.makeText(getApplicationContext(),
                            "Συμπλήρωσε όλα τα πεδία", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){

            // Έλεγχος αν έχει συνδεθεί ο χρήστης στην εφαρμογή
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // Ο χρήστης συνδέθηκε
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    Toast.makeText(getApplicationContext(), "Επιτυχής σύνδεση: " + user.getEmail(),
                            Toast.LENGTH_LONG).show();
                }

            }
        };


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // Δομή της αυθεντικοποίησης (onStart, onStop)
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    // Αποσύνδεση χρήστη
    public void signOut(){
        Log.d(TAG, "onAuthStateChanged:signed_out:" + user.getUid());
        mAuth.signOut();
    }

    // Εμφάνιση alert message όταν ο χρήστης πάει να κλείσει την εφαρμογή με το BackPress
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setTitle("Είστε σίγουρος ότι θέλετε να αποχωρήσετε;")
                .setCancelable(false)
                .setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onStop();
                        signOut();
                        finish();
                    }
                })
                .setNegativeButton("Όχι", null)
                .show();
    }

    // Η μέθοδος επιστρέφει true αν έχει συνδεθεί αλλιώς επιστρέφει false
    public static boolean isUserAuthenticated() {
        return user != null;
    }
}