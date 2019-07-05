package helpinghand.helpinghand2.forUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import helpinghand.helpinghand2.Booking;
import helpinghand.helpinghand2.R;

/**
 * Created by Rasil10 on 2017-07-10.
 */

public class UserBookingAdapter extends ArrayAdapter<Booking> {
    Context context;
    private UserBookingAdapter adapter;
    private DatabaseReference mDatabase;
    final ArrayList<Booking> bookings = new ArrayList<Booking>();


    public UserBookingAdapter(Activity context, ArrayList<Booking> words) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        this.context = context;
        this.adapter = this;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.user_booking_list_view, parent, false);
        }

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.deletejob);

        // Get the {@link AndroidFlavor} object located at this position in the list
        final Booking currentuserBooking = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.name);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        nameTextView.setText(currentuserBooking.getProfessionalname());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView statusTextView = (TextView) listItemView.findViewById(R.id.status);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        statusTextView.setText(currentuserBooking.getStatus());
        dateTextView.setText(currentuserBooking.getDate());
        timeTextView.setText(currentuserBooking.getTime()+" ("+currentuserBooking.getHour()+ " Hours )");

        final String bookingid = currentuserBooking.getId();


        imageView.setOnClickListener(new View.OnClickListener() {
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
                                // Do the stuff.
                                DatabaseReference ddd = FirebaseDatabase.getInstance().getReference("booking").child(bookingid);
                                System.out.println(ddd);

                                ddd.removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                        Toast.makeText(getContext(), "Job Deleted.", Toast.LENGTH_SHORT).show();
                                        adapter.notifyDataSetChanged();
                                        ((Activity)context).finish();
                                        Intent refresh = new Intent(getContext(), UserPanal.class);
                                        context.startActivity(refresh);
                                    }
                                });
                                adapter.notifyDataSetChanged();

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


        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }


}


