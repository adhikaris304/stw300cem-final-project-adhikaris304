package helpinghand.helpinghand2.Services;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import helpinghand.helpinghand2.forUser.User;


public class RegisterUser1 {
    DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


   public RegisterUser1(String email, String password, final Activity activity){

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override

            public void onComplete(@NonNull Task<AuthResult> task) {
                System.out.println("Authentication tab is here");
                if (task.isSuccessful()) {

                    Toast.makeText(activity, "Registration Successfull", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(activity, "Registration Failed. Please Try Again...", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
    public RegisterUser1(User user){

//        firebaseAuth=FirebaseAuth.getInstance();
//        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
//            @Override
//
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                System.out.println("Authentication tab is here");
//                if (task.isSuccessful()) {
//
//                    Toast.makeText(activity, "Registration Successfull", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(activity, "Registration Failed. Please Try Again...", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        String id = databaseReference.push().getKey();
        databaseReference.child(id).setValue(user);
        System.out.println("Email ======"+user.getEmail());

    }


}
