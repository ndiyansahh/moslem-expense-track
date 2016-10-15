package com.MET.salman.MoeslemExpenseTracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Salman on 11/1/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 11;

    // Database Name
    private static final String DATABASE_NAME = "DbMoslemBudget";

    // Contacts table name
    private static final String TABLE_MSUSER = "MsUser";
    private static final String TABLE_MSEXPENSE = "MsExpense";
    private static final String TABLE_MSADDINCOME = "MsAddIncome";
    private static final String TABLE_MSTRANSACTION = "MsTransaction";
    private static final String TABLE_DETAILTRANSACTION = "DetailTransaction";

    private static final String TABLE_ZAKAT = "Zakat";
    private static final String TABLE_ZAKATMAAL = "ZakatMaal";
    private static final String TABLE_ZAKATPROFESI = "ZakatProfesi";
    private static final String TABLE_ZAKATFITRAH = "ZakatFitrah";

    private static final String TABLE_KURBAN = "Kurban";

    // Contacts Table Columns names

    private static final String KURBAN_ID = "KurbanID";
    private static final String KURBAN_PRICE = "Price";
    private static final String KURBAN_PAYMENT = "Payment";
    private static final String KURBAN_DURATION = "Duration";

    private static final String ZAKAT_ID = "ZakatID";

    private static final String ZAKATMAAL_ID = "ZakatMaalID";
    private static final String AMOUNT_MAAL = "Amount";

    private static final String ZAKATPROF_ID = "ZakatProfesiID";
    private static final String AMOUNT_PROF = "Amount";
    private static final String DATE_PROF = "Date";

    private static final String ZAKATFIT_ID = "ZakatFitrahID";
    private static final String AMOUNT_FIT = "Amount";



    //MsUser
    private static final String USER_ID = "UserID";
    private static final String STATUS = "Status";
    private static final String BUDGET = "Budget";
    private static final String INSTALLMENT = "Installment";
    private static final String TOTWEALTH = "TotalWealth";
    private static final String DEBT = "Debt";

    //MsExpense
    private static final String EXPENSE_ID = "ExpenseID";
    private static final String EXPENSE_DATE = "ExpenseDate";
    private static final String EXPENSE = "Expense";
    private static final String EXPENSE_CATEGORY = "ExpenseCategory";

    //MsAddIncome
    private static final String INCOME_ID = "IncomeID";
    private static final String INCOME_DATE = "IncomeDate";
    private static final String INCOME = "Income";
    private static final String INCOME_CATEGORY = "IncomeCategory";

    //MsTransaction
    private static final String TRANSACTION_ID = "TransactionID";
    private static final String TRANSACTION_DATE = "TransactionDate";

    //DetailTransaction


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        String CREATE_MSUSER_TABLE = "CREATE TABLE " + TABLE_MSUSER +
//                "(" + STATUS  + " VARCHAR " + ")";
//
//        String CREATE_MSBUDGET_TABLE = "CREATE TABLE " + TABLE_MSBUDGET +
//                "(" + BUDGET_ID + " CHAR(20) PRIMARY KEY," + BUDGET_DATE + " DATE,"
//                + BUDGET + " INTEGER," + BUDGET_REMAIN + " INTEGER " + ")";

        String CREATE_MSUSER_TABLE = "CREATE TABLE " + TABLE_MSUSER +
                "(" + USER_ID + " CHAR(20) PRIMARY KEY," + BUDGET + " INTEGER," + STATUS  + " VARCHAR, " +
                INSTALLMENT + " INTEGER, " + TOTWEALTH + " INTEGER, " + DEBT + " INTEGER " + ")";

        String CREATE_MSEXPENSE_TABLE = "CREATE TABLE " + TABLE_MSEXPENSE +
                "(" + EXPENSE_ID + " CHAR(20) PRIMARY KEY," + EXPENSE_DATE + " DATE,"
                + EXPENSE + " INTEGER," + EXPENSE_CATEGORY + " VARCHAR " + ")";

        String CREATE_MSADDINCOME_TABLE = "CREATE TABLE " + TABLE_MSADDINCOME +
                "(" + INCOME_ID + " CHAR(20) PRIMARY KEY," + INCOME_DATE + " DATE,"
                + INCOME + " INTEGER," + INCOME_CATEGORY + " VARCHAR " + ")";

        String CREATE_MSTRANSACTION_TABLE = "CREATE TABLE " + TABLE_MSTRANSACTION +
                "(" + TRANSACTION_ID  + " CHAR(20) PRIMARY KEY, " + USER_ID + " CHAR(20) REFERENCES MsUser (UserID), "
                + TRANSACTION_DATE + " DATE " + ")";

        String CREATE_ZAKAT_TABLE = "CREATE TABLE " + TABLE_ZAKAT +
                "(" + ZAKAT_ID  + " CHAR(20) PRIMARY KEY, "
                + ZAKATMAAL_ID + " CHAR(20) REFERENCES ZakatMaal (ZakatMaalID), "
                + ZAKATPROF_ID + " CHAR(20) REFERENCES ZakatProfesi (ZakatProfesiID), "
                + ZAKATFIT_ID + " CHAR(20) REFERENCES ZakatFitrah (ZakatFitrahID) " + ")";
        String CREATE_DETAILTRANSACTION_TABLE = "CREATE TABLE " + TABLE_DETAILTRANSACTION +
                "(" + TRANSACTION_ID  + " CHAR(20) PRIMARY KEY REFERENCES MsTransaction (TransactionID), "
                + INCOME_ID + " CHAR(20) REFERENCES MsAddIncome (IncomeID), " + EXPENSE_ID + " CHAR(20) REFERENCES MsExpense (ExpenseID), "
                + ZAKAT_ID + " CHAR(20) REFERENCES Zakat (ZakatID) " + ")";

        String CREATE_ZAKATMAAL_TABLE = "CREATE TABLE " + TABLE_ZAKATMAAL +
                "(" + ZAKATMAAL_ID + " CHAR(20) PRIMARY KEY," + AMOUNT_MAAL + " INTEGER " + ")";

        String CREATE_ZAKATPROF_TABLE = "CREATE TABLE " + TABLE_ZAKATPROFESI +
                "(" + ZAKATPROF_ID + " CHAR(20) PRIMARY KEY," + AMOUNT_PROF + " INTEGER, " + DATE_PROF  + " DATE " + ")";

        String CREATE_ZAKATFIT_TABLE = "CREATE TABLE " + TABLE_ZAKATFITRAH +
                "(" + ZAKATFIT_ID + " CHAR(20) PRIMARY KEY," + AMOUNT_FIT + " INTEGER " + ")";

        String CREATE_KURBAN_TABLE = "CREATE TABLE " + TABLE_KURBAN +
                "(" + KURBAN_ID + " CHAR(50) PRIMARY KEY," + KURBAN_PRICE + " INTEGER, " + KURBAN_PAYMENT + " INTEGER, " +
                KURBAN_DURATION + " INTEGER " + ")";




        db.execSQL(CREATE_MSUSER_TABLE);
        db.execSQL(CREATE_MSEXPENSE_TABLE);
        db.execSQL(CREATE_MSADDINCOME_TABLE);
        db.execSQL(CREATE_MSTRANSACTION_TABLE);
        db.execSQL(CREATE_DETAILTRANSACTION_TABLE);

        db.execSQL(CREATE_ZAKAT_TABLE);
        db.execSQL(CREATE_ZAKATMAAL_TABLE);
        db.execSQL(CREATE_ZAKATPROF_TABLE);
        db.execSQL(CREATE_ZAKATFIT_TABLE);
        db.execSQL(CREATE_KURBAN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //All CRUD(Create, Read, Update, Delete) Operations

//
//    // Adding new contact
//    void AddMsUser(MsUser MsUser) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(STATUS, MsUser.getStatus());
//
//        // Inserting Row
//        db.insert(TABLE_MSUSER, null, values);
//        db.close(); // Closing database connection
//    }

//    void AddMsBudget(MsBudget MsBudget) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(BUDGET_ID, MsBudget.getBudgetID());
//        values.put(BUDGET_DATE, MsBudget.getBudgetDate());
//        values.put(BUDGET, MsBudget.getBudget());
//        values.put(BUDGET_REMAIN, MsBudget.getBudget());
//
//        // Inserting Row
//        db.insert(TABLE_MSBUDGET, null, values);
//        db.close(); // Closing database connection
//    }

    public int getKurbanCount()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM " + TABLE_KURBAN;

        Cursor cursor = db.rawQuery(countQuery,null);
        int cnt = cursor.getCount();
        return cnt;
    }

    void AddKurban(Kurban Kurban) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KURBAN_ID, Kurban.getKurbanID());
        values.put(KURBAN_PRICE, Kurban.getPrice());
        values.put(KURBAN_PAYMENT, Kurban.getPayment());
        values.put(KURBAN_DURATION, Kurban.getDuration());


        // Inserting Row
        db.insert(TABLE_KURBAN, null, values);
        db.close(); // Closing database connection
    }

    public int getZakatProfCount()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM " + TABLE_ZAKATPROFESI;

        Cursor cursor = db.rawQuery(countQuery,null);
        int cnt = cursor.getCount();
        return cnt;
    }

