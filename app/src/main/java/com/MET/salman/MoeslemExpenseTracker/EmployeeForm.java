package com.MET.salman.MoeslemExpenseTracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EmployeeForm extends AppCompatActivity {

    DatabaseHandler db;
    EditText BudgetText,InstallmentText,TotalWealthText,DebtText;
    ImageButton HelpBtn1,HelpBtn2,HelpBtn3;
    String code,codeMaal,codeFit,codeProf;
    DateFormat dateFormat;
    Date date;
    String Day;

    Calendar Cal;
    SimpleDateFormat sdf;
    String FormattedDate;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    long Budget = 0, Installment = 0, TotalWealth = 0, Debt = 0;
    NumberFormat numberFormatIDR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_form);

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Young.ttf");
        Locale IDR = new Locale("in","ID");
        numberFormatIDR = NumberFormat.getCurrencyInstance(IDR);

        TextView HeaderTxt = (TextView) findViewById(R.id.HeaderTxt);
        HeaderTxt.setTypeface(tf);

        TextView textView1 = (TextView) findViewById(R.id.textView2);
        textView1.setTypeface(tf);
        TextView textView2 = (TextView) findViewById(R.id.textView12);
        textView2.setTypeface(tf);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setTypeface(tf);
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        textView4.setTypeface(tf);

        db = new DatabaseHandler(this);

        dateFormat = new SimpleDateFormat("ddMMyyyy");
        date = new Date();

        Cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("MMMM yyyy");
        FormattedDate = sdf.format(Cal.getTime());

        Day = FormattedDate;

        //setID to editText
        BudgetText = (EditText) findViewById(R.id.BudgetText);
        BudgetText.setTypeface(tf);
        InstallmentText = (EditText) findViewById(R.id.InstallmentText);
        InstallmentText.setTypeface(tf);
        TotalWealthText = (EditText) findViewById(R.id.TotalWealthText);
        TotalWealthText.setTypeface(tf);
        DebtText = (EditText) findViewById(R.id.DebtText);
        DebtText.setTypeface(tf);

        HelpBtn1 = (ImageButton) findViewById(R.id.HelpBtn1);
        HelpBtn2 = (ImageButton) findViewById(R.id.HelpBtn2);
        HelpBtn3 = (ImageButton) findViewById(R.id.HelpBtn3);



        HelpBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EmployeeForm.this)
                        .setTitle("Information")
                        .setMessage("Monthly Debt or Installment. Can be change in settings and does not required to be filled to continue")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        HelpBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EmployeeForm.this)
                        .setTitle("Information")
                        .setMessage("Total wealth in form of gold, silver, savings, investment, and trading income. Can be change in settings and does not required to be filled to continue")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        HelpBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EmployeeForm.this)
                        .setTitle("Information")
                        .setMessage("Debt that due date on the day of zakat maal payment. Can be change in settings and does not required to be filled to continue")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        InstallmentText.setText("Rp0");
        TotalWealthText.setText("Rp0");
        DebtText.setText("Rp0");


        BudgetText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if(BudgetText.getText().toString().equalsIgnoreCase("") || BudgetText.getText().toString().equalsIgnoreCase("0"))
                    {
                        Budget = 0;
                        BudgetText.setText(numberFormatIDR.format(Budget));
                    }
                    else
                    {
                        Budget = Long.parseLong(BudgetText.getText().toString());
                        BudgetText.setText(numberFormatIDR.format(Budget));
                    }


                }

                return false;
            }
        });

        BudgetText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    BudgetText.setText("");
                } else {
                    BudgetText.setText(numberFormatIDR.format(Budget));
                }
            }
        });

        BudgetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BudgetText.setText("");
                Budget = 0;
            }
        });

        InstallmentText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if(InstallmentText.getText().toString().equalsIgnoreCase("") || InstallmentText.getText().toString().equalsIgnoreCase("0"))
                    {
                        Installment = 0;
                        InstallmentText.setText(numberFormatIDR.format(Installment));
                    }
                    else
                    {
                        Installment = Long.parseLong(InstallmentText.getText().toString());
                        InstallmentText.setText(numberFormatIDR.format(Installment));
                    }


                }

                return false;
            }
        });

        InstallmentText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InstallmentText.setText("");
                } else {
                    InstallmentText.setText(numberFormatIDR.format(Installment));
                }
            }
        });

        InstallmentText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstallmentText.setText("");
                Installment = 0;
            }
        });

        TotalWealthText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if(TotalWealthText.getText().toString().equalsIgnoreCase("") || TotalWealthText.getText().toString().equalsIgnoreCase("0"))
                    {
                        TotalWealth = 0;
                        TotalWealthText.setText(numberFormatIDR.format(TotalWealth));

                    }
                    else
                    {
                        TotalWealth = Long.parseLong(TotalWealthText.getText().toString());
                        TotalWealthText.setText(numberFormatIDR.format(TotalWealth));
                    }



                }

                return false;
            }
        });

        TotalWealthText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    TotalWealthText.setText("");
                } else {
                    TotalWealthText.setText(numberFormatIDR.format(TotalWealth));
                }
            }
        });

        TotalWealthText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TotalWealthText.setText("");
                TotalWealth = 0;
            }
        });

        DebtText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if(DebtText.getText().toString().equalsIgnoreCase("") || DebtText.getText().toString().equalsIgnoreCase("0")) {

                        Debt = 0;
                        DebtText.setText(numberFormatIDR.format(Debt));

                    }
                    else
                    {
                        Debt = Long.parseLong(DebtText.getText().toString());
                        DebtText.setText(numberFormatIDR.format(Debt));
                    }

                }

                return false;
            }
        });

        DebtText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DebtText.setText("");
                } else {
                    DebtText.setText(numberFormatIDR.format(Debt));
                }
            }
        });

        DebtText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebtText.setText("");
                Debt = 0;
            }
        });

        ImageButton SaveBtn = (ImageButton) findViewById(R.id.SaveBtn);
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (BudgetText.getText().toString().equalsIgnoreCase("") || BudgetText.getText().toString().equalsIgnoreCase("0") || Budget == 0) {

                    new AlertDialog.Builder(EmployeeForm.this)
                            .setTitle("Error")
                            .setMessage("'Budget Amount' must be filled")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    BudgetText.setText("");
                                }
                            })
                            .show();
                }
                else if(BudgetText.getText().toString().length() > 18)
                {
                    BudgetText.setText("");
                }
                else {

                    long hargaEmas = 85 * 530000;
                    double ZakatMaal;
                    long NisabZakatProfesi = 520 * 6000;
                    double ZakatProfesi;
                    double ZakatFitrah = 3.5 * 6000;

                        //code
                    sharedPreferences = getSharedPreferences("CountExpInc", MODE_PRIVATE);
                    SharedPreferences.Editor countEditor = sharedPreferences.edit();
                    countEditor.putString("countExp", "0");
                    countEditor.putString("countInc", "0");
                    countEditor.apply();


                    sharedPreferences = getSharedPreferences("launch", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("start", true);
                    editor.commit();

                    code = "E" + dateFormat.format(date);
                    db.AddMsUser(new MsUser(code, Budget, "Employee",Installment,TotalWealth,Debt));


                    ZakatMaal = TotalWealth - Debt;
                    if(ZakatMaal > hargaEmas)
                    {
                        ZakatMaal = ZakatMaal * 0.025;
                    }
                    else
                    {
                        ZakatMaal = 0;
                    }
                    int countMaal = db.getZakatMaalCount()+1;
                    codeMaal = "M" + dateFormat.format(date) + countMaal;
                    db.AddZakatMaal(new ZakatFitMal(codeMaal,Math.round(ZakatMaal)));


                    ZakatProfesi = Budget - Installment;
                    if( ZakatProfesi > NisabZakatProfesi)
                    {
                        ZakatProfesi = ZakatProfesi * 0.025;
                    }
                    else
                    {
                        ZakatProfesi = 0;
                    }

                    int countFitProf = db.getZakatFitCount()+1;
                    codeProf = "P" + dateFormat.format(date) + countFitProf;
                    db.AddZakatProf(new ZakatProfesi(codeProf,Math.round(ZakatProfesi),FormattedDate));
//                    db.AddZakatProf(new ZakatProfesi(codeProf,Math.round(ZakatProfesi)));


                    int countFit = db.getZakatFitCount()+1;
                    codeFit = "F" + dateFormat.format(date) + countFit;
                    db.AddZakatFit(new ZakatFitMal(codeFit,Math.round(ZakatFitrah)));

                    Intent e = new Intent(EmployeeForm.this, MainActivity.class);
                    e.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    e.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    StartActivity activity = new StartActivity();
                    activity.finish();
                    startActivity(e);
                    finish();
                }
            }
        });
    }

    public void onBackPressed()
    {
        Intent x = new Intent(EmployeeForm.this, StartActivity.class);
        startActivity(x);
        finish();
    }

}
