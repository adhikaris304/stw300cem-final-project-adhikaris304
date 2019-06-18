package helpinghand.helpinghand2.forProfessional;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import helpinghand.helpinghand2.R;

/**
 * Created by Rasil10 on 2017-07-09.
 */

public class Professional_WelcomeMessageAndroid extends AppCompatActivity {
    RelativeLayout introMessage;
    LinearLayout appContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.professional_welcome_message_android);
        introMessage = (RelativeLayout) findViewById(R.id.welcome_message_layout);

        Button dismiss_btn=(Button)findViewById(R.id.dismisWelcomeMessageBox);
        dismiss_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introMessage.setVisibility(View.INVISIBLE);
                Intent i = new Intent(Professional_WelcomeMessageAndroid.this, ProPanal.class);

                startActivity(i);

            }
        });
    }
}
