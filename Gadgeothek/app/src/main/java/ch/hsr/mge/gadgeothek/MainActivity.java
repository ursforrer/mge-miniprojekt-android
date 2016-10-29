package ch.hsr.mge.gadgeothek;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.style.LineBackgroundSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ch.hsr.mge.gadgeothek.fragments.*;
import ch.hsr.mge.gadgeothek.helpers.Helpers;
import ch.hsr.mge.gadgeothek.helpers.SnackMessages;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private View content;
    private FragmentManager fragmentManager;
    SharedPreferences settings;
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        content = findViewById(R.id.content);

        // ActionBar mit einer Toolbar ersetzen
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Toolbar anpassen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // SharedPerferences laden und speichern
        settings = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        LibraryService.setServerAddress(settings.getString("server", "http://mge1.dev.ifs.hsr.ch/public"));
        if (settings.getString("server","").equals(""))  {
            SharedPreferences.Editor editor;
            editor = settings.edit();
            editor.putString("server", "http://mge1.dev.ifs.hsr.ch/public");
            editor.commit();
        }

        // Verhindern, dass mehrere FragmentManager gestartet werden.
        fragmentManager = getFragmentManager();
        if(fragmentManager.getBackStackEntryCount() == 0) {
            getFragmentManager().beginTransaction().add(R.id.content, new StartFragment()).commit();
        }

        // Menu anpassen, jenachdem ob man eingeloggt ist oder nicht.
        if(LibraryService.isLoggedIn()) {
            navigationView.getMenu().findItem(R.id.drawerLogin).setVisible(false);
            navigationView.getMenu().findItem(R.id.drawerReg).setVisible(false);
            navigationView.getMenu().findItem(R.id.drawerStart).setVisible(false);
            navigationView.getMenu().findItem(R.id.drawerSettings).setVisible(false);
        }
        else {
            navigationView.getMenu().findItem(R.id.drawerRes).setVisible(false);
            navigationView.getMenu().findItem(R.id.drawerLoan).setVisible(false);
            navigationView.getMenu().findItem(R.id.drawerLogout).setVisible(false);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        switch (menuItem.getItemId()) {
            case R.id.drawerStart:
                switchFragment(new StartFragment());
                break;
            case R.id.drawerLogin:
                switchFragment(new LoginFragment());
                break;
            case R.id.drawerLoan:
                switchFragment(new AusleiheFragment());
                break;
            case R.id.drawerRes:
                switchFragment(new ReservationFragment());
                break;
            case R.id.drawerReg:
                switchFragment(new RegistrationsFragment());
                break;
            case R.id.drawerSettings:
                switchFragment(new SettingsFragment());
                break;
            case R.id.drawerLogout:
                LibraryService.logout(new Callback<Boolean>() {
                    private View content = findViewById(R.id.content);
                    @Override
                    public void onCompletion(Boolean input) {
                        SnackMessages.Snack("You are now logged out. See you again.",content);
                    }

                    @Override
                    public void onError(String message) {
                        // Fehler
                        SnackMessages.Snack("Logout was not successfully." + "\n" + "Error: " + message,content);
                    }
                });

                // Menu aktualisieren
                NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
                Helpers.navigationOnLogout(navigationView);

                // Temporäre Userdaten wieder löschen
                Helpers.setPreferencesOnLogout(getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE));

                // Drawerheader aktualisieren
                View header = navigationView.getHeaderView(0);
                TextView mail = (TextView) header.findViewById(R.id.mail_avatar);
                TextView name = (TextView) header.findViewById(R.id.name_avatar);
                mail.setText(settings.getString("mail",""));
                name.setText(settings.getString("status",""));

                // Damit nicht mehr auf eingeloggte Seiten zugeriffen werden kann
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                switchFragment(new StartFragment());
                break;
        }
        drawer.closeDrawers();
        return true;
    }

    public void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

     @Override
    public void onStop() {
         super.onStop();
         Helpers.setPreferencesOnLogout(getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE));
     }
}
