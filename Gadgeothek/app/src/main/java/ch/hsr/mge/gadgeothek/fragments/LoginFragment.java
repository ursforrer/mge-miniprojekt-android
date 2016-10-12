package ch.hsr.mge.gadgeothek.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import ch.hsr.mge.gadgeothek.*;
import ch.hsr.mge.gadgeothek.service.*;

/**
 * Created by Urs Forrer on 10.10.2016.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_fragment, container, false);
        //root.findViewById(R.id.nextButton).setOnClickListener(this);
        //getActivity().setTitle("Login");
        return root;
    }

    @Override
    public void onClick (View v) {
        TextInputEditText textInputEditTextMail = (TextInputEditText) v.findViewById(R.id.editTextLoginMail);
        String mail = textInputEditTextMail.getText().toString();

        TextInputEditText textInputEditTextPassword = (TextInputEditText) v.findViewById(R.id.editTextLoginPassword);
        String password = textInputEditTextPassword.getText().toString();

    }
}
