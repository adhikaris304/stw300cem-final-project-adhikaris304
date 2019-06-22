package helpinghand.helpinghand2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import helpinghand.helpinghand2.forProfessional.ProPanal;
import helpinghand.helpinghand2.forProfessional.Professional_WelcomeMessageAndroid;
import helpinghand.helpinghand2.forUser.User;
import helpinghand.helpinghand2.forUser.User_WelcomeMessageAndroid;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private String email;
    private String password;
    private FirebaseAuth mAuth;
    private EditText email_ET, password_ET;
    ArrayList<User> users = new ArrayList<User>();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    User registeredUser = new User();
    SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        boolean interetState = haveNetworkConnection();

        if (interetState) {
            TextView noacc = (TextView) findViewById(R.id.noaccount);
            noacc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(LoginActivity.this, SignUPActivity.class);
                    startActivity(i);
                }
            });


            preferences = getSharedPreferences("loginInfo", 0);

            if (preferences.getString("token", "").equals("alreadyloggedbyProfessional")) {
                Intent i = new Intent(LoginActivity.this, ProPanal.class);
                startActivity(i);
            } else if (preferences.getString("token", "").equals("alreadyloggedbyUser")) {
                Intent i = new Intent(LoginActivity.this, User_MainActivity.class);
                startActivity(i);
            } else {


                email_ET = (EditText) findViewById(R.id.email);
                password_ET = (EditText) findViewById(R.id.password);
                final Button signinButton = (Button) findViewById(R.id.email_sign_in_button);


                signinButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        email = email_ET.getText().toString().trim();
                        password = password_ET.getText().toString().trim();
                        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                            Toast.makeText(LoginActivity.this, "Field(s) is Empty!!!", Toast.LENGTH_SHORT).show();
                        } else {

                            callsignin(email, password);
                        }
                    }


                });
            }

        } else {
            try {
                AlertDialog.Builder builder =new AlertDialog.Builder(this);
                builder.setTitle("No internet Connection");
                builder.setMessage("Please turn on internet connection to continue");
                builder.setIcon(R.drawable.helpinghand);
                builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            catch(Exception e)
            {
                //Log.d(Constants.TAG, "Show Dialog: "+e.getMessage());
            }

        }

        TextView noacc = (TextView) findViewById(R.id.noaccount);
        noacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUPActivity.class);
                startActivity(i);
            }
        });


        preferences = getSharedPreferences("loginInfo", 0);

        if (preferences.getString("token", "").equals("alreadyloggedbyProfessional")) {
            Intent i = new Intent(LoginActivity.this, ProPanal.class);
            startActivity(i);
        } else if (preferences.getString("token", "").equals("alreadyloggedbyUser")) {
            Intent i = new Intent(LoginActivity.this, User_MainActivity.class);
            startActivity(i);
        } else {


            email_ET = (EditText) findViewById(R.id.email);
            password_ET = (EditText) findViewById(R.id.password);
            final Button signinButton = (Button) findViewById(R.id.email_sign_in_button);


            signinButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    email = email_ET.getText().toString().trim();
                    password = password_ET.getText().toString().trim();
                    findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                    if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                        Toast.makeText(LoginActivity.this, "Field(s) is Empty!!!", Toast.LENGTH_SHORT).show();
                    } else {

                        callsignin(email, password);
                    }
                }


            });
        }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }


    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void callsignin(final String email, final String password) {


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


        final ChildEventListener childEventListener = mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    User user = new User(
                            dataSnapshot.child("id").getValue().toString(),
                            dataSnapshot.child("name").getValue().toString(),
                            dataSnapshot.child("address").getValue().toString(),
                            dataSnapshot.child("email").getValue().toString(),
                            dataSnapshot.child("phone").getValue().toString(),
                            dataSnapshot.child("password").getValue().toString(),
                            dataSnapshot.child("role").getValue().toString()
                    );

                    if ((email.equalsIgnoreCase(dataSnapshot.child("email").getValue().toString())) && (password.equals(dataSnapshot.child("password").getValue().toString()))) {
                        System.out.println("role =========== " + dataSnapshot.child("role").getValue().toString());
                        users.add(user);
                        registeredUser.setId(dataSnapshot.child("id").getValue().toString());
                        registeredUser.setName(dataSnapshot.child("name").getValue().toString());
                        registeredUser.setEmail(dataSnapshot.child("email").getValue().toString());
                        registeredUser.setAddress(dataSnapshot.child("address").getValue().toString());
                        registeredUser.setPhone(dataSnapshot.child("phone").getValue().toString());
                        registeredUser.setRole(dataSnapshot.child("role").getValue().toString());
                        registeredUser.setPassword(dataSnapshot.child("password").getValue().toString());
                    }

                }
                if (users.size() == 0) {
                    Toast.makeText(LoginActivity.this, "No Such User Found....Login Again...", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);


                } else {
                    if (registeredUser.getRole().equalsIgnoreCase("a")) {


                        System.out.println("role =========== " + registeredUser.getRole());

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("UserId", registeredUser.getId());
                        editor.putString("UserName", registeredUser.getName());
                        editor.putString("UserEmail", registeredUser.getEmail());
                        editor.putString("UserRole", registeredUser.getRole());
                        editor.putString("UserAddress", registeredUser.getAddress());
                        editor.putString("UserPhone", registeredUser.getPhone());
                        editor.putString("token", "alreadyloggedbyProfessional");
                        editor.commit();
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                        Intent i = new Intent(LoginActivity.this, Professional_WelcomeMessageAndroid.class);
                        startActivity(i);


                    } else {
                        System.out.println("role=====" + registeredUser.getRole());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("UserId", registeredUser.getId());
                        editor.putString("UserName", registeredUser.getName());
                        editor.putString("UserEmail", registeredUser.getEmail());
                        editor.putString("UserRole", registeredUser.getRole());
                        editor.putString("UserPhone", registeredUser.getPhone());
                        editor.putString("UserAddress", registeredUser.getAddress());
                        editor.putString("token", "alreadyloggedbyUser");
                        editor.commit();

                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                        Intent i = new Intent(LoginActivity.this, User_WelcomeMessageAndroid.class);
                        i.putExtra("UserName", registeredUser.getName());
                        i.putExtra("UserEmail", registeredUser.getEmail());
                        i.putExtra("UserRole", registeredUser.getRole());
                        i.putExtra("UserAddress", registeredUser.getAddress());
                        i.putExtra("UserId", registeredUser.getId());
                        startActivity(i);


                    }


                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }

}

