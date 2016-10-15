package com.MET.salman.MoeslemExpenseTracker;

/**
 * Created by salma on 1/18/2016.
 */
public class ZakatProfesi {

    private String ZakatID;
    private long Amount;
    private String Date;

    public ZakatProfesi(String ZakatID, long Amount, String Date)
    {
        this.ZakatID = ZakatID;
        this.Amount = Amount;
        this.Date = Date;
    }

    public ZakatProfesi()
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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
