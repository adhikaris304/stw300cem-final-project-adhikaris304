package helpinghand.helpinghand2.forUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import helpinghand.helpinghand2.R;

/**
 * Created by Rasil10 on 2017-07-08.
 */

public class User_WelcomeMessageAndroid extends AppCompatActivity {

    RelativeLayout introMessage;
    LinearLayout appContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String registeredUser_name = getIntent().getExtras().getString("UserName");
        final String registeredUser_role = getIntent().getExtras().getString("UserRole");
        final String registeredUser_address = getIntent().getExtras().getString("UserAddress");
        final String registeredUser_email = getIntent().getExtras().getString("UserEmail");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_welcome_message_android);

        introMessage = (RelativeLayout) findViewById(R.id.welcome_message_layout);

        Button dismiss_btn=(Button)findViewById(R.id.dismisWelcomeMessageBox);
        dismiss_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introMessage.setVisibility(View.INVISIBLE);
                Intent i = new Intent(User_WelcomeMessageAndroid.this, User_MainActivity.class);
                i.putExtra("UserName",registeredUser_name);
                i.putExtra("UserEmail",registeredUser_email);
                i.putExtra("UserRole",registeredUser_role);
                i.putExtra("UserAddress",registeredUser_address);
                startActivity(i);

            }
        });
    }

    public void dismisWelcomeMessageBox(View view) {


    }
}