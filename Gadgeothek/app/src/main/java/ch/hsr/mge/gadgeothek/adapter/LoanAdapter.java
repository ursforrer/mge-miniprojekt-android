package ch.hsr.mge.gadgeothek.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.mge.gadgeothek.R;
import ch.hsr.mge.gadgeothek.domain.Loan;

/**
 * Created by Urs Forrer on 22.10.2016.
 */

public class LoanAdapter extends RecyclerView.Adapter<HolderLoan> {

    private List<Loan> loans = new ArrayList<Loan>();

    @Override
    public HolderLoan onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.loadlayout, parent, false);
        TextView textView = (TextView) v.findViewById(R.id.textView3);
        HolderLoan holderLoan = new HolderLoan(v, textView);
        return holderLoan;
    }

    @Override
    public void onBindViewHolder(final HolderLoan holderLoan, int positon) {
        final Loan loan = loans.get(positon);
        holderLoan.gadgetName.setText(loan.getGadget().getName());
        System.out.println(loan.getGadget().getName());
    }

    @Override
    public int getItemCount() {
        return loans.size();
    }

    public void setLoansFromDB(List<Loan> loans) {
        this.loans = loans;
        for (Loan x:this.loans) {
            System.out.println(x.getGadget().getName());
        }
    }
}
