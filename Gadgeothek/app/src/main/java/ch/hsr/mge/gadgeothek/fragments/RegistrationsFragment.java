package ch.hsr.mge.gadgeothek.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hsr.mge.gadgeothek.*;

/**
 * Created by Urs Forrer on 10.10.2016.
 */

public class RegistrationsFragment extends Fragment implements View.OnClickListener {

    NavigationView navView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.reg_fragment, container, false);
        root.findViewById(R.id.buttonRegister).setOnClickListener(this);
        //getActivity().setTitle("Registrieren");

        navView = (NavigationView) getActivity().findViewById(R.id.navigation_view);

        return root;
    }
    @Override
    public void onClick(View v) {

        // Behandlung der OnClick-Methode hier jeweils in

        switch (v.getId()) {
            case R.id.buttonRegister:
                navView.setCheckedItem(R.id.drawerReg);
                getFragmentManager().beginTransaction().replace(R.id.content, new LoginFragment()).commit();
                break;
        }
    }
}
