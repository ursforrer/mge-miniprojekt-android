package ch.hsr.mge.gadgeothek.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Urs Forrer on 22.10.2016.
 */

public class HolderLoan extends RecyclerView.ViewHolder {
    public View parent;
    public TextView gadgetName;
    public TextView pickupDate;
    public TextView returnDate;

    public HolderLoan(View parent, TextView gadgetName, TextView pickupDate, TextView returnDate) {
        super(parent);
        this.parent = parent;
        this.gadgetName = gadgetName;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
    }
}
