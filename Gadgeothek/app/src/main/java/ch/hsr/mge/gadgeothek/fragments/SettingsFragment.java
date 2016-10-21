package ch.hsr.mge.gadgeothek.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hsr.mge.gadgeothek.*;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Urs Forrer on 10.10.2016.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener {

    TextInputEditText textInputServer;
    String server;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_fragment, container, false);

        // Click Listener setzen
        root.findViewById(R.id.settingsbutton).setOnClickListener(this);
        // Refernez für Buttonverarbeitung herauslesen
        textInputServer = (TextInputEditText) root.findViewById(R.id.editTextSettings);
        // Aktuelle Serveradresse setzen
        SharedPreferences settings = getActivity().getApplicationContext().getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        textInputServer.setText(settings.getString("server",""));
        //getActivity().setTitle("Einstellungen");
        return root;
    }

    @Override
    public void onClick(View v) {
        // Server auslesen, setzen und zurückmelden, dass der Server gewechselt wurde.
        server = textInputServer.getText().toString();
        LibraryService.setServerAddress(server);
        // Aktuelle Adresse in die SharedPreferences speichern
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = getActivity().getBaseContext().getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putString("server", server);
        editor.commit();
        // Meldung anzeigen, dass die Serveraddresse geändert wurde.
        Snack("Serveraddresse wurde gewechselt.", getView());
    }

    private void Snack(String message, View v) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
