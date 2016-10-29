package ch.hsr.mge.gadgeothek.helpers;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ch.hsr.mge.gadgeothek.R;
import ch.hsr.mge.gadgeothek.fragments.StartFragment;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;

/**
 * Created by Urs Forrer on 28.10.2016.
 */

public class Helpers {

    public static void updateHeader(Activity activity) {
        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.navigation_view);
        View header = navigationView.getHeaderView(0);
        TextView mail = (TextView) header.findViewById(R.id.mail_avatar);
        TextView name = (TextView) header.findViewById(R.id.name_avatar);
        SharedPreferences settings = activity.getApplicationContext().getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        mail.setText(settings.getString("mail",""));
        name.setText(settings.getString("status",""));
    }

    public static void Logout(final Activity activity) {
        LibraryService.logout(new Callback<Boolean>() {
            private View content = activity.findViewById(R.id.content);
            @Override
            public void onCompletion(Boolean input) {
                SharedPreferences settings;
                SharedPreferences.Editor editor;
                settings = activity.getApplicationContext().getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
                editor = settings.edit();
                editor.putString("mail", "");
                editor.putString("status", "Logged out");
                editor.commit();

                activity.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                activity.getFragmentManager().beginTransaction().replace(R.id.content, new StartFragment()).addToBackStack("").commit();
                SnackMessages.Snack("You are now logged out. A stack is also clear, because the sites shouldent be accessible in Logged in Mode.",content);
            }

            @Override
            public void onError(String message) {
                // Fehler
                SnackMessages.Snack("Logout was not successfully." + "\n" + "Error: " + message,content);
                Log.d("S", "Helper");
            }
        });
    }

    public static void navigationOnLogin(NavigationView navigationView) {
        navigationView.getMenu().findItem(R.id.drawerRes).setVisible(true);
        navigationView.getMenu().findItem(R.id.drawerLoan).setVisible(true);
        navigationView.getMenu().findItem(R.id.drawerLogout).setVisible(true);
        navigationView.getMenu().findItem(R.id.drawerLogin).setVisible(false);
        navigationView.getMenu().findItem(R.id.drawerReg).setVisible(false);
        navigationView.getMenu().findItem(R.id.drawerStart).setVisible(false);
        navigationView.getMenu().findItem(R.id.drawerSettings).setVisible(false);
    }

    public static void navigationOnLogout(NavigationView navigationView) {
        navigationView.getMenu().findItem(R.id.drawerRes).setVisible(false);
        navigationView.getMenu().findItem(R.id.drawerLoan).setVisible(false);
        navigationView.getMenu().findItem(R.id.drawerLogout).setVisible(false);
        navigationView.getMenu().findItem(R.id.drawerLogin).setVisible(true);
        navigationView.getMenu().findItem(R.id.drawerReg).setVisible(true);
        navigationView.getMenu().findItem(R.id.drawerStart).setVisible(true);
        navigationView.getMenu().findItem(R.id.drawerSettings).setVisible(true);
    }

    public static void setPreferencesOnLogin(Activity activity, String mail) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = activity.getBaseContext().getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString("mail", mail);
        editor.putString("status", "Logged in");
        editor.commit();
    }

    public static void setPreferencesOnLogout(SharedPreferences settings) {
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putString("mail", "");
        editor.putString("status", "Logged out");
        editor.commit();
    }
 }
