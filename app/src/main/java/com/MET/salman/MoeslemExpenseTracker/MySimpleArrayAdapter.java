package com.MET.salman.MoeslemExpenseTracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Salman on 11/13/2015.
 */
public class MySimpleArrayAdapter extends ArrayAdapter<String>
{
    private Context context;
    private String[] values;

    public MySimpleArrayAdapter(Context context, String[] values) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

//        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/DENSE-REGULAR.OTF");

        TextView textView = (TextView) rowView.findViewById(R.id.label);
//        textView.setTypeface(tf);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(values[position]);
//
//        "Food", "Drink", "Fuel",
//                "General", "Tickets", "Clothes", "Entertainment", "Gifts",
//                "Holiday", "Kids", "Shopping", "Sports", "Travel"

        // Change the icon for Windows and iPhone
        String s = values[position];
        if (s.startsWith("Food & Beverages"))
        {
            imageView.setImageResource(R.drawable.food);
        }
        else if(s.startsWith("Clothes"))
        {
            imageView.setImageResource(R.drawable.clothes);
        }
        else if(s.startsWith("Education"))
        {
            imageView.setImageResource(R.drawable.education);
        }
        else if(s.startsWith("Fuel"))
        {
            imageView.setImageResource(R.drawable.fuel);
        }
        else if(s.startsWith("Auto"))
        {
            imageView.setImageResource(R.drawable.auto);
        }
        else if(s.startsWith("Transportation"))
        {
            imageView.setImageResource(R.drawable.transportation);
        }
        else if(s.startsWith("Entertainment"))
        {
            imageView.setImageResource(R.drawable.entertainment);
        }
        else if(s.startsWith("House"))
        {
            imageView.setImageResource(R.drawable.house);
        }
        else if(s.startsWith("Groceries"))
        {
            imageView.setImageResource(R.drawable.groceries);
        }
        else if(s.startsWith("Health"))
        {
            imageView.setImageResource(R.drawable.health);
        }
        else if(s.startsWith("Gadget"))
        {
            imageView.setImageResource(R.drawable.gadget);
        }
        else if(s.startsWith("Sports"))
        {
            imageView.setImageResource(R.drawable.sport);
        }
        else if(s.startsWith("Travel"))
        {
            imageView.setImageResource(R.drawable.travelling);
        }
        else if(s.startsWith("Employee"))
        {
            imageView.setImageResource(R.drawable.eic);
        }
        else if(s.startsWith("Student"))
        {
            imageView.setImageResource(R.drawable.sic);
        }
        else if(s.startsWith("Allowance"))
        {
            imageView.setImageResource(R.drawable.allowance);
        }
        else if(s.startsWith("Bonus"))
        {
            imageView.setImageResource(R.drawable.bonus);
        }
        else
        {
            imageView.setImageResource(R.drawable.general);
        }


        return rowView;
    }
}
