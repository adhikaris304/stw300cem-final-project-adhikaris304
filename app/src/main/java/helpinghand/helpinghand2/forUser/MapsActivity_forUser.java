package helpinghand.helpinghand2.forUser;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import helpinghand.helpinghand2.R;

public class MapsActivity_forUser extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_for_user);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        final String professionalLatitude = getIntent().getExtras().getString("ProfessionalLatitude");
        final String professionalLongitude = getIntent().getExtras().getString("ProfessionalLongitude");
        final String professionaladdress = getIntent().getExtras().getString("ProfessionalAddress");
        final String professionalName = getIntent().getExtras().getString("ProfessionalName");

        double mlat = (Double.parseDouble(professionalLatitude));
        double mlon = (Double.parseDouble(professionalLongitude));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MapsActivity_forUser.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);

            return;
        }
        mMap.setMyLocationEnabled(true);

        LatLng sydney = new LatLng(mlat, mlon);

        mMap.addMarker(new MarkerOptions().position(sydney).title(professionalName).snippet(professionaladdress));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14.0f));


    }
}
