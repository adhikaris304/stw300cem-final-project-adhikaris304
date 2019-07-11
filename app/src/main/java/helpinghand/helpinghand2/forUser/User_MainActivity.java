package helpinghand.helpinghand2.forUser;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import helpinghand.helpinghand2.LoginActivity;
import helpinghand.helpinghand2.R;

public class User_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    SharedPreferences preferences;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__main);
        sensor();
//        proximity();
        preferences=getSharedPreferences("loginInfo",0);

        String registeredUser_name = preferences.getString("UserName","");
        String registeredUser_role = preferences.getString("UserRole","");
        String registeredUser_address = preferences.getString("UserAddress","");
        String registeredUser_email = preferences.getString("UserEmail","");
        String registeredUser_Id=preferences.getString("UserId","");

        boolean interetState=haveNetworkConnection();
        if (interetState)
        {
            // my code
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

            mToolbar = (Toolbar) findViewById(R.id.nav_actionbar);
            setSupportActionBar(mToolbar);

            mDrawerLayout.addDrawerListener(mToggle);
            mDrawerLayout.setBackgroundColor(0xffffff);

            mToggle.syncState();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_menu_white_24dp);
            upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

//            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//            navigationView.setNavigationItemSelectedListener(this);

            NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
            navigationView1.setNavigationItemSelectedListener(this);
            View header = navigationView1.getHeaderView(0);

            TextView navheader = (TextView) header.findViewById(R.id.nav_header_name);
            navheader.setText(registeredUser_name);

            TextView navaddress = (TextView) header.findViewById(R.id.nav_header_address);
            navaddress.setText(registeredUser_address);

            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().replace(R.id.main_frame, new User_FragmentMain()).commit();
        }
        else
        {
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




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.real_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_how_to_use) {
//            Intent i =new Intent(User_MainActivity.this,Setting_Activity.class);
//            startActivity(i);
            Toast.makeText(User_MainActivity.this,"How To Use..",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if
            (id == R.id.menu_contact_us) {
            Toast.makeText(User_MainActivity.this,"Contact us..",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm = getFragmentManager();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            fm.beginTransaction().replace(R.id.main_frame, new User_FragmentMain()).commit();


        } else if (id == R.id.nav_cleaner) {
            fm.beginTransaction().replace(R.id.main_frame, new CleanerFragment()).commit();


        } else if (id == R.id.nav_electrician) {
            fm.beginTransaction().replace(R.id.main_frame, new ElectricianFragment()).commit();


        } else if (id == R.id.nav_plumber) {
            fm.beginTransaction().replace(R.id.main_frame, new PlumberFragment()).commit();


        } else if (id == R.id.nav_laptop_repairer) {
            fm.beginTransaction().replace(R.id.main_frame, new LaptopRepairerFragment()).commit();


        }
        else if (id == R.id.nav_panal) {
            startActivity(new Intent(User_MainActivity.this,UserPanal.class));


        }
        else if (id == R.id.nav_logout) {
            SharedPreferences.Editor editor=preferences.edit();
            editor.clear();
            editor.commit();
            Intent i = new Intent(User_MainActivity.this, LoginActivity.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
//    private void proximity() {
//        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
//        SensorEventListener proxilistener = new SensorEventListener() {
//            @Override
//            public void onSensorChanged(SensorEvent event) { if (event.values[0] <= 1) {
//                Intent intent=new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//            }
//
//            @Override
//            public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//            }
//        };
//        sensorManager.registerListener(proxilistener,sensor, SensorManager.SENSOR_DELAY_NORMAL);

//    }
    private void sensor() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[1] < -2) {
                    Intent intent = new Intent(User_MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {


            }
        };
        if (sensor != null) {
            sensorManager.registerListener(listener, sensor, sensorManager.SENSOR_DELAY_NORMAL);

        } else {
            Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
        }
    }

}
