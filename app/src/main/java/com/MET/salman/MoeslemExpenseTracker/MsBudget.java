package com.MET.salman.MoeslemExpenseTracker;


/**
 * Created by Salman on 11/7/2015.
 */
public class MsBudget {

    private String BudgetID;
    private String BudgetDate;
    private int Budget;
    private int BudgetRemain;

    public MsBudget(String BudgetID, String BudgetDate, int Budget, int BudgetRemain)
    {
        this.BudgetID = BudgetID;
        this.BudgetDate = BudgetDate;
        this.Budget = Budget;
        this.BudgetRemain = BudgetRemain;
    }

    public MsBudget()
    {

    }

    public String getBudgetID() {
        return BudgetID;
    }

    public void setBudgetID(String budgetID) {
        BudgetID = budgetID;
    }

    public String getBudgetDate() {
        return BudgetDate;
    }

    public void setBudgetDate(String budgetDate) {
        BudgetDate = budgetDate;
    }

    public int getBudget() {
        return Budget;
    }

    public void setBudget(int budget) {
        Budget = budget;
    }

    public int getBudgetRemain() {
        return BudgetRemain;
    }

    public void setBudgetRemain(int budgetRemain) {
        BudgetRemain = budgetRemain;
    }
}
