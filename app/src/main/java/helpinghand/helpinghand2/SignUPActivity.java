package helpinghand.helpinghand2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import helpinghand.helpinghand2.Services.RegisterUser1;
import helpinghand.helpinghand2.forUser.User;




public class SignUPActivity extends AppCompatActivity {
    private EditText name_ET,  phone_ET, email_ET, password1_ET, password2_ET;
    AutoCompleteTextView address_ET;
    Button btn_singup;
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    String[] locations = {"lazimpat", "thamel", "baluwatar", "naxal", "kanti", "bhatbhateni", "gongabhu",
            "bagmati", "kathmandu", "Khursanitar"

    };
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        address_ET = (AutoCompleteTextView)findViewById(R.id.input_address);
        ArrayAdapter adapter = new ArrayAdapter(SignUPActivity.this, android.R.layout.select_dialog_item, locations);
        address_ET.setThreshold(1);
        address_ET.setAdapter(adapter);


        TextView gotologin=(TextView)findViewById(R.id.link_login);
        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignUPActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        btn_singup = (Button) findViewById(R.id.btn_signup);
        btn_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_ET = (EditText)findViewById(R.id.input_name);
                final String user_name = name_ET.getText().toString().trim();

                final String user_address = address_ET.getText().toString().trim();

                email_ET = (EditText) findViewById(R.id.input_email);
                final String user_email = email_ET.getText().toString().trim();

                password1_ET = (EditText) findViewById(R.id.input_password);
                final String user_password1 = password1_ET.getText().toString().trim();

                password2_ET = (EditText) findViewById(R.id.input_password);
                final String user_password2 = password2_ET.getText().toString().trim();

                phone_ET = (EditText) findViewById(R.id.input_mobile);
                final String user_phone = phone_ET.getText().toString().trim();

                if ((!TextUtils.isEmpty(user_name)) &&
                        (!TextUtils.isEmpty(user_address)) &&
                        (!TextUtils.isEmpty(user_email)) &&
                        (!TextUtils.isEmpty(user_password1)) &&
                        (!TextUtils.isEmpty(user_password2)) &&
                        (!TextUtils.isEmpty(user_phone))) {

                    String user_role="u";
                    String user_id=FirebaseDatabase.getInstance().getReference("Users").push().getKey();
                    User user = new User(user_id,user_name, user_address, user_email, user_phone, user_password1,user_role);



                    RegisterUser1 registerUser = new RegisterUser1(user.getEmail(), user.getPassword(), SignUPActivity.this);


                    databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                    databaseReference.child(user_id ).setValue(user);
                    System.out.println("Email ======"+user.getEmail());
//                    Toast.makeText(SignUPActivity.this, "Please Login Now..", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(SignUPActivity.this,LoginActivity.class);
                    startActivity(i);

                } else {
                    Toast.makeText(SignUPActivity.this, "Field is empty!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void createUser(User user) {




    }
}
