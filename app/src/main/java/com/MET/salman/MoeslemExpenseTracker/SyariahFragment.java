package com.MET.salman.MoeslemExpenseTracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SyariahFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SyariahFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SyariahFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    boolean expandZ = false, expandK = false, expandHM = false;
    boolean expandZ1 = false, expandZ2 = false, expandZ3 = false, expandZ4 = false;
    boolean expandK1 = false, expandK2 = false,expandK3 = false;
    boolean expandHUH = false, expandHUU = false;;

    Button ZakatBtn,KurbanBtn,HajiUmrahBtn;
    Button ZktMaalBtn, ZktProfBtn, ZktFitBtn,ZakatCalc;
    Button KurbanInfoBtn,JnskurbanBtn;
    Button UmrahBtn, HajiBtn;

    TextView ZktMaalTxt, ZktProfTxt, ZktFitTxt;
    TextView KurbanInfoTxt;
    TextView HajiTxt, UmrahTxt;

    TextView textView1,textView2,textView3;
    TextView MyZakatProf,MyZakatMaal,MyZakatFit;
    Button  MyZktBtn,PayZktProfBtn,PayZktMaalBtn,PayZktFitBtn;

    TextView textView4,textView5,textView6,textView7;
    TextView TotKurbanTxt,TotPayment,DateKurbantxt,StatusKurbantxt;
    Button PayKurbanBtn,MyKurbanBtn;

    LinearLayout ZakatDLayout,KurbanDLayout,HajiUmrahDLayout;

    RelativeLayout HajiContainerLayout,UmrahContainerLayout,KurbanContainerLayout,JnskurbanLayout,MyKurbanLayout;
    RelativeLayout MyZktLayout,ZktMaalLayout,ZktProfLayout,ZktFitLayout;

    SliderLayout sliderHaji,sliderUmrah;
    PagerIndicator sliderHajiIndicator,sliderUmrahIndicator;

    ImageView KambingImg, SapiImg, DombaImg;
    MediaPlayer mp1,mp2,mp3;

    NumberFormat numberFormatIDR;
    DatabaseHandler db;
    ZakatFitMal getZakatFit;
    ZakatFitMal getZakatMaal;
    ZakatProfesi getZakatProf;

    Calendar Cal;
    SimpleDateFormat sdf;
    String FormattedDate;

    MsUser getUser;

    private OnFragmentInteractionListener mListener;

    SharedPreferences zakatPreferences;
    SharedPreferences.Editor zakatEditor;
    long maalTot, fitTot, profTot;
    String maalMonth, fitMonth, profMonth;
    boolean maalIsPaid, profIsPaid,fitIsPaid,kurbanIsPaid;
    String kurbanMonth;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SyariahFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SyariahFragment newInstance(String param1, String param2) {
        SyariahFragment fragment = new SyariahFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SyariahFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_syariah, container, false);
        db = new DatabaseHandler(getActivity());
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Young.ttf");
        Locale IDR = new Locale("in","ID");
        numberFormatIDR = NumberFormat.getCurrencyInstance(IDR);

        zakatPreferences = getActivity().getSharedPreferences("State", 0);
        zakatEditor = zakatPreferences.edit();

        Cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("MMMM yyyy");
        FormattedDate = sdf.format(Cal.getTime());

        //mp
        mp1 = MediaPlayer.create(getActivity(),R.raw.goatsound);
        mp2 = MediaPlayer.create(getActivity(),R.raw.cowsound);
        mp3 = MediaPlayer.create(getActivity(), R.raw.sheepsound);

        //Gif
        KambingImg = (ImageView) view.findViewById(R.id.KambingImg);
        SapiImg = (ImageView) view.findViewById(R.id.SapiImg);
        DombaImg = (ImageView) view.findViewById(R.id.DombaImg);

        Glide.with(this).load(R.drawable.kambinggif).fitCenter().crossFade().into(KambingImg);
        Glide.with(this).load(R.drawable.sapigif).fitCenter().crossFade().into(SapiImg);
        Glide.with(this).load(R.drawable.dombagif).fitCenter().crossFade().into(DombaImg);

        KambingImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp1.start();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new JenisKurban().newInstance("Kambing", null)).commit();
            }
        });
        SapiImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp2.start();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new JenisKurban().newInstance("Sapi", null)).commit();
            }
        });

        DombaImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp3.start();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new JenisKurban().newInstance("Domba", null)).commit();
            }
        });


        //Slider
        sliderHaji = (SliderLayout)view.findViewById(R.id.sliderHaji);
        sliderHajiIndicator = (PagerIndicator) view.findViewById(R.id.sliderHajiIndicator);
        HashMap<String,Integer> file_mapsHaji = new HashMap<String, Integer>();
        file_mapsHaji.put("Ihram cloth", R.drawable.ihram);
        file_mapsHaji.put("Mabid", R.drawable.mabid);
        file_mapsHaji.put("Wukuf 1", R.drawable.wukuf1);
        file_mapsHaji.put("Wukuf 2", R.drawable.wukuf2);
        file_mapsHaji.put("Jumrah Stone", R.drawable.ambilbatu);
        file_mapsHaji.put("Jumrah 1", R.drawable.jumrah1);
        file_mapsHaji.put("Jumrah 2", R.drawable.jumrah2);
        file_mapsHaji.put("Tawaf", R.drawable.tawaf);
        file_mapsHaji.put("sai", R.drawable.sai1);
        file_mapsHaji.put("sai", R.drawable.sai2);
        file_mapsHaji.put("Tahalul 1", R.drawable.tahalul1);
        file_mapsHaji.put("Tahalul 2", R.drawable.tahalul2);


        for(String name : file_mapsHaji.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_mapsHaji.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
//                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            sliderHaji.addSlider(textSliderView);
        }
        sliderHaji.setPresetTransformer(SliderLayout.Transformer.FlipPage);
        sliderHaji.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderHaji.setCustomAnimation(new DescriptionAnimation());
        sliderHaji.setDuration(4000);

        sliderUmrah = (SliderLayout)view.findViewById(R.id.sliderUmrah);
        sliderUmrahIndicator = (PagerIndicator) view.findViewById(R.id.sliderUmrahIndicator);
        HashMap<String,Integer> file_mapsUmrah = new HashMap<String, Integer>();
        file_mapsUmrah.put("Ihram", R.drawable.ihram);
        file_mapsUmrah.put("tawaf", R.drawable.tawaf);
        file_mapsUmrah.put("Shalat Sunnah", R.drawable.shalatsunnah);
        file_mapsUmrah.put("Drink Zam - Zam", R.drawable.zamzam);
        file_mapsUmrah.put("Sai 1", R.drawable.sai1);
        file_mapsUmrah.put("Sai 2", R.drawable.sai2);
        file_mapsUmrah.put("Tahalul 1", R.drawable.tahalul1);
        file_mapsUmrah.put("Tahalul 2", R.drawable.tahalul2);

        for(String name : file_mapsUmrah.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_mapsUmrah.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
//                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            sliderUmrah.addSlider(textSliderView);
        }
        sliderUmrah.setPresetTransformer(SliderLayout.Transformer.FlipPage);
        sliderUmrah.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderUmrah.setCustomAnimation(new DescriptionAnimation());
        sliderUmrah.setDuration(4000);



        //Zakat
        ZakatDLayout = (LinearLayout) view.findViewById(R.id.ZakatDLayout);
        ZktMaalLayout = (RelativeLayout) view.findViewById(R.id.ZktMaalLayout);
        ZktProfLayout = (RelativeLayout) view.findViewById(R.id.ZktProfLayout);
        ZktFitLayout = (RelativeLayout) view.findViewById(R.id.ZktFitLayout);
        MyZktLayout = (RelativeLayout) view.findViewById(R.id.MyZktLayout);

        ZakatBtn = (Button) view.findViewById(R.id.ZakatBtn);
        ZakatBtn.setTypeface(tf);

        ZktMaalBtn = (Button) view.findViewById(R.id.ZktMaalBtn);
        ZktMaalBtn.setTypeface(tf);

        ZktMaalTxt = (TextView) view.findViewById(R.id.ZktMaalTxt);
        ZktMaalTxt.setTypeface(tf);
        ZktMaalTxt.setText(Html.fromHtml(
                "<p><b>Zakat Mal</b> (Arabic: الزكاة المال; transliteration: zakat mal) is a zakat which is levied on property owned by the individual" +
                        " with the terms and conditions that were set out in syarak </p>" +
                        "<p>Treasure that must be paid zakat mall / zakat property: <br>" +
                        "- Gold<br>" +
                        "- Silver<br>" +
                        "- Savings<br>" +
                        "- Crops<br>" +
                        "- Livestock<br>" +
                        "- Business objects*<br>" +
                        "- Treasure findings.</p>" +
                        "<p>*business objects is the money from trade, merchandise and tools that generate business.</p><br>" +
                        "<p><b>Calculation formula for Zakat Maal</b></p>" +
                        "<p>Zakat Maal = 2,5% x amount of treasure which will be contributed for zakat that has been running for one year (haul)" +
                        " calculated from the day of the nishab ownership.</p>" +
                        "<p>Calculating zakat maal nishab = 85 x gold price in market per gram</p>"));

        ZktProfBtn = (Button) view.findViewById(R.id.ZktProfBtn);
        ZktProfBtn.setTypeface(tf);

        ZktProfTxt = (TextView) view.findViewById(R.id.ZktProfTxt);
        ZktProfTxt.setTypeface(tf);
        ZktProfTxt.setText(Html.fromHtml(
                "<p><b>Profession Zakat</b> is excluded from profession income (profession results) when it has reached nisab.</p>" +
                        "<p><b>Calculation formula for profession zakat</b></p>" +
                        "<p>Profession Zakat = 2,5% x (Total income - Debt)</p>" +
                        "<p>Profession zakat nisab = 520 x rice price per kg</p>"));

        ZktFitBtn = (Button) view.findViewById(R.id.ZktFitBtn);
        ZktFitBtn.setTypeface(tf);

        ZktFitTxt = (TextView) view.findViewById(R.id.ZktFitTxt);
        ZktFitTxt.setTypeface(tf);
        ZktFitTxt.setText(Html.fromHtml(
                "<p><b>Zakat fitrah</b> is self zakat obligatory upon every individual Muslim men and women are capable with the requirements specified." +
                        " The word refers to the disposition of existing human condition when newly created so that by issuing this zakat," +
                        " man with God's permission would return Fitrah.</p>" +
                        "<p><b>Calculation formula for Zakat Fitrah</b></p>" +
                        "<p>Formula(for one person) = 3,5 x rice price in per liter</p>"));

        //Zakat calculator
        ZakatCalc = (Button) view.findViewById(R.id.ZakatCalc);
        ZakatCalc.setTypeface(tf);
        ZakatCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ActivityFrg, new ZakatCalculatorFragment().newInstance(null, null)).commit();

            }
        });


        //My Zakat
        textView1 = (TextView) view.findViewById(R.id.textView1);
        textView1.setTypeface(tf);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView2.setTypeface(tf);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        textView3.setTypeface(tf);
        MyZakatProf = (TextView) view.findViewById(R.id.MyZakatProf);
        MyZakatProf.setTypeface(tf);
        MyZakatMaal = (TextView) view.findViewById(R.id.MyZakatMaal);
        MyZakatMaal.setTypeface(tf);
        MyZakatFit = (TextView) view.findViewById(R.id.MyZakatFit);
        MyZakatFit.setTypeface(tf);
        MyZktBtn = (Button) view.findViewById(R.id.MyZktBtn);
        MyZktBtn.setTypeface(tf);
        PayZktProfBtn = (Button) view.findViewById(R.id.PayZktProfBtn);
        PayZktProfBtn.setTypeface(tf);
        PayZktMaalBtn = (Button) view.findViewById(R.id.PayZktMaalBtn);
        PayZktMaalBtn.setTypeface(tf);
        PayZktFitBtn = (Button) view.findViewById(R.id.PayZktFitBtn);
        PayZktFitBtn.setTypeface(tf);

        getZakatFit = db.getZakatFit();
        getZakatMaal = db.getZakatMaal();
        getZakatProf = db.getZakatProfesi(FormattedDate);

        MyZakatProf.setText(numberFormatIDR.format(getZakatProf.getAmount()));
        MyZakatMaal.setText(numberFormatIDR.format(Math.round(getZakatMaal.getAmount() / 12)));
        MyZakatFit.setText(numberFormatIDR.format(getZakatFit.getAmount()));

        boolean maalIsOn = zakatPreferences.getBoolean("ZakatMaalIsOn", false );
        maalIsPaid = zakatPreferences.getBoolean("maalPaid", false );
        profIsPaid = zakatPreferences.getBoolean("profPaid", false );
        fitIsPaid = zakatPreferences.getBoolean("fitPaid", false );
        kurbanIsPaid = zakatPreferences.getBoolean("kurbanPaid", false );

        getUser = db.getUser();

        if(getUser.getStatus().equals("Employee"))
        {
            textView1.setVisibility(View.VISIBLE);
            MyZakatProf.setVisibility(View.VISIBLE);
            PayZktProfBtn.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
            MyZakatMaal.setVisibility(View.VISIBLE);
            PayZktMaalBtn.setVisibility(View.VISIBLE);
            textView3.setVisibility(View.VISIBLE);
            MyZakatFit.setVisibility(View.VISIBLE);
            PayZktFitBtn.setVisibility(View.VISIBLE);
        }
        else if(getUser.getStatus().equals("Student"))
        {
            textView1.setVisibility(View.GONE);
            MyZakatProf.setVisibility(View.GONE);
            PayZktProfBtn.setVisibility(View.GONE);
            textView2.setVisibility(View.GONE);
            MyZakatMaal.setVisibility(View.GONE);
            PayZktMaalBtn.setVisibility(View.GONE);
            textView3.setVisibility(View.VISIBLE);
            MyZakatFit.setVisibility(View.VISIBLE);
            PayZktFitBtn.setVisibility(View.VISIBLE);
        }

        if(getZakatProf.getAmount() == 0)
        {
            PayZktProfBtn.setEnabled(false);
        }
        else
        {
            PayZktProfBtn.setEnabled(true);

        }

        if(getZakatMaal.getAmount() == 0)
        {
            PayZktMaalBtn.setEnabled(false);
        }
        else
        {
            PayZktMaalBtn.setEnabled(true);

        }

        if (maalIsPaid){
            PayZktMaalBtn.setEnabled(false);
        }
        if (profIsPaid){
            PayZktProfBtn.setEnabled(false);
        }
        if (fitIsPaid){
            PayZktFitBtn.setEnabled(false);
        }

        PayZktProfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Warning")
                        .setMessage("You are about to pay Zakat Profession for this month. Do you wish to continue?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Calendar Cal = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
                                profMonth = sdf.format(Cal.getTime());
                                long totalBudget = db.getUser().getBudget()+db.countIncomes(sdf.format(Cal.getTime()));
                                long totalExp = db.countExpenses(sdf.format(Cal.getTime()));
                                long remain = totalBudget-totalExp;

                                profTot = db.getZakatProfesi(sdf.format(Cal.getTime())).getAmount();
                                if(maalIsPaid){
                                    remain = remain - zakatPreferences.getLong("maalTot", 0);
                                }
                                if(fitIsPaid){
                                    remain = remain - zakatPreferences.getLong("fitTot", 0);
                                }
                                if(profIsPaid){
                                    remain = remain - zakatPreferences.getLong("profTot", 0);
                                }
                                if(kurbanIsPaid){
                                    remain = remain - zakatPreferences.getLong("kurbanTot", 0);
                                }
                                if (remain < profTot){
                                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                                            .setTitle("Error")
                                            .setMessage("You dont have enough budget!")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            })
                                            .show();
                                }
                                else {
                                    zakatEditor.putString("profMonth", profMonth);
                                    zakatEditor.putBoolean("profPaid", true);
                                    zakatEditor.putLong("profTot", profTot);
                                    zakatEditor.apply();
                                    PayZktProfBtn.setEnabled(false);

                                    maalIsPaid = zakatPreferences.getBoolean("maalPaid", false );
                                    profIsPaid = zakatPreferences.getBoolean("profPaid", false );
                                    fitIsPaid = zakatPreferences.getBoolean("fitPaid", false );
                                    kurbanIsPaid = zakatPreferences.getBoolean("kurbanPaid", false );

                                    if(profIsPaid)
                                    {
                                        new android.support.v7.app.AlertDialog.Builder(getActivity())
                                                .setTitle("Congratulation")
                                                .setMessage("You have completed Zakat Profession for this month!")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                })
                                                .show();
                                    }

                                    if(getUser.getStatus().equals("Employee"))
                                    {
                                        if(maalIsPaid && fitIsPaid && profIsPaid)
                                        {
                                            new android.support.v7.app.AlertDialog.Builder(getActivity())
                                                    .setTitle("Congratulation")
                                                    .setMessage("You have completed zakat for this month!")
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                        }
                                                    })
                                                    .show();
                                         }
                                    }
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        PayZktMaalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Warning")
                        .setMessage("You are about to pay Zakat Maal for this month. Do you wish to continue?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Calendar Cal = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
                                long totalBudget = db.getUser().getBudget()+db.countIncomes(sdf.format(Cal.getTime()));
                                long totalExp = db.countExpenses(sdf.format(Cal.getTime()));
                                long remain = totalBudget-totalExp;

                                maalTot = Math.round(db.getZakatMaal().getAmount());
                                maalTot = maalTot/12;

                                if(maalIsPaid){
                                    remain = remain - zakatPreferences.getLong("maalTot", 0);
                                }
                                if(fitIsPaid){
                                    remain = remain - zakatPreferences.getLong("fitTot", 0);
                                }
                                if(profIsPaid){
                                    remain = remain - zakatPreferences.getLong("profTot", 0);
                                }
                                if(kurbanIsPaid){
                                    remain = remain - zakatPreferences.getLong("kurbanTot", 0);
                                }
                                if (remain < maalTot){
                                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                                            .setTitle("Error")
                                            .setMessage("You dont have enough budget!")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            })
                                            .show();
                                }
                                else {
                                    maalMonth = sdf.format(Cal.getTime());
                                    zakatEditor.putString("maalMonth", maalMonth);
                                    zakatEditor.putBoolean("maalPaid", true);
                                    zakatEditor.putLong("maalTot", maalTot);
                                    zakatEditor.apply();
                                    PayZktMaalBtn.setEnabled(false);
                                    maalIsPaid = zakatPreferences.getBoolean("maalPaid", false );
                                    profIsPaid = zakatPreferences.getBoolean("profPaid", false );
                                    fitIsPaid = zakatPreferences.getBoolean("fitPaid", false );
                                    kurbanIsPaid = zakatPreferences.getBoolean("kurbanPaid", false );
                                    if(maalIsPaid)
                                    {
                                        new android.support.v7.app.AlertDialog.Builder(getActivity())
                                                .setTitle("Congratulation")
                                                .setMessage("You have completed Zakat Maal for this month!")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                })
                                                .show();
                                    }
                                    if(getUser.getStatus().equals("Employee")) {
                                        if (maalIsPaid && fitIsPaid && profIsPaid) {
                                            new android.support.v7.app.AlertDialog.Builder(getActivity())
                                                    .setTitle("Congratulation")
                                                    .setMessage("You have completed zakat for this month!")
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                        }
                                                    })
                                                    .show();
                                        }
                                    }
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        PayZktFitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Warning")
                        .setMessage("You are about to pay Zakat Fitrah for this month. Do you wish to continue?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Calendar Cal = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
                                fitMonth = sdf.format(Cal.getTime());
                                long totalBudget = db.getUser().getBudget()+db.countIncomes(sdf.format(Cal.getTime()));
                                long totalExp = db.countExpenses(sdf.format(Cal.getTime()));
                                long remain = totalBudget-totalExp;

                                fitTot = db.getZakatFit().getAmount();
                                if(maalIsPaid){
                                    remain = remain - zakatPreferences.getLong("maalTot", 0);
                                }
                                if(fitIsPaid){
                                    remain = remain - zakatPreferences.getLong("fitTot", 0);
                                }
                                if(profIsPaid){
                                    remain = remain - zakatPreferences.getLong("profTot", 0);
                                }
                                if(kurbanIsPaid){
                                    remain = remain - zakatPreferences.getLong("kurbanTot", 0);
                                }
                                if (remain < fitTot){
                                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                                            .setTitle("Error")
                                            .setMessage("You dont have enough budget!")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            })
                                            .show();
                                }
                                else {
                                    zakatEditor.putString("fitMonth", fitMonth);
                                    zakatEditor.putBoolean("fitPaid", true);

                                    zakatEditor.putLong("fitTot", fitTot);
                                    zakatEditor.apply();
                                    PayZktFitBtn.setEnabled(false);
                                    maalIsPaid = zakatPreferences.getBoolean("maalPaid", false );
                                    profIsPaid = zakatPreferences.getBoolean("profPaid", false );
                                    fitIsPaid = zakatPreferences.getBoolean("fitPaid", false );
                                    kurbanIsPaid = zakatPreferences.getBoolean("kurbanPaid", false );

                                    if (fitIsPaid) {
                                        new android.support.v7.app.AlertDialog.Builder(getActivity())
                                                .setTitle("Congratulation")
                                                .setMessage("You have completed Zakat Fitrah for this month!")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                })
                                                .show();
                                    }

                                    if(getUser.getStatus().equals("Employee")) {
                                        if (maalIsPaid && fitIsPaid && profIsPaid) {
                                            new android.support.v7.app.AlertDialog.Builder(getActivity())
                                                    .setTitle("Congratulation")
                                                    .setMessage("You have completed zakat for this month!")
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                        }
                                                    })
                                                    .show();
                                        }
                                    }
                                    else if(getUser.getStatus().equals("Student"))
                                    {
                                        if (fitIsPaid) {
                                            new android.support.v7.app.AlertDialog.Builder(getActivity())
                                                    .setTitle("Congratulation")
                                                    .setMessage("You have completed zakat for this month!")
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                        }
                                                    })
                                                    .show();
                                        }
                                    }
                                }

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        //Kurban
        KurbanDLayout = (LinearLayout) view.findViewById(R.id.KurbanDLayout);
        KurbanContainerLayout = (RelativeLayout) view.findViewById(R.id.KurbanContainerLayout);
        JnskurbanLayout = (RelativeLayout) view.findViewById(R.id.JnskurbanLayout);
        MyKurbanLayout = (RelativeLayout) view.findViewById(R.id.MyKurbanLayout);

        KurbanBtn = (Button) view.findViewById(R.id.KurbanBtn);
        KurbanBtn.setTypeface(tf);

        KurbanInfoBtn = (Button) view.findViewById(R.id.KurbanInfobtn);
        KurbanInfoBtn.setTypeface(tf);

        JnskurbanBtn = (Button) view.findViewById(R.id.JnskurbanBtn);
        JnskurbanBtn.setTypeface(tf);

        PayKurbanBtn = (Button) view.findViewById(R.id.PayKurbanBtn);
        PayKurbanBtn.setTypeface(tf);

        MyKurbanBtn = (Button) view.findViewById(R.id.MyKurbanBtn);
        MyKurbanBtn.setTypeface(tf);

        KurbanInfoTxt = (TextView) view.findViewById(R.id.KurbanInfoTxt);
        KurbanInfoTxt.setTypeface(tf);
        KurbanInfoTxt.setText(Html.fromHtml(
                "<p><b>Qurbān</b> (Arabic: قربان‎) (or أ⁭ضحية Udhiyyah as referred to in Islamic Law) is the sacrifice of a livestock animal during Eid al-Adha.</p>" +
                "<p>The word is related to the Hebrew qorbān \"offering\" and Syriac qurbānā 'sacrifice'," +
                        "etymologised through the cognate Arabic triliteral as 'a way or means of approaching someone' or 'nearness'.</p>" +
                "<p>In Shariah, Udhiyya would refer to the sacrifice of a specific animal offered by a specific person " +
                        "on specific days to seek Allah's pleasure and reward.</p>" +
                "<p>The word qurban appears thrice in the Quran and in once in Surat Al-Ma'ida in reference to animal sacrifice.</p>" +
                "<p>In the other two places the Quran speaks of sacrifice in the general sense, referring to any act which may bring one closer to Allah.</p>" +
                "<p>Other appropriate terms are Dhabihah, Udhiyah and Nahar. A fifth term Zabah refers to normal Islamic slaughter outside the days of Udhiyah.</p>"));

        //My Kurban
        textView4 = (TextView) view.findViewById(R.id.textView4);
        textView4.setTypeface(tf);
        textView5 = (TextView) view.findViewById(R.id.textView5);
        textView5.setTypeface(tf);
        textView6 = (TextView) view.findViewById(R.id.textView6);
        textView6.setTypeface(tf);
        textView7 = (TextView) view.findViewById(R.id.textView7);
        textView7.setTypeface(tf);
        TotKurbanTxt = (TextView) view.findViewById(R.id.TotKurbanTxt);
        TotKurbanTxt.setTypeface(tf);
        TotPayment = (TextView) view.findViewById(R.id.TotPayment);
        TotPayment.setTypeface(tf);
        DateKurbantxt = (TextView) view.findViewById(R.id.DateKurbantxt);
        DateKurbantxt.setTypeface(tf);
        StatusKurbantxt = (TextView) view.findViewById(R.id.StatusKurbantxt);
        StatusKurbantxt.setTypeface(tf);

        int kurbanCount = db.getKurbanCount();

        if(kurbanCount != 0)
        {
            TotKurbanTxt.setText(db.getKurbanCount()+"");
        }
        else
        {
            TotKurbanTxt.setText("0");

        }
        TotPayment.setText(numberFormatIDR.format(db.CountKurban()));
        DateKurbantxt.setText(FormattedDate);
        StatusKurbantxt.setText("Not Paid");

        //Haji & Umrah
        HajiUmrahDLayout = (LinearLayout) view.findViewById(R.id.HajiUmrahDLayout);
        HajiContainerLayout = (RelativeLayout) view.findViewById(R.id.HajiContainerLayout);
        UmrahContainerLayout = (RelativeLayout) view.findViewById(R.id.UmrahContainerLayout);
        HajiUmrahBtn = (Button) view.findViewById(R.id.HajiUmrahBtn);
        HajiUmrahBtn.setTypeface(tf);

        UmrahBtn = (Button) view.findViewById(R.id.UmrahBtn);
        UmrahBtn.setTypeface(tf);
        HajiBtn = (Button) view.findViewById(R.id.HajiBtn);
        HajiBtn.setTypeface(tf);

        HajiTxt = (TextView) view.findViewById(R.id.HajiTxt);
        HajiTxt.setTypeface(tf);
        HajiTxt.setText(Html.fromHtml(
                "<p>The <b>Hajj</b> (/hædʒ/;Arabic: حج‎ Ḥaǧǧ 'pilgrimage') is an annual Islamic pilgrimage to Mecca, and a mandatory religious duty for Muslims" +
                        " that must be carried out at least once in their lifetime by all adult Muslims, who are physically and financially capable of" +
                        " undertaking the journey, and can support their family during their absence. It is one of the five pillars of Islam," +
                        " along side Shahadah, Salat, Zakat, and Fasting on Ramadhan.</p>" +
                "<p>The Hajj occurs from the 8th to 12th (or in some cases 13th) of Dhu al-Hijjah, the last month of the Islamic calendar.</p>" +
                "<p><b>Haji Order/Procedures</b></p>" +
                "<p>\n" +
                        "1. Before 8 Zulhijah, Muslims from around the world began to flock to carry out Tawaf Hajj at Masjid Al Haram, Makkah.<br><br>\n" +
                        "2. 8 Dhul-Hijjah, pilgrims spend the night in Mina. On the morning of 8th of Dhul-Hijjah, all Muslims wear Ihram (two pieces of cloth without seams as clothing Hajj), then intending Hajj, and read the readings Talbiyah. Jamaat then went to Mina, so that evening all the pilgrims must stay overnight in Mina.<br><br>\n" +
                        "3. 9 Dhul-Hijjah, the next morning all the pilgrims go to Arafat. Then the pilgrims carry Standing worship, namely silence and prayer in this vast desert until Maghrib come. When evening came, the pilgrims immediately headed and overnight Muzdalifah.<br><br>\n" +
                        "4. 10 Dhul-Hijjah, after a morning in Muzdalifah, the pilgrims rushed to Mina to practice their religion Jumrah Aqabah, ie stone throwing as many as seven times to the first pillar as a symbol of casting out demons. After shaving the hair or some hair, pilgrims can Tawaf Hajj (Hajj finish), or spend the night in Mina and implement jumrah connection (Ula and Wustha).<br><br>\n" +
                        "5. 11 Dhul-Hijjah, throwing jumrah connection (Ula) in the first pillar, the second pillar and third pillar.<br><br>\n" +
                        "6. 12 Dhul-Hijjah, throwing jumrah connection (Ula) in the first pillar, the second pillar and third pillar.<br><br>\n" +
                        "7. Before returning to their respective countries, pilgrims carry out Thawaf Wada' (farewell tawaf).<br>\n" + "</p>"
        ));

        UmrahTxt = (TextView) view.findViewById(R.id.UmrahTxt);
        UmrahTxt.setTypeface(tf);
        UmrahTxt.setText(Html.fromHtml(
                "</p>The <b>ʿUmrah</b> (Arabic: عمرة‎) is a pilgrimage to Mecca, Saudi Arabia, performed by Muslims that can be undertaken at any time of the year.</p>" +
                        "<p>In Arabic, Umrah means \"to visit a populated place\". In the Sharia, Umrah means to perform Tawaf round the Kaaba and Sa'i between" +
                        " Al-Safa and Al-Marwah, after assuming Ihram (a sacred state), either from a Miqat like Zu'l-Hulafa, Juhfa," +
                        " Qarnu'l-Manāzil, Yalamlam, Zāt-i-'Irq, Ibrahīm Mursīa, or a place in Hill.</p>" +
                        "<p>It is sometimes called the \"minor pilgrimage\" or \"lesser pilgrimage\". The Umrah is not compulsory but highly recommended.</p>" +
                        "<p><b>Umrah Order/Procedures</b></p>" +
                        "<p>\n" +
                        "1. Wear ihram<br><br>\n" +
                        "2. Perform a tawaf \"طواف\", which consists of circling the Kaaba seven times in an anticlockwise direction. Men are encouraged to do this three times at a hurried pace, followed by four times, more closely, at a leisurely pace.[1]<br><br>\n" +
                        "3. Perform a sa'i \"سعي\", which means rapidly walking seven times back and forth between the hills of Safa and Marwah. This is a re-enactment of Hajar's frantic search for water. The baby Ishmael cried and hit the ground with his foot (some versions of the story say that an angel scraped his foot or the tip of his wing along the ground), and water miraculously sprang forth. This source of water is today called the Well of Zamzam.<br><br>\n" +
                        "4. Perform a halq or taqsir, meaning a cutting of the hair. A taqsir is a partial shortening of the hair, whereas a halq is a complete shave of the head, except for women, as they cut a little amount of hair instead.<br>\n</p>"
        ));


        //OnclickListener
        ZakatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expandZ) {
                    ZakatBtn.setText("<");
                    ZakatDLayout.setVisibility(View.VISIBLE);
                    KurbanBtn.setVisibility(view.GONE);
                    HajiUmrahBtn.setVisibility(view.GONE);
                    expandZ = true;
                } else {
                    ZakatBtn.setText("ZAKAT");
                    ZakatDLayout.setVisibility(View.GONE);
                    KurbanBtn.setVisibility(view.VISIBLE);
                    HajiUmrahBtn.setVisibility(view.VISIBLE);

                    ZktMaalLayout.setVisibility(View.GONE);
                    ZktProfLayout.setVisibility(View.GONE);
                    ZktFitLayout.setVisibility(View.GONE);
                    MyZktLayout.setVisibility(View.GONE);

                    expandZ = false;
                    expandZ1 = false;
                    expandZ2 = false;
                    expandZ3 = false;
                    expandZ4 = false;

                }
            }
        });

        ZktMaalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expandZ1) {
                    ZktMaalLayout.setVisibility(View.VISIBLE);
                    expandZ1 = true;
                } else {
                    ZktMaalLayout.setVisibility(View.GONE);
                    expandZ1 = false;

                }
            }
        });

        ZktProfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expandZ2) {
                    ZktProfLayout.setVisibility(View.VISIBLE);
                    expandZ2 = true;
                } else {
                    ZktProfLayout.setVisibility(View.GONE);
                    expandZ2 = false;

                }
            }
        });

        ZktFitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!expandZ3) {
                    ZktFitLayout.setVisibility(View.VISIBLE);
                    expandZ3 = true;
                }
                else
                {
                    ZktFitLayout.setVisibility(View.GONE);
                    expandZ3 = false;

                }
            }
        });

        MyZktBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expandZ4) {
                    MyZktLayout.setVisibility(View.VISIBLE);
                    expandZ4 = true;
                } else {
                    MyZktLayout.setVisibility(View.GONE);
                    expandZ4 = false;

                }
            }
        });

        KurbanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!expandK) {
                    KurbanBtn.setText("<");
                    KurbanDLayout.setVisibility(View.VISIBLE);
                    ZakatBtn.setVisibility(view.GONE);
                    HajiUmrahBtn.setVisibility(view.GONE);
                    expandK = true;
                }
                else
                {
                    KurbanBtn.setText("KURBAN");
                    KurbanDLayout.setVisibility(View.GONE);
                    ZakatBtn.setVisibility(view.VISIBLE);
                    HajiUmrahBtn.setVisibility(view.VISIBLE);
                    KurbanContainerLayout.setVisibility(View.GONE);
                    JnskurbanLayout.setVisibility(View.GONE);
                    MyKurbanLayout.setVisibility(View.GONE);
                    expandK = false;
                    expandK1 = false;
                    expandK2 = false;
                    expandK3 = false;


                }
            }
        });

        KurbanInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!expandK1) {
                    KurbanContainerLayout.setVisibility(View.VISIBLE);
                    expandK1 = true;
                }
                else
                {
                    KurbanContainerLayout.setVisibility(View.GONE);
                    expandK1 = false;
                }
            }
        });

        JnskurbanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!expandK2) {
                    JnskurbanLayout.setVisibility(View.VISIBLE);
                    expandK2 = true;
                }
                else
                {
                    JnskurbanLayout.setVisibility(View.GONE);
                    expandK2 = false;
                }
            }
        });

        //My Kurban
        MyKurbanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expandK3) {
                    MyKurbanLayout.setVisibility(View.VISIBLE);
                    expandK3 = true;
                } else {
                    MyKurbanLayout.setVisibility(View.GONE);
                    expandK3 = false;
                }
            }
        });



        if (db.getKurbanCount() == 0){
            PayKurbanBtn.setEnabled(false);
        }
        else
        {
            PayKurbanBtn.setEnabled(true);
        }

        if (kurbanIsPaid){
            PayKurbanBtn.setEnabled(false);
        }

        PayKurbanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Warning")
                        .setMessage("You are about to pay Kurban for this month. Do you wish to continue?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Calendar Cal = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
                                kurbanMonth = sdf.format(Cal.getTime());
                                long totalBudget = db.getUser().getBudget()+db.countIncomes(sdf.format(Cal.getTime()));
                                long totalExp = db.countExpenses(sdf.format(Cal.getTime()));
                                long remain = totalBudget-totalExp;

                                long kurbanTot;

                                kurbanTot = db.CountKurban();
                                if(maalIsPaid){
                                    remain = remain - zakatPreferences.getLong("maalTot", 0);
                                }
                                if(fitIsPaid){
                                    remain = remain - zakatPreferences.getLong("fitTot", 0);
                                }
                                if(profIsPaid){
                                    remain = remain - zakatPreferences.getLong("profTot", 0);
                                }
                                if (remain < kurbanTot){
                                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                                            .setTitle("Error")
                                            .setMessage("You dont have enough budget!")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            })
                                            .show();
                                }
                                else {
                                    zakatEditor.putString("kurbanMonth", kurbanMonth);
                                    zakatEditor.putBoolean("kurbanPaid", true);
                                    zakatEditor.putLong("kurbanTot", kurbanTot);
                                    zakatEditor.apply();
                                    PayKurbanBtn.setEnabled(false);
                                    kurbanIsPaid = zakatPreferences.getBoolean("kurbanPaid", false);
                                    if (kurbanIsPaid) {
                                        new android.support.v7.app.AlertDialog.Builder(getActivity())
                                                .setTitle("Congratulation")
                                                .setMessage("You have completed Kurban for this month!")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                })
                                                .show();
                                    }
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        HajiUmrahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!expandHM) {
                    HajiUmrahBtn.setText("<");
                    HajiUmrahDLayout.setVisibility(View.VISIBLE);
                    KurbanBtn.setVisibility(view.GONE);
                    ZakatBtn.setVisibility(view.GONE);
                    expandHM = true;
                }
                else
                {
                    HajiUmrahBtn.setText("HAJI & UMRAH");
                    HajiUmrahDLayout.setVisibility(View.GONE);
                    KurbanBtn.setVisibility(view.VISIBLE);
                    ZakatBtn.setVisibility(view.VISIBLE);
                    HajiContainerLayout.setVisibility(View.GONE);
                    UmrahContainerLayout.setVisibility(View.GONE);
                    expandHM = false;
                    expandHUH = false;
                    expandHUU = false;
                }
            }
        });

        HajiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!expandHUH) {
                    HajiContainerLayout.setVisibility(View.VISIBLE);
                    expandHUH = true;
                }
                else
                {
                    HajiContainerLayout.setVisibility(View.GONE);
                    expandHUH = false;

                }
            }
        });

        UmrahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!expandHUU) {
                    UmrahContainerLayout.setVisibility(View.VISIBLE);
                    expandHUU = true;
                }
                else
                {
                    UmrahContainerLayout.setVisibility(View.GONE);
                    expandHUU = false;
                }
            }
        });

        //back button
        if(mParam1 != null) {
            if (mParam1.toString().equals("Kurban")) {
                KurbanBtn.setText("<");
                KurbanDLayout.setVisibility(View.VISIBLE);
                ZakatBtn.setVisibility(view.GONE);
                HajiUmrahBtn.setVisibility(view.GONE);
                expandK = true;
            } else if (mParam1.toString().equals("Zakat")) {
                ZakatBtn.setText("<");
                ZakatDLayout.setVisibility(View.VISIBLE);
                KurbanBtn.setVisibility(view.GONE);
                HajiUmrahBtn.setVisibility(view.GONE);
                expandZ = true;
            }
        }

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
