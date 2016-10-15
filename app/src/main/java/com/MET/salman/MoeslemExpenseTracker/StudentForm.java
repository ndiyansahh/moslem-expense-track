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
import android.widget.Switch;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StudentForm extends AppCompatActivity {

    DatabaseHandler db;
    EditText BudgetTxt;
    String code,codeFit;
    DateFormat dateFormat;
    Date date;
    String Day;

    Calendar Cal;
    SimpleDateFormat sdf;
    String FormattedDate;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    long Budget;
    NumberFormat numberFormatIDR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Young.ttf");
        Locale IDR = new Locale("in","ID");
        numberFormatIDR = NumberFormat.getCurrencyInstance(IDR);

        TextView HeaderTxt = (TextView) findViewById(R.id.HeaderTxt);
        HeaderTxt.setTypeface(tf);

        TextView textView1 = (TextView) findViewById(R.id.textView2);
        textView1.setTypeface(tf);

        db = new DatabaseHandler(this);

        dateFormat = new SimpleDateFormat("ddMMyyyy");
        date = new Date();

        Cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("MMMM yyyy");
        FormattedDate = sdf.format(Cal.getTime());

        Day = FormattedDate;

        //setID to editText
        BudgetTxt = (EditText) findViewById(R.id.BudgetTxt);
        BudgetTxt.setTypeface(tf);

        BudgetTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (BudgetTxt.getText().toString().equalsIgnoreCase("") || BudgetTxt.getText().toString().equalsIgnoreCase("0")) {
                        Budget = 0;
                        BudgetTxt.setText(numberFormatIDR.format(Budget));
                    } else {
                        Budget = Long.parseLong(BudgetTxt.getText().toString());
                        BudgetTxt.setText(numberFormatIDR.format(Budget));
                    }

                }

                return false;
            }
        });

        BudgetTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    BudgetTxt.setText("");
                } else {
                    BudgetTxt.setText(numberFormatIDR.format(Budget));
                }
            }
        });

        BudgetTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BudgetTxt.setText("");
                Budget = 0;
            }
        });

        ImageButton SaveBtn = (ImageButton) findViewById(R.id.SaveBtn);
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validasi
//                final Dialog dialog = new Dialog(StudentForm.this);
//                dialog.setContentView(R.layout.dialogsinglebutton);
//                TextView id1 = (TextView) dialog.findViewById(R.id.id1);
//                Button id2 = (Button) dialog.findViewById(R.id.id2);
//                id2.setText("OK");
//                dialog.show();

                if (BudgetTxt.getText().toString().equalsIgnoreCase("") || BudgetTxt.getText().toString().equalsIgnoreCase("0")) {

                    new AlertDialog.Builder(StudentForm.this)
                            .setTitle("Error")
                            .setMessage("'Budget Amount' must be filled")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    BudgetTxt.setText("");
                                }
                            })
                            .show();

//                    dialog.setTitle("Error");
//                    id1.setText("Budget Amount must be filled");
//                    id2.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            BudgetTxt.setText("");
//                            dialog.dismiss();
//                        }
//                    });
                }
                else if(BudgetTxt.getText().toString().length() > 18)
                {

                    BudgetTxt.setText("");

                }
                else {

                    double ZakatFitrah = 3.5 * 6000;
                    //code
                    sharedPreferences = getSharedPreferences("CountExpInc", MODE_PRIVATE);
                    SharedPreferences.Editor countEditor = sharedPreferences.edit();
                    countEditor.putString("countExp","0");
                    countEditor.putString("countInc","0");
                    countEditor.apply();



                    sharedPreferences = getSharedPreferences("launch",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("start", true);
                    editor.commit();

                    code = "S" + dateFormat.format(date);
                    db.AddMsUser(new MsUser(code, Budget, "Student", 0, 0, 0));

                    int countFit = db.getZakatFitCount()+1;
                    codeFit = "F" + dateFormat.format(date) + countFit;
                    db.AddZakatFit(new ZakatFitMal(codeFit, Math.round(ZakatFitrah)));

                    Intent e = new Intent(StudentForm.this, MainActivity.class);

                    e.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    e.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    StartActivity activity  = new StartActivity();
                    activity.finish();
                    startActivity(e);
                    finish();
                }
            }
        });

//        Button CancelBtn = (Button) findViewById(R.id.CancelBtn);
//        CancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent e = new Intent(StudentForm.this, StartActivity.class);
//                startActivity(e);
//                finish();
//            }
//        });
    }

    public void onBackPressed()
    {
        Intent x = new Intent(StudentForm.this, StartActivity.class);
        startActivity(x);
        finish();
    }
}
