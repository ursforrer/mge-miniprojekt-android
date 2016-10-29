package ch.hsr.mge.gadgeothek.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.mge.gadgeothek.R;
import ch.hsr.mge.gadgeothek.adapter.GadgetswithButtonAdapter;
import ch.hsr.mge.gadgeothek.domain.Gadget;
import ch.hsr.mge.gadgeothek.domain.Reservation;
import ch.hsr.mge.gadgeothek.helpers.Helpers;
import ch.hsr.mge.gadgeothek.helpers.SnackMessages;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;
import ch.hsr.mge.gadgeothek.helpers.RecyclerItemClickListener;

/**
 * Created by Urs Forrer on 23.10.2016.
 */

public class GadgetReservationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GadgetswithButtonAdapter gadgetswithButtonAdapter;
    private AppCompatActivity actionBar;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.res_create_fragment, container, false);

        Helpers.updateHeader(getActivity());

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewGadgetReservation);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        gadgetswithButtonAdapter = new GadgetswithButtonAdapter();

        LibraryService.getGadgets(new Callback<List<Gadget>>() {
            @Override
            public void onCompletion(final List<Gadget> inputGadget) {
                // Bereits von mit reservierte Elemente werden nicht mehr angezeigt.
                LibraryService.getReservationsForCustomer(new Callback<List<Reservation>>() {
                    @Override
                    public void onCompletion(List<Reservation> input) {
                        final List<Gadget> gadgetsFiltered = new ArrayList<Gadget>();
                        Boolean isReserved = false;
                        for(Gadget x:inputGadget) {
                            for (Reservation r:input) {
                                if (r.getGadget().getInventoryNumber().equals(x.getInventoryNumber())) {
                                    isReserved = true;
                                }
                            }
                            if(!isReserved) {
                                gadgetsFiltered.add(x);
                            }
                            isReserved = false;
                        }
                        if (gadgetsFiltered.isEmpty()) {
                            TextView text = (TextView) root.findViewById(R.id.no_data_res_create);
                            text.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.INVISIBLE);
                            text.setText("There aren't any gadgets avaiable at this time. Come back later.");
                            text.setTextColor(Color.parseColor("#4fc3f7"));
                        }
                        gadgetswithButtonAdapter.setGadgetsFromDB(gadgetsFiltered);
                        recyclerView.setAdapter(gadgetswithButtonAdapter);
                        // OnClick Aktion, reservieren eines Items
                        recyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override public void onItemClick(View view, final int position) {
                                        LibraryService.reserveGadget(gadgetsFiltered.get(position), new Callback<Boolean>() {
                                            @Override
                                            public void onCompletion(Boolean input) {
                                                if(input) {
                                                    gadgetsFiltered.remove(position);
                                                    gadgetswithButtonAdapter.notifyDataSetChanged();
                                                    getFragmentManager().beginTransaction().replace(R.id.content, new ReservationFragment()).addToBackStack("").commit();
                                                    SnackMessages.Snack("Reservation successfully added", getView());
                                                    Log.d("S", "Reservation successfully added");
                                                }
                                                else {
                                                    SnackMessages.Snack("Already 3 reservation or item is not avaiable", getView());
                                                    Log.d("E", "Already 3 reservation or item is not avaiable");
                                                }
                                            }

                                            @Override
                                            public void onError(String message) {
                                                TextView text = (TextView) root.findViewById(R.id.no_data_res);
                                                text.setVisibility(View.VISIBLE);
                                                recyclerView.setVisibility(View.INVISIBLE);
                                                text.setText("There is a problem with the connection to the server, try again later.");
                                                text.setTextColor(Color.parseColor("#4fc3f7"));
                                            }
                                        });
                                    }
                                })
                        );
                    }

                    @Override
                    public void onError(String message) {
                    }
                });

            }

            @Override
            public void onError(String message) {

            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("Reserve a Gadget");
    }
}
