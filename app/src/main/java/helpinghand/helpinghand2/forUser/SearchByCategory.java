package helpinghand.helpinghand2.forUser;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import helpinghand.helpinghand2.R;

/**
 * Created by Rasil10 on 2017-07-04.
 */

public class SearchByCategory extends Fragment {
    LinearLayout cleaner_ll,plumber_ll,laptoprepairer_ll,electrician_ll;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.search_by_category_fragment, container, false);


        cleaner_ll=(LinearLayout)rootView.findViewById(R.id.cleaner_it);
        plumber_ll=(LinearLayout)rootView.findViewById(R.id.plumber_it);
        laptoprepairer_ll=(LinearLayout)rootView.findViewById(R.id.laptoprepairer_it);
        electrician_ll=(LinearLayout)rootView.findViewById(R.id.electrician_it);

        cleaner_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.main_frame,new CleanerFragment()).commit();
            }
        });
        electrician_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.main_frame,new ElectricianFragment()).commit();
            }
        });
        plumber_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.main_frame,new PlumberFragment()).commit();
            }
        });
        laptoprepairer_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.main_frame,new LaptopRepairerFragment()).commit();
            }
        });



        return rootView;

    }
}
