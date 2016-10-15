package com.MET.salman.MoeslemExpenseTracker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String code,codeMaal,codeProf;
    DatabaseHandler db;
    DateFormat dateFormat;
    Date date;

    TextView BudgetTxt,StatusTxt,InstallmentTxt,TotalWealthTxt,DebtTxt;
    long Budget,Installment,TotalWealth,Debt;

    TableRow InstallmentRow,TotalWealthRow,DebtRow;
    TableRow ZakatMaalRow, ZakatProfRow, ZakatFitRow;
    ImageButton HelpBtn1,HelpBtn2,HelpBtn3,HelpBtn4;

    Calendar Cal;
    SimpleDateFormat sdf;
    String FormattedDate;

    long hargaEmas = 85 * 530000;
    double ZakatMaal;
    long NisabZakatProfesi = 520 * 6000;
    double ZakatProfesi;
    double ZakatFitrah = 3.5 * 6000;

    ZakatProfesi getZakatProf;


    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        db = new DatabaseHandler(getActivity());
        final MsUser getUser = db.getUser();
        dateFormat = new SimpleDateFormat("ddMMyyyy");
        date = new Date();

        Cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("MMMM yyyy");
        FormattedDate = sdf.format(Cal.getTime());

        HelpBtn1 = (ImageButton) view.findViewById(R.id.HelpBtn1);
        HelpBtn2 = (ImageButton) view.findViewById(R.id.HelpBtn2);
        HelpBtn3 = (ImageButton) view.findViewById(R.id.HelpBtn3);
        HelpBtn4 = (ImageButton) view.findViewById(R.id.HelpBtn4);

        InstallmentRow = (TableRow) view.findViewById(R.id.InstallmentRow);
        TotalWealthRow = (TableRow) view.findViewById(R.id.TotalWealthRow);
        DebtRow = (TableRow) view.findViewById(R.id.DebtRow);

        ZakatMaalRow = (TableRow) view.findViewById(R.id.ZakatMaalRow);
        ZakatProfRow = (TableRow) view.findViewById(R.id.ZakatProfRow);
        ZakatFitRow = (TableRow) view.findViewById(R.id.ZakatFitRow);


        BudgetTxt = (TextView) view.findViewById(R.id.BudgetTxt);
        StatusTxt = (TextView) view.findViewById(R.id.StatusTxt);
        InstallmentTxt = (TextView) view.findViewById(R.id.InstallmentTxt);
        TotalWealthTxt = (TextView) view.findViewById(R.id.TotalWealthTxt);
        DebtTxt = (TextView) view.findViewById(R.id.DebtTxt);

        Locale IDR = new Locale("in","ID");
        final NumberFormat numberFormatIDR = NumberFormat.getCurrencyInstance(IDR);

        BudgetTxt.setText(numberFormatIDR.format(getUser.getBudget()));
        StatusTxt.setText(getUser.getStatus());
        InstallmentTxt.setText(numberFormatIDR.format(getUser.getInstallment()));
        TotalWealthTxt.setText(numberFormatIDR.format(getUser.getTotalWealth()));
        DebtTxt.setText(numberFormatIDR.format(getUser.getDebt()));


        Budget = getUser.getBudget();
        Installment = getUser.getInstallment();
        TotalWealth = getUser.getTotalWealth();
        Debt = getUser.getDebt();

        HelpBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Information")
                        .setMessage("Turn On or Off Zakat Maal notification")
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
                new AlertDialog.Builder(getActivity())
                        .setTitle("Information")
                        .setMessage("Turn On or Off Zakat Profession notification")
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
                new AlertDialog.Builder(getActivity())
                        .setTitle("Information")
                        .setMessage("Turn On or Off Zakat Fitrah notification")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        HelpBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Information")
                        .setMessage("Turn On or Off Kurban notification")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });


        if(getUser.getStatus().equals("Employee"))
        {
            InstallmentRow.setVisibility(View.VISIBLE);
            TotalWealthRow.setVisibility(View.VISIBLE);
            DebtRow.setVisibility(View.VISIBLE);
            ZakatMaalRow.setVisibility(View.VISIBLE);
            ZakatProfRow.setVisibility(View.VISIBLE);
            ZakatFitRow.setVisibility(View.VISIBLE);
        }
        else if(getUser.getStatus().equals("Student"))
        {
            InstallmentRow.setVisibility(View.GONE);
            TotalWealthRow.setVisibility(View.GONE);
            DebtRow.setVisibility(View.GONE);
            ZakatMaalRow.setVisibility(View.GONE);
            ZakatProfRow.setVisibility(View.GONE);
            ZakatFitRow.setVisibility(View.VISIBLE);
        }


        StatusTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialogcategorylayout);
                ListView statuspick = (ListView) dialog.findViewById(R.id.listViewCategory);

                String[] values = new String[]{"Employee", "Student"};

                // use your custom layout
                MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getActivity(), values);
                statuspick.setAdapter(adapter);
                dialog.show();

                statuspick.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        String item = (String) parent.getItemAtPosition(position);
                        StatusTxt.setText(item);

                        code = getUser.getUserID();
                        Log.v("Code: ", code);
                        db.updateMsUser(new MsUser(code, Budget, item.toString(), Installment, TotalWealth, Debt));

                        if(item.equals("Employee"))
                        {
                            InstallmentRow.setVisibility(View.VISIBLE);
                            TotalWealthRow.setVisibility(View.VISIBLE);
                            DebtRow.setVisibility(View.VISIBLE);
                            ZakatMaalRow.setVisibility(View.VISIBLE);
                            ZakatProfRow.setVisibility(View.VISIBLE);
                        }
                        else if(item.equals("Student"))
                        {
                            InstallmentRow.setVisibility(View.GONE);
                            TotalWealthRow.setVisibility(View.GONE);
                            DebtRow.setVisibility(View.GONE);
                            ZakatMaalRow.setVisibility(View.GONE);
                            ZakatProfRow.setVisibility(View.GONE);
                        }

                        dialog.dismiss();
                    }
                });
            }
        });


        BudgetTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (BudgetTxt.getText().toString().equalsIgnoreCase("")) {
                        BudgetTxt.setText(numberFormatIDR.format(Budget));
                    } else {
                        db.updateMsUser(new MsUser(getUser.getUserID()
                                , Long.parseLong(BudgetTxt.getText().toString())
                                , StatusTxt.getText().toString()
                                , Installment
                                , TotalWealth
                                , Debt));

                        Budget = Long.parseLong(BudgetTxt.getText().toString());
                        BudgetTxt.setText(numberFormatIDR.format(Budget));

                        getZakatProf = db.getZakatProfesi(FormattedDate);
//                        getZakatProf = db.getZakatProfesi();
                        int countProf = db.getZakatProfCount();

                        if(countProf > 0)
                        {
//                            ZakatProfesi = Budget - Installment;
//                            if(ZakatProfesi > NisabZakatProfesi)
//                            {
//                                ZakatProfesi = ZakatProfesi * 0.025;
//                            }
//                            else
//                            {
//                                ZakatProfesi = 0;
//                            }
//                            db.updateData(new ZakatProfesi(db.getZakatProfesi().getZakatID(), Math.round(ZakatProfesi)));

                            if(getZakatProf.getDate().equals(FormattedDate)) {
                                ZakatProfesi = Budget - Installment;
                                if (ZakatProfesi > NisabZakatProfesi) {
                                    ZakatProfesi = ZakatProfesi * 0.025;
                                } else {
                                    ZakatProfesi = 0;
                                }

                                int countFitProf = db.getZakatFitCount() + 1;
                                codeProf = "P" + dateFormat.format(date) + countFitProf;
                                db.updateData(new ZakatProfesi(getZakatProf.getZakatID(), Math.round(ZakatProfesi), FormattedDate));
                            }
                            else
                            {
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
                                db.AddZakatProf(new ZakatProfesi(codeProf, Math.round(ZakatProfesi), FormattedDate));
                            }
                        }
                        else
                        {
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
                            db.AddZakatProf(new ZakatProfesi(codeProf, Math.round(ZakatProfesi), FormattedDate));
//                            db.AddZakatProf(new ZakatProfesi(codeProf, Math.round(ZakatProfesi)));
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

        InstallmentTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (InstallmentTxt.getText().toString().equalsIgnoreCase("")) {
                        InstallmentTxt.setText(numberFormatIDR.format(Installment));
                    } else {
                        db.updateMsUser(new MsUser(getUser.getUserID()
                                , Budget
                                , StatusTxt.getText().toString()
                                , Long.parseLong(InstallmentTxt.getText().toString())
                                , TotalWealth
                                , Debt));

                        Installment = Long.parseLong(InstallmentTxt.getText().toString());
                        InstallmentTxt.setText(numberFormatIDR.format(Installment));

                        getZakatProf = db.getZakatProfesi(FormattedDate);
//                        getZakatProf = db.getZakatProfesi();
                        int countProf = db.getZakatProfCount();

                        if(countProf > 0)
                        {

//                            ZakatProfesi = Budget - Installment;
//                            if(ZakatProfesi > NisabZakatProfesi)
//                            {
//                                ZakatProfesi = ZakatProfesi * 0.025;
//                            }
//                            else
//                            {
//                                    ZakatProfesi = 0;
//                            }
//                            db.updateData(new ZakatProfesi(db.getZakatProfesi().getZakatID(), Math.round(ZakatProfesi)));
                            if(getZakatProf.getDate().equals(FormattedDate)) {
                                ZakatProfesi = Budget - Installment;
                                if (ZakatProfesi > NisabZakatProfesi) {
                                    ZakatProfesi = ZakatProfesi * 0.025;
                                } else {
                                    ZakatProfesi = 0;
                                }

                                int countFitProf = db.getZakatFitCount() + 1;
                                codeProf = "P" + dateFormat.format(date) + countFitProf;
                                db.updateData(new ZakatProfesi(getZakatProf.getZakatID(), Math.round(ZakatProfesi), FormattedDate));
                            }
                            else
                            {
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
                                db.AddZakatProf(new ZakatProfesi(codeProf, Math.round(ZakatProfesi), FormattedDate));

                            }
                        }
                        else
                        {
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
                            db.AddZakatProf(new ZakatProfesi(codeProf, Math.round(ZakatProfesi), FormattedDate));
//                            db.AddZakatProf(new ZakatProfesi(codeProf, Math.round(ZakatProfesi)));
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
                        db.updateMsUser(new MsUser(getUser.getUserID()
                                , Budget
                                , StatusTxt.getText().toString()
                                , Installment
                                , Long.parseLong(TotalWealthTxt.getText().toString())
                                , Debt));

                        TotalWealth = Long.parseLong(TotalWealthTxt.getText().toString());
                        TotalWealthTxt.setText(numberFormatIDR.format(TotalWealth));

                        int countMaalX = db.getZakatMaalCount();
                        if(countMaalX > 0)
                        {

                            ZakatMaal = TotalWealth - Debt;
                            if(ZakatMaal > hargaEmas)
                            {
                                ZakatMaal = ZakatMaal * 0.025;
                            }
                            else
                            {
                                ZakatMaal = 0;
                            }
                            db.updateZakatMaal(new ZakatFitMal(db.getZakatMaal().getZakatID(), Math.round(ZakatMaal)));

                        }
                        else
                        {
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
                            db.AddZakatMaal(new ZakatFitMal(codeMaal, Math.round(ZakatMaal)));
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
                        db.updateMsUser(new MsUser(getUser.getUserID()
                                , Budget
                                , StatusTxt.getText().toString()
                                , Installment
                                , TotalWealth
                                , Long.parseLong(DebtTxt.getText().toString())));

                        Debt = Long.parseLong(DebtTxt.getText().toString());
                        DebtTxt.setText(numberFormatIDR.format(Debt));

                        int countMaalX = db.getZakatMaalCount();
                        if(countMaalX > 0)
                        {

                            ZakatMaal = TotalWealth - Debt;
                            if(ZakatMaal > hargaEmas)
                            {
                                ZakatMaal = ZakatMaal * 0.025;
                            }
                            else
                            {
                                ZakatMaal = 0;
                            }
                            db.updateZakatMaal(new ZakatFitMal(db.getZakatMaal().getZakatID(), Math.round(ZakatMaal)));

                        }
                        else
                        {
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
                            db.AddZakatMaal(new ZakatFitMal(codeMaal, Math.round(ZakatMaal)));
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


        Switch passcodeSwitch = (Switch) view.findViewById(R.id.PasscodeSwitch);
        final SharedPreferences preferences = getActivity().getSharedPreferences("passcode", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        boolean passcodeDialog = preferences.getBoolean("passcodeIsOn", false);
        if (!passcodeDialog){
            passcodeSwitch.setChecked(false);
        }
        else {

            passcodeSwitch.setChecked(true);
        }
        passcodeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    editor.putBoolean("passcodeIsOn", true);

                    editor.apply();

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.dialog_passcode);

                    final EditText passcode = (EditText) dialog.findViewById(R.id.passcode);
                    final EditText passcode2 = (EditText) dialog.findViewById(R.id.passcode2);
                    Button btnPasscode = (Button) dialog.findViewById(R.id.btn_passcode);

                    dialog.show();

                    btnPasscode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String pass = passcode.getText().toString();
                            String pass2 = passcode2.getText().toString();

                            if (pass.equalsIgnoreCase(pass2)) {
                                editor.putString("passCode", pass);
                                editor.apply();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "passcode harus sama", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    final Dialog dialog = new Dialog(getActivity());
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
                                editor.clear();
                                editor.apply();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "passcode harus sama", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        Switch ZakatMaalSwitch = (Switch) view.findViewById(R.id.ZakatMaalSwitch);
        Switch ZakatProfSwitch = (Switch) view.findViewById(R.id.ZakatProfSwitch);
        Switch ZakatFitSwitch = (Switch) view.findViewById(R.id.ZakatFitSwitch);
        Switch KurbanSwitch = (Switch) view.findViewById(R.id.KurbanSwitch);


        final SharedPreferences preferencesState = getActivity().getSharedPreferences("State", Context.MODE_PRIVATE);
        boolean ZakatMaal = preferencesState.getBoolean("ZakatMaalIsOn", false);
        boolean ZakatProf = preferencesState.getBoolean("ZakatProfIsOn", false);
        boolean ZakatFit = preferencesState.getBoolean("ZakatFitIsOn", false);
        boolean Kurban = preferencesState.getBoolean("KurbanIsOn", false);

        if(ZakatMaal)
        {
            ZakatMaalSwitch.setChecked(true);
        }
        else
        {
            ZakatMaalSwitch.setChecked(false);

        }

        if(ZakatProf)
        {
            ZakatProfSwitch.setChecked(true);
        }
        else
        {
            ZakatProfSwitch.setChecked(false);

        }

        if(ZakatFit)
        {
            ZakatFitSwitch.setChecked(true);
        }
        else
        {
            ZakatFitSwitch.setChecked(false);

        }

        if(Kurban)
        {
            KurbanSwitch.setChecked(true);
        }
        else
        {
            KurbanSwitch.setChecked(false);

        }

        ZakatMaalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    final SharedPreferences preferencesState = getActivity().getSharedPreferences("State", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorState = preferencesState.edit();
                    editorState.putBoolean("ZakatMaalIsOn",true);
                    editorState.apply();
                }
                else
                {
                    final SharedPreferences preferencesState = getActivity().getSharedPreferences("State", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorState = preferencesState.edit();
                    editorState.putBoolean("ZakatMaalIsOn",false);
                    editorState.apply();
                }
            }
        });

        ZakatProfSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    final SharedPreferences preferencesState = getActivity().getSharedPreferences("State", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorState = preferencesState.edit();
                    editorState.putBoolean("ZakatProfIsOn",true);
                    editorState.apply();
                }
                else
                {
                    final SharedPreferences preferencesState = getActivity().getSharedPreferences("State", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorState = preferencesState.edit();
                    editorState.putBoolean("ZakatProfIsOn",false);
                    editorState.apply();
                }
            }
        });

        ZakatFitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    final SharedPreferences preferencesState = getActivity().getSharedPreferences("State", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorState = preferencesState.edit();
                    editorState.putBoolean("ZakatFitIsOn",true);
                    editorState.apply();
                }
                else
                {
                    final SharedPreferences preferencesState = getActivity().getSharedPreferences("State", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorState = preferencesState.edit();
                    editorState.putBoolean("ZakatFitIsOn",false);
                    editorState.apply();
                }
            }
        });

        KurbanSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    final SharedPreferences preferencesState = getActivity().getSharedPreferences("State", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorState = preferencesState.edit();
                    editorState.putBoolean("KurbanIsOn",true);
                    editorState.apply();
                }
                else
                {
                    final SharedPreferences preferencesState = getActivity().getSharedPreferences("State", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorState = preferencesState.edit();
                    editorState.putBoolean("KurbanIsOn",false);
                    editorState.apply();
                }
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
