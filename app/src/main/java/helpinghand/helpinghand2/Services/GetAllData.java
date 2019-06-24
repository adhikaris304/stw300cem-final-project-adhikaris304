package helpinghand.helpinghand2.Services;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import helpinghand.helpinghand2.forUser.Professional;

/**
 * Created by Rasil10 on 2017-07-11.
 */

public class GetAllData {
    DatabaseReference mDatabase;

    public ArrayList<Professional> getallProfessional(){
        final ArrayList<Professional> professionals = new ArrayList<Professional>();
        Professional professional = new Professional();
        String tag[] = {"Cleaners", "Electricians", "Laptop Repairer", "Plumber"};
        for (int i = 0; i <= 3; i++) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child(tag[i]);

            final ChildEventListener childEventListener = mDatabase.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if (dataSnapshot.exists()) {
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
                        System.out.println("name========"+ dataSnapshot.child("name").getValue().toString());



                            professionals.add(professional);


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




        return professionals;


    }

}
