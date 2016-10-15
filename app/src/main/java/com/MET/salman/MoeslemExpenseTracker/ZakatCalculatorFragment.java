package com.MET.salman.MoeslemExpenseTracker;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ZakatCalculatorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ZakatCalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZakatCalculatorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DatabaseHandler db;
    MsUser getUser;
    long Budget,Installment,TotalWealth,Debt,Addincome;
    long Riceprice,Goldprice;
    long NetIncome = 0;
    long NisabZakatProfesi,NisabZakatMaal;
    long Profannually = 0,Profmonthly = 0;
    long Maalannually = 0,Maalmonthly = 0;

    boolean ProfEligibility = false, MaalEligibility = false;

    double zakatmaal = 0,zakatprofesi = 0;
    long HartaWajibZakat = 0;

    Calendar Cal;
    SimpleDateFormat sdf;
    String FormattedDate;
    String Datecheck;

    TextView  textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8,textView9,textView10;
    TextView  textView11,textView12,textView13,textView14,textView15,textView16,textView17,textView18,textView19,textView20,textView21;
    EditText BudgetTxt, AddincomeTxt,InstallmentTxt,RicepriceTxt,TotalWealthTxt,DebtTxt,GoldpriceTxt;
    TextView NetIncometxt,ProfNisabTxt,ProfEligibilityTxt,ProfannuallyTxt,ProfmonthlyTxt;
    TextView WeatlhTxt,MaalNisabTxt,MaalEligibilityTxt,MaalannuallyTxt,MaalmonthlyTxt;

    private OnFragmentInteractionListener mListener;

    public ZakatCalculatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ZakatCalculatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ZakatCalculatorFragment newInstance(String param1, String param2) {
        ZakatCalculatorFragment fragment = new ZakatCalculatorFragment();
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

        final View view = inflater.inflate(R.layout.fragment_zakat_calculator, container, false);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Young.ttf");
        Locale IDR = new Locale("in","ID");
        final NumberFormat numberFormatIDR = NumberFormat.getCurrencyInstance(IDR);
        db = new DatabaseHandler(getActivity());
        MsUser getUser = db.getUser();

        //get date
        Cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("MMMM yyyy");
        FormattedDate = sdf.format(Cal.getTime());

        Datecheck = FormattedDate;

        //back button
        getActivity().findViewById(R.id.BackBtn).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.BackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new SyariahFragment().newInstance("Zakat", null)).commit();
                getActivity().findViewById(R.id.BackBtn).setVisibility(View.INVISIBLE);
            }
        });

        //fontface set
        textView1 = (TextView) view.findViewById(R.id.textView2);
        textView1.setTypeface(tf);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView2.setTypeface(tf);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        textView3.setTypeface(tf);
        textView4 = (TextView) view.findViewById(R.id.textView4);
        textView4.setTypeface(tf);
        textView5 = (TextView) view.findViewById(R.id.textView5);
        textView5.setTypeface(tf);
        textView6 = (TextView) view.findViewById(R.id.textView6);
        textView6.setTypeface(tf);
        textView7 = (TextView) view.findViewById(R.id.textView7);
        textView7.setTypeface(tf);
        textView8 = (TextView) view.findViewById(R.id.textView8);
        textView8.setTypeface(tf);
        textView9 = (TextView) view.findViewById(R.id.textView9);
        textView9.setTypeface(tf);
        textView10 = (TextView) view.findViewById(R.id.textView10);
        textView10.setTypeface(tf);
        textView11 = (TextView) view.findViewById(R.id.textView11);
        textView11.setTypeface(tf);
        textView12 = (TextView) view.findViewById(R.id.textView12);
        textView12.setTypeface(tf);
        textView13 = (TextView) view.findViewById(R.id.textView13);
        textView13.setTypeface(tf);
        textView14 = (TextView) view.findViewById(R.id.textView14);
        textView14.setTypeface(tf);
        textView15 = (TextView) view.findViewById(R.id.textView15);
        textView15.setTypeface(tf);
        textView16 = (TextView) view.findViewById(R.id.textView16);
        textView16.setTypeface(tf);
        textView17 = (TextView) view.findViewById(R.id.textView17);
        textView17.setTypeface(tf);
        textView18 = (TextView) view.findViewById(R.id.textView18);
        textView18.setTypeface(tf);
        textView19 = (TextView) view.findViewById(R.id.textView19);
        textView19.setTypeface(tf);
        textView20 = (TextView) view.findViewById(R.id.textView20);
        textView20.setTypeface(tf);
        textView21 = (TextView) view.findViewById(R.id.textView21);
        textView21.setTypeface(tf);

        //EditText
        BudgetTxt = (EditText) view.findViewById(R.id.BudgetTxt);
        BudgetTxt.setTypeface(tf);
        AddincomeTxt = (EditText) view.findViewById(R.id.AddincomeTxt);
        AddincomeTxt.setTypeface(tf);
        InstallmentTxt = (EditText) view.findViewById(R.id.InstallmentTxt);
        InstallmentTxt.setTypeface(tf);
        RicepriceTxt = (EditText) view.findViewById(R.id.RicepriceTxt);
        RicepriceTxt.setTypeface(tf);
        TotalWealthTxt = (EditText) view.findViewById(R.id.TotalWealthTxt);
        TotalWealthTxt.setTypeface(tf);
        DebtTxt = (EditText) view.findViewById(R.id.DebtTxt);
        DebtTxt.setTypeface(tf);
        GoldpriceTxt = (EditText) view.findViewById(R.id.GoldpriceTxt);
        GoldpriceTxt.setTypeface(tf);

        NetIncometxt = (TextView) view.findViewById(R.id.NetIncometxt);
        NetIncometxt.setTypeface(tf);
        ProfNisabTxt = (TextView) view.findViewById(R.id.ProfNisabTxt);
        ProfNisabTxt.setTypeface(tf);
        ProfEligibilityTxt = (TextView) view.findViewById(R.id.ProfEligibilityTxt);
        ProfEligibilityTxt.setTypeface(tf);
        ProfannuallyTxt = (TextView) view.findViewById(R.id.ProfannuallyTxt);
        ProfannuallyTxt.setTypeface(tf);
        ProfmonthlyTxt = (TextView) view.findViewById(R.id.ProfmonthlyTxt);
        ProfmonthlyTxt.setTypeface(tf);
        WeatlhTxt = (TextView) view.findViewById(R.id.WeatlhTxt);
        WeatlhTxt.setTypeface(tf);
        MaalNisabTxt = (TextView) view.findViewById(R.id.MaalNisabTxt);
        MaalNisabTxt.setTypeface(tf);
        MaalEligibilityTxt = (TextView) view.findViewById(R.id.MaalEligibilityTxt);
        MaalEligibilityTxt.setTypeface(tf);
        MaalannuallyTxt = (TextView) view.findViewById(R.id.MaalannuallyTxt);
        MaalannuallyTxt.setTypeface(tf);
        MaalmonthlyTxt = (TextView) view.findViewById(R.id.MaalmonthlyTxt);
        MaalmonthlyTxt.setTypeface(tf);


        Budget = getUser.getBudget();
        Installment = getUser.getInstallment();
        TotalWealth = getUser.getTotalWealth();
        Debt = getUser.getDebt();
        Addincome = db.countIncomes(Datecheck);
        Riceprice = 6000;
        Goldprice = 530000;

        BudgetTxt.setText(numberFormatIDR.format(Budget));
        InstallmentTxt.setText(numberFormatIDR.format(Installment));
        TotalWealthTxt.setText(numberFormatIDR.format(TotalWealth));
        DebtTxt.setText(numberFormatIDR.format(Debt));
        AddincomeTxt.setText(numberFormatIDR.format(Addincome));
        RicepriceTxt.setText(numberFormatIDR.format(Riceprice));
        GoldpriceTxt.setText(numberFormatIDR.format(Goldprice));

        //zakat profesi
        NetIncome = Budget + Addincome;
        NetIncome = NetIncome - Installment;
        NetIncometxt.setText(numberFormatIDR.format(NetIncome));

        NisabZakatProfesi = 520*Riceprice;
        ProfNisabTxt.setText(numberFormatIDR.format(NisabZakatProfesi));

        if(NetIncome > NisabZakatProfesi)
        {
            ProfEligibility = true;
            ProfEligibilityTxt.setText("Yes");
        }
        else
        {
            ProfEligibility = false;
            ProfEligibilityTxt.setText("No");
        }

        if(ProfEligibility == true)
        {
            zakatprofesi = NetIncome * 0.025;
            Profannually = Math.round(zakatprofesi) * 12;
            Profmonthly = Math.round(zakatprofesi);

            ProfannuallyTxt.setText(numberFormatIDR.format(Profannually));
            ProfmonthlyTxt.setText(numberFormatIDR.format(Profmonthly));

        }
        else
        {
            zakatprofesi = 0;
            Profannually = Math.round(zakatprofesi) * 12;
            Profmonthly = Math.round(zakatprofesi);
            ProfannuallyTxt.setText(numberFormatIDR.format(Profannually));
            ProfmonthlyTxt.setText(numberFormatIDR.format(Profmonthly));
        }

        //zakat maal
        HartaWajibZakat = TotalWealth - Debt;

        WeatlhTxt.setText(numberFormatIDR.format(HartaWajibZakat));
        GoldpriceTxt.setText(numberFormatIDR.format(Goldprice));

        NisabZakatMaal = 85*Goldprice;
        MaalNisabTxt.setText(numberFormatIDR.format(NisabZakatMaal));

        if(HartaWajibZakat > NisabZakatMaal)
        {
            MaalEligibility = true;
            MaalEligibilityTxt.setText("Yes");
        }
        else
        {
            MaalEligibility = false;
            MaalEligibilityTxt.setText("No");
        }

        if(MaalEligibility == true)
        {
            zakatmaal = HartaWajibZakat * 0.025;
            Maalannually = Math.round(zakatmaal);
            Maalmonthly = Math.round(zakatmaal)/12;
            MaalannuallyTxt.setText(numberFormatIDR.format(Maalannually));
            MaalmonthlyTxt.setText(numberFormatIDR.format(Maalmonthly));
        }
        else
        {
            zakatmaal = 0;
            Maalannually = Math.round(zakatmaal);
            Maalmonthly = Math.round(zakatmaal)/12;
            MaalannuallyTxt.setText(numberFormatIDR.format(Maalannually));
            MaalmonthlyTxt.setText(numberFormatIDR.format(Maalmonthly));
        }

        BudgetTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(BudgetTxt.getText().toString().equalsIgnoreCase(""))
                    {
                        BudgetTxt.setText(numberFormatIDR.format(Budget));
                    }
                    else {
                        Budget = Long.parseLong(BudgetTxt.getText().toString());
                        BudgetTxt.setText(numberFormatIDR.format(Budget));

                        NetIncome = Budget + Addincome;
                        NetIncome = NetIncome - Installment;
                        NetIncometxt.setText(numberFormatIDR.format(NetIncome));

                        if(NetIncome > NisabZakatProfesi)
                        {
                            ProfEligibility = true;
                            ProfEligibilityTxt.setText("Yes");
                        }
                        else
                        {
                            ProfEligibility = false;
                            ProfEligibilityTxt.setText("No");
                        }

                        if(ProfEligibility == true)
                        {
                            zakatprofesi = NetIncome * 0.025;
                            Profannually = Math.round(zakatprofesi) * 12;
                            Profmonthly = Math.round(zakatprofesi);

                            ProfannuallyTxt.setText(numberFormatIDR.format(Profannually));
                            ProfmonthlyTxt.setText(numberFormatIDR.format(Profmonthly));

                        }
                        else
                        {
                            zakatprofesi = 0;
                            Profannually = Math.round(zakatprofesi) * 12;
                            Profmonthly = Math.round(zakatprofesi);
                            ProfannuallyTxt.setText(numberFormatIDR.format(Profannually));
                            ProfmonthlyTxt.setText(numberFormatIDR.format(Profmonthly));
                        }
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

            }
        });


        AddincomeTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (AddincomeTxt.getText().toString().equalsIgnoreCase("")) {
                        AddincomeTxt.setText(numberFormatIDR.format(Addincome));
                    } else {
                        Addincome = Long.parseLong(AddincomeTxt.getText().toString());
                        AddincomeTxt.setText(numberFormatIDR.format(Addincome));

                        NetIncome = Budget + Addincome;
                        NetIncome = NetIncome - Installment;
                        NetIncometxt.setText(numberFormatIDR.format(NetIncome));

                        if (NetIncome > NisabZakatProfesi) {
                            ProfEligibility = true;
                            ProfEligibilityTxt.setText("Yes");
                        } else {
                            ProfEligibility = false;
                            ProfEligibilityTxt.setText("No");
                        }

                        if (ProfEligibility == true) {
                            zakatprofesi = NetIncome * 0.025;
                            Profannually = Math.round(zakatprofesi) * 12;
                            Profmonthly = Math.round(zakatprofesi);

                            ProfannuallyTxt.setText(numberFormatIDR.format(Profannually));
                            ProfmonthlyTxt.setText(numberFormatIDR.format(Profmonthly));

                        } else {
                            zakatprofesi = 0;
                            Profannually = Math.round(zakatprofesi) * 12;
                            Profmonthly = Math.round(zakatprofesi);
                            ProfannuallyTxt.setText(numberFormatIDR.format(Profannually));
                            ProfmonthlyTxt.setText(numberFormatIDR.format(Profmonthly));
                        }
                    }

                }

                return false;
            }
        });

        AddincomeTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    AddincomeTxt.setText("");
                } else {
                    AddincomeTxt.setText(numberFormatIDR.format(Addincome));
                }
            }
        });

        AddincomeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddincomeTxt.setText("");

            }
        });

        InstallmentTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (InstallmentTxt.getText().toString().equalsIgnoreCase("")) {
                        InstallmentTxt.setText(numberFormatIDR.format(Installment));
                    } else {
                        Installment = Long.parseLong(InstallmentTxt.getText().toString());
                        InstallmentTxt.setText(numberFormatIDR.format(Installment));

                        NetIncome = Budget + Addincome;
                        NetIncome = NetIncome - Installment;
                        NetIncometxt.setText(numberFormatIDR.format(NetIncome));

                        if (NetIncome > NisabZakatProfesi) {
                            ProfEligibility = true;
                            ProfEligibilityTxt.setText("Yes");
                        } else {
                            ProfEligibility = false;
                            ProfEligibilityTxt.setText("No");
                        }

                        if (ProfEligibility == true) {
                            zakatprofesi = NetIncome * 0.025;
                            Profannually = Math.round(zakatprofesi) * 12;
                            Profmonthly = Math.round(zakatprofesi);

                            ProfannuallyTxt.setText(numberFormatIDR.format(Profannually));
                            ProfmonthlyTxt.setText(numberFormatIDR.format(Profmonthly));

                        } else {
                            zakatprofesi = 0;
                            Profannually = Math.round(zakatprofesi) * 12;
                            Profmonthly = Math.round(zakatprofesi);
                            ProfannuallyTxt.setText(numberFormatIDR.format(Profannually));
                            ProfmonthlyTxt.setText(numberFormatIDR.format(Profmonthly));
                        }


                    }
                }

                return false;
            }
        });

        InstallmentTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InstallmentTxt.setText("");
                } else {
                    InstallmentTxt.setText(numberFormatIDR.format(Installment));
                }
            }
        });

        InstallmentTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstallmentTxt.setText("");

            }
        });

        TotalWealthTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (TotalWealthTxt.getText().toString().equalsIgnoreCase("")) {
                        TotalWealthTxt.setText(numberFormatIDR.format(TotalWealth));
                    } else {
                        TotalWealth = Long.parseLong(TotalWealthTxt.getText().toString());
                        TotalWealthTxt.setText(numberFormatIDR.format(TotalWealth));

                        HartaWajibZakat = TotalWealth - Debt;
                        WeatlhTxt.setText(numberFormatIDR.format(HartaWajibZakat));

                        if (HartaWajibZakat > NisabZakatMaal) {
                            MaalEligibility = true;
                            MaalEligibilityTxt.setText("Yes");
                        } else {
                            MaalEligibility = false;
                            MaalEligibilityTxt.setText("No");
                        }

                        if (MaalEligibility == true) {
                            zakatmaal = HartaWajibZakat * 0.025;
                            Maalannually = Math.round(zakatmaal);
                            Maalmonthly = Math.round(zakatmaal) / 12;
                            MaalannuallyTxt.setText(numberFormatIDR.format(Maalannually));
                            MaalmonthlyTxt.setText(numberFormatIDR.format(Maalmonthly));
                        } else {
                            zakatmaal = 0;
                            Maalannually = Math.round(zakatmaal);
                            Maalmonthly = Math.round(zakatmaal) / 12;
                            MaalannuallyTxt.setText(numberFormatIDR.format(Maalannually));
                            MaalmonthlyTxt.setText(numberFormatIDR.format(Maalmonthly));
                        }
                    }

                }
                return false;
            }
        });

        TotalWealthTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    TotalWealthTxt.setText("");
                } else {
                    TotalWealthTxt.setText(numberFormatIDR.format(TotalWealth));
                }
            }
        });

        TotalWealthTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TotalWealthTxt.setText("");

            }
        });

        DebtTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (DebtTxt.getText().toString().equalsIgnoreCase("")) {
                        DebtTxt.setText(numberFormatIDR.format(Debt));
                    } else {
                        Debt = Long.parseLong(DebtTxt.getText().toString());
                        DebtTxt.setText(numberFormatIDR.format(Debt));
                        HartaWajibZakat = TotalWealth - Debt;
                        WeatlhTxt.setText(numberFormatIDR.format(HartaWajibZakat));

                        if (HartaWajibZakat > NisabZakatMaal) {
                            MaalEligibility = true;
                            MaalEligibilityTxt.setText("Yes");
                        } else {
                            MaalEligibility = false;
                            MaalEligibilityTxt.setText("No");
                        }

                        if (MaalEligibility == true) {
                            zakatmaal = HartaWajibZakat * 0.025;
                            Maalannually = Math.round(zakatmaal);
                            Maalmonthly = Math.round(zakatmaal) / 12;
                            MaalannuallyTxt.setText(numberFormatIDR.format(Maalannually));
                            MaalmonthlyTxt.setText(numberFormatIDR.format(Maalmonthly));
                        } else {
                            zakatmaal = 0;
                            Maalannually = Math.round(zakatmaal);
                            Maalmonthly = Math.round(zakatmaal) / 12;
                            MaalannuallyTxt.setText(numberFormatIDR.format(Maalannually));
                            MaalmonthlyTxt.setText(numberFormatIDR.format(Maalmonthly));
                        }
                    }
                }
                return false;
            }
        });

        DebtTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DebtTxt.setText("");
                } else {
                    DebtTxt.setText(numberFormatIDR.format(Debt));
                }
            }
        });

        DebtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebtTxt.setText("");

            }
        });

        GoldpriceTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (GoldpriceTxt.getText().toString().equalsIgnoreCase("")) {
                        GoldpriceTxt.setText(numberFormatIDR.format(Goldprice));
                    } else {
                        Goldprice = Long.parseLong(GoldpriceTxt.getText().toString());
                        GoldpriceTxt.setText(numberFormatIDR.format(Goldprice));

                        NisabZakatMaal = 85 * Goldprice;
                        MaalNisabTxt.setText(numberFormatIDR.format(NisabZakatMaal));

                        if (HartaWajibZakat > NisabZakatMaal) {
                            MaalEligibility = true;
                            MaalEligibilityTxt.setText("Yes");
                        } else {
                            MaalEligibility = false;
                            MaalEligibilityTxt.setText("No");
                        }

                        if (MaalEligibility == true) {
                            zakatmaal = HartaWajibZakat * 0.025;
                            Maalannually = Math.round(zakatmaal);
                            Maalmonthly = Math.round(zakatmaal) / 12;
                            MaalannuallyTxt.setText(numberFormatIDR.format(Maalannually));
                            MaalmonthlyTxt.setText(numberFormatIDR.format(Maalmonthly));
                        } else {
                            zakatmaal = 0;
                            Maalannually = Math.round(zakatmaal);
                            Maalmonthly = Math.round(zakatmaal) / 12;
                            MaalannuallyTxt.setText(numberFormatIDR.format(Maalannually));
                            MaalmonthlyTxt.setText(numberFormatIDR.format(Maalmonthly));
                        }
                    }
                }
                return false;
            }
        });

        GoldpriceTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    GoldpriceTxt.setText("");
                } else {
                    GoldpriceTxt.setText(numberFormatIDR.format(Goldprice));
                }
            }
        });

        GoldpriceTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoldpriceTxt.setText("");
            }
        });

        RicepriceTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (RicepriceTxt.getText().toString().equalsIgnoreCase("")) {
                        RicepriceTxt.setText(numberFormatIDR.format(Riceprice));
                    } else {
                        Riceprice = Long.parseLong(RicepriceTxt.getText().toString());
                        RicepriceTxt.setText(numberFormatIDR.format(Riceprice));

                        NisabZakatProfesi = 520 * Riceprice;
                        ProfNisabTxt.setText(numberFormatIDR.format(NisabZakatProfesi));

                        if (NetIncome > NisabZakatProfesi) {
                            ProfEligibility = true;
                            ProfEligibilityTxt.setText("Yes");
                        } else {
                            ProfEligibility = false;
                            ProfEligibilityTxt.setText("No");
                        }

                        if (ProfEligibility == true) {
                            zakatprofesi = NetIncome * 0.025;
                            Profannually = Math.round(zakatprofesi) * 12;
                            Profmonthly = Math.round(zakatprofesi);

                            ProfannuallyTxt.setText(numberFormatIDR.format(Profannually));
                            ProfmonthlyTxt.setText(numberFormatIDR.format(Profmonthly));
                        } else {
                            zakatprofesi = 0;
                            Profannually = Math.round(zakatprofesi) * 12;
                            Profmonthly = Math.round(zakatprofesi);
                            ProfannuallyTxt.setText(numberFormatIDR.format(Profannually));
                            ProfmonthlyTxt.setText(numberFormatIDR.format(Profmonthly));
                        }
                    }
                }
                return false;
            }
        });

        RicepriceTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    RicepriceTxt.setText("");
                } else {
                    RicepriceTxt.setText(numberFormatIDR.format(Riceprice));
                }
            }
        });

        RicepriceTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RicepriceTxt.setText("");
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
