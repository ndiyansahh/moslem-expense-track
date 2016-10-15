package com.MET.salman.MoeslemExpenseTracker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Albani on 12/9/2015.
 */
public class DataViewHomeAdapter extends ArrayAdapter<getAllData> {

    private Activity context;
    private ArrayList<getAllData> values;

    public DataViewHomeAdapter(Activity context, int layout, ArrayList<getAllData> values) {
        super(context, layout, values);
        this.context = context;
        this.values = values;
    }

//    static class ViewHolder {
//        public TextView DateLine,CategoryLine,NominalLine;
//        public ImageView image;
//    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View transactionlayout = convertView;
        getAllData getAllData = values.get(position);

        // reuse views
//        if (transactionlayout == null) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        transactionlayout = inflater.inflate(R.layout.expense_home_layout, null);
        TextView CategoryLine = (TextView) transactionlayout.findViewById(R.id.CategoryLine);
        TextView NominalLine = (TextView) transactionlayout.findViewById(R.id.NominalLine);
//            transactionlayout.setTag(viewHolder);
//        }

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Young.ttf");
//        ViewHolder holder = (ViewHolder) transactionlayout.getTag();
        Locale IDR = new Locale("in","ID");
        NumberFormat numberFormatIDR = NumberFormat.getCurrencyInstance(IDR);

        CategoryLine.setText(getAllData.getCategory());
        CategoryLine.setTypeface(tf);
        NominalLine.setText(numberFormatIDR.format(getAllData.getExpense()));
        NominalLine.setTypeface(tf);

        if(getAllData.getId().startsWith("I"))
        {
            NominalLine.setTextColor(Color.GREEN);
        }
        else if(getAllData.getId().startsWith("E"))
        {
            NominalLine.setTextColor(Color.RED);
        }

        return transactionlayout;
    }
}

