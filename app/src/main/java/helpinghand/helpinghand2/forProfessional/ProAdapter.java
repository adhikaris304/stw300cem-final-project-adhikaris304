package helpinghand.helpinghand2.forProfessional;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import helpinghand.helpinghand2.Booking;
import helpinghand.helpinghand2.R;



public class ProAdapter extends ArrayAdapter<Booking> {
    Context context;
    private DatabaseReference mDatabase;
    final ArrayList<Booking> bookings = new ArrayList<Booking>();


    public ProAdapter(Activity context, ArrayList<Booking> words) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.propanal_listview, parent, false);
        }


        // Get the {@link AndroidFlavor} object located at this position in the list
        final Booking currentBooking = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.name);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        nameTextView.setText(currentBooking.getUsername());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView addressTextView = (TextView) listItemView.findViewById(R.id.address);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        addressTextView.setText(currentBooking.getUserAddress());

        TextView statusTextView = (TextView) listItemView.findViewById(R.id.status);
        statusTextView.setText(currentBooking.getStatus());


        final String bookingid = currentBooking.getId();

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        TextView hourTextView = (TextView) listItemView.findViewById(R.id.hour);

        dateTextView.setText(currentBooking.getDate());
        timeTextView.setText(currentBooking.getTime());
        hourTextView.setText(currentBooking.getHour());
        //FOR ONCLICK EVENT TO EACH ICON

        ImageView yes = (ImageView) listItemView.findViewById(R.id.yes_iconview);
        ImageView no = (ImageView) listItemView.findViewById(R.id.no_iconviews);
        ImageView call = (ImageView) listItemView.findViewById(R.id.call_iconview);



        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

                alertDialog.setTitle("Do this job?");
                alertDialog.setMessage("Are you sure you want to do this job?");
                alertDialog.setIcon(R.drawable.helpinghand);

                alertDialog.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Do the stuff.
                                DatabaseReference ddd = FirebaseDatabase.getInstance().getReference("booking").child(bookingid);
                                try {

                                    ddd.child("status").setValue("Responded");
                                    Intent refresh = new Intent(getContext(), ProPanal.class);
                                    context.startActivity(refresh);
                                    Toast.makeText(context, "Responded.", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

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
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());
                alertDialog.setTitle("Delete this job?");
                alertDialog.setMessage("Are you sure you want to delete this job?");
                alertDialog.setIcon(R.drawable.helpinghand);

                alertDialog.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Do the stuff..
                                System.out.println("idididid====" + currentBooking.getId());

                                DatabaseReference ddd = FirebaseDatabase.getInstance().getReference("booking").child(bookingid);
                                System.out.println(ddd);
                                final Booking fakebooking = null;

                                ddd.removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                        Toast.makeText(getContext(), "Job Deleted.", Toast.LENGTH_SHORT).show();
                                        Intent refresh = new Intent(getContext(), ProPanal.class);
                                        context.startActivity(refresh);
                                    }
                                });


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
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Calling", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + currentBooking.getUserphone()));

                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CALL_PHONE}, 101);
                    return;
                }
                context.startActivity(intent);
            }
        });


        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }

    private void notifytouser(final int i) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("booking");
        final ChildEventListener childEventListener = mDatabase.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (i == 1) {

                } else if (i == 0) {

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


