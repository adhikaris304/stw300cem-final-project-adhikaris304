package helpinghand.helpinghand2.forProfessional;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import helpinghand.helpinghand2.LoginActivity;
import helpinghand.helpinghand2.R;


public class ProPanal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    SharedPreferences preferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professionalpanal);
        preferences=getSharedPreferences("loginInfo",0);

        String registeredUser_name = preferences.getString("UserName","");
        String registeredUser_role = preferences.getString("UserRole","");
        String registeredUser_address = preferences.getString("UserAddress","");
        String registeredUser_email = preferences.getString("UserEmail","");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mToolbar = (Toolbar) findViewById(R.id.nav_actionbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        navigationView1.setNavigationItemSelectedListener(this);
        View header = navigationView1.getHeaderView(0);

        TextView navheader = (TextView) header.findViewById(R.id.nav_header_name);
        navheader.setText(registeredUser_name);

        TextView navaddress = (TextView) header.findViewById(R.id.nav_header_address);
        navaddress.setText(registeredUser_address);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.professional_main_frame, new Professional_FragmentMain()).commit();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm = getFragmentManager();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_logout_p) {
            SharedPreferences.Editor editor=preferences.edit();
            editor.clear();
            editor.commit();
            Intent i = new Intent(ProPanal.this, LoginActivity.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
