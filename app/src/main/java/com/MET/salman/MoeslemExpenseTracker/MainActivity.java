package com.MET.salman.MoeslemExpenseTracker;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView DateTxt;

    Calendar Cal;
    SimpleDateFormat sdf;
    String FormattedDate;

    ImageButton AddBtn;
    TextView ActivityTxt;
    ImageButton BackBtn;

    RelativeLayout NotificationLayout;
    LinearLayout linearLayout8,linearLayout9,linearLayout10,linearLayout11;

    TextView ZktProfTxt,ZktMaalTxt,ZktFitTxt,KurbanTxt;
    TextView ZktProfAmountTxt,ZktMaalAmountTxt,ZktFitAmountTxt,KurbanAmountTxt;

    ZakatFitMal getZakatFit;
    ZakatFitMal getZakatMaal;
    ZakatProfesi getZakaProf;

    SharedPreferences preferencesState;
    SharedPreferences.Editor editorState;

    boolean ZakatMaal;
    boolean ZakatProf;
    boolean ZakatFit;
    boolean Kurban;

    DatabaseHandler db;
    NumberFormat numberFormatIDR;

    String maalMonth, fitMonth, profMonth, kurbanMonth;
    boolean maalIsPaid, profIsPaid,fitIsPaid,kurbanIsPaid;
    SharedPreferences zakatPreferences;
    SharedPreferences.Editor zakatEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DatabaseHandler
        db = new DatabaseHandler(this);
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Young.ttf");
        Locale IDR = new Locale("in","ID");
        numberFormatIDR = NumberFormat.getCurrencyInstance(IDR);


        NotificationLayout = (RelativeLayout) findViewById(R.id.NotificationLayout);

        linearLayout8 = (LinearLayout) findViewById(R.id.linearLayout8);
        linearLayout9 = (LinearLayout) findViewById(R.id.linearLayout9);
        linearLayout10 = (LinearLayout) findViewById(R.id.linearLayout10);
        linearLayout11 = (LinearLayout) findViewById(R.id.linearLayout11);


        preferencesState = getSharedPreferences("State", Context.MODE_PRIVATE);
        ZakatMaal = preferencesState.getBoolean("ZakatMaalIsOn", false);
        ZakatProf = preferencesState.getBoolean("ZakatProfIsOn", false);
        ZakatFit = preferencesState.getBoolean("ZakatFitIsOn", false);
        Kurban = preferencesState.getBoolean("KurbanIsOn", false);

        if(!ZakatMaal && !ZakatProf && !ZakatFit && !Kurban)
        {
            NotificationLayout.setVisibility(View.GONE);
        }
        else
        {
            NotificationLayout.setVisibility(View.VISIBLE);
        }

        if (ZakatMaal)
        {
            linearLayout9.setVisibility(View.VISIBLE);
        }
        else
        {
            linearLayout9.setVisibility(View.GONE);
        }

        if (ZakatProf)
        {
            linearLayout8.setVisibility(View.VISIBLE);
        }
        else
        {
            linearLayout8.setVisibility(View.GONE);
        }

        if (ZakatFit)
        {
            linearLayout10.setVisibility(View.VISIBLE);
        }
        else
        {
            linearLayout10.setVisibility(View.GONE);
        }

        if (Kurban)
        {
            linearLayout11.setVisibility(View.VISIBLE);
        }
        else
        {
            linearLayout11.setVisibility(View.GONE);
        }

        NotificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationLayout.setVisibility(View.GONE);
            }
        });

        ZktProfTxt = (TextView) findViewById(R.id.ZktProfTxt);
        ZktProfTxt.setTypeface(tf);
        ZktMaalTxt = (TextView) findViewById(R.id.ZktMaalTxt);
        ZktMaalTxt.setTypeface(tf);
        ZktFitTxt = (TextView) findViewById(R.id.ZktFitTxt);
        ZktFitTxt.setTypeface(tf);
        KurbanTxt = (TextView) findViewById(R.id.KurbanTxt);
        KurbanTxt.setTypeface(tf);
        ZktProfAmountTxt = (TextView) findViewById(R.id.ZktProfAmountTxt);
        ZktProfAmountTxt.setTypeface(tf);
        ZktMaalAmountTxt = (TextView) findViewById(R.id.ZktMaalAmountTxt);
        ZktMaalAmountTxt.setTypeface(tf);
        ZktFitAmountTxt = (TextView) findViewById(R.id.ZktFitAmountTxt);
        ZktFitAmountTxt.setTypeface(tf);
        KurbanAmountTxt = (TextView) findViewById(R.id.KurbanAmounttxt);
        KurbanAmountTxt.setTypeface(tf);



        final SharedPreferences preferences = getSharedPreferences("passcode", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        final boolean passcodeDialog = preferences.getBoolean("passcodeIsOn", false);
        if (!passcodeDialog){

        }
        else {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_passcode);

            final EditText passcode = (EditText) dialog.findViewById(R.id.passcode);
            final EditText passcode2 = (EditText) dialog.findViewById(R.id.passcode2);

            passcode2.setVisibility(View.GONE);

            dialog.show();

            Button btnPasscode = (Button) dialog.findViewById(R.id.btn_passcode);

            btnPasscode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pass = passcode.getText().toString();
                    String pass2 = preferences.getString("passCode", null);

                    if (pass.equalsIgnoreCase(pass2)) {
                        dialog.dismiss();
                    } else {
                        Toast.makeText(MainActivity.this, "passcode harus sama", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        //getActivityName
        ActivityTxt = (TextView) findViewById(R.id.ActivityNameTxt);
        ActivityTxt.setTypeface(tf);
        final RelativeLayout NavBtnLayout = (RelativeLayout) findViewById(R.id.NavBtnLayout);

//        final SharedPreferences preferencesState = getSharedPreferences("State", MODE_PRIVATE);
//        final boolean State = preferencesState.getBoolean("StateON", false);

        //getDate function

        DateTxt = (TextView)findViewById(R.id.DateTxt);
        DateTxt.setTypeface(tf);
        Cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("MMMM yyyy");
//        Cal.add(Calendar.MONTH, 1);
//        FormattedDate = sdf.format(Cal.getTime());
        FormattedDate = sdf.format(Cal.getTime());
        DateTxt.setText(FormattedDate);

        maalMonth = preferencesState.getString("maalMonth", "");
        fitMonth = preferencesState.getString("fitMonth", "");
        profMonth = preferencesState.getString("profMonth", "");
        kurbanMonth = preferencesState.getString("kurbanMonth", "");

        if (!maalMonth.equals(FormattedDate)){
            editorState = preferencesState.edit();
            editorState.putBoolean("maalPaid", false);
            editorState.putString("maalMonth", null);
            editorState.putLong("maalTot", 0);
            editorState.apply();
        }
        if (!fitMonth.equals(FormattedDate)){
            editorState = preferencesState.edit();
            editorState.putBoolean("fitPaid", false);
            editorState.putString("fitMonth", null);
            editorState.putLong("fitTot", 0);
            editorState.apply();
        }
        if (!profMonth.equals(FormattedDate)){
            editorState = preferencesState.edit();
            editorState.putBoolean("profPaid", false);
            editorState.putString("profMonth", null);
            editorState.putLong("profTot", 0);
            editorState.apply();
        }
        if (!kurbanMonth.equals(FormattedDate)){
            editorState = preferencesState.edit();
            editorState.putLong("kurbanTot", 0);
            editorState.putBoolean("kurbanPaid", false);
            editorState.putString("kurbanMonth", null);
            editorState.apply();
        }

        getZakatFit = db.getZakatFit();
        getZakatMaal = db.getZakatMaal();
        getZakaProf = db.getZakatProfesi(FormattedDate);
//        getZakaProf = db.getZakatProfesi();

        zakatPreferences = getSharedPreferences("State", 0);
        zakatEditor = zakatPreferences.edit();
        maalIsPaid = zakatPreferences.getBoolean("maalPaid", false);
        profIsPaid = zakatPreferences.getBoolean("profPaid", false );
        fitIsPaid = zakatPreferences.getBoolean("fitPaid", false );
        kurbanIsPaid = zakatPreferences.getBoolean("kurbanPaid", false );

        if(maalIsPaid)
        {
            ZktMaalAmountTxt.setText("Paid");
        }
        else
        {
            ZktMaalAmountTxt.setText(numberFormatIDR.format(Math.round(getZakatMaal.getAmount() / 12)));
        }

        if(profIsPaid)
        {
            ZktProfAmountTxt.setText("Paid");
        }
        else
        {
            ZktProfAmountTxt.setText(numberFormatIDR.format(getZakaProf.getAmount()));
        }

        if(fitIsPaid)
        {
            ZktFitAmountTxt.setText("Paid");
        }
        else
        {
            ZktFitAmountTxt.setText(numberFormatIDR.format(getZakatFit.getAmount()));

        }

        if(kurbanIsPaid)
        {
            KurbanAmountTxt.setText("Paid");

        }
        else
        {
            KurbanAmountTxt.setText(numberFormatIDR.format(db.CountKurban()));


        }



        ImageButton PrevBtn = (ImageButton) findViewById(R.id.PrevBtn);
        PrevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cal.add(Calendar.MONTH, -1);
                FormattedDate = sdf.format(Cal.getTime());

                Log.v("Previous date: ", FormattedDate);

                DateTxt.setText(FormattedDate);

                if (ActivityTxt.getText().toString().equals("HOME")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new HomeChartFragment().newInstance(DateTxt.getText().toString(), null)).commit();
//                    if (!State){
//                        getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new HomeChartFragment().newInstance(DateTxt.getText().toString(), null)).commit();
//                    }
//                    else {
//                        getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new HomeFragment().newInstance(DateTxt.getText().toString(), null)).commit();
//                    }
                } else if (ActivityTxt.getText().toString().equals("EXPENSES")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new ExpenseFragment().newInstance(DateTxt.getText().toString(), null)).commit();
                } else if (ActivityTxt.getText().toString().equals("GRAPH")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new GraphFragment().newInstance(DateTxt.getText().toString(), null)).commit();

                }

            }
        });


        ImageButton NextBtn = (ImageButton) findViewById(R.id.NextBtn);
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cal.add(Calendar.MONTH, 1);
                FormattedDate = sdf.format(Cal.getTime());

                Log.v("Next date: ", FormattedDate);

                DateTxt.setText(FormattedDate);

                String act = ActivityTxt.getText().toString();

                if(ActivityTxt.getText().toString().equals("HOME")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new HomeChartFragment().newInstance(DateTxt.getText().toString(), null)).commit();
