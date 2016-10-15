package com.MET.salman.MoeslemExpenseTracker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Salman on 12/2/2015.
 */
public class DataViewAdapter extends ArrayAdapter<getAllData> {

    private Activity context;
    private ArrayList<getAllData> values;


    public DataViewAdapter(Activity context, int layout, ArrayList<getAllData> values) {
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
            transactionlayout = inflater.inflate(R.layout.transactionlayout, null);
            // configure view holder
//            ViewHolder viewHolder = new ViewHolder();
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Young.ttf");

        TextView DateLine = (TextView) transactionlayout.findViewById(R.id.DateLine);
        TextView CategoryLine = (TextView) transactionlayout.findViewById(R.id.CategoryLine);
        TextView NominalLine = (TextView) transactionlayout.findViewById(R.id.NominalLine);
        ImageView image = (ImageView) transactionlayout.findViewById(R.id.ImageView1);
//            transactionlayout.setTag(viewHolder);
//        }

//        ViewHolder holder = (ViewHolder) transactionlayout.getTag();
        Locale IDR = new Locale("in","ID");
        NumberFormat numberFormatIDR = NumberFormat.getCurrencyInstance(IDR);

        DateLine.setTypeface(tf);
        CategoryLine.setTypeface(tf);
        NominalLine.setTypeface(tf);

        DateLine.setText(getAllData.getDate());
        CategoryLine.setText(getAllData.getCategory());
        NominalLine.setText(numberFormatIDR.format(getAllData.getExpense()));

        if(getAllData.getId().startsWith("I"))
        {
            NominalLine.setTextColor(Color.parseColor("#EB5244"));
        }
        else if(getAllData.getId().startsWith("E"))
        {
            NominalLine.setTextColor(Color.parseColor("#FCAE28"));
        }


//        if (getAllData.getCategory().startsWith("Food") || getAllData.getCategory().startsWith("Drink")
//                || getAllData.getCategory().startsWith("Fuel")) {
//            image.setImageResource(R.drawable.home);
//        } else {
//            image.setImageResource(R.drawable.setting);
//        }

        if (getAllData.getCategory().startsWith("Food & Beverages"))
        {
            image.setImageResource(R.drawable.food);
        }
        else if(getAllData.getCategory().startsWith("Clothes"))
        {
            image.setImageResource(R.drawable.clothes);
        }
        else if(getAllData.getCategory().startsWith("Education"))
        {
            image.setImageResource(R.drawable.education);
        }
        else if(getAllData.getCategory().startsWith("Fuel"))
        {
            image.setImageResource(R.drawable.fuel);
        }
        else if(getAllData.getCategory().startsWith("Auto"))
        {
            image.setImageResource(R.drawable.auto);

        } else if(getAllData.getCategory().startsWith("Transportation"))
        {
            image.setImageResource(R.drawable.transportation);

        } else if(getAllData.getCategory().startsWith("Entertainment"))
        {
            image.setImageResource(R.drawable.entertainment);

        } else if(getAllData.getCategory().startsWith("House"))
        {
            image.setImageResource(R.drawable.house);

        } else if(getAllData.getCategory().startsWith("Groceries"))
        {
            image.setImageResource(R.drawable.groceries);

        } else if(getAllData.getCategory().startsWith("Health"))
        {
            image.setImageResource(R.drawable.health);

        } else if(getAllData.getCategory().startsWith("Gadget"))
        {
            image.setImageResource(R.drawable.gadget);

        } else if(getAllData.getCategory().startsWith("Sports"))
        {
            image.setImageResource(R.drawable.sport);

        } else if(getAllData.getCategory().startsWith("Travel"))
        {
            image.setImageResource(R.drawable.travelling);
        }
        else if(getAllData.getCategory().startsWith("Allowance"))
        {
            image.setImageResource(R.drawable.allowance);
        }
        else if(getAllData.getCategory().startsWith("Bonus"))
        {
            image.setImageResource(R.drawable.bonus);
        }
        else
        {
            image.setImageResource(R.drawable.general);
        }

        return transactionlayout;
    }
}
