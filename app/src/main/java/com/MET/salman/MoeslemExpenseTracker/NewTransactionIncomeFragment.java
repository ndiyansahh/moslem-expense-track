package com.MET.salman.MoeslemExpenseTracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewTransactionIncomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewTransactionIncomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewTransactionIncomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DatabaseHandler db;
    DateFormat dateFormat;
    Date date;

    SharedPreferences sharedPreferences;

    EditText DateTxt,CategoryTxt,IncomeTxt;

    Calendar Cal;
    SimpleDateFormat sdf,sdf2;
    String FormattedDate,FormattedDate2;

    String code,codeProf;
    String Datecheck;

    int years, month, day;
    Typeface tf;

    long income = 0;
    NumberFormat numberFormatIDR;
    MsUser getUser;
    ZakatProfesi getZakatProf;
    long NisabZakatProfesi = 520 * 6000;
    boolean profIsPaid;
    SharedPreferences zakatPreferences;
    SharedPreferences.Editor zakatEditor;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewTransactionIncomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewTransactionIncomeFragment newInstance(String param1, String param2) {
        NewTransactionIncomeFragment fragment = new NewTransactionIncomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NewTransactionIncomeFragment() {
        // Required empty public constructor
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_transaction_income, container, false);
        db = new DatabaseHandler(getActivity());
        tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Young.ttf");

        Locale IDR = new Locale("in","ID");
        numberFormatIDR = NumberFormat.getCurrencyInstance(IDR);

        TextView textView1 = (TextView) view.findViewById(R.id.textView2);
        textView1.setTypeface(tf);
        TextView textView2 = (TextView) view.findViewById(R.id.textView12);
        textView2.setTypeface(tf);
        TextView textView3 = (TextView) view.findViewById(R.id.textView3);
        textView3.setTypeface(tf);
        TextView textView4 = (TextView) view.findViewById(R.id.textView4);
        textView4.setTypeface(tf);



        ImageButton SaveBtn = (ImageButton) view.findViewById(R.id.SaveBtn);
        ImageButton CancelBtn = (ImageButton) view.findViewById(R.id.CancelBtn);

        //getCode from Sharedpreference
        sharedPreferences = getActivity().getSharedPreferences("CountExpInc", Context.MODE_PRIVATE);
        final String count = sharedPreferences.getString("countInc", "0");

        final int counts = Integer.parseInt(count)+1;


        dateFormat = new SimpleDateFormat("ddMMyyyy");
        date = new Date();
        code = dateFormat.format(date);
        code = mParam2 + "" + code + "" + counts;

        Cal = Calendar.getInstance();
        years = Cal.get(Calendar.YEAR);
        month = Cal.get(Calendar.MONTH);
        day = Cal.get(Calendar.DAY_OF_MONTH);

        sdf = new SimpleDateFormat("d MMMM yyyy");
        sdf2 = new SimpleDateFormat("MMMM yyyy");
        FormattedDate = sdf.format(Cal.getTime());
        FormattedDate2 = sdf2.format(Cal.getTime());


        DateTxt = (EditText) view.findViewById(R.id.DateTxt);
        DateTxt.setText(FormattedDate);
        DateTxt.setTypeface(tf);
        Datecheck = FormattedDate2;

        DateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        years = year;
                        month = monthOfYear;
                        day = dayOfMonth;

//                        Toast.makeText(getActivity(), year +""+month+""+day,Toast.LENGTH_SHORT).show();
                        String d, m, y;
                        String[] months = {"January", "February", "March", "April", "May", "June", "July",
                                "August", "September", "October", "November", "December"};

                        DateTxt.setText("" + day + " " + months[month] + " " + years);
                        code = mParam2 + "" + day + "" + (month + 1) + "" + years;
                        Datecheck = months[month] + " " + years;
                    }
                }, years, month, day).show();

            }
        });

        IncomeTxt = (EditText) view.findViewById(R.id.IncomeTxt);
        IncomeTxt.setTypeface(tf);

        IncomeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncomeTxt.setText("");

            }
        });

        IncomeTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (IncomeTxt.getText().toString().equalsIgnoreCase("") || IncomeTxt.getText().toString().equalsIgnoreCase("0")) {
                        income = 0;
                        IncomeTxt.setText(numberFormatIDR.format(income));
                    } else {
                        income = Long.parseLong(IncomeTxt.getText().toString());
                        IncomeTxt.setText(numberFormatIDR.format(income));
                    }

                }

                return false;
            }
        });

        IncomeTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    IncomeTxt.setText("");
                } else {
                    IncomeTxt.setText(numberFormatIDR.format(income));
                }
            }
        });


        CategoryTxt = (EditText) view.findViewById(R.id.CategoryTxt);

        CategoryTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialogcategorylayout);
                ListView category = (ListView) dialog.findViewById(R.id.listViewCategory);

                String[] values = new String[]{"Allowance", "Bonus", "Others"};

                // use your custom layout
                MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getActivity(), values);
                category.setAdapter(adapter);
                dialog.show();

                category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String item = (String) parent.getItemAtPosition(position);
                        CategoryTxt.setText(item);
                        CategoryTxt.setTypeface(tf);
                        dialog.dismiss();
                    }
                });
            }
        });

        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (IncomeTxt.getText().toString().equalsIgnoreCase("0") || IncomeTxt.getText().toString().equalsIgnoreCase("")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("'Amount' must be filled")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    IncomeTxt.setText("");
                                }
                            })
                            .show();

                } else if (CategoryTxt.getText().toString().equalsIgnoreCase("")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Category must be picked")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CategoryTxt.setText("");
                                }
                            })
                            .show();
                } else {

                    if(income == 0)
                    {
                        income = Long.parseLong(IncomeTxt.getText().toString());
                    }

                    zakatPreferences = getActivity().getSharedPreferences("State", 0);
                    zakatEditor = zakatPreferences.edit();
                    profIsPaid = zakatPreferences.getBoolean("profPaid", false);

                    if(!profIsPaid) {
                        getZakatProf = db.getZakatProfesi(Datecheck);
                        getUser = db.getUser();
                        long totalBudget = getUser.getBudget() + db.countIncomes(Datecheck);
                        totalBudget = totalBudget + income;
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
                            codeProf = "P" + dateFormat.format(date) + countProf;
                            db.updateData(new ZakatProfesi(db.getZakatProfesi(Datecheck).getZakatID(), Math.round(ZakatProfesi), Datecheck));

                        } else {
                            int countProf = db.getZakatFitCount() + 1;
                            codeProf = "P" + dateFormat.format(date) + countProf;
                            db.AddZakatProf(new ZakatProfesi(codeProf, Math.round(ZakatProfesi), Datecheck));
                        }
                    }
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("countInc",counts+"");
                    editor.apply();

                    db.AddIncome(new MsIncome(code, DateTxt.getText().toString(), income
                            , CategoryTxt.getText().toString()));
                    getActivity().finish();
                }

            }
        });

        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
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
        public void onFragmentInteraction(Uri uri);
    }

}
