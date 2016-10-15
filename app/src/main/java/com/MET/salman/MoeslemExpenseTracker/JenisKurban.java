package com.MET.salman.MoeslemExpenseTracker;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JenisKurban.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JenisKurban#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JenisKurban extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button btn1,btn2,btn3,btn4;
    TextView DescriptionTxt,Durationtxt;
    TextView textView1,textView2,textView3,ReminderText;
    EditText SavingTxt;
    ImageView imageView;
    ImageButton SavingBtn;

    int Price = 0,Savings = 0;
    int Duration;
    int Suggestion = 0;
    long userBudget = 0;
    NumberFormat numberFormatIDR;
    String CodeKurban;
    String CodeKurbanWeight;
    String CodeKurbanPrice;
    String EntryDate;

    DatabaseHandler db;
    MsUser getUser;
    ZakatFitMal getZakatFit;
    ZakatFitMal getZakatMaal;
    ZakatProfesi getZakaProf;

    Calendar Cal;
    SimpleDateFormat sdf;
    String FormattedDate;
    DateFormat dateFormat;
    Date date;
    String Datecheck;
    ImageButton BackBtn;

    MediaPlayer mp;

    SharedPreferences zakatPreferences;
    SharedPreferences.Editor zakatEditor;
    boolean maalIsPaid, profIsPaid,fitIsPaid;

    private OnFragmentInteractionListener mListener;

    public JenisKurban() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JenisKurban.
     */
    // TODO: Rename and change types and number of parameters
    public static JenisKurban newInstance(String param1, String param2) {
        JenisKurban fragment = new JenisKurban();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jenis_kurban, container, false);
        db = new DatabaseHandler(getActivity());
        getUser = db.getUser();
        zakatPreferences = getActivity().getSharedPreferences("State", 0);
        zakatEditor = zakatPreferences.edit();

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Young.ttf");
        Locale IDR = new Locale("in","ID");
        numberFormatIDR = NumberFormat.getCurrencyInstance(IDR);

        SavingTxt = (EditText) view.findViewById(R.id.SavingTxt);
        SavingTxt.setTypeface(tf);
        SavingTxt.setText("Rp0");

        imageView = (ImageView) view.findViewById(R.id.imageView);
        btn1 = (Button) view.findViewById(R.id.btn1);
        btn2 = (Button) view.findViewById(R.id.btn2);
        btn3 = (Button) view.findViewById(R.id.btn3);
        btn4 = (Button) view.findViewById(R.id.btn4);

        DescriptionTxt = (TextView) view.findViewById(R.id.DescriptionTxt);
        Durationtxt = (TextView) view.findViewById(R.id.Durationtxt);

        textView1 = (TextView) view.findViewById(R.id.textView2);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        ReminderText = (TextView) view.findViewById(R.id.ReminderText);

        SavingBtn = (ImageButton) view.findViewById(R.id.SavingBtn);
