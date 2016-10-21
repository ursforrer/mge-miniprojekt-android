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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        root.findViewById(R.id.buttonLoginScreen).setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick (View v) {

    }

    private void Snack(String message, View v) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
