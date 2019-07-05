package helpinghand.helpinghand2.forUser;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import helpinghand.helpinghand2.Booking;
import helpinghand.helpinghand2.R;



public class UserPanal extends AppCompatActivity {
    private DatabaseReference mDatabase;
    final ArrayList<Booking> bookings = new ArrayList<Booking>();
    SharedPreferences preferences;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_userpanal);
        preferences = getSharedPreferences("loginInfo", 0);
        System.out.println("pppp-----" + preferences.getString("token", ""));
        final ListView mListView = (ListView) findViewById(R.id.list);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("booking");

        final ChildEventListener childEventListener = mDatabase.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    Booking booking = new Booking(dataSnapshot.child("id").getValue().toString(), dataSnapshot.child("username").getValue().toString(),
                            dataSnapshot.child("professionalname").getValue().toString(),
                            dataSnapshot.child("userAddress").getValue().toString(),
                            dataSnapshot.child("userphone").getValue().toString(),
                            dataSnapshot.child("status").getValue().toString(),
                            dataSnapshot.child("date").getValue().toString(),
                            dataSnapshot.child("hour").getValue().toString(),
                            dataSnapshot.child("time").getValue().toString()

                    );


                    if (booking.getUsername().equals(preferences.getString("UserName", ""))) {
                        bookings.add(booking);

                    }
                }
                System.out.println("no of booking---" + bookings.size());

                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                    UserBookingAdapter adapter = new UserBookingAdapter(UserPanal.this, bookings);
                    mListView.setAdapter(adapter);




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
