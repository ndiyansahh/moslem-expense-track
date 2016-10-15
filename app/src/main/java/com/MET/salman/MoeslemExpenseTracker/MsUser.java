package com.MET.salman.MoeslemExpenseTracker;

/**
 * Created by Salman on 11/7/2015.
 */
public class MsUser {

    private String UserID;
    private long Budget;
    private String Status;
    private long Installment;
    private long TotalWealth;
    private long Debt;


    public MsUser(String UserID, long Budget, String Status,long Installment,long TotalWealth,long Debt)
    {
        this.UserID = UserID;
        this.Budget = Budget;
        this.Status = Status;
        this.Installment = Installment;
        this.TotalWealth = TotalWealth;
        this.Debt = Debt;
    }

    public MsUser()
    {

    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public long getBudget() {
        return Budget;
    }

    public void setBudget(long budget) {
        Budget = budget;
    }

    public long getInstallment() {
        return Installment;
    }

    public void setInstallment(long installment) {
        Installment = installment;
    }

    public long getTotalWealth() {
        return TotalWealth;
    }

    public void setTotalWealth(long totalWealth) {
        TotalWealth = totalWealth;
    }

    public long getDebt() {
        return Debt;
    }

    public void setDebt(long debt) {
        Debt = debt;
    }
}
