package ch.hsr.mge.gadgeothek;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.Stack;

import ch.hsr.mge.gadgeothek.fragments.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private View content;
    private FragmentManager fragmentManager;
    private Stack<Integer> pages = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        content = findViewById(R.id.content);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navigationView.getMenu().findItem(R.id.drawerLoan).setVisible(false);
        navigationView.getMenu().findItem(R.id.drawerRes).setVisible(false);


        fragmentManager = getFragmentManager();
        switchFragment(new StartFragment());
        pages.push(1);
        setTitle("Willkommen!");
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        switch (menuItem.getItemId()) {
            case R.id.drawerHome:
                getFragmentManager().beginTransaction().replace(R.id.content, new StartFragment()).commit();
                break;
            case R.id.drawerLogin:
                getFragmentManager().beginTransaction().replace(R.id.content, new LoginFragment()).commit();
                break;
            case R.id.drawerReg:
                getFragmentManager().beginTransaction().replace(R.id.content, new RegistrationsFragment()).commit();
                break;
            case R.id.drawerSettings:
                getFragmentManager().beginTransaction().replace(R.id.content, new SettingsFragment()).commit();
        }
        drawer.closeDrawers();
        return true;
    }

    public void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, fragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }
}