//                    if (!State){
//                        getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new HomeChartFragment().newInstance(DateTxt.getText().toString(), null)).commit();
//                    }
//                    else {
//                        getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new HomeFragment().newInstance(DateTxt.getText().toString(), null)).commit();
//                    }
                }
                else if(ActivityTxt.getText().toString().equals("EXPENSES"))
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new ExpenseFragment().newInstance(DateTxt.getText().toString(), null)).commit();
                }
                else if(ActivityTxt.getText().toString().equals("GRAPH"))
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new GraphFragment().newInstance(DateTxt.getText().toString(), null)).commit();

                }

            }
        });

        //add button
        AddBtn = (ImageButton) findViewById(R.id.AddBtn);
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(MainActivity.this, NewTransaction.class);
                e.putExtra("position", 0);
                startActivity(e);
            }
        });
        AddBtn.setVisibility(View.INVISIBLE);

        //back button
        BackBtn = (ImageButton) findViewById(R.id.BackBtn);
//        BackBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new SyariahFragment()).commit();
//                BackBtn.setVisibility(View.INVISIBLE);
//            }
//        });
        BackBtn.setVisibility(View.INVISIBLE);


        //Navigation Buttons
        getSupportFragmentManager().beginTransaction().add(R.id.ActivityFrg, new HomeChartFragment().newInstance(DateTxt.getText().toString(), null)).commit();

        //Home Button
        final ImageButton HomeBtn = (ImageButton) findViewById(R.id.HomeBtn);
        HomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new HomeChartFragment().newInstance(DateTxt.getText().toString(), null)).commit();
                NavBtnLayout.setVisibility(View.VISIBLE);
                AddBtn.setVisibility(View.INVISIBLE);
                BackBtn.setVisibility(View.INVISIBLE);
                ActivityTxt.setText("HOME");
            }
        });
        //Expense Button
        final ImageButton ExpenseBtn = (ImageButton) findViewById(R.id.ExpenseBtn);
        ExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavBtnLayout.setVisibility(View.VISIBLE);
                AddBtn.setVisibility(View.VISIBLE);
                BackBtn.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new ExpenseFragment().newInstance(DateTxt.getText().toString(), null)).commit();
                ActivityTxt.setText("EXPENSES");
            }
        });
        //Syariah Button
        final ImageButton SyariahBtn = (ImageButton) findViewById(R.id.SyariahBtn);
        SyariahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavBtnLayout.setVisibility(View.GONE);
                AddBtn.setVisibility(View.INVISIBLE);
                BackBtn.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new SyariahFragment()).commit();
                ActivityTxt.setText("SYARIAH");
            }
        });

        //Graph Button
        final ImageButton GraphBtn = (ImageButton) findViewById(R.id.GraphBtn);
        GraphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavBtnLayout.setVisibility(View.VISIBLE);
                AddBtn.setVisibility(View.INVISIBLE);
                BackBtn.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new GraphFragment().newInstance(DateTxt.getText().toString(), null)).commit();
                ActivityTxt.setText("GRAPH");
            }
        });

        //Settings Button
        final ImageButton SettingBtn = (ImageButton) findViewById(R.id.SettingBtn);
        SettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavBtnLayout.setVisibility(View.GONE);
                AddBtn.setVisibility(View.INVISIBLE);
                BackBtn.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new SettingsFragment()).commit();
                ActivityTxt.setText("SETTINGS");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        preferencesState = getSharedPreferences("State", Context.MODE_PRIVATE);
        ZakatMaal = preferencesState.getBoolean("ZakatMaalIsOn", false);
        ZakatProf = preferencesState.getBoolean("ZakatProfIsOn", false);
        ZakatFit = preferencesState.getBoolean("ZakatFitIsOn", false);
        Kurban = preferencesState.getBoolean("KurbanIsOn", false);

        if(!ZakatMaal && !ZakatProf && !ZakatFit && !Kurban)
        {
            NotificationLayout.setVisibility(View.GONE);
        }
        else
        {
            NotificationLayout.setVisibility(View.VISIBLE);
        }

        if (ZakatMaal)
        {
            linearLayout9.setVisibility(View.VISIBLE);
        }
        else
        {
            linearLayout9.setVisibility(View.GONE);
        }

        if (ZakatProf)
        {
            linearLayout8.setVisibility(View.VISIBLE);
        }
        else
        {
            linearLayout8.setVisibility(View.GONE);
        }

        if (ZakatFit)
        {
            linearLayout10.setVisibility(View.VISIBLE);
        }
        else
        {
            linearLayout10.setVisibility(View.GONE);
        }

        if (Kurban)
        {
            linearLayout11.setVisibility(View.VISIBLE);
        }
        else
        {
            linearLayout11.setVisibility(View.GONE);
        }

        getZakatFit = db.getZakatFit();
        getZakatMaal = db.getZakatMaal();
        getZakaProf = db.getZakatProfesi(FormattedDate);
