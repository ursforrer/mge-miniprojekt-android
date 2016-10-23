package ch.hsr.mge.gadgeothek.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Urs Forrer on 23.10.2016.
 */

public class HolderReservation extends RecyclerView.ViewHolder {

    public View parent;
    public TextView gadgetName;
    public TextView waitingPosition;
    public TextView reservationDate;
    public CheckBox isReady;

    public HolderReservation(View parent, TextView gadgetName, TextView waitingPosition, TextView reservationDate, CheckBox isReady) {
        super(parent);
        this.parent = parent;
        this.gadgetName = gadgetName;
        this.waitingPosition = waitingPosition;
        this.reservationDate = reservationDate;
        this.isReady = isReady;
    }

}
