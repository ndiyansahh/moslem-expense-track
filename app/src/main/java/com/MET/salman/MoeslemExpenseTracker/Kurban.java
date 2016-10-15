package com.MET.salman.MoeslemExpenseTracker;

/**
 * Created by salma on 1/22/2016.
 */
public class Kurban {


    private String KurbanID;
    private long Price;
    private long Payment;
    private long Duration;

    public Kurban(String KurbanID, long Price, long Payment, long Duration)
    {
        this.KurbanID = KurbanID;
        this.Price = Price;
        this.Payment = Payment;
        this.Duration = Duration;
    }

    public Kurban()
    {

    }


    public String getKurbanID() {
        return KurbanID;
    }

    public void setKurbanID(String kurbanID) {
        KurbanID = kurbanID;
    }

    public long getPrice() {
        return Price;
    }

    public void setPrice(long price) {
        Price = price;
    }

    public long getPayment() {
        return Payment;
    }

    public void setPayment(long payment) {
        Payment = payment;
    }

    public long getDuration() {
        return Duration;
    }

    public void setDuration(long duration) {
        Duration = duration;
    }
}