//        SavingBtn.setTypeface(tf);

        DescriptionTxt.setTypeface(tf);
        Durationtxt.setTypeface(tf);
        textView1.setTypeface(tf);
        textView2.setTypeface(tf);
        textView3.setTypeface(tf);
        ReminderText.setTypeface(tf);

        btn1.setTypeface(tf);
        btn2.setTypeface(tf);
        btn3.setTypeface(tf);
        btn4.setTypeface(tf);

        Cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("MMMM yyyy");
        FormattedDate = sdf.format(Cal.getTime());
        Datecheck = FormattedDate;

        dateFormat = new SimpleDateFormat("yyyy");
        date = new Date();
        EntryDate = "ED" + dateFormat.format(date);

        //back button
        getActivity().findViewById(R.id.BackBtn).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.BackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new SyariahFragment().newInstance("Kurban", null)).commit();
                getActivity().findViewById(R.id.BackBtn).setVisibility(View.INVISIBLE);
            }
        });


        if(mParam1.equals("Kambing"))
        {
            Glide.with(this).load(R.drawable.kambinggif).fitCenter().crossFade().into(imageView);
            SavingTxt.setEnabled(false);
            ReminderText.setVisibility(View.GONE);
            btn1.setText("24-27 Kg");
            btn2.setText("28-31 Kg");
            btn3.setText("32-35 Kg");
            btn4.setText("36-39 Kg");
            mp = MediaPlayer.create(getActivity(),R.raw.goatsound);

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DescriptionTxt.setTextColor(Color.parseColor("#FF2C29"));
                    DescriptionTxt.setText(Html.fromHtml(
                            "<p> The Price of a 24 - 27 Kg goat is Rp. 1.500.000 </p>"
                    ));
                    Price = 1500000;
                    SavingTxt.setText("");
                    SavingTxt.setEnabled(true);
                    Savings = 0;
                    Duration = 0;
                    Durationtxt.setText("");
                    CodeKurbanWeight = "W2427";
                    CodeKurbanPrice = "P15J";
                }
            });


            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DescriptionTxt.setTextColor(Color.parseColor("#FF4829"));
                    DescriptionTxt.setText(Html.fromHtml(
                            "<p> The Price of a 28 - 31 Kg goat is Rp. 1.750.000 </p>"
                    ));
                    Price = 1750000;
                    SavingTxt.setText("");
                    SavingTxt.setEnabled(true);
                    Savings = 0;
                    Duration = 0;
                    Durationtxt.setText("");
                    CodeKurbanWeight = "W2831";
                    CodeKurbanPrice = "P175J";
                }
            });


            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DescriptionTxt.setTextColor(Color.parseColor("#FF6C29"));
                    DescriptionTxt.setText(Html.fromHtml(
                            "<p> The Price of a 32 - 35 Kg goat is Rp. 1.950.000 </p>"
                    ));
                    Price = 1950000;
                    SavingTxt.setText("");
                    SavingTxt.setEnabled(true);
                    Savings = 0;
                    Duration = 0;
                    Durationtxt.setText("");
                    CodeKurbanWeight = "W3235";
                    CodeKurbanPrice = "P19J";
                }
            });


            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DescriptionTxt.setTextColor(Color.parseColor("#FF9629"));
                    DescriptionTxt.setText(Html.fromHtml(
                            "<p> The Price of a 36 - 39 Kg goat is Rp. 2.150.000 </p>"
                    ));
                    Price = 2150000;
                    SavingTxt.setText("");
                    SavingTxt.setEnabled(true);
                    Savings = 0;
                    Duration = 0;
                    Durationtxt.setText("");
                    CodeKurbanWeight = "W3639";
                    CodeKurbanPrice = "P215J";
                }
            });

            SavingTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                        if (SavingTxt.getText().toString().equalsIgnoreCase("") || SavingTxt.getText().toString().equalsIgnoreCase("0")) {
                            Savings = 0;
                            SavingTxt.setText(numberFormatIDR.format(Savings));
                        } else {
                            Savings = Integer.parseInt(SavingTxt.getText().toString());
                            SavingTxt.setText(numberFormatIDR.format(Savings));

                            Duration = Price / Savings;
                            Duration = (int) Math.ceil(Duration);

                            if (Savings * Duration < Price) {
                                do {
                                    Duration++;
                                } while (Savings * Duration < Price);
                            }
                            Durationtxt.setText(Duration + " Months until goat is affordable");

                            long counter;
                            if (Duration > 12) {
                                Suggestion = Price / 12;
                                Suggestion = (int) Math.ceil(Suggestion);
                                counter = Suggestion * 12;

                                if (counter < Price) {
                                    do {
                                        Suggestion += 10;
                                        counter += 10;
                                    } while (counter < Price);
                                }

                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Saving advice")
                                        .setMessage("Try saving around " + numberFormatIDR.format(Suggestion) + " for 12 month ")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }
                        }
                    }
                    return false;
                }
            });

            SavingTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        SavingTxt.setText("");
                    } else {
                        SavingTxt.setText(numberFormatIDR.format(Savings));
                    }
                }
            });

            SavingTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SavingTxt.setText("");
                    Savings = 0;
                    Duration = 0;
                    Durationtxt.setText("");

                }
            });

            SavingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getUser = db.getUser();
                    getZakaProf = db.getZakatProfesi(Datecheck);