//        getZakaProf = db.getZakatProfesi();

        zakatPreferences = getSharedPreferences("State", 0);
        zakatEditor = zakatPreferences.edit();
        maalIsPaid = zakatPreferences.getBoolean("maalPaid", false);
        profIsPaid = zakatPreferences.getBoolean("profPaid", false );
        fitIsPaid = zakatPreferences.getBoolean("fitPaid", false);
        kurbanIsPaid = zakatPreferences.getBoolean("kurbanPaid", false );

        if(maalIsPaid)
        {
            ZktMaalAmountTxt.setText("Paid");
        }
        else
        {
            ZktMaalAmountTxt.setText(numberFormatIDR.format(Math.round(getZakatMaal.getAmount() / 12)));
        }

        if(profIsPaid)
        {
            ZktProfAmountTxt.setText("Paid");
        }
        else
        {
            ZktProfAmountTxt.setText(numberFormatIDR.format(getZakaProf.getAmount()));
        }

        if(fitIsPaid)
        {
            ZktFitAmountTxt.setText("Paid");
        }
        else
        {
            ZktFitAmountTxt.setText(numberFormatIDR.format(getZakatFit.getAmount()));

        }

        if(kurbanIsPaid)
        {
            KurbanAmountTxt.setText("Paid");

        }
        else
        {
            KurbanAmountTxt.setText(numberFormatIDR.format(db.CountKurban()));

        }

    }


}
