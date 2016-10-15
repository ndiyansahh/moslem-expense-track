package com.MET.salman.MoeslemExpenseTracker;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpenseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    getAllData getAllData;
    DatabaseHandler db;

    ArrayList<getAllData> list;
    DataViewAdapter adapter;
    ListView ListView;
    TextView txtIncome, txtExpense;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpenseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpenseFragment newInstance(String param1, String param2) {
        ExpenseFragment fragment = new ExpenseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ExpenseFragment() {
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

        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Young.ttf");
        txtExpense = (TextView) view.findViewById(R.id.txtExpense);
        txtIncome = (TextView) view.findViewById(R.id.txtIncome);

//        getAllData = new getAllData();
        db = new DatabaseHandler(getActivity());
        long totalExp = db.countExpenses(mParam1);
        long totalInc = db.countIncomes(mParam1);


        Locale IDR = new Locale("in","ID");
        NumberFormat numberFormatIDR = NumberFormat.getCurrencyInstance(IDR);

        txtExpense.setText(numberFormatIDR.format(totalExp));
        txtExpense.setTypeface(tf);
        txtIncome.setText(numberFormatIDR.format(totalInc));
        txtIncome.setTypeface(tf);

        list = db.getAllExpenses(mParam1);

        Log.d("cek",list+"");

        ListView = (ListView)view.findViewById(R.id.listViewExpense);

        // use your custom layout
        adapter = new DataViewAdapter(getActivity(), R.layout.transactionlayout ,list);
        ListView.setAdapter(adapter);

        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getAllData data = (com.MET.salman.MoeslemExpenseTracker.getAllData) parent.getItemAtPosition(position);

                Intent x = new Intent(getActivity(),EditDeleteForm.class);
                x.putExtra("ID",data.getId());
                startActivity(x);

//                Toast.makeText(getActivity(), data.getId(),Toast.LENGTH_SHORT).show();
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

    @Override
    public void onResume(){
        super.onResume();
        list = db.getAllExpenses(mParam1);
        long totalExp = db.countExpenses(mParam1);
        long totalInc = db.countIncomes(mParam1);
        Locale IDR = new Locale("in","ID");
        NumberFormat numberFormatIDR = NumberFormat.getCurrencyInstance(IDR);

        txtExpense.setText(numberFormatIDR.format(totalExp));
        txtIncome.setText(numberFormatIDR.format(totalInc));

        adapter = new DataViewAdapter(getActivity(), R.layout.transactionlayout ,list);
        ListView.setAdapter(adapter);
    }

}