//    public ArrayList<ZakatProfesi> getZakatProfSearch()
//    {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_ZAKATPROFESI, new String[]{ZAKATPROF_ID, AMOUNT_PROF, DATE_PROF}, DATE_PROF + " LIKE ?",
//                new String[]{("%" + date)}, null, null, null, null);
//
//        if (cursor != null&&cursor.moveToFirst()) {
//
//            ZakatMaal = new ZakatFitMal(cursor.getString(0),Long.parseLong(cursor.getString(1)));
//            cursor.close();
//        }
//
//        int cnt = cursor.getCount();
//        return cnt;
//    }

    void AddZakatProf(ZakatProfesi ZakatProfesi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ZAKATPROF_ID, ZakatProfesi.getZakatID());
        values.put(AMOUNT_PROF, ZakatProfesi.getAmount());
        values.put(DATE_PROF, ZakatProfesi.getDate());


        // Inserting Row
        db.insert(TABLE_ZAKATPROFESI, null, values);
        db.close(); // Closing database connection
    }

    public int getZakatFitCount()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM " + TABLE_ZAKATFITRAH;

        Cursor cursor = db.rawQuery(countQuery,null);
        int cnt = cursor.getCount();
        return cnt;
    }

    void AddZakatFit(ZakatFitMal ZakatFitMal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ZAKATFIT_ID, ZakatFitMal.getZakatID());
        values.put(AMOUNT_FIT, ZakatFitMal.getAmount());


        // Inserting Row
        db.insert(TABLE_ZAKATFITRAH, null, values);
        db.close(); // Closing database connection
    }

    public int getZakatMaalCount()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM " + TABLE_ZAKATMAAL;

        Cursor cursor = db.rawQuery(countQuery,null);
        int cnt = cursor.getCount();
        return cnt;
    }

    void AddZakatMaal(ZakatFitMal ZakatFitMal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ZAKATMAAL_ID, ZakatFitMal.getZakatID());
        values.put(AMOUNT_MAAL, ZakatFitMal.getAmount());


        // Inserting Row
        db.insert(TABLE_ZAKATMAAL, null, values);
        db.close(); // Closing database connection
    }

        void AddMsUser(MsUser MsUser) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_ID, MsUser.getUserID());
        values.put(BUDGET, MsUser.getBudget());
        values.put(STATUS, MsUser.getStatus());
            values.put(INSTALLMENT, MsUser.getInstallment());
            values.put(TOTWEALTH, MsUser.getTotalWealth());
            values.put(DEBT, MsUser.getDebt());

        // Inserting Row
        db.insert(TABLE_MSUSER, null, values);
        db.close(); // Closing database connection
    }


    void AddExpense(MsExpense MsExpense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EXPENSE_ID, MsExpense.getExpenseID());
        values.put(EXPENSE_DATE, MsExpense.getExpenseDate());
        values.put(EXPENSE, MsExpense.getExpense());
        values.put(EXPENSE_CATEGORY, MsExpense.getExpenseCategory());

        // Inserting Row
        db.insert(TABLE_MSEXPENSE, null, values);
        db.close(); // Closing database connection
    }

    void AddIncome(MsIncome MsIncome) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INCOME_ID, MsIncome.getIncomeID());
        values.put(INCOME_DATE, MsIncome.getIncomeDate());
        values.put(INCOME, MsIncome.getIncome());
        values.put(INCOME_CATEGORY, MsIncome.getIncomeCategory());

        // Inserting Row
        db.insert(TABLE_MSADDINCOME, null, values);
        db.close(); // Closing database connection
    }
    // Getting single contact

