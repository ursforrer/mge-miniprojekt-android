package ch.hsr.mge.gadgeothek.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hsr.mge.gadgeothek.*;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;

/**
 * Created by Urs Forrer on 10.10.2016.
 */

public class RegistrationsFragment extends Fragment implements View.OnClickListener {

    NavigationView navView;
    TextInputEditText textInputEditTextMail;
    TextInputEditText textInputEditTextMartikelnummer;
    TextInputEditText textInputEditTextName;
    TextInputEditText textInputEditTextPassword;

    String name, number, mail, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.reg_fragment, container, false);
        root.findViewById(R.id.buttonRegister).setOnClickListener(this);
        //getActivity().setTitle("Registrieren");

        textInputEditTextMail = (TextInputEditText) root.findViewById(R.id.editTextMail);
        textInputEditTextMartikelnummer = (TextInputEditText) root.findViewById(R.id.editTextNumber);
        textInputEditTextName = (TextInputEditText) root.findViewById(R.id.editTextName);
        textInputEditTextPassword = (TextInputEditText) root.findViewById(R.id.editTextPassword);

        navView = (NavigationView) getActivity().findViewById(R.id.navigation_view);

        return root;
    }
    @Override
    public void onClick(View v) {
        name = textInputEditTextName.getText().toString();
        number = textInputEditTextMartikelnummer.getText().toString();
        mail = textInputEditTextMail.getText().toString();
        password = textInputEditTextPassword.getText().toString();

        LibraryService.register(mail, password, name, number, new Callback<Boolean>() {
            @Override
            public void onCompletion(Boolean input) {
                Snack("Registered sucessfully", getView());
                getFragmentManager().beginTransaction().replace(R.id.content, new LoginFragment()).addToBackStack("").commit();
            }

            @Override
            public void onError(String message) {
                Snack("Registration was not successfully." + "\n" + "Error: " + message, getView());
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
        getActivity().setTitle("Register");
    }
}
