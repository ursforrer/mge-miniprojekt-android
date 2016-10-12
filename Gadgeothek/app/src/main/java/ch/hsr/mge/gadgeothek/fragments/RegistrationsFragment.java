package ch.hsr.mge.gadgeothek.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hsr.mge.gadgeothek.*;

/**
 * Created by Urs Forrer on 10.10.2016.
 */

public class RegistrationsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.reg_fragment, container, false);
        //root.findViewById(R.id.nextButton).setOnClickListener(this);
        //getActivity().setTitle("Registrieren");
        return root;
    }
}
