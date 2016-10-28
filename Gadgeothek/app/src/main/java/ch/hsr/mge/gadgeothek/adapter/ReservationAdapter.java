package ch.hsr.mge.gadgeothek.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ch.hsr.mge.gadgeothek.R;
import ch.hsr.mge.gadgeothek.domain.Loan;
import ch.hsr.mge.gadgeothek.domain.Reservation;

/**
 * Created by Urs Forrer on 23.10.2016.
 */

public class ReservationAdapter extends RecyclerView.Adapter<HolderReservation> {

    private List<Reservation> reservations = new ArrayList<Reservation>();
    SimpleDateFormat formatter;

    @Override
    public HolderReservation onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.res_layout, parent, false);
        TextView gadgetName = (TextView) v.findViewById(R.id.gadgetName_reservation);
        TextView reservationsDate = (TextView) v.findViewById(R.id.resdate);
        CheckBox isready = (CheckBox) v.findViewById(R.id.checkBoxReady);
        TextView waitingLine = (TextView) v.findViewById(R.id.watingposition);
        HolderReservation holderReservation = new HolderReservation(v, gadgetName, waitingLine, reservationsDate, isready);
        return holderReservation;
    }


    @Override
    public void onBindViewHolder(final HolderReservation holderReservation, int positon) {
        final Reservation reservation = reservations.get(positon);
        holderReservation.gadgetName.setText(reservation.getGadget().getName());
        // Umwandlung des Datums in einen String, damit dieser angezeigt werden kann
        formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date dr = reservation.getReservationDate();
        String r = formatter.format(dr);
        holderReservation.reservationDate.setText(r);
        holderReservation.isReady.setChecked(reservation.isReady());
        holderReservation.waitingPosition.setText(String.valueOf(reservation.getWatingPosition()));
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    // Methode um die Daten vom Server in die Liste zu speichern
    public void setReservationsFromDB(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void remove(int position) {
        reservations.remove(position);
    }

    public Reservation get(int position) {
        return reservations.get(position);
    }

}
