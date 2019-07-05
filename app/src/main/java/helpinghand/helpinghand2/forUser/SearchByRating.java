package helpinghand.helpinghand2.forUser;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

public class SearchByRating extends Fragment {
    ArrayList<Professional> professionals = new ArrayList<Professional>();
    private RadioGroup radiostartgroup;
    private RadioButton radiostarbutton;
    private ListView mListView;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.search_by_rating_fragment, container, false);

        mListView = (ListView) rootView.findViewById(R.id.list);

        radiostartgroup = (RadioGroup) rootView.findViewById(R.id.radioStar);
        radiostartgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                professionals.clear();

                final int star;
                final int selected = radiostartgroup.getCheckedRadioButtonId();
                radiostarbutton = (RadioButton) rootView.findViewById(selected);
                professionals.clear();

                final ArrayList<Professional> professionals = new ArrayList<>();
                Professional professional = new Professional();
                String tag[] = {"Cleaners", "Electricians", "Laptop Repairer", "Plumber"};
                for (int i = 0; i <= 3; i++) {
                    mDatabase = FirebaseDatabase.getInstance().getReference().child(tag[i]);

                    final ChildEventListener childEventListener = mDatabase.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                            if (dataSnapshot.exists()) {


                                if ((dataSnapshot.child("rating").getValue().toString()).equalsIgnoreCase((String) radiostarbutton.getText())) {
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
                                    System.out.println(professionals.size());

                                }
                            }

                            if (professionals.size() == 0) {
//                                Toast.makeText(getActivity(), "No Result Found..", Toast.LENGTH_SHORT).show();

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
                System.out.println("Before Return--------" + professionals.size());

            }
        });


        return rootView;
    }

    public ArrayList<Professional> getProfessionalByRating(final int rating) {


        return professionals;
    }
}