//    MsBudget getBudget(String date) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        MsBudget budget = new MsBudget();
//        Cursor cursor = db.query(TABLE_MSBUDGET, new String[] { BUDGET_ID,
//                        BUDGET_DATE, BUDGET, BUDGET_REMAIN }, BUDGET_DATE + "=?",
//                new String[] { (date) }, null, null, null, null);
//        if (cursor != null&&cursor.moveToFirst()) {
//
//            budget = new MsBudget(cursor.getString(0),
//                    cursor.getString(1), Integer.parseInt(cursor.getString(2))
//                    ,Integer.parseInt(cursor.getString(3)));
//            cursor.close();
//        }
//        return budget;
//    }

//    public long getZakatMaalExp(String date, String category)
//    {
//        long countMaal = 0;
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.query(TABLE_MSEXPENSE, new String[]{EXPENSE_ID, EXPENSE_DATE, EXPENSE, EXPENSE_CATEGORY},
//                EXPENSE_CATEGORY + "LIKE ? AND " + EXPENSE_DATE + "LIKE ?" ,
//                new String[]{("%" + date),(category)} , null, null, null, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                countMaal += cursor.getLong(2);
//            } while (cursor.moveToNext());
//        }
//
//        db.close();
//
//        return countMaal;
//    }

    public long CountKurban()
    {
        long countKurban = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_KURBAN, new String[]{KURBAN_ID, KURBAN_PRICE, KURBAN_PAYMENT, KURBAN_DURATION},null,
                null , null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                countKurban += cursor.getLong(2);
            } while (cursor.moveToNext());
        }

        db.close();

        return countKurban;
    }

    ZakatFitMal getZakatFit() {
        SQLiteDatabase db = this.getReadableDatabase();
        ZakatFitMal ZakatFit = new ZakatFitMal();
        String selectQuery = "SELECT * FROM " + TABLE_ZAKATFITRAH;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null&&cursor.moveToFirst()) {

            ZakatFit = new ZakatFitMal(cursor.getString(0),Long.parseLong(cursor.getString(1)));
            cursor.close();
        }
        return ZakatFit;
    }

    ZakatFitMal getZakatMaal() {
        SQLiteDatabase db = this.getReadableDatabase();
        ZakatFitMal ZakatMaal = new ZakatFitMal();
        String selectQuery = "SELECT * FROM " + TABLE_ZAKATMAAL;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null&&cursor.moveToFirst()) {

            ZakatMaal = new ZakatFitMal(cursor.getString(0),Long.parseLong(cursor.getString(1)));
            cursor.close();
        }
        return ZakatMaal;
    }
