package com.technight.myrestaurants.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.technight.myrestaurants.Constants;
import com.technight.myrestaurants.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private DatabaseReference mSearchedLocationReference;
    private ValueEventListener mSearchedLocationReferenceListener;

    @BindView(R.id.findRestaurantsButton) Button mFindRestaurantsButton;
//    @BindView(R.id.locationEditText) EditText mLocationEditText;
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;
    @BindView(R.id.savedRestaurantsButton) Button savedRestaurantsButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        mSearchedLocationReference = FirebaseDatabase
//                .getInstance()
//                .getReference()
//                .child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION);
//
//        mSearchedLocationReferenceListener = mSearchedLocationReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot locationSnapShot : dataSnapshot.getChildren()) {
//                    String location = locationSnapShot.getValue().toString();
//                    Log.d("Locations updated", "location: " + location);
//                }
//            }

//            @Override
//            public void onCancelled(DatabaseError error) {
//
//            }
//        });
//
//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mEditor = mSharedPreferences.edit();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                } else {

                }
            }
        };
        mFindRestaurantsButton.setOnClickListener(this);
        savedRestaurantsButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mFindRestaurantsButton) {
//            String location = mLocationEditText.getText().toString();
//
//            saveLocationToFirebase(location);
//
//            if(!(location).equals("")) {
//                addToSharedPreferences(location);
//            }
//
            Intent intent = new Intent(MainActivity.this, RestaurantListActivity.class);
//            intent.putExtra("location", location);
            startActivity(intent);
        }

        if (v == savedRestaurantsButton) {
            Intent intent = new Intent(MainActivity.this, SavedRestaurantListActivity.class);
            startActivity(intent);
        }
    }

//    public void saveLocationToFirebase(String location) {
//        mSearchedLocationReference.push().setValue(location);
//    }
//
//    private void addToSharedPreferences(String location) {
//        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mSearchedLocationReference.removeEventListener(mSearchedLocationReferenceListener);
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