//                    getZakaProf = db.getZakatProfesi();
                    getZakatFit = db.getZakatFit();
                    getZakatMaal = db.getZakatMaal();

                    long totalExpense = db.countExpenses(Datecheck);
                    long totalBudget = getUser.getBudget()+db.countIncomes(Datecheck);
                    long remain = totalBudget-totalExpense;
                    remain = remain - getZakaProf.getAmount();
                    maalIsPaid = zakatPreferences.getBoolean("maalPaid", false );
                    profIsPaid = zakatPreferences.getBoolean("profPaid", false );
                    fitIsPaid = zakatPreferences.getBoolean("fitPaid", false );

                    if(profIsPaid)
                    {
                        remain = remain - getZakaProf.getAmount();
                        Log.v(""+remain," Profesi");
                    }
                    if(maalIsPaid)
                    {
                        remain = remain - (Math.round(getZakatMaal.getAmount()/12));
                        Log.v(""+remain," Maal");
                    }
                    if(fitIsPaid)
                    {
                        remain = remain - getZakatFit.getAmount();
                        Log.v(""+remain," Fitrah");
                    }
                    if(Price == 0)
                    {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Warning")
                                .setMessage("Please select a weight!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                    }
                    else if(Savings == 0 || Duration == 0)
                    {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Warning")
                                .setMessage("Savings/month must be filled")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                    }
                    else {
                        if (remain >= Savings) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Warning")
                                    .setMessage("You are about to start saving for a price of " + numberFormatIDR.format(Price) +
                                            " with a savings/month of " + numberFormatIDR.format(Savings) + " for " + Duration + " Months. Do you wish to Continue?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            int countKurban = db.getKurbanCount() + 1;
                                            CodeKurban = "G" + countKurban + EntryDate + CodeKurbanPrice + CodeKurbanWeight;
                                            db.AddKurban(new Kurban(CodeKurban, Price, Savings, Duration));
                                            ReminderText.setVisibility(View.VISIBLE);
                                            ReminderText.setText("You are now saving " + numberFormatIDR.format(Savings) + "/month for a goat");
                                            SavingTxt.setText("");
                                            Durationtxt.setText("");
                                            Savings = 0;
                                            Duration = 0;

                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                        } else {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Warning")
                                    .setMessage("You dont have any budget left this month to start saving for kurban!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                        }
                    }
                }
            });
        }
        else if(mParam1.equals("Sapi"))
        {
            Glide.with(this).load(R.drawable.sapigif).fitCenter().crossFade().into(imageView);
            SavingTxt.setEnabled(false);
            ReminderText.setVisibility(View.GONE);
            btn1.setText("220-250 Kg");
            btn2.setText("251-280 Kg");
            btn3.setText("281-310 Kg");
            btn4.setText("320-350 Kg");
            mp = MediaPlayer.create(getActivity(),R.raw.cowsound);

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DescriptionTxt.setTextColor(Color.parseColor("#FF2C29"));
                    DescriptionTxt.setText(Html.fromHtml(
                            "<p> The Price of a 220 - 250 Kg cow is Rp. 14.000.000 </p>"
                    ));
                    Price = 14000000;
                    SavingTxt.setText("");
                    SavingTxt.setEnabled(true);
                    Savings = 0;
                    Duration = 0;
                    Durationtxt.setText("");
                    CodeKurbanWeight = "W220250";
                    CodeKurbanPrice = "P14J";
                }
            });


            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DescriptionTxt.setTextColor(Color.parseColor("#FF4829"));
                    DescriptionTxt.setText(Html.fromHtml(
                            "<p> The Price of a 251 - 280 Kg cow is Rp. 16.000.000 </p>"
                    ));
                    Price = 16000000;
                    SavingTxt.setText("");
                    SavingTxt.setEnabled(true);
                    Savings = 0;
                    Duration = 0;
                    Durationtxt.setText("");
                    CodeKurbanWeight = "W251280";
                    CodeKurbanPrice = "P16J";
                }
            });


            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DescriptionTxt.setTextColor(Color.parseColor("#FF6C29"));
                    DescriptionTxt.setText(Html.fromHtml(
                            "<p> The Price of a 281 - 310 Kg cow is Rp. 17.000.000 </p>"
                    ));
                    Price = 17000000;
                    SavingTxt.setText("");
                    SavingTxt.setEnabled(true);
                    Savings = 0;
                    Duration = 0;
                    Durationtxt.setText("");
                    CodeKurbanWeight = "W281310";
                    CodeKurbanPrice = "P17J";
                }
            });


            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DescriptionTxt.setTextColor(Color.parseColor("#FF9629"));
                    DescriptionTxt.setText(Html.fromHtml(
                            "<p> The Price of a 320 - 350 Kg cow is Rp. 20.000.000 </p>"
                    ));
                    Price = 20000000;
                    SavingTxt.setText("");
                    SavingTxt.setEnabled(true);
                    Savings = 0;
                    Duration = 0;
                    Durationtxt.setText("");
                    CodeKurbanWeight = "W320350";
                    CodeKurbanPrice = "P20J";
                }
            });

            SavingTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                        if (SavingTxt.getText().toString().equalsIgnoreCase("") || SavingTxt.getText().toString().equalsIgnoreCase("0")) {
                            Savings = 0;
                            SavingTxt.setText(numberFormatIDR.format(Savings));
                        } else {
                            Savings = Integer.parseInt(SavingTxt.getText().toString());
                            SavingTxt.setText(numberFormatIDR.format(Savings));

                            int save;

                            Duration = Price / Savings;
                            Duration = (int) Math.ceil(Duration);

                            if (Savings * Duration < Price) {
                                do {
                                    Duration++;
                                } while (Savings * Duration < Price);
                            }
                            Durationtxt.setText(Duration + " Months until cow is affordable");

                            long counter;
                            if (Duration > 12) {
                                Suggestion = Price / 12;
                                Suggestion = (int) Math.ceil(Suggestion);
                                counter = Suggestion * 12;

                                if (counter < Price) {
                                    do {
                                        Suggestion += 10;
                                        counter += 10;
                                    } while (counter < Price);
                                }

                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Saving advice")
                                        .setMessage("Try saving around " + numberFormatIDR.format(Suggestion) + " for 12 month ")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();
                            }
                        }
                    }
                    return false;
                }
            });

            SavingTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        SavingTxt.setText("");
                    } else {
                        SavingTxt.setText(numberFormatIDR.format(Savings));
                    }
                }
            });

            SavingTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SavingTxt.setText("");
                    Savings = 0;
                    Duration = 0;
                    Durationtxt.setText("");
                }
            });

            SavingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getUser = db.getUser();
                    getZakaProf = db.getZakatProfesi(Datecheck);
