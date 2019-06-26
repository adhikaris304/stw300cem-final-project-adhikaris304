package helpinghand.helpinghand2.forUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import helpinghand.helpinghand2.Booking;
import helpinghand.helpinghand2.R;

public class BookingDetail extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    Button show_btn;
    static final int START_TIME_ID = 0;
    static final int END_TIME_ID = 1;
    int day, month, year, hour, minute;
    DatabaseReference databaseReference;
    SharedPreferences preferences;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        final String professionalName = getIntent().getExtras().getString("ProfessionalName");
        final String professionalAddress = getIntent().getExtras().getString("ProfessionalAddress");
        final String professionalphone = getIntent().getExtras().getString("ProfessionalNumber");
        final String professionalLongitude = getIntent().getExtras().getString("ProfessionalLongitude");
        final String professionalLatitude = getIntent().getExtras().getString("ProfessionalLatitude");
        final String professionalRate = getIntent().getExtras().getString("ProfessionalRate");
        final String professionalcategory = getIntent().getExtras().getString("ProfessionalCategory");
        final String professionalFburl = getIntent().getExtras().getString("ProfessionalFburl");
        final String professionalId = getIntent().getExtras().getString("ProfessionalId");


        TextView pname = (TextView) findViewById(R.id.professional_name);
        TextView paddress = (TextView) findViewById(R.id.professional_address);
        TextView pcategory = (TextView) findViewById(R.id.professional_category);
        Button showdate = (Button) findViewById(R.id.date_btn);
        Button showtime1 = (Button) findViewById(R.id.startingtime_btn);
        Button noofHours = (Button) findViewById(R.id.noof_hours_btn);
        Button book = (Button) findViewById(R.id.book);


        pname.setText(professionalName);
        paddress.setText(professionalAddress);
        pcategory.setText(professionalcategory);

        showdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });
        showtime1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                selectTime();

            }
        });
        noofHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectNumber();
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView date_TV=(TextView)findViewById(R.id.date_textView);
                final TextView time_TV=(TextView)findViewById(R.id.startingtime_textview);
                final TextView hour_TV=(TextView)findViewById(R.id.noof_hours);

                if((date_TV.getText().toString().length()>0)&&(time_TV.getText().toString().length()>0)&&
                                                (hour_TV.getText().toString().length()>0)){
                    final Professional professional = new Professional(professionalName, professionalAddress, professionalphone,
                            professionalLatitude, professionalLongitude, professionalRate,professionalFburl);
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

                    alertDialog.setTitle("Notify this Professional?");
                    alertDialog.setMessage("Are you sure you want to book this Professional?");
                    alertDialog.setIcon(R.drawable.helpinghand);

                    alertDialog.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do the stuff..
                                    preferences = getSharedPreferences("loginInfo", 0);
                                    String username = preferences.getString("UserName", "");
                                    String professionalname = professional.getName();
                                    String userphone = preferences.getString("UserPhone", "");
                                    String useraddress = preferences.getString("UserAddress", "");
                                    String userId = preferences.getString("UserId", "");
                                    String status = "Not Responded";



                                    String date= (String) date_TV.getText();
                                    String time= (String) time_TV.getText();
                                    String hour= (String) hour_TV.getText();


                                    final Booking booking = new Booking(userId, username, professionalname, useraddress, userphone,
                                            status,date,time,hour);

                                    addtopanal(professional,booking);


                                    NotificationCompat.Builder mBuilder =
                                            new NotificationCompat.Builder(BookingDetail.this);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/"));

                                    PendingIntent pendingIntent = PendingIntent.getActivity(BookingDetail.this, 0, intent, 0);

                                    mBuilder.setContentIntent(pendingIntent);

                                    mBuilder.setSmallIcon(R.drawable.helpinghand);
                                    mBuilder.setContentTitle("Helping Hand Notification");
                                    mBuilder.setContentText("Booking for " + professional.getName());

                                    NotificationManager mNotificationManager =

                                            (NotificationManager) BookingDetail.this.getSystemService(Context.NOTIFICATION_SERVICE);

                                    mNotificationManager.notify(001, mBuilder.build());

                                    Toast.makeText(BookingDetail.this, "Notified", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            }
                    );
                    alertDialog.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do the stuff..
                                }
                            }
                    );


                    alertDialog.show();
                }

                else{
                    Toast.makeText(BookingDetail.this,"Select Date and Time.",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void addtopanal(Professional professional, Booking booking) {
        preferences = getSharedPreferences("loginInfo", 0);
        String username = preferences.getString("UserName", "");
        String professionalname = professional.getName();
        String userphone = preferences.getString("UserPhone", "");
        String useraddress = preferences.getString("UserAddress", "");
        String userId = preferences.getString("UserId", "");
        String status = "Not Responded";

        String bookingid = FirebaseDatabase.getInstance().getReference("booking").push().getKey();

        Booking b=new Booking(bookingid,username,professionalname,useraddress,userphone,status,booking.getDate(),booking.getTime(),booking.getHour());

        databaseReference = FirebaseDatabase.getInstance().getReference("booking");

        databaseReference.child(bookingid).setValue(b);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void selectTime() {
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(BookingDetail.this, BookingDetail.this, hour, minute, true);
        timePickerDialog.setTitle("Select Starting Time.");

        timePickerDialog.show();

    }

    private void selectNumber() {
        final Dialog d = new Dialog(BookingDetail.this);
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.numberpicker_dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(10); // max value 10
        np.setMinValue(1);   // min value 0
        np.setWrapSelectorWheel(false);
//        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.noof_hours_btn).setVisibility(View.GONE);
                TextView tv = (TextView) findViewById(R.id.noof_hours);

                tv.setText(String.valueOf(np.getValue())); //set the value to textview
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss(); // dismiss the dialog
            }
        });
        d.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void selectDate() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(BookingDetail.this, BookingDetail.this, year, month, day);

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        findViewById(R.id.date_btn).setVisibility(View.GONE);
        TextView date_textview = (TextView) findViewById(R.id.date_textView);
        date_textview.setText(year + "/" + month + "/" + dayOfMonth);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        findViewById(R.id.startingtime_btn).setVisibility(View.GONE);
        TextView date_textview1 = (TextView) findViewById(R.id.startingtime_textview);
        date_textview1.setText(hourOfDay + ":" + minute);


    }
}
