package ch.hsr.mge.gadgeothek.helpers;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Urs Forrer on 28.10.2016.
 */

public class SnackMessages {

    public static void Snack(String message, View v) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
