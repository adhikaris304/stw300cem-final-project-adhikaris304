package helpinghand.helpinghand2.forUser;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import helpinghand.helpinghand2.R;

import static helpinghand.helpinghand2.R.id.map;

public class SearchByNearest extends FragmentActivity implements OnMapReadyCallback {
    List<Marker> markers = new ArrayList<Marker>();
    ArrayList<Professional> professionals = new ArrayList<Professional>();
    private DatabaseReference mDatabase;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_nearest);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        final List<Marker> markers = new ArrayList<Marker>();
        final String tag[] = {"Cleaners", "Electricians", "Laptop Repairer", "Plumber"};
        for (int i = 0; i <= 3; i++) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child(tag[i]);


            final ChildEventListener childEventListener = mDatabase.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if (dataSnapshot.exists()) {
                        double mlat = Double.parseDouble(dataSnapshot.child("latitude").getValue().toString());
                        double mlon = Double.parseDouble(dataSnapshot.child("longitude").getValue().toString());
                        Professional professional = new Professional(dataSnapshot.child("id").getValue().toString(),
                                dataSnapshot.child("name").getValue().toString(),
                                dataSnapshot.child("phone").getValue().toString(),
                                dataSnapshot.child("address").getValue().toString(),
                                dataSnapshot.child("address2").getValue().toString(),
                                dataSnapshot.child("address3").getValue().toString(),
                                dataSnapshot.child("address4").getValue().toString(),
                                dataSnapshot.child("longitude").getValue().toString(),
                                dataSnapshot.child("latitude").getValue().toString(),
                                dataSnapshot.child("rate").getValue().toString(),
                                dataSnapshot.child("rating").getValue().toString(),
                                dataSnapshot.child("category").getValue().toString(),
                                dataSnapshot.child("district").getValue().toString(),
                                dataSnapshot.child("zone").getValue().toString(),
                                dataSnapshot.child("fburl").getValue().toString(),
                                dataSnapshot.child("voter").getValue().toString()

                        );
                        LatLng point_new = new LatLng(mlat, mlon);
                        drawMarker(point_new, professional);


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

    }

    private void drawMarker(LatLng point, final Professional professional) {
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                System.out.println("ksmdkf===="+professional.getName());

//                showDetail(professional);

                return false;
            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(SearchByNearest.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);

            return;
        }
        Marker marker = mMap.addMarker(new MarkerOptions().position(point).title(professional.getName()).snippet(professional.getCategory()));
        mMap.setMyLocationEnabled(true);


//        mMap.addMarker(new MarkerOptions().position(point).snippet("Marker in "+ name));

        if (professional.getCategory().equalsIgnoreCase("cleaners")) {
            mMap.addMarker(new MarkerOptions().position(point).
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
//            mMap.addMarker(new MarkerOptions().position(point).
//                    icon(BitmapDescriptorFactory.fromResource(R.mipmap.cleaner_icon)));

        } else if (professional.getCategory().equalsIgnoreCase("plumber")) {
            mMap.addMarker(new MarkerOptions().position(point).
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        } else if (professional.getCategory().equalsIgnoreCase("electricians")) {
            mMap.addMarker(new MarkerOptions().position(point).
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        } else {
            mMap.addMarker(new MarkerOptions().position(point).
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 14.0f));


    }

    private void showDetail(final Professional p) {
        final Dialog dialog = new Dialog(SearchByNearest.this);
        dialog.setContentView(R.layout.custom_dialog);

        TextView pname = (TextView) dialog.findViewById(R.id.professional_name);
        TextView paddress = (TextView) dialog.findViewById(R.id.professional_address);
        TextView pcategory = (TextView) dialog.findViewById(R.id.professional_category);

        pname.setText(p.getName());
        paddress.setText(p.getAddress());
        pcategory.setText(p.getCategory());

        ImageView call_image = (ImageView) dialog.findViewById(R.id.call_iconview);
        ImageView message_image = (ImageView) dialog.findViewById(R.id.message_iconview);
        ImageView notify_image = (ImageView) dialog.findViewById(R.id.notification_iconview);
        ImageView email_image = (ImageView) dialog.findViewById(R.id.email_iconview);

        call_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchByNearest.this, "Calling", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + p.getNumber()));

                if (ActivityCompat.checkSelfPermission(SearchByNearest.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SearchByNearest.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }
                startActivity(intent);
            }
        });
        message_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchByNearest.this, "Messaged", Toast.LENGTH_SHORT).show();
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.setData(Uri.parse("sms:" + p.getNumber()));
                startActivity(smsIntent);
            }
        });
        notify_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchByNearest.this, BookingDetail.class);
                i.putExtra("ProfessionalName", p.getName());
                i.putExtra("ProfessionalAddress", p.getAddress());
                i.putExtra("ProfessionalNumber", p.getNumber());
                i.putExtra("ProfessionalLongitude", p.getLongitude());
                i.putExtra("ProfessionalLatitude", p.getLatitude());
                i.putExtra("ProfessionalRate", p.getRate());
                i.putExtra("ProfessionalRating", p.getRating());
                i.putExtra("ProfessionalCategory", p.getCategory());
                i.putExtra("ProfessionalFburl", p.getFburl());



                startActivity(i);
            }
        });
        email_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchByNearest.this, "Emailed", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Information of Work");
                i.putExtra(Intent.EXTRA_TEXT, "To do my home job at certain time period");

                startActivity(i);
            }
        });


        ImageView dialogButton = (ImageView) dialog.findViewById(R.id.cancel);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }


}