//                    getZakaProf = db.getZakatProfesi();
                    getZakatFit = db.getZakatFit();
                    getZakatMaal = db.getZakatMaal();

                    long totalExpense = db.countExpenses(Datecheck);
                    long totalBudget = getUser.getBudget()+db.countIncomes(Datecheck);
                    long remain = totalBudget-totalExpense;
                    remain = remain - getZakaProf.getAmount();

                    maalIsPaid = zakatPreferences.getBoolean("maalPaid", false );
                    profIsPaid = zakatPreferences.getBoolean("profPaid", false );
                    fitIsPaid = zakatPreferences.getBoolean("fitPaid", false );

                    if(profIsPaid)
                    {
                        remain = remain - getZakaProf.getAmount();
                        Log.v(""+remain," Profesi");
                    }

                    if(maalIsPaid)
                    {
                        remain = remain - (Math.round(getZakatMaal.getAmount()/12));
                        Log.v(""+remain," Maal");
                    }

                    if(fitIsPaid)
                    {
                        remain = remain - getZakatFit.getAmount();
                        Log.v(""+remain," Fitrah");
                    }

                    if(Price == 0)
                    {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Warning")
                                .setMessage("Please select a weight!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                    }
                    else if(Savings == 0 || Duration == 0)
                    {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Warning")
                                .setMessage("Savings/month must be filled")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                    }
                    else {
                        if (remain >= Savings) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Warning")
                                    .setMessage("You are about to start saving for a price of " + numberFormatIDR.format(Price) +
                                            " with a savings/month of " + numberFormatIDR.format(Savings) + " for " + Duration + " Months. Do you wish to Continue?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            int countKurban = db.getKurbanCount() + 1;
                                            CodeKurban = "C" + countKurban + EntryDate + CodeKurbanPrice + CodeKurbanWeight;
                                            db.AddKurban(new Kurban(CodeKurban, Price, Savings, Duration));
                                            ReminderText.setVisibility(View.VISIBLE);
                                            ReminderText.setText("You are now saving " + numberFormatIDR.format(Savings) + "/month for a cow");
                                            SavingTxt.setText("");
                                            Durationtxt.setText("");
                                            Savings = 0;
                                            Duration = 0;

                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                        } else {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Warning")
                                    .setMessage("You dont have any budget left this month to start saving for kurban!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                        }
                    }
                }
            });

        } else if(mParam1.equals("Domba"))
        {
            Glide.with(this).load(R.drawable.dombagif).fitCenter().crossFade().into(imageView);
            SavingTxt.setEnabled(false);
            ReminderText.setVisibility(View.GONE);
            btn1.setText("18-23 Kg");
            btn2.setText("24-30 Kg");
            btn3.setText("31-36 Kg");
            btn4.setText("37-40 Kg");
            mp = MediaPlayer.create(getActivity(),R.raw.sheepsound);

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DescriptionTxt.setTextColor(Color.parseColor("#FF2C29"));
                    DescriptionTxt.setText(Html.fromHtml(
                            "<p> The Price of a 18 - 23 Kg sheep is Rp. 2.100.000 </p>"
                    ));
                    Price = 2100000;
                    SavingTxt.setText("");
                    SavingTxt.setEnabled(true);
                    Savings = 0;
                    Duration = 0;
                    Durationtxt.setText("");
                    CodeKurbanWeight = "W1823";
                    CodeKurbanPrice = "P21J";
                }
            });


            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DescriptionTxt.setTextColor(Color.parseColor("#FF4829"));
                    DescriptionTxt.setText(Html.fromHtml(
                            "<p> The Price of a 24 - 30 Kg sheep is Rp. 2.700.000 </p>"
                    ));
                    Price = 2700000;
                    SavingTxt.setText("");
                    SavingTxt.setEnabled(true);
                    Savings = 0;
                    Duration = 0;
                    Durationtxt.setText("");
                    CodeKurbanWeight = "W2430";
                    CodeKurbanPrice = "P27J";
                }
            });


            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DescriptionTxt.setTextColor(Color.parseColor("#FF6C29"));
                    DescriptionTxt.setText(Html.fromHtml(
                            "<p> The Price of a 31 - 36 Kg sheep is Rp. 3.200.000 </p>"
                    ));
                    Price = 3200000;
                    SavingTxt.setText("");
                    SavingTxt.setEnabled(true);
                    Savings = 0;
                    Duration = 0;
                    Durationtxt.setText("");
                    CodeKurbanWeight = "W3136";
                    CodeKurbanPrice = "P32J";
                }
            });


            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DescriptionTxt.setTextColor(Color.parseColor("#FF9629"));
                    DescriptionTxt.setText(Html.fromHtml(
                            "<p> The Price of a 37 - 40 Kg sheep is Rp. 3.700.000 </p>"
                    ));
                    Price = 3700000;
                    SavingTxt.setText("");
                    SavingTxt.setEnabled(true);
                    Savings = 0;
                    Duration = 0;
                    Durationtxt.setText("");
                    CodeKurbanWeight = "W3740";
                    CodeKurbanPrice = "P37J";
                }
            });

            SavingTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                        if (SavingTxt.getText().toString().equalsIgnoreCase("") || SavingTxt.getText().toString().equalsIgnoreCase("0")) {
                            Savings = 0;
                            SavingTxt.setText(numberFormatIDR.format(Savings));
                        } else {
                            Savings = Integer.parseInt(SavingTxt.getText().toString());
                            SavingTxt.setText(numberFormatIDR.format(Savings));

                            if (Savings * Duration < Price) {
                                do {
                                    Duration++;
                                } while (Savings * Duration < Price);
                            }
                            Durationtxt.setText(Duration + " Months until sheep is affordable");

                            long counter;
                            if (Duration > 12) {
                                Suggestion = Price / 12;
                                Suggestion = (int) Math.ceil(Suggestion);
                                counter = Suggestion * 12;

                                if (counter < Price) {
                                    do {
                                        Suggestion += 10;
                                        counter += 10;
                                    } while (counter < Price);
                                }

                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Saving advice")
                                        .setMessage("Try saving around " + numberFormatIDR.format(Suggestion) + " for 12 month ")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();
                            }
                        }
                    }
                    return false;
                }
            });

            SavingTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        SavingTxt.setText("");
                    } else {
                        SavingTxt.setText(numberFormatIDR.format(Savings));
                    }
                }
            });

            SavingTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SavingTxt.setText("");
                    Savings = 0;
                    Duration = 0;
                    Durationtxt.setText("");
                }
            });

            SavingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getUser = db.getUser();
                    getZakaProf = db.getZakatProfesi(Datecheck);
