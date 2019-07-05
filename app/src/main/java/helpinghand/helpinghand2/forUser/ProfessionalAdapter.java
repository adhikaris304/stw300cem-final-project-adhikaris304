package helpinghand.helpinghand2.forUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import helpinghand.helpinghand2.R;



public class ProfessionalAdapter extends ArrayAdapter<Professional> {
    DatabaseReference databaseReference;
    SharedPreferences preferences;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    int mYear;
    int mMonth;
    int mDay;


    Context context;

    public ProfessionalAdapter(Activity context, ArrayList<Professional> words) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.professiona_list_view, parent, false);
        }


        // Get the {@link AndroidFlavor} object located at this position in the list
        final Professional currentProfessional = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.name);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        nameTextView.setText(currentProfessional.getName());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView addressTextView = (TextView) listItemView.findViewById(R.id.address);

        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        addressTextView.setText(currentProfessional.getAddress());

        TextView categoryTextView = (TextView) listItemView.findViewById(R.id.category);

        categoryTextView.setText(currentProfessional.getCategory());


        //FOR RATING
        int ratenumber = Integer.parseInt(currentProfessional.getRating());
        if (ratenumber == 4) {
            listItemView.findViewById(R.id.image_rating5).setVisibility(View.GONE);

        } else if (ratenumber == 3) {
            listItemView.findViewById(R.id.image_rating5).setVisibility(View.GONE);
            listItemView.findViewById(R.id.image_rating4).setVisibility(View.GONE);

        } else if (ratenumber == 2) {
            listItemView.findViewById(R.id.image_rating5).setVisibility(View.GONE);
            listItemView.findViewById(R.id.image_rating4).setVisibility(View.GONE);
            listItemView.findViewById(R.id.image_rating3).setVisibility(View.GONE);


        } else if (ratenumber == 1) {
            listItemView.findViewById(R.id.image_rating5).setVisibility(View.GONE);
            listItemView.findViewById(R.id.image_rating4).setVisibility(View.GONE);
            listItemView.findViewById(R.id.image_rating3).setVisibility(View.GONE);
            listItemView.findViewById(R.id.image_rating2).setVisibility(View.GONE);

        }


        //FOR DIFFERENT IMAGE FOR DIFFERERNT CATEGORY
        if (currentProfessional.getCategory().equalsIgnoreCase("Plumber")) {
            ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
            imageView.setImageResource(R.drawable.plumber_image);

        } else if (currentProfessional.getCategory().equalsIgnoreCase("Cleaner")) {
            ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
            imageView.setImageResource(R.drawable.cleaner_image);
        } else if (currentProfessional.getCategory().equalsIgnoreCase("Laptop Repairer")) {
            ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
            imageView.setImageResource(R.drawable.laptoprepairer_image);

        } else {
            ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
            imageView.setImageResource(R.drawable.electrician_image);

        }

        //FOR ONCLICK EVENT TO EACH ICON

        final ImageView callImageView = (ImageView) listItemView.findViewById(R.id.call_iconview);
        ImageView messageImageView = (ImageView) listItemView.findViewById(R.id.message_iconview);
        ImageView emailImageView = (ImageView) listItemView.findViewById(R.id.email_iconview);
        ImageView notificationImageView = (ImageView) listItemView.findViewById(R.id.notification_iconview);

        callImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Calling", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + currentProfessional.getNumber()));

                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CALL_PHONE}, 101);
                    return;
                }
                context.startActivity(intent);


            }
        });
        messageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Messaged", Toast.LENGTH_SHORT).show();
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.setData(Uri.parse("sms:" + currentProfessional.getNumber()));
                context.startActivity(smsIntent);


            }
        });
        emailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{currentProfessional.getEmail()});
                i.putExtra(Intent.EXTRA_SUBJECT, "Information of Work");
                i.putExtra(Intent.EXTRA_TEXT, "To do my home job at certain time period");

                Toast.makeText(getContext(), "Emailed", Toast.LENGTH_SHORT).show();
                context.startActivity(i);

            }
        });
        notificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), BookingDetail.class);
                i.putExtra("ProfessionalName", currentProfessional.getName());
                i.putExtra("ProfessionalAddress", currentProfessional.getAddress());
                i.putExtra("ProfessionalNumber", currentProfessional.getNumber());
                i.putExtra("ProfessionalLongitude", currentProfessional.getLongitude());
                i.putExtra("ProfessionalLatitude", currentProfessional.getLatitude());
                i.putExtra("ProfessionalRate", currentProfessional.getRate());
                i.putExtra("ProfessionalRating", currentProfessional.getRating());
                i.putExtra("ProfessionalCategory", currentProfessional.getCategory());
                i.putExtra("ProfessionalId", currentProfessional.getId());

                context.startActivity(i);

            }
        });


        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }





}


