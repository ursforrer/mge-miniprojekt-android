package ch.hsr.mge.gadgeothek.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ch.hsr.mge.gadgeothek.R;
import ch.hsr.mge.gadgeothek.domain.Loan;

/**
 * Created by Urs Forrer on 22.10.2016.
 */

public class LoanAdapter extends RecyclerView.Adapter<HolderLoan> {

    private List<Loan> loans = new ArrayList<Loan>();
    SimpleDateFormat formatter;

    @Override
    public HolderLoan onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.loadlayout, parent, false);
        TextView gadget = (TextView) v.findViewById(R.id.gadgetName);
        TextView pickupdate = (TextView) v.findViewById(R.id.pickupdate);
        TextView returndate = (TextView) v.findViewById(R.id.returndate);
        HolderLoan holderLoan = new HolderLoan(v, gadget, pickupdate, returndate);
        return holderLoan;
    }

    @Override
    public void onBindViewHolder(final HolderLoan holderLoan, int positon) {
        final Loan loan = loans.get(positon);
        holderLoan.gadgetName.setText(loan.getGadget().getName());
        // Umwandlung des Datums in einen String, damit dieser angezeigt werden kann
        formatter = new SimpleDateFormat("dd.MM.yyyy");

        // Berechung des Return-Date, da diese Daten nicht mitgegeben werden.
        Date dp = loan.getPickupDate();
        Calendar c = Calendar.getInstance();
        c.setTime(dp);
        c.add(Calendar.DATE, 7);  // number of days to add
        String r = (String)(formatter.format(c.getTime()));

        String p = formatter.format(dp);
        holderLoan.pickupDate.setText(p);
        holderLoan.returnDate.setText(r);
    }

    @Override
    public int getItemCount() {
        return loans.size();
    }

    // Methode um die Daten vom Server in die Liste zu speichern
    public void setLoansFromDB(List<Loan> loans) {
        this.loans = loans;
    }
}
