package helpinghand.helpinghand2.Services;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import helpinghand.helpinghand2.R;
import helpinghand.helpinghand2.forUser.Professional;

/**
 * Created by Rasil10 on 2017-07-28.
 */

public class CustomDialogClass extends Dialog implements
        View.OnClickListener {
TextView pname=(TextView)findViewById(R.id.professional_name);
    public Activity c;
    public Dialog d;
    public Button yes, no;

    public CustomDialogClass(Activity a, final Professional professional) {

        super(a);
        // TODO Auto-generated constructor stub
        pname.setText(professional.getName());
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);


    }

    @Override
    public void onClick(View v) {

        dismiss();
    }
}
