package ch.hsr.mge.gadgeothek;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Stack;

import ch.hsr.mge.gadgeothek.fragments.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout drawer;
    private FragmentManager fragmentManager;
    private Stack<Integer> pages = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();
        switchFragment(new StartFragment());
        pages.push(1);
    }

    @Override
    public void onClick(View v) {
        // Behandlung, welches Fragment als nächstest Geöffnet werden soll
        int test = 1;
        switch (test) {
            default:
                break;
        }
    }

    public void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, fragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }
}
