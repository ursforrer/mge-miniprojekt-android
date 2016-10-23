package ch.hsr.mge.gadgeothek.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ch.hsr.mge.gadgeothek.*;

/**
 * Created by Urs Forrer on 10.10.2016.
 */

public class StartFragment extends Fragment implements View.OnClickListener {

    NavigationView navView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.start_fragment, container, false);
        root.findViewById(R.id.loginbutton_start).setOnClickListener(this);
        root.findViewById(R.id.regbutton_start).setOnClickListener(this);
        root.findViewById(R.id.textView2).setOnClickListener(this);

        navView = (NavigationView) getActivity().findViewById(R.id.navigation_view);

        return root;
    }

    @Override
    public void onClick(View v) {
        // Behandlung der OnClick-Methode hier jeweils in
        switch (v.getId()) {
            case R.id.loginbutton_start:
                navView.setCheckedItem(R.id.drawerLogin);
                getFragmentManager().beginTransaction().replace(R.id.content, new LoginFragment()).addToBackStack("").commit();
                break;
            case R.id.regbutton_start:
                navView.setCheckedItem(R.id.drawerReg);
                getFragmentManager().beginTransaction().replace(R.id.content, new RegistrationsFragment()).addToBackStack("").commit();
                break;
            case R.id.textView2:
                navView.setCheckedItem(R.id.drawerSettings);
                getFragmentManager().beginTransaction().replace(R.id.content, new SettingsFragment()).addToBackStack("").commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("Welcome");
    }
}
