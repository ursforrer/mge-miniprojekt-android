package ch.hsr.mge.gadgeothek.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.hsr.mge.gadgeothek.R;
import ch.hsr.mge.gadgeothek.domain.Gadget;

/**
 * Created by Urs Forrer on 23.10.2016.
 */

public class GadgetswithButtonAdapter extends RecyclerView.Adapter<HolderGadgetswithButton> {

    private List<Gadget> gadgets = new ArrayList<Gadget>();
    SimpleDateFormat formatter;

    @Override
    public HolderGadgetswithButton onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.gadgetswithbutton_layout, parent, false);
        TextView gadgetName = (TextView) v.findViewById(R.id.gadgetName_NewReservation);
        TextView inventoryNumber = (TextView) v.findViewById(R.id.InventoryNumber_NewReservation);
        TextView condition = (TextView) v.findViewById(R.id.Condition_NewReservation);
        TextView manufacturer = (TextView) v.findViewById(R.id.Manufacturer_NewReservation);
        HolderGadgetswithButton holderGadgetswithButton = new HolderGadgetswithButton(v, gadgetName, inventoryNumber, condition, manufacturer);
        return holderGadgetswithButton;
    }


    @Override
    public void onBindViewHolder(final HolderGadgetswithButton holderGadgetswithButton, int positon) {
        final Gadget gadget = gadgets.get(positon);
        holderGadgetswithButton.gadgetName.setText(gadget.getName());
        holderGadgetswithButton.inventoryNumber.setText(gadget.getInventoryNumber());
        holderGadgetswithButton.manufacturer.setText(gadget.getManufacturer());
        holderGadgetswithButton.condition.setText(gadget.getCondition().toString());
    }

    @Override
    public int getItemCount() {
        return gadgets.size();
    }

    // Methode um die Daten vom Server in die Liste zu speichern
    public void setGadgetsFromDB(List<Gadget> gadgets) {
        this.gadgets = gadgets;
    }
}
