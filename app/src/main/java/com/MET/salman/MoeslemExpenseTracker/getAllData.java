package com.MET.salman.MoeslemExpenseTracker;

/**
 * Created by Salman on 12/2/2015.
 */
public class getAllData {

    private String id;
    private String Date;
    private long Expense;
    private String Category;

    public getAllData(String id,String Date, long Expense, String Category)
    {
        this.id = id;
        this.Date = Date;
        this.Expense = Expense;
        this.Category = Category;
    }

    public getAllData()
    {

    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public long getExpense() {
        return Expense;
    }

    public void setExpense(long expense) {
        Expense = expense;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
