package com.MET.salman.MoeslemExpenseTracker;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DatabaseHandler db;
    TextView BudgetNomTxt, totalExpenseTxt, remainingBudgetTxt;
    TextView textView13,textView6, textView;
    SharedPreferences.Editor editor;

    ListView listView;
    ArrayList<getAllData> list;
    MsUser getUser;

    ZakatFitMal getZakatFit;
    ZakatFitMal getZakatMaal;
    ZakatProfesi getZakatProf;

    DataViewHomeAdapter adapter;
    SharedPreferences zakatPreferences;
    SharedPreferences.Editor zakatEditor;
    boolean maalIsPaid, fitIsPaid, profIsPaid, kurbanIsPaid;
    String maalMonth, fitMonth, profMonth, kurbanMonth;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        db = new DatabaseHandler(getActivity());
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Young.ttf");

        zakatPreferences = getActivity().getSharedPreferences("State", 0);
        zakatEditor = zakatPreferences.edit();

        maalIsPaid = zakatPreferences.getBoolean("maalPaid", false);
        profIsPaid = zakatPreferences.getBoolean("profPaid", false );
        fitIsPaid = zakatPreferences.getBoolean("fitPaid", false );
        kurbanIsPaid = zakatPreferences.getBoolean("kurbanPaid", false );
        getZakatProf = db.getZakatProfesi(mParam1);
        getZakatFit = db.getZakatFit();
        getZakatMaal = db.getZakatMaal();

        /*
        sharedPreferences = getActivity().getSharedPreferences("AdditionalIncomeOn", 0);
        editor = sharedPreferences.edit();
        //boolean flag = sharedPreferences.getBoolean("start",null);
        if(sharedPreferences.getBoolean("start", true))
        {
            AddIncomeBtn.setVisibility(View.INVISIBLE);
        }
        */
        textView13 = (TextView) view.findViewById(R.id.textView13);
        textView13.setTypeface(tf);
        textView6 = (TextView) view.findViewById(R.id.textView6);
        textView6.setTypeface(tf);
        textView = (TextView) view.findViewById(R.id.textView);
        textView.setTypeface(tf);


        BudgetNomTxt = (TextView) view.findViewById(R.id.BudgetNomTxt);
        totalExpenseTxt = (TextView) view.findViewById(R.id.totalExpense);
        remainingBudgetTxt = (TextView) view.findViewById(R.id.remainingBudget);
        BudgetNomTxt.setTypeface(tf);
        totalExpenseTxt.setTypeface(tf);
        remainingBudgetTxt.setTypeface(tf);

        list = db.getOnlyExpenses(mParam1);

        Log.d("cek",list+"");

        listView = (ListView)view.findViewById(R.id.listHomeExpense);

        // use your custom layout
        adapter = new DataViewHomeAdapter(getActivity(), R.layout.transactionlayout ,list);
        listView.setAdapter(adapter);

        totalExpenseTxt.setText("0");
        String month = mParam1;
        Locale IDR = new Locale("in","ID");
        NumberFormat numberFormatIDR = NumberFormat.getCurrencyInstance(IDR);

        long totalExpense = db.countExpenses(mParam1);
        totalExpenseTxt.setText(numberFormatIDR.format(totalExpense));

        getUser = db.getUser();

        long totalBudget = getUser.getBudget()+db.countIncomes(mParam1);


        BudgetNomTxt.setText(numberFormatIDR.format(totalBudget));
        long remain = totalBudget-totalExpense;

        maalMonth = zakatPreferences.getString("maalMonth", "");
        fitMonth = zakatPreferences.getString("fitMonth", "");
        profMonth = zakatPreferences.getString("profMonth", "");
        kurbanMonth = zakatPreferences.getString("kurbanMonth", "");


        if(profMonth.equals(mParam1)) {
            if (profIsPaid) {
                remain = remain - getZakatProf.getAmount();
                Log.v("" + remain, " Profesi");
            }
        }
        if (maalMonth.equals(mParam1))
        {
            if(maalIsPaid)
            {
                remain = remain - (Math.round(getZakatMaal.getAmount()/12));
                Log.v(""+remain," Maal");
            }
        }

        if(fitMonth.equals(mParam1)) {

            if (fitIsPaid) {
                remain = remain - getZakatFit.getAmount();
                Log.v("" + remain, " Fitrah");
            }
        }

        if(kurbanMonth.equals(mParam1)) {

            if (kurbanIsPaid) {
                remain = remain - db.CountKurban();
                Log.v("" + remain, " Kurban");
            }
        }
        remainingBudgetTxt.setText(numberFormatIDR.format(remain));

        Log.e("Budget ", numberFormatIDR.format(getUser.getBudget()));


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

    public void onAttach(){
        super.onAttach(getContext());

        list = db.getOnlyExpenses(mParam1);
        // use your custom layout
        adapter = new DataViewHomeAdapter(getActivity(), R.layout.expense_home_layout ,list);
        listView.setAdapter(adapter);

        getUser = db.getUser();
        long totalBudget = getUser.getBudget()+db.countIncomes(mParam1);
        Locale IDR = new Locale("in","ID");
        NumberFormat numberFormatIDR = NumberFormat.getCurrencyInstance(IDR);
        BudgetNomTxt.setText(numberFormatIDR.format(totalBudget));

        long totalExpense = db.countExpenses(mParam1);
        totalExpenseTxt.setText(numberFormatIDR.format(totalExpense));

        long remain = totalBudget-totalExpense;
        maalMonth = zakatPreferences.getString("maalMonth", "");
        fitMonth = zakatPreferences.getString("fitMonth", "");
        profMonth = zakatPreferences.getString("profMonth", "");
        kurbanMonth = zakatPreferences.getString("profMonth", "");

        if(profMonth.equals(mParam1)) {
            if (profIsPaid) {
                remain = remain - getZakatProf.getAmount();
                Log.v("" + remain, " Profesi");
            }
        }
        if (maalMonth.equals(mParam1))
        {
            if(maalIsPaid)
            {
                remain = remain - (Math.round(getZakatMaal.getAmount()/12));
                Log.v(""+remain," Maal");
            }
        }

        if(fitMonth.equals(mParam1)) {

            if (fitIsPaid) {
                remain = remain - getZakatFit.getAmount();
                Log.v("" + remain, " Fitrah");
            }
        }
        if(kurbanMonth.equals(mParam1)) {

            if (kurbanIsPaid) {
                remain = remain - db.CountKurban();
                Log.v("" + remain, " Kurban");
            }
        }
        remainingBudgetTxt.setText(numberFormatIDR.format(remain));
    }

    @Override
    public void onStart()
    {
        super.onStart();

        list = db.getOnlyExpenses(mParam1);
        // use your custom layout
        adapter = new DataViewHomeAdapter(getActivity(), R.layout.expense_home_layout ,list);
        listView.setAdapter(adapter);

        getUser = db.getUser();
        long totalBudget = getUser.getBudget()+db.countIncomes(mParam1);
        Locale IDR = new Locale("in","ID");
        NumberFormat numberFormatIDR = NumberFormat.getCurrencyInstance(IDR);
        BudgetNomTxt.setText(numberFormatIDR.format(totalBudget));

        long totalExpense = db.countExpenses(mParam1);
        totalExpenseTxt.setText(numberFormatIDR.format(totalExpense));

        long remain = totalBudget-totalExpense;
        maalMonth = zakatPreferences.getString("maalMonth", "");
        fitMonth = zakatPreferences.getString("fitMonth", "");
        profMonth = zakatPreferences.getString("profMonth", "");
        kurbanMonth = zakatPreferences.getString("kurbanMonth", "");

        if(profMonth.equals(mParam1)) {
            if (profIsPaid) {
                remain = remain - getZakatProf.getAmount();
                Log.v("" + remain, " Profesi");
            }
        }
        if (maalMonth.equals(mParam1))
        {
            if(maalIsPaid)
            {
                remain = remain - (Math.round(getZakatMaal.getAmount()/12));
                Log.v(""+remain," Maal");
            }
        }

        if(fitMonth.equals(mParam1)) {

            if (fitIsPaid) {
                remain = remain - getZakatFit.getAmount();
                Log.v("" + remain, " Fitrah");
            }
        }
        if(kurbanMonth.equals(mParam1)) {

            if (kurbanIsPaid) {
                remain = remain - db.CountKurban();
                Log.v("" + remain, " Kurban");
            }
        }
        remainingBudgetTxt.setText(numberFormatIDR.format(remain));
    }
}
