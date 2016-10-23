package ch.hsr.mge.gadgeothek.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.mge.gadgeothek.R;
import ch.hsr.mge.gadgeothek.adapter.GadgetswithButtonAdapter;
import ch.hsr.mge.gadgeothek.adapter.ReservationAdapter;
import ch.hsr.mge.gadgeothek.domain.Gadget;
import ch.hsr.mge.gadgeothek.domain.Reservation;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;

/**
 * Created by Urs Forrer on 23.10.2016.
 */

public class GadgetReservationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GadgetswithButtonAdapter gadgetswithButtonAdapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.res_create_fragment, container, false);

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
                        List<Gadget> gadgetsFiltered = new ArrayList<Gadget>();
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
                        gadgetswithButtonAdapter.setGadgetsFromDB(gadgetsFiltered);
                        recyclerView.setAdapter(gadgetswithButtonAdapter);
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
