package ch.hsr.mge.gadgeothek.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import ch.hsr.mge.gadgeothek.*;
import ch.hsr.mge.gadgeothek.helpers.Helpers;
import ch.hsr.mge.gadgeothek.helpers.SnackMessages;
import ch.hsr.mge.gadgeothek.service.*;

import static ch.hsr.mge.gadgeothek.helpers.Helpers.setPreferencesOnLogin;

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
        if(LibraryService.isLoggedIn()) {
            Helpers.Logout(getActivity());
        }
        View root = inflater.inflate(R.layout.login_fragment, container, false);
        root.findViewById(R.id.buttonLoginScreen).setOnClickListener(this);

        Helpers.updateHeader(getActivity());

        textInputEditTextMail = (TextInputEditText) root.findViewById(R.id.editTextLoginMail);
        textInputEditTextPassword = (TextInputEditText) root.findViewById(R.id.editTextLoginPassword);

        textInputEditTextMail.addTextChangedListener(mailTextWatcher);
        textInputEditTextPassword.addTextChangedListener(passwordTextWatcher);

        return root;
    }

    @Override
    public void onClick (View v) {
        mail = textInputEditTextMail.getText().toString();
        password = textInputEditTextPassword.getText().toString();

        LibraryService.login(mail, password, new Callback<Boolean>() {
            @Override
            public void onCompletion(Boolean input) {
                if (input) {
                    // Meldung, dass jetzt eingeloggt.
                    SnackMessages.Snack("You are now logged in. Have Fun.", getActivity().getCurrentFocus());

                    // Menu anpassen
                    NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.navigation_view);
                    Helpers.navigationOnLogin(navigationView);

                    // Userdaten abspeichern für die Anzeige im Header
                    setPreferencesOnLogin(getActivity(), mail);

                    // Framgent wechseln
                    getFragmentManager().beginTransaction().replace(R.id.content, new AusleiheFragment()).addToBackStack("").commit();
                }
            }

            @Override
            public void onError(String message) {
                // Fehler anzeigen, wenn Login nicht erfolgreich war.
                SnackMessages.Snack("Login was not successfully." + "\n" + "Error: " + message, getView());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Titel ändnern
        getActivity().setTitle("Login");
    }

    // Validation des Mailfeldes
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

    // Validation des Passwortfeldes
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
