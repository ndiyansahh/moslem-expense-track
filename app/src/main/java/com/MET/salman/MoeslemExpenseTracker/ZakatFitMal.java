package com.MET.salman.MoeslemExpenseTracker;

/**
 * Created by salma on 1/18/2016.
 */
public class ZakatFitMal {

    private String ZakatID;
    private long Amount;

    public ZakatFitMal(String ZakatID, long Amount)
    {
        this.ZakatID = ZakatID;
        this.Amount = Amount;
    }

    public ZakatFitMal()
    {

    }


    public String getZakatID() {
        return ZakatID;
    }

    public void setZakatID(String zakatID) {
        ZakatID = zakatID;
    }

    public long getAmount() {
        return Amount;
    }

    public void setAmount(long amount) {
        Amount = amount;
    }
}
