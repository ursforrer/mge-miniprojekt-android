package ch.hsr.mge.gadgeothek.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ch.hsr.mge.gadgeothek.R;
import ch.hsr.mge.gadgeothek.adapter.LoanAdapter;
import ch.hsr.mge.gadgeothek.adapter.ReservationAdapter;
import ch.hsr.mge.gadgeothek.domain.Loan;
import ch.hsr.mge.gadgeothek.domain.Reservation;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;

/**
 * Created by Urs Forrer on 10.10.2016.
 */

public class ReservationFragment extends Fragment {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public ReservationAdapter reservationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.res_fragment, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewReservation);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        reservationAdapter = new ReservationAdapter();

        LibraryService.getReservationsForCustomer(new Callback<List<Reservation>>() {
            @Override
            public void onCompletion(List<Reservation> input) {
                reservationAdapter.setReservationsFromDB(input);
                recyclerView.setAdapter(reservationAdapter);
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
        getActivity().setTitle("Reservations");
    }
}
