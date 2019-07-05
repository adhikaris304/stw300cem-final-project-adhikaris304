package helpinghand.helpinghand2.forProfessional;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import helpinghand.helpinghand2.Booking;
import helpinghand.helpinghand2.R;



public class Professional_FragmentMain extends Fragment {
    private DatabaseReference mDatabase;
    final ArrayList<Booking> bookings = new ArrayList<Booking>();
    SharedPreferences preferences;
Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.content_professional_panal__main, container, false);

        preferences=this.getActivity().getSharedPreferences("loginInfo",0);
        System.out.println("pppp-----"+preferences.getString("token",""));
        final ListView mListView = (ListView) rootView.findViewById(R.id.list);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("booking");
        final ChildEventListener childEventListener = mDatabase.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.exists()) {
                    Booking booking = new Booking(dataSnapshot.child("id").getValue().toString(),dataSnapshot.child("username").getValue().toString(),
                            dataSnapshot.child("professionalname").getValue().toString(),
                            dataSnapshot.child("userAddress").getValue().toString(),
                            dataSnapshot.child("userphone").getValue().toString(),
                            dataSnapshot.child("status").getValue().toString(),
                            dataSnapshot.child("date").getValue().toString(),
                            dataSnapshot.child("time").getValue().toString(),
                            dataSnapshot.child("hour").getValue().toString()
                    );


                    if (booking.getProfessionalname().equals(preferences.getString("UserName", ""))) {
                        bookings.add(booking);

                    }
                }

                    rootView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                    ProAdapter adapter = new ProAdapter(getActivity(), bookings);
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
        return rootView;
    }
}
