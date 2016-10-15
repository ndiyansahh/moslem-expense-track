package com.MET.salman.MoeslemExpenseTracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditDeleteForm extends AppCompatActivity {

    DatabaseHandler db;
    DateFormat dateFormat;
    Date date;
    int years, month, day;
    EditText DateTxt,CategoryTxt,AmountTxt;
    String code,codeProf;
    String ID;
    Typeface tf;

    long amount;
    String text;
    NumberFormat numberFormatIDR;

    Calendar Cal;
    SimpleDateFormat sdf2;
    String FormattedDate2;


    String Datecheck;
    MsUser getUser;
    ZakatProfesi getZakatProf;
    long NisabZakatProfesi = 520 * 6000;
    boolean profIsPaid;
    SharedPreferences zakatPreferences;
    SharedPreferences.Editor zakatEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_form);

        Locale IDR = new Locale("in","ID");
        numberFormatIDR = NumberFormat.getCurrencyInstance(IDR);

        tf = Typeface.createFromAsset(getAssets(), "fonts/Young.ttf");

        TextView textView1 = (TextView) findViewById(R.id.textView2);
        textView1.setTypeface(tf);
        TextView textView2 = (TextView) findViewById(R.id.textView12);
        textView2.setTypeface(tf);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setTypeface(tf);
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        textView4.setTypeface(tf);
        TextView textView5 = (TextView) findViewById(R.id.textView5);
        textView5.setTypeface(tf);


        Bundle bundle = getIntent().getExtras();

        ID = bundle.getString("ID");
        ID.substring(0, 1);
        db = new DatabaseHandler(this);

        dateFormat = new SimpleDateFormat("ddMMyyyy");
        date = new Date();

        getAllData getItem = db.getSingleData(ID);

        DateTxt = (EditText) findViewById(R.id.DateTxt);
        DateTxt.setTypeface(tf);
        AmountTxt = (EditText) findViewById(R.id.AmountTxt);
        AmountTxt.setTypeface(tf);
        CategoryTxt = (EditText) findViewById(R.id.CategoryTxt);
        CategoryTxt.setTypeface(tf);

        ImageButton SaveBtn = (ImageButton) findViewById(R.id.SaveBtn);
        ImageButton DeleteBtn = (ImageButton) findViewById(R.id.DeleteBtn);

        String[] months = {"January", "Febuary", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"};


        DateTxt.setText(getItem.getDate());
        AmountTxt.setText(numberFormatIDR.format(getItem.getExpense()));
        CategoryTxt.setText(getItem.getCategory());
        amount = getItem.getExpense();

        Cal = Calendar.getInstance();
        sdf2 = new SimpleDateFormat("MMMM yyyy");
        FormattedDate2 = sdf2.format(Cal.getTime());
        Datecheck = FormattedDate2;

        code = ID;

        DateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditDeleteForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        years = year;
                        month = monthOfYear;
                        day = dayOfMonth;

//                        Toast.makeText(getActivity(), year + "" + month + "" + day, Toast.LENGTH_SHORT).show();
                        String d, m, y;
                        String[] months = {"January", "Febuary", "March", "April", "May", "June", "July",
                                "August", "September", "October", "November", "December"};

                        DateTxt.setText("" + day + " " + months[month] + " " + years);
                        Datecheck = months[month] + " " + years;
                    }
                }, years, month, day).show();

            }
        });

        AmountTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (AmountTxt.getText().toString().equalsIgnoreCase("") || AmountTxt.getText().toString().equalsIgnoreCase("0")) {
                        amount = 0;
                        AmountTxt.setText(numberFormatIDR.format(amount));
                    } else {
                        amount = Long.parseLong(AmountTxt.getText().toString());
                        AmountTxt.setText(numberFormatIDR.format(amount));
                    }

                }


                return false;
            }
        });

        AmountTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    AmountTxt.setText("");
                } else {
                    AmountTxt.setText(numberFormatIDR.format(amount));
                }
            }
        });

        AmountTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmountTxt.setText("");
            }
        });

        CategoryTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(EditDeleteForm.this);
                dialog.setContentView(R.layout.dialogcategorylayout);
                ListView category = (ListView) dialog.findViewById(R.id.listViewCategory);


                String[] values = new String[]{"Food & Beverages", "Clothes", "Education", "Fuel", "Auto", "Transportation",
                        "Entertainment", "House","Groceries" ,"Health", "Gadget", "Sports", "Travel","General"};

                if(ID.substring(0,1).equals("I"))
                {
                    values = new String[]{"Bonus Salary", "Uang Dinas", "Property Sold"};
                }

                // use your custom layout
                MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(EditDeleteForm.this, values);
                category.setAdapter(adapter);
                dialog.show();

                category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String item = (String) parent.getItemAtPosition(position);
                        CategoryTxt.setText(item);
                        dialog.dismiss();
                    }
                });
            }
        });


        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (AmountTxt.getText().toString().equalsIgnoreCase("0") || AmountTxt.getText().toString().equalsIgnoreCase("") ||  amount == 0) {
                    new AlertDialog.Builder(EditDeleteForm.this)
                            .setTitle("Error")
                            .setMessage("'Amount' must be filled")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AmountTxt.setText("");
                                }
                            })
                            .show();

                } else if (CategoryTxt.getText().toString().equalsIgnoreCase("")) {

                    new AlertDialog.Builder(EditDeleteForm.this)
                            .setTitle("Error")
                            .setMessage("'Category' must be Picked")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CategoryTxt.setText("");
                                }
                            })
                            .show();

                } else {

                                       String a,c;
                    text = AmountTxt.getText().toString();
                    a = text.replace("Rp", "");
                    c = a.replace(".","");
                    amount = Long.parseLong(c);
                    db.updateData(new getAllData(code, DateTxt.getText().toString(), amount,
                            CategoryTxt.getText().toString()));

                    zakatPreferences = getSharedPreferences("State", 0);
                    zakatEditor = zakatPreferences.edit();
                    profIsPaid = zakatPreferences.getBoolean("profPaid", false);

                    if (!profIsPaid) {
                        if (ID.substring(0, 1).equals("I")) {
                            getZakatProf = db.getZakatProfesi(Datecheck);
                            getUser = db.getUser();
                            long totalBudget = getUser.getBudget() + db.countIncomes(Datecheck);
                            Log.v("Total Budget:", "" + totalBudget);
                            double ZakatProfesi = totalBudget - getUser.getInstallment();
                            Log.v("Zakat Profesi:", "" + ZakatProfesi);
                            if (ZakatProfesi > NisabZakatProfesi) {
                                ZakatProfesi = ZakatProfesi * 0.025;
                            } else {
                                ZakatProfesi = 0;
                            }
                            if (getZakatProf.getDate() != null) {

                                int countProf = db.getZakatFitCount() + 1;
                                db.updateData(new ZakatProfesi(db.getZakatProfesi(Datecheck).getZakatID(), Math.round(ZakatProfesi), Datecheck));
                            }
                        }
                    }
                    finish();
                }
            }
        });

        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EditDeleteForm.this)
                        .setTitle("Warning")
                        .setMessage("You are about to delete this transaction. Do you wish to Continue?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                db.DeleteData(ID);
                                zakatPreferences = getSharedPreferences("State", 0);
                                zakatEditor = zakatPreferences.edit();
                                profIsPaid = zakatPreferences.getBoolean("profPaid", false);
                                if (!profIsPaid) {
                                    if (ID.substring(0, 1).equals("I")) {
                                        getZakatProf = db.getZakatProfesi(Datecheck);
                                        getUser = db.getUser();
                                        long totalBudget = getUser.getBudget() + db.countIncomes(Datecheck);
                                        Log.v("Total Budget Del:", "" + totalBudget);
                                        double ZakatProfesi = totalBudget - getUser.getInstallment();
                                        Log.v("Zakat Profesi Del:", "" + ZakatProfesi);
                                        if (ZakatProfesi > NisabZakatProfesi) {
                                            ZakatProfesi = ZakatProfesi * 0.025;
                                        } else {
                                            ZakatProfesi = 0;
                                        }

                                        if (getZakatProf.getDate() != null) {

                                            int countProf = db.getZakatFitCount() + 1;
                                            db.updateData(new ZakatProfesi(db.getZakatProfesi(Datecheck).getZakatID(), Math.round(ZakatProfesi), Datecheck));

                                        }
                                    }
                                }
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });




    }

}
