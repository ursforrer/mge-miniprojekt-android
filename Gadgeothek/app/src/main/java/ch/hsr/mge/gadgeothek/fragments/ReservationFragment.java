package ch.hsr.mge.gadgeothek.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ch.hsr.mge.gadgeothek.R;
import ch.hsr.mge.gadgeothek.adapter.LoanAdapter;
import ch.hsr.mge.gadgeothek.adapter.ReservationAdapter;
import ch.hsr.mge.gadgeothek.domain.Loan;
import ch.hsr.mge.gadgeothek.domain.Reservation;
import ch.hsr.mge.gadgeothek.helpers.Helpers;
import ch.hsr.mge.gadgeothek.helpers.SnackMessages;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;

/**
 * Created by Urs Forrer on 10.10.2016.
 */

public class ReservationFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ReservationAdapter reservationAdapter;
    private FloatingActionButton addbutton;
    private Paint p = new Paint();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.res_fragment, container, false);

        Helpers.updateHeader(getActivity());

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewReservation);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        reservationAdapter = new ReservationAdapter();
        recyclerView.setAdapter(reservationAdapter);

        LibraryService.getReservationsForCustomer(new Callback<List<Reservation>>() {
            @Override
            public void onCompletion(List<Reservation> input) {
                if (input.isEmpty()) {
                    TextView text = (TextView) root.findViewById(R.id.no_data_res);
                    text.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    text.setText("You don't have any reservations at this time. Use the button at the bottom right, to add a new one.");
                    text.setTextColor(Color.parseColor("#4fc3f7"));
                }
                reservationAdapter.setReservationsFromDB(input);
                reservationAdapter.notifyDataSetChanged();
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

        addbutton = (FloatingActionButton) root.findViewById(R.id.floatingActionButton);
        addbutton.setOnClickListener(this);
        reservationAdapter.notifyDataSetChanged();
        initSwipe();
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingActionButton:
                //navView.setCheckedItem(R.id.drawerLogin);
                getFragmentManager().beginTransaction().replace(R.id.content, new GadgetReservationsFragment()).addToBackStack("").commit();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("My Reservations");
    }

    // Code stammt teilweise aus der Quelle von learn2crack.com
    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
                    Reservation reservation = reservationAdapter.get(position);
                    LibraryService.deleteReservation(reservation, new Callback<Boolean>() {
                        @Override
                        public void onCompletion(Boolean input) {
                            reservationAdapter.remove(viewHolder.getAdapterPosition());
                            reservationAdapter.notifyDataSetChanged();
                            SnackMessages.Snack("The reservation was deleted.", getView());
                        }

                        @Override
                        public void onError(String message) {
                            SnackMessages.Snack("The reservation was not deleted, please try again", getView());
                        }
                    });
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){

                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white_48dp);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