//                    getZakaProf = db.getZakatProfesi();
                    getZakatFit = db.getZakatFit();
                    getZakatMaal = db.getZakatMaal();

                    long totalExpense = db.countExpenses(Datecheck);
                    long totalBudget = getUser.getBudget()+db.countIncomes(Datecheck);
                    long remain = totalBudget-totalExpense;
                    remain = remain - getZakaProf.getAmount();
                    maalIsPaid = zakatPreferences.getBoolean("maalPaid", false );
                    profIsPaid = zakatPreferences.getBoolean("profPaid", false );
                    fitIsPaid = zakatPreferences.getBoolean("fitPaid", false );

                    if(profIsPaid)
                    {
                        remain = remain - getZakaProf.getAmount();
                        Log.v(""+remain," Profesi");
                    }

                    if(maalIsPaid)
                    {
                        remain = remain - (Math.round(getZakatMaal.getAmount()/12));
                        Log.v(""+remain," Maal");
                    }

                    if(fitIsPaid)
                    {
                        remain = remain - getZakatFit.getAmount();
                        Log.v(""+remain," Fitrah");
                    }

                    if(Price == 0)
                    {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Warning")
                                .setMessage("Please select a weight!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                    }
                    else if(Savings == 0 || Duration == 0)
                    {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Warning")
                                .setMessage("Savings/month must be filled")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                    }
                    else {
                        if (remain >= Savings) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Warning")
                                    .setMessage("You are about to start saving for a price of " + numberFormatIDR.format(Price) +
                                            " with a savings/month of " + numberFormatIDR.format(Savings) + " for " + Duration + " Months. Do you wish to Continue?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            int countKurban = db.getKurbanCount() + 1;
                                            CodeKurban = "S" + countKurban + EntryDate + CodeKurbanPrice + CodeKurbanWeight;
                                            db.AddKurban(new Kurban(CodeKurban, Price, Savings, Duration));
                                            ReminderText.setVisibility(View.VISIBLE);
                                            ReminderText.setText("You are now saving " + numberFormatIDR.format(Savings) + "/month for a sheep");
                                            SavingTxt.setText("");
                                            Durationtxt.setText("");
                                            Savings = 0;
                                            Duration = 0;

                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                        } else {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Warning")
                                    .setMessage("You dont have any budget left this month to start saving for kurban!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                        }
                    }
                }
            });
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp.start();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new SyariahFragment().newInstance(null, null)).commit();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
