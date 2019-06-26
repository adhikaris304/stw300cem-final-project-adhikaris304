package helpinghand.helpinghand2.forUser;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import helpinghand.helpinghand2.Booking;
import helpinghand.helpinghand2.R;
import helpinghand.helpinghand2.Services.FbCommentsActivity;

public class Detail_Activity extends AppCompatActivity {

    DatabaseReference databaseReference;
    SharedPreferences preferences;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String professionalId = getIntent().getExtras().getString("ProfessionalId");
        final String professionalName = getIntent().getExtras().getString("ProfessionalName");
        final String professionalAddress = getIntent().getExtras().getString("ProfessionalAddress");
        final String professionalphone = getIntent().getExtras().getString("ProfessionalNumber");
        final String professionalLongitude = getIntent().getExtras().getString("ProfessionalLongitude");
        final String professionalLatitude = getIntent().getExtras().getString("ProfessionalLatitude");
        final String professionalRate = getIntent().getExtras().getString("ProfessionalRate");
        final String professionalRating = getIntent().getExtras().getString("ProfessionalRating") ;
        final String professionalCategory = getIntent().getExtras().getString("ProfessionalCategory");
        final String professionalFburl = getIntent().getExtras().getString("ProfessionalFburl");
        final String professionalVoter = getIntent().getExtras().getString("ProfessionalVoter");

        System.out.println(professionalCategory+"sdlfmsd");
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Contact will be made to"+ professionalName, Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                Intent smsIntent = new Intent(Intent.ACTION_MAIN);
//                smsIntent.addCategory(smsIntent.CATEGORY_DEFAULT);
//
//                smsIntent.setType("vnd.android-dir/mms-sms");
////                smsIntent.putExtra("address",professionalphone);
////                smsIntent.putExtra("sms_body","Body of Message");
//                startActivity(smsIntent);
//            }
//        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }


        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        TextView rate_TV = (TextView) findViewById(R.id.professional_rate);
        TextView name_TV1 = (TextView) findViewById(R.id.professional_name1);
        TextView name_TV2 = (TextView) findViewById(R.id.professional_name2);
        TextView address_TV1 = (TextView) findViewById(R.id.professional_address1);
        TextView address_TV2 = (TextView) findViewById(R.id.professional_address2);
        TextView category_TV2 = (TextView) findViewById(R.id.professional_category2);

        ImageView rating_IV2 = (ImageView) findViewById(R.id.professional_rating2);
        Button rating_submit= (Button)findViewById(R.id.rating_submit_btn);
        final RatingBar ratingBar=(RatingBar)findViewById(R.id.ratingbar);

        LinearLayout call_iv = (LinearLayout) findViewById(R.id.call_detail_icon);
        LinearLayout message_iv = (LinearLayout) findViewById(R.id.message_detail_icon);
        LinearLayout notify_iv = (LinearLayout) findViewById(R.id.notification_detail_icon);
        LinearLayout email_iv = (LinearLayout) findViewById(R.id.email_detail_icon);

        LinearLayout facebook_iv = (LinearLayout) findViewById(R.id.facebook_detail_icon);
        LinearLayout viber_iv = (LinearLayout) findViewById(R.id.viber_detail_icon);
        LinearLayout whatsapp_iv = (LinearLayout) findViewById(R.id.whatsapp_detail_icon);


