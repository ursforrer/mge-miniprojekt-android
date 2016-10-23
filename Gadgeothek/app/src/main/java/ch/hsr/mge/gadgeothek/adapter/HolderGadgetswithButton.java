package ch.hsr.mge.gadgeothek.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Urs Forrer on 23.10.2016.
 */

public class HolderGadgetswithButton extends RecyclerView.ViewHolder {

    public View parent;
    public TextView gadgetName;
    public TextView inventoryNumber;
    public TextView condition;
    public TextView manufacturer;

    public HolderGadgetswithButton(View parent, TextView gadgetName, TextView inventoryNumber, TextView condition, TextView manufacturer) {
        super(parent);
        this.parent = parent;
        this.gadgetName = gadgetName;
        this.inventoryNumber = inventoryNumber;
        this.condition = condition;
        this.manufacturer = manufacturer;
    }
}
