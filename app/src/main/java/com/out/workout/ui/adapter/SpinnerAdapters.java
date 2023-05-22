package com.out.workout.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.out.workout.R;

public class SpinnerAdapters extends ArrayAdapter<String> {
    private final Context con;

    public SpinnerAdapters(Context context, int i, String[] strArr) {
        super(context, i, strArr);
        this.con = context;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_spinner, viewGroup, false);
        ((TextView) inflate.findViewById(R.id.TvSpinnerText)).setText(getItem(position));
        return inflate;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_spinner, viewGroup, false);
        System.out.println("------- txtxt : "+getItem(position));
        ((TextView) inflate.findViewById(R.id.TvSpinnerText)).setText(getItem(position));
        return inflate;
    }

}