//
//    ZakatProfesi getZakatProfesi() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        ZakatProfesi ZakatProf = new ZakatProfesi();
//        String selectQuery = "SELECT * FROM " + TABLE_ZAKATPROFESI;
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        if (cursor != null&&cursor.moveToFirst()) {
//
//            ZakatProf = new ZakatProfesi(cursor.getString(0),Long.parseLong(cursor.getString(1)));
//            cursor.close();
//        }
//        return ZakatProf;
//    }

    ZakatProfesi getZakatProfesi(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        ZakatProfesi ZakatProf = new ZakatProfesi();
        Cursor cursor = db.query(TABLE_ZAKATPROFESI, new String[]{ZAKATPROF_ID, AMOUNT_PROF, DATE_PROF}, DATE_PROF + " LIKE ?",
                new String[]{("%" + date)}, null, null, null, null);
        if (cursor != null&&cursor.moveToFirst()) {

            ZakatProf = new ZakatProfesi(cursor.getString(0), Long.parseLong(cursor.getString(1)), cursor.getString(2));
            cursor.close();
        }
        return ZakatProf;
    }

    public long updateData(ZakatProfesi getZakatProf) {
        SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
//            values.put(ZAKATPROF_ID, getZakatProf.getZakatID());
            values.put(AMOUNT_PROF, getZakatProf.getAmount());
//        return db.update(TABLE_ZAKATPROFESI, values, ZAKATPROF_ID + " = ?",
//                new String[] { getZakatProf.getZakatID() });
            return db.update(TABLE_ZAKATPROFESI, values, DATE_PROF + " = ?",
                    new String[] { getZakatProf.getDate() });

    }

    public long updateZakatMaal(ZakatFitMal getZakatMaal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AMOUNT_MAAL, getZakatMaal.getAmount());
        return db.update(TABLE_ZAKATMAAL, values, ZAKATMAAL_ID + " = ?",
                new String[] { getZakatMaal.getZakatID() });

    }

    MsUser getUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        MsUser user = new MsUser();
        String selectQuery = "SELECT * FROM " + TABLE_MSUSER;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null&&cursor.moveToFirst()) {

            user = new MsUser(cursor.getString(0), Long.parseLong(cursor.getString(1)), cursor.getString(2),
                    Long.parseLong(cursor.getString(3)),Long.parseLong(cursor.getString(4)),Long.parseLong(cursor.getString(5)));
            cursor.close();
        }
        return user;
    }

    getAllData getSingleData(String Id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        getAllData data = new getAllData();

            Cursor cursor = db.query(TABLE_MSEXPENSE, new String[]{EXPENSE_ID, EXPENSE_DATE, EXPENSE, EXPENSE_CATEGORY}, EXPENSE_ID + " LIKE ?"
                    , new String[]{(Id)}, null, null, null, null);

        if(Id.substring(0,1).equals("I"))
        {
             cursor = db.query(TABLE_MSADDINCOME, new String[]{INCOME_ID, INCOME_DATE, INCOME, INCOME_CATEGORY}, INCOME_ID + " LIKE ?"
                    , new String[]{(Id)}, null, null, null, null);
        }

        if (cursor != null&&cursor.moveToFirst()) {

            data = new getAllData(cursor.getString(0),
                    cursor.getString(1), Long.parseLong(cursor.getString(2)),cursor.getString(3));
            cursor.close();
        }
        db.close();
        return data;
    }

    public ArrayList<getAllData> getAllExpenses(String date)
    {
        ArrayList<getAllData> Datalist = new ArrayList<getAllData>();
        // Select All Query
//        String selectQuery = "SELECT * FROM " + TABLE_MSEXPENSE + " WHERE " +EXPENSE_DATE +" LIKE ?";
//        String[] selectionArgs = new String[]{}

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(TABLE_MSEXPENSE, new String[]{EXPENSE_ID, EXPENSE_DATE, EXPENSE, EXPENSE_CATEGORY}, EXPENSE_DATE + " LIKE ?",
                new String[]{("%" + date)}, null, null, null, null);

        Cursor cursor2 = db.query(TABLE_MSADDINCOME, new String[]{INCOME_ID, INCOME_DATE, INCOME, INCOME_CATEGORY}, INCOME_DATE + " LIKE ?",
                new String[]{("%" + date)}, null, null, null, null);

        if (cursor.moveToFirst()) {
        do {
            getAllData Expense = new getAllData();
            Expense.setId(cursor.getString(0));
            Expense.setDate(cursor.getString(1));
            Expense.setExpense(Long.parseLong(cursor.getString(2)));
            Expense.setCategory(cursor.getString(3));
            // Adding contact to list
            Datalist.add(Expense);
        } while (cursor.moveToNext());
    }

        if (cursor2.moveToFirst()) {
            do {
                getAllData Income = new getAllData();
                Income.setId(cursor2.getString(0));
                Income.setDate(cursor2.getString(1));
                Income.setExpense(Long.parseLong(cursor2.getString(2)));
                Income.setCategory(cursor2.getString(3));
                // Adding contact to list
                Datalist.add(Income);
            } while (cursor2.moveToNext());
        }

        Collections.sort(Datalist, new Comparator<getAllData>() {
            @Override
            public int compare(getAllData lhs, getAllData rhs) {
                return lhs.getDate().compareTo(rhs.getDate());
            }
        });

        db.close();
        return Datalist;
    }

    public void DeleteData(String Id) {
        SQLiteDatabase db = this.getWritableDatabase();

        if(Id.substring(0,1).equals("E"))
        {
            db.delete(TABLE_MSEXPENSE, EXPENSE_ID + " = ?",
                    new String[]{Id});
        }
        else if(Id.substring(0,1).equals("I"))
        {
            db.delete(TABLE_MSADDINCOME, INCOME_ID + " = ?",
                    new String[]{Id});
        }
        db.close();
    }

    public long updateData(getAllData getAllData) {
        SQLiteDatabase db = this.getWritableDatabase();


        if(getAllData.getId().substring(0, 1).equals("E"))
        {
            ContentValues values = new ContentValues();
            values.put(EXPENSE_DATE, getAllData.getDate());
            values.put(EXPENSE, getAllData.getExpense());
            values.put(EXPENSE_CATEGORY, getAllData.getCategory());
            return db.update(TABLE_MSEXPENSE, values, EXPENSE_ID + " = ?",
                    new String[] { getAllData.getId() });
        }
        else if(getAllData.getId().substring(0, 1).equals("I"))
        {
            ContentValues values = new ContentValues();
            values.put(INCOME_DATE, getAllData.getDate());
            values.put(INCOME, getAllData.getExpense());
            values.put(INCOME_CATEGORY, getAllData.getCategory());
            return db.update(TABLE_MSADDINCOME, values, INCOME_ID + " = ?",
                    new String[] { getAllData.getId() });
        }

        db.close();

        return 0;

    }

    public long updateMsUser(MsUser MsUser) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(USER_ID, MsUser.getUserID());
        values.put(BUDGET, MsUser.getBudget());
        values.put(STATUS, MsUser.getStatus());
        values.put(INSTALLMENT, MsUser.getInstallment());
        values.put(TOTWEALTH, MsUser.getTotalWealth());
        values.put(DEBT, MsUser.getDebt());

        Log.e("asdas", "asdas");
        // updating row
        return db.update(TABLE_MSUSER, values, USER_ID + " = ?",
                new String[] { String.valueOf(MsUser.getUserID()) });
    }

    public long countExpenses(String date)
    {
        long countExpense = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_MSEXPENSE, new String[]{EXPENSE_ID, EXPENSE_DATE, EXPENSE, EXPENSE_CATEGORY}, EXPENSE_DATE + " LIKE ?",
                new String[]{("%" + date)}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                countExpense += cursor.getLong(2);
            } while (cursor.moveToNext());
        }

        db.close();

        return countExpense;
    }

    public long countIncomes(String date)
    {
        long countIncome = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_MSADDINCOME, new String[]{INCOME_ID, INCOME_DATE, INCOME, INCOME_CATEGORY}, INCOME_DATE + " LIKE ?",
                new String[]{("%" + date)}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                countIncome += cursor.getLong(2);
            } while (cursor.moveToNext());
        }
        db.close();

        return countIncome;
    }

    public ArrayList<getAllData> getOnlyExpenses(String date)
    {
        ArrayList<getAllData> Datalist = new ArrayList<getAllData>();
        // Select All Query
//        String selectQuery = "SELECT * FROM " + TABLE_MSEXPENSE + " WHERE " +EXPENSE_DATE +" LIKE ?";
//        String[] selectionArgs = new String[]{}

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(TABLE_MSEXPENSE, new String[]{EXPENSE_ID, EXPENSE_DATE, "SUM("+EXPENSE+")", EXPENSE_CATEGORY}, EXPENSE_DATE + " LIKE ?",
                new String[]{("%" + date)}, EXPENSE_CATEGORY, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                getAllData Expense = new getAllData();
                Expense.setId(cursor.getString(0));
                Expense.setDate(cursor.getString(1));
                Expense.setExpense(Long.parseLong(cursor.getString(2)));
                Expense.setCategory(cursor.getString(3));
                // Adding contact to list
                Datalist.add(Expense);
            } while (cursor.moveToNext());
        }

        Collections.sort(Datalist, new Comparator<getAllData>() {
            @Override
            public int compare(getAllData lhs, getAllData rhs) {
                return lhs.getDate().compareTo(rhs.getDate());
            }
        });

        db.close();
        return Datalist;
    }

    public ArrayList<getAllData> getOnlyExpensesByDate()
    {
        ArrayList<getAllData> Datalist = new ArrayList<getAllData>();
        // Select All Query
//        String selectQuery = "SELECT * FROM " + TABLE_MSEXPENSE + " WHERE " +EXPENSE_DATE +" LIKE ?";
//        String[] selectionArgs = new String[]{}

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(TABLE_MSEXPENSE, new String[]{EXPENSE_ID, EXPENSE_DATE, "SUM("+EXPENSE+")", EXPENSE_CATEGORY}, null,
                null, EXPENSE_DATE, null, null, "4");

        if (cursor.moveToFirst()) {
            do {
                getAllData Expense = new getAllData();
                Expense.setId(cursor.getString(0));
                Expense.setDate(cursor.getString(1));
                Expense.setExpense(Long.parseLong(cursor.getString(2)));
                Expense.setCategory(cursor.getString(3));
                // Adding contact to list
                Datalist.add(Expense);
            } while (cursor.moveToNext());
        }

        Collections.sort(Datalist, new Comparator<getAllData>() {
            @Override
            public int compare(getAllData lhs, getAllData rhs) {
                return lhs.getDate().compareTo(rhs.getDate());
            }
        });

        db.close();
        return Datalist;
    }

    public ArrayList<getAllData> getOnlyIncomeByDate()
    {
        ArrayList<getAllData> Datalist = new ArrayList<getAllData>();
        // Select All Query
//        String selectQuery = "SELECT * FROM " + TABLE_MSEXPENSE + " WHERE " +EXPENSE_DATE +" LIKE ?";
//        String[] selectionArgs = new String[]{}

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(TABLE_MSADDINCOME, new String[]{INCOME_ID, INCOME_DATE, "SUM("+INCOME+")", INCOME_CATEGORY}, null,
                null, INCOME_DATE, null, null, "4");

        if (cursor.moveToFirst()) {
            do {
                getAllData Income = new getAllData();
                Income.setId(cursor.getString(0));
                Income.setDate(cursor.getString(1));
                Income.setExpense(Long.parseLong(cursor.getString(2)));
                Income.setCategory(cursor.getString(3));
                // Adding contact to list
                Datalist.add(Income);
            } while (cursor.moveToNext());
        }

        Collections.sort(Datalist, new Comparator<getAllData>() {
            @Override
            public int compare(getAllData lhs, getAllData rhs) {
                return lhs.getDate().compareTo(rhs.getDate());
            }
        });

        db.close();
        return Datalist;
    }



    /*
    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    */
}
