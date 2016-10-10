package ch.hsr.mge.gadgeothek.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ch.hsr.mge.gadgeothek.*;

/**
 * Created by Urs Forrer on 10.10.2016.
 */

public class StartFragment extends Fragment implements View.OnClickListener {
    private View.OnClickListener activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.start_fragment, container, false);
        //root.findViewById(R.id.nextButton).setOnClickListener(this);
        return root;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if (activity instanceof View.OnClickListener) {
            this.activity = (View.OnClickListener) activity;
        }
        else {
            throw new AssertionError("Activity must implement View.OnClickListener");
        }
    }

    @Override
    public void onClick(View view) {
        activity.onClick(view);
    }
}
