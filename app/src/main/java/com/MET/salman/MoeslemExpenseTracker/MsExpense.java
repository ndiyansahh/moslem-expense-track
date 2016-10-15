package com.MET.salman.MoeslemExpenseTracker;

/**
 * Created by Salman on 11/10/2015.
 */
public class MsExpense {

    private String ExpenseID;
    private String ExpenseDate;
    private long Expense;
    private String ExpenseCategory;

    public MsExpense(String ExpenseID, String ExpenseDate, long Expense, String ExpenseCategory)
    {
        this.ExpenseID = ExpenseID;
        this.ExpenseDate = ExpenseDate;
        this.Expense = Expense;
        this.ExpenseCategory = ExpenseCategory;
    }

    public MsExpense()
    {

    }


    public String getExpenseID() {
        return ExpenseID;
    }

    public void setExpenseID(String expenseID) {
        ExpenseID = expenseID;
    }

    public String getExpenseDate() {
        return ExpenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        ExpenseDate = expenseDate;
    }

    public long getExpense() {
        return Expense;
    }

    public void setExpense(long expense) {
        Expense = expense;
    }

    public String getExpenseCategory() {
        return ExpenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        ExpenseCategory = expenseCategory;
    }
}
