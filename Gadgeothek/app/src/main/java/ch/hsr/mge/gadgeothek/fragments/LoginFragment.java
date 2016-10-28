package ch.hsr.mge.gadgeothek.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import ch.hsr.mge.gadgeothek.*;
import ch.hsr.mge.gadgeothek.service.*;

/**
 * Created by Urs Forrer on 10.10.2016.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {

    TextInputEditText textInputEditTextMail;
    TextInputEditText textInputEditTextPassword;
    String mail;
    String password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_fragment, container, false);
        root.findViewById(R.id.buttonLoginScreen).setOnClickListener(this);
        textInputEditTextMail = (TextInputEditText) root.findViewById(R.id.editTextLoginMail);
        textInputEditTextPassword = (TextInputEditText) root.findViewById(R.id.editTextLoginPassword);

        textInputEditTextMail.addTextChangedListener(mailTextWatcher);
        textInputEditTextPassword.addTextChangedListener(passwordTextWatcher);

        return root;
    }

    @Override
    public void onClick (View v) {
        //textInputEditTextMail = (TextInputEditText) v.findViewById(R.id.editTextLoginMail);
        mail = textInputEditTextMail.getText().toString();

        //textInputEditTextPassword = (TextInputEditText) v.findViewById(R.id.editTextLoginPassword);
        password = textInputEditTextPassword.getText().toString();

        LibraryService.login(mail, password, new Callback<Boolean>() {
            @Override
            public void onCompletion(Boolean input) {
                if (input) {
                    // Logged in
                    Snack("You are now logged in. Have Fun.", getActivity().getCurrentFocus());
                    NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.navigation_view);
                    navigationView.getMenu().findItem(R.id.drawerRes).setVisible(true);
                    navigationView.getMenu().findItem(R.id.drawerLoan).setVisible(true);
                    navigationView.getMenu().findItem(R.id.drawerLogout).setVisible(true);
                    navigationView.getMenu().findItem(R.id.drawerLogin).setVisible(false);
                    navigationView.getMenu().findItem(R.id.drawerReg).setVisible(false);
                    navigationView.getMenu().findItem(R.id.drawerStart).setVisible(false);
                    navigationView.getMenu().findItem(R.id.drawerSettings).setVisible(false);
                    getFragmentManager().beginTransaction().replace(R.id.content, new AusleiheFragment()).addToBackStack("").commit();
                }
            }

            @Override
            public void onError(String message) {
                // Fehler
                Snack("Login was not successfully." + "\n" + "Error: " + message, getView());
            }
        });
    }

    private void Snack(String message, View v) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("Login");
    }

    private TextWatcher mailTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!textInputEditTextMail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                textInputEditTextMail.setError("Invalid Email Address");
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }
    };

    private TextWatcher passwordTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(textInputEditTextPassword.getText().length() < 8) {
                textInputEditTextPassword.setError("Password to short");
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }
    };
}
