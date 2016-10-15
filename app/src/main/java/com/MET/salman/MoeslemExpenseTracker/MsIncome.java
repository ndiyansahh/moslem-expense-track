package com.MET.salman.MoeslemExpenseTracker;

/**
 * Created by Salman on 11/10/2015.
 */
public class MsIncome {

    private String IncomeID;
    private String IncomeDate;
    private long Income;
    private String IncomeCategory;

    public MsIncome(String IncomeID, String IncomeDate, long Income, String IncomeCategory)
    {
        this.IncomeID = IncomeID;
        this.IncomeDate = IncomeDate;
        this.Income = Income;
        this.IncomeCategory = IncomeCategory;
    }

    public MsIncome()
    {

    }

    public String getIncomeID() {
        return IncomeID;
    }

    public void setIncomeID(String incomeID) {
        IncomeID = incomeID;
    }

    public String getIncomeDate() {
        return IncomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        IncomeDate = incomeDate;
    }

    public long getIncome() {
        return Income;
    }

    public void setIncome(long income) {
        Income = income;
    }

    public String getIncomeCategory() {
        return IncomeCategory;
    }

    public void setIncomeCategory(String incomeCategory) {
        IncomeCategory = incomeCategory;
    }
}
