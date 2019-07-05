package helpinghand.helpinghand2.forUser;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import helpinghand.helpinghand2.R;

/**
 * Created by Rasil10 on 2017-07-07.
 */

public class SearchByLocation extends Fragment {
    // ArrayList<String> locationalist = new ArrayList<String>();
    String[] locations = {"lazimpat","baluwatar", "naxal", "kanti", "bhatbhateni", "gongabhu","dhumbarai","balaju",
            "bagmati", "kathmandu", "Khursanitar","gaididhara","maharajgunj","samakhusi","lainchaur","putalisadak"
    };
    AutoCompleteTextView actv;
    private ListView mListView;
    private DatabaseReference mDatabase;
    ArrayList<Professional> professionals = new ArrayList<Professional>();
    private EditText editText;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.search_by_location_fragment, container, false);

        //  locationalist.add("lazimpat");
        // locationalist.add("thamel");
        //locationalist.add("baluwatar");

        actv = (AutoCompleteTextView) rootView.findViewById(R.id.search_box);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.select_dialog_item, locations);
        actv.setThreshold(1);
        actv.setAdapter(adapter);


        mListView = (ListView) rootView.findViewById(R.id.list);
        imageView = (ImageView) rootView.findViewById(R.id.search_image_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                professionals.clear();
                rootView.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);


                editText = (EditText) rootView.findViewById(R.id.search_box);
                final String inputAddress = editText.getText().toString();
                if (inputAddress.equals(null)) {
                    Toast.makeText(getActivity(), "Search Field is Empty..", Toast.LENGTH_LONG).show();
                } else {

                    String tag[] = {"Cleaners", "Electricians", "Laptop Repairer", "Plumber"};
                    for (int i = 0; i <= 3; i++) {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child(tag[i]);


                        final ChildEventListener childEventListener = mDatabase.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                if (dataSnapshot.exists()) {

                                    if ((inputAddress.equalsIgnoreCase(dataSnapshot.child("address").getValue().toString())) ||
                                            (inputAddress.equalsIgnoreCase(dataSnapshot.child("district").getValue().toString())) ||
                                            (inputAddress.equalsIgnoreCase(dataSnapshot.child("zone").getValue().toString())) ||
                                            (inputAddress.equalsIgnoreCase(dataSnapshot.child("address2").getValue().toString())) ||
                                            (inputAddress.equalsIgnoreCase(dataSnapshot.child("address3").getValue().toString())) ||
                                            (inputAddress.equalsIgnoreCase(dataSnapshot.child("address4").getValue().toString())) ||
                                            (inputAddress.equalsIgnoreCase(dataSnapshot.child("address").getValue().toString()))) {
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
                                        professionals.add(professional);

                                    }
                                }

                                if (professionals.size() == 0) {

                                } else {

                                    ProfessionalAdapter adapter = new ProfessionalAdapter(getActivity(), professionals);
                                    rootView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                                    mListView.setAdapter(adapter);
                                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            Intent i = new Intent(getActivity(), Detail_Activity.class);
                                            Professional pp = new Professional();
                                            pp = professionals.get(position);
                                            i.putExtra("ProfessionalId", pp.getId());
                                            i.putExtra("ProfessionalName", pp.getName());
                                            i.putExtra("ProfessionalAddress", pp.getAddress());
                                            i.putExtra("ProfessionalNumber", pp.getNumber());
                                            i.putExtra("ProfessionalLongitude", pp.getLongitude());
                                            i.putExtra("ProfessionalLatitude", pp.getLatitude());
                                            i.putExtra("ProfessionalRate", pp.getRate());
                                            i.putExtra("ProfessionalRating", pp.getRating());
                                            i.putExtra("ProfessionalFburl", pp.getFburl());
                                            i.putExtra("ProfessionalVoter", pp.getVoter());
                                            i.putExtra("ProfessionalCategory", pp.getCategory());

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

                    }
                }

            }
        });


        return rootView;
    }

    private void performSearch() {

    }


}
