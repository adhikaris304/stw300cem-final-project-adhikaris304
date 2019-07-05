package helpinghand.helpinghand2.forUser;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import helpinghand.helpinghand2.R;



public class PlumberFragment extends Fragment {
    private ListView mListView;
    private DatabaseReference mDatabase;
    final ArrayList<Professional> professionals=new ArrayList<Professional>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.plumber_fragment,container,false);
        mListView = (ListView) rootView.findViewById(R.id.list);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Plumber");
        final ChildEventListener childEventListener = mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {

                    Professional professional=new Professional(dataSnapshot.child("id").getValue().toString(),dataSnapshot.child("name").getValue().toString(),
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




                    professionals.add(professional);

                    rootView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                    ProfessionalAdapter adapter = new ProfessionalAdapter(getActivity(), professionals);
                    mListView.setAdapter(adapter);


                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent i =new Intent(getActivity(), Detail_Activity.class);
                            Professional pp=new Professional();
                            pp= professionals.get(position);
                            i.putExtra("ProfessionalId", pp.getId());
                            i.putExtra("ProfessionalName", pp.getName());
                            i.putExtra("ProfessionalAddress", pp.getAddress());
                            i.putExtra("ProfessionalNumber", pp.getNumber());
                            i.putExtra("ProfessionalLongitude", pp.getLongitude());
                            i.putExtra("ProfessionalLatitude", pp.getLatitude());
                            i.putExtra("ProfessionalRate", pp.getRate());
                            i.putExtra("ProfessionalRating", pp.getRating());
                            i.putExtra("ProfessionalCategory", pp.getCategory());
                            i.putExtra("ProfessionalFburl", pp.getFburl());
                            i.putExtra("ProfessionalVoter", pp.getVoter());

                            startActivity(i);
                        }
                    });



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

        ProfessionalAdapter adapter=new ProfessionalAdapter(getActivity(),professionals);
        mListView.setAdapter(adapter);
        return rootView;
    }
}
