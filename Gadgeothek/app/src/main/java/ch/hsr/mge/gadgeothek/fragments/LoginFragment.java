package ch.hsr.mge.gadgeothek.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import ch.hsr.mge.gadgeothek.*;
import ch.hsr.mge.gadgeothek.service.*;

/**
 * Created by Urs Forrer on 10.10.2016.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {

    private View view;
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
                    Snack("Sie sind nun eingeloggt. Viel Spass.",getView());
                    getFragmentManager().beginTransaction().replace(R.id.content, new SettingsFragment()).commit();
                    //Snack("Logged in",getView());
                }
                else {
                    // Passwort oder Benutzer falsch bzw. nicht gefunden
                    // Wie soll das da funktionieren, diese Methode wird gar nie aufgerufen.
                    Snack("Passwort falsch", getView());
                }
            }

            @Override
            public void onError(String message) {
                // Fehler
                Snack("Keine Verbindung zum Server :-) !!!", getView());
            }
        });
    }

    private void Snack(String message, View v) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
