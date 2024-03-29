package ch.hsr.mge.gadgeothek.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import ch.hsr.mge.gadgeothek.R;
import ch.hsr.mge.gadgeothek.adapter.LoanAdapter;
import ch.hsr.mge.gadgeothek.domain.Loan;
import ch.hsr.mge.gadgeothek.helpers.Helpers;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;

/**
 * Created by Urs Forrer on 10.10.2016.
 */

public class AusleiheFragment extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public LoanAdapter loanAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.loan_fragment, container, false);

        Helpers.updateHeader(getActivity());

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        loanAdapter = new LoanAdapter();
        recyclerView.setAdapter(loanAdapter);

        LibraryService.getLoansForCustomer(new Callback<List<Loan>>() {
            @Override
            public void onCompletion(List<Loan> input) {
                if (input.isEmpty()) {
                    loanAdapter.setLoansFromDB(input);
                    loanAdapter.notifyDataSetChanged();
                    TextView text = (TextView) root.findViewById(R.id.no_data_loan);
                    text.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    text.setText("You don't have any loans at this time. Go to the Gadgeothek at your schoool to loan a device.");
                    text.setTextColor(Color.parseColor("#4fc3f7"));

                }
                else {
                    loanAdapter.setLoansFromDB(input);
                    recyclerView.setAdapter(loanAdapter);
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

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("My Loans");
    }
}