        call_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Detail_Activity.this, "Calling", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + professionalphone));

                if (ActivityCompat.checkSelfPermission(Detail_Activity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Detail_Activity.this, new String[]{android.Manifest.permission.CALL_PHONE}, 101);
                    return;
                }
                startActivity(intent);

            }
        });

        message_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Detail_Activity.this, "Messaged", Toast.LENGTH_SHORT).show();
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.setData(Uri.parse("sms:" + professionalphone));
                startActivity(smsIntent);

            }
        });

        email_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Detail_Activity.this, "Emailed", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Information of Work");
                i.putExtra(Intent.EXTRA_TEXT, "To do my home job at certain time period");

                startActivity(i);
            }
        });

        notify_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Detail_Activity.this, BookingDetail.class);
                i.putExtra("ProfessionalName", professionalName);
                i.putExtra("ProfessionalAddress", professionalAddress);
                i.putExtra("ProfessionalNumber", professionalphone);
                i.putExtra("ProfessionalLongitude", professionalLongitude);
                i.putExtra("ProfessionalLatitude", professionalLatitude);
                i.putExtra("ProfessionalRate", professionalRate);
                i.putExtra("ProfessionalRating", professionalRating);
                i.putExtra("ProfessionalCategory", professionalCategory);

                startActivity(i);
            }
        });

        rating_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ratingBar.getRating();
                int voters=Integer.parseInt(professionalVoter);
                int old_rating=Integer.parseInt(professionalRating);
                int new_rating= (int) ratingBar.getRating();
                new_rating=(new_rating+old_rating)/(voters+1);
                DatabaseReference ddd = FirebaseDatabase.getInstance().getReference(professionalCategory).child(professionalId);

                try {

//                    ddd.child("rating").setValue(new_rating);
                    ddd.child("rating").setValue(new_rating);
                    ddd.child("voter").setValue(voters+1);
                    Toast.makeText(Detail_Activity.this, "Rating Submitted.", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        name_TV1.setText(professionalName);
        name_TV2.setText(professionalName);

        address_TV1.setText(professionalAddress);
        address_TV2.setText(professionalAddress);

        category_TV2.setText(professionalCategory);

        rate_TV.setText(professionalRate+ "/ Hour");

        if(professionalRating.equalsIgnoreCase("1"))
            rating_IV2.setImageResource(R.drawable.rating1);
        else if(professionalRating.equalsIgnoreCase("2"))
            rating_IV2.setImageResource(R.drawable.rating2);
        else if(professionalRating.equalsIgnoreCase("3"))
            rating_IV2.setImageResource(R.drawable.rating3);
        else if(professionalRating.equalsIgnoreCase("4"))
            rating_IV2.setImageResource(R.drawable.rating4);
        else if(professionalRating.equalsIgnoreCase("5"))
            rating_IV2.setImageResource(R.drawable.rating5);




        Button goto_map_btn = (Button) findViewById(R.id.professional_goto_map);
        goto_map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Professional pp = new Professional(professionalLongitude, professionalLatitude);

                Intent i = new Intent(Detail_Activity.this, MapsActivity_forUser.class);
                i.putExtra("ProfessionalLongitude", professionalLongitude);
                i.putExtra("ProfessionalAddres", professionalAddress);
                i.putExtra("ProfessionalLatitude", professionalLatitude);

                i.putExtra("ProfessionalName", professionalName);
                startActivity(i);
            }
        });

        facebook_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Detail_Activity.this);
                alertDialog.setTitle("FEEDBACK");
                alertDialog.setMessage("Give Us Feedback.");

                final EditText input = new EditText(Detail_Activity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setIcon(R.drawable.helpinghand);

                alertDialog.setPositiveButton("Send",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                // Write your code here to execute after dialog
                                String feedback=input.getText().toString();

                                Toast.makeText(getApplicationContext(),"Thank Your For Your Feedback.", Toast.LENGTH_SHORT).show();

                            }
                        });
                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });

                alertDialog.show();


            }
        });

        whatsapp_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_Activity.this, FbCommentsActivity.class);

// passing the article url
//                intent.putExtra("url", "https://www.androidhive.info/2016/06/android-firebase-integrate-analytics/");
                intent.putExtra("url", professionalFburl);

                startActivity(intent);
                Toast.makeText(Detail_Activity.this, "Comments on "+professionalName +".", Toast.LENGTH_SHORT).show();

            }
        });

        viber_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Detail_Activity.this, "Viber Contact here!!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, ElectricianFragment.class);

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addtopanal(Professional p, Booking booking) {
        preferences = getSharedPreferences("loginInfo", 0);
        String username = preferences.getString("UserName", "");
        String professionalname = p.getName();
        String userphone = preferences.getString("UserPhone", "");
        String useraddress = preferences.getString("UserAddress", "");
        String userId = preferences.getString("UserId", "");
        String status = "Not Responded";

        String bookingid = FirebaseDatabase.getInstance().getReference("booking").push().getKey();



        databaseReference = FirebaseDatabase.getInstance().getReference("booking");

        databaseReference.child(bookingid).setValue(booking);


    }


}
