package com.MET.salman.MoeslemExpenseTracker;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeChartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeChartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PieChartView chart;
    private PieChartData data;

    DatabaseHandler db;
    MsUser getUser;

    ImageButton AddIncomeBtn;
    ImageButton AddExpenseBtn;


    private OnFragmentInteractionListener mListener;

    public HomeChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeChartFragment newInstance(String param1, String param2) {
        HomeChartFragment fragment = new HomeChartFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_chart, container, false);
        db = new DatabaseHandler(getActivity());

        AddIncomeBtn = (ImageButton) view.findViewById(R.id.AddIncomeBtn);
        AddExpenseBtn = (ImageButton) view.findViewById(R.id.AddExpenseBtn);

        getUser = db.getUser();

        long totalBudget = getUser.getBudget()+db.countIncomes(mParam1);
        long totalExpense = db.countExpenses(mParam1);


        chart = (PieChartView) view.findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());

        generateData(totalBudget, totalExpense);

        AddIncomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(getActivity(), NewTransaction.class);
                e.putExtra("position", 1);
                startActivity(e);
            }
        });

        AddExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent e = new Intent(getActivity(), NewTransaction.class);
                e.putExtra("position", 0);
                startActivity(e);
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
    public void onStart()
    {
        super.onStart();

        getUser = db.getUser();

        long totalBudget = getUser.getBudget()+db.countIncomes(mParam1);
        long totalExpense = db.countExpenses(mParam1);


        generateData(totalBudget, totalExpense);
    }

    public void onAttach(){
        super.onAttach(getContext());

        getUser = db.getUser();

        long totalBudget = getUser.getBudget()+db.countIncomes(mParam1);
        long totalExpense = db.countExpenses(mParam1);


        generateData(totalBudget, totalExpense);

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

    private void generateData(long totalBudget,long totalExpense) {
//        int numValues = 6;

        List<SliceValue> values = new ArrayList<SliceValue>();
//        for (int i = 0; i < numValues; ++i) {
//            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());
//            values.add(sliceValue);
//        }


        long remain = totalBudget-totalExpense;

        SliceValue IncomeCLR = new SliceValue(totalBudget, Color.parseColor("#EB5244"));
        values.add(IncomeCLR);

//        SliceValue IncomeCLR = new SliceValue(totalBudget, Color.parseColor("code"));

        SliceValue ExpenseCLR = new SliceValue(0, Color.parseColor("#FCAE28"));
        values.add(ExpenseCLR);

        data = new PieChartData(values);
        data.setHasCenterCircle(false);
//        data.setHasLabels(hasLabels);
//        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
//        data.setHasLabelsOutside(hasLabelsOutside);
//        data.setHasCenterCircle(hasCenterCircle);

//
//        if (hasCenterText1) {
//            data.setCenterText1("Hello!");
//
//            // Get roboto-italic font.
//            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
//            data.setCenterText1Typeface(tf);
//
//            // Get font size from dimens.xml and convert it to sp(library uses sp values).
//            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
//                    (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
//        }
//
//        if (hasCenterText2) {
//            data.setCenterText2("Charts (Roboto Italic)");
//
//            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
//
//            data.setCenterText2Typeface(tf);
//            data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
//                    (int) getResources().getDimension(R.dimen.pie_chart_text2_size)));
//        }

        IncomeCLR.setTarget(remain);
        ExpenseCLR.setTarget(totalExpense);
        chart.startDataAnimation();
        chart.setChartRotationEnabled(false);
        chart.setPieChartData(data);
    }

    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {

//            final SharedPreferences preferences = getActivity().getSharedPreferences("State", 0);
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putBoolean("StateON", true);
//            editor.apply();

            FragmentManager fragmentManager = getFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.ActivityFrg, new HomeFragment().newInstance(mParam1, null)).commit();

        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }
}
