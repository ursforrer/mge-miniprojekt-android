package ch.hsr.mge.gadgeothek.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hsr.mge.gadgeothek.R;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;

/**
 * Created by Urs Forrer on 21.10.2016.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    NavigationView navView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        root.findViewById(R.id.imageAusleihe).setOnClickListener(this);
        root.findViewById(R.id.imageReservation).setOnClickListener(this);
        root.findViewById(R.id.textHomeAusleihen).setOnClickListener(this);
        root.findViewById(R.id.textHomeReservation).setOnClickListener(this);

        navView = (NavigationView) getActivity().findViewById(R.id.navigation_view);

        return root;
    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.textHomeAusleihen:
            case R.id.imageAusleihe:
                navView.setCheckedItem(R.id.drawerLoan);
                getFragmentManager().beginTransaction().replace(R.id.content, new AusleiheFragment()).addToBackStack("").commit();
                break;
            case R.id.textHomeReservation:
            case R.id.imageReservation:
                navView.setCheckedItem(R.id.drawerRes);
                getFragmentManager().beginTransaction().replace(R.id.content, new ReservationFragment()).addToBackStack("").commit();
                break;
        }
    }

    private void Snack(String message, View v) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("Home");
    }

}
